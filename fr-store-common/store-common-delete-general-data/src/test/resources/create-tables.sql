
CREATE TABLE IF NOT EXISTS del_config(
 date_time timestamptz ,
 store_code varchar(10) ,
 system_brand_code varchar(4) ,
 system_country_code varchar(3) ,
 create_user_id varchar(30),
 create_datetime timestamptz ,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
 date_item varchar(30),
 saved_days numeric(30),
  CONSTRAINT del_config_pk
   PRIMARY KEY(date_item)
);