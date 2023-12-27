package com.example.employee.repository;

import com.example.employee.dao.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * This is Data access object class , it is taking responsibility to getting data from db
 */
@Repository
public class EmployeeDAO {

    private static List<Employee> employeeList;

    static {

        employeeList = new ArrayList<>();

        Employee emp = new Employee();
        emp.setUserName("anand");
        emp.setPassword("abc@123");
        emp.setSalary(12000d);

        Employee emp2 = new Employee();
        emp2.setUserName("pavan");
        emp2.setPassword("abc@123");
        emp2.setSalary(15000d);

        Employee emp3 = new Employee();
        emp3.setUserName("Rahul");
        emp3.setPassword("abc@123");
        emp3.setSalary(20000d);

        employeeList.add(emp);
        employeeList.add(emp2);
        employeeList.add(emp3);
    }

    public Employee getAllEmployees(String id){
        return employeeList.stream().filter(e-> e.getUserName().equals(id)).findFirst().get();
    }

    public String addEmployee(Employee employee){
        employeeList.add(employee);
        return employee.getUserName();
    }

    public List<Employee> getAllEmployeeList(){
        return employeeList;
    }

}
