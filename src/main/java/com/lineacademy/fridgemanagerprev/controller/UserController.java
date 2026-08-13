package com.lineacademy.fridgemanagerprev.controller;

import com.lineacademy.fridgemanagerprev.domain.user.User;
import com.lineacademy.fridgemanagerprev.dto.user.requst.CreateUserRequest;
import com.lineacademy.fridgemanagerprev.dto.user.response.UserResponse;
import com.lineacademy.fridgemanagerprev.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
// 이 클래스가 HTTP 요청을 처리하는 Controller임을 나타냄
@RequestMapping("/users")
// "/users"로 시작하는 요청은 이 Controller가 처리함
@RequiredArgsConstructor
// final 필드를 매개변수로 받는 생성자를 자동으로 만들어줌
public class UserController {

    // UserService 객체를 주입받아 회원가입 로직을 사용함
    private final UserService userService;


    @PostMapping("/create")
    // POST /users/create 요청이 들어오면 아래 메서드 실행
    public ResponseEntity<Map<String, Object>> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        /*
         * @RequestBody
         * → 클라이언트가 보낸 JSON 데이터를 request 객체에 담음
         *
         * @Valid
         * → CreateUserRequest에 설정한 validation 검사를 실행함
         */

        try {

            // Service에 회원가입 요청을 전달
            // 회원가입이 완료되면 생성된 User 객체를 반환받음
            User user = userService.createUser(request);

            // 회원가입 성공
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "성공적으로 회원가입 되었습니다.",
                            "data", UserResponse.from(user)
                    ));

        } catch (RuntimeException e) {

            // 이메일 중복
            if ("ALREADY_EXISTS_EMAIL".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "message", "이미 가입된 이메일입니다."
                        ));
            }

            // 닉네임 중복
            if ("ALREADY_EXISTS_NICKNAME".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of(
                                "message", "이미 사용 중인 닉네임입니다."
                        ));
            }

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "서버 오류가 발생했습니다."
                    ));
        }
    }
}