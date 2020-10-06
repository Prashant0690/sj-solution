package com.assignment.sj.powerBot.data;

import org.junit.jupiter.api.*;

import static com.assignment.sj.powerBot.data.Equipment.Status;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EquipmentTest {

    private static Equipment acs;
    private Equipment light;

    @BeforeEach
    void init(){
        //Assemble
        light = new Light(Status.OFF);
        acs = new AirConditioner();
    }

    @Test
    @Order(1)
    void test_LightObject(){
        assertAll("Light",
                () -> assertSame( Status.OFF, light.getStatus() ),
                () -> assertEquals(5, light.getConsumePower()),
                () ->assertNotNull(light.getStatusUpdatedOn())
        );
    }

    @Test
    @Order(1)
    void test_ACsObject(){
        assertAll("AC",
                () -> assertSame( Status.ON, acs.getStatus() ),
                () -> assertEquals(10, acs.getConsumePower()),
                () -> assertNotNull(acs.getStatusUpdatedOn())
        );
    }

    @Test
    @Order(2)
    void changePowerStatus() {
        //Act
        light.changePowerStatus();
        acs.changePowerStatus();
        //Assert
        assertAll("Power change status",
                () -> assertSame(Status.OFF, acs.getStatus()),
                () -> assertSame(Status.ON, light.getStatus()));
    }

    @Test
    void statusChangeDurationInSec() throws InterruptedException {
        //Act
        light.changePowerStatus();
        acs.changePowerStatus();
        Thread.sleep(5_000L);
        //Assert
        assertAll("Time calculation",
                () -> assertTrue(acs.statusChangeDurationInSec() >= 5, "ACs"),
                () -> assertTrue(light.statusChangeDurationInSec() >= 5, "Light")
        );
    }
}