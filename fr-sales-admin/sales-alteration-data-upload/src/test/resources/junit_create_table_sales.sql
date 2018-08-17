DROP TABLE IF EXISTS public.t_alteration_exclusion_control CASCADE;
CREATE TABLE public.t_alteration_exclusion_control(
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 cash_register_no numeric(3) NOT NULL,
 data_alteration_type varchar(1) NOT NULL,
 exclusion_valid_time varchar(3),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_alteration_exclusion_control_pk
   PRIMARY KEY(store_code,business_date,cash_register_no,data_alteration_type)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_pay_off_data CASCADE;
CREATE TABLE public.t_pay_off_data(
 store_code varchar(10) NOT NULL,
 payoff_date varchar(8) NOT NULL,
 payoff_type_code varchar(6) NOT NULL,
 payoff_type_sub_number_code varchar(10) NOT NULL,
 cash_register_no numeric(3) NOT NULL,
 payoff_amount_currency_code varchar(3),
 payoff_amount numeric(24,4),
 payoff_quantity numeric(16,4),
 report_output_count numeric(5) DEFAULT 0 NOT NULL,
 processing_flag boolean DEFAULT FALSE NOT NULL,
 integrity_check_type varchar(2) NOT NULL,
 accounting_record_status numeric(1),
 batch_region numeric(2) NOT NULL,
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 regular_time_error_notification_flag boolean DEFAULT FALSE NOT NULL,
 daily_summary_error_notification_flag boolean DEFAULT FALSE NOT NULL,
 advance_received_store_code varchar(10) NOT NULL,
 data_alteration_status varchar(20) NOT NULL,
 close_complete_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_pay_off_data_pk
   PRIMARY KEY(store_code,payoff_date,payoff_type_code,payoff_type_sub_number_code,cash_register_no,advance_received_store_code)
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