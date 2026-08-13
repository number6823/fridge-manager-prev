package com.lineacademy.fridgemanagerprev.repository;

import com.lineacademy.fridgemanagerprev.domain.firdge.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {

}
