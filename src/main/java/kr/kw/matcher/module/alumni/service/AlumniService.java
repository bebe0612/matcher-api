package kr.kw.matcher.module.alumni.service;

import kr.kw.matcher.core.exception.NotFoundException;
import kr.kw.matcher.module.friend.domain.Friend;
import kr.kw.matcher.module.friend.domain.FriendRepository;
import kr.kw.matcher.module.user.application.dto.UserDto;
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
public class AlumniService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional(readOnly = true)
    public List<UserDto> getRecentAlumni(Long userId) {
        User user = userRepository.getReferenceById(userId);

        return userRepository.findBySchoolNameAndYearOfAdmissionOrderByCreatedDtDesc(
                user.getSchoolName(),
                user.getYearOfAdmission()
        )
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public void addFriend(Long userId, Long alumniId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        User alumni = userRepository.findById(alumniId).orElseThrow(NotFoundException::new);

        // 한 쪽이 친구로 추가하더라도 쌍방 친구 추가
        friendRepository.save(Friend.of(user, alumni));
        friendRepository.save(Friend.of(alumni, user));
    }
}
