package kr.kw.matcher.module.user.application;

import kr.kw.matcher.core.config.JwtTokenProvider;
import kr.kw.matcher.core.exception.ConflictException;
import kr.kw.matcher.module.member.application.CustomUserDetailsService;
import kr.kw.matcher.module.member.domain.Member;
import kr.kw.matcher.module.member.domain.MemberRepository;
import kr.kw.matcher.module.user.application.dto.AuthDto;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import kr.kw.matcher.module.user.presentation.request.SignInBody;
import kr.kw.matcher.module.user.presentation.request.SignUpBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthDto signUp(SignUpBody body) {
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new ConflictException("이미 존재하는 이메일입니다.");
        }

        Member newMember = Member.ofUser();
        memberRepository.save(newMember);

        User newUser = User.of(newMember.getId(),
                body.getEmail(),
                passwordEncoder.encode(body.getPassword()),
                body.getName(),
                body.getSchoolName(),
                body.getYearOfAdmission()
        );

        userRepository.save(newUser);

        String jwtToken = jwtTokenProvider.createToken(newMember.getId().toString(), newMember.getRoles());

        return AuthDto.builder()
                .token(jwtToken)
                .userId(newMember.getId())
                .build();
    }

    @Transactional
    public AuthDto signIn(SignInBody body) {
        User user = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> new ConflictException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            throw new ConflictException("비밀번호가 일치하지 않습니다.");
        }

        Member member = memberRepository.findById(user.getId()).orElseThrow(() -> new ConflictException("존재하지 않는 회원입니다."));

        String jwtToken = jwtTokenProvider.createToken(member.getId().toString(), member.getRoles());

        return AuthDto.builder()
                .token(jwtToken)
                .userId(member.getId())
                .build();
    }
}
