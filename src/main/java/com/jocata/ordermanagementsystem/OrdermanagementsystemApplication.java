package com.jocata.ordermanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
public class OrdermanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdermanagementsystemApplication.class, args);
	}

}
