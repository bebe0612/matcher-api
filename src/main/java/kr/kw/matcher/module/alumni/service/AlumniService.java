package kr.kw.matcher.module.alumni.service;

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

    @Transactional(readOnly = true)
    public List<UserDto> getRecentAlumni(Long userId) {
        User user = userRepository.getReferenceById(userId);

        return userRepository.findBySchoolNameAndYearOfAdmissionOrderByCreatedDtDesc(
                user.getSchoolName(),
                user.getYearOfAdmission()
        )
                .stream()
                .limit(6)
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
