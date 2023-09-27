package kr.kw.matcher.module.user.application;


import kr.kw.matcher.core.exception.NotFoundException;
import kr.kw.matcher.module.user.application.dto.UserDto;
import kr.kw.matcher.module.user.domain.User;
import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getMyProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        return UserDto.from(user);
    }

    public String getUserNickname(Long userId) {
        return userRepository.getReferenceById(userId).getNickname();
    }
}
