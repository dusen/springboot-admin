DROP TABLE IF EXISTS public.t_lot_number_mgm CASCADE;
CREATE TABLE public.t_lot_number_mgm(
 lot_number varchar(22) NOT NULL,
 out_bound_id varchar(14) NOT NULL,
 table_id varchar(63) NOT NULL,
 store_group_code varchar(10) NOT NULL,
 batch_region numeric(2) NOT NULL,
 check_flag boolean DEFAULT FALSE NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_lot_number_mgm_pk
   PRIMARY KEY(lot_number,out_bound_id,table_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
