package kr.kw.matcher.module.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void ofTest() {
        User user = User.of(1L, "email", "password", "name");

        assertEquals(1L, user.getId());
        assertEquals(false, user.getDeleted());
        assertNotNull(user.getCreatedDt());
    }

    @Test
    void saveTest() {
        User user = User.of(1L, "email", "password", "name");

        User saved = userRepository.save(user);
        Optional<User> found = userRepository.findById(1L);

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }
}
