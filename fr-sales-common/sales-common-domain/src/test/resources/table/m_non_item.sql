DROP TABLE IF EXISTS sales-dev6.m_non_item CASCADE;
CREATE TABLE sales-dev6.m_non_item(
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 non_item_code varchar(25) NOT NULL,
 apply_start_date timestamptz NOT NULL,
 apply_end_date timestamptz,
 non_item_type varchar(8),
 pos_item_name varchar(120),
 unit_retail numeric(20,4),
 g_dept_code varchar(6),
 major_category_code numeric(4) NOT NULL,
 tax_rate varchar(6),
 tax_type varchar(10),
 tax_kind varchar(2),
 discount_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_non_item_pk
   PRIMARY KEY(system_country_code,system_brand_code,non_item_code,apply_start_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
