package com.alejjandrodev.ArcaWareHouse.repositories;

import com.alejjandrodev.ArcaWareHouse.entities.WarehouseInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseInventoryRepository extends JpaRepository<WarehouseInventory, Long> {

    Optional<WarehouseInventory> findByProduct_ProductCodeAndWarehouse_Id(String productCode, Long warehouseId);
}
