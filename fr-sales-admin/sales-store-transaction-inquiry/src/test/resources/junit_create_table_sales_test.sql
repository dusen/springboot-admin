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

DROP TABLE IF EXISTS public.m_common_code_master CASCADE;
CREATE TABLE public.m_common_code_master(
 type_id varchar(50) NOT NULL,
 type_value varchar(50) NOT NULL,
 display_order numeric(5) NOT NULL,
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

DROP TABLE IF EXISTS public.m_non_item CASCADE;
CREATE TABLE public.m_non_item(
  system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 non_item_code varchar(25) NOT NULL,
 apply_start_date timestamptz NOT NULL,
 apply_end_date timestamptz,
 non_item_type varchar(8),
 pos_item_name varchar(120),
 unit_retail numeric(20,4),
 g_dept_code varchar(6),
 major_category_code numeric(4) NOT NULL,
 tax_rate varchar(6),
 tax_type varchar(10),
 tax_kind varchar(2),
 discount_flag boolean,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_non_item_pk
   PRIMARY KEY(system_country_code,system_brand_code,non_item_code,apply_start_date)
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
 send_target_store_flag boolean,
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

DROP TABLE IF EXISTS public.m_trans_tender CASCADE;
CREATE TABLE public.m_trans_tender(
  store_code varchar(10) NOT NULL,
 tender_id varchar(50) NOT NULL,
 tender_group varchar(50),
 valid_date timestamptz,
 ims_tender_id numeric(6) NOT NULL,
 ims_tender_group varchar(6),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_trans_tender_pk
   PRIMARY KEY(store_code,tender_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
CREATE INDEX m_trans_tender_KEY1 ON m_trans_tender(store_code,ims_tender_id);

DROP TABLE IF EXISTS public.t_sales_report_transaction_detail CASCADE;
CREATE TABLE public.t_sales_report_transaction_detail(
 sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 item_detail_sub_number numeric(4) NOT NULL,
 sales_transaction_type varchar(6),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 l2_item_code varchar(20),
 display_l2_item_code varchar(25),
 l2_product_name varchar(250),
 l3_item_code varchar(25),
 l3_pos_product_name varchar(250),
 product_classification varchar(6),
 non_md_type varchar(6),
 non_md_code varchar(25),
 service_code varchar(50),
 epc_code varchar(24),
 g_department_code varchar(6),
 major_category_code varchar(4),
 quantity_code varchar(1),
 detail_quantity numeric(16,4),
 item_cost_currency_code varchar(3),
 item_cost_value numeric(24,4),
 initial_selling_price_currency_code varchar(3),
 initial_selling_price numeric(24,4),
 bclass_price_currency_code varchar(3),
 bclass_price numeric(24,4),
 new_price_currency_code varchar(3),
 new_price numeric(24,4),
 retail_unit_price_tax_excluded_currency_code varchar(3),
 retail_unit_price_tax_excluded numeric(24,4),
 retail_unit_price_tax_included_currency_code varchar(3),
 retail_unit_price_tax_included numeric(24,4),
 sales_amount_tax_excluded_currency_code varchar(3),
 sales_amount_tax_excluded numeric(24,4),
 sales_amount_tax_included_currency_code varchar(3),
 sales_amount_tax_included numeric(24,4),
 calculation_unavailable_type varchar(10),
 order_status_update_date varchar(8),
 order_status_last_update_date_time timestamptz,
 order_status varchar(50),
 order_substatus varchar(50),
 booking_store_code varchar(10),
 booking_store_system_brand_code varchar(4),
 booking_store_system_business_code varchar(10),
 booking_store_system_country_code varchar(3),
 shipment_store_code varchar(10),
 shipment_store_system_brand_code varchar(4),
 shipment_store_system_business_code varchar(10),
 shipment_store_system_country_code varchar(3),
 receipt_store_code varchar(10),
 receipt_store_system_brand_code varchar(4),
 receipt_store_system_business_code varchar(10),
 receipt_store_system_country_code varchar(3),
 contribution_sales_representative varchar(10),
 customer_id varchar(30),
 bundle_purchase_applicable_quantity numeric(16,4),
 bundle_purchase_applicable_price_currency_code varchar(3),
 bundle_purchase_applicable_price numeric(24,4),
 bundle_purchase_index numeric(3),
 limited_amount_promotion_count numeric(2),
 store_item_discount_type varchar(10),
 store_item_discount_currency_code varchar(3),
 store_item_discount_setting numeric(24,4),
 store_bundle_sale_flag boolean,
 store_bundle_sale_price_currency_code varchar(3),
 store_bundle_sale_price numeric(24,4),
 set_sales_detail_index numeric(3),
 taxation_type varchar(10),
 tax_system_type varchar(10),
 sales_record_date varchar(8),
 linkage_date varchar(8),
 ims_linkage_flag boolean,
 inventory_updated_flag boolean,
 return_complete_flag boolean,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_report_transaction_detail_pk
   PRIMARY KEY(sales_transaction_id,detail_sub_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_sales_report_transaction_discount CASCADE;
CREATE TABLE public.t_sales_report_transaction_discount(
  sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 promotion_type varchar(4) NOT NULL,
 promotion_no varchar(10) NOT NULL,
 store_discount_type varchar(2) NOT NULL,
 item_discount_sub_number numeric(4) NOT NULL,
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
  CONSTRAINT t_sales_report_transaction_discount_pk
   PRIMARY KEY(sales_transaction_id,detail_sub_number,promotion_type,promotion_no,store_discount_type)
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
 receipt_issued_flag boolean,
 e_receipt_target_flag boolean,
 processing_company_code varchar(20),
 employee_sale_flag boolean,
 consistency_sales_flag boolean,
 corporate_id varchar(20),
 sales_transaction_discount_flag boolean,
 sales_transaction_discount_type varchar(1),
 sales_transaction_discount_amount_rate_currency_code varchar(3),
 sales_transaction_discount_amount_rate numeric(24,4),
 token_code varchar(30),
 ims_linkage_flag boolean,
 ims_linkage_date varchar(8),
 hourly_sales_by_item_summary_flag boolean,
 hourly_summary_flag boolean,
 transaction_summary_flag boolean,
 department_summary_flag boolean,
 inventory_updated_flag boolean,
 heming_linkage_flag boolean,
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
CREATE INDEX t_sales_report_transaction_header_KEY1 ON t_sales_report_transaction_header(store_code,ims_linkage_flag,ims_linkage_date,update_datetime);
CREATE INDEX t_sales_report_transaction_header_KEY2 ON t_sales_report_transaction_header(store_code,cash_register_no,ims_linkage_date,transaction_summary_flag,update_datetime);

DROP TABLE IF EXISTS public.t_sales_report_transaction_tax CASCADE;
CREATE TABLE public.t_sales_report_transaction_tax(
 sales_transaction_id varchar(30) NOT NULL,
 detail_sub_number numeric(4) NOT NULL,
 tax_group varchar(10) NOT NULL,
 tax_name varchar(120),
 tax_sub_number numeric(4) NOT NULL,
 tax_amount_sign varchar(1),
 tax_amount_currency_code varchar(3),
 tax_amount_value numeric(24,4),
 tax_rate numeric(16,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_report_transaction_tax_pk
   PRIMARY KEY(sales_transaction_id,detail_sub_number,tax_group)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS public.t_sales_report_transaction_tender CASCADE;
CREATE TABLE public.t_sales_report_transaction_tender(
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