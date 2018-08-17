DROP VIEW IF EXISTS v_type;
create view v_type ( 
lot_number,
system_country_code,
code_type,
code,
code_name,
code_num,
eai_update_datetime,
eai_update_type,
eai_send_status,
eai_send_datetime,
system_business_code,
required_type,
batch_region,
system_brand_code
 ) as (
select
 w_type.lot_number,
 w_type.system_country_code,
 w_type.code_type,
 w_type.code,
 w_type.code_name,
 w_type.code_num,
 w_type.eai_update_datetime,
 w_type.eai_update_type,
 w_type.eai_send_status,
 w_type.eai_send_datetime,
 w_type.system_business_code,
 w_type.required_type,
 w_type.batch_region,
 m_trans_store_code.system_brand_code
from
 w_type,m_trans_store_code
where
 w_type.system_country_code = m_trans_store_code.system_country_code
 and w_type.system_business_code = m_trans_store_code.system_business_code
);

DROP VIEW IF EXISTS v_tender;
CREATE OR REPLACE VIEW public.v_tender AS
 SELECT m_store_general_purpose.code,
    w_tender.lot_number,
    w_tender.eai_update_datetime,
    w_tender.eai_update_type,
    w_tender.eai_send_status,
    w_tender.eai_send_datetime,
    w_tender.tender_id AS ims_tender_id,
    w_tender.tender_name,
    w_tender.receipt_tender_name,
    w_tender.tender_group AS ims_tender_group,
    w_tender.valid_date,
    w_tender.currency_code,
    w_tender.kid,
    w_tender.change_flag,
    w_tender.current_deposit_flag,
    w_tender.credit_company_code,
    w_tender.batch_region,
    w_tender.store_code,
    m_trans_store_code.system_brand_code,
    m_trans_store_code.system_business_code,
    m_trans_store_code.system_country_code
   FROM w_tender,
    m_trans_store_code,
    m_store_general_purpose
  WHERE w_tender.store_code::text = m_trans_store_code.store_code::text AND w_tender.store_code::text = m_store_general_purpose.store_code::text AND m_store_general_purpose.general_purpose_type::text = 'time_zone'::text;



