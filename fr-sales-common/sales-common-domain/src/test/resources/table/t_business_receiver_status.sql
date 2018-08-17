DROP TABLE IF EXISTS sales-dev6.t_business_receiver_status CASCADE;
CREATE TABLE sales-dev6.t_business_receiver_status(
 transaction_id varchar(57) NOT NULL,
 business_receiver_name varchar(50) NOT NULL,
 create_date timestamptz NOT NULL,
 create_user varchar(50) NOT NULL,
 retry_count numeric(1) NOT NULL,
  CONSTRAINT t_business_receiver_status_pk
   PRIMARY KEY(transaction_id,business_receiver_name,retry_count)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
