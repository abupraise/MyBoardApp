package com.SQD20.SQD20.LIVEPROJECT.infrastructure.controller;

import com.SQD20.SQD20.LIVEPROJECT.payload.request.RegisterRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.request.UpdateUserRequest;
import com.SQD20.SQD20.LIVEPROJECT.payload.response.UserResponse;
import com.SQD20.SQD20.LIVEPROJECT.service.UserService;
import com.SQD20.SQD20.LIVEPROJECT.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/edit-user")
    public ResponseEntity<UserResponse> editUser(@PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest){
        UserResponse updatedUser = userService.editUser(id, updateUserRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> viewUser (@PathVariable Long id){
        UserResponse appUser = userService.viewUser(id);
        return new ResponseEntity<> (appUser, HttpStatus.OK);
    }

    @PutMapping("/profile-picture")
    public ResponseEntity<UserResponse<String>> uploadProfilePics(@RequestParam MultipartFile profilePics){
        if (profilePics.getSize() > AppConstant.MAX_FILE_SIZE){

            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new UserResponse<>("File size is too large"));

        }

        return userService.uploadProfilePicture(profilePics);


    }

}
