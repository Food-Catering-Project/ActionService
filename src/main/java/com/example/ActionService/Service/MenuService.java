package com.example.ActionService.Service;

import com.example.ActionService.Entity.Menu;
import com.example.ActionService.Entity.Restaurant;
import com.example.ActionService.Repository.MenuRepository;
import com.example.ActionService.Repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Map<String, Object> createMenu(Long restaurantId, Menu menu) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        menu.setRestaurant(restaurant);
        Menu savedMenu = menuRepository.save(menu);
        return Map.of(
                "status", 201,
                "message", "Menu item successfully created",
                "data", savedMenu
        );

    }

    public Map<String, Object> updateMenu(Long menuId, Menu menuDetails) {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existingMenu.setName(menuDetails.getName());
        existingMenu.setDescription(menuDetails.getDescription());
        existingMenu.setPrice(menuDetails.getPrice());
        existingMenu.setCategory(menuDetails.getCategory());
        existingMenu.setImageUrl(menuDetails.getImageUrl());
        existingMenu.setAvailable(menuDetails.getAvailable());

        Menu updatedMenu = menuRepository.save(existingMenu);

        return Map.of(
                "status", 200,
                "message", "Menu item successfully updated",
                "data", updatedMenu
        );
    }

    // Delete Menu Item
    public Map<String, Object> deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menuRepository.delete(menu);

        return Map.of(
                "status", 200,
                "message", "Menu item deleted successfully"
        );
    }
}

