/*==============================================================*/
/* Table: DWPAS_ST_PRODUCT_DATA                                 */
/*==============================================================*/
create table DWPAS_ST_PRODUCT_DATA
(
   product_id           varchar(255) not null,
   report_date          varchar(10) not null,
   date_type            varchar(2) default 'D',
   buy_user_count       numeric(10,0) default 0
);

alter table DWPAS_ST_PRODUCT_DATA comment '单独购买的用户数/销量,等';

alter table DWPAS_ST_PRODUCT_DATA
   add primary key (product_id, report_date);
