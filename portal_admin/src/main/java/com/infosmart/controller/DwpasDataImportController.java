package com.infosmart.controller;

import com.infosmart.po.*;
import com.infosmart.service.DwpasDataImportService;
import com.infosmart.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "dataImport")
public class DwpasDataImportController extends BaseController {
	@Autowired
	private DwpasDataImportService dataImportService;
	private final String IMPORT_SUCCESS_ACTION = "/common/import_result";

	@RequestMapping
	public ModelAndView showImportInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dataImport/dataImport");
		return mv;
	}

	/**
	 * 导入文件
	 *
	 * @param fileType
	 * @param isClearData
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importFile", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(
			@RequestParam("fileType") String fileType,
			@RequestParam("isClearData") String isClearData,
			@RequestParam("filePath") MultipartFile file,
			HttpServletRequest request) throws Exception {
		logger.info("传的文件：" + file.getOriginalFilename());
		ModelAndView mv = new ModelAndView(IMPORT_SUCCESS_ACTION);
		boolean isSuccess = false;
		if (!file.isEmpty()) {

			String tmpPath = System.getProperty("user.dir")
					+ System.getProperty("file.separator")
					+ System.currentTimeMillis() + "_"
					+ file.getOriginalFilename();
			this.logger.info("上传的文件存储路径：" + tmpPath);
			File uploadFile = new File(tmpPath);
			FileCopyUtils.copy(file.getBytes(), new File(tmpPath));
			try {
				switch (Integer.parseInt(fileType)) {
				case 1: {
					// 全国地区销量
					isSuccess = importAreaSalesData(tmpPath, isClearData);
					this.insertLog(request, "导入全国地区销量数据");
					break;
				}
				case 2: {
					// 渠道投入
					isSuccess = importChannelInputs(tmpPath, isClearData);
					this.insertLog(request, "导入渠道投入数据");
					break;
				}
				case 3: {
					// 竞争对手销量
					isSuccess = importCompetitorSalesData(tmpPath, isClearData);
					this.insertLog(request, "导入竞争对手销量数据");
					break;
				}
				case 4: {
					// KPI指标
					isSuccess = importKpiInfoData(tmpPath, isClearData);
					this.insertLog(request, "导入KPI指标数据");
					break;
				}
				case 5: {
					// 全国彩种销量
					isSuccess = importLotSalesData(tmpPath, isClearData);
					this.insertLog(request, "导入全国彩种销量数据");
					break;
				}
				}
			} catch (Exception e) {
				String errorMsg = "";
				if (StringUtils.notNullAndSpace(e.getMessage())) {
					errorMsg = ":" + e.getMessage();
				}
				mv.addObject("errorMsg", errorMsg);
				this.logger.error(e.toString());
			} finally {
				// 删除上传文件
				if (uploadFile.exists()) {
					uploadFile.delete();
				}
			}
		}

		if (isSuccess) {
			mv.addObject("msg", this.isSuccess);
		} else {
			mv.addObject("msg", this.isFailed);
		}
		return mv;
	}

	/**
	 * ODS_KPI指标手工导入
	 *
	 * @param filePath
	 * @param isClearData
	 * @return
	 * @throws Exception
	 */
	public boolean importKpiInfoData(String filePath, String isClearData)
			throws Exception {
		if (!StringUtils.notNullAndSpace(isClearData)) {
			this.logger.warn("导入kpi指标数据出错：isClearData为空");
			return false;
		}
		List<OdsExtKpiInfo> kpiInfoList = new ArrayList<OdsExtKpiInfo>();
		OdsExtKpiInfo kpiInfo = null;
		try {
			Workbook wb = this.getWorkbook(filePath);
			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				return false;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				if (sheet.getRow(0).getLastCellNum() != 19) {
					throw new Exception("请检查导入文件是否正确!");
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					kpiInfo = new OdsExtKpiInfo();
					for (short j = 0; j < row.getLastCellNum(); j++) {
						String cellValue = "";
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							cellValue = this.getCellValue(cell);
						}
						switch (j) {
						case 0:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("KPI指标不能为空!");
							// }
							kpiInfo.setKpiName(cellValue);
							break;
						case 1:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("KPI_NUM不能为空!");
							// }
							kpiInfo.setKpiNum(cellValue);
							break;
						case 2:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("部门不能为空!");
							// }
							kpiInfo.setKpiDep(cellValue);
							break;
						case 3:
							kpiInfo.setKpiYm(cellValue);
							break;
						case 4:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("KPI_CODE不能为空!");
							// }
							kpiInfo.setKpiCode(cellValue);
							break;
						case 5:
							kpiInfo.setCompleteRate(cellValue);
							break;
						case 6:
							kpiInfo.setCurrComValue(cellValue);
							break;
						case 7:
							kpiInfo.setPredComValue(cellValue);
							break;
						case 8:
							kpiInfo.setUnit(cellValue);
							break;
						case 9:
							kpiInfo.setDataSource(cellValue);
							break;
						case 10:
							kpiInfo.setFirstKpi(cellValue);
							break;
						case 11:
							kpiInfo.setSecondKpi(cellValue);
							break;
						case 12:
							kpiInfo.setThreeKpi(cellValue);
							break;
						case 13:
							kpiInfo.setScope(cellValue);
							break;
						case 14:
							kpiInfo.setStandard(cellValue);
							break;
						case 15:
							kpiInfo.setPurposes(cellValue);
						case 16:
							kpiInfo.setWeights(cellValue);
						case 17:
							kpiInfo.setKpiDef(cellValue);
						case 18:
							kpiInfo.setKpiMemo(cellValue);
						}

					}
					kpiInfoList.add(kpiInfo);
				}
			}
			// 插入的方法
			dataImportService.importKpiInfoData(kpiInfoList, isClearData);
		} catch (Exception e) {
			throw (e);
		}
		return true;
	}

	/**
	 * ODS_全国彩种销量导入
	 *
	 * @param filePath
	 * @param isClearData
	 * @return
	 * @throws Exception
	 */
	public boolean importLotSalesData(String filePath, String isClearData)
			throws Exception {
		if (!StringUtils.notNullAndSpace(isClearData)) {
			this.logger.warn("导入kpi指标数据出错：isClearData为空");
			return false;
		}
		List<OdsExtLotSales> lotSalesList = new ArrayList<OdsExtLotSales>();
		OdsExtLotSales lotSalesInfo = null;
		try {
			Workbook wb = this.getWorkbook(filePath);
			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				return false;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				if (sheet.getRow(0).getLastCellNum() != 11) {
					throw new Exception("请检查导入文件是否正确!");
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					lotSalesInfo = new OdsExtLotSales();
					for (short j = 0; j < row.getLastCellNum(); j++) {
						String cellValue = "";
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							cellValue = this.getCellValue(cell);
						}
						switch (j) {
						case 0:
							lotSalesInfo.setLotName(cellValue);
							break;
						case 1:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("KPI_NUM不能为空!");
							// }
							lotSalesInfo.setKpiNum(cellValue);
							break;
						case 2:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("部门不能为空!");
							// }
							lotSalesInfo.setKpiDep(cellValue);
							break;
						case 3:
							lotSalesInfo.setKpiYm(cellValue);
							break;
						case 4:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("KPI_CODE不能为空!");
							// }
							lotSalesInfo.setKpiCode(cellValue);
							break;
						case 5:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("LOTID 不能为空!");
							// }
							lotSalesInfo.setLotid(cellValue);
							break;
						case 6:
							lotSalesInfo.setClass1(cellValue);
							break;
						case 7:
							lotSalesInfo.setClass2(cellValue);
							break;
						case 8:
							lotSalesInfo.setClass3(cellValue);
							break;
						case 9:
							lotSalesInfo.setSales(cellValue);
							break;
						case 10:
							// if(!StringUtils.notNullAndSpace(cellValue)){
							// throw new Exception("单位不能为空!");
							// }
							lotSalesInfo.setUnit(cellValue);
							break;
						}

					}
					lotSalesList.add(lotSalesInfo);
				}
			}
			// 插入的方法
			dataImportService.importLotSalesData(lotSalesList, isClearData);
		} catch (Exception e) {
			throw (e);
		}
		return true;
	}

	/**
	 * 地区销量导入
	 *
	 * @param filePath
	 * @param isClearData
	 * @return
	 * @throws Exception
	 */
	public boolean importAreaSalesData(String filePath, String isClearData)
			throws Exception {
		if (!StringUtils.notNullAndSpace(isClearData)) {
			this.logger.warn("导入kpi指标数据出错：isClearData为空");
			return false;
		}
		List<OdsExtAreaSales> areaSalesList = new ArrayList<OdsExtAreaSales>();
		OdsExtAreaSales areaSalesInfo = null;
		try {
			Workbook wb = this.getWorkbook(filePath);

			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				return false;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				if (sheet.getRow(0).getLastCellNum() != 9) {
					throw new Exception("请检查导入文件是否正确!");
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					areaSalesInfo = new OdsExtAreaSales();
					for (short j = 0; j < row.getLastCellNum(); j++) {
						String cellValue = "";
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							cellValue = this.getCellValue(cell);
						}
						switch (j) {
						case 0:
							areaSalesInfo.setLotid(cellValue);
							break;
						case 1:
							areaSalesInfo.setKpiNum(cellValue);
							break;
						case 2:
							areaSalesInfo.setKpiDep(cellValue);
							break;
						case 3:
							areaSalesInfo.setKpiYm(cellValue);
							break;
						case 4:
							areaSalesInfo.setKpiCode(cellValue);
							break;
						case 5:
							areaSalesInfo.setClass1(cellValue);
							break;
						case 6:
							areaSalesInfo.setMonthSales(cellValue);
							break;
						case 7:
							areaSalesInfo.setYearSalesAccu(cellValue);
							break;
						case 8:
							areaSalesInfo.setUnit(cellValue);
							break;
						}

					}
					areaSalesList.add(areaSalesInfo);
				}
			}
			// 插入的方法
			dataImportService.importAreaSalesData(areaSalesList, isClearData);

		} catch (Exception e) {
			throw (e);
		}
		return true;
	}

	/**
	 * ODS_竞争对手销量导入
	 *
	 * @param filePath
	 * @param isClearData
	 * @return
	 * @throws Exception
	 */
	public boolean importCompetitorSalesData(String filePath, String isClearData)
			throws Exception {
		if (!StringUtils.notNullAndSpace(isClearData)) {
			this.logger.warn("导入kpi指标数据出错：isClearData为空");
			return false;
		}
		List<OdsExtCompetitorsSales> competitorsSalesList = new ArrayList<OdsExtCompetitorsSales>();
		OdsExtCompetitorsSales competitorsSalesInfo = null;
		try {
			Workbook wb = this.getWorkbook(filePath);
			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				return false;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				if (sheet.getRow(0).getLastCellNum() != 7) {
					throw new Exception("请检查导入文件是否正确!");
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					competitorsSalesInfo = new OdsExtCompetitorsSales();
					for (short j = 0; j < row.getLastCellNum(); j++) {
						String cellValue = "";
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							cellValue = this.getCellValue(cell);
						}
						switch (j) {
						case 0:
							competitorsSalesInfo.setCompetitors(cellValue);
							break;
						case 1:
							competitorsSalesInfo.setKpiNum(cellValue);
							break;
						case 2:
							competitorsSalesInfo.setKpiDep(cellValue);
							break;
						case 3:
							competitorsSalesInfo.setKpiYm(cellValue);
							break;
						case 4:
							competitorsSalesInfo.setKpiCode(cellValue);
							break;
						case 5:
							competitorsSalesInfo.setCurrComValue(cellValue);
							break;
						case 6:
							competitorsSalesInfo.setUnit(cellValue);
							break;
						}

					}
					competitorsSalesList.add(competitorsSalesInfo);
				}
			}
			// 插入的方法
			dataImportService.importCompetitorsSalesData(competitorsSalesList,
					isClearData);

		} catch (Exception e) {
			throw (e);
		}
		return true;
	}

	/**
	 * 渠道投入手工导入
	 *
	 * @param filePath
	 * @param isClearData
	 * @return
	 * @throws Exception
	 */
	public boolean importChannelInputs(String filePath, String isClearData)
			throws Exception {
		if (!StringUtils.notNullAndSpace(isClearData)) {
			this.logger.warn("导入kpi指标数据出错：isClearData为空");
			return false;
		}
		List<OdsExtChannelInputs> channelInputsList = new ArrayList<OdsExtChannelInputs>();
		OdsExtChannelInputs channelInputsInfo = null;
		try {
			Workbook wb = this.getWorkbook(filePath);
			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				return false;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				if (sheet.getRow(0).getLastCellNum() != 9) {
					throw new Exception("请检查导入文件是否正确!");
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					channelInputsInfo = new OdsExtChannelInputs();
					for (short j = 0; j < row.getLastCellNum(); j++) {
						String cellValue = "";
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							cellValue = this.getCellValue(cell);
						}
						switch (j) {
						case 0:
							channelInputsInfo.setChannel(cellValue);
							break;
						case 1:
							channelInputsInfo.setKpiNum(cellValue);
							break;
						case 2:
							channelInputsInfo.setKpiDep(cellValue);
							break;
						case 3:
							channelInputsInfo.setThrowinStart(cellValue);
							break;
						case 4:
							channelInputsInfo.setThrowinEnd(cellValue);
							break;
						case 5:
							channelInputsInfo.setThrowinRange(cellValue);
							break;
						case 6:
							channelInputsInfo.setKpiCode(cellValue);
							break;
						case 7:
							channelInputsInfo.setCurrComValue(cellValue);
							break;
						case 8:
							channelInputsInfo.setUnit(cellValue);
							break;
						}

					}
					channelInputsList.add(channelInputsInfo);
				}
			}
			// 插入的方法
			dataImportService.importChannelInputsData(channelInputsList,
					isClearData);
		} catch (Exception e) {
			throw (e);
		}
		return true;
	}

	@RequestMapping("/restoreImportData")
	public void restoreImportData(HttpServletRequest request,
			HttpServletResponse response) {
		String importMsg = "";
		String selValue = request.getParameter("selValue");
		if (!StringUtils.notNullAndSpace(selValue)) {
			this.logger.warn("还原数据出错：未选择还原类别,selValue为空");
			importMsg = "还原数据失败：数据类型为空";
			return;
		}
		try {
			dataImportService.restoreImportData(selValue);
			this.insertLog(request, "回滚导入数据");
		} catch (Exception e) {
			this.logger.warn(e);
			importMsg = e.toString();
		}
		this.sendMsgToClient(importMsg, response);
	}

	// 判断从Excel文件中解析出来数据的格式
	private String getCellValue(Cell cell) {
		String value = null;
		// 简单的查检列类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:// 字符串
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:// 数字
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		case Cell.CELL_TYPE_FORMULA:
			// value = String.valueOf(cell.getCellFormula());
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_BOOLEAN:// boolean型值
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		return value;
	}

	private Workbook getWorkbook(String filePath) {
		Workbook wb = null;
		try {
			if (filePath.endsWith(".xlsx")) {
				FileInputStream inp = new FileInputStream(filePath);
				wb = WorkbookFactory.create(inp);
				inp.close();
				// wb = new XSSFWorkbook(filePath);
			} else if (filePath.endsWith(".xls")) {
				FileInputStream input = new FileInputStream(filePath);
				wb = new HSSFWorkbook(input);
				input.close();
			} else {
				this.logger.warn("请选择EXCEL文件");
				return null;
			}
		} catch (Exception e) {
			this.logger.warn(e);
			return null;
		}
		return wb;
	}

	@RequestMapping(value = "down")
	public void downFile(HttpServletRequest request,
			HttpServletResponse response, String filePath) throws Exception {
		String type = request.getParameter("type")==null ? "xxx" :request.getParameter("type");
		request.setCharacterEncoding("utf-8");
		if (StringUtils.notNullAndSpace(filePath)) {
			try {
				filePath = new String(filePath.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e) {

			}
		}
		OutputStream o = response.getOutputStream();
		byte b[] = new byte[500];
		/** * 得到文件的当前路径 * @param args */
		String serverpath = request.getSession().getServletContext()
				.getRealPath("/");
		File fileLoad = new File(serverpath + filePath);
		response.setContentType("application/octet-stream");
		String flieName = "";
		if(type.equals("1")){
			flieName = "nationalSales.xlsx";
		}else if(type.equals("2")){
			flieName = "channel.xlsx";
		}else if(type.equals("3")){
			flieName = "competitor.xlsx";
		}else if(type.equals("4")){
			flieName = "kpi.xlsx";
		}else if(type.equals("5")){
			flieName = "areaSales.xlsx";
		}else{
			flieName = "kpiData.txt";
		}
		response.setHeader("content-disposition", "attachment; filename="
				+ flieName);
		long fileLength = fileLoad.length();
		String length1 = String.valueOf(fileLength);
		response.setHeader("Content_Length", length1);
		FileInputStream in = new FileInputStream(fileLoad);
		int n;
		while ((n = in.read(b)) != -1) {
			o.write(b, 0, n);
		}
		in.close();
		o.close();

	}

}
