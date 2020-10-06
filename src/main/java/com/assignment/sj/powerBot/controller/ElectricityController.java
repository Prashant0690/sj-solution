package com.assignment.sj.powerBot.controller;

import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.PowerControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/power-controller/")
public class ElectricityController {

    @Autowired
    private PowerControlService controlService;

    @GetMapping("default-status")
    public ResponseEntity<String> getDefaultState(){
        String response = controlService.printDefaultStatus();
        return ResponseEntity.ok().body(getResponse(response));
    }

    @GetMapping("status")
    public ResponseEntity<String> getCurrentState(){
        String response = controlService.printCurrentStatus();
        return ResponseEntity.ok().body(getResponse(response));
    }

    @PostMapping("sensor")
    public ResponseEntity<String> getSensorInputs(@RequestBody SensorRequest sensorRequest) throws InterruptedException {
        String response = controlService.sensorInputs(sensorRequest);
        return ResponseEntity.ok().body(getResponse(response));
    }

    private String getResponse(String output){
        return "{output:"+output+"}";
    }

}
