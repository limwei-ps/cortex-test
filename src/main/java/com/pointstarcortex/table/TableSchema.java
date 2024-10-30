package com.pointstarcortex.table;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.pointstarcortex.constant.Config;

//https://cloud.google.com/bigquery/docs/information-schema-intro
//SELECT table_name,ddl FROM testdata_sap_raw.INFORMATION_SCHEMA.TABLES;

public class TableSchema {
	
	public Integer TableRows() {
		try {
			String datasetName = "testdata_sap_cdc_processed" ; 
			String projectId =Config.projectId; 
		    String query =
		    		"SELECT table_name, sum(total_rows) as total_rows FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.PARTITIONS GROUP BY table_name"; 
		    
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
		    BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			for (FieldValueList row : results.iterateAll()) {
				System.out.println(row.get("table_name").getStringValue()+": "+row.get("total_rows").getStringValue());
			}
			return 0;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
        }
		return 1;
	}
	
	public Integer Partitioning() {
		try {
			
			String datasetName = "testdata_sap_cdc_processed" ; 
			String projectId =Config.projectId; 
			//String query = "SELECT COUNT(*) FROM " + projectId + "."+ datasetName + "."+tableName;
			String query = "SELECT * FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.COLUMNS where is_partitioning_column = 'YES'";
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			for (FieldValueList row : results.iterateAll()) {
				System.out.println(row.get("table_name").getStringValue()+": "+row.get("column_name").getStringValue());
			}
			return 0;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
        }
		return 1;
	}
	
	public Integer Clustering() {
		try {
			
			String datasetName = "testdata_sap_cdc_processed" ; 
			String projectId =Config.projectId; 
			String query = "SELECT * FROM " + projectId + "."+ datasetName + "."+ "INFORMATION_SCHEMA.COLUMNS WHERE clustering_ordinal_position IS NOT NULL";
			// Initialize client that will be used to send requests. 
		    // This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			for (FieldValueList row : results.iterateAll()) {
				System.out.println(row.get("table_name").getStringValue()+": "+row.get("column_name").getStringValue()+": "+ row.get("clustering_ordinal_position").getStringValue());
			}
			return 0;
			
        }  catch (BigQueryException | InterruptedException e) {
          System.out.println("Query not performed \n" + e.toString());
        }
		return 1;
	}
	
}

