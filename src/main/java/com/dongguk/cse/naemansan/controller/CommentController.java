package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.annotation.UserId;
import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.dto.request.CommentRequestDto;
import com.dongguk.cse.naemansan.dto.response.CommentDto;
import com.dongguk.cse.naemansan.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CommentController {
    private final CommentService commentService;
    // Comment Create
    @PostMapping("/{courseId}/comment")
    public ResponseDto<Boolean> createComment(@UserId Long userId,
                                              @PathVariable Long courseId,
                                              @RequestBody CommentRequestDto commentRequestDto){
        return new ResponseDto<Boolean>(commentService.createComment(userId, courseId, commentRequestDto));
    }

    // Comment Read
    @GetMapping("/{courseId}/comment")
    public ResponseDto<List<CommentDto>> readComment(@PathVariable Long courseId , @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<CommentDto>>(commentService.readComment(courseId, page, num));
    }

    // Comment Update
    @PutMapping("/{courseId}/comment/{commentId}")
    public ResponseDto<Boolean> updateComment(@UserId Long userId, @PathVariable Long courseId,
                                              @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        return new ResponseDto<Boolean>(commentService.updateComment(userId, courseId, commentId, commentRequestDto));
    }

    // Comment Delete
    @DeleteMapping("/{courseId}/comment/{commentId}")
    public ResponseDto<Boolean> deleteComment(@UserId Long userId, @PathVariable Long courseId, @PathVariable Long commentId) {
        return new ResponseDto<Boolean>(commentService.deleteComment(userId, courseId, commentId));
    }
}
