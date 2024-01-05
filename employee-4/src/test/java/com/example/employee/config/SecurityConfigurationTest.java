package com.example.employee.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.employee.repository.EmployeeDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SecurityConfiguration.class})
@ExtendWith(SpringExtension.class)
class SecurityConfigurationTest {
    @MockBean
    private EmployeeDAO employeeDAO;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    /**
     * Method under test: {@link SecurityConfiguration#userDetailsService()}
     */
    @Test
    void testUserDetailsService() {
        assertTrue(securityConfiguration.userDetailsService() instanceof InMemoryUserDetailsManager);
    }

    /**
     * Method under test: {@link SecurityConfiguration#passwordEncoder()}
     */
    @Test
    void testPasswordEncoder() {
        assertTrue(securityConfiguration.passwordEncoder() instanceof BCryptPasswordEncoder);
    }
}
