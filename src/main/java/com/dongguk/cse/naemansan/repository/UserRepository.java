package com.dongguk.cse.naemansan.repository;

import com.dongguk.cse.naemansan.domain.type.ELoginProvider;
import com.dongguk.cse.naemansan.domain.User;
import com.dongguk.cse.naemansan.domain.type.EUserRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdNotAndNickname(Long userId, String nickname);
    Optional<User> findBySerialIdAndProvider(String serialId, ELoginProvider ELoginProvider);

    @Query(value = "SELECT u.id, u.role FROM User u WHERE u.id = :userId")
    Optional<Object[]> findUserForAuthentication(@Param("userId") Long userId);

    @Query("SELECT u.id AS id, u.role AS userRoleType FROM User u WHERE u.id = :userId AND u.isLogin = true AND u.refreshToken = :refreshToken")
    Optional<UserLoginForm> findByIdAndRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);

    Optional<User> findByIdAndIsLoginAndRefreshTokenIsNotNull(Long userId, Boolean isLogin);

    @EntityGraph(attributePaths = {"image", "badges", "likes", "followings", "followers", "userTags"},
            type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(Long id);


    public interface UserLoginForm {
        Long getId();
        EUserRole getUserRoleType();
    }
}