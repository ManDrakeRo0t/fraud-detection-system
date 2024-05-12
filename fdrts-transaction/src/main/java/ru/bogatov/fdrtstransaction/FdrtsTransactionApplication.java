package ru.bogatov.fdrtstransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FdrtsTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FdrtsTransactionApplication.class, args);
	}

}
