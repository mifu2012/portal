package com.infosmart.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.DwpasCKpiInfo;
import com.infosmart.po.DwpasStKpiData;
import com.infosmart.po.KpiInfo;
import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.KpiInfoService;
import com.infosmart.util.StringUtils;

@Controller
@RequestMapping("/kpiinfo1")
public class DwpasKpiInfoController extends BaseController {
	@Autowired
	private KpiInfoService kpiInfoService;
	private final String SUCCESS_ACTION = "/common/save_result";
	protected final String IMPORT_SUCCESS_ACTION = "/common/import_result";

	@RequestMapping
	public ModelAndView getAllKpiInfo(HttpServletRequest req,
			HttpServletResponse res) {
		// 指标大类查询
		List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		// 指标信息列表查询
		List<KpiInfo> kpiList = kpiInfoService.queryKpiInfos1();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/kpiinfo/kpiinfo1");
		mv.addObject("cSysTypeDOList", cSysTypeDOList);
		mv.addObject("kpiList", kpiList);
		mv.addObject("operFlag", "save");
		this.insertLog(req, "查询指标列表");
		return mv;
	}

	/**
	 * 根据kpiCode查询
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/select")
	public void getKpiInfo(HttpServletRequest req, HttpServletResponse res) {
		String kpiCode = req.getParameter("kpiCode");
		KpiInfo kpiinfo = new KpiInfo();
		kpiinfo = kpiInfoService.queryKpiInfoByCode(kpiCode);
		// 指标大类查询
		// List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		// 指标信息列表查询
		// List<KpiInfo> kpiList = kpiInfoService.queryKpiInfos1();
		JSONObject jSON = null;
		jSON = JSONObject.fromObject(kpiinfo);
		PrintWriter out;
		try {
			res.setHeader("Cache-Control", "no-cache");
			res.setContentType("text/json;charset=UTF-8");
			out = res.getWriter();
			out.print(jSON.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param req
	 * @param res
	 * @param kpiinfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/add")
	public ModelAndView addKpiInfo(HttpServletRequest req,
			HttpServletResponse res, KpiInfo kpiinfo) throws ParseException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (kpiinfo == null) {
			this.logger.warn("添加数据时传递的指标信息对象为null");
			mv.addObject("msg", isFailed);
			return mv;
		}

		try {
			KpiInfo existKpiInfo = kpiInfoService.queryKpiInfoByCode(kpiinfo
					.getKpiCode());
			if (existKpiInfo == null) {
				boolean success = kpiInfoService.insertKpiInfo(kpiinfo);
				mv.addObject("msg", success ? this.isSuccess : this.isFailed);
				this.insertLog(req, "新增指标" + kpiinfo.getKpiCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", isFailed);
			logger.error("指标管理新增失败:" + e.getMessage(), e);
		}
		return mv;
	}

	/**
	 * 修改数据
	 * 
	 * @param req
	 * @param res
	 * @param kpiinfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/update")
	public ModelAndView updateKpiInfo(HttpServletRequest req,
			HttpServletResponse res, KpiInfo kpiinfo) throws ParseException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(SUCCESS_ACTION);
		if (kpiinfo == null) {
			this.logger.warn("修改数据时传递的kpiInfo对象为null");
			mv.addObject("msg", isFailed);
			return mv;
		}

		// // 指标信息列表查询
		// List<KpiInfo> kpiList = kpiInfoService.queryKpiInfos1();
		// // 指标大类查询
		// List<MisTypeInfo> cSysTypeDOList = kpiInfoService.qryCSysType();
		try {
			boolean success = kpiInfoService.updateKpiInfo(kpiinfo);
			mv.addObject("msg", success ? this.isSuccess : this.isFailed);
			this.insertLog(req, "修改指标" + kpiinfo.getKpiCode());
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", isFailed);
			logger.error("指标信息修改保存失败" + e.getMessage(), e);
		}

		return mv;
	}

	/**
	 * 删除数据
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping("/del")
	public void deleteKpiInfo(HttpServletRequest req, HttpServletResponse res) {
		String kpiCode = req.getParameter("kpiCode");
		try {
			String result = null;
			result = kpiInfoService.selKpiInfoLikeRoleFormula(kpiCode);
			if (result != null && !("").equals(result)) {
				this.sendMsgToClient(result, res);
			} else {
				kpiInfoService.deleteKpiInfoByCode(kpiCode);
				this.insertLog(req, "删除指标" + kpiCode);
				this.sendMsgToClient("success", res);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.logger.error("删除指标信息失败：" + ex.getMessage(), ex);
			this.sendMsgToClient("failed", res);
		}
	}

	/**
	 * 获得基础指标列表
	 * 
	 * @param modelMap
	 */
	@RequestMapping("/choicecode{sign}")
	public ModelAndView choicecode(@PathVariable String sign,
			HttpServletRequest request, HttpServletResponse response) {
		// String sign = request.getParameter("sign");
		if (!StringUtils.notNullAndSpace(sign)) {
			this.logger.warn("获得指标列表时传递的值为null");
		}
		List<KpiInfo> kpiinfoList = kpiInfoService.queryBaseKpiInfos();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/kpiinfo/choicecode");
		mav.addObject("kpiinfoList", kpiinfoList);
		mav.addObject("sign", sign);
		return mav;
	}

