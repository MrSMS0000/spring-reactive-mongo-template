package com.example;

import com.example.controller.EmployeeController;
import com.example.model.Employee;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
class SpringReactiveMongoTemplateApplicationTests {
	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private EmployeeService service;

	@Test
	public void addEmployeeTest(){
		Mono<Employee> employeeMono = Mono.just(new Employee(2,"Jadu","Teri",10));
		when(service.addEmployee(employeeMono)).thenReturn(employeeMono);

		webTestClient.post().uri("/employees")
				.body(Mono.just(employeeMono),Employee.class)
				.exchange()
				.expectStatus().isOk();
	}
}
