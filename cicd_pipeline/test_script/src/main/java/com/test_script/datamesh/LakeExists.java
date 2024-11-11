package com.test_script.datamesh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.dataplex.v1.Asset;
import com.google.cloud.dataplex.v1.DataplexServiceClient;
import com.google.cloud.dataplex.v1.Lake;
import com.google.cloud.dataplex.v1.LocationName;
import com.google.cloud.dataplex.v1.Zone;

public class LakeExists {
	public Integer exist(String projectId, String location) {
		
		try {
			/* GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("application_default_credentials.json"));
			 * DataplexServiceSettings dataplexServiceSettings =
			 * DataplexServiceSettings.newBuilder()
			 * .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
			 * .build(); DataplexServiceClient dataplexServiceClient =
			 * DataplexServiceClient.create(dataplexServiceSettings);
			 * 
			 * Map<String, Map<String, Map<String, String>>> dataLake = new HashMap<>();
			 * Map<String, Map<String, String>> dataZone = new HashMap<>(); Map<String,
			 * String> dataAsset = new HashMap<>();*/
			
			DataplexServiceClient dataplexServiceClient = DataplexServiceClient.create();
			String parent = LocationName.of(projectId, location).toString();
			for (Lake element : dataplexServiceClient.listLakes(parent).iterateAll()) {
				System.out.println("Lake: "+ element.getName().split("/")[element.getName().split("/").length-1]);
				//dataLake.put("Lake", dataZone);
				for (Zone zone : dataplexServiceClient.listZones(element.getName()).iterateAll()) {
					System.out.println("Zone: "+ zone.getName().split("/")[zone.getName().split("/").length-1]);
					//dataZone.put("zone", dataAsset);
					for (Asset asset : dataplexServiceClient.listAssets(zone.getName()).iterateAll()) {
						System.out.println("Asset: "+ asset.getName().split("/")[asset.getName().split("/").length-1]);
						//dataAsset.put(location, asset.getName())
					}
				}
			 }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
	   return 0;
}
}
