package com.pointstarcortex.datamesh;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.pointstarcortex.constant.Config;

public class DescriptionExists {
	/*
	 * String description = dataset.getDescription();
	 * System.out.println(description);
	 */
	public Integer TableDescription() {
		try {
			String datasetName = Config.dataSet;
			String projectId = Config.projectId;
			String tableName = "adr6";
			// Initialize client that will be used to send requests. This client only needs
			// to be created
			// once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			
			TableId tableId = TableId.of(projectId, datasetName, tableName);
			Table table = bigquery.getTable(tableId);
			System.out.println(table.getDescription());
			//SELECT table_name,column_name,description FROM `my-int-cortex-0824.testdata_sap_cdc_processed.INFORMATION_SCHEMA.COLUMN_FIELD_PATHS`
			return 0;
		} catch (BigQueryException e) {
			System.out.println("Table not retrieved. \n" + e.toString());
		}
		return 1;
	}
}
