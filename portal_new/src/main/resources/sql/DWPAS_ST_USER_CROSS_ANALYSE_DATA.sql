/*==============================================================*/
/* Table: DWPAS_ST_USER_CROSS_ANALYSE_DATA                      */
/*==============================================================*/
create table DWPAS_ST_USER_CROSS_ANALYSE_DATA
(
   kpi_code             varchar(32) not null comment 'ָ��',
   report_date          varchar(8) not null comment 'ͳ��ʱ��',
   rel_kpi_code         varchar(32) not null comment '����ָ��',
   cross_user_cnt       numeric(10,0) comment '�����û���',
   cross_user_rate      numeric(30,4) comment '�����û���ռ��',
   GMT_CREATE           timestamp comment '����ʱ��',
   GMT_MODIFIED         timestamp comment '�޸�ʱ��'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table DWPAS_ST_USER_CROSS_ANALYSE_DATA comment '�û���Ϊ���������ÿ������ݲֿ�ͬ�����ݡ�';