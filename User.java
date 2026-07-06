package com.example.canteen.controller;

import com.example.canteen.model.MenuItem;
import com.example.canteen.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin(origins = "http://localhost:8081")
public class ManagerController {

    private final MenuService menuService;

    public ManagerController(MenuService menuService) {
        this.menuService = menuService;
    }

    // ✅ Get all menu items for the manager's canteen
    @GetMapping("/menu/{canteenId}")
    public List<MenuItem> getCanteenMenu(@PathVariable String canteenId) {
        return menuService.getMenuByCanteen(canteenId);
    }

    // ✅ Add a new menu item
    @PostMapping("/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem addMenuItem(@Valid @RequestBody MenuItem item) {
        return menuService.createMenuItem(item);
    }

    // ✅ Update existing menu item
    @PutMapping("/menu/{id}")
    public MenuItem updateMenuItem(@PathVariable String id, @Valid @RequestBody MenuItem updatedItem) {
        return menuService.updateMenuItem(id, updatedItem);
    }

    // ✅ Delete a menu item by ID
    @DeleteMapping("/menu/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable String id) {
        menuService.deleteMenuItem(id);
    }
}
