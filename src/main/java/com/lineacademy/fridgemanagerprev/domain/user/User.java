package com.lineacademy.fridgemanagerprev.domain.user;

import com.lineacademy.fridgemanagerprev.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerprev.domain.enums.Roletype;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity // DB 테이블과 매핑되는 JPA 엔티티 클래스임을 선언
@Table(name = "user") // 매핑될 DB 테이블 이름을 "user"로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// NoArgsConstructor()라고만 쓰면 기본 생성자를 만들어주는 어노테이션
// 기본 생성자에 접근제한자를 protected로 만들어달라
// 엔티티는 기본 생성자를 protected로 해줘야 함
public class User extends BaseTimeEntity {
    @Id // 테이블의 Primary Key를 지정
    // 기록되는 값이 자동으로 생성되는데 AUTO_INCREMENT 되도록 생성
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // DB에 int 타입은 Java 에서는 Long

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column()
    private LocalDateTime birthdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roletype role = Roletype.USER;

    // TODO: 다른 테이블과의 관계를 기록해줘야 함

}
