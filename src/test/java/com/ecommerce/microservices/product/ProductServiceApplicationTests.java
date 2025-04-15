package com.ecommerce.microservices.product;

import com.ecommerce.microservices.product.model.Product;
import com.ecommerce.microservices.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* loads the entire Spring Boot application context.
Think of it like starting the whole engine just to test a spark plug. It's heavy and slow.
It’s best used for integration tests, where you want to test how multiple components work together (e.g., service + repository + database).
 */
@SpringBootTest
@Testcontainers	//Spins up a real MongoDB container (using Testcontainers).
@AutoConfigureMockMvc	//Enables and configures MockMvc so you can simulate HTTP requests without a real server.
class ProductServiceApplicationTests {

	//annotation tells Testcontainers to manage the lifecycle of the container
	@Container
	//This creates a real MongoDB instance inside a Docker container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@Autowired
	private MockMvc mockMvc;	// mock http requests

	@Autowired
	private ObjectMapper objectMapper; //Converts Java <-> JSON

	@Autowired
	private ProductRepository productRepository;

	/*
	Dynamically sets Spring Boot’s MongoDB URL property to use the container’s address.
	So instead of using application.yml, this test uses the container’s URI (like mongodb://localhost:12345/test).
	 */
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void createProductTest() throws Exception{

		Product sampleProduct = productRequest();
		String request = objectMapper.writeValueAsString(sampleProduct);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request)).andExpect(status().isCreated());

		Assertions.assertEquals(1, productRepository.findAll().size());

	}

	@Test
	void getProductByIdTest() throws Exception {

		Product product = productRequest();

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/list/1"))
				.andExpect(status().isOk());
		Assertions.assertEquals("Sample Product",product.getName());
	}

	private Product productRequest(){
		return Product.builder()
				.id("1")
				.name("Sample Product")
				.description("The sample product demo")
				.price(BigDecimal.valueOf(1600))
				.build();
	}

}
