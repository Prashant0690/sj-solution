package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.Equipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.assignment.sj.powerBot.type.EquipmentType.AC;
import static com.assignment.sj.powerBot.type.EquipmentType.LIGHT;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EquipmentRepoTest {

    @Autowired
    private EquipmentRepo equipmentRepo;


    @Test
    @Sql("/db/test.sql")
    void testFindAll() {
        List<Equipment> equipments = equipmentRepo.findAll();
        assertEquals(12, equipments.size());
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdSuccess() {
        Optional<Equipment> equipmentAc= equipmentRepo.findById(12);
        Optional<Equipment> equipmentLight= equipmentRepo.findById(11);

        assertTrue(equipmentAc.isPresent());
        assertEquals(12 ,equipmentAc.get().getId());
        assertEquals(10 ,equipmentAc.get().getConsumePower());
        assertEquals(AC ,equipmentAc.get().getEquipmentType());
        assertEquals(ON ,equipmentAc.get().getDefaultPowerStatus());

        assertTrue(equipmentLight.isPresent());
        assertEquals(11 ,equipmentLight.get().getId());
        assertEquals(5 ,equipmentLight.get().getConsumePower());
        assertEquals(LIGHT ,equipmentLight.get().getEquipmentType());
        assertEquals(OFF ,equipmentLight.get().getDefaultPowerStatus());


    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdNotFound() {
        Optional<Equipment> equipment = equipmentRepo.findById(13);
        assertFalse(equipment.isPresent());
    }

    @Test
    @Sql("/db/test.sql")
    void testSave() {
        Equipment equipment = new Equipment();
        equipment.setConsumePower(5);
        equipment.setDefaultPowerStatus(OFF);
        equipment.setPresentPowerStatus(OFF);
        equipment.setEquipmentType(LIGHT);
        Equipment savedEquipment = equipmentRepo.save(equipment);
        assertTrue(savedEquipment.getId() > 0);
    }

    @Test
    @Sql("/db/test.sql")
    void test_FindBy_SubCorridorIdAndType_Success() {
        List<Equipment> savedEquipment = equipmentRepo.findBySubCorridorIdAndType(4, LIGHT.toString());
        assertTrue(savedEquipment.size() == 1);
    }

    @Test
    @Sql("/db/test.sql")
    void test_FindBy_SubCorridorIdAndType_NotFound() {
        List<Equipment> savedEquipment = equipmentRepo.findBySubCorridorIdAndType(10, LIGHT.toString());
        assertTrue(savedEquipment.size() == 0);
    }

}