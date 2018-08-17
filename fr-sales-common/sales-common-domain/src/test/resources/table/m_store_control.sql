DROP TABLE IF EXISTS sales-dev6.m_store_control CASCADE;
CREATE TABLE sales-dev6.m_store_control(
 store_code varchar(10) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10),
 system_country_code varchar(3) NOT NULL,
 fc_store_type varchar(1),
 business_date varchar(8),
 closing_store_time varchar(6),
 language_type varchar(2),
 business_end_of_date varchar(8),
 tax_rate numeric(16,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_store_control_pk
   PRIMARY KEY(store_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
