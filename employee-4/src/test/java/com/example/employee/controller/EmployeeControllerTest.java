package com.example.employee.controller;

import static org.mockito.Mockito.when;

import com.example.employee.dao.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private Job job;

    @MockBean
    private JobLauncher jobLauncher;

    /**
     * Method under test: {@link EmployeeController#updateEmployee(Employee)}
     */
    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(Mockito.<Employee>any())).thenReturn("2020-03-01");

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("2020-03-01"));
    }

    /**
     * Method under test: {@link EmployeeController#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee() throws Exception {
        when(employeeService.addEmployee(Mockito.<Employee>any())).thenReturn("Add Employee");

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Add Employee"));
    }

    /**
     * Method under test: {@link EmployeeController#getSecondHighestSalary()}
     */
    @Test
    void testGetSecondHighestSalary() throws Exception {
        when(employeeService.getSecondHighestSalary()).thenReturn(10.0d);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/getSalary");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(String)}
     */
    @Test
    void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(Mockito.<String>any())).thenReturn("Delete Employee");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/employee/{id}", "42");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Employee"));
    }

    /**
     * Method under test: {@link EmployeeController#deleteEmployee(String)}
     */
    @Test
    void testDeleteEmployee2() throws Exception {
        when(employeeService.deleteEmployee(Mockito.<String>any())).thenReturn("Delete Employee");
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link EmployeeController#getAllEmployeeList()}
     */
    @Test
    void testGetAllEmployeeList() throws Exception {
        when(employeeService.getAllEmployee()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/getAll");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link EmployeeController#getEmployees(String)}
     */
    @Test
    void testGetEmployees() throws Exception {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        when(employeeService.getEmployee(Mockito.<String>any())).thenReturn(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/{id}", "42");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userName\":\"janedoe\",\"password\":\"iloveyou\",\"salary\":10.0}"));
    }

    /**
     * Method under test: {@link EmployeeController#jobInvoker()}
     */
    @Test
    void testJobInvoker() throws Exception {
        when(jobLauncher.run(Mockito.<Job>any(), Mockito.<JobParameters>any())).thenReturn(new JobExecution(1L));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/job");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Job has been invoke"));
    }
}
