DROP TABLE IF EXISTS sales-dev6.w_plu CASCADE;
CREATE TABLE sales-dev6.w_plu(
 lot_number varchar(22) NOT NULL,
 store_code varchar(6),
 price_look_up_code varchar(13),
 single_item_code varchar(7),
 color_code varchar(3),
 size_code varchar(3),
 pattern_length_code varchar(3),
 year_code varchar(1),
 season_code varchar(1),
 cost_sequence varchar(3),
 current_inventory_quantity numeric(12),
 selling_price numeric(20,4),
 single_promotion_price numeric(20,4),
 single_promotion_end_date varchar(8),
 mobile_single_promotion_price numeric(20,4),
 multi_buy_flag varchar(1),
 business_date varchar(8),
 pr_chg_start_date varchar(8),
 single_promotion_start_date varchar(8),
 mobile_single_promotion_start_date varchar(8),
 mobile_single_promotion_end_date varchar(8),
 multi_buy_discount_type_cd varchar(1),
 multi_buy_quantity1 varchar(2),
 multi_buy_amount1 numeric(20,4),
 multi_buy_quantity2 varchar(2),
 multi_buy_amount2 numeric(20,4),
 multi_buy_quantity3 varchar(2),
 multi_buy_amount3 numeric(20,4),
 multi_buy_amount4 numeric(20,4),
 multi_buy_start_date varchar(8),
 multi_buy_end_date varchar(8),
 limited_quantity_promotion_discount_type_cd varchar(1),
 limited_quantity_promotion_quantity1 varchar(2),
 limited_quantity_promotion_amount1 numeric(20,4),
 limited_quantity_promotion_start_date varchar(8),
 limited_quantity_promotion_end_date varchar(8),
 end_of_day_data varchar(1),
 order_quantity varchar(8),
 communication_code varchar(25),
 preliminary varchar(30),
 create_datetime timestamptz NOT NULL,
 create_program_id varchar(30),
 update_user_id varchar(30),
 update_datetime timestamptz,
 update_program_id varchar(30),
  CONSTRAINT w_plu_pk
   PRIMARY KEY(lot_number,store_code,price_look_up_code)
)
WITH (OIDS=FALSE,FILLFACTOR=90)
;


commit;
