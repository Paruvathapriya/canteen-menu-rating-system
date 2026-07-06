package com.example.canteen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "canteens")
public class Canteen {
    @Id
    private String id;
    private String name;
    private String location;

    // ✅ Default constructor
    public Canteen() {}

    // ✅ All-args constructor (matching your usage)
    public Canteen(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    // ✅ Getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
