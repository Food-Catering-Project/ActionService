package com.example.ActionService.Controller;

import com.example.ActionService.Entity.Menu;
import com.example.ActionService.Service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/action/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/addingMenu/{restaurantId}")
    public ResponseEntity<Map<String, Object>> createMenu(@PathVariable Long restaurantId, @RequestBody Menu menu) {
        try{
            Map<String, Object> response = menuService.createMenu(restaurantId, menu);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to add", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{menuId}")
    public ResponseEntity<Map<String, Object>> updateMenu(@PathVariable Long menuId, @RequestBody Menu menu) {
        try {
            Map<String, Object> response = menuService.updateMenu(menuId, menu);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to update menu", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<Map<String, Object>> deleteMenu(@PathVariable Long menuId) {
        try {
            Map<String, Object> response = menuService.deleteMenu(menuId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Unable to delete", "error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
