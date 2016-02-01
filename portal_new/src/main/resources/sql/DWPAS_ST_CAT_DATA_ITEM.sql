/*==============================================================*/
/* Table: DWPAS_ST_CAT_DATA_ITEM                                */
/*==============================================================*/
create table DWPAS_ST_CAT_DATA_ITEM
(
   QUESTION_ID          varchar(255) not null comment 'ID',
   CREATE_DATE          date comment '求助日期',
   CREATE_USER          varchar(255) comment '创建人',
   DEAL_USER            varchar(255) comment '处理人',
   QUESTION_TITLE       varchar(255) not null comment '标题',
   QUESTION_CONTENT     text not null comment '内容'
);

alter table DWPAS_ST_CAT_DATA_ITEM
   add primary key (QUESTION_ID);