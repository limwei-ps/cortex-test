package com.pointstarcortex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class AppTest {
	
	String message = "BrowserStack is the intended message";
	@Test 
	public void testMessage() {
		/*
		 * System.out. println("Inside testMessage()"); assertEquals(message,
		 * "BrowserStack is the intended message");
		 */
	}
}