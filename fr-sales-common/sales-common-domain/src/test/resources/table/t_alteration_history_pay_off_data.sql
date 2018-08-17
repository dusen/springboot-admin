DROP TABLE IF EXISTS sales-dev6.t_alteration_history_pay_off_data CASCADE;
CREATE TABLE sales-dev6.t_alteration_history_pay_off_data(
 store_code varchar(10) NOT NULL,
 payoff_date varchar(8) NOT NULL,
 payoff_type_code varchar(6) NOT NULL,
 payoff_type_sub_number_code varchar(10) NOT NULL,
 cash_register_no numeric(3) NOT NULL,
 history_type numeric(1) NOT NULL,
 correction_history_sub_number numeric(10) NOT NULL,
 payoff_type_name varchar(120),
 payoff_type_sub_number_name varchar(120),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 payoff_amount numeric(24,4),
 payoff_quantity numeric(16,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_alteration_history_pay_off_data_pk
   PRIMARY KEY(store_code,payoff_date,payoff_type_code,payoff_type_sub_number_code,cash_register_no,history_type,correction_history_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
