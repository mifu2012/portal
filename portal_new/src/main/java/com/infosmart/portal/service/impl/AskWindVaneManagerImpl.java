package com.infosmart.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DropDownList;
import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCKpiUserCnt;
import com.infosmart.portal.pojo.DwpasCSysParam;
import com.infosmart.portal.pojo.DwpasStDrogueData;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.TrendListDTO;
import com.infosmart.portal.pojo.UserCountStatistics;
import com.infosmart.portal.service.AskWindVaneManager;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.util.DateUtils;
import com.infosmart.portal.util.StringUtils;

@Service
public class AskWindVaneManagerImpl extends BaseServiceImpl implements
		AskWindVaneManager {
	// 屏蔽的关键字组ID
	private final static int NO_SHOW_HELP_KEY_WORD_GROUPID = 2000;
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;

	@Override
	public List<DwpasStDrogueData> getAskWindVaneData(String queryDate) {
			if(!StringUtils.notNullAndSpace(queryDate)){
				this.logger.warn("查询风向标信息失败：查询时间为空");
				return null;
			}
		// 截取前20条数据
		List<DwpasCSysParam> dwpasCSysParam = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCSysParam.queryCSysParamForIndex");
		int showKeyWordCount = 10;
		try {
			showKeyWordCount = Integer.parseInt(dwpasCSysParam.get(0)
					.getParamValue());
		} catch (Exception e) {
			showKeyWordCount = 10;
		}
		// 查询所有风向标数据 List1
		Map paramMap = new HashMap();
		paramMap.put("reportDate", queryDate.replace("-", ""));
		// 根据groupId=2000(常量)查询表dwpas_c_sys_type中数据 List2
		paramMap.put("groupId", String.valueOf(NO_SHOW_HELP_KEY_WORD_GROUPID));
		paramMap.put("num", showKeyWordCount <= 10 ? 10 : showKeyWordCount);
		List<DwpasStDrogueData> stDrogueDataList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasStDrogueData.listAllShowStDrogueDatas",
						paramMap);
		return stDrogueDataList;
	}

	@Override
	public List<UserCountStatistics> getUserCountStatistics(String kpiType,
			String queryDate) {
		// KPI类型
		if (!StringUtils.notNullAndSpace(kpiType)) {
			kpiType = String.valueOf(DwpasCKpiInfo.KPI_TYPE_OF_DAY);
		}
		// 数据类型
		String dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
		switch (Integer.parseInt(kpiType)) {
		case DwpasCKpiInfo.KPI_TYPE_OF_MONTH:
			dateType = DwpasStKpiData.DATE_TYPE_OF_MONTH;
			if (!StringUtils.notNullAndSpace(queryDate)) {
				queryDate = DateUtils.formatByFormatRule(
						DateUtils.getPreviousDate(new Date()), "yyyyMM");
			}
			break;
		case DwpasCKpiInfo.KPI_TYPE_OF_WEEK:
			dateType = DwpasStKpiData.DATE_TYPE_OF_WEEK;
			break;
		default:
			dateType = DwpasStKpiData.DATE_TYPE_OF_DAY;
			if (!StringUtils.notNullAndSpace(queryDate)) {
				queryDate = DateUtils.formatByFormatRule(
						DateUtils.getPreviousDate(new Date()), "yyyyMMdd");
			}
			break;
		}
		// 查询获得用户统计指标 显示类别
		List<DwpasCKpiUserCnt> dwpasCKpiUserCnt = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCKpiUserCntMapper.queryDwpasCKpiUserCnt");
		if (dwpasCKpiUserCnt == null)
			return null;
		List<UserCountStatistics> userCountStatisticsList = new ArrayList<UserCountStatistics>();
		// kpi_info
		DwpasCKpiInfo dwpasCKpiInfo = null;
		UserCountStatistics userCountStatistics = null;
		List<String> kpiCodeList = new ArrayList<String>();
		for (DwpasCKpiUserCnt us : dwpasCKpiUserCnt) {
			kpiCodeList.addAll(getKpiCodeList(us));
		}
		// 查询指标数据
		Map<String, DwpasStKpiData> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiCodeList, queryDate, dateType);
		if (kpiDataMap == null)
			kpiDataMap = new HashMap<String, DwpasStKpiData>();
		for (DwpasCKpiUserCnt us : dwpasCKpiUserCnt) {
			userCountStatistics = new UserCountStatistics();
			userCountStatistics.setKpiName(us.getKpiName());
			// 根据各类别的kpi_code获取ckpiInfo指标信息
			List<DwpasStKpiData> kpiDataList = new ArrayList<DwpasStKpiData>();
			for (String kpiCode : getKpiCodeList(us)) {
				kpiDataList.add(kpiDataMap.get(kpiCode));
			}
			if (kpiDataList != null && !kpiDataList.isEmpty()) {
				for (DwpasStKpiData dwpasStKpiData : kpiDataList) {
					if (dwpasStKpiData == null
							|| dwpasStKpiData.getDwpasCKpiInfo() == null) {
						continue;
					}
					// 指标信息
					dwpasCKpiInfo = dwpasStKpiData.getDwpasCKpiInfo();
					if (dwpasCKpiInfo.getKpiCode().equalsIgnoreCase(
							us.getTaobaoKpiCode())) {
						userCountStatistics.setFromTaobaoModule(dwpasCKpiInfo
								.getUnit());
						userCountStatistics.setFromTaobao(dwpasStKpiData
								.getShowValue());
					} else if (dwpasCKpiInfo.getKpiCode().equalsIgnoreCase(
							us.getAlipayKpiCode())) {
						userCountStatistics.setFromInnerModule(dwpasCKpiInfo
								.getUnit());
						userCountStatistics.setFromInner(dwpasStKpiData
								.getShowValue());
					} else if (dwpasCKpiInfo.getKpiCode().equalsIgnoreCase(
							us.getOutKpiCode())) {
						userCountStatistics.setFromOutterModule(dwpasCKpiInfo
								.getUnit());
						userCountStatistics.setFromOutter(dwpasStKpiData
								.getShowValue());
					} else if (dwpasCKpiInfo.getKpiCode().equalsIgnoreCase(
							us.getOtherKpiCode())) {
						userCountStatistics.setFromOutterModule(dwpasCKpiInfo
								.getUnit());
						userCountStatistics.setFromOther(dwpasStKpiData
								.getShowValue());
					}
				}
			}
			userCountStatisticsList.add(userCountStatistics);
		}
		return userCountStatisticsList;
	}

	private List<String> getKpiCodeList(DwpasCKpiUserCnt dwpasCKpiUserCntDO) {
		List<String> codeList = new ArrayList<String>();
		codeList.add(dwpasCKpiUserCntDO.getAlipayKpiCode());
		codeList.add(dwpasCKpiUserCntDO.getTaobaoKpiCode());
		codeList.add(dwpasCKpiUserCntDO.getOtherKpiCode());
		codeList.add(dwpasCKpiUserCntDO.getOutKpiCode());
		return codeList;
	}

	@Override
	public List<TrendListDTO> init() {
		// 查询dwpas_c_kpi_user_cnt表各类别及kpi_code
		List<DwpasCKpiUserCnt> DOs = this.myBatisDao
				.getList("com.infosmart.mapper.DwpasCKpiUserCntMapper.queryDwpasCKpiUserCnt");
		Map<String, String> map = new HashMap<String, String>();
		map.put("新增活跃会员数", "APY4010000701D");
		map.put("新增注册会员数", "ADD_APY1011000701D");
		map.put("登录会员数", "ADD_CUS5031000301D");
		map.put("登陆会员数", "ADD_CUS5031000301D");
		map.put("求助会员数", "ADD_APY4021002601D");
		List<TrendListDTO> dtos = new ArrayList<TrendListDTO>();
		for (DwpasCKpiUserCnt userCnt : DOs) {
			TrendListDTO dto = new TrendListDTO();
			String name = userCnt.getKpiName();
			dto.setName(name);
			List<DropDownList> drops = new ArrayList<DropDownList>();
			DropDownList taobao = new DropDownList();
			taobao.setName("WAP");
			taobao.setValue(userCnt.getTaobaoKpiCode());
			drops.add(taobao);
			DropDownList alipay = new DropDownList();
			alipay.setName("站内");
			alipay.setValue(userCnt.getAlipayKpiCode());
			drops.add(alipay);
			DropDownList out = new DropDownList();
			out.setName("外部");
			out.setValue(userCnt.getOutKpiCode());
			drops.add(out);
			DropDownList other = new DropDownList();
			other.setName("其它");
			other.setValue(userCnt.getOtherKpiCode());
			drops.add(other);
			DropDownList all = new DropDownList();
			all.setName("所有");
			all.setValue(map.get(name));
			drops.add(all);
			dto.setProperties(drops);
			dtos.add(dto);
		}
		return dtos;
	}
}
