package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.common.ResponseDto;
import com.dongguk.cse.naemansan.dto.request.AdvertisementRequestDto;
import com.dongguk.cse.naemansan.dto.request.NoticeRequestDto;
import com.dongguk.cse.naemansan.dto.request.ShopRequestDto;
import com.dongguk.cse.naemansan.dto.response.AdvertisementDto;
import com.dongguk.cse.naemansan.dto.response.NoticeDetailDto;
import com.dongguk.cse.naemansan.dto.response.ShopDto;
import com.dongguk.cse.naemansan.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CommonService commonService;

    @PostMapping("/shop")
    public ResponseDto<?> createShopProfile(@RequestBody ShopRequestDto requestDto) {
        return new ResponseDto<Boolean>(commonService.createShopProfile(requestDto));
    }

    @PutMapping("/shop/{shopId}")
    public ResponseDto<?> updateShopProfile(@PathVariable Long shopId, @RequestBody ShopRequestDto requestDto) {
        return new ResponseDto<ShopDto>(commonService.updateShopProfile(shopId, requestDto));
    }

    @DeleteMapping("/shop/{shopId}")
    public ResponseDto<?> deleteShopProfile(@PathVariable Long shopId) {
        return new ResponseDto<Boolean>(commonService.deleteShopProfile(shopId));
    }
}
