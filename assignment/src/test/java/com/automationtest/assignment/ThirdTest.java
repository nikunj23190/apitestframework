package com.automationtest.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.automationtest.assignment.config.AppConfig;

@SpringBootTest
@ContextConfiguration(classes = {AppConfig.class})
public class ThirdTest {
	
	@Test
	void simpleTest() {
		System.out.println("Hi i am third");
	}

}
