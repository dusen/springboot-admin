DROP TABLE IF EXISTS sales-dev6.w_g_class_link CASCADE;
CREATE TABLE sales-dev6.w_g_class_link(
 lot_number varchar(22) NOT NULL,
 eai_update_date varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_date varchar(18),
 major_category_code numeric(4) NOT NULL,
 class_code numeric(4) NOT NULL,
 middle_category_code numeric(4) NOT NULL,
 g_department_class_code varchar(6) NOT NULL,
 global_business numeric(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
  CONSTRAINT w_g_class_link_pk
   PRIMARY KEY(lot_number,major_category_code,class_code,middle_category_code,global_business,eai_update_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
