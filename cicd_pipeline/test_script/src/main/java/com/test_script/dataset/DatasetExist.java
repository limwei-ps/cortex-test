package com.test_script.dataset;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetId;

//@Component
public class DatasetExist {
	
	public Integer exist(String projectId,String datasetName){

		try {
			// Initialize client that will be used to send requests. 
			// This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

			Dataset dataset = bigquery.getDataset(DatasetId.of(projectId,datasetName));
			if (dataset != null) {
				System.out.println("Dataset already Exists.");
				return 0;
			} else {
				System.out.println("Dataset not found.");
				return 1;
			}
		} catch (BigQueryException e) {
			System.out.println("Bigquery error exception. \n" + e.toString());
		}
		return 1;
	}
}