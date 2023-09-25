package kr.kw.matcher.module.alumni.controller;

import kr.kw.matcher.module.alumni.service.AlumniService;
import kr.kw.matcher.module.user.application.dto.UserDto;
import kr.kw.matcher.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/alumni")
@RestController
public class AlumniController {
    private final AlumniService alumniService;

    @GetMapping
    public List<UserDto> alumni (Authentication authentication) { // 전체 동창 리스트
        User user = (User) authentication.getPrincipal();

        return alumniService.getRecentAlumni(user.getId());
    }
}
