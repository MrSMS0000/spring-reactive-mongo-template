package com.example.controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Mono<Employee> addEmployee(@RequestBody Mono<Employee> employeeMono){
        return employeeService.addEmployee(employeeMono);
    }

    @GetMapping("/{id}")
    public Mono<Employee> findEmployeeById(@PathVariable Integer id){
        return employeeService.findEmployeeById(id);
    }

    @PutMapping
    public Mono<Employee> updateEmployee(@RequestBody Mono<Employee> employeeMono){
        return employeeService.updateEmployee(employeeMono);
    }

    @DeleteMapping("/{id}")
    public Mono<Employee> deleteEmployee(@PathVariable Integer id){
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/many")
    public Flux<Employee> addManyEmployees(@RequestBody Collection<Employee> employeesCollection){
        return employeeService.addManyEmployees(employeesCollection);
    }

    @PutMapping("/many")
    public Flux<Employee> updateManyEmployees(@RequestBody Flux<Employee> employeesFlux){
        return employeeService.updateManyEmployees(employeesFlux);
    }
}
