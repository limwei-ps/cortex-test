package com.test_script.dag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test_script.constant.Config;

class BlobExistTest {
	
	private BlobExist blob_exist = new BlobExist();
	
	Integer indicator = 0; 
	
	@Test
	public void blobExist() {
		String project_id = Config.sourceProjectId; 
		String bucketName = Config.bucketName;
		
		// if the cdc_settings.yaml and reporting settings.yaml consist of files, then that should be checked against
		assertEquals(indicator, blob_exist.exist(project_id,bucketName)); 
	}

}
