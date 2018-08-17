DROP TABLE IF EXISTS public.m_error_notification_pattern CASCADE;
CREATE TABLE public.m_error_notification_pattern(
 error_type varchar(10) NOT NULL,
 error_notification_pattern varchar(2),
 error_notification_message_id varchar(3),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_error_notification_pattern_pk
   PRIMARY KEY(error_type)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
