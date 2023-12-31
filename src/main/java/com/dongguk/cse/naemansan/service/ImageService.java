package com.dongguk.cse.naemansan.service;

import com.dongguk.cse.naemansan.exception.ErrorCode;
import com.dongguk.cse.naemansan.exception.RestApiException;
import com.dongguk.cse.naemansan.domain.Image;
import com.dongguk.cse.naemansan.domain.Shop;
import com.dongguk.cse.naemansan.domain.User;
import com.dongguk.cse.naemansan.domain.type.ImageUseType;
import com.dongguk.cse.naemansan.repository.ImageRepository;
import com.dongguk.cse.naemansan.repository.ShopRepository;
import com.dongguk.cse.naemansan.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final ImageRepository imageRepository;

    @Value("${spring.image.path}")
    private String FOLDER_PATH;

    public String uploadImage(Long useId, ImageUseType imageUseType, MultipartFile file) throws IOException {
        // File Path Fetch
        String uuidImageName = UUID.randomUUID().toString() + "."
                + file.getContentType().substring(file.getContentType().indexOf("/") + 1);
        String filePath = FOLDER_PATH + uuidImageName;

        // File Upload
        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            throw new RestApiException(ErrorCode.FILE_UPLOAD);
        }

        // Path DB Save
        Object useObject = null;
        switch (imageUseType) {
            case USER -> { useObject = userRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_USER)); }
            case SHOP -> { useObject = shopRepository.findById(useId).orElseThrow(() -> new RestApiException(ErrorCode.NOT_FOUND_SHOP)); }
        }

        // Image Object find
        Image findImage = null;
        switch (imageUseType) {
            case USER -> { findImage = imageRepository.findByUser((User) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
            case SHOP -> { findImage = imageRepository.findByShop((Shop) useObject).orElseThrow(() -> new RestApiException(ErrorCode.NOT_EXIST_ENTITY_REQUEST)); }
        }

        if (!findImage.getUuidName().equals("0_default_image.png")) {
            File currentFile = new File(FOLDER_PATH + findImage.getUuidName());
            boolean result = currentFile.delete();
        }

        findImage.updateImage(file.getOriginalFilename(), uuidImageName, file.getContentType());

        return uuidImageName;
    }

    public byte[] downloadImage(String UuidName) throws IOException {
        String filePath = null;

        if (UuidName.equals("0_default_image.png")) {
            filePath = FOLDER_PATH + "0_default_image.png";
        } else {
            imageRepository.findByUuidName(UuidName).orElseThrow(() -> new RestApiException(ErrorCode.FILE_DOWNLOAD));
            filePath = FOLDER_PATH + UuidName;
        }

        return Files.readAllBytes(new File(filePath).toPath());
    }
}
