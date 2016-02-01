/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.infosmart.portal.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.infosmart.portal.pojo.DwpasStKpiData;
import com.infosmart.portal.pojo.ProkpiAnalyze;
import com.infosmart.portal.util.NumberFormatter;

/**
 * 产品指标分析Excel下载Controller
 * 
 * @author
 * @version
 */
public class AnalyzeExcelDownloadController extends AbstractExcelView {

	private static final String PAGE_DESCRIPT_SHEET_NAME = "数据明细";

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
		List<ProkpiAnalyze> downloadHistories = (List<ProkpiAnalyze>) model
				.get("listKpiDates");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 表1
		HSSFSheet sheet1 = this.creatSheetByWorkBook(workbook, 0,
				PAGE_DESCRIPT_SHEET_NAME);
		if (downloadHistories != null && downloadHistories.size() > 0) {
			int rowCount = downloadHistories.size() + 1;
			// 设置表1结构
			HSSFRow[] rows = new HSSFRow[rowCount];// 初始化可用行
			for (int i = 0; i < rows.length; i++) {
				rows[i] = sheet1.createRow((short) i);
			}
			ProkpiAnalyze tmp = downloadHistories.get(0);
			// 标题单元格（设置列宽和标题设置:一行两列一起设置）
			for (int i = 0; i < tmp.getKpiNames().size() + 1; i++) {
				this.setColumWidth(sheet1, (short) i, (short) (15 * 256));
			}
			HSSFCell cell11 = rows[0].createCell((short) 0);
			cell11.setCellStyle(style);
			cell11.setCellValue("时间");
			int i = 1;
			for (String kpiName : tmp.getKpiNames()) {
				HSSFCell cell12 = rows[0].createCell((short) i++);
				cell12.setCellStyle(style);
				cell12.setCellValue(kpiName);

			}
			i = 1;
			for (ProkpiAnalyze prokpiAnalyze : downloadHistories) {
				HSSFCell groupDescCell = rows[i].createCell((short) 0);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				groupDescCell.setCellStyle(style);
				groupDescCell.setCellValue(prokpiAnalyze.getDate());
				int j = 1;
				for (DwpasStKpiData data : prokpiAnalyze.getDataList()) {
					HSSFCell urlCell = rows[i].createCell((short) j++);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
					urlCell.setCellStyle(style);
					if (null != data.getShowValue()) {
						urlCell.setCellValue(NumberFormatter.format(data
								.getShowValue()));
					} else {
						urlCell.setCellValue("-");
					}
				}
				i++;
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

}
