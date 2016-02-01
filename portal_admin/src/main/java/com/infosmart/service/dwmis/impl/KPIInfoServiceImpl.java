package com.infosmart.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisRelateKpiInfo;
import com.infosmart.service.dwmis.KPIInfoService;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class KPIInfoServiceImpl extends BaseServiceImpl implements
		KPIInfoService {
	@Override
	public List<DwmisKpiInfo> getKPIInfo(DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo == null) {
			this.logger.warn("查询所有指标信息失败：指标为空！");
			return null;
		}
		return myBatisDao.getList("dwmisKpiInfoMapper.listKpiInfo",
				dwmisKpiInfo);
	}

	@Override
	public void addKpiInfo(DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo == null) {
			this.logger.warn("添加KpiInfo失败：DwmisKpiInfo参数为空");
			return;
		}
		this.myBatisDao.save("dwmisKpiInfoMapper.addKpiInfo", dwmisKpiInfo);

	}

	@Override
	public void updateKpiInfo(DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo == null) {
			this.logger.warn("更新KpiInfo失败:参数dwmisKpiInfo为空");
			return;
		}
		this.myBatisDao
				.update("dwmisKpiInfoMapper.updateKpiInfo", dwmisKpiInfo);

	}

	@Override
	public void deleteKpiInfo(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("删除KpiInfo失败:参数kpiCode为空");
			return;
		}
		this.myBatisDao.delete("dwmisKpiInfoMapper.deleteRelateKpiInfo",
				kpiCode);
		this.myBatisDao.delete("dwmisKpiInfoMapper.deleteKpiInfo", kpiCode);
	}

	@Override
	public DwmisKpiInfo selectKpiInfo(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("根据KpiCode查询指标信息失败：KpiCode为空！");
			return null;
		}
		return this.myBatisDao.get("dwmisKpiInfoMapper.selectKpiInfo", kpiCode);
	}

	@Override
	public List<DwmisKpiInfo> getRelateKpiInfo(String relateKpiCode) {
		if (StringUtils.isBlank(relateKpiCode)) {
			this.logger.warn("查询关联指标信息失败：关联指标Code为空");
			return null;
		}
		return this.myBatisDao
				.getList("dwmisKpiInfoMapper.listKpiInforByRelateKpiCode",
						relateKpiCode);
	}

	@Override
	public void deleteRelateKpiInfo(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("删除RelateKpiInfo失败:参数kpiCode为空");
			return;
		}
		this.myBatisDao.delete("dwmisKpiInfoMapper.deleteRelateKpiInfo",
				kpiCode);
	}

	@Override
	public void updateRelateKpiInfo(String kpiCode, String linkedKpiCode) {
		if (StringUtils.isBlank(kpiCode) || StringUtils.isBlank(linkedKpiCode)) {
			this.logger
					.warn("updateRelateKpiInfo方法失败:参数linkedKpiCode或者kpiCode为空");
			return;
		}
		String[] str = linkedKpiCode.split(",");
		DwmisRelateKpiInfo dwmisRelateKpiInfo = null;
		List<DwmisRelateKpiInfo> kpiInfoList = new ArrayList<DwmisRelateKpiInfo>();
		if (str.length > 0) {
			for (int i = 1; i < str.length; i++) {
				dwmisRelateKpiInfo = new DwmisRelateKpiInfo();
				dwmisRelateKpiInfo.setKpiCode(kpiCode);
				dwmisRelateKpiInfo.setLinkedKpiCode(str[i]);
				kpiInfoList.add(dwmisRelateKpiInfo);
			}
			if (kpiInfoList.isEmpty()) {
				this.logger
						.warn("updateRelateKpiInfo方法中insertRelateKpiInfos方法失败:kpiInfoList参数个数为0");
				return;
			}
			this.myBatisDao.save("dwmisKpiInfoMapper.insertRelateKpiInfos",
					kpiInfoList);
		}
	}

	/**
	 * 分页
	 */
	@Override
	public List<DwmisKpiInfo> getKPIInfoPages(DwmisKpiInfo dwmisKpiInfo) {
		if (dwmisKpiInfo == null) {
			this.logger.warn("查询指标信息分页失败：指标为空！");
			return null;
		}
		return myBatisDao.getList("dwmisKpiInfoMapper.listPageKpiInfo",
				dwmisKpiInfo);
	}

	@Override
	public DwmisKpiInfo seleKpiInfoByName(String kpiName) {
		if (StringUtils.isBlank(kpiName)) {
			this.logger.warn("根据KpiName查询指标信息失败：KpiCode为空！");
			return null;
		}
		return this.myBatisDao.get("dwmisKpiInfoMapper.selectKpiInfoByName",
				kpiName);
	}

	@Override
	public List<DwmisKpiInfo> searchNotRelateBySetValue(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("根据指标Code查询为关联指标失败：指标Code为空!");
			return null;
		}
		return this.myBatisDao.getList(
				"dwmisKpiInfoMapper.searchNotRelateBySetValue", kpiCode);
	}

	@Override
	public List<DwmisKpiInfo> searchNotRelateKpiCodes(String kpiCode,
			String linkedKpiCode) {
		String[] str = linkedKpiCode.split(",");
		List<String> kpicodesList = new ArrayList<String>();
		if (str.length > 0) {
			for (int i = 1; i < str.length; i++) {
				kpicodesList.add(str[i]);
			}
		}
		kpicodesList.add("");
		Map map = new HashMap();
		map.put("kpiCode", kpiCode);
		map.put("linkedKpiCodes", kpicodesList);
		return this.myBatisDao.getList(
				"dwmisKpiInfoMapper.searchNotRelateKpiCodes", map);
	}

	@Override
	public List<DwmisKpiInfo> getKpiInfoByNameOrCode(DwmisKpiInfo dwmisKpiInfo) {
		return this.myBatisDao
				.get("dwmisKpiInfoMapper.getKpiInfoByKpiNameOrKpiCode",
						dwmisKpiInfo);
	}
}
