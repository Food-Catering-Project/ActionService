package com.example.ActionService.Repository;

import com.example.ActionService.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository  extends JpaRepository<Restaurant,Long> {
}
