DROP TABLE IF EXISTS sales-dev6.t_dclose CASCADE;
CREATE TABLE sales-dev6.t_dclose(
 store_code varchar(10) NOT NULL,
 business_date varchar(8) NOT NULL,
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 send_file_count numeric(4),
 processing_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_dclose_pk
   PRIMARY KEY(store_code,business_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
