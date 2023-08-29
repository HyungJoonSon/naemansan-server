package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.annotation.UserId;
import com.dongguk.cse.naemansan.dto.NotificationDto;
import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.dto.request.NotificationRequestDto;
import com.dongguk.cse.naemansan.service.NotificationService;
import com.dongguk.cse.naemansan.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationUtil notificationUtil;

    //안드로이드 푸시알림 테스트
    @PostMapping("/andfcm")
    public void sendNotificationByToken(@RequestBody NotificationRequestDto requestDto) throws IOException {
        notificationUtil.sendNotificationByTokenTest(requestDto.getTargetToken(), requestDto.getTitle(), requestDto.getBody());
    }

    //ios 푸시알림 테스트
    @PostMapping("/api/iosfcm")
    public void pushIosMessage(@RequestBody String token) throws Exception {
        notificationUtil.sendApnFcmtokenTest(token);
    }

    //Notification Read
    @GetMapping("")
    public ResponseDto<List<NotificationDto>> readNotification(@UserId Long userId, @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<NotificationDto>>(notificationService.readNotification(userId, page, num));
    }

    //Notification Update
    @PutMapping("/{notificationId}")
    public ResponseDto<Boolean> updateNotification(@UserId Long userId, @PathVariable Long notificationId) {
        return new ResponseDto<Boolean>(notificationService.updateNotification(userId, notificationId));
    }

    //Notification Delete
    @DeleteMapping("/{notificationId}")
    public ResponseDto<Boolean> deleteNotification(@UserId Long userId, @PathVariable Long notificationId) {
        return new ResponseDto<Boolean>(notificationService.deleteNotification(userId, notificationId));
    }
}
