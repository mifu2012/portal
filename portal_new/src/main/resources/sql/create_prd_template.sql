/*==============================================================*/
/* Table: DWPAS_R_PRD_TEMPLATE                                  */
/*==============================================================*/
create table DWPAS_R_PRD_TEMPLATE
(
   TEMPLATE_ID          INT not null,
   PRODUCT_ID           varchar(255) not null,
   GMT_CREATE           timestamp,
   GMT_MODIFIED         timestamp
);

alter table DWPAS_R_PRD_TEMPLATE comment 'DWPAS_R_PRD_TEMPLATE';

alter table DWPAS_R_PRD_TEMPLATE
   add primary key (TEMPLATE_ID, PRODUCT_ID);