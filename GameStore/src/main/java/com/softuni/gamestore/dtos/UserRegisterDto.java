package com.softuni.gamestore.dtos;

import lombok.*;

import java.util.regex.Pattern;

import static com.softuni.gamestore.constants.Validations.*;

@Getter
@Setter
public class UserRegisterDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDto(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
        validate();
    }

    private void validate() {
        if (!Pattern.matches(EMAIL_PATTERN, this.email)) {
            throw new IllegalArgumentException(EMAIL_NOT_VALID_MESSAGE);
        }

        if (!Pattern.matches(PASSWORD_PATTERN, this.password)) {
            throw new IllegalArgumentException(USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE);
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(PASSWORDS_MISS_MATCH_MESSAGE);
        }
    }

    public String successfulRegisterFormat() {
        return fullName + " was registered";
    }
}
