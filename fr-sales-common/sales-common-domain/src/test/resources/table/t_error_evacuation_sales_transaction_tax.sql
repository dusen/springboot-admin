DROP TABLE IF EXISTS sales-dev6.t_error_evacuation_sales_transaction_tax CASCADE;
CREATE TABLE sales-dev6.t_error_evacuation_sales_transaction_tax(
 transaction_id varchar(57) NOT NULL,
 order_sub_number numeric(4) NOT NULL,
 sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 tax_group varchar(10) NOT NULL,
 sales_transaction_error_id varchar(57),
 tax_sub_number numeric(4) NOT NULL,
 tax_amount_sign varchar(1),
 tax_amount_currency_code varchar(3),
 tax_amount_value numeric(24,4),
 tax_rate numeric(16,4),
 tax_name varchar(120),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_error_evacuation_sales_transaction_tax_pk
   PRIMARY KEY(transaction_id,order_sub_number,sales_transaction_id,detail_sub_number,tax_group,tax_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX t_error_evacuation_sales_transaction_tax_KEY1 ON sales-dev6.t_error_evacuation_sales_transaction_tax(sales_transaction_error_id);


commit;
