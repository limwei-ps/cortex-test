package com.test_script.table;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

//https://cloud.google.com/bigquery/docs/information-schema-intro

// check table type SELECT type FROM `my-int-cortex-0824.SAP_RAW.__TABLES__`

public class TableSchema {
	
	public Map<String, Integer> TableRows(String projectId, String datasetName) {
		try {
			
		    String query =
		    		"SELECT table_id, row_count FROM " + projectId + "."+ datasetName + ".__TABLES__"; 
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
		    BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			
			Map<String, Integer> rowCountTable = new HashMap<>();
			
			for (FieldValueList row : results.iterateAll()) {
				rowCountTable.put(row.get("table_id").getStringValue(), Integer.parseInt(row.get("row_count").getStringValue()));
			}
			
			//System.out.println(rowCountTable);
			return rowCountTable;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
          return null;
        }
	}
	
	public Map<String, String> Partitioning(String projectId, String datasetName) {
		try {
			
			String query = "SELECT table_name,column_name FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.COLUMNS where is_partitioning_column = 'YES'";
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			Map<String, String> partitionedColTable = new HashMap<>();
			
			for (FieldValueList row : results.iterateAll()) {
				partitionedColTable.put(row.get("table_name").getStringValue(), row.get("column_name").getStringValue());
			}
			return partitionedColTable;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
          return null;
        }
	}
	
	public Map<String, String> Clustering(String projectId, String datasetName) {
		try {
			
			String query = "SELECT table_name,column_name,clustering_ordinal_position FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.COLUMNS WHERE clustering_ordinal_position IS NOT NULL";
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
			Map<String, String> clusteredColTable = new HashMap<>();
			
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			
			for (FieldValueList row : results.iterateAll()) {
				clusteredColTable.put(row.get("table_name").getStringValue(), row.get("column_name").getStringValue()+"_"+row.get("clustering_ordinal_position").getStringValue());
			}
			return clusteredColTable;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
          return null;
        }
		
	}
	
	public Map<String, String> TableCols(String projectId,String datasetName){
		
		try {
			String query = "SELECT table_name,column_name,data_type FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.COLUMNS";
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			Map<String, String> colTable = new HashMap<>();
			
			for (FieldValueList row : results.iterateAll()) {
				colTable.put(row.get("table_name").getStringValue()+ "_" + row.get("column_name").getStringValue() , row.get("data_type").getStringValue());
			}
			return colTable;
		} catch (BigQueryException  | InterruptedException e) {
	          System.out.println("Query not performed \n" + e.toString());
	          return null;
	    }
	}
	
	public void TableType(String projectId,String datasetName) {}
}

