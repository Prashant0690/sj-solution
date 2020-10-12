package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static com.assignment.sj.powerBot.type.EquipmentType.AC;
import static com.assignment.sj.powerBot.type.EquipmentType.LIGHT;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ManagePowerRepoTest {

    @Autowired
    private ManagePowerRepo managePowerRepo;


    @Test
    @Sql("/db/test.sql")
    void findAllFloors_Success() {
        assertEquals(2, managePowerRepo.findAllFloors().size());
    }

    @Test
    void findAllFloors_ExpectedException() {
        assertThrows(RuntimeException.class, () -> managePowerRepo.findAllFloors());
    }

    @Test
    @Sql("/db/test.sql")
    void getEquipment_Success() {
        assertEquals(1, managePowerRepo.getEquipment(1, AC).size());
        assertEquals(1, managePowerRepo.getEquipment(1, LIGHT).size());
    }

    @Test
    @Sql("/db/test.sql")
    void getEquipment_ExpectedException() {
        assertThrows(RuntimeException.class, () -> managePowerRepo.getEquipment(100, LIGHT));
    }

    @Test
    @Sql("/db/test.sql")
    void getSubCorridor_Success() {
        SubCorridor subCorridor = managePowerRepo.getSubCorridor(1, 1);
        assertNotNull(subCorridor);
        assertEquals(1, subCorridor.getId());
        assertEquals(1, subCorridor.getCorridorNum());

    }

    @Test
    @Sql("/db/test.sql")
    void getSubCorridor_ExpectedException() {
        assertThrows(RuntimeException.class, () -> managePowerRepo.getSubCorridor(100, 1));
        assertThrows(RuntimeException.class, () -> managePowerRepo.getSubCorridor(1, 100));
    }

    @Test
    @Sql("/db/test.sql")
    void updateSubCorridorLightPower_Success() {
        assertDoesNotThrow(() -> managePowerRepo.updateSubCorridorLightPower(1, 1));
    }

    @Test
    @Sql("/db/test.sql")
    void findFloorById_Success() {
        Floor floor = managePowerRepo.findFloorById(1);
        assertEquals(1, floor.getId());
    }

    @Test
    @Sql("/db/test.sql")
    void findFloorById_ExpectedException() {
        assertThrows(RuntimeException.class, () -> managePowerRepo.findFloorById(100));
    }

    @Test
    @Sql("/db/test.sql")
    void saveEquipment() {
        Equipment equipment = new Equipment(10, AC, ON, OFF, 10);
        assertDoesNotThrow(() -> managePowerRepo.saveEquipment(equipment));
    }
}