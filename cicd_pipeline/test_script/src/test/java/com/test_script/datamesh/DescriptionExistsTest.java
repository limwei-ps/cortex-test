package com.test_script.datamesh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import com.test_script.constant.Config;

class DescriptionExistsTest {
	
	private DescriptionExists description_schema = new DescriptionExists();
	
	Integer indicator = 0; 
	
	@Test 
	public void testRawTableDescription() {
		try {
			String rawDatasetName = Config.raw_dataSet; 
			String projectId = Config.sourceProjectId; 
			String directoryPath = Config.RAWBQCONFIGPATH;
			Map <String, String> tableDescription = new HashMap<>();
			Yaml yaml = new Yaml();
	        
		    File directory = new File(directoryPath);

	       File[] files = directory.listFiles();
	       for (File file : files) {
	        //System.out.println(file.getName());
	        FileReader fileReader = new FileReader(directoryPath + "/" + file.getName());
		
	        Map<String, Object> configMap = yaml.load(fileReader);

	        // Access values from the map
	        String descriptions = (String) configMap.get("description");
	        String tableName = ((String) configMap.get("name")).split("\\.")[((String) configMap.get("name")).split("\\.").length-1];
	        tableDescription.put(tableName, descriptions);
		    }
	       System.out.println(tableDescription);
	       
	       assertEquals(indicator, description_schema.TableDescription(projectId, rawDatasetName));
	       
	       } catch (FileNotFoundException e) {
				e.printStackTrace();
		}
	}
	
	@Test 
	public void testCDCTableDescription() {
		String cdcDatasetName = Config.processed_dataSet;
		String projectId = Config.sourceProjectId; 
		assertEquals(indicator, description_schema.TableDescription(projectId, cdcDatasetName)); 
	}

}
