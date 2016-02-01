package com.infosmart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.engine.Engine;
import com.infosmart.po.DwpasStKpiData;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.KpiInfoService;

@Service
public class KpiInfoServiceImpl extends BaseServiceImpl implements
		KpiInfoService {

	@Override
	/**
	 * 获得指标信息列表
	 */
	public List<KpiInfo> queryKpiInfos(KpiInfo kpiinfo) {
		if (kpiinfo == null) {
			this.logger.warn("获取指标信息失败，参数为空！");
			return null;
		}
		List<KpiInfo> cKpiInfoDOList = new ArrayList<KpiInfo>();
		try {
			cKpiInfoDOList = myBatisDao.getList("KpiInfoMapper.QueryKpiinfo",
					kpiinfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询指标信息列表失败", e);
		}
		return cKpiInfoDOList;
	}

	/**
	 * 获得指标信息列表
	 */
	public List<KpiInfo> queryKpiInfos1() {
		List<KpiInfo> cKpiInfoDOList = new ArrayList<KpiInfo>();
		try {
			cKpiInfoDOList = myBatisDao
					.getList("KpiInfoMapper.KpiInfoQueryAll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询指标信息列表失败", e);
		}
		return cKpiInfoDOList;
	}

	/**
	 * 根据产品Id获得指标信息列表
	 * 
	 * @param productId
	 * @return
	 */
	public List<KpiInfo> queryKpiInfoByProductId(String productId) {
		if (StringUtils.isBlank(productId)) {
			this.logger.warn("获取指标信息失败，产品Id为空");
			return null;
		}
		List<KpiInfo> cKpiInfoDOList = new ArrayList<KpiInfo>();
		try {
			cKpiInfoDOList = myBatisDao.getList(
					"KpiInfoMapper.KpiInfoByProductId", productId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询指标信息列表失败", e);
		}
		return cKpiInfoDOList;
	}

	/**
	 * 获取基础指标列表
	 */
	public List<KpiInfo> queryBaseKpiInfos() {
		List<KpiInfo> cKpiInfoDOList = new ArrayList<KpiInfo>();
		try {
			cKpiInfoDOList = myBatisDao
					.getList("KpiInfoMapper.KpiInfoQueryBaseAll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询指标信息列表失败", e);
		}
		return cKpiInfoDOList;
	}

	@Override
	/**
	 * 指标增加
	 */
	public boolean insertKpiInfo(KpiInfo dwpasCKpiInfoDO) {
		boolean flag = false;
		if (dwpasCKpiInfoDO == null) {
			logger.warn("指标增加时参数dwpasCKpiInfoDO为null");
		} else {
			try {
				myBatisDao.save("KpiInfoMapper.KpiInfoInsert", dwpasCKpiInfoDO);
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
	 * 通过指标code查询指标信息
	 */
	public KpiInfo queryKpiInfoByCode(String kpiCode) {
		KpiInfo dwpasCKpiInfoDO = new KpiInfo();
		if (StringUtils.isBlank(kpiCode)) {
			logger.warn("通过指标code查询指标信息时参数指标code为null");
		} else {
			try {
				dwpasCKpiInfoDO = myBatisDao.get(
						"KpiInfoMapper.KpiInfoQueryByCode", kpiCode);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通过指标code查询指标信息失败", e);
			}
		}
		return dwpasCKpiInfoDO;
	}

	@Override
	/**
	 * 指标信息修改保存
	 */
	public boolean saveKpiInfo(KpiInfo dwpasCKpiInfoDO) throws Exception {
		boolean flag = false;
		if (dwpasCKpiInfoDO == null) {
			logger.warn("指标信息修改时参数dwpasCKpiInfoDO为null");
		} else {
			final KpiInfo finDwpasCKpiInfoDO = dwpasCKpiInfoDO;
			try {

				// 指标删除
				myBatisDao.delete("KpiInfoMapper.KpiInfoDelete",
						finDwpasCKpiInfoDO.getKpiCode());
				// 指标新增
				myBatisDao.save("KpiInfoMapper.KpiInfoInsert",
						finDwpasCKpiInfoDO);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("通用指标修改失败", e);
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 通过指标code删除指标信息
	 */
	public boolean deleteKpiInfoByCode(String kpiCode) {
		boolean flag = false;
		if (StringUtils.isBlank(kpiCode)) {
			logger.warn("通过指标code删除指标信息时参数指标code为null");
		} else {
			final String falKpiCode = kpiCode;
			try {

				// 通用指标关联指标删除

				// dwpasRKpiComkpiDAO.deleteByKpiCode(falKpiCode);
				// 指标删除
				myBatisDao.delete("KpiInfoMapper.KpiInfoDelete", falKpiCode);

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("指标删除失败", e);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	/**
	 * 校验表达式是否符合指标计算规则
	 */
	public String checkRule(String roleFormula) {
		String flag = "";// 指标计算规则校验结果返回标识
		if (StringUtils.isNotBlank(roleFormula)) {
			String formula = roleFormula;
			String code = "";
			List<String> list = new ArrayList<String>();// 表达式中涉及到得指标code集合
			List<String> kpiCodeList = new ArrayList<String>();// 所有指标code集合
			int num = 1;
			try {
				while (formula.indexOf("[") > -1 && formula.indexOf("]") > 0) {
					// 指标code
					code = formula.substring(formula.indexOf("[") + 1,
							formula.indexOf("]"));
					if (StringUtils.isBlank(code)) {
						return "blank";
					}
					// 替换表达式中的指标code为空
					formula = formula
							.replaceAll("\\[" + code + "\\]", num + "");
					if (!list.contains(code)) {
						list.add(code);
					}
					num++;
				}
				String result = Engine.playExpression(formula);
				if (StringUtils.equals(result, "NAN")) {
					flag = "operator";// 操作符不符合规则
				} else {
					if (list.size() > 0) {
						// 查询所有指标信息
						List<KpiInfo> kpiList = myBatisDao
								.getList("KpiInfoMapper.KpiInfoQueryAll");
						if (kpiList != null && kpiList.size() > 0) {
							for (KpiInfo dwpasCKpiInfoDO : kpiList) {
								kpiCodeList.add(dwpasCKpiInfoDO.getKpiCode());
							}
						}
						for (String kpiCode : list) {
							if (!kpiCodeList.contains(kpiCode)) {
								flag = kpiCode;// 若该指标没定义则返回该指标的指标code
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("校验表达式是否符合指标计算规则失败", e);
				flag = "operator";
			}
		}
		return flag;
	}

	@Override
	public boolean updateKpiInfo(KpiInfo dwpasCKpiInfoDO) {
		boolean flag = false;
		if (dwpasCKpiInfoDO == null) {
			this.logger.warn("指标信息更新失败：参数KpiInfo为空");
		} else {
			try {
				myBatisDao.update("KpiInfoMapper.updateKpiInfo",
						dwpasCKpiInfoDO);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("kpiCode=" + dwpasCKpiInfoDO.getKpiCode()
						+ "指标更新失败", e);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public void deleteKpiInfos(List<String> ids) throws Exception {
		if (ids == null || ids.isEmpty()) {
			this.logger.warn("指标信息批量删除失败：参数id列表为空");
			throw new Exception("指标信息批量删除失败：参数id列表为空");
		}
		try {
			myBatisDao.delete("KpiInfoMapper.deleteKpiInfos", ids);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("指标批量删除失败", e);
		}

	}

	@Override
	public List<MisTypeInfo> qryCSysType() {
		List<MisTypeInfo> cSysTypeDOList = null;
		MisTypeInfo dwpasCSysTypeDO = new MisTypeInfo();
		dwpasCSysTypeDO.setGroupId("1000");
		try {
			cSysTypeDOList = myBatisDao.getList(
					"MisTypeInfoMapper.MisTypeInfoQueryByDo", dwpasCSysTypeDO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获得指标大类列表失败", e);
		}
		return cSysTypeDOList;
	}

	@Override
	public List<KpiInfo> listAllOverallKpiInfo(int kpiType) {
		this.logger.info("列出所有的大盘指标");
		return this.myBatisDao.getList("KpiInfoMapper.listAllOverallKpiInfo",
				kpiType);
	}

	@Override
	public String selKpiInfoLikeRoleFormula(String kpiCode) {
		String result = "";
		List<KpiInfo> list = null;
		if (StringUtils.isNotBlank(kpiCode)) {
			list = this.myBatisDao.getList(
					"KpiInfoMapper.selKpiInfoLikeRoleFormula", kpiCode);
		}
		if (list != null && list.size() > 0) {
			result = "计算指标:";
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					result = result + list.get(i).getKpiCode() + ",";
				} else {
					result = result + list.get(i).getKpiCode();
				}
			}
			result = result + "中有该指标算法配置，请先修改这些计算指标的算法！";
		}
		return result;
	}

	@Override
	public void insertKpiData(DwpasStKpiData dwpasStKpiData) throws Exception {
		try {
			// TODO Auto-generated method stub
			this.myBatisDao.save("KpiInfoMapper.insertKpiData", dwpasStKpiData);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(e.getMessage());
			this.logger.error(e.getMessage(), e);
			throw e;
		}

	}
}
