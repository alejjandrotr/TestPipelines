package com.alejjandrodev.ArcaWareHouse.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseDeliveryTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_origin_id")
    private Warehouse warehouseOrigin;

    @ManyToOne
    @JoinColumn(name = "warehouse_destination_id")
    private Warehouse warehouseDestination;

    private int deliveryTimeHours;
}

