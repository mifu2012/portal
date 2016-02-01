package com.infosmart.portal.pojo;

import java.util.Comparator;

public class CrossUserActionComparator implements Comparator<UserAction> {
	
	public int compare(UserAction arg0, UserAction arg1) {
        if(arg0.getCrossUserCnt()<arg1.getCrossUserCnt()){
            return 1;
        }
        else if(arg0.getCrossUserCnt() == arg1.getCrossUserCnt()){
            return 0;
        }
        return -1;
    }

}
