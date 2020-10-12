package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepo extends JpaRepository<Equipment, Integer> {

    @Query(value = "SELECT * FROM EQUIPMENT WHERE SUB_CORRIDOR_ID =?1 and EQUIPMENT_TYPE =?2  ", nativeQuery = true)
    List<Equipment> findBySubCorridorIdAndType(int i, String type);
}
