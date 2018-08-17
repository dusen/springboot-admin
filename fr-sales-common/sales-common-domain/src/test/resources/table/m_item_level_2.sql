DROP TABLE IF EXISTS public.m_item_level_2 CASCADE;
CREATE TABLE public.m_item_level_2(
 system_brand_code varchar(10) NOT NULL,
 system_business_code varchar(10) NOT NULL,
 system_country_code varchar(3) NOT NULL,
 item_code varchar(25) NOT NULL,
 item_code_type varchar(6),
 view_item_code varchar(25),
 year_code varchar(4),
 view_year_type varchar(1),
 season_code varchar(6),
 detail_season_code varchar(6),
 sales_country_code varchar(3),
 color_code varchar(10),
 size_code varchar(10),
 pattern_length_code varchar(10),
 major_category_code varchar(4),
 class_code varchar(4),
 middle_category_code varchar(4),
 small_category_value numeric(5),
 item_parent varchar(25),
 item_crand_parent varchar(25),
 item_level numeric(1),
 transaction_level numeric(1),
 pack_flag boolean DEFAULT FALSE NOT NULL,
 simple_item_pack_flag boolean DEFAULT FALSE NOT NULL,
 contains_inner_kind varchar(1),
 sellable_flag boolean DEFAULT FALSE NOT NULL,
 orderable_flag boolean DEFAULT FALSE NOT NULL,
 pack_status varchar(1),
 order_unit_type varchar(1),
 asort_code varchar(4),
 fob numeric(20,4),
 cost_price_sequence numeric(3),
 stock_buying_type_code varchar(6),
 initial_selling_price numeric(20,4),
 sales_zone_group numeric(4),
 standard_sales_unit varchar(4),
 item_name varchar(250),
 intern_item_name varchar(250),
 pos_item_name varchar(120),
 second_pos_item_name varchar(120),
 external_item_name_1 varchar(66),
 external_item_name_2 varchar(66),
 external_item_name_3 varchar(66),
 external_item_name_4 varchar(66),
 second_item_name varchar(255),
 picking_shipment_flag boolean DEFAULT FALSE NOT NULL,
 direct_delivery_unit_quantity numeric(8),
 regular_item_flag boolean DEFAULT FALSE NOT NULL,
 color_size_management_flag boolean DEFAULT FALSE NOT NULL,
 b_item_return_possible_flag boolean DEFAULT FALSE NOT NULL,
 status varchar(1),
 delivery_type_code varchar(6),
 supplier_code varchar(10),
 stock_buying_cost numeric(20,4),
 packing_quantity numeric(8),
 picking_unit_quantity numeric(8),
 selling_price numeric(20,4),
 selling_unit varchar(4),
 taxation_flag boolean DEFAULT FALSE NOT NULL,
 batch_region numeric(2) NOT NULL,
 create_user_id varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT m_item_level_2_pk
   PRIMARY KEY(system_brand_code,system_country_code,item_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
