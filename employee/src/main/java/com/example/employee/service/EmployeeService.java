package com.example.employee.service;

import com.example.employee.dao.entity.Employee;
import com.example.employee.exception.CommonException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service class , it is used from write the business logic
 *
 */
@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;
    public Employee getEmployee(String id){
        try {
            return employeeDAO.getAllEmployees(id);
        }catch (Exception ex){
            log.info("Resource is not found" + ex.getMessage());
            throw new ResourceNotFoundException("Employee are not available");
        }
    }

    public String addEmployee(Employee employee){
        try{
            return employeeDAO.addEmployee(employee);
        }catch (Exception ex){
            log.info("Getting error" + ex.getMessage());
            throw new CommonException("Employee is not add");
        }

    }

    public List<Employee> getAllEmployee(){
        try {
            return employeeDAO.getAllEmployeeList();
        }catch (Exception ex){
            log.info("Getting error" + ex.getMessage());
            throw new CommonException("Employee are not available");
        }

    }


}
