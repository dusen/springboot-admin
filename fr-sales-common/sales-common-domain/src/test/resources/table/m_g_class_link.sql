DROP TABLE IF EXISTS sales-dev6.m_g_class_link CASCADE;
CREATE TABLE sales-dev6.m_g_class_link(
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 major_category_code varchar(4) NOT NULL,
 class_code varchar(4) NOT NULL,
 middle_category_code varchar(4) NOT NULL,
 g_department_code varchar(6) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_g_class_link_pk
   PRIMARY KEY(system_country_code,system_brand_code,major_category_code,class_code,middle_category_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
