package com.infosmart.portal.util.dwmis;

import java.math.BigDecimal;

import com.infosmart.portal.pojo.dwmis.DwmisKpiInfo;

/**
 * 用于对原始数据根据定义的单位进行换算
 * 
 * 如KPI_Data表中的数据为 1500，要转换的单位为“笔数”、“千笔”，则返回 1.5 数值
 * 
 * 默认KPI_DATA表中的数据单位为： 金额为：分 人数：个 笔数：个
 * 
 * @author jin.zheng
 * @date 2011-3-29
 */
public class UnitTransformer {

	/**
	 * 根据传入的 数值、单位类别、数量级 进行转换
	 * 
	 * 默认KPI_DATA表中的数据单位为： 金额为：元 人数：个 笔数：个
	 * 
	 * 
	 * 代码说明：
	 * 
	 * 
	 * 指标数量级 6001 个 6002 十 6003 百 6004 千 6005 万 6006 十万 6007 百万 6008 千万 6009 亿
	 * 6010 十亿 6011 百亿
	 * 
	 * @param baseNum
	 * @param unitID
	 * @param sizeID
	 * @return
	 */
	public static double transform(double baseNum, Integer sizeID,
			DwmisKpiInfo kpiInfo) {

		return transform(baseNum, sizeID, kpiInfo, CoreConstant.DEFAULT_DIGIT);

	}

	/**
	 * 根据传入的 数值、单位类别、数量级 进行转换
	 * 
	 * 默认KPI_DATA表中的数据单位为： 金额为：元 人数：个 笔数：个
	 * 
	 * 可以明确提供需要保留的小数点位数 代码说明：
	 * 
	 * 
	 * 指标数量级 6001 个 6002 十 6003 百 6004 千 6005 万 6006 十万 6007 百万 6008 千万 6009 亿
	 * 6010 十亿 6011 百亿
	 * 
	 * @param baseNum
	 * @param unitID
	 * @param sizeID
	 * @return
	 */
	public static double transform(double baseNum, Integer sizeID,
			DwmisKpiInfo kpiInfo, int digit) {

		double result = transformFullValue(baseNum, sizeID, kpiInfo);

		return keepDecimal(result, digit, kpiInfo);
	}

	/**
	 * 以上单位换算方法的反转，即 原数 254.555555，转换数量级为 千 6004 transform() 方法 返回 0.3
	 * transformReverse() 方法 返回 254555.6
	 * 
	 * @param baseNum
	 * @param sizeID
	 * @return
	 */
	public static double transformReverse(double baseNum, Integer sizeID,
			DwmisKpiInfo kpiInfo) {
		Integer tempSizeID = sizeID;
		double num = baseNum
				/ UnitTransformer.transformFullValue(1, tempSizeID, kpiInfo);
		return UnitTransformer.keepDecimal(num, CoreConstant.DEFAULT_DIGIT,
				kpiInfo);

	}

	/**
	 * 穆春于2011/6/11 11:44 增加此方法，同上方法，但增加小数点参数，方便对外定制小数点位数，调用 以上单位换算方法的反转，即 原数
	 * 254.555555，转换数量级为 千 6004 transform() 方法 返回 0.3 transformReverse() 方法 返回
	 * 254555.6
	 * 
	 * @param baseNum
	 * @param sizeID
	 * @param digit
	 *            小数点位数
	 * @return
	 */
	public static double transformReverse(double baseNum, Integer sizeID,
			DwmisKpiInfo kpiInfo, int digit) {
		Integer tempSizeID = sizeID;
		double num = baseNum
				/ UnitTransformer.transformFullValue(1, tempSizeID, kpiInfo);
		return UnitTransformer.keepDecimal(num, digit, kpiInfo);

	}

	/**
	 * 与上面的单位换算方法不同，这个方法只换算，不保留小数点后n位
	 * 
	 * @param baseNum
	 * @param sizeID
	 * @return
	 */
	public static double transformFullValue(double baseNum, Integer sizeID,
			DwmisKpiInfo kpiInfo) {
		if (baseNum == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
			return baseNum;
		}

		if (sizeID == null) {
			return keepDecimal(baseNum, CoreConstant.DEFAULT_DIGIT, kpiInfo);
		}

		double result = baseNum;

		switch (sizeID) {

		case 6001: // 个
			result = baseNum;
			break;

		case 6002: // 十
			result = baseNum / 10;
			break;

		case 6003: // 百
			result = baseNum / 100;
			break;

		case 6004: // 千
			result = baseNum / 1000;
			break;

		case 6005: // 万
			result = baseNum / 10000;
			break;

		case 6006: // 十万
			result = baseNum / 100000;
			break;

		case 6007: // 百万
			result = baseNum / 1000000;
			break;

		case 6008: // 千百
			result = baseNum / 10000000;
			break;

		case 6009: // 亿
			result = baseNum / 100000000;
			break;

		case 6010: // 十亿
			result = baseNum / 1000000000;
			break;

		case 6011: // 百亿
			result = baseNum / 10000000000.0;
			break;

		case 6012: // 千亿
			result = baseNum / 100000000000.0;
			break;

		case 6013: // 万亿
			result = baseNum / 1000000000000.0;
			break;

		case 6014: // 十万亿
			result = baseNum / 10000000000000.0;
			break;

		case 6015: // 百万亿
			result = baseNum / 100000000000000.0;
			break;

		case 6016: // 千万亿
			result = baseNum / 1000000000000000.0;
			break;

		case 6017: // 万万亿
			result = baseNum / 10000000000000000.0;
			break;

		default:
			break;
		}
		return result;
	}

	/**
	 * 四舍五入，把数值转换成保留n位小数的数值
	 * 
	 * 如 keepDecimal(12.5456786, 2) = 12.54
	 * 
	 * @param num
	 * @param digit
	 * @return
	 */
	public static double keepDecimal(double num, int digit, DwmisKpiInfo kpiInfo) {
		int flag = digit;
		// 对技术部进行特殊处理，都是保留两位小数
		if (kpiInfo != null && kpiInfo.getKpiCode() != null) {

			if (kpiInfo.getKpiCode().startsWith("TEC")
					|| kpiInfo.getKpiCode().startsWith("tec")) {

				flag = 2;
			}
		}

		if (num == CoreConstant.DEFAULT_DATA_NOT_FOUND) {
			return num;
		}

		BigDecimal big = new BigDecimal(String.valueOf(num));
		double result = big.setScale(flag, BigDecimal.ROUND_HALF_UP)
				.doubleValue();

		return result;
	}
}