package kr.kw.matcher.module.alumni.controller;

import kr.kw.matcher.module.alumni.service.AlumniService;
import kr.kw.matcher.module.member.domain.Member;
import kr.kw.matcher.module.user.application.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/home")
@RestController
public class AlumniController {
    private final AlumniService alumniService;

    @GetMapping
    public List<UserDto> recentAlumni(@AuthenticationPrincipal Member member) {
        return alumniService.getRecentAlumni(member.getId());
    }
}
