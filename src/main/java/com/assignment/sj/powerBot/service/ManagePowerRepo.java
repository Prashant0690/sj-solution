package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import com.assignment.sj.powerBot.repository.EquipmentRepo;
import com.assignment.sj.powerBot.repository.FloorRepo;
import com.assignment.sj.powerBot.repository.SubCorridorRepo;
import com.assignment.sj.powerBot.type.EquipmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.assignment.sj.powerBot.type.EquipmentType.LIGHT;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;

@Service
@Transactional
public class ManagePowerRepo {

    @Autowired
    private FloorRepo floorRepo;

    @Autowired
    private SubCorridorRepo subCorridorRepo;

    @Autowired
    private EquipmentRepo equipmentRepo;

    public List<Floor> findAllFloors() {
        List<Floor> floors = floorRepo.findAll();
        if (floors.size() == 0){
            throw new RuntimeException("Data issue: No floors found - Not found ");
        }
        return floors;
    }

    public List<Equipment> getEquipment(int subCorridorId, EquipmentType type){
        List<Equipment> equipmentList = equipmentRepo
                .findBySubCorridorIdAndType(subCorridorId, type.toString());
        if (equipmentList.size() == 0){
            throw new RuntimeException("Data issue: Wrong Sub Corridor ID - Not Found");
        }
        return equipmentList;
    }

    public SubCorridor getSubCorridor(int floorNo, int subCorridorNo) {
        Optional<SubCorridor> optSubCorridor = subCorridorRepo
                .findByFloorIdAndCorridorNum(floorNo, subCorridorNo);
        if (!optSubCorridor.isPresent()){
            new RuntimeException("Data issue: Wrong floor no or sub corridor no- Not Found");
        }
        return optSubCorridor.get();
    }

    public void updateSubCorridorLightPower(int floorNo, int subCorridorNo) {
        SubCorridor subCorridor = getSubCorridor(floorNo, subCorridorNo);
        subCorridor.getEquipment().stream()
                .filter(equipment -> equipment.getEquipmentType() == LIGHT)
                .filter(equipment -> equipment.getPresentPowerStatus() == OFF)
                .forEach(equipment -> equipment.setPresentPowerStatus(ON));
        subCorridor.setMovementTimeStamp(LocalDateTime.now());
        subCorridorRepo.save(subCorridor);
    }


    public Floor findFloorById(int floorNo) {
        floorRepo.flush();
        Optional<Floor> optFloor = floorRepo.findById(floorNo);
        if (!optFloor.isPresent()){
            new RuntimeException("Data issue: Wrong floor no-Not Found");
        }
        return optFloor.get();
    }

    public void saveEquipment(Equipment equipment){
        equipmentRepo.save(equipment);
    }
}
