package com.infosmart.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.SysParamInfo;
import com.infosmart.service.SysParamService;
import com.infosmart.util.Tools;

@Controller
@RequestMapping("/sysparam")
public class DwpasSysParamController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private SysParamService sysParamService;

	@RequestMapping("/helloWorld")
	public ModelAndView helloWorld(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/sysparam/welcome");
		mav.addObject("message", "欢迎访问springMVC的demo!");
		return mav;
	}

	/** 列表 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, SysParamInfo sysparam) {
		String paramName=sysparam.getParamName();
		String paramValue=sysparam.getParamValue();
		if(paramName!=null && !"".equals(paramName)){
			sysparam.setParamName(paramName.trim());
		}
		if(!"".equals(paramValue)&& paramValue!=null){
			sysparam.setParamValue(paramValue.trim());
		}
		List<SysParamInfo> sysparams = sysParamService
				.getSysParamPages(sysparam);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/sysparam/sysparam");
		mav.addObject("sysparams", sysparams);
		mav.addObject("sysparam", sysparam);
		this.insertLog(request, "查看系统参数列表");
		return mav;
	}

	/** 显示 */
	@RequestMapping(value = "/{paramId}")
	public ModelAndView show(@PathVariable Long paramId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysParamInfo sysparam = (SysParamInfo) sysParamService
				.getSysParam(paramId);
		return new ModelAndView("admin/sysparam/show", "sysparam", sysparam);
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/sysparam/sysparam_add");
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{paramId}")
	public ModelAndView edit(@PathVariable Long paramId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysParamInfo sysparam = sysParamService.getSysParam(paramId);
		return new ModelAndView("admin/sysparam/sysparam_info", "sysparam",
				sysparam);
	}

	/** 保存 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExample(HttpServletRequest request,
			HttpServletResponse response, SysParamInfo sysparam) {
		if(sysparam == null){
			this.logger.warn("保存系统参数时传递的SysParamInfo对象为null");
		}
		ModelAndView mv = new ModelAndView();
		if(sysparam != null){
			if (Tools.isEmpty(sysparam.getParamId())) {
				if (sysParamService.getSysParam(sysparam.getParamId()) != null) {
					mv.addObject("msg", "failed");
				} else {
					try {
						sysParamService.save(sysparam);
						mv.addObject("msg", "success");
					} catch (Exception e) {

						mv.addObject("msg", "failed");
					}
					this.insertLog(request, "新增系统参数");
				}
			} else {
				SysParamInfo sysparam1 = (SysParamInfo) sysParamService
						.getSysParam(sysparam.getParamId());
				BeanUtils.copyProperties(sysparam, sysparam1);
				try {
					sysParamService.update(sysparam1);
					mv.addObject("msg", "success");
				} catch (Exception e) {

					mv.addObject("msg", "failed");
				}
				
				
				this.insertLog(request, "修改系统参数");
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{paramId}")
	public void delete(@PathVariable Long paramId, PrintWriter out,
			HttpServletRequest request ,HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		if(paramId == null){
			this.logger.warn("删除参数时传递的paramId为null");
		}
		try {
			sysParamService.delete(paramId);
			out.write("success");
		} catch (Exception e) {

			out.write("failed");
		}
		out.close();
		this.insertLog(request, "删除系统参数");
	}
	/**
	 * 校验参数名的唯一性
	 */
	@RequestMapping("/alidateSysName")
	public void alidateSysName(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String paramName = req.getParameter("paramName");
		paramName = new String(paramName.getBytes("ISO-8859-1"),"UTF-8");
		SysParamInfo sysparam = sysParamService.getSysParamByParamName(paramName);
		if (sysparam == null) {
			this.sendMsgToClient("0", res);
		} else {
			this.sendMsgToClient("1", res);
		}
	}
}
