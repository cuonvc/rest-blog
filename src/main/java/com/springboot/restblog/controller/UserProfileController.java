package com.springboot.restblog.controller;

import com.springboot.restblog.model.payload.CustomUser;
import com.springboot.restblog.model.payload.UserProfileDTO;
import com.springboot.restblog.service.IUserProfileService;
import com.springboot.restblog.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class UserProfileController {

    @Autowired
    private IUserProfileService userProfileService;

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDTO> updateProfile(@RequestBody UserProfileDTO profileDTO) {
        UserProfileDTO profileResponse = userProfileService.setInfoUser(profileDTO);

        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable(name = "userId") Integer userId) {
        UserProfileDTO profileResponse = userProfileService.getProfileByUser(userId);

        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    @PostMapping("/profile/avatar")
    public ResponseEntity<String> updateAvatar(
            @RequestParam(name = "image") MultipartFile multipartFile) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Integer userId = customUser.getUserId();

        if (!multipartFile.isEmpty()) {
            String uploadDir = "user-avatars/" + userId;

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, multipartFile);

            return new ResponseEntity<>("Update avatar successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bằng một cách thần kì nào đó mà nó đã lỗi (có lẽ chưa chọn file)!",
                    HttpStatus.BAD_REQUEST);
        }
    }
}