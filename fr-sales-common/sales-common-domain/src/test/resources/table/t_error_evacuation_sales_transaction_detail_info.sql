DROP TABLE IF EXISTS sales-dev6.t_error_evacuation_sales_transaction_detail_info CASCADE;
CREATE TABLE sales-dev6.t_error_evacuation_sales_transaction_detail_info(
 transaction_id varchar(57) NOT NULL,
 order_sub_number numeric(4) NOT NULL,
 sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 sales_transaction_error_id varchar(57),
 item_detail_sub_number numeric(4),
 key_code varchar(20),
 code_value_1 varchar(25),
 code_value_2 varchar(25),
 code_value_3 varchar(25),
 code_value_4 varchar(25),
 name_1 varchar(250),
 name_2 varchar(250),
 name_3 varchar(250),
 name_4 varchar(250),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_error_evacuation_sales_transaction_detail_info_pk
   PRIMARY KEY(transaction_id,order_sub_number,sales_transaction_id,detail_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX t_error_evacuation_sales_transaction_detail_info_KEY1 ON sales-dev6.t_error_evacuation_sales_transaction_detail_info(sales_transaction_error_id);


commit;
