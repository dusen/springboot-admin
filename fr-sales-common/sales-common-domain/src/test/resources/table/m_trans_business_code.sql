DROP TABLE IF EXISTS sales-dev6.m_trans_business_code CASCADE;
CREATE TABLE sales-dev6.m_trans_business_code(
 system_country_code varchar(3) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_brand_code varchar(4) NOT NULL,
 batch_region numeric(2) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_trans_business_code_pk
   PRIMARY KEY(system_country_code,system_business_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX m_trans_business_code_KEY1 ON sales-dev6.m_trans_business_code(system_brand_code);


commit;
