DROP TABLE IF EXISTS sales-dev6.w_untreated_transaction CASCADE;
CREATE TABLE sales-dev6.w_untreated_transaction(
 transaction_id varchar(57) NOT NULL,
 retry_count numeric(1) NOT NULL,
 delete_flag boolean NOT NULL,
 create_user_id varchar(30) DEFAULT 'BATCH',
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30) DEFAULT 'fr-store-receiver-recovery',
 update_user_id varchar(30) DEFAULT 'BATCH',
 update_datetime timestamptz NOT NULL,
 update_program_id varchar(30) DEFAULT 'fr-store-receiver-recovery',
  CONSTRAINT w_untreated_transaction_pk
   PRIMARY KEY(transaction_id)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
