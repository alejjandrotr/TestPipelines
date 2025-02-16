package com.alejjandrodev.ArcaWareHouse.repositories;

import com.alejjandrodev.ArcaWareHouse.entities.StoreDeliveryTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreDeliveryTimeRepository extends JpaRepository<StoreDeliveryTime, Long> {
    StoreDeliveryTime findByWarehouseOriginIdAndStoreId(Long id, Long id1);
}
