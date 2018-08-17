DROP TABLE IF EXISTS sales-dev6.w_type CASCADE;
CREATE TABLE sales-dev6.w_type(
 lot_number varchar(22) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 code_type varchar(4) NOT NULL,
 code varchar(6) NOT NULL,
 code_name varchar(40),
 code_num numeric(4),
 eai_update_datetime varchar(18),
 eai_update_type varchar(1),
 eai_send_status varchar(1),
 eai_send_datetime varchar(18),
 system_business_code varchar(10),
 required_type varchar(1),
 batch_region numeric(2),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT w_type_pk
   PRIMARY KEY(lot_number,system_country_code,code_type,code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
