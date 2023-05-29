package kr.kw.matcher.core.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.kw.matcher.core.exception.AuthenticationException;
import kr.kw.matcher.core.exception.ConflictException;
import kr.kw.matcher.module.member.application.CustomUserDetailsService;
import kr.kw.matcher.module.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${matcher.jwtSecretKey}")
    private String secretKey;

    private final static long TOKEN_VALID_MILLISECOND = 1000L * 60 * 60 * 24 * 90; // 90일 토큰 유효
    private final static long TOKEN_VALID_MILLISECOND_ADMIN = 1000L * 60 * 60 * 36; // 하루 유효

    private final CustomUserDetailsService customUserDetailsService;

    public String createAdminToken(String userId, List<String> role) {
        // 토큰 제목에 userId 저장
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        // 발행일과 만료일을 토큰에 저장
        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expireDt = issuedDt.plus(TOKEN_VALID_MILLISECOND_ADMIN, ChronoUnit.MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedDt))
                .setExpiration(Date.from(expireDt))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Token 생성
    public String createToken(String userId, List<String> role) {

        // 토큰 제목에 userId 저장
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role);

        // 발행일과 만료일을 토큰에 저장
        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expireDt = issuedDt.plus(TOKEN_VALID_MILLISECOND, ChronoUnit.MILLIS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedDt))
                .setExpiration(Date.from(expireDt))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }



    public Authentication getAuthentication(String token) throws AuthenticationException {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String username = claims.getSubject();

        Member member = customUserDetailsService.loadUserByUsername(username);

        ArrayList<String> arrayList = (ArrayList)claims.get("role");

        Collection<? extends GrantedAuthority> memberAuthorities = member.getAuthorities();

        if(memberAuthorities.stream().map(Objects::toString).collect(Collectors.toList()).containsAll(arrayList)){
            return new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
        }
        throw new ConflictException("jwt 인증정보와 member 인증정보 불일치");
    }

    // 토큰 검증 1. 비밀키로 디코딩
    // 토큰 검증 2. 토큰이 만료되었는지 체크
    public boolean validate(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            // 2.
            Date expireDt = claims.getBody().getExpiration();
            Date nowDt = new Date();

            return !expireDt.before(nowDt);
        }catch (Exception e){
            return false;
        }
    }

    // createToken() 에서 토큰 제목에 넣어둔 userId 값을 꺼내옴
    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
