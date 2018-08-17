DROP TABLE IF EXISTS sales-dev6.w_transaction_status_time CASCADE;
CREATE TABLE sales-dev6.w_transaction_status_time(
 transaction_status_time timestamptz NOT NULL
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
