package com.example.employee.service;

import com.example.employee.dao.entity.Employee;
import com.example.employee.exception.CommonException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String updateEmployee(Employee employee){
        try {
          return employeeDAO.updateEmployee(employee);
        }catch (Exception ex){
            log.info("Getting error" + ex.getMessage());
            throw new CommonException("Employee not updated");
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

    public double getSecondHighestSalary (){
        Optional<Employee> emp = employeeDAO.getAllEmployeeList().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).skip(1).findFirst();
        return emp.get().getSalary();
    }

    public String deleteEmployee(String empId){
        try {
            employeeDAO.deleteEmployee(empId);
            log.info("Deleted Employee ");
        }catch (Exception ex){
            log.info("Getting error" + ex.getMessage());
            throw new CommonException("Employee are not available");
        }
        return "Success";
    }

}
