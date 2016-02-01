package com.infosmart.controller.dwmis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisKpiGoal;
import com.infosmart.po.dwmis.DwmisKpiInfo;
import com.infosmart.service.dwmis.KPIInfoService;
import com.infosmart.service.dwmis.KpiGoalService;
import com.infosmart.util.DateUtils;

/**
 * 绩效管理
 * 
 */
@Controller
@RequestMapping("/kpiGoal")
public class KpiGoalController extends BaseController {
	@Autowired
	private KpiGoalService kpiGoalService;
	@Autowired
	private KPIInfoService kpiInfoService;

	protected final Logger logger = Logger.getLogger(this.getClass());
	public final double DEFAULT_NUMBER_FOR_AMCHART = 0.0D;
	// actionType
	public final String KPI_ADD = "add";
	// 月份
	private static String[] months = { "一月", "二月", "三月", "四月", "五月", "六月",
			"七月", "八月", "九月", "十月", "十一月", "十二月" };
	// 绩效页面
	private static final String KPI_GOAL_JSP = "/dwmis/kpiGoal/kpiGoal";
	// 其他绩效页面
	private static final String UNIQUE_ERROR_JSP = "/dwmis/kpiGoal/uniqueError";
	private final String SUCCESS_ACTION = "/common/save_result";

