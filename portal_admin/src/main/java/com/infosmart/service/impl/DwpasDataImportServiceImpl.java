package com.infosmart.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.infosmart.po.OdsExtAreaSales;
import com.infosmart.po.OdsExtChannelInputs;
import com.infosmart.po.OdsExtCompetitorsSales;
import com.infosmart.po.OdsExtKpiInfo;
import com.infosmart.po.OdsExtLotSales;
import com.infosmart.service.DwpasDataImportService;
import com.infosmart.util.StringUtils;

@Service
public class DwpasDataImportServiceImpl extends BaseServiceImpl implements
		DwpasDataImportService {

	@Override
	public Connection connect() throws Exception {
		Properties props = new Properties();
		Connection con = null;
		InputStream in = this.getClass().getResourceAsStream(
				"/import-jdbc.properties");
		try {
			props.load(in);
			if (in != null) {
				in.close();
			}
			String url = props.getProperty("jdbc.url");
			String userName = props.getProperty("jdbc.username");
			String passWord = props.getProperty("jdbc.password");
			String driver = props.getProperty("jdbc.driver");
			this.getClass();
			Class.forName(driver);
			con = DriverManager.getConnection(url, userName, passWord);
		} catch (IOException ex1) {

			ex1.printStackTrace();

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();

		} catch (SQLException ex) {
			/** @todo Handle this exception */
			ex.printStackTrace();

		}
		return con;
	}

	/**
	 * 全国销量数据导入
	 * 
	 * @param lotSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	@Override
	public void importLotSalesData(List<OdsExtLotSales> lotSalesList,
			String isClearData) throws Exception {
		if (lotSalesList == null || lotSalesList.size() == 0) {
			this.logger.error("导入全国销量数据失败：lotSalesList为空");
			throw (new Exception("表格数据为空!"));
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			con.setAutoCommit(false);
			//删除备份表数据
			stmt.executeUpdate("DELETE FROM ods_ext_lot_sales_bak");
			//将原表数据 备份到 备份表
			stmt.executeUpdate("INSERT INTO ods_ext_lot_sales_bak (SELECT * FROM ods_ext_lot_sales)");
			//清除原有数据导入
			if("1".equals(isClearData)){
				stmt.executeUpdate("DELETE FROM ods_ext_lot_sales");
			}
			
			for (int i = 0; i < lotSalesList.size(); i++) {
				String sqlContent = "INSERT INTO ods_ext_lot_sales " +
						"(LOT_NAME,KPI_NUM,KPI_DEP,KPI_YM,KPI_CODE,LOTID,CLASS_1,CLASS_2,CLASS_3,SALES,UNIT)" +
						"VALUES("
						+ "'"+ lotSalesList.get(i).getLotName() + "',"
						+ "'"+ lotSalesList.get(i).getKpiNum() + "',"
						+ "'"+ lotSalesList.get(i).getKpiDep() + "',"
						+ "'"+ lotSalesList.get(i).getKpiYm() + "',"
						+ "'"+ lotSalesList.get(i).getKpiCode() +"',"
						+ "'"+ lotSalesList.get(i).getLotid() + "',"
						+ "'"+ lotSalesList.get(i).getClass1() + "',"
						+ "'"+ lotSalesList.get(i).getClass2() + "',"
						+ "'"+ lotSalesList.get(i).getClass3() + "',"
						+ "'"+ lotSalesList.get(i).getSales() + "',"
						+ "'"+ lotSalesList.get(i).getUnit() + "'"
						+")";
							
				stmt.executeUpdate(sqlContent);
			}
			con.commit();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}

		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}

	}

	/**
	 * 地区销量数据导入
	 * 
	 * @param areaSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	@Override
	public void importAreaSalesData(List<OdsExtAreaSales> areaSalesList,
			String isClearData) throws Exception {
		if (areaSalesList == null || areaSalesList.size() == 0) {
			this.logger.error("导入地区销量数据失败：areaSalesList为空");
			throw (new Exception("表格数据为空!"));
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			con.setAutoCommit(false);
			
			//删除备份表数据
			stmt.executeUpdate("DELETE FROM ods_ext_area_sales_bak");
			//将原表数据备份到 备份表
			stmt.executeUpdate("INSERT INTO ods_ext_area_sales_bak (SELECT * FROM ods_ext_area_sales)");
			//清除原有数据导入
			if("1".equals(isClearData)){
				stmt.executeUpdate("DELETE FROM ods_ext_area_sales");
			}
			for (int i = 0; i < areaSalesList.size(); i++) {
				String sqlContent ="INSERT INTO ods_ext_area_sales " +
			            "(LOTID,KPI_NUM,KPI_DEP,KPI_YM,KPI_CODE,CLASS_1,MONTH_SALES,YEAR_SALES_ACCU,UNIT)" +
						"VALUES("
						+ "'"+ areaSalesList.get(i).getLotid() + "',"
						+ "'"+ areaSalesList.get(i).getKpiNum() + "',"
						+ "'"+ areaSalesList.get(i).getKpiDep() + "',"
						+ "'"+ areaSalesList.get(i).getKpiYm() + "',"
						+ "'"+ areaSalesList.get(i).getKpiCode() +"',"
						+ "'"+ areaSalesList.get(i).getClass1() + "',"
						+ "'"+ areaSalesList.get(i).getMonthSales() + "',"
						+ "'"+ areaSalesList.get(i).getYearSalesAccu() + "',"
						+ "'"+ areaSalesList.get(i).getUnit() + "'"
						+")";
				
				//备份表  逐个插入数据
				stmt.executeUpdate(sqlContent);
			}
            con.commit();
            con.setAutoCommit(true);
            if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}

	}

	/**
	 * 渠道投入数据导入
	 * 
	 * @param channelInputsList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importChannelInputsData(
			List<OdsExtChannelInputs> channelInputsList, String isClearData)
			throws Exception {
		if (channelInputsList == null || channelInputsList.size() == 0) {
			this.logger.error("导入渠道引入数据失败：channelInputsList为空");
			throw (new Exception("表格数据为空!"));
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			
			con.setAutoCommit(false);
			
			
			//删除备份表数据
			stmt.executeUpdate("DELETE FROM ods_ext_channel_inputs_bak");
			//将原表数据备份到 备份表
			stmt.executeUpdate("INSERT INTO ods_ext_channel_inputs_bak (SELECT * FROM ods_ext_channel_inputs)");
			//清除原有数据导入
			if("1".equals(isClearData)){
				stmt.executeUpdate("DELETE FROM ods_ext_channel_inputs");
			}
			
			for (int i = 0; i < channelInputsList.size(); i++) {
				String sqlContent = "INSERT INTO ods_ext_channel_inputs " +
						"(CHANNEL,KPI_NUM,KPI_DEP,THROWIN_START,THROWIN_END,THROWIN_RANGE,KPI_CODE,CURR_COM_VALUE,UNIT)" +
						"VALUES("
						+ "'"+ channelInputsList.get(i).getChannel() + "',"
						+ "'"+ channelInputsList.get(i).getKpiNum() + "',"
						+ "'"+ channelInputsList.get(i).getKpiDep() + "',"
						+ "'"+ channelInputsList.get(i).getThrowinStart() + "',"
						+ "'"+ channelInputsList.get(i).getThrowinEnd() +"',"
						+ "'"+ channelInputsList.get(i).getThrowinRange() + "',"
						+ "'"+ channelInputsList.get(i).getKpiCode() + "',"
						+ "'"+ channelInputsList.get(i).getCurrComValue() + "',"
						+ "'"+ channelInputsList.get(i).getUnit() + "'"
						+")";
				stmt.executeUpdate(sqlContent);
			}
			con.commit();
            con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}

		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}

	}

	/**
	 * 竞争对手销量数据导入
	 * 
	 * @param competitorsSalesList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importCompetitorsSalesData(
			List<OdsExtCompetitorsSales> competitorsSalesList,
			String isClearData) throws Exception {
		if (competitorsSalesList == null || competitorsSalesList.size() == 0) {
			this.logger.error("导入竞争对手销量数据失败：competitorsSalesList为空");
			throw (new Exception("表格数据位空!"));
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			con.setAutoCommit(false);
			
			//删除备份表数据
			stmt.executeUpdate("DELETE FROM ods_ext_competitors_sales_bak");
			//将原表数据备份到 备份表
			stmt.executeUpdate("INSERT INTO ods_ext_competitors_sales_bak (SELECT * FROM ods_ext_competitors_sales)");
			//清除原有数据导入
			if("1".equals(isClearData)){
				stmt.executeUpdate("DELETE FROM ods_ext_competitors_sales");
			}
			
			for (int i = 0; i < competitorsSalesList.size(); i++) {
				String sqlContent = "INSERT INTO ods_ext_competitors_sales " +
						"(COMPETITORS,KPI_NUM,KPI_DEP,KPI_YM,KPI_CODE,CURR_COM_VALUE,UNIT)" +
						"VALUES("
						+ "'"+ competitorsSalesList.get(i).getCompetitors() + "',"
						+ "'"+ competitorsSalesList.get(i).getKpiNum() + "',"
						+ "'"+ competitorsSalesList.get(i).getKpiDep() + "',"
						+ "'"+ competitorsSalesList.get(i).getKpiYm() + "',"
						+ "'"+ competitorsSalesList.get(i).getKpiCode() +"',"
						+ "'"+ competitorsSalesList.get(i).getCurrComValue() + "',"
						+ "'"+ competitorsSalesList.get(i).getUnit() + "'"
						+")";
				stmt.executeUpdate(sqlContent);
			}
			con.commit();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}

		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}

	}

	/**
	 * 指标信息数据导入
	 * 
	 * @param kpiInfoList
	 * @param isClearData
	 * @throws Exception
	 */
	public void importKpiInfoData(List<OdsExtKpiInfo> kpiInfoList,
			String isClearData) throws Exception {
		if (kpiInfoList == null || kpiInfoList.size() == 0) {
			this.logger.error("导入指标信息数据失败：kpiInfoList为空");
			throw (new Exception("表格数据为空!"));
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			con.setAutoCommit(false);
			
			//删除备份表数据
			stmt.executeUpdate("DELETE FROM ods_ext_kpi_info_bak");
			//将原表数据备份到 备份表
			stmt.executeUpdate("INSERT INTO ods_ext_kpi_info_bak (SELECT * FROM ods_ext_kpi_info)");
			
			//清除原有数据导入
			if("1".equals(isClearData)){
				stmt.executeUpdate("DELETE FROM ods_ext_kpi_info");
			}
			//插入数据到原表
			for (int i = 0; i < kpiInfoList.size(); i++) {
				String sqlContent = "INSERT INTO ods_ext_kpi_info " +
						"(KPI_NAME,KPI_NUM,KPI_DEP,KPI_YM,KPI_CODE,COMPLETE_RATE,CURR_COM_VALUE,PRED_COM_VALUE,UNIT,DATA_SOURCE,FIRST_KPI,SECOND_KPI,THREE_KPI,SCOPE,STANDARD,PURPOSES,WEIGHTS,KPI_DEF,KPI_MEMO)" +
						"VALUES("
						+ "'"+ kpiInfoList.get(i).getKpiName() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiNum() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiDep() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiYm() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiCode() +"',"
						+ "'"+ kpiInfoList.get(i).getCompleteRate() + "',"
						+ "'"+ kpiInfoList.get(i).getCurrComValue() + "',"
						+ "'"+ kpiInfoList.get(i).getPredComValue() + "',"
						+ "'"+ kpiInfoList.get(i).getUnit() + "',"
						+ "'"+ kpiInfoList.get(i).getDataSource() + "',"
						+ "'"+ kpiInfoList.get(i).getFirstKpi() + "',"
						+ "'"+ kpiInfoList.get(i).getSecondKpi() + "',"
						+ "'"+ kpiInfoList.get(i).getThreeKpi() + "',"
						+ "'"+ kpiInfoList.get(i).getScope() + "',"
						+ "'"+ kpiInfoList.get(i).getStandard() + "',"
						+ "'"+ kpiInfoList.get(i).getPurposes() + "',"
						+ "'"+ kpiInfoList.get(i).getWeights() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiDef() + "',"
						+ "'"+ kpiInfoList.get(i).getKpiMemo() + "'"
						+")";
				stmt.executeUpdate(sqlContent);
			}
			con.commit();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}

		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}

	}
	
	
	/**
	 * 根据表格数据类型 回滚操作
	 * @param type
	 * @throws Exception
	 */
	public void restoreImportData(String type) throws Exception{
		if(!StringUtils.notNullAndSpace(type)){
			this.logger.warn("还原数据出错： 表格数据类型为空!");
			throw new Exception("未选择数据类型!");
		}
		Connection con = this.connect();
		Statement stmt = con.createStatement();
		try {
			con.setAutoCommit(false);
			
			switch (Integer.parseInt(type)) {
			case 1:
				//全国地区销量表
				//删除原表数据
				stmt.executeUpdate("DELETE FROM ods_ext_area_sales");
				//将备份表数据 插入原表
				stmt.executeUpdate("INSERT INTO ods_ext_area_sales (SELECT * FROM ods_ext_area_sales_bak)");
				break;
	        case 2:
	        	//渠道投入表
	        	//删除原表数据
				stmt.executeUpdate("DELETE FROM ods_ext_channel_inputs");
				//将备份表数据 插入原表
				stmt.executeUpdate("INSERT INTO ods_ext_channel_inputs (SELECT * FROM ods_ext_channel_inputs_bak)");
				break;
			case 3:
				//竞争对手销量表
				//删除原表数据
				stmt.executeUpdate("DELETE FROM ods_ext_competitors_sales");
				//将备份表数据 插入原表
				stmt.executeUpdate("INSERT INTO ods_ext_competitors_sales (SELECT * FROM ods_ext_competitors_sales_bak)");
				break;
	        case 4:
	        	//KPI指标表
	        	//删除原表数据
				stmt.executeUpdate("DELETE FROM ods_ext_kpi_info");
				//将备份表数据 插入原表
				stmt.executeUpdate("INSERT INTO ods_ext_kpi_info (SELECT * FROM ods_ext_kpi_info_bak)");
				break;
	        case 5:
	        	//全国彩种销量表
	        	//删除原表数据
				stmt.executeUpdate("DELETE FROM ods_ext_lot_sales");
				//将备份表数据 插入原表
				stmt.executeUpdate("INSERT INTO ods_ext_lot_sales (SELECT * FROM ods_ext_lot_sales_bak)");
				break;
			default:
				break;
			}
			con.commit();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			
		} catch (Exception e) {
			con.rollback();
			con.setAutoCommit(true);
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
			e.printStackTrace();
			throw e;

		}
		
	}
	

}
