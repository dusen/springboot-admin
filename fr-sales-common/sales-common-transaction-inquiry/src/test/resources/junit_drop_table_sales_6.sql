DROP TABLE IF EXISTS public.m_general_delete_setting;

DROP TABLE IF EXISTS public.t_dclose;

DROP TABLE IF EXISTS public.w_dclose_send;

DROP TABLE IF EXISTS public.t_ims_pay_off_data;

DROP TABLE IF EXISTS public.t_ims_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_ims_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_ims_sales_transaction_header;

DROP TABLE IF EXISTS public.t_ims_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_ims_sales_transaction_tender;

DROP TABLE IF EXISTS public.m_plu;

DROP TABLE IF EXISTS public.m_error_notification_pattern;

DROP TABLE IF EXISTS public.m_error_notification_message;

DROP TABLE IF EXISTS public.t_jobnet_parameter;

DROP TABLE IF EXISTS public.t_salesreport_management;

DROP TABLE IF EXISTS public.t_open_log;

DROP TABLE IF EXISTS public.m_tender;

DROP TABLE IF EXISTS public.m_trans_tender;

DROP TABLE IF EXISTS public.m_type;

DROP TABLE IF EXISTS public.w_type;

DROP TABLE IF EXISTS public.m_store_control;

DROP TABLE IF EXISTS public.m_trans_business_code;

DROP TABLE IF EXISTS public.m_business_country_state_setting;

DROP TABLE IF EXISTS public.m_non_item;

DROP TABLE IF EXISTS public.m_non_item_if;

DROP TABLE IF EXISTS public.t_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_sales_transaction_detail_info;

DROP TABLE IF EXISTS public.t_pay_off_data;

DROP TABLE IF EXISTS public.m_pay_off_summary_mapping;

DROP TABLE IF EXISTS public.t_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_pile_up_pay_off_data;

DROP TABLE IF EXISTS public.t_sales_order_information;

DROP TABLE IF EXISTS public.m_trans_store_code;

DROP TABLE IF EXISTS public.m_store;

DROP TABLE IF EXISTS public.t_store_hourly_sku_result;

DROP TABLE IF EXISTS public.t_store_hourly_gather_result;

DROP TABLE IF EXISTS public.t_store_sku_result;

DROP TABLE IF EXISTS public.t_store_gather_result;

DROP TABLE IF EXISTS public.m_store_general_purpose;

DROP TABLE IF EXISTS public.t_store_hourly_two_digit_category_result;

DROP TABLE IF EXISTS public.t_sales_transaction_error_detail;

DROP TABLE IF EXISTS public.t_sales_transaction_header;

DROP TABLE IF EXISTS public.t_sales_transaction_total_amount;

DROP TABLE IF EXISTS public.t_sales_transaction_tender;

DROP TABLE IF EXISTS public.t_sales_transaction_tender_info;

DROP TABLE IF EXISTS public.t_sales_transaction_history;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_detail_info;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_transaction_inquiry_order_information;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_header;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_total_amount;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_tender;

DROP TABLE IF EXISTS public.t_transaction_inquiry_sales_transaction_tender_info;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_detail_info;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_detail_info;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_order_information;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_header;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_total_amount;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_tender;

DROP TABLE IF EXISTS public.t_error_evacuation_sales_transaction_tender_info;

DROP TABLE IF EXISTS public.t_sales_error_sales_order_information;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_header;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_total_amount;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_tender;

DROP TABLE IF EXISTS public.t_sales_error_sales_transaction_tender_info;

DROP TABLE IF EXISTS public.t_sales_check_data_file;

DROP TABLE IF EXISTS public.t_sales_report_transaction_discount;

DROP TABLE IF EXISTS public.t_sales_report_transaction_detail;

DROP TABLE IF EXISTS public.t_sales_report_transaction_detail_info;

DROP TABLE IF EXISTS public.t_sales_report_transaction_tax;

DROP TABLE IF EXISTS public.t_sales_report_transaction_order_information;

DROP TABLE IF EXISTS public.t_sales_report_transaction_header;

DROP TABLE IF EXISTS public.t_sales_report_transaction_total_amount;

DROP TABLE IF EXISTS public.t_sales_report_transaction_tender;

DROP TABLE IF EXISTS public.t_sales_report_transaction_tender_info;

DROP TABLE IF EXISTS public.m_common_code_master;

DROP TABLE IF EXISTS public.t_alteration_exclusion_control;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_discount;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_detail;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_detail_info;

DROP TABLE IF EXISTS public.t_alteration_history_pay_off_data;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_tax;

DROP TABLE IF EXISTS public.t_alteration_history_order_information;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_header;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_total_amount;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_tender;

DROP TABLE IF EXISTS public.t_alteration_history_sales_transaction_tender_info;

DROP TABLE IF EXISTS public.m_purge_item;

DROP TABLE IF EXISTS public.w_transaction_status_time;

DROP TABLE IF EXISTS public.t_business_receiver_status;

DROP TABLE IF EXISTS public.t_business_processor_status_p0;

DROP TABLE IF EXISTS public.t_business_processor_status_p1;

DROP TABLE IF EXISTS public.w_untreated_transaction;

DROP TABLE IF EXISTS public.m_g_class_link;

DROP TABLE IF EXISTS public.w_trans_store_code;

DROP TABLE IF EXISTS public.w_g_class_link;

DROP TABLE IF EXISTS public.w_plu;

DROP TABLE IF EXISTS public.w_skulist_child;

DROP TABLE IF EXISTS public.m_item;

DROP TABLE IF EXISTS public.w_skulist_parent;

DROP TABLE IF EXISTS public.w_tender;

DROP TABLE IF EXISTS public.w_item_hierarchy_major_category;

DROP TABLE IF EXISTS public.w_purge_item;

DROP TABLE IF EXISTS public.m_item_list_head;

DROP TABLE IF EXISTS public.m_item_list_detail;

DROP TABLE IF EXISTS public.m_color_size;

DROP TABLE IF EXISTS public.w_color_size_if;

DROP TABLE IF EXISTS public.t_lot_number_mgm;

DROP TABLE IF EXISTS public.m_daily_sales_report_item_setting;

DROP TABLE IF EXISTS public.m_item;

DROP TABLE IF EXISTS public.m_item_level_1;

DROP TABLE IF EXISTS public.m_item_level_2;

DROP TABLE IF EXISTS public.m_item_pack;

DROP TABLE IF EXISTS public.m_dept;

DROP TABLE IF EXISTS public.m_store_group_map;

commit;
