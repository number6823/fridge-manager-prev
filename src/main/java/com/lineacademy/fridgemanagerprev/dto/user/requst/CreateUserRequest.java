package com.lineacademy.fridgemanagerprev.dto.user.requst;

// Data Transfer Object > 데이터 전달 객체
// 매핑 클래스   // 비슷한 개념 zod의 schema역할

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {


    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
    private String password;


    @NotBlank(message = "닉네임은 필수입니다")
    @Size(min = 6, message = "닉네임을  2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private String birthdate;
}
