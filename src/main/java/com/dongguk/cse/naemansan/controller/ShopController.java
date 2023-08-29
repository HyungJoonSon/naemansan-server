package com.dongguk.cse.naemansan.controller;

import com.dongguk.cse.naemansan.exception.ResponseDto;
import com.dongguk.cse.naemansan.dto.request.ShopRequestDto;
import com.dongguk.cse.naemansan.dto.response.ShopDto;
import com.dongguk.cse.naemansan.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final CommonService commonService;

    @GetMapping("/shop")
    public ResponseDto<?> readShopList(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
                                          @RequestParam("page") Long page, @RequestParam("num") Long num) {
        return new ResponseDto<List<ShopDto>>(commonService.readShopList(latitude, longitude, page, num));
    }

    @PostMapping("/admin/hop")
    public ResponseDto<?> createShopProfile(@RequestBody ShopRequestDto requestDto) {
        return new ResponseDto<Boolean>(commonService.createShopProfile(requestDto));
    }

    @PutMapping("/admin/shop/{shopId}")
    public ResponseDto<?> updateShopProfile(@PathVariable Long shopId, @RequestBody ShopRequestDto requestDto) {
        return new ResponseDto<ShopDto>(commonService.updateShopProfile(shopId, requestDto));
    }

    @DeleteMapping("/admin/shop/{shopId}")
    public ResponseDto<?> deleteShopProfile(@PathVariable Long shopId) {
        return new ResponseDto<Boolean>(commonService.deleteShopProfile(shopId));
    }
}
