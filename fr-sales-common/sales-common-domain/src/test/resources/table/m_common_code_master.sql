DROP TABLE IF EXISTS sales-dev6.m_common_code_master CASCADE;
CREATE TABLE sales-dev6.m_common_code_master(
 type_id varchar(50) NOT NULL,
 type_value varchar(50) NOT NULL,
 display_order numeric(5) DEFAULT 0 NOT NULL,
 name_1 varchar(120) NOT NULL,
 name_2 varchar(120),
 name_3 varchar(120),
 name_4 varchar(120),
 name_5 varchar(120),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_common_code_master_pk
   PRIMARY KEY(type_id,type_value)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
