package com.example.employee.repository;

import com.example.employee.dao.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is Data access object class , it is taking responsibility to getting data from db
 */
@Repository
public class EmployeeDAO {
    @Autowired
    private EmployeeRepository repository;

    public Employee getAllEmployees(String id){
        return repository.findById(id).get();
    }

    public String updateEmployee(Employee employee){
        repository.deleteById(employee.getUserName());
        repository.save(employee);
        return employee.getUserName();
    }

    public String addEmployee(Employee employee){
        repository.save(employee);
        return employee.getUserName();
    }

    public List<Employee> getAllEmployeeList(){
        return repository.findAll();
    }

    public void deleteEmployee(String empId){
        repository.deleteById(empId);
    }

}
