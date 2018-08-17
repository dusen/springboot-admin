DROP TABLE IF EXISTS sales-dev6.m_trans_tender CASCADE;
CREATE TABLE sales-dev6.m_trans_tender(
 store_code varchar(10) NOT NULL,
 tender_id varchar(50) NOT NULL,
 tender_group varchar(50),
 valid_date timestamptz,
 ims_tender_id numeric(6) NOT NULL,
 ims_tender_group varchar(6),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_trans_tender_pk
   PRIMARY KEY(store_code,tender_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;
 CREATE INDEX m_trans_tender_KEY1 ON sales-dev6.m_trans_tender(store_code,ims_tender_id);


commit;
