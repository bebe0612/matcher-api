package kr.kw.matcher.module.user.application;


import kr.kw.matcher.module.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String getUserNickname(Long userId) {
        return userRepository.getReferenceById(userId).getNickname();
    }
}
