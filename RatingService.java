package com.example.canteen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menu_items")
public class MenuItem {
    @Id
    private String id;
    private String canteenId;
    private String name;
    private double price;
    private double avgRating;
   


    public MenuItem() {} // ✅ REQUIRED for JSON deserialization

    public MenuItem(String id, String canteenId, String name, double price, double avgRating) {
        this.id = id;
        this.canteenId = canteenId;
        this.name = name;
        this.price = price;
        this.avgRating = avgRating;
    }

    // ✅ getters & setters
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCanteenId() { return canteenId; }
    public void setCanteenId(String canteenId) { this.canteenId = canteenId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }
}
