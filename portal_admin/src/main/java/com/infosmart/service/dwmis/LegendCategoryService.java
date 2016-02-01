package com.infosmart.service.dwmis;

import java.util.List;

import com.infosmart.po.dwmis.DwmisLegendCategory;

public interface LegendCategoryService {

	public List<DwmisLegendCategory> list(
			DwmisLegendCategory dwmisLegendCategory);

	public void add(DwmisLegendCategory dwmisLegendCategory);

	public void edit(DwmisLegendCategory dwmisLegendCategory);

	public void del(String categoryId);
	
	public DwmisLegendCategory getById(String categoryId);
	
	/**
	 * 查询所有图例分类
	 * @return
	 */
	public  List<DwmisLegendCategory> getAllCategory();
}
