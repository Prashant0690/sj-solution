package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.Floor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FloorRepoTest {

    @Autowired
    private FloorRepo floorRepo;


    @Test
    @Sql("/db/test.sql")
    void testFindAll() {
        List<Floor> floors = floorRepo.findAll();
        assertEquals(2, floors.size(), "2 products in DB test");
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdSuccess() {
        Optional<Floor> fLoor = floorRepo.findById(2);
        assertTrue(fLoor.isPresent());
        assertEquals(2 ,fLoor.get().getId());
        assertEquals("2020-10-10T02:12:34.171596",
                fLoor.get().getMovementTimeStamp().toString());

    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdNotFound() {
        Optional<Floor> fLoor = floorRepo.findById(3);
        assertFalse(fLoor.isPresent());
    }

    @Test
    @Sql("/db/test.sql")
    void testSave() {
        Floor fLoor = new Floor();
        Floor savedfLoor = floorRepo.save(fLoor);
        assertTrue(savedfLoor.getId() > 0);
    }

    @Test
    @Sql("/db/test.sql")
    void testUpdateSuccess() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Optional<Floor> fLoor = floorRepo.findById(1);
        fLoor.get().setMovementTimeStamp(localDateTime);
        Floor savedFloor  = floorRepo.save(fLoor.get());
        assertEquals(localDateTime.toString(), savedFloor.getMovementTimeStamp().toString());
    }


}