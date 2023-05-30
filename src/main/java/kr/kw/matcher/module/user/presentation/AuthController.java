package kr.kw.matcher.module.user.presentation;

import kr.kw.matcher.module.user.application.AuthService;
import kr.kw.matcher.module.user.application.dto.AuthDto;
import kr.kw.matcher.module.user.presentation.request.SignInEmailBody;
import kr.kw.matcher.module.user.presentation.request.SignUpBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/signup")
    public AuthDto signUp(@RequestBody @Valid SignUpBody body) {
        return authService.signUp(body);
    }

    @PostMapping(value = "/signin/email")
    public AuthDto signInWithEmail(@RequestBody @Valid SignInEmailBody body) {
        return AuthDto.builder()
                .token("token")
                .userId(1L)
                .build();
    }
}
