package com.infosmart.portal.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class MathUtils {
	private static final int DEF_DIV_SCALE = 10;
	
	public static BigDecimal ZERO = new BigDecimal("0.00");
	public static BigDecimal ONE = new BigDecimal("1.00");
	public static BigDecimal ONEHANDRED = new BigDecimal("100.00");

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double v1, double v2) {
		return div(v1, v2, 10);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, 4).doubleValue();
	}

	/**
	 * ��������
	 * 
	 * @param v
	 * @param scale
	 * @return
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 4).doubleValue();
	}

	public static BigDecimal round(BigDecimal v, int scale) {
		BigDecimal one = new BigDecimal("1");
		return v.divide(one, scale, 4);
	}

	public static String getRandomNumber(int i) {
		if (i < 1) {
			i = 1;
		}
		String retStr = "";
		Date d = new Date();
		int value = 0;
		Random r = new Random(d.getTime());
		for (int j = 1; j <= i; j++) {
			value = Math.abs(r.nextInt());
			value %= 9;
			retStr = retStr + value;
		}
		return retStr;
	}

	public static void main(String[] args) {
		System.out.println("MathUtils:"
				+ MathUtils.round(new BigDecimal(3333333333389.236567788), 2));
	}

	public static BigDecimal add(BigDecimal a, BigDecimal b) {
		a = exceptFroNull(a, ZERO);
		b = exceptFroNull(b, ZERO);
		return a.add(b);
	}

	public static BigDecimal sub(BigDecimal a, BigDecimal b) {
		a = exceptFroNull(a, ZERO);
		b = exceptFroNull(b, ZERO);
		return a.subtract(b);
	}

	public static BigDecimal multi(BigDecimal a, BigDecimal b) {
		a = exceptFroNull(a, ZERO);
		b = exceptFroNull(b, ZERO);
		return a.multiply(b);
	}

	public static BigDecimal div(BigDecimal a, BigDecimal b) {
		a = exceptFroNull(a, ZERO);
		b = exceptFroNull(b, ZERO);
		if (b.compareTo(ZERO) == 0) {
			return ZERO;
		}
		return a.divide(b, 6, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal div_100(BigDecimal a) {
		BigDecimal div = div(a, ONEHANDRED);
		return div;

	}

	public static BigDecimal exceptFroNull(BigDecimal a, BigDecimal defaultValue) {
		return a == null ? defaultValue : a;
	}

	public static BigDecimal exceptFroNull(BigDecimal a) {
		return exceptFroNull(a, ZERO);
	}
	
	 public static BigDecimal getBigDecimal( Object value ) {  
		         BigDecimal ret = null;  
		        if( value != null ) {  
		            if( value instanceof BigDecimal ) {  
		               ret = (BigDecimal) value;  
		            } else if( value instanceof String ) {  
		                ret = new BigDecimal( (String) value );  
		          } else if( value instanceof BigInteger ) {  
		                 ret = new BigDecimal( (BigInteger) value );  
		            } else if( value instanceof Number ) {  
		                 ret = new BigDecimal( ((Number)value).doubleValue() );  
		            } else {  
		                 throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");  
		             }  
		        }  
		         return ret;  
		    } 

}
