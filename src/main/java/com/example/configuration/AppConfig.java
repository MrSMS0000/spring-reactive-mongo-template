package com.example.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static org.springframework.data.mongodb.core.WriteResultChecking.EXCEPTION;

@Configuration
public class AppConfig {

    public static final String dataBaseName = "EmployeesDataBase";
    public static final String collectionName = "reactiveTemplateEmployees";
    @Bean
    public MongoClient mongoClient(){
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://admin:12345@springmongocluster.xbfbl9p.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return  MongoClients.create(mongoClientSettings);
    }
    @Bean
    public ReactiveMongoTemplate mongoTemplate() throws Exception{
        String dataBaseName = AppConfig.dataBaseName;
        ReactiveMongoTemplate reactiveMongoTemplate = new ReactiveMongoTemplate(mongoClient(), dataBaseName);
        reactiveMongoTemplate.setWriteResultChecking(EXCEPTION);
        return reactiveMongoTemplate;
    }
}
