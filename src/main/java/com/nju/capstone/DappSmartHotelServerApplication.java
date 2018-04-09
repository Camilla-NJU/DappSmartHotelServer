package com.nju.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nju.capstone")
public class DappSmartHotelServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DappSmartHotelServerApplication.class, args);
	}
}
