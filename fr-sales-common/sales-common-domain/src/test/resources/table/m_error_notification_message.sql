DROP TABLE IF EXISTS public.m_error_notification_message CASCADE;
CREATE TABLE public.m_error_notification_message(
 error_notification_message_id varchar(3) NOT NULL,
 error_output_message varchar(200),
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_error_notification_message_pk
   PRIMARY KEY(error_notification_message_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
