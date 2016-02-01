

/*加上日期类型,0:日，1:月，２:日/3月*/
alter table dwpas_c_system_menu
add date_type int default 0;

/*首页*/
update dwpas_c_system_menu csm set csm.date_type=0 where csm.MENU_CODE='01';
/*龙虎榜*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='02';
/*产品健康度*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='03';
/*产品发展*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='0301';
/*用户存留*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0302';
/*用户体验*/
update dwpas_c_system_menu csm set csm.date_type=0 where csm.MENU_CODE='0303';
/*用户声音*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='0304';
/*用户特征*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0305';
/*场景交叉*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0306';
/*产品指标分析*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='04';
/*用户行为分析*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='05';
