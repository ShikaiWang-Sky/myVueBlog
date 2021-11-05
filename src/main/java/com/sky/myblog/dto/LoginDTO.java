package com.sky.myblog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * deal with login request
 * @author sky
 */
@Data
public class LoginDTO {
    @NotBlank(message = "username cannot be empty!")
    private String username;

    @NotBlank(message = "password canoot be empty!")
    private String password;
}
