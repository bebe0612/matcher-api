package kr.kw.matcher.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {

        String exception = (String) request.getAttribute("UnauthorizedException");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception);
        log.error("exception {} {} {}", exception, request.getRequestURI(), request.getRemoteHost());
    }
}