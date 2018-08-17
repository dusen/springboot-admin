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

DROP TABLE IF EXISTS public.m_business_country_state_setting CASCADE;
CREATE TABLE public.m_business_country_state_setting(
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 state_code varchar(4) NOT NULL,
 store_code varchar(10) NOT NULL,
 code_l1 varchar(50) NOT NULL,
 code_l2 varchar(50) NOT NULL,
 code_l3 varchar(50) NOT NULL,
 code_value varchar(50) NOT NULL,
 variable_type varchar(1) NOT NULL,
 display_order numeric(5) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_business_country_state_setting_pk
   PRIMARY KEY(store_code,code_l1,code_l2,code_l3,code_value)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_open_log CASCADE;
CREATE TABLE public.t_open_log(
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

DROP TABLE IF EXISTS public.t_sales_report_transaction_header CASCADE;
CREATE TABLE public.t_sales_report_transaction_header(
 sales_transaction_id varchar(30) NOT NULL,
 integrated_order_id varchar(27) NOT NULL,
 sales_transaction_sub_number numeric(4) NOT NULL,
 store_code varchar(10) NOT NULL,
 data_creation_date_time timestamptz,
 data_creation_business_date varchar(8),
 sales_record_date varchar(8),
 cash_register_no numeric(3) NOT NULL,
 receipt_no varchar(4) NOT NULL,
 sales_transaction_type varchar(6),
 return_type numeric(1),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 channel_code varchar(10),
 order_status varchar(50),
 order_substatus varchar(50),
 order_status_update_date varchar(8),
 order_status_last_update_date_time timestamptz,
 order_cancelled_date_time timestamptz,
 booking_store_code varchar(10),
 booking_store_system_brand_code varchar(4),
 booking_store_system_business_code varchar(10),
 booking_store_system_country_code varchar(3),
 payment_status varchar(50),
 payment_store_code varchar(10),
 payment_store_system_brand_code varchar(4),
 payment_store_system_business_code varchar(10),
 payment_store_system_country_code varchar(3),
 transfer_out_status varchar(50),
 shipment_status varchar(50),
 shipment_store_code varchar(10),
 shipment_store_system_brand_code varchar(4),
 shipment_store_system_business_code varchar(10),
 shipment_store_system_country_code varchar(3),
 receiving_status varchar(50),
 receipt_store_code varchar(10),
 receipt_store_system_brand_code varchar(4),
 receipt_store_system_business_code varchar(10),
 receipt_store_system_country_code varchar(3),
 customer_id varchar(30),
 customer_type varchar(10),
 order_number_for_store_payment varchar(27),
 advance_received_store_code varchar(10),
 advance_received_store_system_brand_code varchar(4),
 advance_received_store_system_business_code varchar(10),
 advance_received_store_system_country_code varchar(3),
 operator_code varchar(18),
 original_transaction_id varchar(30),
 original_cash_register_no numeric(3),
 original_receipt_no varchar(4),
 deposit_currency_code varchar(3),
 deposit_value numeric(24,4),
 change_currency_code varchar(3),
 change_value numeric(24,4),
 receipt_no_for_credit_card_cancellation varchar(11),
 receipt_no_for_credit_card varchar(30),
 receipt_issued_flag boolean DEFAULT FALSE NOT NULL,
 e_receipt_target_flag boolean DEFAULT FALSE NOT NULL,
 processing_company_code varchar(20),
 employee_sale_flag boolean DEFAULT FALSE NOT NULL,
 consistency_sales_flag boolean DEFAULT FALSE NOT NULL,
 corporate_id varchar(20),
 sales_transaction_discount_flag boolean DEFAULT FALSE NOT NULL,
 sales_transaction_discount_type varchar(1),
 sales_transaction_discount_amount_rate_currency_code varchar(3),
 sales_transaction_discount_amount_rate numeric(24,4),
 token_code varchar(30),
 ims_linkage_flag boolean DEFAULT FALSE NOT NULL,
 ims_linkage_date varchar(8),
 hourly_sales_by_item_summary_flag boolean DEFAULT FALSE NOT NULL,
 hourly_summary_flag boolean DEFAULT FALSE NOT NULL,
 transaction_summary_flag boolean DEFAULT FALSE NOT NULL,
 department_summary_flag boolean DEFAULT FALSE NOT NULL,
 inventory_updated_flag boolean DEFAULT FALSE NOT NULL,
 heming_linkage_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_report_transaction_header_pk
   PRIMARY KEY(sales_transaction_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX t_sales_report_transaction_header_KEY1 ON public.t_sales_report_transaction_header(ims_linkage_flag,ims_linkage_date,store_code,update_datetime);
 CREATE INDEX t_sales_report_transaction_header_KEY2 ON public.t_sales_report_transaction_header(transaction_summary_flag,ims_linkage_date,store_code,cash_register_no,update_datetime);

DROP TABLE IF EXISTS public.m_tender CASCADE;
CREATE TABLE public.m_tender(
 store_code varchar(10) NOT NULL,
 tender_name varchar(120),
 receipt_tender_name varchar(45),
 valid_date timestamptz,
 currency_code varchar(3),
 kid varchar(3),
 change_flag boolean DEFAULT FALSE NOT NULL,
 current_deposit_flag boolean DEFAULT FALSE NOT NULL,
 credit_company_code varchar(3),
 system_country_code varchar(3),
 system_business_code varchar(10),
 system_brand_code varchar(4),
 ims_tender_id numeric(6) NOT NULL,
 ims_tender_group varchar(6),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_tender_pk
   PRIMARY KEY(store_code,ims_tender_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_pile_up_pay_off_data CASCADE;
CREATE TABLE public.t_pile_up_pay_off_data(
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
