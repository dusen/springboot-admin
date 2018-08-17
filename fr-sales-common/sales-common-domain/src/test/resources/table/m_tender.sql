DROP TABLE IF EXISTS sales-dev6.m_tender CASCADE;
CREATE TABLE sales-dev6.m_tender(
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


commit;
