package com.pointstarcortex.row;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

public class RowExist {

	public Integer exist() {
		try {
			String query = "SELECT * " + "FROM `my-int-cortex-0824.testdata_sap_raw.adr6` WHERE client=" +"\"250\""+ "AND addrnumber ="+"\"0000023058\"";
			// Initialize client that will be used to send requests. 
			// This client only needs to be created once, and can be reused for multiple requests.
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableResult results = bigquery.query(QueryJobConfiguration.of(query));
			for (FieldValueList row : results.iterateAll()) {
				System.out.println(row.get("client").getStringValue());
			}
			return 0;
		} catch (BigQueryException | InterruptedException e) {
			System.out.println("Query not performed \n" + e.toString());
			return 1;
		}
	}
}