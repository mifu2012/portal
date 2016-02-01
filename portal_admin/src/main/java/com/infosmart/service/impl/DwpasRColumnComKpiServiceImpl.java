package com.infosmart.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasRColumnComKpi;
import com.infosmart.service.DwpasRColumnComKpiService;

@Service
public class DwpasRColumnComKpiServiceImpl extends BaseServiceImpl implements
		DwpasRColumnComKpiService {

	@Override
	public void saveDwpasRColumnComKpi(Map<String, Integer> map)
			throws Exception {
		if (map == null || map.isEmpty()) {
			this.logger.warn("saveDwpasRColumnComKpi方法失败:参数map为空");
			throw new Exception("saveDwpasRColumnComKpi方法失败:参数map为空");
		}
		myBatisDao.save("DwpasRColumnComKpiMapper.copyDwpasRColumnComKpi", map);
	}

	@Override
	public void deleteDwpasRColumnComKpi(List<String> columnId)
			throws Exception {
		if (columnId == null || columnId.isEmpty()) {
			this.logger.warn("deleteDwpasRColumnComKpi方法失败:columnId参数列表为空");
			throw new Exception("deleteDwpasRColumnComKpi方法失败:columnId参数列表为空");
		}
		myBatisDao.delete("DwpasRColumnComKpiMapper.deleteDwpasRColumnComKpi",
				columnId);
	}

	@Override
	public List<DwpasRColumnComKpi> getDwpasRColumnComKpiByTidAndCode(
			String columnId) {
		if (StringUtils.isBlank(columnId)) {
			this.logger.warn("getDwpasRColumnComKpiByTidAndCode方法失效:栏目Id为空！");
			return null;
		}
		return myBatisDao.getList(
				"DwpasRColumnComKpiMapper.getDwpasRColumnComKpiByColumnId",
				columnId);
	}

	@Override
	public void insertCommonCode(DwpasRColumnComKpi dwpasRColumnComKpi)
			throws Exception {
		if (dwpasRColumnComKpi == null) {
			this.logger.warn("insertCommonCode方法失败:参数dwpasRColumnComKpi为空");
			throw new Exception("insertCommonCode方法失败:参数dwpasRColumnComKpi为空");
		}
		myBatisDao.save("DwpasRColumnComKpiMapper.insertCommoncode",
				dwpasRColumnComKpi);
	}

	@Override
	public void deleteCommonCode(String columnId) throws Exception {
		if (StringUtils.isBlank(columnId)) {
			this.logger.warn("deleteCommonCode方法失败：参数columnId为空");
			throw new Exception("deleteCommonCode方法失败：参数columnId为空");
		}
		myBatisDao
				.delete("DwpasRColumnComKpiMapper.deleteCommoncode", columnId);
	}

}
