package ru.bogatov.fdrtscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FdrtsParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FdrtsParentApplication.class, args);
	}

}
