DROP TABLE IF EXISTS sales-dev6.t_alteration_exclusion_control CASCADE;
CREATE TABLE sales-dev6.t_alteration_exclusion_control(
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


commit;
