package com.example.employee.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.employee.dao.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeDAO.class})
@ExtendWith(SpringExtension.class)
class EmployeeDAOTest {
    @Autowired
    private EmployeeDAO employeeDAO;

    @MockBean
    private EmployeeRepository employeeRepository;

    /**
     * Method under test: {@link EmployeeDAO#getAllEmployees(String)}
     */
    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        Employee actualAllEmployees = employeeDAO.getAllEmployees("42");
        verify(employeeRepository).findById(Mockito.<String>any());
        assertSame(employee, actualAllEmployees);
    }

    /**
     * Method under test: {@link EmployeeDAO#updateEmployee(Employee)}
     */
    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee);
        doNothing().when(employeeRepository).deleteById(Mockito.<String>any());

        Employee employee2 = new Employee();
        employee2.setPassword("iloveyou");
        employee2.setSalary(10.0d);
        employee2.setUserName("janedoe");
        String actualUpdateEmployeeResult = employeeDAO.updateEmployee(employee2);
        verify(employeeRepository).deleteById(Mockito.<String>any());
        verify(employeeRepository).save(Mockito.<Employee>any());
        assertEquals("janedoe", actualUpdateEmployeeResult);
    }

    /**
     * Method under test: {@link EmployeeDAO#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        when(employeeRepository.save(Mockito.<Employee>any())).thenReturn(employee);

        Employee employee2 = new Employee();
        employee2.setPassword("iloveyou");
        employee2.setSalary(10.0d);
        employee2.setUserName("janedoe");
        String actualAddEmployeeResult = employeeDAO.addEmployee(employee2);
        verify(employeeRepository).save(Mockito.<Employee>any());
        assertEquals("janedoe", actualAddEmployeeResult);
    }

    /**
     * Method under test: {@link EmployeeDAO#getAllEmployeeList()}
     */
    @Test
    void testGetAllEmployeeList() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualAllEmployeeList = employeeDAO.getAllEmployeeList();
        verify(employeeRepository).findAll();
        assertTrue(actualAllEmployeeList.isEmpty());
        assertSame(employeeList, actualAllEmployeeList);
    }

    /**
     * Method under test: {@link EmployeeDAO#deleteEmployee(String)}
     */
    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(Mockito.<String>any());
        employeeDAO.deleteEmployee("42");
        verify(employeeRepository).deleteById(Mockito.<String>any());
        assertTrue(employeeDAO.getAllEmployeeList().isEmpty());
    }
}
