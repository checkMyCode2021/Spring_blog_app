package com.example.spring_blog_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email address is not valid")
    private String email;
    @Size(min = 8, max = 128, message = "Password must have number of characters between {min} and {max}")
 //   @Pattern(regexp = "([A-Z]{1,}.*\\d{1,}|\\d{1,}.*[A-Z]{1,})", message = "Password must have at    least 1 capital letter and 1 digit")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password must have minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character")
    private String password;
}
