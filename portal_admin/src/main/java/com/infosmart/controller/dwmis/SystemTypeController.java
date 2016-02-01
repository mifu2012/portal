package com.infosmart.controller.dwmis;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.dwmis.DwmisSystemGroup;
import com.infosmart.po.dwmis.DwmisSystemParamType;
import com.infosmart.service.dwmis.DwmisSystemParamTypeService;
import com.infosmart.util.Tools;

@Controller
@RequestMapping(value = "/systemType")
public class SystemTypeController extends BaseController {
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	DwmisSystemParamTypeService dwmisSystemParamTypeService;

	@RequestMapping
	public ModelAndView ModelAndView(HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		List<DwmisSystemParamType> systemParamGroupsList = dwmisSystemParamTypeService
				.listSystemParamByGroupId(0);
		this.logger.info("----->:" + systemParamGroupsList.size());
		String groupId = request.getParameter("groupId") == null ? "0"
				: request.getParameter("groupId");
		// if("".equals(groupId)||null==groupId){
		// groupId = request.getSession().getAttribute("groupId") == null ?
		// "all"
		// : request.getSession().getAttribute("groupId").toString();
		// }

		// String groupName=request.getSession().getAttribute("groupId") == null
		// ? "选择全部"
		// : request.getSession().getAttribute("groupId").toString();
		if (systemParamGroupsList.size() > 0) {
			map.put("systemParamGroups", systemParamGroupsList);
			List<Object> dwmisSystemParamTypeList = new ArrayList<Object>();
			if (groupId.equals("0")) {
				for (int i = 0; i < systemParamGroupsList.size(); i++) {
					List<DwmisSystemParamType> dwmisSystemParamType = dwmisSystemParamTypeService
							.listSystemParamByGroupId(systemParamGroupsList
									.get(i).getTypeId());
					DwmisSystemGroup dwmisSystemGroup = new DwmisSystemGroup();
					dwmisSystemGroup.setGroupId(systemParamGroupsList.get(i)
							.getTypeId());
					dwmisSystemGroup.setGroupName(systemParamGroupsList.get(i)
							.getTypeName());
					dwmisSystemGroup
							.setDwmisSystemParamTypeList(dwmisSystemParamType);
					dwmisSystemParamTypeList.add(dwmisSystemGroup);
				}
			} else {
				List<DwmisSystemParamType> dwmisSystemParamTypes = dwmisSystemParamTypeService
						.listSystemParamByGroupId(Integer.valueOf(groupId));
				DwmisSystemGroup dwmisSystemGroup = new DwmisSystemGroup();
				// 获得groupName
				String groupName = dwmisSystemParamTypeService
						.getGroupName(Integer.valueOf(groupId));
				dwmisSystemGroup.setGroupId(Integer.valueOf(groupId));
				dwmisSystemGroup.setGroupName(groupName);
				dwmisSystemGroup
						.setDwmisSystemParamTypeList(dwmisSystemParamTypes);
				dwmisSystemParamTypeList.add(dwmisSystemGroup);
			}
			map.put("groupId", groupId);
			// request.getSession().setAttribute("groupId", groupId);
			map.put("dwmisSystemParamTypeList", dwmisSystemParamTypeList);
		}
		return new ModelAndView("/dwmis/dwmisSystemType/dwmisSystemType", map);
	}

