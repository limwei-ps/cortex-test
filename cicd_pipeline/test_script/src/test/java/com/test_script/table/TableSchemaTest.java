package com.test_script.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test_script.constant.Config;

//@SpringApplicationConfiguration(classes = {AppConfig.class}) 
class TableSchemaTest {
		
	private TableSchema table_schema = new TableSchema();
	
	Integer indicator = 0; 
	
	@Test 
	public void testTableRows() {
		String rawDatasetName = Config.raw_dataSet; 
		String cdcDatasetName = Config.processed_dataSet;
		String projectId = Config.sourceProjectId; 
		assertEquals(table_schema.TableRows(projectId, rawDatasetName), table_schema.TableRows(projectId, cdcDatasetName)); 
	}
	
	@Test 
	public void testPartitioning() {
		String rawDatasetName = Config.raw_dataSet; 
		String cdcDatasetName = Config.processed_dataSet;
		String projectId = Config.sourceProjectId; 
		assertEquals(table_schema.Partitioning(projectId, rawDatasetName), table_schema.Partitioning(projectId, cdcDatasetName)); 
	}
	
	@Test 
	public void testClustering() {
		String rawDatasetName = Config.raw_dataSet; 
		String cdcDatasetName = Config.processed_dataSet;
		String projectId = Config.sourceProjectId; 
		assertEquals(table_schema.Clustering(projectId, rawDatasetName), table_schema.Clustering(projectId, cdcDatasetName)); 
	}
	
	@Test 
	public void testTableCols() {
		String rawDatasetName = Config.raw_dataSet; 
		String cdcDatasetName = Config.processed_dataSet;
		String projectId = Config.sourceProjectId; 
		assertEquals(table_schema.TableCols(projectId, rawDatasetName), table_schema.TableCols(projectId, cdcDatasetName)); 
	}
	
	
}