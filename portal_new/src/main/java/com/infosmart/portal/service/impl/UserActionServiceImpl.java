package com.infosmart.portal.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.CrossUserActionComparator;
import com.infosmart.portal.pojo.DwpasCUserActionInfo;
import com.infosmart.portal.pojo.DwpasStUserActionCrossAnalyseData;
import com.infosmart.portal.pojo.UserAction;
import com.infosmart.portal.service.UserActionService;
import com.infosmart.portal.util.StringUtils;

@Service
public class UserActionServiceImpl extends BaseServiceImpl implements UserActionService {
	@Override
	public List<UserAction> getUserActionCrossInfo( String actionCode , String queryDate){
		if (!StringUtils.notNullAndSpace(actionCode)) {
			this.logger.warn("查询交叉用户信息失败  产品Id为空");
			return null;
		}
		if (!StringUtils.notNullAndSpace(queryDate)) {
			this.logger.warn("查询交叉用户信息失败  查询日期为空");
			return null;
		}
		BigDecimal mul = new BigDecimal(100);
		List<UserAction> dtos = new ArrayList<UserAction>();
		DwpasCUserActionInfo dwpasCUserActionInfo = new DwpasCUserActionInfo();
		dwpasCUserActionInfo.setIsShow(1);
		dwpasCUserActionInfo.setIsDefault(1);
		// 查询所有已经标记的产品
		List<DwpasCUserActionInfo> userActionInfoList = this.myBatisDao
				.getList(
						"com.infosmart.mapper.DwpasCUserActionInfoMapper.queryDwpasUserActionList",
						dwpasCUserActionInfo);
		if (userActionInfoList == null || userActionInfoList.isEmpty()) {
			this.logger.warn("没有找到已标记的用户行为");
			return null;
		}
		for (int i = 0; i < userActionInfoList.size(); i++) {
			DwpasCUserActionInfo userActionInfo = userActionInfoList.get(i);
			if (actionCode.equals(userActionInfo.getActionCode())) {
				continue;
			}
			// 根据产品id和月份查询交叉用户信息
			Map param = new HashMap();
			param.put("actionCode", actionCode);
			param.put("reportDate", queryDate);
			param.put("relActionCode", userActionInfo.getActionCode());
			List<DwpasStUserActionCrossAnalyseData> userActionCrossDatas = this.myBatisDao
					.getList(
							"com.infosmart.mapper.DwpasStUserActionCrossAnalyseDataMapper.getDwpasStUserActionData",
							param);
			UserAction dto = new UserAction();
			dto.setRelActionCode(userActionInfo.getActionCode());
			dto.setRelActionName(userActionInfo.getActionName());
			if (userActionCrossDatas != null && userActionCrossDatas.size() <= 0) {
				dto.setCrossUserCnt(-1);
				dto.setCrossUserRate(null);
			} else {
				dto.setCrossUserCnt(userActionCrossDatas.get(0).getCrossUserCnt());
				BigDecimal tmp = userActionCrossDatas.get(0).getCrossUserRate()
						.multiply(mul);
				tmp = tmp.setScale(2, BigDecimal.ROUND_UP);
				dto.setCrossUserRate(tmp);
			}
			dtos.add(dto);
			CrossUserActionComparator cmp = new CrossUserActionComparator();
			Collections.sort(dtos, cmp);
		}
		return dtos;
	}
	
	
	/**
	 * 根据用户行为code查询用户行为信息
	 */
	public DwpasCUserActionInfo getUserActionInfoById(String actionCode) {
		this.logger.info("****根据用户行为Code查询用户行为信息****" + actionCode);
		DwpasCUserActionInfo userActionInfo = this.myBatisDao
				.get("com.infosmart.mapper.DwpasCUserActionInfoMapper.getUserActionInfoById",
						actionCode);
		return userActionInfo;
	}

}
