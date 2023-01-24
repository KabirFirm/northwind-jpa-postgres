package com.egen.northwind;

import com.egen.northwind.dto.CategoryDto;
import com.egen.northwind.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NorthwindApplication {

	public static void main(String[] args) {
		SpringApplication.run(NorthwindApplication.class, args);
	}

	/*@Bean
	CommandLineRunner commandLineRunner (CategoryService categoryService) {
		return args -> {


			var category = categoryService.createNewCategory(CategoryDto.builder()
							.categoryName("cat1")
							.description("descr")
					.build());

			System.out.println(categoryService.getCategoryById(category.getId()));





		};
	}*/

}
