package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisLegendInfo;

public interface LegendInfoService {

	/**
	 * 查询所有图例信息
	 * 
	 * @return
	 */
	public List<DwmisLegendInfo> getAllLegendInfos();

	public List<DwmisLegendInfo> list(DwmisLegendInfo dwmisLegendInfo);

	public void add(DwmisLegendInfo dwmisLegendInfo);

	/**
	 * 保存图例信息及关联的指标信息
	 * 
	 * @param dwmisLegendInfo
	 */
	void saveDwmisLegendInfo(DwmisLegendInfo dwmisLegendInfo);

	public void edit(DwmisLegendInfo dwmisLegendInfo);

	public void del(String legendId);

	public DwmisLegendInfo getById(String legendId);

	public List<DwmisKpiInfo> getUnRelativeDwmisKpiInfoByKey(String legendId,
			String keyCode) throws Exception;

	/**
	 * 新增图例指标关联表数据
	 * 
	 * @param kpiCodes
	 * @param legendId
	 */
	public void addLegendKpiR(String[] kpiCodes, String legendId);

	/**
	 * 删除图例指标关联表数据
	 * 
	 * @param legendId
	 */
	public void delLegendKpiR(String legendId);

	public List<DwmisKpiInfo> getKpiInfoByLegendId(String legendId);
}
