DROP TABLE IF EXISTS sales-dev6.t_sales_transaction_total_amount CASCADE;
CREATE TABLE sales-dev6.t_sales_transaction_total_amount(
 order_sub_number numeric(4) NOT NULL,
 sales_transaction_id varchar(30) NOT NULL,
 total_type varchar(50) NOT NULL,
 total_amount_sub_number numeric(4) NOT NULL,
 total_amount_tax_excluded_currency_code varchar(3),
 total_amount_tax_excluded_value numeric(24,4),
 total_amount_tax_included_currency_code varchar(3),
 total_amount_tax_included_value numeric(24,4),
 tax_rate numeric(16,4),
 sales_transaction_information_1 varchar(250),
 sales_transaction_information_2 varchar(250),
 sales_transaction_information_3 varchar(250),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_transaction_total_amount_pk
   PRIMARY KEY(order_sub_number,sales_transaction_id,total_type)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
