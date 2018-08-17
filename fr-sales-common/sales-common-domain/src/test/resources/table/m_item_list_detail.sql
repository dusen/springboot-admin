DROP TABLE IF EXISTS sales-dev6.m_item_list_detail CASCADE;
CREATE TABLE sales-dev6.m_item_list_detail(
 system_brand_code varchar(10) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 skulist_code varchar(8) NOT NULL,
 item_code varchar(25) NOT NULL,
 item_level numeric(1),
 inventory_management_level numeric(1),
 pack_ind varchar(1),
 main_item_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_item_list_detail_pk
   PRIMARY KEY(system_brand_code,system_country_code,skulist_code,item_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
