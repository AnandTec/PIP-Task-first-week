package com.example.employee.config;

import com.example.employee.repository.EmployeeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * This is describing the Security part, this is containing 3 methods
 * 1-: filerChain - Having authentication logic
 * 2-: userDetailsService - Adding user in UserDetailsService
 * 3-: passwordEncoder - doing in encryption for the password
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    @Autowired
    private EmployeeDAO employeeDAO;

    /**
     * Here we are doing authentication and authorize
     * Doing Cross-Site Request Forgery in this method
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Doing Authentication for the endpoints ");
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults())
                .headers(Customizer.withDefaults());

        /**
         * Ignoring data security part for h2-console
         */

//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize ->
//                                authorize.requestMatchers("/h2-console/**").permitAll()
////                        authorize.anyRequest().authenticated()
//                ).httpBasic(Customizer.withDefaults())
//                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable());

        return http.build();
    }

    /**
     * adding user configuration for authorize the user
     */
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();

//        employeeDAO.getAllEmployeeList().forEach(emp -> userDetails.add(User
//                .withUsername(emp.getUserName())
//                .password(passwordEncoder().encode(emp.getPassword()))
//                .roles("USER")
//                .build()));

        log.info("adding user for authorization");
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        userDetails.add(admin);

        return new InMemoryUserDetailsManager(userDetails);
    }

    /**
     * Doing password encryption
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("making encoding for the password");
        return new BCryptPasswordEncoder(8);
    }
}
