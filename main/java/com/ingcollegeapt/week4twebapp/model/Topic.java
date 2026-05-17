package com.ingcollegeapt.week4twebapp.model;

import java.time.LocalDateTime;

public class Topic {

    private int id;
    private String name;
    private int userId; //userId added
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // TODO: Constructor
    public Topic() {
    }
    
    public Topic(int id, String name, int userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.userId = userId; //added userId
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Topic(int userId, String name) { //added userid
        this.userId = userId;
        this.name = name;
    }

    // TODO: Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    //updated getter and setter for userid

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    
}
