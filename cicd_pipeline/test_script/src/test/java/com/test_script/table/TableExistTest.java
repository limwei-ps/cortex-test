package com.test_script.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.test_script.constant.Config;

class TableExistTest {

	private TableExist table_exist = new TableExist();
	
	Integer indicator = 0; 
	
	@Test 
	public void testRawTableExist() {
		String raw_dataSet = Config.raw_dataSet;
		String project_id = Config.sourceProjectId; 
		HashMap<String, HashMap<String, String>> rawTable = Config.bqRawTable;
		List<String> bqTable = new ArrayList<>();
		for (Map.Entry<String, HashMap<String, String>> entry : rawTable.entrySet()) {
		    //System.out.println("Outer Map Key: " + entry.getKey().trim());
			if (entry.getKey() != null) {
				bqTable.add(entry.getKey().trim());
		}
		}
		assertEquals(indicator, table_exist.exist(project_id, raw_dataSet, bqTable)); 
	}
	
	@Test 
	public void testCDCTableExist() {
		String cdc_dataSet = Config.processed_dataSet;
		String project_id = Config.sourceProjectId; 
		HashMap<String, HashMap<String, String>> cdcTable = Config.bqCDCTable;
		List<String> bqTable = new ArrayList<>();
		for (Map.Entry<String, HashMap<String, String>> entry : cdcTable.entrySet()) {
		    //System.out.println("Outer Map Key: " + entry.getKey().trim());
			if (entry.getKey() != null) {
				bqTable.add(entry.getKey().trim());
		}
		}
		assertEquals(indicator, table_exist.exist(project_id, cdc_dataSet, bqTable)); 
	}
	
	@Test
	public void testReportingTableExist() {
		//"\src\SAP\SAP_REPORTING\reporting_settings_ecc.yaml"
	}
}
