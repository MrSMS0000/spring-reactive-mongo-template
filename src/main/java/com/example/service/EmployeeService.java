package com.example.service;

import com.example.configuration.AppConfig;
import com.example.model.Employee;
import com.mongodb.MongoWriteException;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.internal.bulk.DeleteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class EmployeeService {
    private final ReactiveMongoTemplate mongoOps;

    @Autowired
    public EmployeeService(ReactiveMongoTemplate mongoOps) {
        this.mongoOps = mongoOps;
    }


    public Mono<Employee> addEmployee(Mono<Employee> employeeMono){
        return mongoOps.insert(employeeMono);
    }

    public Mono<Employee> findEmployeeById(Integer id){
        return mongoOps.findById(id, Employee.class, AppConfig.collectionName);
    }

    public Mono<Employee> updateEmployee(Mono<Employee> employeeMono){
        return employeeMono
                .flatMap(employee ->
                    mongoOps.findAndReplace(
                            query(where("_id").is(employee.getId())),
                            employee,
                            // to return updated employee
                            //FindAndReplaceOptions.options().returnNew(),
                            AppConfig.collectionName
                    )
                );
                // to show error when id does not exist.
                //.switchIfEmpty(Mono.error(new Exception()));
    }

    public Mono<Employee> deleteEmployee(Integer id){
        return mongoOps.findAndRemove(query(where("_id").is(id)), Employee.class, AppConfig.collectionName);
    }

    public Flux<Employee> addManyEmployees(Mono<Collection<Employee>> employeesCollectionMono){
        return mongoOps.insertAll(employeesCollectionMono, AppConfig.collectionName);
    }

    public Flux<Employee> updateManyEmployees(Flux<Employee> employeesFlux){
        return employeesFlux.flatMap(employee -> mongoOps.findAndReplace(
                query(where("_id").is(employee.getId())),
                employee,
                AppConfig.collectionName
                )
        );
        // to return null when any of the id does not present
        //.switchIfEmpty(Mono.error(new Exception()));
    }

}
