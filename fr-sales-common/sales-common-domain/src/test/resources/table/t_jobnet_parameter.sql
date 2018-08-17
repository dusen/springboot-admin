DROP TABLE IF EXISTS public.t_jobnet_parameter CASCADE;
CREATE TABLE public.t_jobnet_parameter(
 parameter_store_code varchar(10) NOT NULL,
 parameter_payoff_date varchar(10) NOT NULL,
 parameter_business_date varchar(10) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT t_jobnet_parameter_pk
   PRIMARY KEY(parameter_store_code,parameter_payoff_date,parameter_business_date)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
