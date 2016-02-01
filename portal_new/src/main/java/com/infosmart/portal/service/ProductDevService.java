package com.infosmart.portal.service;

import java.util.List;
import java.util.Map;

import com.infosmart.portal.pojo.DwpasStKpiData;

public interface ProductDevService {
    /**
     * 根据kpiCodeList查询queryDate当日KpiData数据
     * @param codeList      kpiCode列表
     * @param queryDate     查询日期
     * @return key：                     指标名称，value：kpi数据 的Map
     */
    Map<String, DwpasStKpiData> calculateKpiDataToDay(List<String> codeList, String queryDate);
    
    /**
     * 根据kpiCodeList查询queryDate当月KpiData数据
     * @param codeList      kpiCode列表
     * @param queryDate     查询日期
     * @return key：                     指标名称，value：kpi数据 的Map
     */
    Map<String, DwpasStKpiData> calculateKpiDataToMonth(List<String> codeList, String queryDate);
    
    /**
     * 根据产品ID查询产品名称
     * 
     * @param prodID
     * @return
     */
    public String queryProdNameByID(String prodID);
}
