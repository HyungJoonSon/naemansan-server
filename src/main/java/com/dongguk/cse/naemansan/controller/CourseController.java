package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.annotation.UserId;
import com.dongguk.cse.naemansan.domain.type.ECourseTag;
import com.dongguk.cse.naemansan.dto.request.IndividualCourseRequestDto;
import com.dongguk.cse.naemansan.dto.response.*;
import com.dongguk.cse.naemansan.dto.request.EnrollmentCourseRequestDto;
import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    // Individual Course Create
    @PostMapping("/individual")
    public ResponseDto<IndividualCourseDetailDto> createIndividualCourse(@UserId Long userId, @RequestBody IndividualCourseRequestDto requestDto){
        return new ResponseDto<>(courseService.createIndividualCourse(userId, requestDto));
    }

    // Individual Course Read
    @GetMapping("/individual/{courseId}")
    public ResponseDto<IndividualCourseDetailDto> readIndividualCourse(@UserId Long userId, @PathVariable Long courseId){
        return new ResponseDto<>(courseService.readIndividualCourse(userId, courseId));
    }

    // Individual Course Update
    @PutMapping("/individual/{courseId}")
    public ResponseDto<Boolean> updateIndividualCourse(@UserId Long userId, @PathVariable Long courseId){
        return new ResponseDto<>(courseService.updateIndividualCourse(userId, courseId));
    }

    // Individual Course Delete
    @DeleteMapping("/individual/{courseId}")
    public ResponseDto<Boolean> deleteIndividualCourse(@UserId Long userId, @PathVariable Long courseId){
        return new ResponseDto<>(courseService.deleteIndividualCourse(userId, courseId));
    }

    // Course Create
    @PostMapping("/enrollment")
    public ResponseDto<EnrollmentCourseDetailDto> createCourse(@UserId Long userId, @RequestBody EnrollmentCourseRequestDto enrollmentCourseRequestDto){
        return new ResponseDto<>(courseService.createEnrollmentCourse(userId, enrollmentCourseRequestDto));
    }

    // Course Read
    @GetMapping("/enrollment/{courseId}")
    public ResponseDto<EnrollmentCourseDetailDto> readCourse(@UserId Long userId, @PathVariable Long courseId) {
        return new ResponseDto<>(courseService.readEnrollmentCourse(userId, Long.valueOf(courseId)));
    }

    // Course Update
    @PutMapping("/enrollment/{courseId}")
    public ResponseDto<EnrollmentCourseDetailDto> updateCourse(@UserId Long userId, @PathVariable Long courseId, @RequestBody EnrollmentCourseRequestDto enrollmentCourseRequestDto) {
        return new ResponseDto<>(courseService.updateEnrollmentCourse(userId, courseId, enrollmentCourseRequestDto));
    }

    // Course Delete
    @DeleteMapping("/enrollment/{courseId}")
    public ResponseDto<Boolean> deleteCourse(@UserId Long userId, @PathVariable Long courseId) {
        return new ResponseDto<>(courseService.deleteEnrollmentCourse(userId, Long.valueOf(courseId)));
    }

    // Using Course Create
    @PostMapping("/using")
    public ResponseDto<Boolean> createUsingCourse(@UserId Long userId, @RequestBody UsingCourseRequestDto requestDto){
        return new ResponseDto<>(courseService.createUsingCourse(userId, requestDto));
    }

    @GetMapping("/list/main/tag")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByTagForMain(@UserId Long userId, @RequestParam("name") String tag) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByTag(userId, 0L, 5L, tag));
    }

    @GetMapping("/list/main/location")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByLocationsForMain(@UserId Long userId, @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByLocation(userId, 0L, 5L, latitude, longitude));
    }

    @GetMapping("/list/individual/basic")
    public ResponseDto<List<IndividualCourseListDto>> getIndividualCourseList(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<IndividualCourseListDto>>(courseService.getIndividualCourseList(userId, page, num));
    }

    @GetMapping("/list/individual/enrollment")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByUser(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByUser(userId, page, num));
    }

    @GetMapping("/list/individual/like")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByLikeAndUser(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByLikeAndUser(userId, page, num));
    }

    @GetMapping("/list/individual/using")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByUsingAndUser(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByUsingAndUser(userId, page, num));
    }

    @GetMapping("/list/individual/tag")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByTag(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num,
                                                                         @RequestParam("name") String tag) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByTag(userId, page, num, tag));
    }

    // 서버 연동 시 사용가능
    @GetMapping("/list/recommend")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByUsingCount(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByRecommend(userId, page, num));
    }

    @GetMapping("/list/all")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByRecommend(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseList(userId, page, num));
    }

    @GetMapping("/list/like")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseList(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByLikeCount(userId, page, num));
    }

    @GetMapping("/list/using")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByLikeCount(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByUsingCount(userId, page, num));
    }

    @GetMapping("/list/location")
    public ResponseDto<List<EnrollmentCourseListDto>> getEnrollmentCourseListByLocation(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num,
                                                                               @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        return new ResponseDto<List<EnrollmentCourseListDto>>(courseService.getEnrollmentCourseListByLocation(userId, page, num, latitude, longitude));
    }

    @PostMapping("/{courseId}/like")
    public ResponseDto<Map<String, Object>> likeCourse(@UserId Long userId, @PathVariable Long courseId) {
        return new ResponseDto<Map<String, Object>>(courseService.likeCourse(userId, courseId));
    }

    @DeleteMapping("/{courseId}/like")
    public ResponseDto<Map<String, Object>> dislikeCourse(@UserId Long userId, @PathVariable Long courseId) {
        return new ResponseDto<Map<String, Object>>(courseService.dislikeCourse(userId, courseId));
    }

    @GetMapping("/tags")
    public ResponseDto<?> getTagList() {
        return new ResponseDto<List<ECourseTag>>(courseService.getTagList());
    }
}
