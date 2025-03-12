package com.example.ActionService.Repository;

import com.example.ActionService.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
