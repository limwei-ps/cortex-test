package com.pointstarcortex.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
	//This is the list of System Variables
    //Declared as 'public', so that it can be used in other classes of this project
    //Declared as 'static', so that we do not need to instantiate a class object
    //Declared as 'final', so that the value of this variable cannot be changed
	public static final String projectId = "my-int-cortex-0824";
	public static final String dataSet = "testdata_sap_raw";
	public static final List<String> bqRawTable = new ArrayList<String>(Arrays.asList("M", "W", "E", "K", "T"));
	public static final String blobName = "dags/cdc_my-int-cortex-0824_testdata_sap_raw_adr6.py";
	
	
	private Config(){ };
	 
}
