package com.test_script.constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {
	//This is the list of System Variables
    //Declared as 'public', so that it can be used in other classes of this project
    //Declared as 'static', so that we do not need to instantiate a class object
    //Declared as 'final', so that the value of this variable cannot be changed
	
	public static final JSONObject jsonObject = Config.createObject();
	public static final String sourceProjectId = Config.sourceProjectId(jsonObject);
	public static final String targetProjectId = Config.targetProjectId(jsonObject);
	public static final String raw_dataSet = Config.raw(jsonObject);
	public static final String processed_dataSet = Config.CDC(jsonObject);
	public static final String CONFIGPATH = "../../config/config_development.json";
	public static final String RAWBQCONFIGPATH = "../../src/SAP/SAP_CDC/config/ecc/annotations/raw";
	public static final String CDCBQCONFIGPATH = "../../src/SAP/SAP_CDC/config/ecc/annotations/cdc";
	public static final String location = "asia-southeast1";
	
	public static final HashMap<String, HashMap<String, String>> bqRawTable = Config.bqTable();
	public static final HashMap<String, HashMap<String, String>> bqCDCTable = Config.bqTable();
	
	public static final List<String> bqCDCAnnotationTable = Config.bqAnnotationTable(CDCBQCONFIGPATH);
	
	public static final String bucketName = "cortex-data-foundation-dag";
	
	private static JSONObject createObject() {
		try {
			JSONParser parser = new JSONParser();
			Reader reader = new FileReader(CONFIGPATH);
			Object jsonObj = parser.parse(reader);
			JSONObject jsonObject = (JSONObject) jsonObj;
			return jsonObject;
		} catch (ParseException | IOException e) {
			System.out.println("JSON File not found \n" + e.toString());
		}
		return null;
	}
	
	private static HashMap<String, HashMap<String, String>> bqTable(){
			try {
				Boolean state = false;
				Integer tableState = 0; 
				 File myObj = new File("../../src/SAP/SAP_CDC/cdc_settings.yaml");
				 Scanner myReader;
				 myReader = new Scanner(myObj);
				 String lastBase = null;
				 
				 HashMap<String, HashMap<String, String>> tableEntries = new HashMap<>();
				 HashMap<String, String> innerMap = new HashMap<>();
				 
				 while (myReader.hasNextLine()) {
					 String data = myReader.nextLine();
						 
						 if (state == true && !data.contains("{% endif %}")) { 
							 //System.out.println(data);
							 if (data.contains("base_table")) {
								 //System.out.println(data.split(":")[1]);
								 tableEntries.put(lastBase, (HashMap) innerMap.clone());
								 innerMap.clear();
								 lastBase = data.split(":")[1];
							} else if (data.contains("load_frequency")) {
								//System.out.println(data.split(":")[1]); 
							innerMap.put("load_frequency",data.split(":")[1].trim()); 
							}else if (data.contains("partition_type")){
							//System.out.println(data.split(":")[0]); 
							innerMap.put("partition",data.trim()); 
							}else if (data.contains("cluster_details")) {
								//System.out.println(data.split(":")[data.split(":").length - 1].substring(0, data.split(":")[data.split(":").length - 1].length() - 1));
							innerMap.put("cluster", data.split(":")[data.split(":").length -1].substring(2, data.split(":")[data.split(":").length - 1].length() - 2).trim()); }
							}
						
						  if (data.contains("{% if sql_flavour.upper() == 'ECC' %}")) { 
							  state = true;
						  }

				 }
				 myReader.close();
				 return  tableEntries;
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			} 
		return null;
	}
	
	private static List<String> bqAnnotationTable(String rawBqConfigPath) {
		File folder = new File(rawBqConfigPath);
		File[] listOfFiles = folder.listFiles();
		
		List<String> tableList = new ArrayList<>();
		
		if(listOfFiles != null) {
		 for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		    tableList.add(listOfFiles[i].getName().split("\\.")[0]);
		  } 
		 }
		}
		return tableList;
	}
	
	private static String sourceProjectId(JSONObject jsonObject){
		String projectId = (String) jsonObject.get("projectIdSource");
		return projectId;
	};
	
	private static String targetProjectId(JSONObject jsonObject){
		String projectId = (String) jsonObject.get("projectIdTarget");
		return projectId;
	};
	
	private static String raw(JSONObject jsonObject){
		JSONObject sap = (JSONObject) jsonObject.get("SAP");
		JSONObject datasets = (JSONObject) sap.get("datasets");
		String raw_dataset = (String) datasets.get("raw");
		
		return raw_dataset;
	};
	
	private static String CDC(JSONObject jsonObject){
		JSONObject sap = (JSONObject) jsonObject.get("SAP");
		JSONObject datasets = (JSONObject) sap.get("datasets");
		String cdc_dataset = (String) datasets.get("cdc");
		
		return cdc_dataset;
	};
	 
}
