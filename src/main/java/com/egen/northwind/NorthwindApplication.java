package com.egen.northwind;

import com.egen.northwind.entity.Category;
import com.egen.northwind.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NorthwindApplication {

	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (CategoryRepository categoryRepository) {
		return args -> {

			var category1 = new Category();
			category1.setCategoryName("Acx");
			category1.setDescription("description");
			category1.setPicture("picture path");
			categoryRepository.save(category1);

			categoryRepository.findById(category1.getId())
					.ifPresent(System.out::println);


		};
	}

}
