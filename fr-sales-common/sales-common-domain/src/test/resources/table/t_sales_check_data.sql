DROP TABLE IF EXISTS sales-dev6.t_sales_check_data CASCADE;
CREATE TABLE sales-dev6.t_sales_check_data(
 lot_number varchar(22) NOT NULL,
 batch_region numeric(2),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 store_code varchar(10),
 business_date varchar(8),
 tran_create_date varchar(8),
 sales_hour numeric(2),
 sales_result_amount_of_money numeric(24,4),
 sales_quantity numeric(16,4),
 number_of_customers numeric(20),
 sales_discount_amount numeric(16,4),
 sales_return_amount numeric(24,4),
 non_item_sales_amount numeric(24,4),
 sel_update_datetime timestamptz,
 bih_status varchar(1),
 bih_transaction_id varchar(30),
 major_category_code varchar(4),
 class_code varchar(4),
 middle_category_code varchar(4),
 create_user_id varchar(30),
 create_datetime timestamptz,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_check_data_pk
   PRIMARY KEY(lot_number,middle_category_code,system_brand_code,system_business_code,store_code,business_date,tran_create_date,sales_hour,major_category_code,class_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
