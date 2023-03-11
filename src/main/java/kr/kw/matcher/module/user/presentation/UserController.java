package kr.kw.matcher.module.user.presentation;

import kr.kw.matcher.module.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public void getMyProfile() {
    }
}
