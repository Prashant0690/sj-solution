package com.assignment.sj.powerBot.jobs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
class AsyncMotionTrackingTest {

    @Autowired
    private AsyncMotionTracking asyncMotionTracking;

    @Test
    void test() throws InterruptedException {
        asyncMotionTracking.executeAfterMotion(null);
        /*asyncMotionTracking.executeAfterMotion();
        asyncMotionTracking.executeAfterMotion();*/

        Thread.sleep(1_5000l);

    }

}