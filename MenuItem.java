package com.example.canteen.controller;

import com.example.canteen.model.Canteen;
import com.example.canteen.repository.CanteenRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteens")
@CrossOrigin(origins = "http://localhost:8081")
public class CanteenController {

    private final CanteenRepo repo;

    public CanteenController(CanteenRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Canteen> getAllCanteens() {
        return repo.findAll();
    }

    @PostMapping("/add")
    public Canteen addCanteen(@RequestBody Canteen c) {
        return repo.save(c);
    }
}
