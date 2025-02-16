package com.alejjandrodev.ArcaWareHouse.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispatchOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "dispatchOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DispatchOrderDetail> details;
}
