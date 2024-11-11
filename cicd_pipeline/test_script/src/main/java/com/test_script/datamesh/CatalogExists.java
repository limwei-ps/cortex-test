package com.test_script.datamesh;

import java.io.IOException;

import com.google.cloud.datacatalog.v1.DataCatalogClient;
import com.google.cloud.datacatalog.v1.SearchCatalogRequest;
import com.google.cloud.datacatalog.v1.SearchCatalogResult;
import com.google.cloud.datacatalog.v1.TagTemplate;
import com.google.cloud.datacatalog.v1.TagTemplateName;

public class CatalogExists {
	
	public static void main(String[] args) {
		
	String projectId = "my-int-cortex-0824";
    String tagTemplateId = "line_of_business";
    
    // Currently, Data Catalog stores metadata in the us-central1 region.
    String location = "asia-southeast1";

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (DataCatalogClient dataCatalogClient = DataCatalogClient.create()) {

    String name = TagTemplateName.of(projectId, location, tagTemplateId).toString();
    TagTemplate response = dataCatalogClient.getTagTemplate(name);
    System.out.println(response.toString());
    
    SearchCatalogRequest.Scope scope = SearchCatalogRequest.Scope.newBuilder().addIncludeProjectIds(projectId).build();
    
    String query = "tag:my-int-cortex-0824.line_of_business";
    
    for (SearchCatalogResult element :
        dataCatalogClient.searchCatalog(scope, query).iterateAll()) {
       System.out.println(element.toString());
    }
    
    } catch (IOException e) {
		e.printStackTrace();
	}
}
}