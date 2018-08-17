DROP TABLE IF EXISTS public.m_common_code_master CASCADE;
CREATE TABLE public.m_common_code_master(
 type_id varchar(50) NOT NULL,
 type_value varchar(50) NOT NULL,
 display_order numeric(5) DEFAULT 0 NOT NULL,
 name_1 varchar(120) NOT NULL,
 name_2 varchar(120),
 name_3 varchar(120),
 name_4 varchar(120),
 name_5 varchar(120),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_common_code_master_pk
   PRIMARY KEY(type_id,type_value)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

 DROP TABLE IF EXISTS public.m_trans_store_code CASCADE;
CREATE TABLE public.m_trans_store_code(
 store_code varchar(10) NOT NULL,
 store_name varchar(150) NOT NULL,
 view_store_code varchar(20),
 integrate_db_store_code varchar(20),
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 business_name varchar(120) NOT NULL,
 block_code varchar(10) NOT NULL,
 block_name varchar(120) NOT NULL,
 area_code varchar(10) NOT NULL,
 area_name varchar(120) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 country_name varchar(120) NOT NULL,
 batch_region numeric(2) NOT NULL,
 integrate_db_business_code varchar(3),
 store_device_rmvl_date timestamptz,
 send_target_store_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_trans_store_code_pk
   PRIMARY KEY(store_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_alteration_history_pay_off_data CASCADE;
CREATE TABLE public.t_alteration_history_pay_off_data(
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