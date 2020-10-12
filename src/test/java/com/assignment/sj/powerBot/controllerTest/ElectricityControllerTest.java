package com.assignment.sj.powerBot.controllerTest;

import com.assignment.sj.powerBot.controller.ElectricityController;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.ManagePowerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ElectricityController.class)
public class ElectricityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagePowerService managePowerService;

    @MockBean
    private AsyncMotionTracking asyncMotionTracking;


    @Test
    @DisplayName("Get v1/power-controller/status")
    public void test_GetCurrentElectricityStatus() throws Exception {
        //Assemble
        Floor floor = new Floor();
        floor.setId(1);
        doReturn(Arrays.asList(floor)).when(managePowerService).getFloors();
        //Act Assert
        this.mockMvc.perform(get("/v1/power-controller/status")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",  containsString("SUCCESS")))
                .andExpect(jsonPath("$.floors[0].id", Matchers.is(1)));

        verify(managePowerService, times(1)).getFloors();
    }

    @Test
    public void test_PostMotionSensorInput() throws Exception {
        //Assemble
        SensorRequest sensorRequest = new SensorRequest(1, 1);
        Floor floor = new Floor();
        floor.setId(1);
        doNothing().when(asyncMotionTracking).executeAfterMotion(eq(sensorRequest));
        doNothing().when(managePowerService).noMotionUpdatePower(eq(sensorRequest));
        doReturn(Arrays.asList(floor)).when(managePowerService).getFloors();
        //when(controlService.sensorInputs( eq(sensorRequest) )).thenReturn("Success");
        //Act Assert
        this.mockMvc.perform(post("/v1/power-controller/sensor")
                .content(asJsonString(sensorRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",  containsString("SUCCESS")))
                .andExpect(jsonPath("$.floors[0].id", Matchers.is(1)));
        verify(managePowerService, times(1)).getFloors();
        verify(managePowerService, times(1)).updatePower(eq(sensorRequest));
        verify(asyncMotionTracking, times(1)).executeAfterMotion(eq(sensorRequest));
    }

    @Test
    public void test_PostMotionSensorInput_Exception() throws Exception {
        //Assemble
        SensorRequest sensorRequest = new SensorRequest(1, 1);
        doThrow(new RuntimeException("Error message")).when(managePowerService).getFloors();
        //Act Assert
        this.mockMvc.perform(post("/v1/power-controller/sensor")
                .content(asJsonString(sensorRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status",  containsString("UNSUCCESSFUL")))
                .andExpect(jsonPath("$.message",  containsString("Error message")))
                .andExpect(jsonPath("$.timestamp").hasJsonPath());

    }




    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
