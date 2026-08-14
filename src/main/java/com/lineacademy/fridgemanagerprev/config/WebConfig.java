package com.lineacademy.fridgemanagerprev.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// cors 말고도 여러가지 Spring-Boot가 웹 환경에서 동작되는 환경 설정을 할 수 있는 클래스로 @Configuration 어노테이션을 붙여서 만듦
@Configuration
public class WebConfig  implements WebMvcConfigurer {
    // 원래부터 Spring-boot는 cors 규칙이 기본 설정되어 있음
    // 그걸 우린 Override를 통해 재작성해서 우리가 마음대로 바꿀 것임
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   // 들어오는 모든 경로에 대해
                .allowedOrigins("http://localhost:8081","http://localhost:8082","http://localhost:8083")
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowCredentials(true);
    }
}
