DROP TABLE IF EXISTS sales-dev6.m_type CASCADE;
CREATE TABLE sales-dev6.m_type(
 system_brand_code varchar(4) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 code_type varchar(4) NOT NULL,
 code varchar(6) NOT NULL,
 code_name varchar(40),
 code_num numeric(4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_type_pk
   PRIMARY KEY(system_brand_code,system_country_code,code_type,code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
