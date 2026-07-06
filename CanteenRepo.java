package com.example.canteen.controller;

import com.example.canteen.model.MenuItem;
import com.example.canteen.model.Rating;
import com.example.canteen.repository.MenuItemRepo;
import com.example.canteen.repository.RatingRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:8081")
public class MenuController {

    private final MenuItemRepo menuItemRepo;
    private final RatingRepo ratingRepo;

    public MenuController(MenuItemRepo menuItemRepo, RatingRepo ratingRepo) {
        this.menuItemRepo = menuItemRepo;
        this.ratingRepo = ratingRepo;
    }

    // ✅ Add new dish to a canteen
    @PostMapping("/add")
    public ResponseEntity<?> addMenuItem(@RequestBody MenuItem item) {
        try {
            if (item.getCanteenId() == null || item.getCanteenId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("canteenId is required");
            }

            if (item.getName() == null || item.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("name is required");
            }

            item.setAvgRating(0);
            MenuItem saved = menuItemRepo.save(item);
            System.out.println("✅ Added new menu item: " + saved.getName() + " (Canteen: " + saved.getCanteenId() + ")");
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding dish: " + e.getMessage());
        }
    }

    // ✅ Get all dishes across all canteens
    @GetMapping("/all")
    public ResponseEntity<?> getAllMenuItems() {
        try {
            List<MenuItem> allItems = menuItemRepo.findAll();
            return ResponseEntity.ok(allItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching menu items: " + e.getMessage());
        }
    }

    // ✅ Get menu items for a specific canteen
    @GetMapping("/canteen/{canteenId}")
    public ResponseEntity<?> getMenuByCanteen(@PathVariable String canteenId) {
        try {
            List<MenuItem> items = menuItemRepo.findByCanteenId(canteenId);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching canteen menu: " + e.getMessage());
        }
    }

    // ✅ Delete a dish
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable String id) {
        try {
            if (!menuItemRepo.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Menu item not found");
            }

            // Delete menu item & its ratings
            menuItemRepo.deleteById(id);
            ratingRepo.deleteAll(ratingRepo.findByMenuItemId(id));

            System.out.println("🗑️ Deleted menu item with ID: " + id);
            return ResponseEntity.ok("Menu item deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting dish: " + e.getMessage());
        }
    }

    // ✅ Rate a dish (with comment & username)
    @PostMapping("/rate/{menuId}")
    public ResponseEntity<?> rateDish(@PathVariable String menuId, @RequestBody Rating rating) {
        try {
            if (rating.getStars() < 1 || rating.getStars() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Stars must be between 1 and 5");
            }

            if (rating.getComment() == null) rating.setComment("");
            if (rating.getUsername() == null) rating.setUsername("Anonymous");

            rating.setMenuItemId(menuId);
            ratingRepo.save(rating);

            // Recalculate average rating
            List<Rating> ratings = ratingRepo.findByMenuItemId(menuId);
            double avg = ratings.stream().mapToInt(Rating::getStars).average().orElse(0);

            MenuItem menuItem = menuItemRepo.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            menuItem.setAvgRating(avg);
            menuItemRepo.save(menuItem);

            System.out.println("⭐ Updated rating for dish ID: " + menuId + " to " + avg);
            return ResponseEntity.ok(menuItem);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error rating dish: " + e.getMessage());
        }
    }

    // ✅ Get all ratings & comments for a dish (optional endpoint)
    @GetMapping("/ratings/{menuId}")
    public ResponseEntity<?> getRatingsForDish(@PathVariable String menuId) {
        try {
            List<Rating> ratings = ratingRepo.findByMenuItemId(menuId);
            return ResponseEntity.ok(ratings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching ratings: " + e.getMessage());
        }
    }
}
