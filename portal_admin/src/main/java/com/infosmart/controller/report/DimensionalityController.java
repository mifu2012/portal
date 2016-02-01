package com.infosmart.controller.report;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.controller.BaseController;
import com.infosmart.po.Dimension;
import com.infosmart.po.DimensionDetail;
import com.infosmart.po.DimensionDetailSec;
import com.infosmart.po.report.ReportColumnConfig;
import com.infosmart.service.DimensionalityService;
import com.infosmart.util.StringUtils;

@Controller
@RequestMapping(value = "/Dimensionality")
public class DimensionalityController extends BaseController {
	@Autowired
	private DimensionalityService dimensionalityService;

	/**
	 * 显示维度主表信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView showDimensionMessage(HttpServletRequest request,
			HttpServletResponse response) {
		List<Dimension> DimensionList = dimensionalityService
				.getDimensionList();
		ModelAndView mav = new ModelAndView();
		mav.addObject("DimensionList", DimensionList);
		mav.setViewName("/dimension/dimensionManage");
		return mav;
	}

	/**
	 * 显示维度主表信息
	 * 
	 * @param request
	 * @param response
	 * @param dimensionDetail
	 * @return
	 */
	@RequestMapping(value = "/selectDimension")
	public ModelAndView showDimensionMessageById(HttpServletRequest request,
			HttpServletResponse response, DimensionDetail dimensionDetail) {
		if (dimensionDetail == null) {
			this.logger.warn("DimensionDetail对象为null");
		}
		Integer id = null;
		if ("".equals(request.getParameter("id"))
				|| request.getParameter("id") == null) {
			if (dimensionDetail != null) {
				id = dimensionDetail.getDimensionId();
			}
		} else {
			id = Integer.parseInt(request.getParameter("id"));
		}
		List<Dimension> DimensionList = dimensionalityService
				.getDimensionList();
		List<DimensionDetail> DimensionDetailList = dimensionalityService
				.getDimensionDetailList(id);
		Dimension dimensionById = dimensionalityService.getDimensionById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("DimensionList", DimensionList);
		mav.addObject("dimension", dimensionById);
		mav.addObject("DimensionDetailList", DimensionDetailList);
		mav.setViewName("/dimension/dimensionManage");
		return mav;
	}

	@RequestMapping(value = "/selectDimensionByAjax")
	public void selectDimensionByAjax(HttpServletRequest request,
			HttpServletResponse response, DimensionDetail dimensionDetail) {
		if (dimensionDetail == null) {
			this.logger.warn("DimensionDetail对象为null");
		}
		Integer id = null;
		if ("".equals(request.getParameter("id"))
				|| request.getParameter("id") == null) {
			if (dimensionDetail != null) {
				id = dimensionDetail.getDimensionId();
			}
		} else {
			id = Integer.parseInt(request.getParameter("id"));
		}

		List<DimensionDetail> DimensionDetailList = dimensionalityService
				.getDimensionDetailList(id);
		JSONArray jsonArray = null;
		jsonArray = JSONArray.fromObject(DimensionDetailList);
		this.sendMsgToClient(jsonArray.toString(), response);
	}

