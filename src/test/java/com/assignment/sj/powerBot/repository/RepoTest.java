package com.assignment.sj.powerBot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepoTest {

    @Autowired
    private FloorRepo floorRepo;

    @Autowired
    private MainCorridorRepo mainCorridorRepo;

    @Autowired
    private SubCorridorRepo subCorridorRepo;

    @Autowired
    private EquipmentRepo equipmentRepo;

    @Test
    @Sql("/db/test.sql")
    public void test(){
        assertNotNull(floorRepo);
        assertNotNull(mainCorridorRepo);
        assertNotNull(subCorridorRepo);
        assertNotNull(equipmentRepo);
    }


}
