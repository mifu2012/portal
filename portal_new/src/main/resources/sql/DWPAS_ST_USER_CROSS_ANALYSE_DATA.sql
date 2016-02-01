/*==============================================================*/
/* Table: DWPAS_ST_USER_CROSS_ANALYSE_DATA                      */
/*==============================================================*/
create table DWPAS_ST_USER_CROSS_ANALYSE_DATA
(
   kpi_code             varchar(32) not null comment '指标',
   report_date          varchar(8) not null comment '统计时间',
   rel_kpi_code         varchar(32) not null comment '关联指标',
   cross_user_cnt       numeric(10,0) comment '交叉用户数',
   cross_user_rate      numeric(30,4) comment '交叉用户数占比',
   GMT_CREATE           timestamp comment '创建时间',
   GMT_MODIFIED         timestamp comment '修改时间'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table DWPAS_ST_USER_CROSS_ANALYSE_DATA comment '用户行为交叉情况表，每天从数据仓库同步数据。';