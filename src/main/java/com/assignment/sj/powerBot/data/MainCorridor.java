package com.assignment.sj.powerBot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainCorridor {

    private int number;
    Equipment light = new Light(Equipment.Status.ON);
    Equipment ac = new AirConditioner();

    public MainCorridor(int number){
        this.number = number;
    }

    @Override
    public String toString() {
        return  "Main corridor "+number+" Light "+number+" : "+light.getStatus().toString()+" AC : "+ ac.getStatus().toString();
    }
}
