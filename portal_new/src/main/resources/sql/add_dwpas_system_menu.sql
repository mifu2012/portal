

/*������������,0:�գ�1:�£���:��/3��*/
alter table dwpas_c_system_menu
add date_type int default 0;

/*��ҳ*/
update dwpas_c_system_menu csm set csm.date_type=0 where csm.MENU_CODE='01';
/*������*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='02';
/*��Ʒ������*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='03';
/*��Ʒ��չ*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='0301';
/*�û�����*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0302';
/*�û�����*/
update dwpas_c_system_menu csm set csm.date_type=0 where csm.MENU_CODE='0303';
/*�û�����*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='0304';
/*�û�����*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0305';
/*��������*/
update dwpas_c_system_menu csm set csm.date_type=1 where csm.MENU_CODE='0306';
/*��Ʒָ�����*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='04';
/*�û���Ϊ����*/
update dwpas_c_system_menu csm set csm.date_type=2 where csm.MENU_CODE='05';
