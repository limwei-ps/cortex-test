package com.pointstarcortex.table;

import java.util.ArrayList;
import java.util.List;

import com.google.api.gax.paging.Page;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQuery.TableListOption;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetId;
import com.google.cloud.bigquery.Table;
import com.pointstarcortex.constant.Config;


public class TableExist {

	public Integer exist() {
		String datasetName = Config.dataSet;
		try {
			
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			Dataset dataset = bigquery.getDataset(DatasetId.of(datasetName));
			
			if (dataset != null) {
				List<String> bqTables = new ArrayList<>();
				Page<Table> tables = bigquery.listTables(datasetName, TableListOption.pageSize(100));
				for (Table table : tables.iterateAll()) {
				    System.out.println(table.getTableId().getTable());
				    bqTables.add(table.getTableId().getTable());
				}
				bqTables.removeAll(Config.bqRawTable);
				System.out.println(bqTables);
				return 0;
				
			} else {
				System.out.println("Dataset not found.");
				return 1;
			}
			
		} catch (BigQueryException e) {
			System.out.println("Class TableExist crash.\n" + e.toString());
			return 1;
		}
	}
}
