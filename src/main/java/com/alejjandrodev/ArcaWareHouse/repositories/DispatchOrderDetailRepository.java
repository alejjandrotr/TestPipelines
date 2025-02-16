package com.alejjandrodev.ArcaWareHouse.repositories;

import com.alejjandrodev.ArcaWareHouse.entities.DispatchOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchOrderDetailRepository extends JpaRepository<DispatchOrderDetail, Long> {
}
