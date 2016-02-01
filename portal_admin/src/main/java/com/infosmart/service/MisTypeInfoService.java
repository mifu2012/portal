package com.infosmart.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class MisTypeInfoService extends BaseServiceImpl {
	@Autowired
	private MyBatisDao myBatisDao;

	/**
	 * 获得分页系统类型
	 * 
	 * @param param
	 * @return
	 */
	public List<MisTypeInfo> getMisTypePages(Object param) {
		if(param == null){
			this.logger.warn("获取系统类型失败，参数param为null");
			return null;
		}
		return myBatisDao.getList("MisTypeInfoMapper.listPageMisType", param);
	}

	/**
	 * 获得所有系统类型
	 * 
	 * @return
	 */
	public List<MisTypeInfo> getMisTypeInfos() {
		return myBatisDao.getList("MisTypeInfoMapper.listAllMisType");
	}

	/**
	 * 根据id获得对应系统类型
	 * 
	 * @param id
	 * @return
	 */
	public MisTypeInfo getMisTypeInfo(Serializable id) {
		if(id == null){
			this.logger.warn("根据id获得对应系统类型失败，参数Id为null");
			return null;
		}
		return myBatisDao.get("MisTypeInfoMapper.getMisTypeById", id);
	}
	
	/**
	 * 根据类型组ID查询
	 * @param id
	 * @return
	 */
	public List<MisTypeInfo> getMisTypeInfoByGroupId(Serializable id) {
		if(id == null){
			this.logger.warn("根据类型组ID查询系统类型失败，参数Id为null");
			return null;
		}
		return myBatisDao.getList("MisTypeInfoMapper.getMisTypeByGroupId", id);
	}
	/**
	 * 根据类型名称查询
	 * @param typeName
	 * @return
	 */
	public List<MisTypeInfo> getMisTypeByTypeName(String typeName) {
		if(StringUtils.isBlank(typeName)){
			this.logger.warn("根据类型名称查询失败，参数typeName为null");
			return null;
		}
		return myBatisDao.getList("MisTypeInfoMapper.getMisTypeByTypeName", typeName);
	}
	
	/**
	 * 新增系统类型
	 * 
	 * @param param
	 */
	public void save(MisTypeInfo param)throws Exception {
		if(param == null){
			this.logger.warn("新增系统类型失败，参数为null");
			throw new Exception("新增系统类型失败，参数为null");
		}
		if(param.getTypeId()==null || param.getGroupId()==null){
			this.logger.warn("新增系统类型失败，typeId或者groupId为null");
			throw new Exception("新增系统类型失败，typeId或者groupId为null");
		}
		String groupId = param.getGroupId();
		Integer typeId =(Integer) this.myBatisDao.get("MisTypeInfoMapper.getMaxTypeBygroupId", groupId);
		if(typeId ==null){
			typeId = Integer.valueOf(groupId)+1;
		}else {
			typeId = typeId + 1;
		}
		param.setTypeId(String.valueOf(typeId));
		myBatisDao.save("MisTypeInfoMapper.insertMisType", param);
	}

	/**
	 * 保存groupId为"0"的系统类型
	 * 
	 * @param param
	 */
	public void saveGroup(Object param) throws Exception {
		if(param == null){
			this.logger.warn("保存groupId为0的系统类型失败，参数为null");
			throw new Exception("保存groupId为0的系统类型失败，参数为null");
		}
		myBatisDao.save("MisTypeInfoMapper.insertMisGroup", param);
	}

	/**
	 * 更新系统类型
	 * 
	 * @param param
	 */
	public void update(Object param) throws Exception {
		if(param == null){
			this.logger.warn("更新系统类型失败，参数为null");
			throw new Exception("更新系统类型失败，参数为null");
		}
		myBatisDao.save("MisTypeInfoMapper.updateMisType", param);
	}

	/**
	 * 更新除了groupId的系统类型
	 * 
	 * @param param
	 */
	public void updateGroup(Object param) throws Exception {
		if(param == null){
			this.logger.warn("更新除了groupId的系统类型 失败，参数为null");
			throw new Exception("更新除了groupId的系统类型 失败，参数为null");
		}
		myBatisDao.save("MisTypeInfoMapper.updateMisGroup", param);
	}

	/**
	 * 根据typeId删除系统类型
	 * 
	 * @param id
	 */
	public void delete(Serializable id) throws Exception {
		if(id == null){
			this.logger.warn("根据typeId删除系统类型失败，参数typeId为null");
			throw new Exception("根据typeId删除系统类型失败，参数typeId为null");
		}
		myBatisDao.delete("MisTypeInfoMapper.deleteMisType", id);
	}

	/**
	 * 根据groupId删除系统类型
	 * 
	 * @param id
	 */
	public void deleteGroupId(Serializable id) throws Exception {
		if(id == null){
			this.logger.warn("根据groupId删除系统类型失败，参数groupId为null");
			throw new Exception("根据groupId删除系统类型失败，参数groupId为null");
		}
		myBatisDao.delete("MisTypeInfoMapper.deleteMisTypeByGroupId", id);
	}
	/**
	 * 根据groupId删除系统类型组以及下面的类型
	 * @param id
	 */
	public void deleteGroupAndTypeByGroupId(Serializable id) throws Exception {
		if(id == null){
			this.logger.warn("根据groupId删除系统类型组以及下面的类型失败，参数groupId为null");
			throw new Exception("根据groupId删除系统类型组以及下面的类型类型失败，参数groupId为null");
		}
		try {
			myBatisDao.delete("MisTypeInfoMapper.deleteMisTypeByGroupId", id);
			myBatisDao.delete("MisTypeInfoMapper.deleteMisType", id);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.warn("删除系统类型组以及下面的类型失败:"+e.getMessage(),e);
			throw e;

		}
		
	}
	
	/**
	 * 获取所有系统类型信息
	 * @return
	 */
	public List<MisTypeInfo> getMisTypeProdlist(){
		return myBatisDao.getList("MisTypeInfoMapper.mis_prod_info_type");
	}
}
