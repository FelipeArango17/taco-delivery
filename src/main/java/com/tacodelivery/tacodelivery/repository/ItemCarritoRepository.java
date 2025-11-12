package com.tacodelivery.tacodelivery.repository;

import com.tacodelivery.tacodelivery.model.ItemCarrito;
import com.tacodelivery.tacodelivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    List<ItemCarrito> findByUser(User user);
    void deleteByUser(User user);
}
