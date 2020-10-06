package com.assignment.sj.powerBot.data;

import lombok.Getter;

import java.time.LocalDateTime;

import static com.assignment.sj.powerBot.data.Equipment.Status.OFF;
import static com.assignment.sj.powerBot.data.Equipment.Status.ON;
import static java.time.Duration.between;

@Getter
public abstract class Equipment {

    public enum EquipmentType{AC,LIGHT}
    public enum Status{ON,OFF}

    private final EquipmentType type;
    private Status status;
    private LocalDateTime statusUpdatedOn;
    private final int consumePower;



    public Equipment(EquipmentType type, Status status, int consumePower) {
        this.type = type;
        this.status = status;
        this.statusUpdatedOn = LocalDateTime.now();
        this.consumePower = consumePower;
    }

    public void changePowerStatus(){
        this.status = status == ON ? OFF : ON;
        this.statusUpdatedOn = LocalDateTime.now();
    }

    public void changePowerStatusON(){
        this.status =  ON;
        this.statusUpdatedOn = LocalDateTime.now();
    }

    public void changePowerStatusOFF(){
        this.status =  OFF;
        this.statusUpdatedOn = LocalDateTime.now();
    }

    public int statusChangeDurationInSec(){
        return between(statusUpdatedOn, LocalDateTime.now()).toSecondsPart();
    }
}
