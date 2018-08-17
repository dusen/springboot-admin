DROP TABLE IF EXISTS sales-dev6.t_sales_transaction_history CASCADE;
CREATE TABLE sales-dev6.t_sales_transaction_history(
 transaction_id varchar(57) NOT NULL,
 sales_transaction_data text,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_transaction_history_pk
   PRIMARY KEY(transaction_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
