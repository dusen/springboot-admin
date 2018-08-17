DROP TABLE IF EXISTS sales-dev6.m_salesreport_item_setting CASCADE;
CREATE TABLE sales-dev6.m_salesreport_item_setting(
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 category varchar(50) NOT NULL,
 output_line numeric(3) NOT NULL,
 output_column numeric(3) NOT NULL,
 summary_type numeric(1) NOT NULL,
 summary_code varchar(50),
 name1 varchar(100),
 name2 varchar(100),
 count_output_flag boolean DEFAULT FALSE NOT NULL,
 amount_output_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_salesreport_item_setting_pk
   PRIMARY KEY(system_brand_code,system_business_code,system_country_code,category,output_line,output_column)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
