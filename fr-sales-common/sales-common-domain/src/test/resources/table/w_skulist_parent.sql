DROP TABLE IF EXISTS sales-dev6.w_skulist_parent CASCADE;
CREATE TABLE sales-dev6.w_skulist_parent(
 lot_number varchar(22) NOT NULL,
 eai_update_date varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_date varchar(18),
 skulist_code numeric(8) NOT NULL,
 item_list_name varchar(120) NOT NULL,
 create_date timestamptz NOT NULL,
 create_user_id varchar(30) NOT NULL,
 static_ind varchar(1) NOT NULL,
 last_rebuild_date timestamptz NOT NULL,
 user_security_ind varchar(1) NOT NULL,
 tax_prod_goup_ind varchar(1),
 comment_desc varchar(250),
 parent_skulist_code numeric(8),
 color_code varchar(10),
 size_code varchar(10),
 pattern_length_code varchar(10),
 major_category_code numeric(4),
 class_code numeric(4),
 middle_category_code numeric(4),
 initial_selling_price numeric(20,4),
 regular_item_type varchar(1) NOT NULL,
 core_attribute_type varchar(1) NOT NULL,
 out_of_stock_judge_quantity numeric(3),
 line_up_type_code varchar(6),
 input_aggregation_type varchar(1),
 skulist_status varchar(1) NOT NULL,
 view_item_code varchar(25),
 item_list_hierarchy_code varchar(6) NOT NULL,
 year_code varchar(4),
 view_year_type varchar(1),
 season_code varchar(1),
 detail_season_code varchar(2),
 skulist_pattern_code varchar(6) NOT NULL,
 set_object_auto_update_type varchar(1) NOT NULL,
 business_code numeric(10) NOT NULL,
 country_code varchar(3) NOT NULL,
 batch_region numeric(2) NOT NULL,
 uda_value numeric(5),
  CONSTRAINT w_skulist_parent_pk
   PRIMARY KEY(lot_number,business_code,country_code,skulist_code,eai_update_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
