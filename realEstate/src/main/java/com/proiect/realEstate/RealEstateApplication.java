package com.proiect.realEstate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RealEstateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}

}
