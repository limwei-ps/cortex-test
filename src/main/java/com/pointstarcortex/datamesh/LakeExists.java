package com.pointstarcortex.datamesh;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dataplex.v1.Asset;
import com.google.cloud.dataplex.v1.DataplexServiceClient;
import com.google.cloud.dataplex.v1.DataplexServiceSettings;
import com.google.cloud.dataplex.v1.Lake;
import com.google.cloud.dataplex.v1.LocationName;
import com.google.cloud.dataplex.v1.Zone;

public class LakeExists {
	public Integer exist() {
		
		try {
			GoogleCredentials credentials = GoogleCredentials.fromStream(
				    new FileInputStream("C:\\Users\\Lim Fang Wei\\Downloads\\others\\eclipse-workspace\\pointstarcortex\\application_default_credentials.json"));
			DataplexServiceSettings dataplexServiceSettings =
				     DataplexServiceSettings.newBuilder()
				         .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
				         .build();
				 DataplexServiceClient dataplexServiceClient =
				     DataplexServiceClient.create(dataplexServiceSettings);
			String parent = LocationName.of("my-int-cortex-0824", "asia-southeast1").toString();
			for (Lake element : dataplexServiceClient.listLakes(parent).iterateAll()) {
				System.out.println(element.getName());
				for (Zone zone : dataplexServiceClient.listZones(element.getName()).iterateAll()) {
					System.out.println(zone.getName());
					for (Asset asset : dataplexServiceClient.listAssets(zone.getName()).iterateAll()) {
						System.out.println(asset.getName());
					}
				}
			 }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return 0;
}
}
