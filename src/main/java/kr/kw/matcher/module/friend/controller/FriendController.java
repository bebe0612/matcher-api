package kr.kw.matcher.module.friend.controller;

import kr.kw.matcher.module.friend.dto.FriendDto;
import kr.kw.matcher.module.friend.service.FriendService;
import kr.kw.matcher.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/friends")
@RestController
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    public List<FriendDto> friends(Authentication authentication) { // 전체 친구 조회
        User user = (User) authentication.getPrincipal();

        return friendService.getFriends(user.getId());
    }

}
