package kr.kw.matcher.module.user.presentation.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignUpBody {
    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String schoolName;

    @NotNull
    private Long yearOfAdmission;
}

