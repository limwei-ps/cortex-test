# BigQuery Connector for SAP
- [Version 2.7](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/all-guides): [includes significant changes including version-specific enhancements](https://cloud.google.com/solutions/sap/docs/bq-connector/whats-new#enhancements)
- [Version 2.0](https://cloud.google.com/solutions/sap/docs/bq-connector/2.0/all-guides)

- For working with connector, go through below steps of 
    - Planning
    - Installation and Configuration
    - Operations Guide
    - Troubleshooting Guide for issues


## Version 2.7
- BigQuery Connector for SAP installs into SAP Landscape Transformation Replication Server (SAP LT Replication Server) and enables near real time replication of SAP data directly into BigQuery.

### Planning Guide 
#### [Overview of BigQuery Connector for SAP](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#overview)
- is a SAP Business Add In (transport files) installed in SAP LT replication server that leverages on its change data capture by following SAP LT Replication Server SDK process.
- is [downloaded](https://bq-slt-connector.appspot.com/bqconnectorforsap) at no cost but requires cloud billing account 
- uses the standard interfaces and capabilities of SAP Landscape Transformation Replication Server and supports all of the data sources that SAP LT Replication Server supports.

##### Data Processing 
- sends the SAP records directly to BigQuery from SAP LT Replication Server without any intermediary data integration layers or tools via BigQuery Streaming API
- conforms to Extract, Load, Transform (ELT) model by appending them to BigQuery table in insert-only mode
- automatically suggests the fields, field names, and data types for the target table based on the source data but can be manually adjusted
- can insert custom enhancements into BigQuery Connector for SAP code
- creates if a target table doesn't already exist in BigQuery

##### Security
- use custom role to control access to BigQuery Connector for SAP in SAP LT Replication Server
- use IAM and BigQuery API Authentication for Google Cloud and BigQuery Access
- communication between BigQuery Connector for SAP and BigQuery uses end-to-end HTTPS communication and SSL

##### Scalability
- BigQuery Connector for SAP uses SAP LT Replication Server scaling and partitioning functions to parallelize the data extraction at scale
- use BigQuery Streaming API to scale data loading

#### [Installation architectures](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#architecture)
- SAP LT Replication Server (SLT) should be as close to the source system as possible to ensure low latency and high throughput between the source SAP system, SAP LT Replication Server, and BigQuery dataset.
- SLT should can be on Google Cloud, on-premise or on another cloud provider

##### [Architecture for SAP data sources on Google Cloud](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#architecture-on-gc)
- Because the SAP LT Replication Server instances are installed on Google Cloud, BigQuery Connector for SAP connects to the BigQuery API endpoint directly, without requiring a Cloud Interconnect or Cloud VPN connection.
##### [Architecture for SAP data sources on-premises or on another cloud provider](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#architecture-off-gc)
- connection from BigQuery Connector for SAP in SAP LT Replication Server to BigQuery is provided by either a Cloud Interconnect connection or a Cloud VPN connection.

##### [Detailed architectural view of the data flow](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#architecture-slt-data-flow)
- [standard architecure of how SAP SLT and BigQuery Connector for SAP works](https://cloud.google.com/solutions/sap/docs/images/bqc4sap-architecture-replication-detail.svg)
- detailed steps on how SLT works with BigQuery Streaming API

#### [Software requirements](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#reqs-software)
##### [SAP software version requirements](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#reqs-software)
- depends on whether SAP LT Replication Server is installed on its own server in a standalone architecture or within the source ABAP application system in an embedded architecture.
- depends if client is using SAP S/4HANA or SAP ECC
- [view detailed specification of ECC](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#ecc)

##### Operating system requirements
- supports any operating system that is supported by SAP LT Replication Server

#### [Supported replication sources](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#supported-sap-features)
- supports most of the commonly used application and database source systems that are supported by SAP LT Replication Server.

##### Supported SAP application sources
- replicate data from the SAP application sources that SAP LT Replication Server supports
- support including: 
    - SAP Business Suite 7
    - S/4HANA
    - SAP applications running on SAP NetWeaver
- not recommended to replicate data from SAP Business Warehouse
- does not support SAP Cloud applications, such as S/4HANA Cloud, SAP Ariba, SAP SuccessFactors

##### Supported data sources
- replicate only transparent or cluster tables
- does not support the replication SAP Core Data Services(CDS) views
- BigQuery is supported starting from SAP BusinessObjects Business Intelligence 4.3 as data source

#### [Security](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#security)
- implement security controls in SAP LT Replication Server, the SAP LT Replication Server host operating system, and in Google Cloud

##### SAP security
- as a part of the transport installation, authorization object ZGOOG_MTID is provided to use standard SAP role-based authorization
- a role can be defined that has administrative access within SAP LT Replication Server in order to configure and run data replication jobs that use the BigQuery Connector for SAP

##### Google Cloud security
###### Google Cloud Identity and Access Management
- IAM service account for authentication and authorization of BigQuery Service Connector for SAP
    - provide roles of BigQuery Data Editor & BigQuery Job User
    - provide service account token creator if running on Google Cloud 
    - provide service account key if running on premise or another cloud platform

###### BigQuery dataset and table access controls
- optional to grant access to dataset and table level with access control on dataset and table to service account used by SLT

###### [VPC Service Control](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#security-gcp-service-controls)
- On Google Cloud, the VPC firewall rules are not applicable to API-based interactions with BigQuery. VPC Service control should be used here

- If SAP workload is running on Google Cloud, VPC service controls can be implemented by defining service perimeters

- If SAP workload is not running on Google Cloud, VPC service controls can be implemented as a part of setting up Private Google Access for on-premises hosts
- [Configure routes to restricted.googleapis.com](https://cloud.google.com/vpc-service-controls/docs/set-up-private-connectivity#configure-routes)
- [On-premise network with restricted with VPC service control](https://cloud.google.com/vpc-service-controls/docs/private-connectivity#on-premises_network_example)


###### [Private Service Connect endpoints](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#security-gcp-psc)
- Not applicable for the BigQuery Connector for SAP that is running on a host outside of Google Cloud
- Use Private Service Connect to set up endpoints in VPC network that allow private consumption of Google-managed services like BigQuery.
- Allows private endpoints that uses internal IP addresses from a VPC CIDR range to be created to access Google APIs and services 
- Use Private Service Connect to create a custom private DNS name for the BigQuery streaming API

#### [Networking](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#networking)
- consider:
    - Bandwidth
    - For SAP workload not running on Google Cloud, consider connection type: Cloud Interconnect or Cloud VPN
    - Latency and its impact on resource consumption on the SAP LT Replication Server host
    - Data volume and its impact on any existing network load

##### Connecting to Google Cloud
- If SAP systems are not running on Google Cloud, connection needs to be established (either Cloud Interconnect(recommended for high-volume, performance-sensitive jobs) or Cloud VPN) and configure private access to the Google Cloud APIs.

- With Cloud VPN, your replication data travels over the public internet, so network contention is less predictable and latencies are typically higher.

- Determine whether the connection has sufficient bandwidth and network speed to support the replication jobs and any other workloads without negatively affecting either

- Advantages of RFC destinations to connect to Google Cloud:
    - [Optional] To use a proxy server to send the HTTP requests to Google Cloud, we recommend that you use [RFC destinations](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#rfc_destinations) defined in transaction SM59 by configuring the proxy server in RFC destination
    - [Not applicable for SAP workload outside Google Cloud] To enable access to Google Cloud APIs and services through Private Service Connect endpoints, create those endpoints in your Google Cloud project and then specify the endpoints in your RFC destinations.
    - [Not applicable for SAP workload outside Google Cloud] Enable HTTP compression for cross-region replications where the CE and bigquery datasets set in different GCE regions

##### Bandwidth
- Cloud Interconnect is recommended but Cloud VPN is also possible 

##### Latency
- Ensure bigquery is as close to SLT and SAP source system as possible 
- Test your latency before migrating your installation to a production environment

##### Network Access Controls
- On the Google Cloud, use VPC Service Controls to restrict traffic as communication via API endpoint are not subjected to firewall rules.
- On the SAP LT Replication Server host, ensure that any firewalls or proxies allow egress traffic from the server to the BigQuery API endpoint. Specifically, SAP LT Replication Server needs to be able to access the following Google Cloud APIs:
    - https://bigquery.googleapis.com
    - https://iamcredentials.googleapis.com
- [Not applicable for SAP workload running outside Google Cloud] If you want to use Private Service Connect endpoints to access the BigQuery streaming API, then you must make sure to configure the Private Service Connect endpoints in the table /GOOG/SERVIC_MAP.

#### [Performance planning](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#performance)
- Generally, bandwidth of connection to Google Cloud and distance between SAP LT Replication Server and BigQuery dataset has greater impact on performance than most other factors

##### [General performance best practices](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#performance-best-practices)
- follow the general best practice here

##### [Additional characteristics that can affect performance](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#additional_characteristics_that_can_affect_performance)
- follow the general best practice here

##### SAP LT Replication Server performance considerations
- Performance and the SAP LT Replication Server installation architecture
    - A standalone architecture, where SAP LT Replication Server is installed on its own dedicated server, usually provides better performance than an embedded architecture, where SAP LT Replication Server is installed on the same server as the source system.
- Performance and the LTRS Advanced Replication Settings
    - This is the settings specified for source table in LTRS transaction code
    - Google Cloud recommends the following specifications in the [Advanced Replication Settings > General Performance section of transaction LTRS](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#perf-reading-types)

##### Network connection performance
- For SAP workload is running on premises or another cloud provider, Google Cloud recommends using a Cloud Interconnect. Cloud VPN can still be utilized but replications have to compete with general traffic
- For SAP workload is running on Google Cloud, Google Cloud recommends locating SAP LT Replication Server and BigQuery dataset in the same region

##### Data transmission
- Generally, send as much data as possible in each HTTP request so as to reduce the overall number of HTTP requests and the related processing overhead.

- In some cases, might need to reduce the amount of data sent, either because of the size of the records in a particular table, or because of hitting a quota cap or other limit in BigQuery via: 
    - Adjust data (the portion size) that SLT sends to BigQuery Connector for SAP.
    - Adjust data (the chunk size) that BigQuery Connector for SAP sends to BigQuery.
    - Adjust the quotas for streaming inserts in your BigQuery project.

###### Adjusting the amount of data sent by SAP LT Replication Server
- SLT sends records from the source system to the BigQuery Connector for SAP in portions. Each portion is handled as a separate load or replication job that consumes server resources until it completes.

- Generally, increasing the SAP LT Replication Server portion size will decrease the number of SAP LT Replication Server processes, as well as the overhead that is associated with them.

**Portion size and chunk size**
- Ideally, set a portion size in SAP LT Replication Server that allows BigQuery Connector for SAP to create the largest chunks possible without exceeding the BigQuery limit on the number of bytes in each HTTP request.
- SLT portions are sized in bytes or as a product of bytes and records. 
- BigQuery Connector for SAP chunks are sized by the number of records that they can contain. The byte size of a chunk varies depending on several factors, including the number of fields in the records and the amount of data each record holds.

- If the SLT portion size is larger than BigQuery Connector for SAP chunk size, then BigQuery Connector for SAP sends multiple chunks for each portion, until all records from the portion are sent.

- If the portion size is smaller than the chunk size, then BigQuery Connector for SAP sends only one chunk per portion. Each chunk contains only the number of records sent in each portion, regardless of the chunk size set in BigQuery Connector for SAP.

**Chunk size in BigQuery Connector for SAP**
- Recommend to use the default chunk size with BigQuery Connector for SAP, which is 10,000 records
- If records in a source table contain very few fields or the fields contain very small size data values, then possible to use a larger chunk size up to the maximum chunk size that BigQuery Connector for SAP allows, which is 50,000 records
- If the number of records in a given chunk resolves to a byte size that exceeds the BigQuery limit on the byte size for HTTP requests, you might receive either a `quotaExceeded` or an `invalid` error which can happen if the records in a source table contain a lot of fields or the fields contain a lot of data
- For error related to chunk size, try reducing the chunk size that is specified in the mass transfer configuration for that table. Alternatively, enable dynamic chunk size for that table to automatically adjust the chunk size. 

- If dynamic chunk size are not enabled, then for SAP source tables like MSEG, ACDOCA, and MATDOC, which can have large records with a lot of fields per record, you might need to specify a chunk size as low as 2,000

**Processing overhead associated with the sending of portions**
Each portion that is sent triggers the following actions, each of which incurs some processing overhead or resource consumption:

1. A collection of changed records in the logging table on the source system are sent to SAP LT Replication Server in a single portion. The changed records are not yet deleted from the logging table.
2. SAP LT Replication Server requests a new access token from Google Cloud.
3. BigQuery Connector for SAP sends an HTTP request to BigQuery to check the structure of the target table.
4. BigQuery Connector for SAP sends the records to BigQuery in as many chunks as are needed to send all of the records that it received in the single portion. Each chunk is sent in a separate HTTP request.
5. BigQuery processes each chunk that it receives.
6. An HTTP OK status code is returned to SAP LT Replication Server for each chunk.
7. After BigQuery receives all of the records, SAP LT Replication Server deletes the sent records from the logging table, which finally frees resources on the source system.

**BigQuery quotas**
- For streaming inserts, BigQuery fixes the size of HTTP requests to 10 MB and the number of records that you can send in a single HTTP request to 50,000.

##### Record compression
- When record compression is enabled, which is the default(to improve replication performance), BigQuery Connector for SAP omits fields that are empty in the source record from the records that are sent to BigQuery. When the record is inserted into BigQuery, the fields that were omitted from the sent data, are initialized with null in the target table.
- When the Send Uncompressed Flag is selected, BigQuery Connector for SAP sends records to BigQuery with all of their fields, including any empty fields. Except for date fields and timestamp fields, the empty fields retain whatever value they were initialized with in the source table.

#### BigQuery replication configurations
- Transaction code used in BigQuery Connector:
    - SM30 - Defines properties for connecting to Google Cloud, which are stored as a record in the custom configuration table /GOOG/CLIENT_KEY. Optionally, when you use RFC destinations to connect to Google Cloud APIs and services, some connection properties are stored in the custom configuration table /GOOG/SERVIC_MAP.
    - LTRC - Defines BigQuery Connector for SAP replication application and mass transfer ID, among other properties.
    - SM59 - Defines RFC destinations that enable connecting to Google Cloud APIs and services like BigQuery and IAM.
    - /GOOG/SLT_SETTINGS - Defines properties for the target BigQuery dataset, table and fields. 

##### Language Support
- BigQuery Connector for SAP only supports replication configurations and error messages in English so EN should be used as logon language 
- BigQuery Connector for SAP supports execution of background jobs running on SLT with all the  languages SLT supports

##### Target table properties 
- can specify settings that apply when BigQuery Connector for SAP creates the target table in BigQuery

###### Default naming options for fields
- configure BigQuery Connector for SAP to create the names for the fields in the target BigQuery table either from the names of the source fields or the labels and descriptions of the source fields
- By default, BigQuery Connector for SAP uses the names of the source fields
- When the `Custom Names` flag is specified, the names that the BigQuery Connector for SAP connector is going to use when it creates the target table are shown in the `External Field Name` column of the field mapping screen

###### Capturing record changes and enabling record counts
-  specify the `Extra Fields Flag` option to capture the type of change in the source table that triggered replication and to be able to query record counts in the BigQuery table for comparison with SAP

###### Table partitioning
- create BigQuery tables that are partitioned by either a timestamp field in the source table, which creates a time-unit column-partitioned table, or by the time at which the records are inserted into BigQuery, which creates an ingestion-time partitioned table.
- enable partitioning by specifying a partition type in the Partition Type field in the /GOOG/BQ_TABLE
- specify the name of the source field in the Partition Field field to use a timestamp from the source table for time-unit column partitioning
- leave Partition Field blank to use a BigQuery insertion time for ingestion-time partitioning

##### Target field properties
- By default, BigQuery Connector for SAP uses the field names and data types in the SAP source table as the field names and data types in the target BigQuery.
- Fields can be customized before target table is created

###### Customizing target field names
- stores settings in the /GOOG/BQ_FIELD configuration table.
- can specify a custom field name by editing the generated name in the `Temporary Field Name column` of the field mapping screen

###### Use a spreadsheet or text file to edit the BigQuery field map
- Before creation of a target BigQuery table, default data types, names, and descriptions of the target fields can be saved to a spreadsheet or text file, so that the values can be edited without requiring access to SLT

###### BigQuery naming convention for fields
- uses only lowercase letters, numbers, and underscores

##### Data type mapping
- Stores settings in the /GOOG/BQ_FIELD configuration table.
- By default, BigQuery Connector for SAP assigns data types to the target BigQuery fields based on the SAP *type kind* or the SAP *data type* of the source SAP field.
- can change the default data type specification to a different BigQuery data type in the `External Data Element` column of the field mapping screen before a table is created

######  Data types that require special handling
- configure replication by using the /GOOG/SLT_SETTINGS transaction
- Boolean
    - SAP uses the data type CHAR, which by default, the BigQuery Connector for SAP maps to the STRING data type in the target BigQuery table.
    - the default data type assignment for boolean fields need to be changed from STRING to BOOLEAN in the field mapping screen.
- Timestamps
    - SAP uses the data types P (packed decimal) or DEC (decimal), which by default, BigQuery Connector for SAP maps to NUMERIC in the target BigQuery table
    - the default data type assignment for timestamp fields need to be changed from NUMERIC to TIMESTAMP or TIMESTAMP (LONG) in the field mapping screen.
- X SAP type kind
    - hexadecimal and is represented by the RAW, RAWSTRING, or LRAW SAP data types. By default, BigQuery Connector for SAP maps these data types to STRING in the source BigQuery table.
    - can configure a source field with the x SAP type kind to map to BYTES instead
    - The X SAP type kind is also sometimes used in SAP to represent integers. In this case, BigQuery Connector for SAP checks the data type of the source field for one of the SAP data types for integers, INT1, INT2, INT4, INT8 and assigns the INTEGER data type in the target BigQuery table.
- y SAP type kind
    - is a byte string and is represented by the RAW, RAWSTRING, or LRAW SAP data types
    - can configure a source field with the y SAP type kind to map to BYTES instead

###### Default data type mapping
- this is the [default data type mapping](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#default_data_type_mapping)

#### [Support lifecycle](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/planning#support-lifecycle)
- Google Cloud supports and maintains the latest major version of BigQuery Connector for SAP for a period of at least 12 months following the publication of a notice of deprecation

### [Installation and Configuration]
uses an SAP LT Replication Server standalone architecture, in which SAP LT Replication Server is installed on a separate server

uses an SAP LT Replication Server embedded architecture, in which SAP LT Replication Server is installed in the SAP source system server.

#### [External Hosts: Install and configure BigQuery Connector for SAP on a host outside of Google Cloud](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc)
- If your SAP LT Replication Server box is managed by SAP through the SAP RISE program, then you follow this guide to install and configure BigQuery Connector for SAP.

##### [Prerequisite](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#prereqs)
- Read planning guide
- Maintenance for the installed SAP software is current and the versions of all of the SAP software are compatible with each other, as documented in the SAP Product Availability Matrix.
- The versions of the SAP software you are using are supported by the BigQuery Connector for SAP, as documented in Software requirements.
- You have the correct SAP licenses that are required to replicate data to any target via the SAP LT Replication Server SDK. For more information about SAP licensing, see SAP Note 2707835.
- SAP LT Replication Server is installed. For information about installing SAP LT Replication Server, see the SAP documentation.
- The RFC or database connection between SAP LT Replication Server and the source system is configured. If necessary, test the RFC connections by using SAP transaction SM59. Test database connections by using SAP transaction DBACOCKPIT.
- Create project & Enable billing

##### [Overview of the installation and configuration process](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#overview)
- If necessary, after validating all appropriate licenses from SAP, follow SAP instructions to install an SAP Landscape Transformation Replication Server.
- If necessary, install the user interface (UI) add-on for SAP NetWeaver. 
- Establish a network connection between your SAP system and Google Cloud.
- Enable the required Google Cloud APIs. 
- Enable private access to the Google Cloud APIs. 
- Create a BigQuery dataset. 
- Set up Google Cloud authentication and authorization.
- Download the BigQuery Connector for SAP installation package.
- Install BigQuery Connector for SAP.
- Create SAP roles and permissions for BigQuery Connector for SAP.
- Configure replication.
- Test replication.
- Validate replication.

##### Establish a network connection to Google Cloud
- Setup either cloud interconnect (recommended) or cloud vpn

##### Enable the required Google Cloud APIs
- Enable bigquery API and IAM service account credentials API

##### Enable private access to the Google Cloud APIs
- Enable private access to the Google Cloud APIs for on-premise hosts
1. Ensure BigQuery and relevant API are turned on
2. Setup HA VPN with on-premise with BGP session
    - 2 separate gateway with one interface each
    - 1 gateway with 2 interface
    - 1 gateway with only 1 interface
3. Or choose cloud interconnect 
4. Add custom static routes to configure next hop as default internet gateway for private.googleapis.com if the default internet gateway route is removed
5. Use Cloud Router Custom Route Advertisements to announce routes for the IP ranges used by the private.googleapis.com (or restricted.googleapis.com if VPC service control is used) domains
6. On-premises network must have DNS zones and records configured so that Google domain names resolve to the set of IP addresses for private.googleapis.com. Or, create Cloud DNS managed private zones and use a Cloud DNS inbound server policy where on-prem will forward to cloud DNS to resolve the addresses. Either one of the method will be able to direct on premise RFC destination setup in SAP SLT to private.googleapis.com 
7. Ensure firewall configuration of on-premises systems allows outbound traffic to private.googleapis.com
8. Use response policy and rules to only direct specific names to google private access VIP addresses form on prem to gcp to resolve them
9. [TBC] Create deny egress rule to block all outbound traffic. Create higher priority allow egress rule to permit to the private.googleapis.com IP address ranges on TCP port 443.
11. [TBC] Look into VPC service control

##### Create a BigQuery dataset
- Create dataset to test for authentication and authorization 

##### [Set up Google Cloud authentication and authorization](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#iam)
Overview:
- Create a service account for JWT based authentication to Google Cloud.
    - [GCP] Grant the service account the IAM role that is required to create tokens with service account token creator role.
    - [GCP] Create a service account key (P12) for SAP.
    - [SLT] Enable JWT signing for the service account on the SAP LT Replication Server host.
- Configure security settings for Google Cloud on the SAP LT Replication Server host.
    - [SLT] Create a new SSF application and enable STRUST node for the SSF application.
    - [SLT] Import the service account key into STRUST.
        - [SLT] For ECC, convert the P12 to PSE key
- Create another service account for authorization to access BigQuery.
    - [GCP] Grant the service account the IAM roles that are required to access BigQuery.
    - [Not applicable where the creation of service account and bigquery dataset are the in same project] Add the BigQuery Connector for SAP service account as a principal to the BigQuery project.
    - [SLT] Set up SSL certificates and HTTPS.
- Create ABAP configurations.
    - [SLT] Create new RFC destinations.
    - [SLT] Specify access settings in /GOOG/CLIENT_KEY.
    - [SLT] Specify RFC destinations in /GOOG/SERVIC_MAP.

##### Download the installation package
- Download [BigQuery Connector for SAP](https://bq-slt-connector.appspot.com/bqconnectorforsap) with cloud billing number

##### Install BigQuery Connector for SAP
- Install BigQuery Connector for SAP by importing the transport files into SAP LT Replication Server

- If you need to install BigQuery Connector for SAP on a host machine where the ABAP SDK for Google Cloud is already installed, then you must contact Cloud Customer Care to get appropriate installation instructions for your setup. Installing BigQuery Connector for SAP without following the instructions from Customer Care can render your ABAP SDK for Google Cloud inoperable.

- Despite using a supported version of SLT, in some cases, while importing the transport files, you might see the error message, `Requests do not match the component version of the target system`. In such cases, you need to re-import the transport files into SAP LT Replication Server and while re-importing, on the Import Transport Request screen, go to the Options tab, and then select the Ignore Invalid Component Version checkbox.

##### Confirm that BigQuery Connector for SAP is ready to configure
- Confirm that BigQuery Connector for SAP Business Add-In (BAdI) implementation is active and that BigQuery Connector for SAP replication applications have entries in the IUUC_REPL_APPL table 
    - Check the BAdI implementation with SE80
    - Check the replication applications SE16

##### [Create SAP roles and authorizations for BigQuery Connector for SAP](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#bqc4sap-define-authorized-sap-roles)
- require standard SAP LT Replication Server authorizations, and access to custom transactions that are provided with BigQuery Connector for SAP:`/GOOG/SLT_SETTINGS` and `/GOOG/REPLIC_VALID`(these 2 are given by default to users)
- access to the custom transaction `/GOOG/LOAD_SIMULATE` to use the Load Simulation tool

###### Create SAP roles and authorizations for viewing BigQuery Connector for SAP settings
- grant read-only access to the custom transaction `/GOOG/SLT_SETT_DISP` to view the BigQuery Connector for SAP settings

##### [Configure Replication](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#slt-setup)
- specify both BigQuery Connector for SAP and SAP LT Replication Server settings.

###### Specify access settings in /GOOG/CLIENT_KEY
- Use transaction SM30 to specify settings for access to BigQuery. BigQuery Connector for SAP stores the settings as a record in the /GOOG/CLIENT_KEY custom configuration table.

###### Configure RFC destinations
- RFC Destination is recommended to be used to connect the BigQuery Connector for SAP to Google Cloud
- Create new RFC destination from sample with SM59
- Create new entries in table `/GOOG/SERVIC_MAP` with newly created RFC destinations
- [Optional] Configure proxy settings
    - Can route communication from BigQuery Connector for SAP through the proxy server used in your SAP landscape
- [Not applicable for SAP workload outside Google Cloud] Enable HTTP compression
- [Not applicable for SAP workload outside Google Cloud]Specify Private Service Connect endpoints

###### Enable token caching
- Makes sure that an access token is reused until the access token expires or is revoked, which in turn reduces the number of HTTP calls made to retrieve new access tokens.

- For SAP workloads that are not running on Google Cloud, the cached access tokens also prevent technical issues that might arise while replicating huge data loads, where several processes of SAP LT Replication Server can simultaneously request for an access token at any given time.

- Clear the cached access token by enterring transaction SE38 and then running the program /GOOG/R_CLEAR_TOKEN_CACHE to get updated IAM access from new token

###### Create an SAP LT Replication Server replication configuration
- Use SAP transaction LTRC to create an SAP LT Replication Server replication configuration.
- The interface and configuration options for SAP LT Replication Server might be slightly different depending on which version you are using.
    - Configure replication in DMIS 2011 SP17, DMIS 2018 SP02, or later
    - Configure replication in DMIS 2011 SP16, DMIS 2018 SP01, or earlier

###### Create a mass transfer configuration for BigQuery
- Use the custom /GOOG/SLT_SETTINGS transaction to configure a mass transfer for BigQuery and specify the table and field mappings.

- Select the initial mass transfer options
    - When you first enter , you select which part of the BigQuery mass transfer configuration you need to edit with `/n/GOOG/SLT_SETTINGS` transaction
- Specify table creation and other general attributes
    - Follow the recommendation [here](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#configure-connection)

###### Specify table attributes
- Specify table attributes in the second section of the /GOOG/SLT_SETTINGS transaction.
- Follow the recommendation [here](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/install-config-off-gc#table-attributes)

###### Customize the default field mapping
- If the source SAP table contains timestamp fields or booleans, change the default data type mapping to accurately reflect the data type in the target BigQuery table.
    - Timestamps. Change the default target data type from NUMERIC to TIMESTAMP or TIMESTAMP (LONG).
    - Booleans. Change the default target data type from STRING to BOOLEAN.
    - Hexadecimals. Change the default target data type from STRING to BYTES.

##### Test Replication
- Test the replication configuration by starting data provisioning from SLT 

##### Validate Replication
- Validate by monitoring logs of SLT or checking resources in BigQuery 
- Or, use `/n/GOOG/REPLIC_VALID` as a provided replication validation tool from BigQuery Connector for SAP

### Operations Guide
- This is for performing operational tasks, such as performance tuning and version updates for the BigQuery Connector for SAP.
- TBC - https://cloud.google.com/solutions/sap/docs/bq-connector/latest/operations

### [TroubleShooting](https://cloud.google.com/solutions/sap/docs/bq-connector/latest/troubleshooting)

## Version 2.0
