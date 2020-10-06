package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.data.Floor;
import com.assignment.sj.powerBot.data.Hotel;
import com.assignment.sj.powerBot.data.MainCorridor;
import com.assignment.sj.powerBot.data.SubCorridor;
import com.assignment.sj.powerBot.jobs.AsyncMotionTracking;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

import static com.assignment.sj.powerBot.data.Equipment.Status.OFF;
import static com.assignment.sj.powerBot.data.Equipment.Status.ON;
import static java.time.Duration.between;

@Service
public class HotelDataService {

    @Value("${no.motion.check.time.millisecond}")
    private String timeNoMotion;

    /*@Autowired
    private AsyncMotionTracking asyncMotionTracking;*/

    private volatile Hotel hotel;
    private String defaultOutput;
    private final int maxFloorPow = 1*15 + 2*10;

    public String getDefaultStatusValue() {
        return this.defaultOutput;
    }

    public String getCurrentStatus() {
        String res = hotel.toString();
        return res;
    }

    public void updatePower(SensorRequest sensorRequest) throws InterruptedException {
        Floor inputFloor = hotel.getFloors().stream()
                .filter(floor -> floor.getNumber() == sensorRequest.getFloorNo())
                .findAny()
                .get();
        inputFloor.setMovementTimeStamp(LocalDateTime.now());
        SubCorridor inputSubCorridor = inputFloor.getSubCorridors().stream()
                .filter(subCorridor -> subCorridor.getNumber() == sensorRequest.getSubCorridorNo())
                .findAny()
                .get();
        if (inputSubCorridor.getLight().getStatus() == OFF){
            inputSubCorridor.getLight().changePowerStatus();
        }

        if(inputFloor.calTotalFloorPower() >= maxFloorPow){
            inputFloor.getSubCorridors()
                    .stream()
                    .filter(subCorridor -> subCorridor.getNumber() != sensorRequest.getSubCorridorNo())
                    .filter(subCorridor -> subCorridor.getAc().getStatus() == ON)
                    .forEach(subCorridor -> subCorridor.getAc().changePowerStatus());
        }

        //asyncMotionTracking.executeAfterMotion(hotel, sensorRequest);
    }

    public void noMotionUpdate(SensorRequest sensorRequest){
        Floor floor = hotel.getFloors()
                .stream().filter(floor1 -> floor1.getNumber() == sensorRequest.getFloorNo())
                .findAny()
                .get();
        int lastFloorMoment = between(floor.getMovementTimeStamp(), LocalDateTime.now()).toSecondsPart();
        if (lastFloorMoment > (Integer.valueOf(timeNoMotion)/1000)){
            for (SubCorridor subCorridor: floor.getSubCorridors() ){
                subCorridor.getLight().changePowerStatusOFF();
                subCorridor.getAc().changePowerStatusON();
            }
        }

    }

    @PostConstruct
    private void postConstruct() {
        hotel = new Hotel();
        for (int flNo= 1; flNo<=2; flNo++){
            Floor floor = new Floor(flNo);
            floor.getMainCorridors().add(new MainCorridor(1));
            floor.getSubCorridors().add(new SubCorridor(1));
            floor.getSubCorridors().add(new SubCorridor(2));
            hotel.getFloors().add(floor);
        }
        defaultOutput = hotel.toString();
    }
}
