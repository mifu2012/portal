package com.infosmart.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.SysParamInfo;

@Service
public class SysParamService {
	private static final Logger logger = Logger
			.getLogger(SysParamService.class);
	@Autowired
	private MyBatisDao myBatisDao;

	/**
	 * 分页列出所有参数记录
	 * 
	 * @param param
	 * @return
	 */
	public List<SysParamInfo> getSysParamPages(Object param) {
		this.logger.info("分页列出所有记录");
		return myBatisDao.getList("SysParamInfoMapper.listPageSysParam", param);
	}
	/**
	 * 根据参数名称查询
	 * @param paramName
	 * @return
	 */
	public SysParamInfo getSysParamByParamName(String paramName){
		return myBatisDao.get("SysParamInfoMapper.getSysParamByParamName", paramName);
	}
	/**
	 * 列出所有参数记录
	 * 
	 * @param param
	 * @return
	 */
	public List<SysParamInfo> getSysParams(Object param) {
		return myBatisDao.getList("SysParamInfoMapper.listAllSysParam", param);
	}

	/**
	 * 根据id获得相关参数记录
	 * 
	 * @param id
	 * @return
	 */
	public SysParamInfo getSysParam(Serializable id) {
		return myBatisDao.get("SysParamInfoMapper.getSysParamById", id);
	}

	/**
	 * 保存系统参数
	 * 
	 * @param param
	 */
	public void save(Object param) {
		myBatisDao.save("SysParamInfoMapper.insertSysParam", param);
	}

	/**
	 * 更新系统参数
	 * 
	 * @param param
	 */
	public void update(Object param) {
		myBatisDao.save("SysParamInfoMapper.updateSysParam", param);
	}

	/**
	 * 根据id删除相关系统参数
	 * 
	 * @param id
	 */
	public void delete(Serializable id) {
		myBatisDao.delete("SysParamInfoMapper.deleteSysParam", id);
	}

}
