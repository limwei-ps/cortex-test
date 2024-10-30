package com.pointstarcortex.dag;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.pointstarcortex.constant.Config;

public class BlobExist {

	public Integer exist(){
		try {
		// Instantiates a client
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String blobName = Config.blobName;
		
		String bucketName = "cortex-data-foundation-dag";
	
		Blob cloudStorageObject = storage.get(BlobId.of(bucketName, blobName));
		// this is where the null pointer exception occurs
		if (cloudStorageObject != null) {
			System.out.printf("Object exist");
			return 0;
		}
		} catch (StorageException e) {
			System.out.println("Something went wrong. \n" + e.toString());
		}
		return 1;
	}
}