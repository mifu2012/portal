package com.infosmart.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasCPrdInfo;
import com.infosmart.po.HelprateShow;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.PrdKpiInfo;
import com.infosmart.po.PrdMngInfo;
import com.infosmart.service.PrdMngInfoService;

@Service
public class PrdMngInfoServiceImpl extends BaseServiceImpl implements
		PrdMngInfoService {

	@Override
	/**
	 * 查询产品目录列表
	 */
	public List<PrdMngInfo> qryPrdFolder() {
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		PrdMngInfo prd = new PrdMngInfo();
		try {
			cPrdInfoDOList = myBatisDao.getList(
					"PrdMngInfoMapper.PrdMngInfoQueryAllPrdClasses", prd);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品目录列表失败", e);
		}
		return cPrdInfoDOList;
	}

	@Override
	/**
	 * 查询产品信息列表
	 */
	public List<PrdMngInfo> qryPrdInfo() {
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		try {
			cPrdInfoDOList = myBatisDao
					.getList("PrdMngInfoMapper.PrdMngInfoQueryAll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品信息列表失败", e);
		}
		return cPrdInfoDOList;
	}

	@Override
	/**
	 * 根据模板Id查询产品信息列表
	 */
	public List<PrdMngInfo> qryPrdInfoByTemplateId(String templateId) {
		if(StringUtils.isBlank(templateId)){
			this.logger.warn("查询产品信息失败，参数模板Id为空！");
			return null;
		}
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		try {
			cPrdInfoDOList = myBatisDao.getList(
					"PrdMngInfoMapper.queryPrdInfoBytemplateId", templateId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据模板ID查询产品信息列表失败", e);
		}
		return cPrdInfoDOList;
	}

	/**
	 * 根据产品Id编辑产品的关联
	 */
	public void updateProInfoByProductIdList(String markValue,
			List<String> productIds)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("markValue", markValue);
		map.put("productIds", productIds);
		if (productIds != null && productIds.size() > 0) {
			try {
				this.myBatisDao.save(
						"PrdMngInfoMapper.updateProInfoByProductIdList", map);
			} catch (Exception e) {

				this.logger.error("根据产品Id编辑产品的关联失败", e);
			}
		} else {
			this.logger.info("更新的产品列表不存在！");
			throw new Exception("更新的产品列表不存在！");
		}
	}

	@Override
	/**
	 * 查询产品健康度关系的所有产品信息
	 * @return
	 */
	public List<PrdMngInfo> qryHelthPrdInfo() {
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		try {
			cPrdInfoDOList = myBatisDao
					.getList("PrdMngInfoMapper.queryAllHelthPrdInfo");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品信息列表失败", e);
		}
		return cPrdInfoDOList;
	}

	@Override
	/**
	 * 根据模板Id查询产品健康度关系的所有产品信息
	 * @return
	 */
	public List<PrdMngInfo> qryHelthPrdInfoByTemplateId(String templateId) {
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		if(StringUtils.isBlank(templateId)){
			this.logger.warn("参数templateId为空！");
			return null;
		}
		try {
			cPrdInfoDOList = myBatisDao.getList(
					"PrdMngInfoMapper.qryHelthPrdInfoByTemplateId", templateId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品信息列表失败", e);
		}
		return cPrdInfoDOList;
	}

	@Override
	/**
	 * 新增产品信息
	 */
	public boolean insertPrdInfo(PrdMngInfo dwpasCPrdInfoDO) throws Exception {
		// 保存产品信息(产品信息表，产品求助率展示配置表)
		boolean flag = false;
		if (dwpasCPrdInfoDO == null) {
			logger.warn("新增产品信息时参数dwpasCPrdInfoDO为null");
		} else {
			final PrdMngInfo finDwpasCPrdInfoDO = dwpasCPrdInfoDO;
			try {

				// 增加产品信息
				myBatisDao.save("PrdMngInfoMapper.PrdMngInfoInsert",
						finDwpasCPrdInfoDO);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("产品信息新增失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 修改产品信息
	 */
	public boolean savePrdInfo(PrdMngInfo dwpasCPrdInfoDO) throws Exception {
		boolean flag = false;
		if (dwpasCPrdInfoDO == null) {
			logger.warn("修改产品信息时参数dwpasCPrdInfoDO为null");
		} else {
//			final PrdMngInfo finDwpasCPrdInfoDO = dwpasCPrdInfoDO;
			try {

//				// 产品删除
//				myBatisDao.delete("PrdMngInfoMapper.PrdMngInfoDelete",
//						finDwpasCPrdInfoDO.getProductId());
//				// 产品新增
//				myBatisDao.save("PrdMngInfoMapper.PrdMngInfoInsert",
//						finDwpasCPrdInfoDO);
				this.myBatisDao.update("PrdMngInfoMapper.updatePrdInfo", dwpasCPrdInfoDO);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("产品信息修改失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 新增产品关联指标
	 */
	public boolean insertPrdKPIRelation(String productId, String kpiCodes) throws Exception {
		boolean flag = false;
		if (StringUtils.isBlank(productId)) {
			logger.warn("新增产品关联指标时productId为null");
		} else {
			final String falKpiCodes = kpiCodes;
			final String falProductId = productId;
			try {

				// 删除产品相关联指标
				myBatisDao.delete("PrdKpiInfoMapper.PrdKpiInfoDeleteByPrdId",
						falProductId);
				if (!StringUtils.isEmpty(falKpiCodes)) {
					final String[] kpiCodes1 = falKpiCodes.split("!@!");
					for (int i = 0; i < kpiCodes1.length; i++) {
						PrdKpiInfo dwpasRPrdKpiDO = new PrdKpiInfo();
						dwpasRPrdKpiDO.setProductId(falProductId);
						dwpasRPrdKpiDO.setKpiCode(kpiCodes1[i]);
						// 新增产品相关联指标
						myBatisDao.save("PrdKpiInfoMapper.PrdKpiInfoInsert",
								dwpasRPrdKpiDO);
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
	 * 通过productId查询产品信息
	 */
	public PrdMngInfo getPrdInfo(String productId) {
		PrdMngInfo dwpasCPrdInfoDO = new PrdMngInfo();
		if (StringUtils.isBlank(productId)) {
			logger.warn("查询通用指标信息传递参数通用指标code为null");
		} else {
			try {
				dwpasCPrdInfoDO = myBatisDao.get(
						"PrdMngInfoMapper.PrdMngInfoQueryByCode", productId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获得通用指标信息失败", e);
			}
		}
		return dwpasCPrdInfoDO;
	}

	@Override
	/**
	 * 分别获得指定产品相关联指标列表及不关联指标列表
	 */
	public Map<String, List<KpiInfo>> getKPIRelationAndPrd(String productId)  throws Exception{
		Map<String, List<KpiInfo>> map = new HashMap<String, List<KpiInfo>>();
		List<KpiInfo> rKpiAndPrdList = new ArrayList<KpiInfo>();
		KpiInfo DwpasCKpiInfoDO = null;
		PrdKpiInfo dwpasRPrdKpiDO = null;
		List<KpiInfo> cKpiInfoList = null;
		try {
			cKpiInfoList = myBatisDao.getList("KpiInfoMapper.KpiInfoQueryAll");
			List<PrdKpiInfo> rPrdkpiList = myBatisDao.getList(
					"PrdKpiInfoMapper.PrdKpiInfoQueryAllByPrdId", productId);
			for (int i = 0; i < cKpiInfoList.size(); i++) {
				DwpasCKpiInfoDO = cKpiInfoList.get(i);
				for (int j = 0; j < rPrdkpiList.size(); j++) {
					dwpasRPrdKpiDO = rPrdkpiList.get(j);
					if (StringUtils.equals(DwpasCKpiInfoDO.getKpiCode(),
							dwpasRPrdKpiDO.getKpiCode())) {
						cKpiInfoList.remove(i);
						i--;
						rKpiAndPrdList.add(DwpasCKpiInfoDO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分别获得指定产品相关联指标列表及不关联指标列表时失败", e);
			throw e;
		}
		map.put("cKpiInfoList", cKpiInfoList);
		map.put("rKpiAndPrdList", rKpiAndPrdList);
		return map;
	}

	@Override
	/**
	 * 产品信息删除
	 */
	public boolean deletePrdInfo(String productId)throws Exception {
		boolean flag = false;
		if (StringUtils.isBlank(productId)) {
			logger.warn("产品信息删除时参数productId为null");
		} else {
			final String falProductId = productId;
			try {

				// 删除产品求助率
				myBatisDao.delete("HelprateShowMapper.HelprateShowDelete",
						falProductId);
				// 产品关联指标删除
				myBatisDao.delete("PrdKpiInfoMapper.PrdKpiInfoDeleteByPrdId",
						falProductId);
				// 产品健康度删除
				myBatisDao.delete("PrdDimInfoMapper.PrdDimInfoDelete",
						falProductId);
				// 产品信息删除
				myBatisDao.delete("PrdMngInfoMapper.PrdMngInfoDelete",
						falProductId);

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("产品信息删除失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 通过产品ID获得产品求助率
	 */
	public HelprateShow getPrdHelpRateById(String productId) {
		HelprateShow dwpasCHelprateShowDO = null;
		if (StringUtils.isBlank(productId)) {
			logger.warn("通过产品ID获得产品求助率时参数productId为null");
		} else {
			try {
				dwpasCHelprateShowDO = myBatisDao.get(
						"HelprateShowMapper.HelprateShowQueryByProductId",
						productId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通过产品ID获得产品求助率失败", e);
			}
		}
		return dwpasCHelprateShowDO;
	}

	@Override
	public List<PrdMngInfo> ListPagePrdInfo(PrdMngInfo dwpasCPrdInfoDO) {
		if(dwpasCPrdInfoDO == null){
			this.logger.warn("查询产品信息参数dwpasCPrdInfoDO为空！");
			return null;
		}
		List<PrdMngInfo> cPrdInfoDOList = new ArrayList<PrdMngInfo>();
		try {
			cPrdInfoDOList = myBatisDao.getList(
					"PrdMngInfoMapper.listPagePrdinfo", dwpasCPrdInfoDO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询产品信息列表失败", e);
		}
		return cPrdInfoDOList;
	}

	/**
	 * 获取此产品关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwpasCKpiInfo> getRelativeKPI(Serializable productId)
			throws Exception {
		List<DwpasCKpiInfo> kpiRelList = null;
		try {
			kpiRelList = myBatisDao.getList("PrdKpiInfoMapper.getRelativeKPI",
					productId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PrdMngInfoServiceImpl.getRelativeKPI()访问数据库出错", e);
			return null;
		}
		return kpiRelList;

	}

	/**
	 * 获取此产品不关联的指标
	 * 
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<DwpasCKpiInfo> getUnRelativeKPI(String productId, String kpiCode)
			throws Exception {
		List<DwpasCKpiInfo> kpiUnRelList = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("productId", productId);
		map.put("kpiCode", kpiCode);
		try {
			kpiUnRelList = myBatisDao.getList(
					"PrdKpiInfoMapper.getUnRelativeKPI", map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PrdMngInfoServiceImpl.getUnRelativeKPI()访问数据库出错", e);
			return null;
		}
		return kpiUnRelList;
	}

	@Override
	public void deleteRelativeKPI(Serializable comKpiCode) throws Exception {
		if (comKpiCode == null) {
			this.logger.warn("删除产品关联的指标失败：参数comKpiCode为空");
			throw new Exception("删除产品关联的指标失败：参数comKpiCode为空");
		}
		myBatisDao.delete("PrdKpiInfoMapper.deleteRelativeKPI", comKpiCode);

	}

	@Override
	public void insertRelativeKPI(Map DwpasCKpiInfoList) throws Exception {
		if (DwpasCKpiInfoList == null || DwpasCKpiInfoList.isEmpty()) {
			this.logger.warn("插入此产品关联的指标失败：参数DwpasCKpiInfoList为空");
			throw new Exception("插入此产品关联的指标失败：参数DwpasCKpiInfoList为空");
		}
		try {
			myBatisDao.save("PrdKpiInfoMapper.insertRelativeKPI",
					DwpasCKpiInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入关联指标信息出错", e);
			throw e;
		}

	}

	@Override
	public void updateRelateKpiInfo(String productId, String kpiCode) throws Exception {
		if(StringUtils.isBlank(kpiCode)){
			this.logger.warn("更新失败，参数kipCode为null！");
			throw new Exception("更新失败，参数kipCode为null！");
		}
		String[] str = kpiCode.split(",");
		List<PrdKpiInfo> kpiInfoList = new ArrayList<PrdKpiInfo>();
		for (int i = 1; i < str.length; i++) {
			PrdKpiInfo kpiInfo = new PrdKpiInfo();
			kpiInfo.setKpiCode(str[i]);
			kpiInfo.setProductId(productId);
			kpiInfoList.add(kpiInfo);
		}
		if(kpiInfoList==null || kpiInfoList.size()<1){
			this.logger.warn("更新失败，kpiInfoList为null！");
			throw new Exception("更新失败，kpiInfoList为null！");
		}
		this.myBatisDao.save("PrdKpiInfoMapper.saveRelativeKPI", kpiInfoList);
	}

	@Override
	public List<DwpasCKpiInfo> searchNotRelateKpiCodes(String kpiCode,
			String linkedKpiCode) {
		String[] str = null;
		if(StringUtils.isNotBlank(linkedKpiCode)){
			str = linkedKpiCode.split(",");
		}
		List<String> kpiCodesList = new ArrayList<String>();
		if (str!=null && str.length > 0) {
			for (int i = 1; i < str.length; i++) {
				kpiCodesList.add(str[i]);
			}
		}
		kpiCodesList.add("");
		Map map = new HashMap();
		map.put("kpiCode", kpiCode);
		map.put("kpiCodesList", kpiCodesList);
		return this.myBatisDao.getList(
				"PrdKpiInfoMapper.searchNotRelateKpiCodes", map);
	}

	@Override
	public List<PrdMngInfo> getMarkedPro(String typeId) {
		if (StringUtils.isBlank(typeId)) {
			this.logger.warn("查询已关联产品失败：typeId为空！");
			return null;
		}
		return this.myBatisDao.getList("PrdMngInfoMapper.getMarkedPro", typeId);
	}

	@Override
	public List<PrdMngInfo> getUnMarkedPro(String typeId, String keyCode) {
		if (StringUtils.isBlank(typeId)) {
			this.logger.warn("查询未关联产品失败：typeId为空！");
			return null;
		}
		Map map = new HashMap();
		map.put("typeId", typeId);
		map.put("keyCode", keyCode);
		return this.myBatisDao.getList("PrdMngInfoMapper.getUnMarkedPro", map);
	}

	@Override
	public void updateMarkedPro(String typeId, List<String> productIds) throws Exception {
		if (StringUtils.isBlank(typeId) || productIds.isEmpty()) {
			this.logger.warn("修改与此产品类型已关联的产品:typeId为空或产品ids为空！");
			throw new Exception("修改与此产品类型已关联的产品:typeId为空或产品ids为空！");
		}
		Map map = new HashMap();
		map.put("typeId", typeId);
		map.put("productIds", productIds);
		this.myBatisDao.update("PrdMngInfoMapper.updateMarkedPro", map);
	}

	@Override
	public void updateUnMarkedPro(String typeId, List<String> productIds)throws Exception {
		if (StringUtils.isBlank(typeId) || productIds.isEmpty()) {
			this.logger.warn("修改与此产品类型未关联的产品:typeId为空或产品Ids为空！");
			throw new Exception("修改与此产品类型未关联的产品:typeId为空或产品Ids为空！");
		}
		Map map = new HashMap();
		map.put("typeId", typeId);
		map.put("productIds", productIds);
		this.myBatisDao.update("PrdMngInfoMapper.updateUnMarkedPro", map);
	}

	@Override
	public List<PrdMngInfo> getPrdMngInfoByProids(List<String> productIds) {
		if (productIds.isEmpty()) {
			this.logger.warn("根据产品Ids查询产品列表失败：产品ids为空！");
			return null;
		}
		return this.myBatisDao.getList(
				"PrdMngInfoMapper.getPrdMngInfoByProids", productIds);
	}

	@Override
	public void deleteTepRPrd(String productId) throws Exception {
		if(StringUtils.isBlank(productId)){
			this.logger.warn("删除失败，产品Id为null！");
			throw new Exception("删除失败，产品Id为null！！");
		}
		this.myBatisDao.delete("PrdMngInfoMapper.deleteTepRPrdByProdId", productId);
	}
	
	@Override
	public List<DwpasCPrdInfo> getAllProInfosByTemplateId(String templateId){
		return this.myBatisDao.getList("PrdMngInfoMapper.getAllProInfosByTemplateId",templateId);
	}
}
