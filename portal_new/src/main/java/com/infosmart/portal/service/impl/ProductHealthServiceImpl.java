package com.infosmart.portal.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCPrdDim;
import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProductDimHealth;
import com.infosmart.portal.service.DwpasStKpiDataService;
import com.infosmart.portal.service.ProductHealthService;
import com.infosmart.portal.util.StringUtils;

@Service
public class ProductHealthServiceImpl extends BaseServiceImpl implements
		ProductHealthService {
	@Autowired
	private DwpasStKpiDataService dwpasStKpiDataService;

	public ProductDimHealth getDwpasCPrdDim(DwpasCPrdDim dwpasCPrdDim,
			String reportDate, String dateType) {
		this.logger.info("查询某产品某天/月的六维度数据:" + reportDate);
		if (dwpasCPrdDim == null) {
			this.logger.error("六维度指标参数为空对象");
			return null;
		}
		// 对日期格式进行转换
		if (reportDate != null && reportDate.indexOf("-") != -1) {
			reportDate = StringUtils.replace(reportDate, "-", "");
		}
		// 六维度所有指标
		List<String> kpiCodeList = new ArrayList<String>();
		kpiCodeList.add(dwpasCPrdDim.getDim1Code());
		kpiCodeList.add(dwpasCPrdDim.getDim2Code());
		kpiCodeList.add(dwpasCPrdDim.getDim3Code());
		kpiCodeList.add(dwpasCPrdDim.getDim4Code());
		kpiCodeList.add(dwpasCPrdDim.getDim5Code());
		kpiCodeList.add(dwpasCPrdDim.getDim6Code());
		// 查询KPI数据
		Map<String, DwpasStKpiData> kpiDataMap = this.dwpasStKpiDataService
				.listDwpasStKpiDataByKpiCode(kpiCodeList, reportDate,
						DwpasStKpiData.DATE_TYPE_OF_MONTH);
		if (kpiDataMap == null || kpiDataMap.isEmpty()) {
			return null;
		}
		ProductDimHealth productDimHealth = new ProductDimHealth();
		for (int i = 1; i <= kpiCodeList.size(); i++) {
			String kpiCode = kpiCodeList.get(i - 1);
			DwpasStKpiData kpiData = kpiDataMap.get(kpiCode);
			if (kpiData == null || kpiData.getDwpasCKpiInfo() == null) {
				this.logger.error("没有取到指标信息或指标数据");
				continue;
			}
			this.logger.debug("设置指标[" + i + "]维度[指标编码为:" + kpiCode + "]信息和值");
			try {
				// 六维度指标名称
				Object dimName = PropertyUtils.getProperty(dwpasCPrdDim, "dim"
						+ i + "Name");
				if (dimName == null || dimName.toString().length() == 0) {
					// 设置六维度名称
					PropertyUtils.setProperty(dwpasCPrdDim, "dim" + i + "Name",
							kpiData.getDwpasCKpiInfo().getDispName());
				}
				// 六维度指标值的单位
				Object dimValueUnit = PropertyUtils.getProperty(dwpasCPrdDim,
						"dim" + i + "ValueUnit");
				if (dimValueUnit == null
						|| dimValueUnit.toString().length() == 0) {
					// 设置六维度值的单位
					PropertyUtils
							.setProperty(dwpasCPrdDim, "dim" + i + "ValueUnit",
									kpiData.getDwpasCKpiInfo().getUnit());
				}
				// 设置名称
				PropertyUtils.setProperty(productDimHealth, "dimName" + i,
						kpiData.getDwpasCKpiInfo().getDispName());
				// 单位
				PropertyUtils.setProperty(productDimHealth, "dimUnit" + i,
						kpiData.getDwpasCKpiInfo().getUnit());
				// 实际值
				PropertyUtils.setProperty(productDimHealth, "dimValue" + i,
						kpiData.getShowValue());
				// 维度指标最小值
				Object inValue = PropertyUtils.getProperty(dwpasCPrdDim, "dim"
						+ i + "InValue");
				// 维度指标最大值
				Object outValue = PropertyUtils.getProperty(dwpasCPrdDim, "dim"
						+ i + "OutValue");
				// 归化值
				PropertyUtils.setProperty(productDimHealth, "drowValue" + i,
						this.getDrowValue(inValue == null ? new BigDecimal(0)
								: new BigDecimal(inValue.toString()),
								new BigDecimal(outValue.toString()), kpiData
										.getShowValue()));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				this.logger.error(e.getMessage(), e);
			}
		}
		return productDimHealth;
	}

	/**
	 * 计算实际值归一化处理后值(六维度用)
	 * 
	 * @param inValue
	 * @param outValue
	 * @param dimValue
	 * @return
	 */
	private static BigDecimal getDrowValue(BigDecimal inValue,
			BigDecimal outValue, BigDecimal dimValue) {
		BigDecimal han = new BigDecimal(100);
		BigDecimal minusHan = new BigDecimal(0);
		BigDecimal drowValue = null;
		if (inValue != null && outValue != null && dimValue != null) {
			drowValue = ((dimValue.subtract(inValue)).divide(
					outValue.subtract(inValue), 4, BigDecimal.ROUND_HALF_UP))
					.multiply(han).setScale(2, BigDecimal.ROUND_HALF_UP);

		/*	if (drowValue.compareTo(han) >= 0) {
				return han;
			}
			if (drowValue.compareTo(minusHan) <= 0) {
				return minusHan;
			}*/
		}
		return drowValue;
	}
}
