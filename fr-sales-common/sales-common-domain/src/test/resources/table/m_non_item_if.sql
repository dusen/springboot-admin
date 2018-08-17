DROP TABLE IF EXISTS public.m_non_item_if CASCADE;
CREATE TABLE public.m_non_item_if(
 lot_num varchar(22) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 non_item_code varchar(25) NOT NULL,
 apply_start_date varchar(8) NOT NULL,
 apply_end_date varchar(8),
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
  CONSTRAINT m_non_item_if_pk
   PRIMARY KEY(lot_num,system_country_code,system_brand_code,non_item_code,apply_start_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
