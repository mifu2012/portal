alter table dwpas_c_module_info
add POSITION int(1) DEFAULT '0' COMMENT '0：底部\r\n1：左边\r\n2：右边\r\n',
add WIDTH int(4) DEFAULT '0',
add  HEIGHT int(3) DEFAULT '0',
add  MODULE_TYPE int(1) DEFAULT '1' COMMENT '0：自定义模块 1：系统模块',
add  MODULE_SORT int(2) DEFAULT '0',
add  POSITION_X float DEFAULT '0',
add  POSITION_Y float DEFAULT '0';