package com.example.canteen.controller;

import com.example.canteen.model.MenuItem;
import com.example.canteen.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8081")
public class MenuItemController {

    private final MenuService service;

    public MenuItemController(MenuService service) {
        this.service = service;
    }

    // ✅ Get all menu items for a specific canteen
    @GetMapping("/canteens/{canteenId}/menu-items")
    public List<MenuItem> getMenuByCanteen(@PathVariable String canteenId) {
        return service.getMenuByCanteen(canteenId);
    }

    // ✅ Get single menu item by ID
    @GetMapping("/menu-items/{id}")
    public MenuItem getMenuItem(@PathVariable String id) {
        return service.getMenuById(id);
    }

    // ✅ Create new menu item
    @PostMapping("/menu-items")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem createMenuItem(@Valid @RequestBody MenuItem item) {
        return service.createMenuItem(item);
    }

    // ✅ Update menu item
    @PutMapping("/menu-items/{id}")
    public MenuItem updateMenuItem(@PathVariable String id, @Valid @RequestBody MenuItem updatedItem) {
        return service.updateMenuItem(id, updatedItem);
    }

    // ✅ Delete menu item
    @DeleteMapping("/menu-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable String id) {
        service.deleteMenuItem(id);
    }
}
