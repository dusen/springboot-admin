DROP TABLE IF EXISTS sales-dev6.t_store_sku_result CASCADE;
CREATE TABLE sales-dev6.t_store_sku_result(
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 skulist_code varchar(8) NOT NULL,
 gather_code varchar(25),
 sku_code varchar(25),
 system_brand_code varchar(4),
 system_country_code varchar(3),
 item_name varchar(250),
 color_code varchar(10),
 view_color_code varchar(10),
 color_name varchar(120),
 size_code varchar(10),
 view_size_code varchar(10),
 size_name varchar(120),
 pattern_length_code varchar(10),
 view_pattern_length_code varchar(10),
 core_attribute_type boolean DEFAULT FALSE NOT NULL,
 season_code varchar(6),
 major_category_code varchar(4),
 g_department_code varchar(6),
 g_department_name varchar(120),
 input_aggregation_type varchar(1),
 initial_selling_price numeric(24,4),
 initial_selling_price_currency_code varchar(3),
 new_retail_price numeric(24,4),
 new_retail_price_currency_code varchar(3),
 selling_price numeric(24,4),
 selling_price_currency_code varchar(3),
 single_promotion_price numeric(24,4),
 single_promotion_price_currency_code varchar(3),
 previous_day_stock_quantity numeric(16),
 current_stock_quantity numeric(16),
 delivery_expected numeric(16),
 sales_amount_code varchar(1),
 sales_amount numeric(24,4),
 sales_amount_currency_code varchar(3),
 sales_quantity_code varchar(1),
 sales_quantity numeric(16,4),
 create_user_id varchar(30),
 create_datetime timestamptz,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_store_sku_result_pk
   PRIMARY KEY(store_code,business_date,skulist_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
