Drop Table IF EXISTS t_business_processor_status_p0;
CREATE TABLE t_business_processor_status_p0(
 transaction_id varchar(57) NOT NULL,
 business_process_name varchar(50) NOT NULL,
 create_date timestamptz NOT NULL,
 create_user varchar(50) NOT NULL,
 retry_count numeric(1) NOT NULL,
  CONSTRAINT t_business_processor_status_p0_pk
   PRIMARY KEY(transaction_id,business_process_name,retry_count)
)
WITH (FILLFACTOR=90)
;

Drop Table IF EXISTS t_business_processor_status_p1;
CREATE TABLE t_business_processor_status_p1(
 transaction_id varchar(57) NOT NULL,
 business_process_name varchar(50) NOT NULL,
 create_date timestamptz NOT NULL,
 create_user varchar(50) NOT NULL,
 retry_count numeric(1) NOT NULL,
  CONSTRAINT t_business_processor_status_p1_pk
   PRIMARY KEY(transaction_id,business_process_name,retry_count)
)
WITH (FILLFACTOR=90)
;