package com.assignment.sj.powerBot.controller;

import com.assignment.sj.powerBot.dto.Floor;
import lombok.Data;

import java.util.List;

@Data
public class PowStatusResponse {
    private String status = "SUCCESS";
    List<Floor> floors;

    public String displayStr(){
        String output = "";
        for (Floor fLoor: floors){
            output = output + fLoor.toString();
        }
        return output;
    }
}
