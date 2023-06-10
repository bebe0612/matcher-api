package kr.kw.matcher.core.config;

import kr.kw.matcher.core.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasLength(jwt) && jwtTokenProvider.validate(jwt)) {
                try {
                    SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(jwt));
                } catch (AuthenticationException e) {
                    request.setAttribute("UnauthorizedException", e.getMessage());
                }
            } else {
                if (!StringUtils.hasLength(jwt)) {
                    request.setAttribute("UnauthorizedException", "인증토큰이 없습니다.");
                } else {
                    if (!jwtTokenProvider.validate(jwt)) {
                        request.setAttribute("UnauthorizedException", "인증토큰이 유효하지 않습니다.");
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context");
        }

        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "custId, appId, Origin, Content-Type, Cookie, X-CSRF-TOKEN, Accept, Authorization, X-XSRF-TOKEN, Access-Control-Allow-Origin");
        response.addHeader("Access-Control-Expose-Headers", "Authorization, authenticated");
        response.addHeader("Access-Control-Max-Age", "1728000");

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}

