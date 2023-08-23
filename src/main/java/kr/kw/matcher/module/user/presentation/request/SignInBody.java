package kr.kw.matcher.module.user.presentation.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignInBody {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}