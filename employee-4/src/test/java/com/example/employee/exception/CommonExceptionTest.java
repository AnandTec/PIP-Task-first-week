package com.example.employee.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CommonExceptionTest {
    /**
     * Method under test: {@link CommonException#CommonException(String)}
     */
    @Test
    void testConstructor() {
        CommonException actualCommonException = new CommonException("The characteristics of someone or something");
        assertEquals("The characteristics of someone or something", actualCommonException.getLocalizedMessage());
        assertEquals("The characteristics of someone or something", actualCommonException.getMessage());
        assertNull(actualCommonException.getCause());
        assertEquals(0, actualCommonException.getSuppressed().length);
    }
}
