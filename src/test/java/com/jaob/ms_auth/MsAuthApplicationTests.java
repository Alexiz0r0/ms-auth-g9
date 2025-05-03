package com.jaob.ms_auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MsAuthApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodRunsWithoutException() {
        assertDoesNotThrow(() -> MsAuthApplication.main(new String[]{}));
    }
}
