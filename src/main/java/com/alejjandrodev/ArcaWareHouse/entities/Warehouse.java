package com.alejjandrodev.ArcaWareHouse.entities;

import com.alejjandrodev.ArcaWareHouse.enums.WarehouseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Enumerated(EnumType.STRING)
    private WarehouseType warehouseType;
    private String warehouseName;
}
