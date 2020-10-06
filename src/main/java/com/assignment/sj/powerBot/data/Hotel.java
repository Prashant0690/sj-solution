package com.assignment.sj.powerBot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    private List<Floor> floors = new ArrayList<>();

    @Override
    public String toString() {
        String value = "";
        for (Floor floor: floors){
            value = value + floor.toString() + System.lineSeparator()
            + floor.getMainCorridors().get(0).toString() + System.lineSeparator()
            + floor.getSubCorridors().get(0).toString() + System.lineSeparator()
            + floor.getSubCorridors().get(1).toString() + System.lineSeparator();
        }
        return value;

    }
}
