package com.alejjandrodev.ArcaWareHouse.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispatchOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dispatch_order_id")
    @JsonBackReference
    private DispatchOrder dispatchOrder;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;

    private int quantity;
}
