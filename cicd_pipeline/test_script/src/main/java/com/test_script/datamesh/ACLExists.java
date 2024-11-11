package com.test_script.datamesh;

import java.io.IOException;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.Table;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.datapolicies.v1.DataPolicy;
import com.google.cloud.bigquery.datapolicies.v1.DataPolicyServiceClient;
import com.google.cloud.bigquery.datapolicies.v1.LocationName;
import com.google.cloud.datacatalog.v1.PolicyTag;
import com.google.cloud.datacatalog.v1.PolicyTagManagerClient;
import com.google.cloud.datacatalog.v1.TaxonomyName;

public class ACLExists {
	public static void main(String[] args) {
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

	     TableId tableId = TableId.of("testdata_sap_reporting", "BalanceSheet");
	     Table table = bigquery.getTable(tableId);
	     Schema schema = table.getDefinition().getSchema();
	     FieldList fields = schema.getFields();
	     for (Object i : fields.toArray()) {
	    	  System.out.println(i.toString().split(", ")[4]);
	    	}
	     
		 
		try {
			PolicyTagManagerClient policyTagManagerClient = PolicyTagManagerClient.create();
			TaxonomyName parent = TaxonomyName.of("my-int-cortex-0824", "asia-southeast1", "4266629357653977615");
			   for (PolicyTag element : policyTagManagerClient.listPolicyTags(parent).iterateAll()) {
			     System.out.println("This is the policy tag under taxonomy" + element.toString());
			}
			   //https://cloud.google.com/java/docs/reference/google-cloud-datacatalog/1.48.0/com.google.cloud.datacatalog.v1.PolicyTagManagerClient#com_google_cloud_datacatalog_v1_PolicyTagManagerClient_listPolicyTagsCallable__
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			  DataPolicyServiceClient dataPolicyServiceClient = DataPolicyServiceClient.create();
			   String parent = LocationName.of("my-int-cortex-0824", "asia-southeast1").toString();
			   for (DataPolicy element : dataPolicyServiceClient.listDataPolicies(parent).iterateAll()) {
			     System.out.println(element.toString());
			   }
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		/* row level security*/
		
}
}
		    /*
		      // -------------------------------
		      // Lookup Data Catalog's Entry referring to the table.
		      // -------------------------------
		      String linkedResource =
		          String.format(
		              "//bigquery.googleapis.com/projects/%s/datasets/test_dataset/tables/test_table",
		              projectId);
		      LookupEntryRequest lookupEntryRequest =
		          LookupEntryRequest.newBuilder().setLinkedResource(linkedResource).build();
		      Entry tableEntry = dataCatalogClient.lookupEntry(lookupEntryRequest);

		      // -------------------------------
		      // Attach a Tag to the table.
		      // -------------------------------
		      TagField sourceValue =
		          TagField.newBuilder().setStringValue("Copied from tlc_yellow_trips_2017").build();
		      TagField numRowsValue = TagField.newBuilder().setDoubleValue(113496874).build();
		      TagField hasPiiValue = TagField.newBuilder().setBoolValue(false).build();
		      TagField piiTypeValue =
		          TagField.newBuilder()
		              .setEnumValue(TagField.EnumValue.newBuilder().setDisplayName("NONE").build())
		              .build();

		      Tag tag =
		          Tag.newBuilder()
		              .setTemplate(tagTemplate.getName())
		              .putFields("source", sourceValue)
		              .putFields("num_rows", numRowsValue)
		              .putFields("has_pii", hasPiiValue)
		              .putFields("pii_type", piiTypeValue)
		              .build();

		      CreateTagRequest createTagRequest =
		          CreateTagRequest.newBuilder().setParent(tableEntry.getName()).setTag(tag).build();

		      dataCatalogClient.createTag(createTagRequest);
		      System.out.printf("Tag created successfully");*/

