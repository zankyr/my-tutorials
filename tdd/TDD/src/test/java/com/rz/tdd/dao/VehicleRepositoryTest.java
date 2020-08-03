package com.rz.tdd.dao;

import com.rz.tdd.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void findById_shouldGetVehicle() {
        Long vehicleId = 1L;
        Vehicle vehicleToSave =
                Vehicle.builder()
                        .model("488")
                        .brand("Ferrari")
                        .year(2020)
                        .build();

        Vehicle expectedVehicle = vehicleRepository.save(vehicleToSave);

        assertEquals(Optional.of(expectedVehicle), vehicleRepository.findById(vehicleId));
    }

    @Test
    void findById_shouldReturnEmptyIfVehicleNotFound() {
        assertEquals(Optional.empty(), vehicleRepository.findById(999L));
    }

    @Test
    void save_shouldReturnVehicleWithId() {
        Vehicle vehicleToSave =
                Vehicle.builder()
                        .model("488")
                        .brand("Ferrari")
                        .year(2020)
                        .build();

        Vehicle actual = vehicleRepository.save(vehicleToSave);

        assertEquals(vehicleToSave, actual);
    }
}
