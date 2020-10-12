package com.assignment.sj.powerBot.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class SubCorridor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "corridor_num", nullable = false)
    private int corridorNum;

    @Column(name = "movement_timeStamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime movementTimeStamp;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_corridor_id")
    private Set<Equipment> equipment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubCorridor that = (SubCorridor) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String toString(){
        String s = "Sub corridor " + corridorNum;
        for (Equipment equipment: equipment){
            s = s + " " +equipment.toString();
        }
        s = s + System.lineSeparator();
        return s;
    }

}
