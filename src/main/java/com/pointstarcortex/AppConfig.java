package com.pointstarcortex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pointstarcortex.dag.BlobExist;
import com.pointstarcortex.datamesh.ACLExists;
import com.pointstarcortex.datamesh.CatalogExists;
import com.pointstarcortex.datamesh.DescriptionExists;
import com.pointstarcortex.datamesh.LakeExists;
import com.pointstarcortex.dataset.DatasetExist;
import com.pointstarcortex.row.RowExist;
import com.pointstarcortex.table.TableExist;
import com.pointstarcortex.table.TableSchema;

@Configuration
public class AppConfig {
	
    @Bean
    public DatasetExist datasetExist() {
        return new DatasetExist();
    }
    
    @Bean
    public TableExist tableExist() {
        return new TableExist();
    }

    @Bean
    public RowExist rowExist() {
        return new RowExist();
    }
   
    @Bean
    public TableSchema tableSchema() {
        return new TableSchema();
    }
    
    @Bean
    public BlobExist BlobExist() {
        return new BlobExist();
    }
    @Bean
    public ACLExists ACLExists() {
        return new ACLExists();
    }
    @Bean
    public CatalogExists CatalogExists() {
        return new CatalogExists();
    }
    @Bean
    public DescriptionExists DescriptionExists() {
        return new DescriptionExists();
    }
    @Bean
    public LakeExists LakeExists() {
        return new LakeExists();
    }
}
