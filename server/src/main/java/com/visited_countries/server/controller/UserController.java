package com.visited_countries.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.visited_countries.server.Dto.ApiResponseDto;
import com.visited_countries.server.Dto.UserDto;
import com.visited_countries.server.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createNewUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto> deleteUser(@RequestParam int id) {
        Boolean isDeleted = userService.deleteUser(id);

        if(isDeleted) 
            return ResponseEntity.status(200).body(new ApiResponseDto("User Deleted Successfully", true));
        
        
        return ResponseEntity.status(500).body(new ApiResponseDto("Failed to delete user", false));
    }
}
