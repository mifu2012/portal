package com.infosmart.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.ComKpiInfo;
import com.infosmart.po.DwpasCColumnInfo;
import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasRColumnComKpi;
import com.infosmart.po.KpiComKpiInfo;
import com.infosmart.po.KpiInfo;
import com.infosmart.service.ComKpiInfoService;

@Service
public class ComKpiInfoServiceImpl extends BaseServiceImpl implements
		ComKpiInfoService {

	@Override
	public List<ComKpiInfo> listCommKpiInfo(String moduleId) {
		if (StringUtils.isBlank(moduleId) || StringUtils.isEmpty(moduleId)) {
			this.logger.warn("查询失败，参数为空！");
			return null;
		}
		// getDwpasRColumnComKpiByModuleId
		return this.myBatisDao.getList(
				"ComKpiInfoMapper.getDwpasRColumnComKpiByModuleId", moduleId);
	}

	@Override
	public void saveCommKpiOfModule(DwpasCColumnInfo columnInfo,
			List<String> comKpiList, String moduleId) throws Exception {
		// TODO Auto-generated method stub
		// 删除产品排行关联的栏目
		this.myBatisDao.delete(
				"ComKpiInfoMapper.deleteComKpi_r_columnByModuleId", moduleId);
		// 删除栏目关联的指标
		this.myBatisDao.delete("ComKpiInfoMapper.deleteColumnInfoByModuleId",
				moduleId);
		// 新增关联的栏目
		columnInfo.setColumnId(UUID.randomUUID().toString());
		columnInfo.setModuleId(moduleId);
		this.myBatisDao
				.save("ComKpiInfoMapper.insertLhbColumnInfo", columnInfo);
		// 新增栏目关联的指标
		List<DwpasRColumnComKpi> columnRKpiList = new ArrayList<DwpasRColumnComKpi>();
		int seq = 0;
		if (comKpiList != null) {
			for (String comKpiCode : comKpiList) {
				if (StringUtils.isBlank(comKpiCode)
						|| StringUtils.isEmpty(comKpiCode))
					continue;
				columnRKpiList.add(new DwpasRColumnComKpi(columnInfo
						.getColumnId(), comKpiCode, seq));
				seq++;
			}
		}
		this.myBatisDao.save("ComKpiInfoMapper.insertComKpi_r_column",
				columnRKpiList);
	}

	@Override
	public List<ComKpiInfo> listPageComKpiInfo(ComKpiInfo cComKpiInfoDO) {
		if (cComKpiInfoDO == null) {
			this.logger.warn("获取通用指标失败，参数为空！");
			return null;
		}
		List<ComKpiInfo> cComKpiInfoDOList = new ArrayList<ComKpiInfo>();
		try {
			cComKpiInfoDOList = myBatisDao.getList(
					"ComKpiInfoMapper.listPageComKpiInfo", cComKpiInfoDO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询通用指标信息列表失败", e);
		}
		return cComKpiInfoDOList;
	}

	@Override
	/**
	 * 获得通用指标信息列表
	 */
	public List<ComKpiInfo> searchCommonKPI() {
		List<ComKpiInfo> cComKpiInfoDOList = new ArrayList<ComKpiInfo>();
		try {
			cComKpiInfoDOList = myBatisDao
					.getList("ComKpiInfoMapper.ComKpiInfoQueryAll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询通用指标信息列表失败", e);
		}
		return cComKpiInfoDOList;
	}

	@Override
	/**
	 * 通用指标管理新增
	 */
	public boolean insertCommonKPIConfig(ComKpiInfo cComKpiInfoDO) {
		boolean flag = false;
		if (cComKpiInfoDO == null) {
			logger.warn("插入通用指标参数失败:通用指标新增参数为null");
		} else {
			try {
				// 通用指标新增
				myBatisDao.save("ComKpiInfoMapper.ComKpiInfoInsert",
						cComKpiInfoDO);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通用指标新增失败", e);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 通用指标管理修改
	 */
	public boolean saveCommonKPIConfig(ComKpiInfo cComKpiInfoDO) {
		boolean flag = false;
		if (cComKpiInfoDO == null) {
			logger.warn("通用指标管理修改失败：通用指标修改参数为null");
		} else {
			final ComKpiInfo finDwpasCComKpiInfoDO = cComKpiInfoDO;
			try {
				// 通用指标修改
				// 通用指标删除
				myBatisDao.delete("ComKpiInfoMapper..ComKpiInfoDelete",
						finDwpasCComKpiInfoDO.getComKpiCode());
				// 通用指标新增
				myBatisDao.save("ComKpiInfoMapper.ComKpiInfoInsert",
						finDwpasCComKpiInfoDO);

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通用指标新增失败", e);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 通用指标删除
	 */
	public boolean deleteCommonKPI(String comKpiCode) throws Exception {
		boolean flag = false;
		if (StringUtils.isBlank(comKpiCode)) {
			logger.warn("通用指标删除参数通用指标code为null");
		} else {
			final KpiComKpiInfo DwpasRKpiComkpiDO = new KpiComKpiInfo();
			DwpasRKpiComkpiDO.setComKpiCode(comKpiCode);
			final String falComKpiCode = comKpiCode;
			try {

				// 通用指标关联指标删除
				myBatisDao.delete(
						"KpiComKpiInfoMapper.KpiComKpiInfoDeleteByComKpiCode",
						falComKpiCode);
				// 通用指标删除
				myBatisDao.delete("ComKpiInfoMapper.ComKpiInfoDelete",
						falComKpiCode);

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通用指标删除失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 通用指标相关联指标保存
	 */
	public boolean insertKPIRelation(String comKpiCode, String kpiCodes)
			throws Exception {
		boolean flag = false;
		if (StringUtils.isBlank(comKpiCode) || StringUtils.isBlank(kpiCodes)) {
			this.logger.warn("通用指标相关联指标保存失败：参数comKpiCode或者kpiCodes为空");
		} else {
			final String falKpiCodes = kpiCodes;
			final String falcomKpiCode = comKpiCode;
			try {

				// 删除通用指标相关联指标
				myBatisDao
						.delete("KpiComKpiInfoMapper.KpiComKpiInfoQueryAllByComKpiCode",
								falcomKpiCode);
				if (!StringUtils.isEmpty(falKpiCodes)) {
					final String[] kpiCodes1 = falKpiCodes.split("!@!");
					for (int i = 0; i < kpiCodes1.length; i++) {
						// ********新增通用指标相关联指标addKPIRelation
						KpiComKpiInfo tempDwpasRKpiComkpiDO = new KpiComKpiInfo();
						tempDwpasRKpiComkpiDO.setComKpiCode(falcomKpiCode);
						tempDwpasRKpiComkpiDO.setKpiCode(kpiCodes1[i]);
						myBatisDao.save(
								"KpiComKpiInfoMapper.KpiComKpiInfoInsert",
								tempDwpasRKpiComkpiDO);
					}
				}

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通用指标相关联指标保存失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 获得通用指标信息
	 */
	public List<ComKpiInfo> getCommonKPIInfo(String comKpiCode) {
		List<ComKpiInfo> comList = null;
		if (StringUtils.isBlank(comKpiCode)) {
			logger.warn("获得通用指标信息失败:查询通用指标信息传递参数通用指标code为null");
		} else {
			try {
				myBatisDao.getList("ComKpiInfoMapper.ComKpiInfoQueryByCode",
						comKpiCode);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获得通用指标信息失败", e);
			}
		}
		return comList;
	}

	@Override
	/**
	 * 分别获得指定通用指标相关联指标列表及不关联指标列表
	 */
	public List<ComKpiInfo> getKPIRelationAndKPI(String comKpiCode) {
		List<KpiInfo> rKpiAndComkpiList = new ArrayList<KpiInfo>();
		KpiInfo DwpasCKpiInfoDO = null;
		KpiComKpiInfo DwpasRKpiComkpiDO = null;
		List<KpiInfo> cKpiInfoList = null;
		try {
			cKpiInfoList = myBatisDao.getList("KpiInfoMapper.KpiInfoQueryAll");
			List<KpiComKpiInfo> rKpiComkpiList = myBatisDao.getList(
					"KpiComKpiInfoMapper.KpiComKpiInfoQueryAllByComKpiCode",
					comKpiCode);
			for (int i = 0; i < cKpiInfoList.size(); i++) {
				DwpasCKpiInfoDO = cKpiInfoList.get(i);
				for (int j = 0; j < rKpiComkpiList.size(); j++) {
					DwpasRKpiComkpiDO = rKpiComkpiList.get(j);
					if (StringUtils.equals(DwpasCKpiInfoDO.getKpiCode(),
							DwpasRKpiComkpiDO.getKpiCode())) {
						cKpiInfoList.remove(i);
						i--;
						rKpiAndComkpiList.add(DwpasCKpiInfoDO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得指定通用指标相关联指标列表及不关联指标列表时失败", e);
		}
		// map.put("cKpiInfoList", cKpiInfoList);
		// map.put("rKpiAndComkpiList", rKpiAndComkpiList);
		// return List<ComKpiInfo>;
		return null;
	}

	@Override
	public void updateComKpiInfo(ComKpiInfo cComKpiInfoDO) throws Exception {
		if (cComKpiInfoDO == null) {
			this.logger.warn("更新通用指标信息失败:参数cComKpiInfoDO为空");
			throw new Exception("更新通用指标信息失败:参数cComKpiInfoDO为空");
		}
		myBatisDao.update("ComKpiInfoMapper.updateComKpiinfo", cComKpiInfoDO);
	}

	@Override
	public ComKpiInfo getComkpiInfo(String comKpiCode) {
		if (StringUtils.isBlank(comKpiCode)) {
			this.logger.warn("查询通用指标信息失败：通用指标Code为空！");
			return null;
		}
		ComKpiInfo conkpi = new ComKpiInfo();
		conkpi = myBatisDao.get("ComKpiInfoMapper.ComKpiInfoQueryByCode",
				comKpiCode);
		return conkpi;
	}

	/**
	 * 获取此通用指标关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwpasCKpiInfo> getRelativeKPI(Serializable comKpiCode)
			throws Exception {
		List<DwpasCKpiInfo> kpiRelList = null;
		try {
			kpiRelList = myBatisDao.getList(
					"KpiComKpiInfoMapper.getRelativeKPI", comKpiCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ComKpiInfoServiceImpl.getRelativeKPI()访问数据库出错", e);
			return null;
		}
		return kpiRelList;

	}

	/**
	 * 获取此通用指标不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwpasCKpiInfo> getUnRelativeKPI(String linkedKpiCode,
			String kpiCode) throws Exception {
		String[] str = null;
		if (!StringUtils.isBlank(linkedKpiCode)) {
			str = linkedKpiCode.split(",");
		}

		List<String> kpiCodesList = new ArrayList<String>();
		if (str != null && str.length > 0) {
			for (int i = 1; i < str.length; i++) {
				kpiCodesList.add(str[i]);
			}
		}
		kpiCodesList.add("");
		List<DwpasCKpiInfo> kpiUnRelList = null;
		Map map = new HashMap<String, String>();
		map.put("kpiCodesList", kpiCodesList);
		map.put("kpiCode", kpiCode);
		try {
			kpiUnRelList = myBatisDao.getList(
					"KpiComKpiInfoMapper.getUnRelativeKPI", map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ComKpiInfoServiceImpl.getUnRelativeKPI()访问数据库出错", e);
			return null;
		}
		return kpiUnRelList;
	}

	@Override
	public void deleteRelativeKPI(Serializable comKpiCode) throws Exception {
		if (comKpiCode == null) {
			this.logger.warn("删除通用指标关联的指标失败:参数comKpiCode为空");
			throw new Exception("删除通用指标关联的指标失败:参数comKpiCode为空");
		}
		myBatisDao.delete("KpiComKpiInfoMapper.deleteRelativeKPI", comKpiCode);

	}

	@Override
	public void insertRelativeKPI(Map DwpasCKpiInfoList) throws Exception {
		if (DwpasCKpiInfoList == null || DwpasCKpiInfoList.isEmpty()) {
			this.logger.warn("插入关联指标失败:参数DwpasCKpiInfoList为空");
			throw new Exception("插入关联指标失败:参数DwpasCKpiInfoList为空");
		}
		try {
			myBatisDao.save("KpiComKpiInfoMapper.insertRelativeKPI",
					DwpasCKpiInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入关联指标信息出错", e);
			throw e;
		}

	}

	@Override
	public void updateRelateKpiInfo(String comKpiCode, String kpiCode)
			throws Exception {
		if (StringUtils.isBlank(comKpiCode) || StringUtils.isBlank(kpiCode)) {
			this.logger.warn("更新失败，参数为空！");
			throw new Exception("更新失败，参数为空");
		}
		String[] str = null;
		if (!StringUtils.isBlank(kpiCode)) {
			str = kpiCode.split(",");
		}
		List<KpiComKpiInfo> kpiInfoList = new ArrayList<KpiComKpiInfo>();
		if (str != null && str.length > 0) {
			for (int i = 1; i < str.length; i++) {
				KpiComKpiInfo kpiInfo = new KpiComKpiInfo();
				kpiInfo.setKpiCode(str[i]);
				kpiInfo.setComKpiCode(comKpiCode);
				kpiInfoList.add(kpiInfo);
			}
		}
		this.myBatisDao
				.save("KpiComKpiInfoMapper.saveRelativeKPI", kpiInfoList);
	}

	// 根据通用指标模块查询数据 zy
	@Override
	public List<ComKpiInfo> selKpiCodeByCode(ComKpiInfo kpiInfoDO) {
		if (kpiInfoDO == null) {
			this.logger.warn("根据通用指标查询数据失败，参数为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"KpiComKpiInfoMapper.selKpiInfoByComKpiCode", kpiInfoDO);
	}

	@Override
	public List<DwpasCKpiInfo> getNotRelateKpiBySetValue(String kpiCode) {
		if (StringUtils.isBlank(kpiCode)) {
			this.logger.warn("查询失败，参数为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"KpiComKpiInfoMapper.getNotRelateKpiBySetValue", kpiCode);
	}

	// 查询是否在龙榜榜中显示的通用指标zy
	@Override
	public List<ComKpiInfo> tigerIsShowComKpiInfo(String moduleId) {
		if (StringUtils.isBlank(moduleId) || StringUtils.isEmpty(moduleId)) {
			this.logger.warn("查询失败，参数为空！");
			return null;
		}
		// getDwpasRColumnComKpiByModuleId
		return this.myBatisDao.getList(
				"ComKpiInfoMapper.getDwpasRColumnComKpiByModuleId", moduleId);
	}

	// //搜索未在龙虎榜中显示的通用指标列表
	@Override
	public List<ComKpiInfo> searchTigerNotShow(String comKpiCode,
			String linkedKpiCode) {
		String str[] = null;
		if (!StringUtils.isBlank(linkedKpiCode)) {
			str = linkedKpiCode.split(",");
		}
		List<String> kpiCodeList = new ArrayList<String>();
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; i++) {
				kpiCodeList.add(str[i]);
			}
		}else{
			kpiCodeList.add("");
		}
		Map map = new HashMap();
		map.put("comKpiCode", comKpiCode);
		map.put("kpiCodeList", kpiCodeList);
		return this.myBatisDao.getList("ComKpiInfoMapper.searchTigerNotShow",
				map);
	}

	@Override
	public void updatePrdRanking(List<String> comKpiCodeList, String moduleId) {
		// TODO Auto-generated method stub
		// 删除产品排行关联的栏目
		this.myBatisDao.delete(
				"ComKpiInfoMapper.deleteComKpi_r_columnByModuleId", moduleId);
		// 删除栏目关联的指标
		this.myBatisDao.delete("ComKpiInfoMapper.deleteColumnInfoByModuleId",
				moduleId);
		// 新增产品排行关联的栏目
		DwpasCColumnInfo columnInfo = new DwpasCColumnInfo();
		columnInfo.setColumnId(UUID.randomUUID().toString());
		columnInfo.setModuleId(moduleId);
		columnInfo.setColumnCode("LHB_PRODUCT_RANKING");
		columnInfo.setColumnName("龙虎榜产品排行");
		this.myBatisDao
				.save("ComKpiInfoMapper.insertLhbColumnInfo", columnInfo);
		// 新增栏目关联的指标
		List<DwpasRColumnComKpi> columnRKpiList = new ArrayList<DwpasRColumnComKpi>();
		int seq = 0;
		for (String comKpiCode : comKpiCodeList) {
			if (StringUtils.isBlank(comKpiCode)
					|| StringUtils.isEmpty(comKpiCode))
				continue;
			columnRKpiList.add(new DwpasRColumnComKpi(columnInfo.getColumnId(),
					comKpiCode, seq));
			seq++;
		}
		this.myBatisDao.save("ComKpiInfoMapper.insertComKpi_r_column",
				columnRKpiList);
	}

	// 批量修改是否在龙虎榜中显示
	@Override
	public void updateShowTigerComKpiCodes(String showKpiCode,
			String notShowKpiCode) throws Exception {
		if (StringUtils.isBlank(notShowKpiCode)
				|| StringUtils.isBlank(showKpiCode)) {
			this.logger.warn("更新失败，传递参数为空！");
			throw new Exception("更新失败，传递参数为空！");
		}
		String showStr[] = null;
		String notShowStr[] = null;
		if (!StringUtils.isBlank(showKpiCode)) {
			showStr = showKpiCode.split(",");
		}
		if (!StringUtils.isBlank(notShowKpiCode)) {
			notShowStr = notShowKpiCode.split(",");
		}
		List<String> NotShowKpiCodeList = new ArrayList<String>();
		ComKpiInfo comKpiInfo = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (showStr != null && showStr.length > 0) {
			for (int i = 1; i < showStr.length; i++) {
				comKpiInfo = new ComKpiInfo();
				comKpiInfo.setComKpiCode(showStr[i]);
				comKpiInfo.setRankShowOrder(i);
				comKpiInfo.setGmtModified(new Date());
				this.logger.info("i=" + i);
				this.myBatisDao.update(
						"ComKpiInfoMapper.updateShowTigerComKpiCodes",
						comKpiInfo);
			}
		}
		if (notShowStr != null && notShowStr.length > 0) {
			for (int i = 0; i < notShowStr.length; i++) {
				NotShowKpiCodeList.add(notShowStr[i]);
			}
		}
		this.myBatisDao.update(
				"ComKpiInfoMapper.updateNotShowTigerComKpiCodes",
				NotShowKpiCodeList);
	}
}
