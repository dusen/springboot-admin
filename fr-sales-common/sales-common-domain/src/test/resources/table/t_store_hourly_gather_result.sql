DROP TABLE IF EXISTS sales-dev6.t_store_hourly_gather_result CASCADE;
CREATE TABLE sales-dev6.t_store_hourly_gather_result(
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 skulist_code varchar(8) NOT NULL,
 time_local varchar(4) NOT NULL,
 transaction_create_date varchar(8),
 time_utc varchar(4),
 sales_hour numeric(2),
 sales_quantity_code varchar(1),
 sales_quantity numeric(16,4),
 create_user_id varchar(30),
 create_datetime timestamptz,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_store_hourly_gather_result_pk
   PRIMARY KEY(store_code,business_date,skulist_code,time_local)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
