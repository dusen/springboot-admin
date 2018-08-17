DROP TABLE IF EXISTS sales-dev6.w_g_class CASCADE;
CREATE TABLE sales-dev6.w_g_class(
 lot_number varchar(22) NOT NULL,
 eai_update_date varchar(18) NOT NULL,
 eai_update_type varchar(1) NOT NULL,
 eai_send_status varchar(1),
 eai_send_date varchar(18),
 g_department_class_code varchar(6) NOT NULL,
 g_department_class_name varchar(120) NOT NULL,
 global_business numeric(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
  CONSTRAINT w_g_class_pk
   PRIMARY KEY(lot_number,g_department_class_code,global_business,eai_update_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
