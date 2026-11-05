package com.mx.cbtis.gestor;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ejercicio5MyApiGestorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainArrancaLaAplicacion() {
		String[] args = {};
		try (MockedStatic<SpringApplication> spring = mockStatic(SpringApplication.class)) {
			Ejercicio5MyApiGestorApplication.main(args);
			spring.verify(() -> SpringApplication.run(Ejercicio5MyApiGestorApplication.class, args));
		}
	}

}
