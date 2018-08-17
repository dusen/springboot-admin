DROP TABLE IF EXISTS public.m_store_group_map CASCADE;
CREATE TABLE public.m_store_group_map(
 store_code varchar(10) NOT NULL,
 store_group_code varchar(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_store_group_map_pk
   PRIMARY KEY(store_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
