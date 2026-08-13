package com.lineacademy.fridgemanagerprev.domain.category;

import com.lineacademy.fridgemanagerprev.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String icon = "tag";

    // Java의 ORM(데이터베이스 관계 매니징 라이브러리)들은
    // 무조건 데이터베이스에 사용하는 영향들을
    // 소문자와 언더바를 이용해서 만듦
    // 소문자와 언더바를 이용해서 만듦
    // ex. isDefault - is_default
    // 그러다보니 다른 언어를 사ㅛㅇ해서 만드는 프로젝트들에도
    // 그 관레를 따르는 편
    @Column(nullable = false, name = "is_default")
    private Boolean isDefault;

    // TODO : Product 테이블과의 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Category(String name, String icon, Boolean isDefault, User user) {
        this.name = name;
        this.icon = icon;
        this.isDefault = isDefault;
        this.user = user;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
