package com.test_script.datamesh;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class DescriptionExists {
	/*
	 * String description = dataset.getDescription();
	 * System.out.println(description);
	 */
	public Map<String, String> TableDescription(String projectId, String datasetName) {
		try {
			// Initialize client that will be used to send requests. This client only needs
			// to be created
			// once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

		    String query =
		    		"SELECT table_name,option_value FROM " + projectId + "."+ datasetName + ".INFORMATION_SCHEMA.TABLE_OPTIONS WHERE option_name = \"description\"" ;

		    TableResult results = bigquery.query(QueryJobConfiguration.of(query));
		    
		    Map<String, String> descriptionDictionary = new HashMap<>();
		    
		    for (FieldValueList row : results.iterateAll()) {
		    	descriptionDictionary.put(row.get("table_name").getStringValue(), row.get("option_value").getStringValue());
			}
			return descriptionDictionary;
			
		} catch (BigQueryException | JobException | InterruptedException e) {
			System.out.println("Table not retrieved. \n" + e.toString());
		}
		return null;
	}
	public Map<String, String> ColumnDescription(String projectId, String datasetName) {
		//SELECT table_name,column_name,description FROM `my-int-cortex-0824.testdata_sap_cdc_processed.INFORMATION_SCHEMA.COLUMN_FIELD_PATHS`
		// need to adjust string of string and use clone and clear to account for multiple columns from same table
		try {
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
	
		    String query =
		    		"SELECT table_name,column_name,description FROM " + projectId + "."+ datasetName + ".INFORMATION_SCHEMA.COLUMN_FIELD_PATHS" ;
	
		    TableResult results = bigquery.query(QueryJobConfiguration.of(query));
		    
		    Map<String, String> descriptionDictionary = new HashMap<>();
		    
		    for (FieldValueList row : results.iterateAll()) {
		    	descriptionDictionary.put(row.get("table_name").getStringValue(), row.get("column_name").getStringValue()+":::"+row.get("description").getStringValue());
			}
			return descriptionDictionary;
		
		} catch (BigQueryException | JobException | InterruptedException e) {
			System.out.println("Table not retrieved. \n" + e.toString());
		}
		return null;
	}
}
