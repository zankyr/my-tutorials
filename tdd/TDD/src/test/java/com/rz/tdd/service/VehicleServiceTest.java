package com.rz.tdd.service;

import com.rz.tdd.dao.VehicleRepository;
import com.rz.tdd.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.rz.tdd.VehicleUtils.aValidVehicle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void get_shouldReturnVehicle() {
        Long vehicleId = 1L;
        Optional<Vehicle> returnedVehicle = Optional.of(
                Vehicle.builder()
                        .id(vehicleId)
                        .brand("Ferrari")
                        .model("488")
                        .year(2020)
                        .build()
        );

        when(vehicleRepository.findById(vehicleId))
                .thenReturn(returnedVehicle);

        assertEquals(returnedVehicle, vehicleService.get(vehicleId));
    }

    @Test
    void get_shouldReturnEmptyIfNotFound() {
        Long vehicleId = 1L;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertTrue(vehicleService.get(vehicleId).isEmpty());
    }

    @Test
    void create_shouldSaveTheVehicle() {
        var vehicleToSave = aValidVehicle();
        var expectedVehicle = aValidVehicle().toBuilder().id(12L).build();

        when(vehicleRepository.save(vehicleToSave))
                .thenReturn(expectedVehicle);

        assertEquals(
                expectedVehicle,
                vehicleService.save(vehicleToSave)
        );


    }
}
