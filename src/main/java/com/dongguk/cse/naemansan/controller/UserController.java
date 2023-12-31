package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.annotation.UserId;
import com.dongguk.cse.naemansan.dto.request.UserDeviceRequestDto;
import com.dongguk.cse.naemansan.dto.request.UserTagRequestDto;
import com.dongguk.cse.naemansan.dto.response.*;
import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.dto.request.UserRequestDto;
import com.dongguk.cse.naemansan.service.BadgeService;
import com.dongguk.cse.naemansan.service.FollowService;
import com.dongguk.cse.naemansan.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BadgeService badgeService;
    private final FollowService followService;
    @GetMapping("")
    public ResponseDto<UserDto> readUser(@UserId Long userId) {
        log.info("userId: {}", userId);
        return new ResponseDto<>(userService.readUserProfile(userId));
    }
    @GetMapping("/{otherUserId}")
    public ResponseDto<UserDto> readAnotherUser(@PathVariable Long otherUserId) {
        return new ResponseDto<UserDto>(userService.readUserProfile(otherUserId));
    }

    @PutMapping("")
    public ResponseDto<UserDto> updateUser(@UserId Long userId, @RequestBody UserRequestDto userRequestDto) {
        return new ResponseDto<UserDto>(userService.updateUserProfile(userId, userRequestDto));
    }

    @DeleteMapping("")
    public ResponseDto<Boolean> deleteUser(@UserId Long userId) {
        return new ResponseDto<Boolean>(userService.deleteUserProfile(userId));
    }

    @GetMapping("/badge")
    public ResponseDto<List<BadgeDto>> readBadgeList(@UserId Long userId) {
        return new ResponseDto<List<BadgeDto>>(badgeService.readBadgeList(userId));
    }

    @GetMapping("/comment")
    public ResponseDto<List<CommentListDto>> readCommentList(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<CommentListDto>>(userService.readCommentList(userId, page, num));
    }

    // User가 팔로우한 사람들의 List를 얻음 - Follow Read#1
    @GetMapping("/following")
    public ResponseDto<List<FollowDto>> readFollowing(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<FollowDto>>(followService.readFollowing(userId, page, num));
    }

    // User를 팔로우한 사람들의 List를 얻음 - Follow Read#2
    @GetMapping("/follower")
    public ResponseDto<List<FollowDto>> readFollower(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<FollowDto>>(followService.readFollower(userId, page, num));
    }

    @PostMapping("/tags")
    public ResponseDto<?> createUserTag(@UserId Long userId, @RequestBody UserTagRequestDto requestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("tags", userService.createTagByUserChoice(userId, requestDto));
        return new ResponseDto<>(map);
    }

    @GetMapping("/tags")
    public ResponseDto<?> readUserTag(@UserId Long userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("tags", userService.readTagByUserChoice(userId));
        return new ResponseDto<Map<String, Object>>(map);
    }

    @PutMapping("/tags")
    public ResponseDto<?> readUserTag(@UserId Long userId, @RequestBody UserTagRequestDto requestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("tags", userService.updateTagByUserChoice(userId, requestDto));
        return new ResponseDto<Map<String, Object>>(map);
    }

    @PutMapping("/notification")
    public ResponseDto<?> updateUserDevice(@UserId Long userId, @RequestBody UserDeviceRequestDto requestDto) {
        return new ResponseDto<Boolean>(userService.updateUserDevice(userId, requestDto));
    }
}
