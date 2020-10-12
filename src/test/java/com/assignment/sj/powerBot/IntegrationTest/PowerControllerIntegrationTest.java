package com.assignment.sj.powerBot.IntegrationTest;

import com.assignment.sj.powerBot.controller.PowStatusResponse;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PowerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @Sql("/db/test.sql")
    void test_GetCurrentElectricityStatus(){
        ResponseEntity<PowStatusResponse> response = restTemplate
                .getForEntity("http://localhost:" + port + "/v1/power-controller/status", PowStatusResponse.class);
        PowStatusResponse statusResponse = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, statusResponse.getFloors().size());

        String output = statusResponse.displayStr();
        System.out.println("===========Status Start================");
        System.out.println(output);
        System.out.println("===========Status End================");

        assertTrue(output.contains("Floor 1"));
        assertTrue(output.contains("Floor 1 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 1 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 1 : Sub corridor 2 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 2"));
        assertTrue(output.contains("Floor 2 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 2 LIGHT : OFF AC : ON"));
    }

    @Test
    @Sql("/db/test.sql")
    void test_MotionSensorInputUpdate() {
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<PowStatusResponse> response = restTemplate
                .postForEntity("http://localhost:" + port + "/v1/power-controller/sensor",
                        new HttpEntity<>(sensorRequest,headers),
                        PowStatusResponse.class);

        PowStatusResponse statusResponse = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, statusResponse.getFloors().size());

        String output = statusResponse.displayStr();
        System.out.println("===========Status Start================");
        System.out.println(output);
        System.out.println("===========Status End================");

        assertTrue(output.contains("Floor 1"));
        assertTrue(output.contains("Floor 1 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 1 : Sub corridor 1 LIGHT : OFF AC : OFF"));
        assertTrue(output.contains("Floor 1 : Sub corridor 2 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 2"));
        assertTrue(output.contains("Floor 2 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 2 LIGHT : OFF AC : ON"));
    }

    @Test
    @Sql("/db/test.sql")
    void test_MotionSensorInputAfterAsync() throws InterruptedException {
        SensorRequest sensorRequest = new SensorRequest(1, 2);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<PowStatusResponse> updateResponse = restTemplate
                .postForEntity("http://localhost:" + port + "/v1/power-controller/sensor",
                        new HttpEntity<>(sensorRequest,headers),
                        PowStatusResponse.class);
        Thread.sleep(10000L);

        ResponseEntity<PowStatusResponse> response = restTemplate
                .getForEntity("http://localhost:" + port + "/v1/power-controller/status", PowStatusResponse.class);

        PowStatusResponse statusResponse = response.getBody();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, statusResponse.getFloors().size());

        String output = statusResponse.displayStr();
        System.out.println("===========Before Async================");
        System.out.println(updateResponse.getBody().displayStr());
        System.out.println("===========Status End================");

        System.out.println("===========After Async================");
        System.out.println(output);
        System.out.println("===========Status End================");

        assertTrue(output.contains("Floor 1"));
        assertTrue(output.contains("Floor 1 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 1 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 1 : Sub corridor 2 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 2"));
        assertTrue(output.contains("Floor 2 : Main corridor 1 LIGHT : ON AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 1 LIGHT : OFF AC : ON"));
        assertTrue(output.contains("Floor 2 : Sub corridor 2 LIGHT : OFF AC : ON"));
    }


}
