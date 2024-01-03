package com.example.employee.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    String userName;
    String password;
    Double salary;


}
