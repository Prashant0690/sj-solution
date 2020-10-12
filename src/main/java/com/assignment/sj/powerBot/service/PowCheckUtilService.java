package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.assignment.sj.powerBot.type.PowerStatusType.ON;
import static java.time.Duration.between;

@Service
public class PowCheckUtilService {

    @Value("${max.power.multiplier.per.main.corridors}")
    private int powMulMaincorridors;

    @Value("${max.power.multiplier.per.sub.corridors}")
    private int powMulSubcorridors;

    @Value("${no.motion.check.time.seconds}")
    private int noMotionTimeLimit;

    public boolean checkNoMotionInSubCorridor(Floor floor){
        boolean response = true;
        for(SubCorridor subCorridor : floor.getSubCorridors()) {
            if(subCorridor.getMovementTimeStamp() != null){
                //int timeDiff = between(subCorridor.getMovementTimeStamp(), LocalDateTime.now()).toSecondsPart();
                if ( secondsPassed(subCorridor.getMovementTimeStamp()) < noMotionTimeLimit ){
                    response = false;
                    break;
                }
            }
        }
        return response;
    }

    private int secondsPassed(LocalDateTime localDateTime){
        return between(localDateTime, LocalDateTime.now()).toSecondsPart();
    }

    public boolean testPowerOverFlow(Floor floor){
        return totalFloorPow(floor) > calMaxFloorPow(floor);
    }

    public int calMaxFloorPow(Floor floor){
        return (floor.getMainCorridors().size()*powMulMaincorridors
                + floor.getSubCorridors().size()*powMulSubcorridors);
    }

    public int totalFloorPow(Floor floor){
        return getTotPowerMainCorr(floor) + getTotPowerSubCorr(floor);
    }

    private int getTotPowerSubCorr(Floor floor) {
        int totPowerSubCorr = floor.getSubCorridors().stream()
                .flatMap(subCorridor -> subCorridor.getEquipment().stream())
                .filter(equipment -> equipment.getPresentPowerStatus() == ON)
                .mapToInt(Equipment::getConsumePower)
                .sum();
        return totPowerSubCorr;
    }

    private int getTotPowerMainCorr(Floor floor) {
        int totPowerMainCorr = floor.getMainCorridors().stream()
                .flatMap(mainCorridor -> mainCorridor.getEquipmentList().stream())
                .filter(equipment -> equipment.getPresentPowerStatus() == ON)
                .mapToInt(Equipment::getConsumePower)
                .sum();
        return totPowerMainCorr;
    }
}
