package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.SubCorridor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SubCorridorRepoTest {

    @Autowired
    private SubCorridorRepo subCorridorRepo;


    @Test
    @Sql("/db/test.sql")
    void testFindAll() {
        List<SubCorridor> subCorridors = subCorridorRepo.findAll();
        assertEquals(4, subCorridors.size(), "2 products in DB test");
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdSuccess() {
        Optional<SubCorridor> subCorridor = subCorridorRepo.findById(4);
        assertTrue(subCorridor.isPresent());
        assertEquals(4 ,subCorridor.get().getId());
        assertEquals(2 ,subCorridor.get().getCorridorNum());
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdNotFound() {
        Optional<SubCorridor> subCorridor = subCorridorRepo.findById(5);
        assertFalse(subCorridor.isPresent());
    }

    @Test
    @Sql("/db/test.sql")
    void testSave() {
        SubCorridor subCorridor = new SubCorridor();
        subCorridor.setCorridorNum(5);
        SubCorridor savedSubCorridor = subCorridorRepo.save(subCorridor);
        assertTrue(savedSubCorridor.getId() > 0);
        assertEquals(5, savedSubCorridor.getCorridorNum());
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByFloorNoAndSubCorridorNo_Success() {
        Optional<SubCorridor> subCorridor =
                subCorridorRepo.findByFloorIdAndCorridorNum(1, 2 );
        assertTrue(subCorridor.isPresent());
        assertEquals(2, subCorridor.get().getId());
        assertEquals(2, subCorridor.get().getCorridorNum());

    }

    @Test
    @Sql("/db/test.sql")
    void testFindByFloorNoAndSubCorridorNo_NotFound() {
        Optional<SubCorridor> subCorridor =
                subCorridorRepo.findByFloorIdAndCorridorNum(10, 2 );
        assertFalse(subCorridor.isPresent());
    }

}