DROP TABLE IF EXISTS sales-dev6.t_open_log CASCADE;
CREATE TABLE sales-dev6.t_open_log(
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 cash_register_no numeric(3) NOT NULL,
 payoff_flag boolean DEFAULT FALSE NOT NULL,
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_open_log_pk
   PRIMARY KEY(store_code,business_date,cash_register_no)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
