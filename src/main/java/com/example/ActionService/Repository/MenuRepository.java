package com.example.ActionService.Repository;

import com.example.ActionService.Entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {
   // List<Menu> findByRestaurantRestaurantId(Long restaurantId);

}
