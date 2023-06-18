package kr.kw.matcher.module.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findBySchoolNameAndYearOfAdmissionOrderByCreatedDtDesc(String schoolName, Long yearOfAdmission);
}
