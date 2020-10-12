package com.assignment.sj.powerBot.dto;

import com.assignment.sj.powerBot.type.EquipmentType;
import com.assignment.sj.powerBot.type.PowerStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EquipmentType equipmentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerStatusType defaultPowerStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PowerStatusType presentPowerStatus;

    @Column(nullable = false)
    private int consumePower;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment that = (Equipment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString(){
        String s = equipmentType.toString() + " : " + presentPowerStatus.toString();
        return s;
    }

}
