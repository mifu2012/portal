package com.infosmart.service;

import java.sql.Connection;
import java.util.List;

import com.infosmart.po.OdsExtAreaSales;
import com.infosmart.po.OdsExtChannelInputs;
import com.infosmart.po.OdsExtCompetitorsSales;
import com.infosmart.po.OdsExtKpiInfo;
import com.infosmart.po.OdsExtLotSales;


public interface DwpasDataImportService {
	/**
	 * 全国销量数据导入
	 * @param lotSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importLotSalesData(List<OdsExtLotSales> lotSalesList,
			String isClearData) throws Exception;
	/**
	 * 地区销量数据导入
	 * @param areaSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importAreaSalesData(List<OdsExtAreaSales> areaSalesList,
			String isClearData) throws Exception;
	/**
	 * 渠道投入数据导入
	 * @param channelInputsList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importChannelInputsData(List<OdsExtChannelInputs> channelInputsList,
			String isClearData) throws Exception;
	/**
	 * 竞争对手销量数据导入
	 * @param competitorsSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importCompetitorsSalesData(List<OdsExtCompetitorsSales> competitorsSalesList,
			String isClearData) throws Exception;
	/**
	 * 指标信息数据导入
	 * @param kpiInfoList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importKpiInfoData(List<OdsExtKpiInfo> kpiInfoList,
			String isClearData) throws Exception;
	/**
	 * 获取连接connection
	 * @return
	 * @throws Exception
	 */
	public Connection connect()throws Exception;
	/**
	 * 根据表格数据类型 回滚操作
	 * @param type
	 * @throws Exception
	 */
	public void restoreImportData(String type) throws Exception;

}
