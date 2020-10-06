package com.assignment.sj.powerBot.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorRequest {

    private int floorNo;
    private int subCorridorNo;

}
