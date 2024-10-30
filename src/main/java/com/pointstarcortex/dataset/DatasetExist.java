package com.pointstarcortex.dataset;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetId;
import com.pointstarcortex.constant.Config;

public class DatasetExist {
	
	public Integer exist(){

		try {
			// Initialize client that will be used to send requests. 
			// This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			String datasetName = Config.dataSet;

			Dataset dataset = bigquery.getDataset(DatasetId.of(datasetName));
			if (dataset != null) {
				System.out.println("Dataset already Exists.");
				return 0;
			} else {
				System.out.println("Dataset not found.");
				return 1;
			}
		} catch (BigQueryException e) {
			System.out.println("Something went wrong. \n" + e.toString());
		}
		return 1;
	}
}