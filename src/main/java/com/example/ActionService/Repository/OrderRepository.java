package com.example.ActionService.Repository;

import com.example.ActionService.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {
}
