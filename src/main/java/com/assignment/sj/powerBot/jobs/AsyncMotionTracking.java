package com.assignment.sj.powerBot.jobs;

import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.ManagePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncMotionTracking {

    @Value("${no.motion.check.time.seconds}")
    private String noMotionTimeLimit;

    @Autowired
    private ManagePowerService managePowerService;


    @Async
    public void executeAfterMotion(SensorRequest sensorRequest) throws InterruptedException {
        Thread.sleep(Long.parseLong(noMotionTimeLimit)* 1000);
        managePowerService.noMotionUpdatePower(sensorRequest);
        System.out.println(" AsyncWorker: Completed [" + Thread.currentThread().getName() + "]");
    }
}
