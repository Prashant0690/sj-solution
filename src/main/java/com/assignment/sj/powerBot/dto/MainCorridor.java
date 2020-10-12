package com.assignment.sj.powerBot.dto;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class MainCorridor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "corridor_num", nullable = false)
    private int corridorNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_corridor_id")
    private Set<Equipment> equipmentList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainCorridor that = (MainCorridor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString(){
        String s = "Main corridor " + corridorNum;
        for (Equipment equipment: equipmentList){
            s = s + " " +equipment.toString();
        }
        s = s + System.lineSeparator();
        return s;
    }
}
