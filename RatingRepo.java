package com.example.canteen.controller;

import com.example.canteen.model.Rating;
import com.example.canteen.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "http://localhost:8081")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    // ✅ 1. Get all ratings (for admin/test/debug)
    @GetMapping
    public List<Rating> getAllRatings() {
        return service.getAllRatings();
    }

    // ✅ 2. Get ratings for a specific menu item + average
    @GetMapping("/menu-item/{menuItemId}")
    public Map<String, Object> getRatingsByMenuItem(@PathVariable String menuItemId) {
        return service.getRatingsByMenuItem(menuItemId);
    }

    // ✅ 3. Add a new rating
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rating addRating(@Valid @RequestBody Rating rating) {
        return service.addRating(rating);
    }

    // ✅ 4. Get a single rating by ID
    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable String id) {
        return service.getRatingById(id);
    }

    // ✅ 5. Delete rating (optional - admin feature)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable String id) {
        service.deleteRating(id);
    }
}
