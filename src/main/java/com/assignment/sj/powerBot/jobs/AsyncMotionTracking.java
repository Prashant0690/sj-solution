package com.assignment.sj.powerBot.jobs;

import com.assignment.sj.powerBot.data.Floor;
import com.assignment.sj.powerBot.data.Hotel;
import com.assignment.sj.powerBot.data.SubCorridor;
import com.assignment.sj.powerBot.request.SensorRequest;
import com.assignment.sj.powerBot.service.HotelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.Duration.between;

@Component
public class AsyncMotionTracking {

    @Value("${no.motion.check.time.millisecond}")
    private String timeNoMotion;

    @Autowired
    private HotelDataService hotelDataService;


    @Async
    public void executeAfterMotion(SensorRequest sensorRequest) throws InterruptedException {
        System.out.println(" AsyncWorker: current thread [" + Thread.currentThread().getName() + "]");
        System.out.println(timeNoMotion);
        Thread.sleep(Long.valueOf(timeNoMotion));
        /*Floor floor = hotel.getFloors()
                .stream().filter(floor1 -> floor1.getNumber() == sensorRequest.getFloorNo())
                .findAny()
                .get();
        int lastFloorMoment = between(floor.getMovementTimeStamp(), LocalDateTime.now()).toSecondsPart();
        if (lastFloorMoment > (Integer.valueOf(timeNoMotion)/1000)){
            for (SubCorridor subCorridor: floor.getSubCorridors() ){
                subCorridor.getLight().changePowerStatusOFF();
                subCorridor.getAc().changePowerStatusON();
            }
        }*/

        hotelDataService.noMotionUpdate(sensorRequest);
        System.out.println(" AsyncWorker: Completed [" + Thread.currentThread().getName() + "]");
    }
}
