DROP TABLE IF EXISTS sales-dev6.m_general_delete_setting CASCADE;
CREATE TABLE sales-dev6.m_general_delete_setting(
 processing_target_type varchar(10) NOT NULL,
 processing_no numeric(10) NOT NULL,
 delete_target_table varchar(100) NOT NULL,
 saved_days numeric(10) NOT NULL,
 date_item varchar(100) NOT NULL,
 system_brand_code varchar(4),
 system_country_code varchar(3),
 store_code varchar(10),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_general_delete_setting_pk
   PRIMARY KEY(processing_target_type,processing_no)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
