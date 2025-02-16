package com.alejjandrodev.ArcaWareHouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDeliveryTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_origin_id")
    private Warehouse warehouseOrigin;

    @ManyToOne
    @JoinColumn(name = "store_destination_id")
    private Store store;

    private int deliveryTimeHours;
}
