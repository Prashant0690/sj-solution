package com.assignment.sj.powerBot.repository;

import com.assignment.sj.powerBot.dto.MainCorridor;
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
class MainCorridorRepoTest {

    @Autowired
    private MainCorridorRepo mainCorridorRepo;


    @Test
    @Sql("/db/test.sql")
    void testFindAll() {
        List<MainCorridor> mainCorridors = mainCorridorRepo.findAll();
        assertEquals(2, mainCorridors.size(), "2 products in DB test");
    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdSuccess() {
        Optional<MainCorridor> mainCorridor = mainCorridorRepo.findById(2);
        assertTrue(mainCorridor.isPresent());
        assertEquals(2 ,mainCorridor.get().getId());
        assertEquals(1 ,mainCorridor.get().getCorridorNum());

    }

    @Test
    @Sql("/db/test.sql")
    void testFindByIdNotFound() {
        Optional<MainCorridor> mainCorridor = mainCorridorRepo.findById(3);
        assertFalse(mainCorridor.isPresent());
    }

    @Test
    @Sql("/db/test.sql")
    void testSave() {
        MainCorridor mainCorridor = new MainCorridor();
        mainCorridor.setCorridorNum(2);
        MainCorridor savedMainCorridor = mainCorridorRepo.save(mainCorridor);
        assertTrue(savedMainCorridor.getId() > 0);
        assertEquals(2, savedMainCorridor.getCorridorNum());
    }

}