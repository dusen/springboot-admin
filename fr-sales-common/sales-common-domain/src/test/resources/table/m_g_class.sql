DROP TABLE IF EXISTS sales-dev6.m_g_class CASCADE;
CREATE TABLE sales-dev6.m_g_class(
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 g_department_code varchar(6) NOT NULL,
 g_department_name varchar(120) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_g_class_pk
   PRIMARY KEY(system_country_code,system_brand_code,g_department_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
