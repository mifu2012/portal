package com.infosmart.service.dwmis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.po.dwmis.DwmisLegendInfo;
import com.infosmart.service.dwmis.LegendInfoService;
import com.infosmart.service.impl.BaseServiceImpl;
import com.infosmart.util.StringUtils;

@Service
public class LegendInfoServiceImpl extends BaseServiceImpl implements
		LegendInfoService {

	/**
	 * 图例信息列表
	 */
	@Override
	public List<DwmisLegendInfo> list(DwmisLegendInfo dwmisLegendInfo) {
		return this.myBatisDao.getList(
				"com.infosmart.DwmisLegendInfoMapper.listPageLegendInfo",
				dwmisLegendInfo);
	}

	/**
	 * 保存图例信息并关联指标
	 */
	@Override
	public void saveDwmisLegendInfo(DwmisLegendInfo dwmisLegendInfo) {
		if (dwmisLegendInfo == null) {
			this.logger.warn("保存图例与图例与指标关联信息失败，参数为空");
			return;
		}
		if (dwmisLegendInfo.getLegendId() == null
				|| dwmisLegendInfo.getLegendId() == "") {
			String id = UUID.randomUUID().toString().replace("-", "");
			dwmisLegendInfo.setLegendId(id);
			this.logger.info("新增图例信息");
			this.myBatisDao.save(
					"com.infosmart.DwmisLegendInfoMapper.addLegendInfo",
					dwmisLegendInfo);
		} else {
			this.logger.info("编辑图例信息:" + dwmisLegendInfo.getLegendId());
			this.myBatisDao.update(
					"com.infosmart.DwmisLegendInfoMapper.updateLegendInfo",
					dwmisLegendInfo);
			this.logger.info("删除原来关联的指标信息:" + dwmisLegendInfo.getLegendId());
			this.myBatisDao.delete(
					"com.infosmart.DwmisLegendInfoMapper.delLegendKpiInfo",
					dwmisLegendInfo.getLegendId());
		}
		DwmisKpiInfo dwmisKpiInfo = null;
		List<DwmisKpiInfo> kpiInfoList = new ArrayList<DwmisKpiInfo>();
		if (dwmisLegendInfo.getRelateKpiCodeList() != null) {
			this.logger.info("保存图例与指标关联信息:" + dwmisLegendInfo.getCategoryId());
			for (int i = 0; i < dwmisLegendInfo.getRelateKpiCodeList().size(); i++) {
				dwmisKpiInfo = new DwmisKpiInfo();
				dwmisKpiInfo.setLegendId(dwmisLegendInfo.getLegendId());
				dwmisKpiInfo.setKpiCode(dwmisLegendInfo.getRelateKpiCodeList()
						.get(i));
				kpiInfoList.add(dwmisKpiInfo);
			}
			this.myBatisDao.save(
					"com.infosmart.DwmisLegendInfoMapper.insertLegendKpiInfo",
					kpiInfoList);
		}
	}

	/**
	 * 新增图例信息
	 */
	@Override
	public void add(DwmisLegendInfo dwmisLegendInfo) {
		this.myBatisDao.getList(
				"com.infosmart.DwmisLegendInfoMapper.addLegendInfo",
				dwmisLegendInfo);
	}

	/**
	 * 修改图例信息
	 */
	@Override
	public void edit(DwmisLegendInfo dwmisLegendInfo) {
		this.myBatisDao.getList(
				"com.infosmart.DwmisLegendInfoMapper.updateLegendInfo",
				dwmisLegendInfo);
	}

	/**
	 * 删除图例信息
	 */
	@Override
	public void del(String legendId) {
		this.myBatisDao.getList(
				"com.infosmart.DwmisLegendInfoMapper.delLegendInfo", legendId);
	}

	/**
	 * 根据ID查询图例信息
	 */
	@Override
	public DwmisLegendInfo getById(String legendId) {
		return this.myBatisDao.get(
				"com.infosmart.DwmisLegendInfoMapper.getLegendInfoById",
				legendId);
	}

	@Override
	public List<DwmisKpiInfo> getUnRelativeDwmisKpiInfoByKey(String legendId,
			String keyCode) throws Exception {
		if (!StringUtils.notNullAndSpace(legendId)) {
			this.logger.warn("查询图例不关联指标信息失败：图例Id为空");
			return null;
		}
		List<DwmisKpiInfo> dwmisKpiInfos = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("legendId", legendId);
		map.put("keyCode", keyCode);
		dwmisKpiInfos = this.myBatisDao
				.getList(
						"com.infosmart.DptmentRelKpiMapper.getUnRelativeDwmisKpiInfoByKeyCode",
						map);
		return dwmisKpiInfos;
	}

	/**
	 * 新增图例信息管理指标
	 */
	@Override
	public void addLegendKpiR(String[] kpiCodes, String legendId) {
		if (kpiCodes == null || kpiCodes.length == 0) {
			this.logger.error("新增图例与指标关联信息失败，参数错误");
			return;
		}
		if (!StringUtils.notNullAndSpace(legendId)) {
			this.logger.error("新增图例与指标关联信息失败，参数错误");
			return;
		}
		DwmisKpiInfo dwmisKpiInfo = null;
		List<DwmisKpiInfo> kpiInfoList = new ArrayList<DwmisKpiInfo>();
		if (kpiCodes.length > 0) {
			for (int i = 0; i < kpiCodes.length; i++) {
				dwmisKpiInfo = new DwmisKpiInfo();
				dwmisKpiInfo.setLegendId(legendId);
				dwmisKpiInfo.setKpiCode(kpiCodes[i]);
				kpiInfoList.add(dwmisKpiInfo);
			}
		}
		try {
			this.myBatisDao.save(
					"com.infosmart.DwmisLegendInfoMapper.insertLegendKpiInfo",
					kpiInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 删除图例信息
	 */
	@Override
	public void delLegendKpiR(String legendId) {
		try {
			this.myBatisDao.delete(
					"com.infosmart.DwmisLegendInfoMapper.delLegendKpiInfo",
					legendId);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据ID查询图例信息
	 */
	@Override
	public List<DwmisKpiInfo> getKpiInfoByLegendId(String legendId) {
		return this.myBatisDao.getList(
				"dwmisKpiInfoMapper.getKpiInfoByLegendId", legendId);
	}

	/**
	 * 查询所有图例信息
	 */
	@Override
	public List<DwmisLegendInfo> getAllLegendInfos() {
		return this.myBatisDao
				.getList("com.infosmart.DwmisLegendInfoMapper.getAllLegendInfos");
	}

}
