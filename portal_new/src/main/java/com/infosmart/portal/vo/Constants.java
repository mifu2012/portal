package com.infosmart.portal.vo;



import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static final String  CHART_DATA_SEPERATOR   = ",";

    public static final String  FUNNEL_SEPARATOR_CHARS = ";";

    public static BigDecimal    ZERO                   = new BigDecimal("0.00");

    public static DateFormat    format_day             = new SimpleDateFormat("yyyyMMdd");
    public static DateFormat    format_month           = new SimpleDateFormat("yyyyMM");

    public static boolean       isMakeData             = true;

    public static BigDecimal    ONE_HANDREN            = new BigDecimal("100.00");

    public static BigDecimal    ONE_THOUSAND           = new BigDecimal("1000.00");

    public static DateFormat    format_day_display     = new SimpleDateFormat("yyyy��MM��dd��");
    public static DateFormat    format_month_dispaly   = new SimpleDateFormat("yyyy��MM��");

    public static DecimalFormat amtFmt                 = new DecimalFormat("##,###,###,###,##0.00");

    //��ҳ��״ͼ��ʾ�õ�-��������Χ��
    public static final String  Help_Rate_Normal       = "#80A935";

    //��ҳ��״ͼ��ʾ�õ�-  Ԥ��ֵ<������<����ֵ
    public static final String  Help_Rate_Pre_Alert    = "#FFC200";

    //��ҳ��״ͼ��ʾ�õ�-    ������>����ֵ
    public static final String  Help_Rate_Alert        = "#D53E28";
}
