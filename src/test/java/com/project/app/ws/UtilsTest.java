package com.project.app.ws;

import com.project.app.ws.shared.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    final void testGenerateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);
        assertNotNull(userId);
        assertTrue(userId.length() == 30);
        assertTrue(!userId.equalsIgnoreCase(userId2));
    }
    @Test
    final void testHasTokenExpired() {
        //TODO Get Token Not Hardcoded
        String token = "";
        boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }
}
