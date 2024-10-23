package ch.bpm.innovation.innovation_process;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class InnovationProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(InnovationProcessApplication.class, args);
	}

}
