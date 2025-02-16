package com.alejjandrodev.ArcaWareHouse.services;

import com.alejjandrodev.ArcaWareHouse.dtos.CreateWarehouseDto;
import com.alejjandrodev.ArcaWareHouse.dtos.WarehouseUpdateDTO;
import com.alejjandrodev.ArcaWareHouse.entities.City;
import com.alejjandrodev.ArcaWareHouse.entities.Warehouse;
import com.alejjandrodev.ArcaWareHouse.errors.CityNoFoundException;
import com.alejjandrodev.ArcaWareHouse.errors.WarehouseNotFoundException;
import com.alejjandrodev.ArcaWareHouse.repositories.*;
import com.alejjandrodev.ArcaWareHouse.utils.ILoggerWriter;
import com.alejjandrodev.ArcaWareHouse.utils.loggerWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ILoggerWriter logger; // Inject the logger


    public List<Warehouse> findAll() {
        logger.info("Finding all warehouses", null);
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> findById(Long id) {
        logger.info("Finding warehouse by ID", id);
        return warehouseRepository.findById(id);
    }

    public Warehouse save(CreateWarehouseDto warehouseDTO) {
        logger.info("Saving warehouse", warehouseDTO);
        Optional<City> city = cityRepository.findById(warehouseDTO.getCityId());
        if (city.isEmpty()){
            CityNoFoundException exception = new CityNoFoundException(warehouseDTO.getCityId());
            logger.error("City not found while saving warehouse", exception);
            throw exception;
        }

        //No es responsabilidad de create hacer el Mapper del dto
        Warehouse warehouse = new Warehouse();
        warehouse.setCity(city.get()); // Asignar ciudad por ID
        warehouse.setWarehouseType(warehouseDTO.getWarehouseType());
        warehouse.setWarehouseName(warehouseDTO.getWarehouseName());
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        logger.info("Warehouse saved successfully", savedWarehouse);
        return savedWarehouse;
    }

    public Warehouse update(Long id, WarehouseUpdateDTO updateDTO) {
        logger.info("Updating warehouse with ID", id);
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> {
            WarehouseNotFoundException exception = new WarehouseNotFoundException(id);
            logger.error("Warehouse not found while updating", exception.toErrorResponseDto());
            return exception;
        });

        if (updateDTO.getCityId() != null) {
            Optional<City> city = cityRepository.findById(updateDTO.getCityId());
            if (city.isEmpty()){
                CityNoFoundException exception = new CityNoFoundException(updateDTO.getCityId());
                logger.error("City not found while updating warehouse", exception.toErrorResponseDto());
                throw exception;
            }
            warehouse.setCity(city.get());
        }
        if (updateDTO.getWarehouseType() != null) {
            warehouse.setWarehouseType(updateDTO.getWarehouseType());
        }
        if (updateDTO.getWarehouseName() != null) {
            warehouse.setWarehouseName(updateDTO.getWarehouseName());
        }

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);
        logger.info("Warehouse updated successfully", updatedWarehouse);
        return updatedWarehouse;
    }

    public void delete(Long id) {
        logger.info("Deleting warehouse with ID", id);
        try {
            warehouseRepository.deleteById(id);
            logger.info("Warehouse deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting warehouse", e.getMessage());
            throw new WarehouseNotFoundException(id);
        }
    }
}
