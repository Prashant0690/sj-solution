package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.repository.FloorRepo;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ManagePowerServiceTest {

    @Autowired
    private ManagePowerService managePowerService;

    @Autowired
    private FloorRepo floorRepo;


    @Test
    @Sql("/db/test.sql")
    void getFloors() {
        assertEquals(2, managePowerService.getFloors().size());
    }



    @Test
    @Sql("/db/test.sql")
    void updatePower() {
        assertDoesNotThrow(() -> managePowerService.updatePower(new SensorRequest(1,1)));
        Optional<Floor> optFloor = floorRepo.findById(1);
        Floor floor = optFloor.get();
        System.out.println("=================");
        String outputStr = floor.toString();
        System.out.println(outputStr);
        assertEquals(2, floor.getSubCorridors().size());
        assertEquals(2, floor.getSubCorridors().size());
        assertTrue(outputStr.contains("Floor 1"));
        assertTrue(outputStr.contains("Floor 1 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(outputStr.contains("Floor 1 : Sub corridor 1 LIGHT : ON AC : ON"));
        assertTrue(outputStr.contains("Floor 1 : Sub corridor 2 LIGHT : OFF AC : OFF"));
    }

    @Test
    @Sql("/db/test.sql")
    void noMotionUpdatePower() throws InterruptedException {
        managePowerService.updatePower(new SensorRequest(1,1));
        Thread.sleep(6000L);
        assertDoesNotThrow(() -> managePowerService.noMotionUpdatePower(new SensorRequest(1,1)));
        Optional<Floor> optFloor = floorRepo.findById(1);
        Floor floor = optFloor.get();
        String outputStr = floor.toString();
        System.out.println("=================");
        System.out.println(outputStr);
        assertTrue(outputStr.contains("Floor 1"));
        assertTrue(outputStr.contains("Floor 1 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(outputStr.contains("Floor 1 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(outputStr.contains("Floor 1 : Sub corridor 2 LIGHT : OFF AC : ON"));
    }
}