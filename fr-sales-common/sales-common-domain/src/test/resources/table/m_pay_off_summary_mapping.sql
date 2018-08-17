DROP TABLE IF EXISTS sales-dev6.m_pay_off_summary_mapping CASCADE;
CREATE TABLE sales-dev6.m_pay_off_summary_mapping(
 mapping_pattern numeric(3) NOT NULL,
 mapping_ptn_name varchar(30),
 payoff_type_code varchar(6) NOT NULL,
 mapping_sub_pattern numeric(3),
 system_brand_code varchar(4),
 system_business_code varchar(10),
 system_country_code varchar(3),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_pay_off_summary_mapping_pk
   PRIMARY KEY(mapping_pattern,payoff_type_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
