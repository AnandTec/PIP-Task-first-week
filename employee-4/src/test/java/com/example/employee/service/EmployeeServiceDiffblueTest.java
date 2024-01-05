package com.example.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employee.dao.entity.Employee;
import com.example.employee.exception.CommonException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeDAO;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceDiffblueTest {
    @MockBean
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Method under test: {@link EmployeeService#getEmployee(String)}
     */
    @Test
    void testGetEmployee() {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        when(employeeDAO.getAllEmployees(Mockito.<String>any())).thenReturn(employee);
        Employee actualEmployee = employeeService.getEmployee("42");
        verify(employeeDAO).getAllEmployees(Mockito.<String>any());
        assertSame(employee, actualEmployee);
    }

    /**
     * Method under test: {@link EmployeeService#getEmployee(String)}
     */
    @Test
    void testGetEmployee2() {
        when(employeeDAO.getAllEmployees(Mockito.<String>any()))
                .thenThrow(new ResourceNotFoundException("The characteristics of someone or something"));
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployee("42"));
        verify(employeeDAO).getAllEmployees(Mockito.<String>any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(Employee)}
     */
    @Test
    void testUpdateEmployee() {
        when(employeeDAO.updateEmployee(Mockito.<Employee>any())).thenReturn("2020-03-01");

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        String actualUpdateEmployeeResult = employeeService.updateEmployee(employee);
        verify(employeeDAO).updateEmployee(Mockito.<Employee>any());
        assertEquals("2020-03-01", actualUpdateEmployeeResult);
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(Employee)}
     */
    @Test
    void testUpdateEmployee2() {
        when(employeeDAO.updateEmployee(Mockito.<Employee>any()))
                .thenThrow(new ResourceNotFoundException("The characteristics of someone or something"));

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        assertThrows(CommonException.class, () -> employeeService.updateEmployee(employee));
        verify(employeeDAO).updateEmployee(Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee() {
        when(employeeDAO.addEmployee(Mockito.<Employee>any())).thenReturn("Add Employee");

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        String actualAddEmployeeResult = employeeService.addEmployee(employee);
        verify(employeeDAO).addEmployee(Mockito.<Employee>any());
        assertEquals("Add Employee", actualAddEmployeeResult);
    }

    /**
     * Method under test: {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee2() {
        when(employeeDAO.addEmployee(Mockito.<Employee>any()))
                .thenThrow(new ResourceNotFoundException("The characteristics of someone or something"));

        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        assertThrows(CommonException.class, () -> employeeService.addEmployee(employee));
        verify(employeeDAO).addEmployee(Mockito.<Employee>any());
    }

    /**
     * Method under test: {@link EmployeeService#getAllEmployee()}
     */
    @Test
    void testGetAllEmployee() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        when(employeeDAO.getAllEmployeeList()).thenReturn(employeeList);
        List<Employee> actualAllEmployee = employeeService.getAllEmployee();
        verify(employeeDAO).getAllEmployeeList();
        assertTrue(actualAllEmployee.isEmpty());
        assertSame(employeeList, actualAllEmployee);
    }

    /**
     * Method under test: {@link EmployeeService#getAllEmployee()}
     */
    @Test
    void testGetAllEmployee2() {
        when(employeeDAO.getAllEmployeeList())
                .thenThrow(new ResourceNotFoundException("The characteristics of someone or something"));
        assertThrows(CommonException.class, () -> employeeService.getAllEmployee());
        verify(employeeDAO).getAllEmployeeList();
    }

    /**
     * Method under test: {@link EmployeeService#getSecondHighestSalary()}
     */
    @Test
    void testGetSecondHighestSalary() {
        when(employeeDAO.getAllEmployeeList())
                .thenThrow(new ResourceNotFoundException("The characteristics of someone or something"));
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getSecondHighestSalary());
        verify(employeeDAO).getAllEmployeeList();
    }

    /**
     * Method under test: {@link EmployeeService#getSecondHighestSalary()}
     */
    @Test
    void testGetSecondHighestSalary2() {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");

        Employee employee2 = new Employee();
        employee2.setPassword("Password");
        employee2.setSalary(0.5d);
        employee2.setUserName("User Name");

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee2);
        employeeList.add(employee);
        when(employeeDAO.getAllEmployeeList()).thenReturn(employeeList);
        double actualSecondHighestSalary = employeeService.getSecondHighestSalary();
        verify(employeeDAO).getAllEmployeeList();
        assertEquals(0.5d, actualSecondHighestSalary);
    }

    /**
     * Method under test: {@link EmployeeService#deleteEmployee(String)}
     */
    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeDAO).deleteEmployee(Mockito.<String>any());
        String actualDeleteEmployeeResult = employeeService.deleteEmployee("42");
        verify(employeeDAO).deleteEmployee(Mockito.<String>any());
        assertEquals("Success", actualDeleteEmployeeResult);
    }

    /**
     * Method under test: {@link EmployeeService#deleteEmployee(String)}
     */
    @Test
    void testDeleteEmployee2() {
        doThrow(new ResourceNotFoundException("The characteristics of someone or something")).when(employeeDAO)
                .deleteEmployee(Mockito.<String>any());
        assertThrows(CommonException.class, () -> employeeService.deleteEmployee("42"));
        verify(employeeDAO).deleteEmployee(Mockito.<String>any());
    }
}
