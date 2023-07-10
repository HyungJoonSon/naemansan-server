package com.dongguk.cse.naemansan.service;

import com.dongguk.cse.naemansan.domain.User;
import com.dongguk.cse.naemansan.repository.FollowRepository;
import com.dongguk.cse.naemansan.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FollowService 테스트")
@ExtendWith(MockitoExtension.class)
@Transactional
class FollowServiceTest {
    @Spy
    private FollowRepository followRepository;
    @Spy
    private UserRepository userRepository;
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private FollowService followService;


    @BeforeEach
    void setUp() {
    }
//
//    @AfterEach
//    void tearDown() {
//    }

    @Test
    @DisplayName("팔로우 생성")
    void createFollow() {
        // given
        Long followingId = 2L;
        Long followerId = 3L;
        Optional<User> user = userRepository.findById(followerId);
        if (user.isEmpty()) {
            System.out.println("User 못찾음");
            return;
        } else {
            System.out.println("User 찾음");
            return;
        }

//        // when
//        Boolean result = followService.createFollow(followingId, followerId);
//
//        // then
//        assertEquals(result, Boolean.TRUE);
    }

    @Test
    void readFollowing() {
    }

    @Test
    void readFollower() {
    }

    @Test
    void deleteFollow() {
    }
}