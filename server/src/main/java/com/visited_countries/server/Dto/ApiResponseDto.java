package com.visited_countries.server.Dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private String message;
    private boolean success;

    public ApiResponseDto(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
