package com.assignment.sj.powerBot.service;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.assignment.sj.powerBot.type.EquipmentType.AC;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;
import static java.util.stream.Collectors.toList;

@Service
public class ManagePowerService {

    @Autowired
    private PowCheckUtilService powCheckUtil;

    @Autowired
    ManagePowerRepo managePowerRepo;

    public  List<Floor> getFloors() {
       return managePowerRepo.findAllFloors();
    }

    public void updatePower(SensorRequest sensorRequest) {
        managePowerRepo.updateSubCorridorLightPower(sensorRequest.getFloorNo(), sensorRequest.getSubCorridorNo());
        Floor fLoor = managePowerRepo.findFloorById(sensorRequest.getFloorNo());
        if(powCheckUtil.testPowerOverFlow(fLoor)){
            List<SubCorridor> subCorridors = filterFloorSubCorridor(fLoor, sensorRequest.getSubCorridorNo());

            for (SubCorridor subCorridor: subCorridors){
                List<Equipment> equipmentList = fetchSubCorridorEquipmentList(subCorridor);
                turnOffAllEquipmentTypeAC(equipmentList);
            }
        }
    }

    private List<Equipment> fetchSubCorridorEquipmentList(SubCorridor subCorridor) {
        return managePowerRepo.getEquipment(subCorridor.getId(), AC);
    }

    private List<SubCorridor> filterFloorSubCorridor(Floor fLoor, int filterSubCorrNo) {
        List<SubCorridor> subCorridors = fLoor.getSubCorridors()
                .stream()
                .filter(subCorridor -> subCorridor.getCorridorNum() != filterSubCorrNo)
                .collect(toList());
        return subCorridors;
    }

    private void turnOffAllEquipmentTypeAC(List<Equipment> equipmentList) {
        equipmentList
                .stream()
                .filter(equipment -> equipment.getPresentPowerStatus() == ON)
                .forEach(equipment -> {
                    equipment.setPresentPowerStatus(OFF);
                    managePowerRepo.saveEquipment(equipment);
                });
    }

    public void noMotionUpdatePower(SensorRequest sensorRequest) {
        Floor fLoor = managePowerRepo.findFloorById(sensorRequest.getFloorNo());
        if (powCheckUtil.checkNoMotionInSubCorridor(fLoor)){
            for (SubCorridor subCorridor : fLoor.getSubCorridors()){
                for(Equipment equipment : subCorridor.getEquipment()){
                    if (equipment.getEquipmentType() == AC){
                        equipment.setPresentPowerStatus(ON);
                    } else {
                        equipment.setPresentPowerStatus(OFF);
                    }
                    managePowerRepo.saveEquipment(equipment);
                }
            }
        }
    }

}
