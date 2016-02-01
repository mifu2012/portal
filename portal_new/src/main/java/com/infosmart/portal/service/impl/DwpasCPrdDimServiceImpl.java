package com.infosmart.portal.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.DwpasCKpiInfo;
import com.infosmart.portal.pojo.DwpasCPrdDim;
import com.infosmart.portal.pojo.DwpasCSystemMenu;
import com.infosmart.portal.pojo.DwpasMisKpiEventRel;
import com.infosmart.portal.service.DwpasCKpiInfoService;
import com.infosmart.portal.service.DwpasCPrdDimService;
import com.infosmart.portal.service.DwpasCSystemMenuService;
import com.infosmart.portal.util.StringUtils;
import com.infosmart.portal.util.SystemModuleInfo;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 产品健康度六维度配置管理
 * 
 * @author infosmart
 * 
 */
@Service
public class DwpasCPrdDimServiceImpl extends BaseServiceImpl implements
		DwpasCPrdDimService {

	@Autowired
	private DwpasCKpiInfoService dwpasCKpiInfoService;
	
	@Autowired
	private DwpasCSystemMenuService menuServer;
	/**
	 * 得到某产品的六维度配置信息
	 * 
	 * @param productId
	 * @return
	 */
	public DwpasCPrdDim getDwpasCPrdDimByProductId(String productId,String templateId) {
		this.logger.info("根据产品ID查询产品六维度配置信息");
		if (!StringUtils.notNullAndSpace(productId)) {
			this.logger.error("根据产品ID查询产品六维度配置信息,传的参数为空");
			return null;
		}
		DwpasCPrdDim dwpasCPrdDim = this.myBatisDao.get(
				"com.infosmart.mapper.DwpasCPrdDimMapper.queryOneByProductId",
				productId);
		if (dwpasCPrdDim == null)
			return null;
		// 如果六维度指标没有名称,则取指标信息名称
		Object dimName = null;
		Object dimCode = null;
		Object dimUrl =null;
		DwpasCKpiInfo kpiInfo = null;
		DwpasCSystemMenu menuInfo = null;
		try {
			for (int i = 1; i <= 6; i++) {
				dimName = PropertyUtils.getProperty(dwpasCPrdDim, "dim" + i
						+ "Name");
				if (dimName != null
						&& StringUtils.notNullAndSpace(dimName.toString())) {
					continue;
				}
				dimUrl = PropertyUtils.getProperty(dwpasCPrdDim, "dim" + i
						+ "Url");
				if(dimUrl != null){
					menuInfo = menuServer.getMenuInfoByMenuCodeAndTemplateId(dimUrl.toString(), templateId);
					if(menuInfo!=null&&menuInfo.getMenuUrl()!=null&&menuInfo.getMenuUrl().length()>0&&menuInfo.getMenuId()!=null&&menuInfo.getMenuId().length()>0){
						PropertyUtils.setProperty(dwpasCPrdDim, "dim" + i + "Url",
								menuInfo.getMenuUrl()+"&menuId="+menuInfo.getMenuId());
					}else {
						PropertyUtils.setProperty(dwpasCPrdDim, "dim" + i + "Url","");
					}
					
				}else {
					PropertyUtils.setProperty(dwpasCPrdDim, "dim" + i + "Url","");
				}
				// 如果值为空
				dimCode = PropertyUtils.getProperty(dwpasCPrdDim, "dim" + i
						+ "Code");
				kpiInfo = this.dwpasCKpiInfoService
						.getDwpasCKpiInfoByCode(dimCode.toString());
				if (kpiInfo == null)
					continue;
				// 名称
				PropertyUtils.setProperty(dwpasCPrdDim, "dim" + i + "Name",
						kpiInfo.getDispName());
				// 单位
				PropertyUtils.setProperty(dwpasCPrdDim,
						"dim" + i + "ValueUnit", kpiInfo.getUnit());
			}
		} catch (IllegalAccessException e) {
			this.logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			this.logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			this.logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return dwpasCPrdDim;
	}

}
