package com.dongguk.cse.naemansan.service;

import com.dongguk.cse.naemansan.common.ErrorCode;
import com.dongguk.cse.naemansan.common.RestApiException;
import com.dongguk.cse.naemansan.domain.*;
import com.dongguk.cse.naemansan.domain.type.ETagStatus;
import com.dongguk.cse.naemansan.dto.TagDto;
import com.dongguk.cse.naemansan.dto.request.UserDeviceRequestDto;
import com.dongguk.cse.naemansan.dto.request.UserTagRequestDto;
import com.dongguk.cse.naemansan.dto.response.CommentListDto;
import com.dongguk.cse.naemansan.dto.response.UserDto;
import com.dongguk.cse.naemansan.dto.request.UserRequestDto;
import com.dongguk.cse.naemansan.repository.*;
import com.dongguk.cse.naemansan.util.CourseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final CourseUtil courseUtil;

    public UserDto readUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        Long commentCnt = commentRepository.countByUserAndStatus(user, true);

        return UserDto.builder()
                .user(user)
                .image(user.getImage())
                .comment_cnt(commentCnt)
                .like_cnt((long) user.getLikes().size())
                .badge_cnt((long) user.getBadges().size())
                .following_cnt((long) user.getFollowings().size())
                .follower_cnt((long) user.getFollowers().size()).build();
    }

    @Transactional
    public UserDto updateUserProfile(Long userId, UserRequestDto userRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        userRepository.findByIdNotAndNickname(userId, userRequestDto.getName()).ifPresent(u -> { throw new RestApiException(ErrorCode.DUPLICATION_NAME); });

        if ((userRequestDto.getName() == null) || (userRequestDto.getName().length() == 0)) {
            throw new RestApiException(ErrorCode.NOT_EXIST_PARAMETER);
        }

        user.updateUser(userRequestDto.getName(), userRequestDto.getIntroduction());
        Long commentCnt = commentRepository.countByUserAndStatus(user, true);

        return UserDto.builder()
                .user(user)
                .image(user.getImage())
                .comment_cnt(commentCnt)
                .like_cnt((long) user.getLikes().size())
                .badge_cnt((long) user.getBadges().size())
                .following_cnt((long) user.getFollowings().size())
                .follower_cnt((long) user.getFollowers().size()).build();
    }

    public Boolean deleteUserProfile(Long id) {
        // 삭제할 유저를 찾고, 해당 유저가 작성한 course, comment 를 Super_Admin(삭제된 게시물 용) 계정으로 Update
        User user = userRepository.findById(id).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        User Admin = userRepository.findById(1L).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));

        List<EnrollmentCourse> enrollmentCourseList = user.getEnrollmentCourses();
        List<Comment> comments =  user.getComments();
        userRepository.delete(user);

        for (EnrollmentCourse enrollmentCourse : enrollmentCourseList) {
            enrollmentCourse.setUser(Admin);
        }
        for (Comment comment : comments) {
            comment.setUser(Admin);
        }

        return Boolean.TRUE;
    }

    public List<TagDto> createTagByUserChoice(Long userId, UserTagRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        List<Tag> tags = tagRepository.findTagsByIds(requestDto.getTags().stream()
                .map(tagDto -> tagDto.getName().getId())
                .collect(Collectors.toList()));

        userTagRepository.saveAll(courseUtil.getTag2UserTag(user, tags));

        return courseUtil.getTag2TagDto(tags);
    }

    @Deprecated
    public List<TagDto> readTagByUserChoice(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));

        return courseUtil.getTag2TagDto(user.getUserTags().stream()
                .map(UserTag::getTag)
                .collect(Collectors.toList()));
    }

    public List<TagDto> updateTagByUserChoice(Long userId, UserTagRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));
        userTagRepository.deleteAll(user.getUserTags());
        List<Tag> tags = tagRepository.findTagsByIds(requestDto.getTags().stream()
                .filter(tagDto -> tagDto.getStatus() != ETagStatus.DELETE)
                .map(tagDto -> tagDto.getName().getId())
                .collect(Collectors.toList()));

        userTagRepository.saveAll(courseUtil.getTag2UserTag(user, tags));

        return courseUtil.getTag2TagDto(tags);
    }

    public List<CommentListDto> readCommentList(Long userId, Long pageNum, Long num) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));

        Pageable paging = PageRequest.of(pageNum.intValue(), num.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Comment> comments = commentRepository.findListByUser(user, paging);



        List<CommentListDto> list = new ArrayList<>();

        for (Comment comment: comments.getContent()) {
            EnrollmentCourse course = comment.getEnrollmentCourse();

            list.add(CommentListDto.builder()
                    .id(comment.getId())
                    .course_id(course.getId())
                    .course_title(course.getTitle())
                    .content(comment.getContent())
                    .tags(courseUtil.getTag2TagDto(course.getCourseTags().stream()
                            .map(CourseTag::getTag)
                            .collect(Collectors.toList()))).build());
        }

        return list;
    }

    public Boolean updateUserDevice(Long userId, UserDeviceRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER));

        user.updateDevice(requestDto.getDevice_token(), requestDto.getIs_ios());
        return Boolean.TRUE;
    }
}
