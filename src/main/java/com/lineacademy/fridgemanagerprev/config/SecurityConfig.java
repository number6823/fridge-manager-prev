package com.lineacademy.fridgemanagerprev.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity              // 웹 관련 Spring Security 기능을 켜줘
@EnableMethodSecurity  // 앞으로 권한 제어를 메서드에 따라서 접근 제한을 할게
@RequiredArgsConstructor
// request가 도착할 때마다 이러한 절차를 진행해주세요
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean // 사용할 때마다 동일한 기능을 이용하게 됨
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Spring Boot를 통한 이 프로그램이 한 세션(1회 통신의 활동)에 대해 어떻게 관리하는가
        http.csrf(AbstractHttpConfigurer::disable)
                // 그 한 세션 안에서 사용자에 대한 식별을 위해 통신의 대상 으로 저장 하지 않겠다
                // 왜? 사용자 확인을 JWT를 통해 헤더에서 매번 까서 볼꺼니까
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 라우터에 따라서 접근 유무를 설정
        .authorizeHttpRequests(auth -> auth.requestMatchers(
                "/users/create",
                "/users/login",
                "/users/password-reset",
                "/notice/**"
                ).permitAll() // 지금 여기에 등록된 주소들은 토큰을 검사하지 않겠다 허용하겠다
                .anyRequest().authenticated() // 이 외의 모든 요청들은 인증 필요
        )
                // 이 처리 맨 앞단에 여기에 추가한 필터를 붙임
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }
}
