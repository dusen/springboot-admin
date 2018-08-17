DROP TABLE IF EXISTS sales-dev6.t_store_hourly_two_digit_category_result CASCADE;
CREATE TABLE sales-dev6.t_store_hourly_two_digit_category_result(
 business_code numeric(3) NOT NULL,
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 time_local varchar(4) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10),
 eai_update_datetime varchar(18),
 eai_update_type varchar(1),
 eai_send_status varchar(1),
 eai_send_datetime varchar(10),
 tran_create_date varchar(8),
 time_utc varchar(4),
 sales_hour numeric(2),
 sales_result_amount_of_money numeric(24,4),
 sales_quantity numeric(16,4),
 number_of_customers numeric(20),
 sales_discount_amount numeric(24,4),
 sales_return_amount numeric(24,4),
 non_item_sales_amount numeric(24,4),
 major_category_code varchar(4),
 class_code varchar(4),
 middle_category_code varchar(4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_store_hourly_two_digit_category_result_pk
   PRIMARY KEY(store_code,business_date,time_local,tran_create_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
