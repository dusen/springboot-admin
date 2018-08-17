DROP TABLE IF EXISTS sales-dev6.t_sales_transaction_error_detail CASCADE;
CREATE TABLE sales-dev6.t_sales_transaction_error_detail(
 sales_transaction_error_id varchar(57) NOT NULL,
 integrated_order_id varchar(27),
 sales_transaction_id varchar(30),
 store_code varchar(10),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 error_type varchar(10),
 regular_time_error_notification_flag boolean DEFAULT FALSE NOT NULL,
 daily_summary_error_notification_flag boolean DEFAULT FALSE NOT NULL,
 data_alteration_status_type varchar(1),
 error_occurred_list varchar(50),
 key_info_1 varchar(40),
 key_info_2 varchar(40),
 key_info_3 varchar(40),
 key_info_4 varchar(40),
 key_info_5 varchar(40),
 key_info_6 varchar(40),
 key_info_7 varchar(40),
 key_info_8 varchar(40),
 error_item_id_1 varchar(50),
 error_item_value_1 varchar(100),
 error_item_id_2 varchar(50),
 error_item_value_2 varchar(100),
 error_item_id_3 varchar(50),
 error_item_value_3 varchar(100),
 error_item_id_4 varchar(50),
 error_item_value_4 varchar(100),
 error_item_id_5 varchar(50),
 error_item_value_5 varchar(100),
 tax_type varchar(10),
 item_sales_amount numeric(24,4),
 non_item_sales_amount numeric(24,4),
 total_amount numeric(24,4),
 currency_code varchar(3),
 data_creation_date_time timestamptz,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_transaction_error_detail_pk
   PRIMARY KEY(sales_transaction_error_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
