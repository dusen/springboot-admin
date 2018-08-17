DROP SEQUENCE IF EXISTS report_receipt_number_seq;
CREATE SEQUENCE report_receipt_number_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 999999999
  NO CYCLE
  OWNED BY NONE
;

DROP TABLE IF EXISTS m_report_master;
CREATE TABLE m_report_master(
 report_id varchar(14) NOT NULL,
 report_type varchar(5) NOT NULL,
 country_code varchar(3) NOT NULL,
 apply_start_date timestamptz NOT NULL,
 apply_end_date timestamptz NOT NULL,
 report_form_bucket_name varchar(256) NOT NULL,
 report_form_key_name varchar(256) NOT NULL,
 report_title varchar(40),
 report_preservation_period numeric(5),
 printer_name varchar(50),
 outer_command_execute_flag boolean,
 auto_print_server_ip_address varchar(15),
 create_datetime timestamptz NOT NULL,
 update_datetime timestamptz,
  CONSTRAINT m_report_master_pk
   PRIMARY KEY(report_id,report_type,country_code,apply_start_date,apply_end_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS m_store_general_purpose;
CREATE TABLE m_store_general_purpose(
 general_purpose_type varchar(20) NOT NULL,
 store_code   varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 code varchar(50) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_store_general_purpose_pk
   PRIMARY KEY(general_purpose_type,store_code,system_country_code,system_brand_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

DROP TABLE IF EXISTS t_report_create_status;
CREATE TABLE t_report_create_status(
 receipt_number varchar(50) NOT NULL,
 report_id varchar(14) NOT NULL,
 report_type varchar(5) NOT NULL,
 store_code varchar(10) NOT NULL,
 create_report_status numeric(1) NOT NULL,
 auto_print_status numeric(1),
 created_report_bucket_name varchar(256),
 created_report_key_name varchar(256),
 create_report_business_day date,
 delete_report_business_day date,
 printer_name varchar(50),
 outer_command_execute_flag boolean,
 create_datetime timestamptz NOT NULL,
 update_datetime timestamptz,
  CONSTRAINT t_report_create_status_pk
   PRIMARY KEY(receipt_number)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;

commit;
