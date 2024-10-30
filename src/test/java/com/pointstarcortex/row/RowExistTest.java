package com.pointstarcortex.row;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pointstarcortex.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
//@SpringApplicationConfiguration(classes = {AppConfig.class}) 
public class RowExistTest {
	
	@Autowired
	private RowExist row_exist;
	
	Integer indicator = 0; 
	
	@Test 
	public void testRowExist() {
		assertEquals(indicator, row_exist.exist()); 
	}
	
}