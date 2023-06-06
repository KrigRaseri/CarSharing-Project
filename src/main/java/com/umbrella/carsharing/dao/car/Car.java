package com.umbrella.carsharing.dao.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private int ID;
    private String name;
    private int company_ID;
    private boolean isRented;
}
