package kr.kw.matcher.module.member.application;

import kr.kw.matcher.core.exception.ForbiddenException;
import kr.kw.matcher.module.member.domain.Member;
import kr.kw.matcher.module.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public Member loadUserByUsername(String username) throws UsernameNotFoundException, AuthenticationException {
        Member member = memberRepository.findById(Long.valueOf(username)).orElseThrow(ForbiddenException::new);

        return member;
    }

    public Member signIn(String username, String token) throws UsernameNotFoundException, AuthenticationException {
        Member member = loadUserByUsername(username);

        memberRepository.save(member);

        return member;
    }
}
