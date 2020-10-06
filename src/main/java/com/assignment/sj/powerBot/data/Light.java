package com.assignment.sj.powerBot.data;

import static com.assignment.sj.powerBot.data.Equipment.EquipmentType.*;
import static com.assignment.sj.powerBot.data.Equipment.Status.*;

public class Light extends Equipment {

    public Light(Status status){
        super(LIGHT, status, 5);
    }

    /*public Light(int consumePower) {
        super(LIGHT, OFF, consumePower);
    }*/
}
