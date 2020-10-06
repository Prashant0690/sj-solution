package com.assignment.sj.powerBot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
    private int number;
    private List<MainCorridor> mainCorridors = new ArrayList<>();
    private List<SubCorridor> subCorridors = new ArrayList<>();
    private LocalDateTime movementTimeStamp = LocalDateTime.now();

    public Floor(int number){
        this.number = number;
    }


    public String toString(){
        return "Floor " + number;
    }

    public int calTotalFloorPower(){
        int totalPow = 0;
        for(MainCorridor mainCorridor: mainCorridors){
            totalPow = totalPow + mainCorridor.getLight().getConsumePower() + mainCorridor.getAc().getConsumePower();
        }
        for(SubCorridor subCorridor: subCorridors){
            totalPow = totalPow + subCorridor.getLight().getConsumePower() + subCorridor.getAc().getConsumePower();
        }
        return totalPow;
    }

}
