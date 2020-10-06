package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.request.SensorRequest;

public interface PowerControlService {
    String printDefaultStatus();

    String printCurrentStatus();

    String sensorInputs(SensorRequest inputs) throws InterruptedException;
}
