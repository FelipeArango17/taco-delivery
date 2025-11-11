package com.tacodelivery.tacodelivery.repository;

import com.tacodelivery.tacodelivery.model.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}