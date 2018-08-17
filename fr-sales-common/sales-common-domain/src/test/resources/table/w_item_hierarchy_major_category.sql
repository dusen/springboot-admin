DROP TABLE IF EXISTS sales-dev6.w_item_hierarchy_major_category CASCADE;
CREATE TABLE sales-dev6.w_item_hierarchy_major_category(
 lot_number varchar(22) NOT NULL,
 eai_update_date varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_date varchar(18),
 major_category_code numeric(4) NOT NULL,
 major_category_name varchar(120) NOT NULL,
 batch_region numeric(2) NOT NULL,
  CONSTRAINT w_item_hierarchy_major_category_pk
   PRIMARY KEY(lot_number,major_category_code,eai_update_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
