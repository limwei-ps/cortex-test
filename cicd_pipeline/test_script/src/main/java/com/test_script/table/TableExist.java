package com.test_script.table;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class TableExist {

	public Integer exist(String projectId, String datasetName, List<String> bqTable) {
		try {
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			
			
		    String query =
		    		"SELECT table_id FROM " + projectId + "."+ datasetName + ".__TABLES__" ;

		    TableResult results = bigquery.query(QueryJobConfiguration.of(query));
		    List<String> addTableList = new ArrayList<>();
		    List<String> tableList = new ArrayList<>();
			
		    for (FieldValueList row : results.iterateAll()) {
		    	tableList.add(row.get("table_id").getStringValue());
		    	addTableList.add(row.get("table_id").getStringValue());
			}
			
		    addTableList.removeAll(bqTable); 

		    bqTable.removeAll(tableList); 
			
			  if (addTableList.size()==0 && bqTable.size()==0) { 
				  System.out.println("All tables accounted");
				  return 0; 
			  } else { 
				  System.out.println("Additional tables in BigQuery "+ addTableList.size() +": "+ addTableList);
				  System.out.println("Missing tables in BigQuery " + bqTable.size() +": "+bqTable); 
				  return 1; 
			  }
			 
		} catch (BigQueryException | InterruptedException e) {
			System.out.println("Class TableExist crash.\n" + e.toString());
			return 1;
		}
	}
}
