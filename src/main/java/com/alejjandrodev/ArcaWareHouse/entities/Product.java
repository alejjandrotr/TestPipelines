package com.alejjandrodev.ArcaWareHouse.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    private String productCode; // Using code as primary key
    private String productName;
}
