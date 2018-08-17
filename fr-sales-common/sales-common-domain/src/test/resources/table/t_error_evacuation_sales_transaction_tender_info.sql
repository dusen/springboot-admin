DROP TABLE IF EXISTS sales-dev6.t_error_evacuation_sales_transaction_tender_info CASCADE;
CREATE TABLE sales-dev6.t_error_evacuation_sales_transaction_tender_info(
 transaction_id varchar(57) NOT NULL,
 order_sub_number numeric(4) NOT NULL,
 sales_transaction_id varchar(30) NOT NULL,
 tender_group varchar(50) NOT NULL,
 tender_id varchar(50) NOT NULL,
 tender_sub_number numeric(4) NOT NULL,
 sales_transaction_error_id varchar(57),
 discount_value_currency_code varchar(3),
 discount_value numeric(24,4),
 discount_rate numeric(16,4),
 discount_code_id_corporate_id varchar(30),
 coupon_type varchar(6),
 coupon_discount_amount_setting_currency_code varchar(3),
 coupon_discount_amount_setting_value numeric(24,4),
 coupon_min_usage_amount_threshold_currency_code varchar(3),
 coupon_min_usage_amount_threshold_value numeric(24,4),
 coupon_user_id varchar(30),
 card_no varchar(30),
 credit_approval_code varchar(30),
 credit_processing_serial_number varchar(30),
 credit_payment_type varchar(30),
 credit_payment_count numeric(5),
 credit_affiliated_store_number varchar(30),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_error_evacuation_sales_transaction_tender_info_pk
   PRIMARY KEY(transaction_id,order_sub_number,sales_transaction_id,tender_group,tender_id,tender_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX t_error_evacuation_sales_transaction_tender_info_KEY1 ON sales-dev6.t_error_evacuation_sales_transaction_tender_info(sales_transaction_error_id);


commit;
