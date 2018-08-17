DROP TABLE IF EXISTS sales-dev6.t_alteration_history_sales_transaction_discount CASCADE;
CREATE TABLE sales-dev6.t_alteration_history_sales_transaction_discount(
 transaction_id varchar(57) NOT NULL,
 order_sub_number numeric(4) NOT NULL,
 sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 promotion_type varchar(4) NOT NULL,
 promotion_no varchar(10) NOT NULL,
 store_discount_type varchar(2) NOT NULL,
 history_type numeric(1) NOT NULL,
 sales_transaction_error_id varchar(57),
 item_discount_sub_number numeric(4),
 quantity_code varchar(1),
 discount_quantity numeric(2),
 discount_amount_tax_excluded_currency_code varchar(3),
 discount_amount_tax_excluded numeric(24,4),
 discount_amount_tax_included_currency_code varchar(3),
 discount_amount_tax_included numeric(24,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_alteration_history_sales_transaction_discount_pk
   PRIMARY KEY(transaction_id,order_sub_number,sales_transaction_id,detail_sub_number,promotion_type,promotion_no,store_discount_type,history_type)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX t_alteration_history_sales_transaction_discount_KEY1 ON sales-dev6.t_alteration_history_sales_transaction_discount(sales_transaction_error_id);


commit;
