DROP TABLE IF EXISTS sales-dev6.m_store_general_purpose CASCADE;
CREATE TABLE sales-dev6.m_store_general_purpose(
 general_purpose_type varchar(20) NOT NULL,
 store_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 code varchar(50) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_store_general_purpose_pk
   PRIMARY KEY(general_purpose_type,store_code,system_country_code,system_brand_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
