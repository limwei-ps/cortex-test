package com.test_script.datamesh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test_script.constant.Config;

class LakeExistsTest {

	private LakeExists lake_exist = new LakeExists();
	Integer indicator = 0; 
	
	@Test 
	public void testLakeExists() {

		String projectId = Config.sourceProjectId; 
		String location = Config.location;
		
		assertEquals(1, lake_exist.exist(projectId, location)); 
	}
	

}
