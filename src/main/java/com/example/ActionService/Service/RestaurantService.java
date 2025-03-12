package com.example.ActionService.Service;

import com.example.ActionService.Entity.Restaurant;
import com.example.ActionService.Entity.User;
import com.example.ActionService.Repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Map<String, Object> addRestaurant(Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "message", "User successfully created",
                "data", savedRestaurant
        );
    }

    // Update Restaurant
    public Map<String, Object> updateRestaurant(Long restaurantId, Restaurant restaurantDetails) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        existingRestaurant.setName(restaurantDetails.getName());
        existingRestaurant.setEmail(restaurantDetails.getEmail());
        existingRestaurant.setPhone(restaurantDetails.getPhone());
        existingRestaurant.setAddress(restaurantDetails.getAddress());
        existingRestaurant.setRating(restaurantDetails.getRating());
        existingRestaurant.setStatus(restaurantDetails.getStatus());

        Restaurant updatedRestaurant = restaurantRepository.save(existingRestaurant);
        return Map.of(
                "status", 200,
                "message", "Restaurant successfully updated",
                "data", updatedRestaurant
        );
    }

    // Delete Restaurant
    public Map<String, Object> deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurantRepository.delete(restaurant);
        return Map.of(
                "status", 200,
                "message", "Restaurant deleted successfully"
        );
    }
}

