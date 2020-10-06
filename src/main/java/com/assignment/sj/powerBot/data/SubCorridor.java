package com.assignment.sj.powerBot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCorridor {

    private int number;
    Equipment light = new Light(Equipment.Status.OFF);
    Equipment ac = new AirConditioner();

    public SubCorridor(int number){
        this.number = number;
    }

    @Override
    public String toString() {
        return  "Sub corridor "+number+" Light "+number+" : "+light.getStatus().toString()+" AC : "+ ac.getStatus().toString();
    }
}
