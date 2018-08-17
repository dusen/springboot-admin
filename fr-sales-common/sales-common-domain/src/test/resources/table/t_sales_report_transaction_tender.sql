DROP TABLE IF EXISTS sales-dev6.t_sales_report_transaction_tender CASCADE;
CREATE TABLE sales-dev6.t_sales_report_transaction_tender(
 sales_transaction_id varchar(30) NOT NULL,
 tender_group varchar(6) NOT NULL,
 tender_id numeric(6) NOT NULL,
 tender_sub_number numeric(4) NOT NULL,
 payment_sign varchar(1),
 tax_included_payment_amount_currency_code varchar(3),
 tax_included_payment_amount_value numeric(24,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_report_transaction_tender_pk
   PRIMARY KEY(sales_transaction_id,tender_group,tender_id,tender_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
