INSERT INTO dwpas_c_column_info VALUES ('dgtl3YN5j8HcPdvWtzrhndfSKwkedsTB','4b8ded24f4db4f7f9536201fc2790c7a','KPI_COM_MEMBER_TOTAL_OLDANDNEW','���ϻ�Ա�����Ա��','�������������û�','�����û�ת�����','1','���ϻ�Ա�����Ա��',NOW(),-1,NULL,NULL,NULL);

INSERT INTO dwpas_r_column_com_kpi VALUES ('CKC0500',NOW(),NULL,NULL,NULL,NULL,'dgtl3YN5j8HcPdvWtzrhndfSKwkedsTB');

/* CKC0500ͨ��ָ�����*/
insert  into dwpas_c_com_kpi_info(com_kpi_code,com_kpi_name,is_show_rank,rank_show_order,gmt_create,gmt_modified) values ('CKC0500', '���ϻ�Ա�����Ա��', '0', 0, now(), now());


/* ͨ��ָ���������ָ�� */
INSERT INTO dwpas_r_kpi_comkpi (
	kpi_code,
	com_Kpi_code,
	GMT_CREATE,
	GMT_MODIFIED
)
VALUES
	(
		'CUS1010000301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS1020000301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001010301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001020301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001030301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001040301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001050301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001060301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS2001080301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS300101A301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS300103A301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'CUS300104A301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'WAP5010000301D',
		'CKC0500',
		now(),
		now()
	),
	(
		'WAP5020000301D',
		'CKC0500',
		now(),
		now()
	);