	/**
	 * 搜索维度主表
	 * 
	 * @param request
	 * @param response
	 * @param out
	 */
	@RequestMapping(value = "/showDimensionByCode")
	public void showDimensionMessageByCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyCode = request.getParameter("keyCode");
		if (null != keyCode && "" != keyCode) {
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		// 与该部门不管关联的指标
		List<Dimension> dimensionByKeyCodeList = null;
		dimensionByKeyCodeList = dimensionalityService
				.getDimensionByCodeOrName(keyCode);
		List<Dimension> dimensionRelList = new ArrayList<Dimension>();
		Dimension dimension = null;
		if (dimensionByKeyCodeList != null && dimensionByKeyCodeList.size() > 0) {
			for (Dimension dimension1 : dimensionByKeyCodeList) {
				dimension = new Dimension();
				dimension.setDimensionCode(dimension1.getDimensionCode());
				dimension.setDimensionName(dimension1.getDimensionName());
				dimensionRelList.add(dimension);
			}
		}
		// List对象转json
		JSONArray jSONArray = null;

		jSONArray = JSONArray.fromObject(dimensionByKeyCodeList);
		PrintWriter out;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.print(jSONArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 搜索维度从表
	 * 
	 * @param request
	 * @param response
	 * @param out
	 * @param dimensionDetail
	 */
	@RequestMapping(value = "/showDimensionDetailByCode")
	public void showDimensionMessageDetailByKey(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String keyCode = request.getParameter("keyCode");
		String dimensionId = request.getParameter("dimensionId");
		if (null != keyCode && "" != keyCode) {
			keyCode = new String(keyCode.getBytes("ISO-8859-1"), "UTF-8");
		}
		// 与该部门不管关联的指标
		List<DimensionDetail> dimensionDetailByKeyList = new ArrayList<DimensionDetail>();
		dimensionDetailByKeyList = dimensionalityService
				.getDimensionDetaiByIdAndKeyCode(dimensionId, keyCode);
		List<DimensionDetail> list = new ArrayList<DimensionDetail>();
		DimensionDetail dimensionDetail = new DimensionDetail();
		if (dimensionDetailByKeyList != null
				&& dimensionDetailByKeyList.size() > 0) {
			for (DimensionDetail dimensionDetail1 : dimensionDetailByKeyList) {
				dimensionDetail = new DimensionDetail();
				dimensionDetail.setPrimaryKeyId(dimensionDetail1
						.getPrimaryKeyId());
				dimensionDetail.setDimensionId(dimensionDetail1
						.getDimensionId());
				dimensionDetail.setKey(dimensionDetail1.getKey());
				dimensionDetail.setValue(dimensionDetail1.getValue());
				list.add(dimensionDetail);
			}
		}
		// List对象转json
		JSONArray jSONArray = null;

		jSONArray = JSONArray.fromObject(dimensionDetailByKeyList);
		PrintWriter out;
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			out.print(jSONArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据id显示维度从表信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/showDimensionDetailMessageById")
	public ModelAndView showDimensionDetailMessageById(
			HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("primaryKeyId"));
		Integer dimensionId = Integer.parseInt(request
				.getParameter("dimensionId"));
		List<Dimension> DimensionList = dimensionalityService
				.getDimensionList();
		List<DimensionDetail> DimensionDetailList = dimensionalityService
				.getDimensionDetailList(dimensionId);
		DimensionDetail dimensionDetail = dimensionalityService
				.getDimensionDetaiById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("DimensionDetailList", DimensionDetailList);
		mav.addObject("DimensionList", DimensionList);
		mav.addObject("dimensionDetail", dimensionDetail);
		mav.addObject("dimensionDetaiState", "select");
		mav.setViewName("/dimension/dimensionManage");
		return mav;

	}

	/**
	 * 添加维度主表跳转
	 * 
	 * @param Dimension
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDimension")
	public ModelAndView addDimension(Dimension Dimension) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("dimensionState", "add");
		mav.setViewName("/dimension/dimensionInfo");
		return mav;
	}

	/**
	 * 修改维度主表跳转
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDimension{id}")
	public ModelAndView editDimension(@PathVariable Integer id)
			throws Exception {
		if (id == null) {
			this.logger.warn("Dimension的id为null");
		}
		Dimension dimensionById = dimensionalityService.getDimensionById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dimension", dimensionById);
		mav.addObject("dimensionState", "select");
		mav.setViewName("/dimension/dimensionInfo");
		return mav;
	}

	/**
	 * 保存维度主表
	 * 
	 * @param request
	 * @param response
	 * @param dimension
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDimension", method = RequestMethod.POST)
	public ModelAndView saveDimension(HttpServletRequest request,
			HttpServletResponse response, Dimension dimension) throws Exception {
		if (dimension == null) {
			this.logger.warn("Dimension为null");
		}
		ModelAndView mav = new ModelAndView();
		if (dimension != null) {
			if (dimension.getId() == null) {
				if (dimensionalityService.getDimensionById(dimension.getId()) != null) {
					mav.addObject("msg", "failed");
				} else {
					try {
						dimensionalityService.insertDimension(dimension);
						this.insertLog(request, "新增维度主表");
						mav.addObject("msg", "success");
					} catch (Exception e) {

						mav.addObject("msg", "failed");
					}
				}
			} else {
				Dimension dimensionPo = dimensionalityService
						.getDimensionById(dimension.getId());
				BeanUtils.copyProperties(dimension, dimensionPo);
				try {
					dimensionalityService.updateDimension(dimension);
					this.insertLog(request, "编辑维度主表");
					mav.addObject("msg", "success");
				} catch (Exception e) {

					mav.addObject("msg", "failed");
				}

			}
		}
		return new ModelAndView("/common/save_result");
	}

	/**
	 * 添加维度从表跳转
	 * 
	 * @param id
	 * @param dimensionDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDimensionDetail{id}")
	public ModelAndView addDimensionDetail(@PathVariable Integer id,
			DimensionDetail dimensionDetail) throws Exception {
		if (id == null) {
			this.logger.warn("添加维度从表时传递的id为null");
		}
		ModelAndView mav = new ModelAndView();
		Dimension dimensionById = dimensionalityService.getDimensionById(id);
		mav.addObject("dimension", dimensionById);
		mav.addObject("dimensionDetaiState", "add");
		mav.setViewName("/dimension/dimensionDetailInfo");
		return mav;
	}

	// 跳转到添加二级维度页面
	@RequestMapping(value = "/addDimensionDetailSec")
	public ModelAndView addDimensionDetailSec(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		if (!StringUtils.notNullAndSpace(parentId)) {
			this.logger.warn("跳转到添加二级维度页面出错：parentId为空");
			return null;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("dimensionDetaiState", "add");
		mav.addObject("parentId", parentId);
		mav.setViewName("/dimension/dimensionDetailInfoSec");
		return mav;
	}

	/**
	 * 批量添加维度主表跳转
	 * 
	 * @param Dimension
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchAddDimension{id}")
	public ModelAndView batchAddDimension(@PathVariable Integer id)
			throws Exception {
		if (id == null) {
			this.logger.warn("添加维度从表时传递的id为null");
		}
		ModelAndView mav = new ModelAndView();
		Dimension dimensionById = dimensionalityService.getDimensionById(id);
		mav.addObject("dimension", dimensionById);
		mav.addObject("dimensionDetaiState", "add");
		mav.setViewName("/dimension/batchAdddimensionDetail");
		return mav;
	}

	// 批量添加二级维度跳转页面
	@RequestMapping(value = "/batchAddDimensionSec")
	public ModelAndView batchAddDimensionSec(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String parentId = request.getParameter("parentId");
		if (!StringUtils.notNullAndSpace(parentId)) {
			this.logger.warn("批量添加二级维度出错：parentId为空");
			return null;
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("parentId", parentId);
		mav.setViewName("/dimension/batchAdddimensionDetailSec");
		return mav;
	}

	/**
	 * 修改维度从表跳转
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDimensionDetail{id}")
	public ModelAndView editDimensionDetail(@PathVariable Integer id)
			throws Exception {
		if (id == null) {
			this.logger.warn("修改从表跳转时传的id为null");
		}
		DimensionDetail dimensionDetailById = dimensionalityService
				.getDimensionDetaiById(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dimensionDetail", dimensionDetailById);
		mav.addObject("dimensionDetaiState", "select");
		mav.setViewName("/dimension/dimensionDetailInfo");
		return mav;
	}

	/**
	 * 修改二级维度值
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editDimensionDetailSec")
	public ModelAndView editDimensionDetailSec(HttpServletRequest request,
			HttpServletResponse reponse) {
		String primary_id = request.getParameter("primary_id");
		if (!StringUtils.notNullAndSpace(primary_id)) {
			this.logger.warn("修改二级维度出错：primary_id为空");
			return null;
		}
		DimensionDetailSec dimensionDetailSec = dimensionalityService
				.getDimensionDetailSecById(Integer.parseInt(primary_id));

		ModelAndView mav = new ModelAndView();
		mav.addObject("dimensionDetailSec", dimensionDetailSec);
		mav.addObject("dimensionDetaiState", "edit");
		mav.setViewName("/dimension/dimensionDetailInfoSec");
		return mav;
	}

	/**
	 * 保存维度从表
	 * 
	 * @param request
	 * @param response
	 * @param dimensionDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDimensionDetail", method = RequestMethod.POST)
	public ModelAndView saveDimensionDetail(HttpServletRequest request,
			HttpServletResponse response, DimensionDetail dimensionDetail)
			throws Exception {
		if (dimensionDetail == null) {
			this.logger.warn("保存维度从表时传递的DimensionDetail对象为null");
		}
		ModelAndView mav = new ModelAndView();
		if (dimensionDetail != null) {
			if (dimensionDetail.getPrimaryKeyId() == null) {
				if (dimensionalityService.getDimensionDetaiById(dimensionDetail
						.getPrimaryKeyId()) != null) {
					mav.addObject("msg", "failed");
				} else {
					try {
						dimensionalityService
								.insertDimensionDetai(dimensionDetail);
						this.insertLog(request, "新增维度从表");
						mav.addObject("msg", "success");
					} catch (Exception e) {

						mav.addObject("msg", "failed");
					}

				}
			} else {
				DimensionDetail dimensionD = dimensionalityService
						.getDimensionDetaiById(dimensionDetail
								.getPrimaryKeyId());
				BeanUtils.copyProperties(dimensionDetail, dimensionD);
				try {
					dimensionalityService.updateDimensionDetai(dimensionD);
					this.insertLog(request, "编辑维度从表");
					mav.addObject("msg", "success");
				} catch (Exception e) {

					mav.addObject("msg", "failed");
				}

			}
		}
		return new ModelAndView("/common/save_result");
	}

	/**
	 * 添加、修改二级维度
	 * 
	 * @param request
	 * @param response
	 * @param dimensionDetail
	 * @return
	 */
	@RequestMapping(value = "/saveDimensionDetailSec", method = RequestMethod.POST)
	public ModelAndView saveDimensionDetailSec(HttpServletRequest request,
			HttpServletResponse response, DimensionDetailSec dimensionDetailSec) {
		ModelAndView mav = new ModelAndView();
		if (dimensionDetailSec.getPrimary_id() != 0) {
			// 修改
			try {
				dimensionalityService
						.updateDimensionDetailSec(dimensionDetailSec);
				mav.addObject("msg", "success");
			} catch (Exception e) {
				e.printStackTrace();
				mav.addObject("msg", "failed");
			}
		} else {
			try {
				dimensionalityService
						.insertDimensionDetailSec(dimensionDetailSec);
			} catch (Exception e) {
				mav.addObject("msg", "failed");
			}
		}
		return new ModelAndView("/common/save_result");
	}

	/*
	 * 批量添加sql语句测试
	 */
	@RequestMapping(value = "/testReportSql")
	public void testReportSql(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("ISO8859-1");
			String querySql = request.getParameter("querySql");
			querySql = new String(querySql.getBytes("ISO-8859-1"), "UTF-8");

			List<ReportColumnConfig> columnConfigList = dimensionalityService
					.listSelfReportColumnConfigBySQL(querySql);
			this.sendJsonMsgToClient(columnConfigList, response);
			this.insertLog(request, "测试SQL语句");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量保存维度信息
	 * 
	 * @param request
	 * @param response
	 * @param dimensionDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchSaveDimensionDetail", method = RequestMethod.POST)
	public ModelAndView batchSaveDimensionDetail(HttpServletRequest request,
			HttpServletResponse response, DimensionDetail dimensionDetail)
			throws Exception {
		this.logger.info("通过执行SQL语句批量插入维度明细信息:"
				+ dimensionDetail.getDimensionId());
		ModelAndView mav = new ModelAndView();
		if (dimensionDetail == null) {
			this.logger.warn("保存维度从表时传递的DimensionDetail对象为null");
			mav.addObject("msg", this.isFailed);
		}
		try {
			this.dimensionalityService
					.insertDimensionDetailBySQL(dimensionDetail);
			mav.addObject("msg", this.isSuccess);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			mav.addObject("msg", this.isFailed);
		}
		return new ModelAndView("/common/save_result");
	}

	/**
	 * 批量保存二级维度数据
	 * 
	 * @param request
	 * @param response
	 * @param dimensionDetail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchSaveDimensionDetailSec", method = RequestMethod.POST)
	public ModelAndView batchSaveDimensionDetailSec(HttpServletRequest request,
			HttpServletResponse response, DimensionDetailSec dimensionDetailSec)
			throws Exception {
		this.logger.info("通过执行SQL语句批量插入维度明细信息:"
				+ dimensionDetailSec.getParent_id());
		ModelAndView mav = new ModelAndView();
		if (dimensionDetailSec == null) {
			this.logger.warn("保存维度从表时传递的DimensionDetail对象为null");
			mav.addObject("msg", this.isFailed);
		}
		try {
			this.dimensionalityService
					.insertDimensionDetailSecBySQL(dimensionDetailSec);
			mav.addObject("msg", this.isSuccess);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			mav.addObject("msg", this.isFailed);
		}
		return new ModelAndView("/common/save_result");
	}

	/**
	 * 删除维度主表
	 * 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteDimension{id}")
	public void deleteDimension(@PathVariable Integer id,
			HttpServletResponse response) throws IOException {
		if (id == null) {
			this.logger.warn("删除维度从表时传递的id为null");
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			dimensionalityService.deleteDimension(id);
			out.write("success");
		} catch (Exception e) {
			this.logger.error("删除维度主表及其从表失败：" + e.getMessage(), e);
			out.write(this.isFailed);
			e.printStackTrace();
		}

		out.close();

	}

	/**
	 * 删除维度从表
	 * 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteDimensionDetail{id}")
	public void deleteDimensionDetail(@PathVariable Integer id,
			HttpServletResponse response) throws IOException {
		if (id == null) {
			this.logger.warn("删除维度从表时传递的id为null");
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			dimensionalityService.deleteDimensionDetai(id);
			out.write("success");
		} catch (Exception e) {
			this.logger.error("删除维度主表失败：" + e.getMessage(), e);
			out.write(this.isFailed);
			e.printStackTrace();
		}
		out.close();

	}

	/**
	 * 删除二级维度数据
	 * 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteDimensionDetailSec")
	public void deleteDimensionDetailSec(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String primary_id = request.getParameter("primary_id");
		if (!StringUtils.notNullAndSpace(primary_id)) {
			this.logger.warn("删除二级维度出错：主键Id为空");
			this.sendMsgToClient("failed", response);
			return;
		}
		try {
			dimensionalityService.deleteDimensionDetaiSec(primary_id);
			this.sendMsgToClient("success", response);
		} catch (Exception e) {
			this.logger.warn("删除二级维度出错：", e);
			this.sendMsgToClient("failed", response);
		}
	}
}