	/**
	 * 查询所有指标
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("/queryByKpiName")
	public void queryByKpiName(HttpServletRequest req, HttpServletResponse res,
			KpiInfo kpiInfo) {
		this.logger.info("查询指标信息");
		List<KpiInfo> kpiinfoList = new ArrayList<KpiInfo>();
		try {
			kpiinfoList = kpiInfoService.queryKpiInfos(kpiInfo);
			this.sendJsonMsgToClient(kpiinfoList, res);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 校验kpiCode的唯一性
	 */
	@RequestMapping("/alidateKpiCode")
	public void alidateKpiCode(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String kpiCode = req.getParameter("kpiCode");
		KpiInfo kpiinfo = kpiInfoService.queryKpiInfoByCode(kpiCode);
		if (kpiinfo == null) {
			this.sendMsgToClient("0", res);
		} else {
			this.sendMsgToClient("1", res);
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goToImportPage")
	public ModelAndView goToImportPage(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String kpiCode = request.getParameter("kpiCode");
		String kpiType = request.getParameter("kpiType");
		if (kpiCode == null || ("").equals(kpiCode)) {
			this.logger.info("进入导入页面出错，kpiCode为空！");
			return null;
		}
		mv.addObject("kpiCode", kpiCode);
		mv.addObject("kpiType", kpiType);
		mv.setViewName("admin/kpiinfo/import");
		return mv;
	}

	/**
	 * 单文件上传
	 * 
	 * @param name
	 * @RequestParam 取得name字段的值
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/oneFileUpload", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(
			@RequestParam("fileType") String fileType,
			@RequestParam("filePath") MultipartFile file,
			@RequestParam("kpiCode") String kpiCode,
			@RequestParam("kpiType") String kpiType,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// System.out.println("--------------------->");
		logger.info("传的文件：" + file.getOriginalFilename());
		ModelAndView mv = new ModelAndView(IMPORT_SUCCESS_ACTION);
		Map map = new HashMap<String, String>();
		String fileName = file.getOriginalFilename().toLowerCase();
		if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")
				&& !fileName.endsWith(".txt")) {
			mv.addObject("msg", this.isFailed);
			mv.addObject("errorMsg", "错误的文件格式");
			return mv;
		}
		map.put("isSuccess", "failed");
		map.put("errorMsg", "上传文件错误或为空！");
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
					map = importExcelData(tmpPath, kpiCode,
							Integer.parseInt(kpiType));
					break;
				}
				case 2: {
					map = importTxtData(tmpPath, kpiCode,
							Integer.parseInt(kpiType));
					break;
				}
				}
			} catch (Exception e) {
				mv.addObject("errorMsg", e.toString().replaceAll("\"", " "));
				e.printStackTrace();
				this.logger.error(e.toString());
			} finally {
				// 删除上传文件
				if (uploadFile.exists()) {
					uploadFile.delete();
				}
			}
		}
		if (("success").equals(map.get("isSuccess"))) {
			this.insertLog(request, "上传KpiData成功！");
			mv.addObject("msg", this.isSuccess);
		} else {
			this.insertLog(request, "上传KpiData失败：" + map.get("errorMsg"));
			mv.addObject("msg", this.isFailed);
			mv.addObject("errorMsg", map.get("errorMsg"));
		}
		return mv;
	}

	/**
	 * 导入txt文件
	 * 
	 * @param filePath
	 * @return
	 */
	public Map importTxtData(String filePath, String kpiCode, int kpiType) {
		DwpasStKpiData dwpasStKpiData = null;
		Map map = new HashMap<String, String>();
		int m = 0;
		try {
			// 判断文件是否为txt文件
			if (!(filePath.toLowerCase().endsWith(".txt"))) {
				this.logger.warn("您必须上传txt格式的数据文件");
				map.put("isSuccess", "failed");
				map.put("errorMsg", "您必须上传txt格式的数据文件");
				return map;
			}
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String line = "";
			int i = 1;
			while ((line = in.readLine()) != null) {
				dwpasStKpiData = new DwpasStKpiData();
				if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
					dwpasStKpiData.setDateType(DwpasStKpiData.DATE_TYPE_OF_DAY);
				} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
					dwpasStKpiData
							.setDateType(DwpasStKpiData.DATE_TYPE_OF_WEEK);
				} else {
					dwpasStKpiData
							.setDateType(DwpasStKpiData.DATE_TYPE_OF_MONTH);
				}
				StringTokenizer st = new StringTokenizer(line, ",");
				dwpasStKpiData.setKpiCode(kpiCode.trim());
				if (st.hasMoreTokens()) {
					String reportDate = st.nextToken();
					dwpasStKpiData.setReportDate(reportDate.trim());
				} else {
					this.logger.warn("reportDate不能为空！");
				}
				if (st.hasMoreTokens()) {
					String baseValue = st.nextToken();
					dwpasStKpiData
							.setBaseValue(baseValue == null ? new BigDecimal(0)
									: new BigDecimal(baseValue.trim()));
				} else {
					dwpasStKpiData.setBaseValue(new BigDecimal(0));
				}
				if (st.hasMoreTokens()) {
					// '均值',
					String vgeValue = st.nextToken();
					dwpasStKpiData
							.setAgeValue(vgeValue == null ? new BigDecimal(0)
									: new BigDecimal(vgeValue.trim()));
				} else {
					dwpasStKpiData.setAgeValue(new BigDecimal(0));
				}
				if (st.hasMoreTokens()) {
					// '趋势值',
					String trendValue = st.nextToken();
					dwpasStKpiData
							.setTrendValue(trendValue == null ? new BigDecimal(
									0) : new BigDecimal(trendValue.trim()));
				} else {
					dwpasStKpiData.setTrendValue(new BigDecimal(0));
				}
				if (st.hasMoreTokens()) {
					// '占比值',
					String perVlaue = st.nextToken();
					dwpasStKpiData
							.setPerValue(perVlaue == null ? new BigDecimal(0)
									: new BigDecimal(perVlaue.trim()));
				} else {
					dwpasStKpiData.setPerValue(new BigDecimal(0));
				}
				if (st.hasMoreTokens()) {
					// '最大值',
					String maxVlaue = st.nextToken();
					dwpasStKpiData
							.setMaxValue(maxVlaue == null ? new BigDecimal(0)
									: new BigDecimal(maxVlaue.trim()));
				} else {
					dwpasStKpiData.setMaxValue(new BigDecimal(0));
				}
				if (st.hasMoreTokens()) {
					// '最小值',
					String minValue = st.nextToken();
					dwpasStKpiData
							.setMinValue(minValue == null ? new BigDecimal(0)
									: new BigDecimal(minValue.trim()));
				} else {
					dwpasStKpiData.setMinValue(new BigDecimal(0));
				}
				// 插入数据
				this.kpiInfoService.insertKpiData(dwpasStKpiData);
				System.out.println(i);
				i++;
				m = i;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("isSuccess", "failed");
			System.out.println(m);
			map.put("errorMsg", "上传txt文件出错：第" + m + "行错误！");
			return map;
		}
		map.put("isSuccess", "success");
		return map;
	}

	/**
	 * 导入Excel数据
	 * 
	 * @param request
	 * @param resonse
	 * @return
	 */
	private Map importExcelData(String filePath, String kpiCode, int kpiType) {
		if (filePath == null || filePath.length() == 0)
			return null;
		Map map = new HashMap<String, String>();
		DwpasStKpiData dwpasStKpiData = null;
		int m = 0;
		boolean isSuccess = true;
		try {
			Workbook wb = this.getWorkbook(filePath);
			if (wb == null) {
				this.logger.warn("导入Excel表格出错:Workbook为空");
				map.put("isSuccess", "failed");
				map.put("errorMsg", "导入Excel表格出错:Workbook为空");
				return map;
			}
			Sheet sheet = null;
			for (int numSheets = 0; numSheets < wb.getNumberOfSheets(); numSheets++) {
				if (null != wb.getSheetAt(numSheets)) {
					sheet = wb.getSheetAt(numSheets);// 获得一个sheet
				}
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					m = i;
					Row row = sheet.getRow(i);
					dwpasStKpiData = new DwpasStKpiData();
					dwpasStKpiData.setGmtCreate(new Date());
					for (short j = 0; j < row.getLastCellNum(); j++) {
						if (null != row.getCell(j)) {
							Cell cell = row.getCell(j);
							String cellValue = this.getCellValue(row, cell);
							dwpasStKpiData.setKpiCode(kpiCode);
							switch (j) {
							case 0:
								// '数据日期',
								if (!StringUtils.notNullAndSpace(cellValue)) {
									this.logger.warn("第" + i + "行第" + j
											+ "列数据日期格式错误或数据非法");
									isSuccess = false;
									break;
								}
								dwpasStKpiData.setReportDate(cellValue);
								break;
							case 1:
								// '基础值',
								try {
									dwpasStKpiData.setBaseValue(new BigDecimal(
											cellValue));
								} catch (Exception e) {
									dwpasStKpiData.setBaseValue(new BigDecimal(
											0));
									this.logger.warn("第" + i + "行第" + j
											+ "列基础值数据非法");
									// 错误的数据
									isSuccess = false;
									break;
								}
								break;
							case 2:
								// '均值',
								try {
									dwpasStKpiData.setAgeValue(new BigDecimal(
											cellValue));
								} catch (Exception e) {
									this.logger.warn("第" + i + "行第" + j
											+ "列均值数据非法,默认为0");
									dwpasStKpiData
											.setAgeValue(new BigDecimal(0));
								}
								break;
							case 3:
								// '趋势值',
								try {
									dwpasStKpiData
											.setTrendValue(new BigDecimal(
													cellValue));
								} catch (Exception e) {
									this.logger.warn("第" + i + "行第" + j
											+ "列趋势值数据非法,默认为0");
									dwpasStKpiData
											.setTrendValue(new BigDecimal(0));
								}
								break;
							case 4:
								// '占比值',
								try {
									dwpasStKpiData.setPerValue(new BigDecimal(
											cellValue));
								} catch (Exception e) {
									this.logger.warn("第" + i + "行第" + j
											+ "列占比值数据非法,默认为0");
									dwpasStKpiData
											.setPerValue(new BigDecimal(0));
								}
								break;
							case 5:
								// '最大值',
								try {
									dwpasStKpiData.setMaxValue(new BigDecimal(
											cellValue));
								} catch (Exception e) {
									this.logger.warn("第" + i + "行第" + j
											+ "列最大值数据非法,默认为0");
									dwpasStKpiData
											.setMaxValue(new BigDecimal(0));
								}
								break;
							case 6:
								// '最小值',
								try {
									dwpasStKpiData.setMinValue(new BigDecimal(
											cellValue));
								} catch (Exception e) {
									this.logger.warn("第" + i + "行第" + j
											+ "列最小值数据非法,默认为0");
									dwpasStKpiData
											.setMinValue(new BigDecimal(0));
								}
								break;
							}
						}
					}
					// '日期类型,D-日，W-周，M-月'
					if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_DAY) {
						dwpasStKpiData
								.setDateType(DwpasStKpiData.DATE_TYPE_OF_DAY);
					} else if (kpiType == DwpasCKpiInfo.KPI_TYPE_OF_WEEK) {
						dwpasStKpiData
								.setDateType(DwpasStKpiData.DATE_TYPE_OF_WEEK);
					} else {
						dwpasStKpiData
								.setDateType(DwpasStKpiData.DATE_TYPE_OF_MONTH);
					}
					try {
						this.kpiInfoService.insertKpiData(dwpasStKpiData);
					} catch (Exception e) {
						e.printStackTrace();
						this.logger.error(e.getMessage(), e);
						isSuccess = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			this.logger.error(e);
			map.put("isSuccess", "failed");
			map.put("errorMsg", "导入Excel表格出错:第" + m + "行出错！");
			return map;
		}
		if (isSuccess) {
			map.put("isSuccess", "success");
		} else {
			map.put("isSuccess", "failed");
			map.put("errorMsg", "导入Excel表格出错:第" + m + "行出错！");
		}
		return map;
	}

	/**
	 * 获取值
	 * 
	 * @param row
	 * @param cell
	 * @return
	 */
	private String getCellValue(Row row, Cell cell) {
		String cellValue = "";
		if (cell.getCellType() == 1) {
			cellValue = cell.getStringCellValue();
		} else {
			java.text.NumberFormat nf = NumberFormat.getCurrencyInstance();
			DecimalFormat df = (DecimalFormat) nf;
			df.setDecimalSeparatorAlwaysShown(true);
			df.applyPattern("###############");
			cellValue = df.format(new Double(cell.getNumericCellValue()));
		}
		return cellValue;
	}

	/**
	 * 获取Workbook
	 * 
	 * @param filePath
	 * @return
	 */
	private Workbook getWorkbook(String filePath) {
		Workbook wb = null;
		try {
			if (filePath.toLowerCase().endsWith(".xlsx")) {
				wb = new XSSFWorkbook(filePath);
			} else if (filePath.toLowerCase().endsWith(".xls")) {
				wb = new HSSFWorkbook(new FileInputStream(filePath));
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

}