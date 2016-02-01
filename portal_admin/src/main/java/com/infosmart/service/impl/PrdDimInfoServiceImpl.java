package com.infosmart.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.infosmart.po.PrdDimInfo;
import com.infosmart.service.PrdDimInfoService;

@Service
public class PrdDimInfoServiceImpl extends BaseServiceImpl implements
		PrdDimInfoService {

	@Override
	/**
	 * 查询指定产品id产品健康度信息
	 */
	public PrdDimInfo qryPrdDimByProductId(String productId) {
		PrdDimInfo dwpasCPrdDimDO = new PrdDimInfo();
		if (StringUtils.isBlank(productId)) {
			logger.warn("查询指定产品id产品健康度信息时参数产品id为null");
		} else {
			try {
				dwpasCPrdDimDO = myBatisDao.get(
						"PrdDimInfoMapper.PrdDimInfoQueryByPrdId", productId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("查询指定产品id产品健康度信息失败", e);
			}
		}
		return dwpasCPrdDimDO;
	}

	@Override
	/**
	 * 保存产品健康度信息
	 */
	public boolean savePrdDim(PrdDimInfo dwpasCPrdDimDO) {
		boolean flag = false;
		if (dwpasCPrdDimDO == null) {
			logger.warn("保存产品健康度信息时参数dwpasCPrdDimDO为null");
		} else {
			final PrdDimInfo finDwpasCPrdDimDO = dwpasCPrdDimDO;
			try {
				// 产品健康度信息删除
				// myBatisDao.delete("PrdDimInfoMapper.PrdDimInfoDelete",
				// finDwpasCPrdDimDO.getProductId());
				// 产品健康度信息新增
				myBatisDao.save("PrdDimInfoMapper.PrdDimInfoInsert",
						finDwpasCPrdDimDO);

				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存产品健康度信息失败", e);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public boolean updatePrddimInfo(PrdDimInfo prddiminfo) {
		if (prddiminfo == null) {
			this.logger.warn("更新健康度信息失败：参数prddiminfo为空");
			return false;
		} else {
			try {
				myBatisDao.update("PrdDimInfoMapper.updatePrddiminfo",
						prddiminfo);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("productId=" + prddiminfo.getProductId()
						+ "jiankangdu更新失败", e);
				return false;
			}

		}
	}
}
