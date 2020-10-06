package com.assignment.sj.powerBot.data;

import static com.assignment.sj.powerBot.data.Equipment.EquipmentType.*;
import static com.assignment.sj.powerBot.data.Equipment.Status.*;

public class AirConditioner extends Equipment {

    public AirConditioner(){
        super(AC, ON, 10);
    }

    /*public ACs(int consumePower) {
        super(AC, ON, consumePower);
    }*/
}
