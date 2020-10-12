package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.MainCorridor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static com.assignment.sj.powerBot.type.EquipmentType.AC;
import static com.assignment.sj.powerBot.type.EquipmentType.LIGHT;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PowCheckUtilService.class
})
@TestPropertySource("classpath:test-application.properties")
class PowCheckUtilServiceTest {

    @Autowired
    private PowCheckUtilService powCheckUtilService;

    Floor floor;

    @BeforeEach
    public void initEach(){
        floor = new Floor();
        floor.setId(1);
        floor.setMainCorridors(new HashSet<>());
        floor.setSubCorridors(new HashSet<>());

        MainCorridor mainCorridor = new MainCorridor();
        mainCorridor.setId(1);
        mainCorridor.setCorridorNum(1);
        mainCorridor.setEquipmentList(new HashSet<>());
        mainCorridor.getEquipmentList().add(new Equipment(1, LIGHT, ON,ON,5));
        mainCorridor.getEquipmentList().add(new Equipment(2, AC, ON,ON,10));
        floor.getMainCorridors().add(mainCorridor);

    }


    @Test
    void checkNoMotionInSubCorridor() {

        SubCorridor subCorridor1 = new SubCorridor();
        subCorridor1.setId(1);
        subCorridor1.setCorridorNum(1);
        subCorridor1.setEquipment(new HashSet<>());
        subCorridor1.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor1.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        SubCorridor subCorridor2 = new SubCorridor();
        subCorridor2.setId(2);
        subCorridor2.setCorridorNum(2);
        subCorridor2.setEquipment(new HashSet<>());
        subCorridor2.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor2.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        floor.getSubCorridors().add(subCorridor1);
        floor.getSubCorridors().add(subCorridor2);

        Assertions.assertEquals(35, powCheckUtilService.calMaxFloorPow(floor));
    }

    @Test
    void testPowerOverFlow_False() {

        SubCorridor subCorridor1 = new SubCorridor();
        subCorridor1.setId(1);
        subCorridor1.setCorridorNum(1);
        subCorridor1.setEquipment(new HashSet<>());
        subCorridor1.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor1.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        SubCorridor subCorridor2 = new SubCorridor();
        subCorridor2.setId(2);
        subCorridor2.setCorridorNum(2);
        subCorridor2.setEquipment(new HashSet<>());
        subCorridor2.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor2.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        floor.getSubCorridors().add(subCorridor1);
        floor.getSubCorridors().add(subCorridor2);

        //Pow 35
        Assertions.assertFalse(powCheckUtilService.testPowerOverFlow(floor));
    }

    @Test
    void testPowerOverFlow_True() {
        SubCorridor subCorridor1 = new SubCorridor();
        subCorridor1.setId(1);
        subCorridor1.setCorridorNum(1);
        subCorridor1.setEquipment(new HashSet<>());
        subCorridor1.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor1.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        SubCorridor subCorridor2 = new SubCorridor();
        subCorridor2.setId(2);
        subCorridor2.setCorridorNum(2);
        subCorridor2.setEquipment(new HashSet<>());
        subCorridor2.getEquipment().add(new Equipment(1, LIGHT, OFF,ON,5));
        subCorridor2.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        floor.getSubCorridors().add(subCorridor1);
        floor.getSubCorridors().add(subCorridor2);

        //Pow 40
        Assertions.assertTrue(powCheckUtilService.testPowerOverFlow(floor));
    }

    @Test
    void calMaxFloorPow() {
        SubCorridor subCorridor1 = new SubCorridor();
        subCorridor1.setId(1);
        subCorridor1.setCorridorNum(1);
        subCorridor1.setEquipment(new HashSet<>());
        subCorridor1.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor1.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        SubCorridor subCorridor2 = new SubCorridor();
        subCorridor2.setId(2);
        subCorridor2.setCorridorNum(2);
        subCorridor2.setEquipment(new HashSet<>());
        subCorridor2.getEquipment().add(new Equipment(1, LIGHT, OFF,ON,5));
        subCorridor2.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        floor.getSubCorridors().add(subCorridor1);
        floor.getSubCorridors().add(subCorridor2);
        Assertions.assertEquals(40, powCheckUtilService.totalFloorPow(floor));
    }

    @Test
    void totalFloorPow() {
        SubCorridor subCorridor1 = new SubCorridor();
        subCorridor1.setId(1);
        subCorridor1.setCorridorNum(1);
        subCorridor1.setEquipment(new HashSet<>());
        subCorridor1.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor1.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        SubCorridor subCorridor2 = new SubCorridor();
        subCorridor2.setId(2);
        subCorridor2.setCorridorNum(2);
        subCorridor2.setEquipment(new HashSet<>());
        subCorridor2.getEquipment().add(new Equipment(1, LIGHT, OFF,OFF,5));
        subCorridor2.getEquipment().add(new Equipment(2, AC, ON,ON,10));

        floor.getSubCorridors().add(subCorridor1);
        floor.getSubCorridors().add(subCorridor2);
        Assertions.assertEquals(35, powCheckUtilService.totalFloorPow(floor));
    }
}