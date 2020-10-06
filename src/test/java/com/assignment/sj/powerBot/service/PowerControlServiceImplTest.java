package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.assignment.sj.powerBot.service.ServiceTestConstants.GENERAL_OUTPUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        PowerControlServiceImpl.class,
        HotelDataService.class,
        AsyncMotionTracking.class
})
@TestPropertySource("classpath:test-application.properties")
class PowerControlServiceImplTest {

    @Autowired
    private PowerControlService controlService;

    @MockBean
    private HotelDataService hotelDataService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }


    @Test
    void test_printDefaultStatus() {
        when(hotelDataService.getDefaultStatusValue()).thenReturn(GENERAL_OUTPUT);
        String response = controlService.printDefaultStatus();
        assertEquals("Success", response);
        assertTrue(outContent.toString().contains(GENERAL_OUTPUT));
        verify(hotelDataService, times(1)).getDefaultStatusValue();
    }

    @Test
    void test_printCurrentStatus(){
        when(hotelDataService.getCurrentStatus()).thenReturn(GENERAL_OUTPUT);
        String response = controlService.printCurrentStatus();
        assertEquals("Success", response);
        assertTrue(outContent.toString().contains(GENERAL_OUTPUT));
        verify(hotelDataService, times(1)).getCurrentStatus();
    }

    @Test
    void sensorInputs(){
        SensorRequest inputs = new SensorRequest(1,2);
        System.out.println(inputs.getFloorNo());
        System.out.println(inputs.getSubCorridorNo());
    }

}