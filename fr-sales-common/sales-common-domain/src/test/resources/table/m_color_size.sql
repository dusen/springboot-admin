DROP TABLE IF EXISTS sales-dev6.m_color_size CASCADE;
CREATE TABLE sales-dev6.m_color_size(
 system_brand_code varchar(10) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 difference_type_code varchar(6) NOT NULL,
 difference_id varchar(10) NOT NULL,
 difference_name varchar(120) NOT NULL,
 view_difference_code varchar(10) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_color_size_pk
   PRIMARY KEY(system_brand_code,system_country_code,difference_type_code,difference_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
