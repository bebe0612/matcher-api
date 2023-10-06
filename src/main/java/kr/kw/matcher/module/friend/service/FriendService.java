package kr.kw.matcher.module.friend.service;

import kr.kw.matcher.module.chat.chat_room.application.ChatRoomService;
import kr.kw.matcher.module.friend.dto.FriendDto;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FriendService {
    private final UserRepository userRepository;

    private final ChatRoomService chatRoomService;

    @Transactional(readOnly = true)
    public List<FriendDto> getFriends(Long userId) {
        User user = userRepository.getReferenceById(userId);

        var friends = user.getFriends()
                .stream()
                .map(FriendDto::from)
                .collect(Collectors.toList());

        for(FriendDto friend : friends) {
            Long roomId = chatRoomService.getChatRoomId(userId, friend.getId());

            if(roomId != null) {
                friend.setRoomId(roomId);
            }
        }

        return friends;
    }
}
