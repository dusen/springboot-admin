DROP TABLE IF EXISTS sales-dev6.w_color_size_if CASCADE;
CREATE TABLE sales-dev6.w_color_size_if(
 eai_update_datetime varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_datetime varchar(18),
 difference_id varchar(10) NOT NULL,
 difference_type_code varchar(6) NOT NULL,
 difference_name varchar(120) NOT NULL,
 view_difference_code varchar(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
 lot_number varchar(22) NOT NULL,
  CONSTRAINT w_color_size_if_pk
   PRIMARY KEY(lot_number,difference_id,eai_update_datetime)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
