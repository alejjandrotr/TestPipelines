package com.alejjandrodev.ArcaWareHouse.repositories;

import com.alejjandrodev.ArcaWareHouse.entities.DispatchOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchOrderRepository extends JpaRepository<DispatchOrder, Long> {
}
