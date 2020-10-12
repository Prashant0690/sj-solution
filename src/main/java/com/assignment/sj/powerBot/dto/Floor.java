package com.assignment.sj.powerBot.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "movement_timeStamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime movementTimeStamp;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "floor_id")
    private Set<MainCorridor> mainCorridors;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "floor_id")
    private Set<SubCorridor> subCorridors;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor that = (Floor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString(){
        String s = "Floor " + id + System.lineSeparator();
        String add ="Floor " + id + " : ";
        for (MainCorridor mainCorridor : mainCorridors){
            s = s + add + mainCorridor.toString();
        }
        for (SubCorridor subCorridor: subCorridors){
            s = s + add +subCorridor.toString();
        }
        return s;
    }


}
