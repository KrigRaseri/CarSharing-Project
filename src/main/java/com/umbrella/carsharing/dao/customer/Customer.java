package com.umbrella.carsharing.dao.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private int ID;
    private String name;
    private Integer rentedCarID;

    public Customer(int id, String name) {
        this.ID = id;
        this.name = name;
    }
}