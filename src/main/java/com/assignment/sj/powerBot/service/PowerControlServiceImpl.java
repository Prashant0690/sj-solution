package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PowerControlServiceImpl implements PowerControlService {

    @Autowired
    private HotelDataService hotelDataService;

    @Autowired
    private AsyncMotionTracking asyncMotionTracking;


    @Override
    public String printDefaultStatus() {
        System.out.println(hotelDataService.getDefaultStatusValue());
        return "Success";
    }

    @Override
    public String printCurrentStatus() {
        System.out.println(hotelDataService.getCurrentStatus());
        return "Success";
    }

    @Override
    public String sensorInputs(SensorRequest inputs) throws InterruptedException {
        hotelDataService.updatePower(inputs);
        System.out.println(hotelDataService.getCurrentStatus());
        asyncMotionTracking.executeAfterMotion(inputs);
        return "Success";
    }


}