	/** 进入新增类型组 */
	@RequestMapping(value = "/addGroup")
	public ModelAndView addGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/dwmis/dwmisSystemType/dwmisSystemTypeGroup_add");
	}

	/** 进入新增类型 */
	@RequestMapping(value = "/add")
	public ModelAndView addType(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<DwmisSystemParamType> systemParamGroupsList = dwmisSystemParamTypeService
				.listSystemParamByGroupId(0);
		return new ModelAndView("/dwmis/dwmisSystemType/dwmisSystemType_add",
				"systemParamGroups", systemParamGroupsList);
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{typeId}")
	public ModelAndView update(@PathVariable int typeId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DwmisSystemParamType dwmisSystemParamType = dwmisSystemParamTypeService
				.getSystemParamTypeByTypeId(typeId);
		return new ModelAndView("/dwmis/dwmisSystemType/dwmisSystemTypeInfo",
				"dwmisSystemParamType", dwmisSystemParamType);
	}

	/** 进入更新 */
	@RequestMapping(value = "/editGroup{typeId}")
	public ModelAndView updateGroup(@PathVariable int typeId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DwmisSystemParamType dwmisSystemParamType = dwmisSystemParamTypeService
				.getSystemParamTypeByTypeId(typeId);
		return new ModelAndView("/dwmis/dwmisSystemType/dwmisSystemTypeGroup_info",
				"dwmisSystemParamType", dwmisSystemParamType);
	}

	/** 保存 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveSystemTypeGroup(HttpServletRequest request,
			HttpServletResponse response,
			DwmisSystemParamType dwmisSystemParamType) {
		if(dwmisSystemParamType == null){
			this.logger.warn("保存时传递的DwmisSystemParamType对象为null");
		}
		ModelAndView mv = new ModelAndView();
		if(dwmisSystemParamType != null){
			if (Tools.isEmpty(String.valueOf(dwmisSystemParamType.getGroupId()))
					|| 0 == dwmisSystemParamType.getGroupId()) {

				if (dwmisSystemParamTypeService
						.getSystemParamTypeByTypeId(dwmisSystemParamType
								.getTypeId()) != null) {
					mv.addObject("msg", "failed");
				} else {
					dwmisSystemParamTypeService
							.saveSystemparamTypeGroup(dwmisSystemParamType);
					mv.addObject("msg", "success");
				}
			} else {
				DwmisSystemParamType aDwmisSystemParamType = dwmisSystemParamTypeService
						.getSystemParamTypeByTypeId(dwmisSystemParamType
								.getTypeId());

				BeanUtils.copyProperties(dwmisSystemParamType,
						aDwmisSystemParamType);
				dwmisSystemParamTypeService
						.updateSystemParamType(aDwmisSystemParamType);
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 系统类型新增保存 */
	@RequestMapping(value = "/saveType", method = RequestMethod.POST)
	public ModelAndView saveSystemType(HttpServletRequest request,
			HttpServletResponse response,
			DwmisSystemParamType dwmisSystemParamType) {
		if(dwmisSystemParamType == null){
			this.logger.warn("系统类型新增保存时传递的DwmisSystemParamType对象为null");
		}
		ModelAndView mv = new ModelAndView();
		if(dwmisSystemParamType != null){
			if (!Tools.isEmpty(String.valueOf(dwmisSystemParamType.getGroupId()))) {
				dwmisSystemParamTypeService
						.saveSystemparamTypeGroup(dwmisSystemParamType);
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 系统类型组修改保存 */
	@RequestMapping(value = "/saveUpadateGroup", method = RequestMethod.POST)
	public ModelAndView saveUpadateGroup(HttpServletRequest request,
			HttpServletResponse response,
			DwmisSystemParamType dwmisSystemParamType) {
		if(dwmisSystemParamType == null){
			this.logger.warn("系统类型组修改保存时传递的DwmisSystemParamType对象为null");
		}
		ModelAndView mv = new ModelAndView();
		if(dwmisSystemParamType != null){
			if (!Tools.isEmpty(String.valueOf(dwmisSystemParamType.getTypeId()))) {
				DwmisSystemParamType aDwmisSystemParamType = dwmisSystemParamTypeService
						.getSystemParamTypeByTypeId(dwmisSystemParamType
								.getTypeId());
				BeanUtils.copyProperties(dwmisSystemParamType,
						aDwmisSystemParamType);
				dwmisSystemParamTypeService
						.updateSystemParamType(aDwmisSystemParamType);
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{typeId}")
	public void delete(@PathVariable int typeId, PrintWriter out) {
		dwmisSystemParamTypeService.deleteSystemParamType(typeId);
		out.write("success");
		out.close();
	}

	/** 删除 类型组 */
	@RequestMapping(value = "/deleteGroup{groupId}")
	public void deleteGroup(@PathVariable int groupId, PrintWriter out) {
		dwmisSystemParamTypeService.delSystemTypeGroup(groupId);
		dwmisSystemParamTypeService.delSystemTypeGroupNode(groupId);
		out.write("success");
		out.close();
	}

}
