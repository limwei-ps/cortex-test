package com.test_script.dag;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;

public class BlobExist {

	public Integer exist(String project_id, String bucketName){
		try {
		// Instantiates a client
		//Storage storage = StorageOptions.getDefaultInstance().getService();
		//String blobName = Config.blobName;
		
		Storage storageGCS = StorageOptions.newBuilder().setProjectId(project_id).build().getService();
	    Page<Blob> blobs = storageGCS.list(bucketName);

	    for (Blob blob : blobs.iterateAll()) {
	      System.out.println(blob.getName());
	    }
	    
		return 0;
		} catch (StorageException e) {
			System.out.println("Something went wrong. \n" + e.toString());
			return 1;
		}
	}
}