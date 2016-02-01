package com.infosmart.service.dwmis.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infosmart.po.dwmis.DwmisLegendCategory;
import com.infosmart.service.dwmis.LegendCategoryService;
import com.infosmart.service.impl.BaseServiceImpl;

@Service
public class LegendCategoryServiceImpl extends BaseServiceImpl implements
		LegendCategoryService {

	/**
	 * 图例分类列表
	 */
	@Override
	public List<DwmisLegendCategory> list(
			DwmisLegendCategory dwmisLegendCategory) {
		return this.myBatisDao
				.getList(
						"com.infosmart.DwmisLegendCategoryMapper.listPageLegendCategory",
						dwmisLegendCategory);
	}

	/**
	 * 新增图例分类
	 */
	@Override
	public void add(DwmisLegendCategory dwmisLegendCategory) {
		if (dwmisLegendCategory != null) {
			dwmisLegendCategory.setCategoryId(dwmisLegendCategory
					.getCategoryId().replace("-", ""));
		}
		this.myBatisDao.save(
				"com.infosmart.DwmisLegendCategoryMapper.addLegendCategory",
				dwmisLegendCategory);
	}

	/**
	 * 修改图例分类
	 */
	@Override
	public void edit(DwmisLegendCategory dwmisLegendCategory) {
		this.myBatisDao.update(
				"com.infosmart.DwmisLegendCategoryMapper.updateLegendCategory",
				dwmisLegendCategory);
	}

	/**
	 * 删除图例分类
	 */
	@Override
	public void del(String categoryId) {
		this.myBatisDao.delete(
				"com.infosmart.DwmisLegendCategoryMapper.delLegendCategory",
				categoryId);
	}

	/**
	 * 根据ID查询图例分类
	 */
	@Override
	public DwmisLegendCategory getById(String categoryId) {
		return this.myBatisDao
				.get("com.infosmart.DwmisLegendCategoryMapper.getLegendCategoryById",
						categoryId);
	}

	/**
	 * 查询所有图例分类
	 */
	@Override
	public List<DwmisLegendCategory> getAllCategory() {
		return this.myBatisDao
				.getList("com.infosmart.DwmisLegendCategoryMapper.getAllCategory");
	}

}
