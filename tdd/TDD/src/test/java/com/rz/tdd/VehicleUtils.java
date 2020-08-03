package com.rz.tdd;

import com.rz.tdd.model.Vehicle;

public class VehicleUtils {
    public static Vehicle aValidVehicle() {
        return Vehicle.builder()
                .brand("Ferrari")
                .model("488 GTB")
                .year(2019)
                .build();
    }
}
