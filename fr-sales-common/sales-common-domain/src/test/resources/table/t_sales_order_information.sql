DROP TABLE IF EXISTS sales-dev6.t_sales_order_information CASCADE;
CREATE TABLE sales-dev6.t_sales_order_information(
 integrated_order_id varchar(27) NOT NULL,
 order_barcode_number varchar(23),
 store_code varchar(10) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 system_business_code varchar(10),
 system_country_code varchar(3) NOT NULL,
 channel_code varchar(10),
 update_type varchar(10),
 customer_id varchar(30),
 order_confirmation_business_date varchar(8),
 order_confirmation_date_time timestamptz,
 error_check_type numeric(1),
 data_alteration_sales_linkage_type numeric(1),
 data_alteration_backbone_linkage_type numeric(1),
 data_alteration_editing_flag boolean DEFAULT FALSE NOT NULL,
 data_alteration_user_id varchar(30),
 create_user_id varchar(30),
 create_datetime timestamptz,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_sales_order_information_pk
   PRIMARY KEY(integrated_order_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
