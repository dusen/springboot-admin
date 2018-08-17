DROP TABLE IF EXISTS sales-dev6.w_tender CASCADE;
CREATE TABLE sales-dev6.w_tender(
 eai_update_datetime varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_datetime varchar(18),
 tender_id numeric(6) NOT NULL,
 tender_name varchar(120) NOT NULL,
 receipt_tender_name varchar(45) NOT NULL,
 tender_group varchar(6) NOT NULL,
 valid_date timestamp NOT NULL,
 currency_code varchar(3),
 kid varchar(3),
 change_flag varchar(1) NOT NULL,
 current_deposit_flag varchar(1) NOT NULL,
 credit_company_code varchar(3),
 store_code varchar(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
 lot_number varchar(22) NOT NULL,
  CONSTRAINT w_tender_pk
   PRIMARY KEY(lot_number,store_code,tender_id,eai_update_datetime)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
