package com.infosmart.controller.dwmis;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisMisDptmnt;
import com.infosmart.po.dwmis.DwmisMisType;
import com.infosmart.service.dwmis.DwmisMisDptmntService;
import com.infosmart.service.dwmis.DwmisMisTypeService;
import com.infosmart.util.dwmis.CoreConstant;

@Controller
@RequestMapping("/dwmisMisDptmnt")
public class DwmisMisDptmntController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private DwmisMisDptmntService dwmisMisDptmntService;
	@Autowired
	private DwmisMisTypeService dwmisMisTypeService;

	/**
	 * 查询所有部门信息
	 * 
	 * @param req
	 * @param res
	 * @param dwmisMisDptmnt
	 * @return
	 */
	@RequestMapping(value = "/search")
	public ModelAndView searchDwmisMisDptmnt(HttpServletRequest req,
			HttpServletResponse res, DwmisMisDptmnt dwmisMisDptmnt)
			throws Exception {
		String depName = dwmisMisDptmnt.getDepName();
		if (depName != null && !"".equals(depName)) {
			dwmisMisDptmnt.setDepName(depName.trim());
		}
		List<DwmisMisDptmnt> dwmisMisDptmnts = dwmisMisDptmntService
				.getALLDwmisMisDptmntPage(dwmisMisDptmnt);
		List<DwmisMisType> dwmisMisTypes = dwmisMisTypeService
				.getAllDwmisMisTypes(CoreConstant.DEPT_TYPE);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dwmisMisTypes", dwmisMisTypes);
		mav.addObject("dwmisMisDptmnts", dwmisMisDptmnts);
		mav.addObject("dwmisMisDptmnt", dwmisMisDptmnt);
		mav.setViewName("/dwmis/depmanage/depmanage");
		return mav;
	}

	/**
	 * 跳转 部门编辑页面
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView editDwmisMisDptmnt(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mv = new ModelAndView();
		String depId = req.getParameter("depId");
		if (!StringUtils.isBlank(depId)) {
			DwmisMisDptmnt dwmisMisDptmnt = dwmisMisDptmntService
					.queryDwmisMisDptmntBydepId(depId);
			mv.addObject("dwmisMisDptmnt", dwmisMisDptmnt);
		}
		List<DwmisMisType> dwmisMisTypes = dwmisMisTypeService
				.getAllDwmisMisTypes(CoreConstant.DEPT_TYPE);
		mv.addObject("dwmisMisTypes", dwmisMisTypes);
		mv.setViewName("/dwmis/depmanage/depmanage_info");
		return mv;
	}

	/**
	 * 保存部门信息
	 * 
	 * @param request
	 * @param response
	 * @param dwmisMisDptmnt
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveDwmisMisDptmnt(HttpServletRequest request,
			HttpServletResponse response, DwmisMisDptmnt dwmisMisDptmnt) {
		ModelAndView mav = new ModelAndView();
		if (dwmisMisDptmnt != null) {
			if (StringUtils.isBlank(dwmisMisDptmnt.getDepId())) {
				boolean isSuccuss = dwmisMisDptmntService
						.insertDwmisMisDptmnt(dwmisMisDptmnt);
				mav.addObject("msg", isSuccuss ? "success" : "failed");
			} else {
				DwmisMisDptmnt newDwmisMisDptmnt = dwmisMisDptmntService
						.queryDwmisMisDptmntBydepId(dwmisMisDptmnt.getDepId());
				BeanUtils.copyProperties(dwmisMisDptmnt, newDwmisMisDptmnt);
				boolean isSuccuss = dwmisMisDptmntService
						.updateDwmisMisDptmnt(newDwmisMisDptmnt);
				mav.addObject("msg", isSuccuss ? "success" : "failed");
			}
		}
		mav.setViewName(SUCCESS_ACTION);
		return mav;
	}

	/**
	 * 删除部门信息
	 * 
	 * @param depId
	 * @param out
	 */
	@RequestMapping(value = "/delete{depId}")
	public void deleteDwmisMisDptmnt(@PathVariable String depId,
			PrintWriter out, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		try {
			dwmisMisDptmntService.deleteDwmisMisDptmnt(depId);
			out.write("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("删除部门信息失败：" + e.getMessage(), e);
			out.write("failed");
		}
		out.close();
	}

	/**
	 * 批量删除部门信息
	 * 
	 * @param depIds
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteList")
	public void batchDeleteDwmisMisDptmnt(HttpServletRequest request,
			HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String[] depIds = null;
		if (ids != null && ids.length() > 0) {
			depIds = ids.split(",");
		}
		if (depIds != null && depIds.length > 0)
			try {
				dwmisMisDptmntService.deleteDwmisMisDptmntByIds(Arrays
						.asList(depIds));
				this.insertLog(request, "批量删除部门信息");
				this.sendMsgToClient(isSuccess, response);
			} catch (Exception e) {
				this.logger.error("批量删除部门信息失败：" + e.getMessage(), e);
				this.sendMsgToClient(isFailed, response);
			}

	}

}
