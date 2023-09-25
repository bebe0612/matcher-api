package kr.kw.matcher.module.alumni.controller;

import kr.kw.matcher.module.alumni.service.AlumniService;
import kr.kw.matcher.module.user.application.dto.UserDto;
import kr.kw.matcher.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/alumni")
@RestController
public class AlumniController {
    private final AlumniService alumniService;

    @GetMapping
    public List<UserDto> alumni(Authentication authentication) { // 전체 동창 리스트
        User user = (User) authentication.getPrincipal();

        return alumniService.getRecentAlumni(user.getId());
    }

    @PostMapping("/{alumniId}")
    public void addFriend(Authentication authentication, @PathVariable Long alumniId) { // 해당 동창을 친구로 추가
        User user = (User) authentication.getPrincipal();

        alumniService.addFriend(user.getId(), alumniId);
    }
}
