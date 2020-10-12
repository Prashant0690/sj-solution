package com.assignment.sj.powerBot.controller;

import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.ManagePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/v1/power-controller/")
public class ElectricityController {


    @Autowired
    private ManagePowerService managePowerService;

    @Autowired
    private AsyncMotionTracking asyncMotionTracking;


    @GetMapping("status")
    public ResponseEntity<PowStatusResponse> getCurrentState(){
        PowStatusResponse statusResponse = new PowStatusResponse();
        List<Floor> floors = managePowerService.getFloors();
        statusResponse.setFloors(floors);
        return ok().body(statusResponse);
    }

    @PostMapping("sensor")
    public ResponseEntity<PowStatusResponse> getSensorInputs(@RequestBody SensorRequest sensorRequest) throws InterruptedException {
        PowStatusResponse statusResponse = new PowStatusResponse();
        managePowerService.updatePower(sensorRequest);
        asyncMotionTracking.executeAfterMotion(sensorRequest);
        List<Floor> floors = managePowerService.getFloors();
        statusResponse.setFloors(floors);
        return ok().body(statusResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception ex) {
        HashMap<String , String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", ex.getMessage());
        response.put("status", "UNSUCCESSFUL");
        return ResponseEntity.badRequest().body(response);
    }


}
