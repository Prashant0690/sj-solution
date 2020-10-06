package com.assignment.sj.powerBot.controllerTest;

import com.assignment.sj.powerBot.controller.ElectricityController;
import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.PowerControlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Path.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectricityController.class)
public class ElectricityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PowerControlService controlService;

    @Test
    @DisplayName("Get /v1/power-controller/default-status - Success")
    public void test_GetDefaultElectricityStatus() throws Exception {
        //Assemble
        when(controlService.printDefaultStatus()).thenReturn("Success");
        //Act Assert
        this.mockMvc.perform(get("/v1/power-controller/default-status")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output").exists())
                .andExpect(jsonPath("$.output").isString())
                .andExpect(jsonPath("$.output",  containsString("Success")));
        verify(controlService, times(1)).printDefaultStatus();
    }

    @Test
    @DisplayName("Get v1/power-controller/status")
    public void test_GetCurrentElectricityStatus() throws Exception {
        //Assemble
        when(controlService.printCurrentStatus()).thenReturn("Success");
        //Act Assert
        this.mockMvc.perform(get("/v1/power-controller/status")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output").exists())
                .andExpect(jsonPath("$.output").isString())
                .andExpect(jsonPath("$.output",  containsString("Success")))
                .andReturn();
        verify(controlService, times(1)).printCurrentStatus();
    }

    @Test
    public void test_PostMotionSensorInput() throws Exception {
        //Assemble
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        when(controlService.sensorInputs( eq(sensorRequest) )).thenReturn("Success");
        //Act Assert
        this.mockMvc.perform(post("/v1/power-controller/sensor")
                .content(asJsonString(sensorRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.output").exists())
                .andExpect(jsonPath("$.output").isString())
                .andExpect(jsonPath("$.output",  containsString("Success")));
        verify(controlService, times(1)).sensorInputs(sensorRequest);
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
