DROP TABLE IF EXISTS sales-dev6.t_sales_report_transaction_order_information CASCADE;
CREATE TABLE sales-dev6.t_sales_report_transaction_order_information(
 integrated_order_id varchar(27) NOT NULL,
 order_barcode_number varchar(23),
 store_code varchar(10) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10),
 system_country_code varchar(3) NOT NULL,
 channel_code varchar(10) NOT NULL,
 customer_id varchar(30),
 order_confirmation_business_date varchar(8),
 order_confirmation_date_time timestamptz,
 data_alteration_sales_linkage_type numeric(1),
 data_alteration_backbone_linkage_type numeric(1),
 data_alteration_flag boolean DEFAULT FALSE NOT NULL,
 data_alteration_user_id varchar(30),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_report_transaction_order_information_pk
   PRIMARY KEY(integrated_order_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
