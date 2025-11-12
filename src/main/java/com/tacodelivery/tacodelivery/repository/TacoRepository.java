package com.tacodelivery.tacodelivery.repository;

import com.tacodelivery.tacodelivery.model.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TacoRepository extends JpaRepository<Taco, Long> {
    Optional<Taco> findByNombreAndTamaño(String nombre, String tamaño);
}