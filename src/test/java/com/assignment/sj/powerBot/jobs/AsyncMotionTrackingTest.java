package com.assignment.sj.powerBot.jobs;

import com.assignment.sj.powerBot.dto.Equipment;
import com.assignment.sj.powerBot.dto.Floor;
import com.assignment.sj.powerBot.dto.SubCorridor;
import com.assignment.sj.powerBot.repository.FloorRepo;
import com.assignment.sj.powerBot.repository.SubCorridorRepo;
import com.assignment.sj.powerBot.request.SensorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.assignment.sj.powerBot.type.EquipmentType.LIGHT;
import static com.assignment.sj.powerBot.type.PowerStatusType.OFF;
import static com.assignment.sj.powerBot.type.PowerStatusType.ON;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AsyncMotionTrackingTest {

    @Autowired
    private AsyncMotionTracking asyncMotionTracking;

    @Autowired
    private FloorRepo floorRepo;

    @Autowired
    private SubCorridorRepo subCorridorRepo;


    @Test
    @Sql("/db/test.sql")
    void test_noMotionAsyncExecution_StatusChange() throws InterruptedException {
        SensorRequest sensorRequest = new SensorRequest(1, 1);
        Optional<SubCorridor> optSubCorridor1 = subCorridorRepo.findByFloorIdAndCorridorNum(1,1);
        SubCorridor subCorridor1 = optSubCorridor1.get();
        subCorridor1.setMovementTimeStamp(LocalDateTime.now());
        for(Equipment equipment: subCorridor1.getEquipment()){
            equipment.setPresentPowerStatus(ON);
        }
        subCorridorRepo.saveAndFlush(subCorridor1);
        Optional<SubCorridor> optSubCorridor2 = subCorridorRepo.findByFloorIdAndCorridorNum(1,2);
        SubCorridor subCorridor2 = optSubCorridor2.get();
        for(Equipment equipment: subCorridor2.getEquipment()){
            equipment.setPresentPowerStatus(OFF);
        }
        subCorridorRepo.saveAndFlush(subCorridor2);
        System.out.println("========Before=========");
        System.out.println(floorRepo.findById(1).get().toString());

        asyncMotionTracking.executeAfterMotion(sensorRequest);
        Thread.sleep(6000L);

        Optional<Floor> optFloor = floorRepo.findById(1);
        Floor floor = optFloor.get();
        System.out.println("========After=========");
        System.out.println(floor.toString());
        assertEquals(2, floor.getSubCorridors().size());
        assertEquals(2, floor.getSubCorridors().size());
        for (SubCorridor subCorridor: floor.getSubCorridors()){
            assertEquals(2, subCorridor.getEquipment().size());
            subCorridor.getEquipment().forEach(equipment -> {
                if (equipment.getEquipmentType() == LIGHT){
                    assertEquals(OFF, equipment.getPresentPowerStatus());
                }else {
                    assertEquals(ON, equipment.getPresentPowerStatus());
                }
            });
        }

    }

}