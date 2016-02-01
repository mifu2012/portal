package com.infosmart.controller.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.infosmart.po.report.ReportCell;
import com.infosmart.util.StringUtils;


/**
 * 报表EXCEL下载
 * 
 * @author infosmart
 * 
 */
public class SelfExcelDownloadController extends AbstractExcelView {

	private static final String PAGE_DESCRIPT_SHEET_NAME = "报表数据明细";

	/**
	 * 实施EXCEL文件的构建
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map,
	 *      org.apache.poi.hssf.usermodel.HSSFWorkbook,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = (String) model.get("fileName");
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xls"
				+ "\"");
		createPageDescriptSheet(workbook, model);
	}

	/**
	 * 创建页面说明sheet
	 * 
	 * @param workbook
	 */
	@SuppressWarnings("unchecked")
	private void createPageDescriptSheet(HSSFWorkbook workbook, Map model) {
		List<List<String>> reportDataList = (List<List<String>>) model.get("reportDataList");
		List<ReportCell> reportCellList =(List<ReportCell>) model.get("reportCellList");
		if((reportCellList==null || reportCellList.isEmpty()) || (reportDataList==null ||reportDataList.isEmpty())){
			this.logger.warn("------------------------------->");
		}
		// 创建字体 红色、粗体
		HSSFFont font = workbook.createFont();
		// font.setColor(HSSFFont.COLOR_RED);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 表头样式
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setFont(font);
		// 数据样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 表1
		HSSFSheet sheet1 = this.creatSheetByWorkBook(workbook, 0,PAGE_DESCRIPT_SHEET_NAME);
		if (reportDataList != null && reportDataList.size() > 0) {
			int rowCount = reportDataList.size() + 5;// 初始化行数
			// 设置表1结构
			HSSFRow[] rows = new HSSFRow[rowCount];// 初始化可用行
			for (int i = 0; i < rows.length; i++) {
				rows[i] = sheet1.createRow((short) i);
			}
			int i = 0;
			List<ReportCell> mergeCellList = (List<ReportCell>) model.get("mergeCellList");
			if (mergeCellList != null && !mergeCellList.isEmpty()) {// 二级表头
				i = 0;
				for (int n = 0; n < mergeCellList.size(); n++) {
					HSSFCell cell12 = rows[0].createCell((short) i);// 从第一行(0)开始注入数据
					cell12.setCellStyle(style1);
					cell12.setCellValue(mergeCellList.get(n).getContent());
					sheet1.addMergedRegion(new Region(0,(short) i,0,(short) (i + (mergeCellList.get(n).getColSpan()) - 1)));
					i += mergeCellList.get(n).getColSpan();
				}
				i = 1;
				//List<String> queryColumn = (List<String>) model.get("excelTitleList");// 二级表头
				List<String> queryColumn = this.getexcelTitleList(reportCellList);// 二级表头
				this.logger.info("queryColumn=" + queryColumn);
				for (String column : queryColumn) {
					HSSFCell cell12 = rows[1].createCell((short) (i - 1));// 从第一行(0)开始注入数据
					i++;
					cell12.setCellStyle(style1);
					cell12.setCellValue(column);

				}
				i = 2;
				for (List<String> rowDataList : reportDataList) {
					// 行
					HSSFCell groupDescCell = rows[i].createCell((short) 0);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					groupDescCell.setCellStyle(style);
					// 列
					int j = 0;
					for (String cellData : rowDataList) {
						// EXCEL 数据
						HSSFCell urlCell = rows[i].createCell((short) j++);
						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						urlCell.setCellStyle(style);
						if (StringUtils.notNullAndSpace(cellData)) {
							urlCell.setCellValue(cellData);
						} else {
							urlCell.setCellValue("");
						}
					}
					i++;
				}
			} else {// 一级表头
				i = 0;
				//List<String> queryColumn = (List<String>) model.get("excelTitleList");
				List<String> queryColumn = this.getexcelTitleList(reportCellList);
				this.logger.info("queryColumn=" + queryColumn);
				for (String column : queryColumn) {
					HSSFCell cell12 = rows[0].createCell((short) i++);
					cell12.setCellStyle(style1);
					this.logger.info("column=" + column);
					cell12.setCellValue(column);

				}
				i = 1;
				for (List<String> rowDataList : reportDataList) {
					// 行
					HSSFCell groupDescCell = rows[i].createCell((short) 0);
					style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
					groupDescCell.setCellStyle(style);
					// 列
					int j = 0;
					for (String cellData : rowDataList) {
						// EXCEL 数据
						HSSFCell urlCell = rows[i].createCell((short) j++);
						style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
						urlCell.setCellStyle(style);
						if (StringUtils.notNullAndSpace(cellData)) {
							urlCell.setCellValue(cellData);
						} else {
							urlCell.setCellValue("");
						}
					}
					i++;
				}
			}
		}
	}

	/**
	 * 
	 * 在指定的工作簿中创建表
	 * 
	 * @param workbook
	 * @param index
	 *            表的序号
	 * @param sheetName
	 *            表名
	 * @return
	 */
	public HSSFSheet creatSheetByWorkBook(HSSFWorkbook workbook, int index,
			String sheetName) {
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(index, sheetName);
		return sheet;
	}

	/**
	 * 设置单元格的宽度（表对应的列宽）
	 * 
	 * @param sheet
	 *            (对应的表)
	 * @param columPoint
	 *            （表:列的位置）
	 * @param width
	 *            （宽度值）
	 */
	public void setColumWidth(HSSFSheet sheet, short columPoint, short width) {
		sheet.setColumnWidth(columPoint, width);
	}
	
	/**
	 * 获取表格头 1、当没有合并表头时为一级表头。2、当有合并表头为二级表头
	 * @param reportCellList
	 * @return
	 */
	public List<String> getexcelTitleList(List<ReportCell> reportCellList){
	List<String> excelTitleList = new ArrayList<String>();
	for (ReportCell reportCell : reportCellList) {
		if (reportCell == null)
			continue;
		if (excelTitleList.toString().length() > 0) {
			excelTitleList.add(reportCell.getContent());
		}
	}
	//modelMap.put("excelTitleList", excelTitleList);
	return excelTitleList;
	}
	
	private void getlist(List<ReportCell> reportCellList){
		int a [] = new int [5] ;  
		/*if(){
			
		}*/
	}
 
}
