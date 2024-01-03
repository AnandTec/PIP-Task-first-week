package com.example.employee.controller;

import com.example.employee.dao.entity.Employee;
import com.example.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job employeeJob;

    @GetMapping(path="/job")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String jobInvoker()
    {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            log.info("job is running");
            jobLauncher.run(employeeJob,jobParameters);
            log.info("job is  After running");
        }catch (Exception ex){
            log.info("Job failed ::---" +ex.getMessage());
            return "fail the job";
        }
        return "Job has been invoke";
    }

    /**
     * getting Employee data by id
     */
    @GetMapping(path="/{id}")
    @PreAuthorize("permitAll()")
    public Employee getEmployees(@PathVariable String id)
    {
        log.error("getting Employee by id");
        return employeeService.getEmployee(id);

    }

    @GetMapping(path="/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Employee> getAllEmployeeList()
    {
        log.info("getting All the Employee");
        return employeeService.getAllEmployee();
    }

    /**
     * modify Employee data by put method
     */
    @PutMapping(path="/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateEmployee(@RequestBody Employee employee)
    {
        log.info("updating the Employee");
        return employeeService.updateEmployee(employee);
    }

    /**
     * adding Employee data by post method
     */
    @PostMapping(path="/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addEmployee(@RequestBody Employee employee)
    {
        log.info("adding the Employee");
        return employeeService.addEmployee(employee);
    }

    /**
     * Getting second, highest salary
     */
    @GetMapping(path="/getSalary")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getSecondHighestSalary()
    {
        log.info("getting All the Employee");
        return String.valueOf(employeeService.getSecondHighestSalary());
    }

    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteEmployee(@PathVariable String id)
    {
        log.info("deleting the employee");
        return employeeService.deleteEmployee(id);
    }

}
