package com.alejjandrodev.ArcaWareHouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreInventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private int quantity;
}
