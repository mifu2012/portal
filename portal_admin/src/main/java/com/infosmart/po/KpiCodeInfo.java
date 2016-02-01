package com.infosmart.po;

import java.util.Date;

public class KpiCodeInfo {
	
	    private static final long serialVersionUID = 741231858441822688L;

	    //========== properties ==========

		/**
		 * This property corresponds to db column <tt>KPI_CODE</tt>.
		 */
		private String kpiCode;

		/**
		 * This property corresponds to db column <tt>GMT_CREATE</tt>.
		 */
		private Date gmtCreate;

		/**
		 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
		 */
		private Date gmtModified;

	    //========== getters and setters ==========

	    /**
	     * Getter method for property <tt>kpiCode</tt>.
	     *
	     * @return property value of kpiCode
	     */
		public String getKpiCode() {
			return kpiCode;
		}
		
		/**
		 * Setter method for property <tt>kpiCode</tt>.
		 * 
		 * @param kpiCode value to be assigned to property kpiCode
	     */
		public void setKpiCode(String kpiCode) {
			this.kpiCode = kpiCode;
		}

	    /**
	     * Getter method for property <tt>gmtCreate</tt>.
	     *
	     * @return property value of gmtCreate
	     */
		public Date getGmtCreate() {
			return gmtCreate;
		}
		
		/**
		 * Setter method for property <tt>gmtCreate</tt>.
		 * 
		 * @param gmtCreate value to be assigned to property gmtCreate
	     */
		public void setGmtCreate(Date gmtCreate) {
			this.gmtCreate = gmtCreate;
		}

	    /**
	     * Getter method for property <tt>gmtModified</tt>.
	     *
	     * @return property value of gmtModified
	     */
		public Date getGmtModified() {
			return gmtModified;
		}
		
		/**
		 * Setter method for property <tt>gmtModified</tt>.
		 * 
		 * @param gmtModified value to be assigned to property gmtModified
	     */
		public void setGmtModified(Date gmtModified) {
			this.gmtModified = gmtModified;
		}
	}

