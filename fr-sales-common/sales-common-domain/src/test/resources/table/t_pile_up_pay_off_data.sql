DROP TABLE IF EXISTS sales-dev6.t_pile_up_pay_off_data CASCADE;
CREATE TABLE sales-dev6.t_pile_up_pay_off_data(
 store_code varchar(10) NOT NULL,
 payoff_date varchar(8) NOT NULL,
 payoff_type_code varchar(6) NOT NULL,
 payoff_type_sub_number_code varchar(10) NOT NULL,
 cash_register_no numeric(3) NOT NULL,
 payoff_amount_currency_code varchar(3),
 payoff_amount numeric(24,4),
 payoff_quantity numeric(16,4),
 payoff_processing_flag boolean DEFAULT FALSE NOT NULL,
 batch_region numeric(2) NOT NULL,
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 advance_received_store_code varchar(10) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_pile_up_pay_off_data_pk
   PRIMARY KEY(store_code,payoff_date,payoff_type_code,payoff_type_sub_number_code,cash_register_no,advance_received_store_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
