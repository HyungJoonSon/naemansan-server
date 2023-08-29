package com.dongguk.cse.naemansan.domain;

import com.dongguk.cse.naemansan.domain.type.ELoginProvider;
import com.dongguk.cse.naemansan.domain.type.EUserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name = "serial_id")
    private String serialId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private EUserRole userRoleType;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isLogin;

    @Column(name = "is_ios", columnDefinition = "TINYINT(1)")
    private Boolean isIos;

    @Column(name = "refresh_Token")
    private String refreshToken;

    @Column(name = "device_Token")
    private String deviceToken;

    // ------------------------------------------------------------

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Image image;

    @OneToMany(mappedBy = "followingUser", fetch = FetchType.LAZY)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "followerUser", fetch = FetchType.LAZY)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserTag> userTags = new ArrayList<>();

    // ------------------------------------------------------------

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<IndividualCourse> individualCourses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EnrollmentCourse> enrollmentCourses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UsingCourse> usingCourses = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    @Builder
    public User(ELoginProvider provider, String serialId, String nickname, String password,
                String introduction, EUserRole role) {
        this.provider = provider;
        this.serialId = serialId;
        this.nickname = nickname;
        this.password = password;
        this.introduction = introduction;
        this.userRoleType = role;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.isLogin = true;
        this.isIos = false;
        this.refreshToken = null;
        this.deviceToken = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.isLogin = true;
        this.refreshToken = refreshToken;
    }

    public void updateUser(String nickname, String introduction) {
        this.nickname = nickname;
        this.introduction = introduction;
    }

    public void updateDevice(String deviceToken, Boolean isIos) {
        this.deviceToken = deviceToken;
        this.isIos = isIos;
    }

    public void logoutUser() {
        this.isLogin = false;
        this.refreshToken = null;
        this.deviceToken = null;
    }
}
