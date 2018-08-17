DROP TABLE IF EXISTS sales-dev6.w_skulist_child CASCADE;
CREATE TABLE sales-dev6.w_skulist_child(
 lot_number varchar(22) NOT NULL,
 eai_update_date varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_date varchar(18),
 skulist_code numeric(8) NOT NULL,
 item_code varchar(25) NOT NULL,
 item_level numeric(1) NOT NULL,
 inventory_management_level numeric(1) NOT NULL,
 pack_ind varchar(1) NOT NULL,
 insert_id varchar(30) NOT NULL,
 insert_date timestamptz NOT NULL,
 create_datetime timestamptz NOT NULL,
 last_update_datetime timestamptz NOT NULL,
 last_update_id varchar(30) NOT NULL,
 main_item_type varchar(1) NOT NULL,
 inner_pack_size numeric(12,4) NOT NULL,
 logical_delete_type varchar(1) NOT NULL,
 business_code numeric(10) NOT NULL,
 country_code varchar(3) NOT NULL,
 batch_region numeric(2) NOT NULL,
  CONSTRAINT w_skulist_child_pk
   PRIMARY KEY(lot_number,business_code,country_code,skulist_code,item_code,eai_update_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
