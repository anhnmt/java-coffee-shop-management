package com.example.DTO;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private float price;
    private boolean status;
    private String created_at;
}
