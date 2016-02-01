package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisSystemParamType;

public interface DwmisSystemParamTypeService {
	/**
	 * 根据groupId获得系统参数列表
	 * 
	 * @param groupId
	 * @return
	 */
	List<DwmisSystemParamType> listSystemParamByGroupId(int groupId);

	/**
	 * 根据typeId获得系统参数
	 * 
	 * @param typeId
	 * @return
	 */
	DwmisSystemParamType getSystemParamTypeByTypeId(int typeId);

	/**
	 * 更新系统参数类型
	 * 
	 * @param dwmisSystemParamType
	 */
	void updateSystemParamType(DwmisSystemParamType dwmisSystemParamType);

	/**
	 * 根据typeId删除系统参数类型
	 * 
	 * @param typeId
	 */
	void deleteSystemParamType(int typeId);

	/**
	 * 保存mis_type
	 * 
	 * @param parm
	 */
	void saveSystemparamTypeGroup(Object parm);

	/**
	 * 获得组名
	 * 
	 * @param typeId
	 * @return
	 */
	String getGroupName(int typeId);

	/**
	 * 根据groupId删除Group内容
	 * 
	 * @param groupId
	 */
	public void delSystemTypeGroup(int groupId);

	/**
	 * 删除Group节点
	 * 
	 * @param typeId
	 */
	public void delSystemTypeGroupNode(int typeId);
}
