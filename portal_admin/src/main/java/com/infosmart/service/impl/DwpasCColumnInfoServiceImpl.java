package com.infosmart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCComKpiInfo;
import com.infosmart.po.DwpasCmoduleInfo;
import com.infosmart.po.DwpasRColumnComKpi;
import com.infosmart.service.DwpasCColumnInfoService;
import com.infosmart.util.StringUtils;

@Service
public class DwpasCColumnInfoServiceImpl extends BaseServiceImpl implements
		DwpasCColumnInfoService {

	@Override
	public List<DwpasCColumnInfo> listCColumnInfoAndRelCommKpiInfoByModuleId(
			String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("根据栏目Id查询栏目信息失败：模块Id为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.listColumnAndRelCommKpiInfoByModuleId",
				moduleId);
	}

	@Override
	public void saveDwpasCColumnInfoByBatch(DwpasCmoduleInfo moduleInfo)
			throws Exception {
		// TODO Auto-generated method stub
		if (moduleInfo == null || moduleInfo.getColumnlist() == null
				|| moduleInfo.getColumnlist().isEmpty()) {
			this.logger.warn("批量保存模块关联的指标失败：参数为空");
			throw new Exception("批量保存模块关联的指标失败：参数为空");
		}
		this.logger.info("当前栏目ID:" + moduleInfo.getModuleId());
		try {
			if (!StringUtils.notNullAndSpace(moduleInfo.getModuleId())) {
				this.logger.info("保存新的模块信息:" + moduleInfo.getModuleName());
				moduleInfo.setModuleId(UUID.randomUUID().toString());
				// 保存模块信息
				this.myBatisDao.save(
						"DwpasCModuleInfoMapper.insertModuleAllInfo",
						moduleInfo);
			} else {
				this.logger.info("批量更新模块关联的指标：" + moduleInfo.getModuleName());
				// 删除栏目关联通用指标
				this.myBatisDao
						.delete("DwpasRColumnComKpiMapper.deleteDwpasRColumnComKpiByModuleId",
								moduleInfo.getModuleId());
				// 删除栏目信息
				List<String> moduleIdList = new ArrayList<String>();
				moduleIdList.add(moduleInfo.getModuleId());
				myBatisDao.delete(
						"DwpasCColumnInfoMapper.deleteDwpasCColumnInfo",
						moduleIdList);
			}
			DwpasRColumnComKpi rColumnComKpi = null;
			// 保存栏目信息
			List<DwpasCColumnInfo> columnInfoList = moduleInfo.getColumnlist();
			for (DwpasCColumnInfo columnInfo : columnInfoList) {
				if (columnInfo == null
						|| !StringUtils.notNullAndSpace(columnInfo
								.getColumnName()))
					continue;
				if (!StringUtils.notNullAndSpace(columnInfo.getColumnCode())) {
					columnInfo.setColumnCode(UUID.randomUUID().toString());
				}
				columnInfo.setColumnId(UUID.randomUUID().toString());
				columnInfo.setIsShow(1);
				columnInfo.setGmtCreate(new Date());
				columnInfo.setModuleId(moduleInfo.getModuleId());
				this.myBatisDao.save("DwpasCColumnInfoMapper.insertColumnInfo",
						columnInfo);
				String relComKpiCode = columnInfo.getRelCommKpiCodes();
				if (StringUtils.notNullAndSpace(relComKpiCode)) {
					String relComKpyCodeArray[] = relComKpiCode.split(",");
					Integer orderNum = 1;
					for (String commKpiCode : relComKpyCodeArray) {
						if (!StringUtils.notNullAndSpace(commKpiCode))
							continue;
						String ComKpiAndProduct[] = commKpiCode.split("\\^");

						rColumnComKpi = new DwpasRColumnComKpi();

						// 关联的模块
						rColumnComKpi.setColumnId(columnInfo.getColumnId());
						if (ComKpiAndProduct.length > 0) {
							rColumnComKpi.setComKpiCode(ComKpiAndProduct[0]);
						} else {
							rColumnComKpi.setComKpiCode("-");
						}
						if (ComKpiAndProduct.length > 1) {
							rColumnComKpi.setValue2(ComKpiAndProduct[1]);
						} else {
							rColumnComKpi.setValue2("-");
						}

						// setValue2存放productId
						rColumnComKpi.setGmtCreate(new Date());
						rColumnComKpi.setOrderNum(orderNum);
						orderNum += 1;
						// 保存关联的通用指标信息
						this.myBatisDao.save(
								"DwpasRColumnComKpiMapper.insertCommoncode",
								rColumnComKpi);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("批量保存模块关联的栏目信息失败：" + e.getMessage(), e);
			throw e;

		}

	}

	@Override
	public List<DwpasCColumnInfo> listPageDwpasCColumnInfo(
			DwpasCColumnInfo columnInfo) {
		if (columnInfo == null) {
			this.logger.warn("查询栏目信息失败:栏目为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.listPageDwpasCColumnInfo", columnInfo);
	}

	@Override
	public void saveDwpasCColumnInfo(Map<String, Integer> map) throws Exception {
		if (map == null || map.isEmpty()) {
			this.logger.warn("saveDwpasCColumnInfo失败：参数map为空");
			throw new Exception("保存通用指标信息失败：参数map为空");
		}
		myBatisDao.save("DwpasCColumnInfoMapper.copyDwpasCColumnInfo", map);
	}

	@Override
	public void deleteDwpasCColumnInfo(List<String> moduleId) throws Exception {
		if (moduleId == null || moduleId.isEmpty()) {
			this.logger.warn("deleteDwpasCColumnInfo方法失败:参数moduleId为空");
			throw new Exception("deleteDwpasCColumnInfo方法失败:参数moduleId为空");
		}
		myBatisDao.delete("DwpasCColumnInfoMapper.deleteDwpasCColumnInfo",
				moduleId);
	}

	@Override
	public List<DwpasCColumnInfo> getCColumnInfoByModuleId(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("根据栏目Id查询栏目信息失败：栏目Id为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.getCColumnInfoByModuleId", moduleId);
	}

	@Override
	public List<DwpasCComKpiInfo> getAllDwpasCComKpiInfo(
			DwpasCComKpiInfo dwpasCComKpiInfo) {
		return myBatisDao.getList("DwpasCColumnInfoMapper.alldwpasccomkpiinfo",
				dwpasCComKpiInfo);
	}

	@Override
	public List<DwpasCColumnInfo> getMoBan() {
		return myBatisDao.getList("DwpasCColumnInfoMapper.mobanlist");
	}

	@Override
	public void insertColumnInfo(DwpasCColumnInfo columnInfo) throws Exception {
		if (columnInfo == null) {
			this.logger.warn("insertColumnInfo方法失败:参数columnInfo为空");
			throw new Exception("insertColumnInfo方法失败:参数columnInfo为空");
		}
		myBatisDao.save("DwpasCColumnInfoMapper.insertDwpasCColumnInfo",
				columnInfo);
	}

	@Override
	public void updateColumnInfo(DwpasCColumnInfo columnInfo) throws Exception {
		if (columnInfo == null) {
			this.logger.warn("updateColumnInfo失败:参数columnInfo为空");
			throw new Exception("updateColumnInfo失败:参数columnInfo为空");
		}
		myBatisDao.update("DwpasCColumnInfoMapper.updateColumn", columnInfo);
	}

	@Override
	public List<DwpasCColumnInfo> getDaleiByModuleId(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("根据栏目Id查询栏目大类失败：栏目Id为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.getColumnINfoKindByModuleId", moduleId);
	}

	@Override
	public List<DwpasCColumnInfo> getBYModuleIds(List<String> moduleIds) {
		if (moduleIds == null || moduleIds.isEmpty()) {
			this.logger.warn("查询栏目信息失败：栏目Ids为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.getDwpasCColumnInfoByColumnIds",
				moduleIds);
	}

	@Override
	public List<DwpasCColumnInfo> getAllColumnInfoAndComkpilist(String moduleId) {
		if (!StringUtils.notNullAndSpace(moduleId)) {
			this.logger.warn("根据栏目Id查询栏目信息及指标信息失败：栏目Id为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasCColumnInfoMapper.getAllCColumnInfoListByModuleId",
				moduleId);
	}

}
