DROP TABLE IF EXISTS sales-dev6.m_plu CASCADE;
CREATE TABLE sales-dev6.m_plu(
 store_code varchar(10) NOT NULL,
 price_look_up_code varchar(25) NOT NULL,
 selling_price numeric(24,4),
 single_promotion_price numeric(24,4),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_plu_pk
   PRIMARY KEY(store_code,price_look_up_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