	/** 列表 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, DwmisKpiInfo dwmisKpiInfo) {
		String kpiCode = dwmisKpiInfo.getKpiCode();
		if (!"".equals(kpiCode) && kpiCode != null) {
			dwmisKpiInfo.setKpiCode(kpiCode.trim());
		}
		List<DwmisKpiInfo> kpiInfoList = kpiInfoService
				.getKPIInfoPages(dwmisKpiInfo);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/dwmis/kpiGoal/kpiInfo");
		mav.addObject("kpiInfoList", kpiInfoList);
		mav.addObject("dwmisKpiInfo", dwmisKpiInfo);
		return mav;
	}

	/**
	 * 绩效页面
	 * 
	 * @param kpiCode
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add{kpiCode}")
	public ModelAndView add(@PathVariable String kpiCode,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap map) throws Exception {
		DecimalFormat df = new DecimalFormat("#.################");
		String actionType = null;
		String goalType = null; // 判断是年绩效还是月绩效的参数
		DwmisKpiGoal kGoalInfo = null;
		String goalLowValue = null;// 页面上和年初值在同一行的3.5分值
		String goalHighValue = null;// 页面上和年初值在同一行的5分值
		try {
			if (kpiCode == null) {
				return new ModelAndView(KPI_GOAL_JSP);// KPICODE为空
			}
			List<DwmisKpiGoal> kList = new ArrayList<DwmisKpiGoal>();
			DwmisKpiInfo kpiInfo = kpiInfoService.selectKpiInfo(kpiCode);// 通过kpicode查询
			if (kpiInfo != null) {
				// 判断指标绩效类型是否是3001(月峰值)，3002(月谷值)，3009(月末值)，30011(月峰值-pt)，30012(月峰值-计去年)，30021(月谷值-pt)
				if (kpiInfo.getGoalType() == 3001
						|| kpiInfo.getGoalType() == 3002
						|| kpiInfo.getGoalType() == 3009
						|| kpiInfo.getGoalType() == 30011
						|| kpiInfo.getGoalType() == 30012
						|| kpiInfo.getGoalType() == 30021) {
					kList = kpiGoalService.getKPIGoals(kpiCode);
					// kGoalInfo = new DwmisKpiGoal();
					// if (kpiGoal == null) {
					// actionType = "add";
					// kGoalInfo.setMonth(DateUtils.getCurrentYear());
					// } else {
					// kGoalInfo.setMonth(kpiGoal.getGoalDate());
					// if (kpiGoal.getScore35() != null) {
					// kGoalInfo.setScore35(kpiGoal.getScore35());
					// }
					// if (kpiGoal.getScore5() != null) {
					// kGoalInfo.setScore5(kpiGoal.getScore5());
					// }
					// if (kpiGoal.getLastYearKPI() != null) {
					// kGoalInfo.setLastYearKPI(kpiGoal.getLastYearKPI());
					// }
					// if (kpiGoal.getGoalDate() != null) {
					// kGoalInfo.setGoalDate(kpiGoal.getGoalDate());
					// }
					// actionType = "update";
					// }
					// kList.add(kGoalInfo);
					goalType = "YEAR";

					// 判断指标绩效类型是否是日累计值3005(日累积值)，3007(七日均值峰值)，30051(日积累值-pt)，30052(日积累值-计入年)
				} else if (kpiInfo.getGoalType() == 3005
						|| kpiInfo.getGoalType() == 3007
						|| kpiInfo.getGoalType() == 30052
						|| kpiInfo.getGoalType() == 30051) {
					List<DwmisKpiGoal> goalList = kpiGoalService
							.getKPIGoals(kpiCode);
					if (goalList != null && goalList.size() > 0) {
						for (int i = 0; i < goalList.size(); i++) {
							kGoalInfo = new DwmisKpiGoal();
							if (goalList.get(i).getScore5() != null) {
								kGoalInfo
										.setScore5(goalList.get(i).getScore5());
							}
							if (goalList.get(i).getScore35() != null) {
								kGoalInfo.setScore35(goalList.get(i)
										.getScore35());
							}
							if (goalList.get(i).getLastYearKPI() != null) {
								kGoalInfo.setLastYearKPI(goalList.get(i)
										.getLastYearKPI());
							}
							if (goalList.get(i).getGoalDate() != null) {
								kGoalInfo.setGoalDate(goalList.get(i)
										.getGoalDate());
							}
							// kGoalInfo.setMonth(months[i]);
							kList.add(kGoalInfo);
						}
					}

					if (goalList != null && goalList.size() > 0) {
						if (goalList.size() >= 11) {
							if (goalList.get(11).getScore35() != null) {
								goalLowValue = df.format(goalList.get(11)
										.getScore35());
							}
							if (goalList.get(11).getScore5() != null) {
								goalHighValue = df.format(goalList.get(11)
										.getScore5());
							}
						}

						actionType = "update";
					} else {

						actionType = "add";
					}
					goalType = "MONTH";
				} else {
					// 3003(日峰值),3004(日谷值),3006(月积累值),3008(月当值)
					// Map<Integer, Map<Integer, String>> cacheMap =
					// misTypeManager
					// .getCacheMap();
					// Map<Integer, String> goalMap = cacheMap
					// .get(ConstantClass.KPI_GOAL_TYPE);
					// map.addAttribute("flag",
					// goalMap.get(kpiInfoDo.getGoalType()));
					// return "uniqueError.vm";
					String flag = "暂无绩效管理信息";
					map.addAttribute("flag", flag);
					this.logger.info("***********暂无绩效管理信息***********");
					return new ModelAndView(UNIQUE_ERROR_JSP, map);
				}
			}
			map.addAttribute("goalLowValue", goalLowValue);
			map.addAttribute("goalHighValue", goalHighValue);
			map.addAttribute("goalList", kList);
			map.addAttribute("goalType", goalType);
			map.addAttribute("actionType", actionType);
			map.addAttribute("kpiInfo", kpiInfo);
		} catch (Exception e) {
			this.logger.error("***********绩效页面出错***********", e);
			return null;
		}
		return new ModelAndView(KPI_GOAL_JSP, map);
	}

	/**
	 * 添加或者修改指标绩效记录
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView addAndUpdateKPIGoal(ModelMap map,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String actionType = request.getParameter("actionType").trim();
		String goalType = request.getParameter("goalType").trim();
		String kpiCode = request.getParameter("kpiCode").trim();
		String[] goalDate = request.getParameterValues("goalDate");
		String[] score35 = request.getParameterValues("score35");
		String[] score5 = request.getParameterValues("score5");
		String[] lastYearKPI = request.getParameterValues("lastYearKPI");
		List<DwmisKpiGoal> kpiGoalList = new ArrayList<DwmisKpiGoal>();
		try {
			if (score35 != null && score35.length > 0) {
				for (int i = 0; i < score35.length; i++) {
					DwmisKpiGoal kpiGoal = new DwmisKpiGoal();
					kpiGoal.setKpiCode(kpiCode);
					if (!"".equals(score35[i])) {
						kpiGoal.setScore35(Double.parseDouble(score35[i]));
					}
					if (!"".equals(score5[i])) {
						kpiGoal.setScore5(Double.parseDouble(score5[i]));
					}
					if (!"".equals(goalDate[i])) {
						kpiGoal.setGoalDate(goalDate[i]);
					}
					if (!"".equals(lastYearKPI[i])) {
						if (lastYearKPI[i] == null) {
							kpiGoal.setLastYearKPI(this.DEFAULT_NUMBER_FOR_AMCHART);
						} else {
							kpiGoal.setLastYearKPI(Double
									.parseDouble(lastYearKPI[i]));
						}
					}
					kpiGoalService.deleteKpiGoal(kpiGoal);
					kpiGoalList.add(kpiGoal);
				}
			}
			// if (this.KPI_ADD.equals(actionType)) {
			try {
				kpiGoalService.addKPIGoal(kpiGoalList, goalType);
				mv.addObject("msg", "success");
				this.insertLog(request, "添加绩效记录");
			} catch (Exception e) {
				mv.addObject("msg", "failed");
			}

			// } else {
			// try {
			// kpiGoalService.updateKPIGoal(kpiGoalList, goalType);
			// this.insertLog(request, "修改绩效记录");
			// mv.addObject("msg", "success");
			// } catch (Exception e) {
			// mv.addObject("msg", "failed");
			// }

			// }
		} catch (Exception e) {
			this.logger.info("kpiCode=" + kpiCode + "的绩效记录添加失败", e);
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	@RequestMapping(value = "/deleteKpiGoal")
	public void deleteKpiGoal(HttpServletRequest request,
			HttpServletResponse response) {
		String KpiCode = request.getParameter("kpiCode");
		String goalDate = request.getParameter("goalDate");

		DwmisKpiGoal kpiGoal = new DwmisKpiGoal();
		kpiGoal.setKpiCode(KpiCode);
		kpiGoal.setGoalDate(goalDate);

		try {
			kpiGoalService.deleteKpiGoal(kpiGoal);
			this.sendMsgToClient(this.isSuccess, response);
		} catch (Exception e) {
			this.sendMsgToClient(this.isFailed, response);
		}

	}
}
