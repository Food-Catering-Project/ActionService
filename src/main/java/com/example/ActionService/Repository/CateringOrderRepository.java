package com.example.ActionService.Repository;


import com.example.ActionService.Entity.CateringOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateringOrderRepository extends JpaRepository< CateringOrder, Long> {
}

