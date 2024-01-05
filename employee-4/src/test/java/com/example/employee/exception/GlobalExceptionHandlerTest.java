package com.example.employee.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {
    /**
     * Method under test:
     * {@link GlobalExceptionHandler#resourceNotFound(ResourceNotFoundException)}
     */
    @Test
    void testResourceNotFound() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<ErrorDetails> actualResourceNotFoundResult = globalExceptionHandler
                .resourceNotFound(new ResourceNotFoundException("The characteristics of someone or something"));
        ErrorDetails body = actualResourceNotFoundResult.getBody();
        assertEquals("The characteristics of someone or something", body.getMessage());
        assertEquals(0, body.getStatus().intValue());
        assertEquals(404, actualResourceNotFoundResult.getStatusCodeValue());
        assertTrue(actualResourceNotFoundResult.hasBody());
        assertTrue(actualResourceNotFoundResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#resourceNotFound(ResourceNotFoundException)}
     */
    @Test
//    @Disabled("TODO: Complete this test")
    void testResourceNotFound2() {
        (new GlobalExceptionHandler()).resourceNotFound(new ResourceNotFoundException("Data is not available"));
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#commonException(Exception)}
     */
    @Test
    void testCommonException() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ResponseEntity<ErrorDetails> actualCommonExceptionResult = globalExceptionHandler
                .commonException(new Exception("foo"));
        ErrorDetails body = actualCommonExceptionResult.getBody();
        assertEquals("foo", body.getMessage());
        assertEquals(1, body.getStatus().intValue());
        assertEquals(207, actualCommonExceptionResult.getStatusCodeValue());
        assertTrue(actualCommonExceptionResult.hasBody());
        assertTrue(actualCommonExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#commonException(Exception)}
     */
    @Test
//    @Disabled("TODO: Complete this test")
    void testCommonException2() {
        (new GlobalExceptionHandler()).commonException(new Exception("Data is not available"));
    }
}
