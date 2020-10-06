package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.assignment.sj.powerBot.service.ServiceTestConstants.GENERAL_OUTPUT;
import static com.assignment.sj.powerBot.service.ServiceTestConstants.MOVEMENT_FL1_SC2_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        HotelDataService.class,
        AsyncMotionTracking.class
})*/
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@EnableAsync
@TestMethodOrder(OrderAnnotation.class)
class HotelDataServiceTest {

    @Autowired
    private  HotelDataService hotelDataService;

    @Autowired
    private AsyncMotionTracking asyncMotionTracking;

    @Test
    @Order(1)
    void test_postConstruct(){
        assertEquals(GENERAL_OUTPUT, hotelDataService.getDefaultStatusValue());
        assertEquals(GENERAL_OUTPUT, hotelDataService.getCurrentStatus());
    }

    @Test
    @Order(2)
    void getDefaultStatusValue() {
        assertEquals(GENERAL_OUTPUT, hotelDataService.getCurrentStatus());
    }

    @Test
    @Order(2)
    void getCurrentStatus() {
        assertEquals(GENERAL_OUTPUT, hotelDataService.getCurrentStatus());
    }

    @Test
    @Order(3)
    void updatePower() throws InterruptedException {
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        hotelDataService.updatePower(sensorRequest);
        assertEquals(MOVEMENT_FL1_SC2_OUTPUT, hotelDataService.getCurrentStatus());
    }

    @Test
    @Order(3)
    void updatePower_MultipleMovement() throws InterruptedException {
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        hotelDataService.updatePower(sensorRequest);
        hotelDataService.updatePower(sensorRequest);
        assertEquals(MOVEMENT_FL1_SC2_OUTPUT, hotelDataService.getCurrentStatus());
    }

    @Test
    @Order(4)
    @DisplayName("No motion > minute in sub corridor")
    void test_NoMotion() throws InterruptedException {
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        hotelDataService.updatePower(sensorRequest);
        asyncMotionTracking.executeAfterMotion(sensorRequest);
        Thread.sleep(2000L);
        System.out.println("HHHHHEEEEEELLLLLOOOOOO *****************");
        Thread.sleep(15000L);
        assertEquals(GENERAL_OUTPUT, hotelDataService.getCurrentStatus());
    }
}