package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.SubCorridor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubCorridorRepo extends JpaRepository<SubCorridor, Integer> {

    @Query(value = "SELECT * FROM SUB_CORRIDOR  WHERE floor_id =?1 AND corridor_num =?2", nativeQuery = true)
    public Optional<SubCorridor> findByFloorIdAndCorridorNum(int floor_id, int corridor_num);
}
