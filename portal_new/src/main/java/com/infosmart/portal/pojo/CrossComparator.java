/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */
package com.infosmart.portal.pojo;

import java.util.Comparator;



/**
 * 产品排行
 * @author yufei.sun
 * @version $Id: CrossComparator.java, v 0.1 2011-10-31 下午04:55:54 yufei.sun Exp $
 */
public class CrossComparator implements Comparator<CrossUser> {

    /** 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(CrossUser arg0, CrossUser arg1) {
        if(arg0.getCrossUserCnt()<arg1.getCrossUserCnt()){
            return 1;
        }
        else if(arg0.getCrossUserCnt() == arg1.getCrossUserCnt()){
            return 0;
        }
        return -1;
    }

}
