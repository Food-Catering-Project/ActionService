package com.example.ActionService.Controller;

import com.example.ActionService.Entity.Restaurant;
import com.example.ActionService.Service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<Map<String,Object>> addRestaurant(@RequestBody Restaurant restaurant){
        try{
            Map<String,Object> res = restaurantService.addRestaurant(restaurant);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to add", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<Map<String, Object>> updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        try {
            Map<String, Object> response = restaurantService.updateRestaurant(restaurantId, restaurant);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to update", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{restaurantId}")
    public ResponseEntity<Map<String, Object>> deleteRestaurant(@PathVariable Long restaurantId) {
        try {
            Map<String, Object> response = restaurantService.deleteRestaurant(restaurantId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to delete", "error", e.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }
}
