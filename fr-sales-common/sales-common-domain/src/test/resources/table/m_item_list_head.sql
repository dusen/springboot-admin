DROP TABLE IF EXISTS sales-dev6.m_item_list_head CASCADE;
CREATE TABLE sales-dev6.m_item_list_head(
 system_brand_code varchar(10) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 skulist_code varchar(8) NOT NULL,
 item_list_name varchar(120) NOT NULL,
 parent_skulist_code varchar(8),
 color_code varchar(10),
 size_code varchar(10),
 pattern_length_code varchar(10),
 major_category_code varchar(4),
 class_code varchar(4),
 middle_category_code varchar(4),
 small_category_value numeric(5),
 initial_selling_price numeric(20,4),
 regular_item_flag boolean DEFAULT FALSE NOT NULL,
 core_attribute_flag boolean DEFAULT FALSE NOT NULL,
 out_of_stock_judge_quantity numeric(3),
 line_up_type_code varchar(6),
 input_aggregation_type varchar(1),
 skulist_status varchar(1),
 view_item_code varchar(25),
 item_list_hierarchy_code varchar(6),
 year_code varchar(4),
 view_year_type varchar(1),
 season_code varchar(6),
 detail_season_code varchar(6),
 skulist_pattern_code varchar(6),
 set_object_auto_update_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_item_list_head_pk
   PRIMARY KEY(system_brand_code,system_country_code,skulist_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX m_item_list_head_KEY1 ON sales-dev6.m_item_list_head(system_brand_code,system_country_code,color_code,size_code,pattern_length_code,view_item_code,item_list_hierarchy_code);


commit;
