package ecutb.fishingtrip.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static ecutb.fishingtrip.constants.message.ValidationMessages.*;
import static ecutb.fishingtrip.constants.regex.RegexPattern.EMAIL_REGEX_PATTERN;
import static ecutb.fishingtrip.constants.regex.RegexPattern.PASSWORD_REGEX_PATTERN;

public class CreateAppUser {

    @NotBlank(message = FIELD_FORMAT_MESSAGE)
    @Size(min = 2, max = 60, message = USERNAME_FORMAT_MESSAGE)
    private String userName;
    @NotBlank(message = FIELD_FORMAT_MESSAGE)
    @Size(min = 2, max = 64, message = NAME_FORMAT_MESSAGE)
    private String firstName;
    @NotBlank(message = FIELD_FORMAT_MESSAGE)
    @Size(min = 2, max = 64, message = NAME_FORMAT_MESSAGE)
    private String lastName;
    @NotBlank(message = FIELD_FORMAT_MESSAGE)
    @Pattern(regexp = EMAIL_REGEX_PATTERN,
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = EMAIL_FORMAT_MESSAGE)
    private String email;
    @NotBlank(message = FIELD_FORMAT_MESSAGE)
    @Pattern(regexp = PASSWORD_REGEX_PATTERN,
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = PASSWORD_FORMAT_MESSAGE)
    private String password;
    @NotBlank(message = PASSWORD_CONFIRMATION_MESSAGE)
    private String confirmPassword;
    private boolean admin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
