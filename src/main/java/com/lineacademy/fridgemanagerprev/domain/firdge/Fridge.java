package com.lineacademy.fridgemanagerprev.domain.firdge;

import com.lineacademy.fridgemanagerprev.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerprev.domain.product.Product;
import com.lineacademy.fridgemanagerprev.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fridge")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fridge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();


    @Builder
    public Fridge(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
