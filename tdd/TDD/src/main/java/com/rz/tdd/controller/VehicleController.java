package com.rz.tdd.controller;

import com.rz.tdd.exception.VehicleNotFoundException;
import com.rz.tdd.model.Vehicle;
import com.rz.tdd.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/api/v1/vehicles/{id}")
    Vehicle getVehicleDetail(@PathVariable Long id) {
        return vehicleService
                .get(id)
                .orElseThrow(VehicleNotFoundException::new);
    }

    @PostMapping("/api/v1/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    Vehicle insertVehicle(@RequestBody @Valid  Vehicle vehicle) {
        return vehicleService.save(vehicle);
    }

}
