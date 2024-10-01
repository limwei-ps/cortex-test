SELECT
  vbpa.MANDT AS Client_MANDT,
  vbpa.VBELN AS SalesDocument_VBELN,
  vbpa.POSNR AS Item_POSNR,
  vbpa.PARVW AS PartnerFunction_PARVW,
  vbpa.KUNNR AS Customer_KUNNR,
  vbpa.LIFNR AS Vendor_LIFNR,
  vbpa.PERNR AS PersonnelNumber_PERNR,
  vbpa.PARNR AS NumberOfContactPerson_PARNR,
  vbpa.ADRNR AS Address_ADRNR,
  vbpa.ABLAD AS UnloadingPoint_ABLAD,
  vbpa.LAND1 AS Country_LAND1,
  vbpa.ADRDA AS AddressIndicator_ADRDA,
  vbpa.XCPDK AS IndicatorOneTimeAccount_XCPDK,
  vbpa.HITYP AS CustomerHierarchyType_HITYP,
  vbpa.PRFRE AS RelevantForPriceDeterminationID_PRFRE,
  vbpa.BOKRE AS IndicatorCustomerToReceiveRebates_BOKRE,
  vbpa.HISTUNR AS LevelNumberWithinHierarchy_HISTUNR,
  vbpa.KNREF AS CustomerDescriptionOfPartner_KNREF,
  vbpa.LZONE AS TransportationZone_LZONE,
  vbpa.HZUOR AS AssignmentToHierarchy_HZUOR,
  vbpa.STCEG AS VATRegistrationNumber_STCEG,
  vbpa.PARVW_FF AS IndicatorFurtherPartners_PARVW_FF,
  vbpa.ADRNP AS PersonNumber_ADRNP,
  vbpa.KALE AS MaintainAppointmentsInCalendar_KALE,
  vbpa.DATAAGING AS DataFilterValueForDataAging_DATAAGING,
  vbpa.DUMMY_SDDOCPARTNER_INCL_EEW_PS AS DummyFunctionInLength1_DUMMY_SDDOCPARTNER_INCL_EEW_PS
FROM
  `{{ project_id_src }}.{{ dataset_cdc_processed_s4 }}.vbpa` AS vbpa