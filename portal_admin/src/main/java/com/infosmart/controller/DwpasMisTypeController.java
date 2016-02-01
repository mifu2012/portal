package com.infosmart.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.MisTypeInfo;
import com.infosmart.service.MisTypeInfoService;

@Controller
@RequestMapping("/mistype")
public class DwpasMisTypeController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private MisTypeInfoService mistypeService;

	@RequestMapping("/helloWorld")
	public ModelAndView helloWorld(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/mistype/welcome");
		mav.addObject("message", "欢迎访问springMVC的demo!");
		return mav;
	}

	/** 列表 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, MisTypeInfo mistype) {
		if (mistype == null) {
			this.logger.warn("获取列表时传递的MisTypeInfo对象为null");
		}
		List<MisTypeInfo> mistypes = mistypeService.getMisTypePages(mistype);
		List<MisTypeInfo> mistypes1 = mistypeService.getMisTypeInfos();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/mistype/mistype");
		mav.addObject("mistypes", mistypes);
		mav.addObject("mistypes1", mistypes1);
		mav.addObject("mistype", mistype);
		this.insertLog(request, "查询系统类型列表");
		return mav;
	}

	/** 显示 */
	@RequestMapping(value = "/{id}")
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (id == null) {
			this.logger.warn("获得MisTypeInfo时传递的id为null");
		}
		MisTypeInfo mistype = mistypeService.getMisTypeInfo(id);
		return new ModelAndView("admin/mistype/show", "mistype", mistype);
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<MisTypeInfo> mistypes = mistypeService.getMisTypeInfos();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/mistype/mistype_info");
		mav.addObject("mistypes", mistypes);
		return mav;
	}

	/** 进入新增系统类型组 */
	@RequestMapping(value = "/addgroup")
	public ModelAndView addgroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("admin/mistype/misgroup_info");
	}

	/** 进入保存新增系统类型组 */
	@RequestMapping(value = "/savegroup")
	public ModelAndView savegroup(HttpServletRequest request,
			HttpServletResponse response, MisTypeInfo mistype) throws Exception {
		String msg = "";
		ModelAndView mav = new ModelAndView();
		mav.setViewName(SUCCESS_ACTION);
		if (mistype == null) {
			this.logger.warn("保存系统类型组时传递的MisTypeInfo为null");
			msg = "failed";

		} else {
			MisTypeInfo checkMisType = mistypeService.getMisTypeInfo(mistype
					.getTypeId());
			if (checkMisType == null) {
				try {
					mistypeService.saveGroup(mistype);
					this.insertLog(request, "新增系统类型组");
				} catch (Exception e) {

					msg = "failed";
					e.printStackTrace();
					this.logger.error("新增系统类型组失败：" + e.getMessage(), e);
				}

			} else {
				msg = "该系统类型组Id已存在！";
			}
		}

		mav.addObject("mistype", mistype);
		mav.addObject("msg", msg);
		return mav;
	}

	/** 进入修改系统类型组 */
	@RequestMapping(value = "/editgroup{id}")
	public ModelAndView editgroup(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isBlank(id)) {
			this.logger.warn("修改系统类型组时传递的id为null");
		}
		MisTypeInfo mistype = mistypeService.getMisTypeInfo(id);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/mistype/misgroup_edit");
		mav.addObject("mistype", mistype);
		return mav;
	}

	/** 进入保存修改系统类型组 */
	@RequestMapping(value = "/saveeditgroup")
	public ModelAndView saveeditgroup(HttpServletRequest request,
			HttpServletResponse response, MisTypeInfo mistype) throws Exception {
		String msg = "";
		if (mistype == null) {
			this.logger.warn("保存修改系统类型组时传递的MisTypeInfo对象为null");
			msg = "failed";
		} else {
			try {
				mistypeService.updateGroup(mistype);
				msg = "success";
				this.insertLog(request, "修改系统类型组");
			} catch (Exception e) {

				e.printStackTrace();
				this.logger.error("修改系统类型组失败：" + e.getMessage(), e);
				msg = "failed";
			}

		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName(SUCCESS_ACTION);
		mav.addObject("msg", msg);
		return mav;
	}

	/** 进入更新系统类型 */
	@RequestMapping(value = "/edit{id}")
	public ModelAndView edit(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isBlank(id)) {
			this.logger.warn("进入更新时候传递的id为Null");
		}
		MisTypeInfo mistype = mistypeService.getMisTypeInfo(id);
		List<MisTypeInfo> mistypes = mistypeService.getMisTypeInfos();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/mistype/mistype_edit");
		mav.addObject("mistypes", mistypes);
		mav.addObject("mistype", mistype);
		return mav;
	}

	/** 保存新增系统类型 */
	@RequestMapping(value = "/saveAddType", method = RequestMethod.POST)
	public ModelAndView saveExample(HttpServletRequest request,
			HttpServletResponse response, MisTypeInfo mistype) {
		String msg = "";
		ModelAndView mv = new ModelAndView();
		if (mistype == null) {
			this.logger.warn("保存时传递的misType为null");
			msg = "failed";
		} else {
			MisTypeInfo mistype1 = mistypeService.getMisTypeInfo(mistype
					.getTypeId());
			if (mistype1 == null) {
				try {
					
					mistypeService.save(mistype);
					msg = "success";
					this.insertLog(request, "新增系统类型");
				} catch (Exception e) {

					e.printStackTrace();
					this.logger.error("新增系统类型失败："+e.getMessage(), e);
					msg = "failed";
				}

			} else {
				msg = "该系统类型Id已存在";
			}
		}
		mv.addObject("msg", msg);
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 保存修改系统类型 */
	@RequestMapping(value = "/saveUpdateType", method = RequestMethod.POST)
	public ModelAndView saveUpdateType(HttpServletRequest request,
			HttpServletResponse response, MisTypeInfo mistype) {
		String msg = "";
		if (mistype == null) {
			this.logger.warn("保存时传递的misType为null");
			msg = "failed";
		} else {
			try {
				mistypeService.update(mistype);
				msg = "success";
				this.insertLog(request, "修改系统类型");
			} catch (Exception e) {

				msg = "failed";
			}

		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", msg);
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{id}")
	public void delete(@PathVariable Long id, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		if (id == null) {
			this.logger.warn("删除时传递的id为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		try {
			mistypeService.delete(id);
			this.sendMsgToClient(isSuccess, response);
			this.insertLog(request, "删除系统类型");
		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("删除系统类型"+e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}

	}

	/** 删除 */
	@RequestMapping(value = "/deleteGroup{id}")
	public void deleteGroup(@PathVariable Long id, PrintWriter out,
			HttpServletRequest request, HttpServletResponse response) {
		if (id == null) {
			this.logger.warn("删除组时传递的id为null");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		try {
			mistypeService.deleteGroupAndTypeByGroupId(id);
			this.insertLog(request, "删除系统类型组以及下面的类型");
			this.sendMsgToClient(isSuccess, response);

		} catch (Exception e) {

			e.printStackTrace();
			this.logger.error("删除系统类型组以及下面的类型失败："+e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}

	}

	@RequestMapping(value = "/checkTypeName")
	public void checkTypeName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String typeName = request.getParameter("typeName");
		List<MisTypeInfo> misTypes = new ArrayList<MisTypeInfo>();
		typeName = new String(typeName.getBytes("ISO-8859-1"), "UTF-8");
		if (typeName != null && typeName.length() > 0) {
			misTypes = mistypeService.getMisTypeByTypeName(typeName);
		}
		if (misTypes.size() < 1) {
			this.sendMsgToClient(isSuccess, response);
		} else {
			this.sendMsgToClient(isFailed, response);
		}
	}

}
