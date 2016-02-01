package com.infosmart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCSysType;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.ParentTypeRelKpiService;
import com.infosmart.util.StringUtils;

@Service
public class ParentTypeRelKpiServiceImp extends BaseServiceImpl implements
		ParentTypeRelKpiService {

	@Override
	public List<DwpasCSysType> getAllParentKpi() throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("groupId", "1000");
		return this.myBatisDao
				.getList("com.infosmart.mapper.ParentTypeRelKpi.getParentKpis",
						paramMap);
	}

	@Override
	public List<DwpasCKpiInfo> getRelativeKpi(String typeId) throws Exception {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("查询大类关联指标失败：typeId为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.mapper.ParentTypeRelKpi.getRelativeKpi", typeId);
	}

	@Override
	public List<DwpasCKpiInfo> getUnRelativeKpi(String typeId) throws Exception {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("查询大类不关联指标失败：typeId为空！");
			return null;
		}
		
		return this.myBatisDao.getList(
				"com.infosmart.mapper.ParentTypeRelKpi.getUnRelativeKpi", typeId);
	}

	@Override
	public boolean insertParentKPI(MisTypeInfo parentKpi) throws Exception {
		if (parentKpi == null) {
			this.logger.warn("新增大类指标失败：传入大类指标为空");
			return false;
		}
		parentKpi.setGroupId("1000");
		Integer typeId = (Integer) this.myBatisDao.get(
				"com.infosmart.mapper.ParentTypeRelKpi.getTypeIdByGroupId",
				parentKpi);
		if (typeId == null) {
			typeId = 1001;
		} else {
			typeId = typeId + 1;
		}
		parentKpi.setTypeId(String.valueOf(typeId));
		try {
			this.myBatisDao.save(
					"com.infosmart.mapper.ParentTypeRelKpi.insertParentKPI",
					parentKpi);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public void updateRelateKpiInfo(String typeId, List<String> kpiCodeList)
			throws Exception {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("修改与此大类指标关联的指标失败:typeId为空！");
			throw new Exception("修改与此大类指标关联的指标失败:typeId为空！");
		}
		Map map = new HashMap();
		map.put("typeId", typeId);
		map.put("kpiCodeList", kpiCodeList);
		this.myBatisDao.update(
				"com.infosmart.mapper.ParentTypeRelKpi.updateRelateKpiInfo",
				map);
	}

	@Override
	public void updateUnRelateKpiInfo(String typeId, List<String> kpiCodeList)
			throws Exception {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("修改与此大类指标不关联的指标失败:typeId为空！");
			throw new Exception("修改与此大类指标不关联的指标失败:typeId为空！");
		}
		Map map = new HashMap();
		map.put("typeId", typeId);
		map.put("kpiCodeList", kpiCodeList);
		this.myBatisDao.update(
				"com.infosmart.mapper.ParentTypeRelKpi.updateUnRelateKpiInfo",
				map);
	}

	@Override
	public MisTypeInfo getParentKpiByid(String typeId) {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("通过typeId查询大类指标失败：typeId为空");
			return null;
		}
		MisTypeInfo parentKpi = new MisTypeInfo();
		parentKpi = this.myBatisDao.get(
				"com.infosmart.mapper.ParentTypeRelKpi.getParentKpiByid",
				typeId);
		return parentKpi;
	}

	@Override
	public boolean updateParentKpi(MisTypeInfo parentKpi) throws Exception {
		if (parentKpi == null) {
			this.logger.warn("更新大类指标信息失败：大类指标为空");
			return false;
		}
		try {
			this.myBatisDao.update(
					"com.infosmart.mapper.ParentTypeRelKpi.updateParentKpi",
					parentKpi);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteParentKpi(String typeId) {
		if (!StringUtils.notNullAndSpace(typeId)) {
			this.logger.warn("删除大类指标失败：typeId为空");
			return false;
		}
		try {
			this.myBatisDao.delete(
					"com.infosmart.mapper.ParentTypeRelKpi.deleteParentKpi",
					typeId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public List<DwpasCKpiInfo> getUnRelativeKpiBycodes(List<String> codeList) {
		if (codeList.isEmpty() && codeList.size() <= 0) {
			this.logger.warn("通过指标Codes查询指标信息失败：指标Codes为空");
			return null;
		}
		return this.myBatisDao.getList(
				"com.infosmart.ParentTypeRelKpi.getUnRelativeKpiBycodes",
				codeList);
	}

	/**
	 * 搜索时查询未关联指标
	 */
	@Override
	public List<DwpasCKpiInfo> getUnRelKpiBySerach(String keyCode,
			List<String> kpiCodeList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyCode", keyCode);
		map.put("kpiCodeList", kpiCodeList);
		return this.myBatisDao.getList(
				"com.infosmart.ParentTypeRelKpi.getUnRelKpiBySerach", map);
	}

}
