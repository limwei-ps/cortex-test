package com.test_script.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.test_script.constant.Config;

//@SpringBootTest(classes = AppConfig.class) //(classes = {DatasetExist.class}) //(classes = AppConfig.class)
//@ExtendWith(MockitoExtension.class)
public class DatasetExistTest {
	//@Autowired
	//private DatasetExist dataset_exist;

	//@Mock
	private DatasetExist dataset_exist = new DatasetExist();
	
	Integer indicator = 0; 
	
	@Test 
	public void testRawDataSetExist() {
		//DatasetExist dataset_exist = mock(DatasetExist.class);
		//when(dataset_exist.exist()).thenReturn(1);
		String raw_dataSet = Config.raw_dataSet;
		String project_id = Config.sourceProjectId; 
		assertEquals(indicator, dataset_exist.exist(project_id, raw_dataSet));
	}
	
	@Test 
	public void testCDCDataSetExist() {
		//DatasetExist dataset_exist = mock(DatasetExist.class);
		//when(dataset_exist.exist()).thenReturn(1);
		String cdc_dataSet = Config.processed_dataSet;
		String project_id = Config.sourceProjectId; 
		assertEquals(indicator, dataset_exist.exist(project_id, cdc_dataSet));
	}
}
