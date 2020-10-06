package com.assignment.sj.powerBot.IntegrationTest;

import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PowerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test_GetDefaultElectricity(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + port + "/v1/power-controller/default-status", String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, response.getBody().contains("Success"));
    }

    @Test
    void test_GetCurrentElectricityStatus(){
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://localhost:" + port + "/v1/power-controller/status", String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, response.getBody().contains("Success"));
    }

    @Test
    void test_PostMotionSensorInput(){
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type","application/json");
        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:" + port + "/v1/power-controller/sensor",
                        new HttpEntity<>(sensorRequest,headers),
                        String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, response.getBody().contains("Success"));
    }


}
