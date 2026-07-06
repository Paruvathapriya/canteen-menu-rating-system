package com.example.canteen.controller;

import com.example.canteen.model.MenuItem;
import com.example.canteen.model.Rating;
import com.example.canteen.service.CanteenService;
import com.example.canteen.service.MenuService;
import com.example.canteen.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:8081")
public class CustomerController {

    private final CanteenService canteenService;
    private final MenuService menuService;
    private final RatingService ratingService;

    public CustomerController(CanteenService canteenService,
                              MenuService menuService,
                              RatingService ratingService) {
        this.canteenService = canteenService;
        this.menuService = menuService;
        this.ratingService = ratingService;
    }

    // ✅ Get all canteens (for selection)
    @GetMapping("/canteens")
    public List<?> getAllCanteens() {
        return canteenService.getAll();
    }

    // ✅ Get menu items for a chosen canteen
    @GetMapping("/menu/{canteenId}")
    public List<MenuItem> getMenuForCanteen(@PathVariable String canteenId) {
        return menuService.getMenuByCanteen(canteenId);
    }

    // ✅ Get all ratings for a menu item (and average)
    @GetMapping("/ratings/{menuItemId}")
    public Map<String, Object> getRatingsForMenuItem(@PathVariable String menuItemId) {
        return ratingService.getRatingsByMenuItem(menuItemId);
    }

    // ✅ Submit a rating for a dish
    @PostMapping("/rate")
    @ResponseStatus(HttpStatus.CREATED)
    public Rating addRating(@Valid @RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }
}
