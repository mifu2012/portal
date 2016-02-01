package com.infosmart.portal.pojo;

import java.math.BigDecimal;


public class UserExperienceInfo {
	//引入会员数
		private String totalMemberIn ;
		//尝试使用数
		private String totalTry ;
		//成功使用量
		private String totalSuccess ;
		//尝试使用百分比
		private BigDecimal totalTryPer ;
		//尝试使用者中，成功使用百分比
		private BigDecimal totalSucPer ;
		//尝试使用量饼图半径
		private double totalTryPie;
		//成功使用量饼图半径
		private double totalSucPie;
		
		
		//新老会员总体会员数
		private String totalOldAndNew;
		
		public String getTotalOldAndNew() {
			return totalOldAndNew;
		}


		public void setTotalOldAndNew(String totalOldAndNew) {
			this.totalOldAndNew = totalOldAndNew;
		}


		//老会员尝试使用数
		private String oldMemberTry ;
		//老会员成功使用量
		private String oldMemberSuccess ;
		//老会员尝试使用百分比
		private BigDecimal oldMemberTryPer ;
		//老会员尝试使用者中，成功使用百分比
		private BigDecimal oldMemberSucPer ;
		//老会员尝试使用量饼图半径
		private double oldMemberTryPie;
		//老会员成功使用量饼图半径
		private double oldMemberSucPie;
		
		
		//新会员尝试使用数
		private String newMemberTry ;
		//新会员成功使用量
		private String newMemberSuccess ;
		//新会员尝试使用百分比
		private BigDecimal newMemberTryPer ;
		//新会员尝试使用者中，成功使用百分比
		private BigDecimal newMemberSucPer ;
		//新会员尝试使用量饼图半径
		private double newMemberTryPie;
		//新会员成功使用量饼图半径
		private double newMemberSucPie;

		

		public BigDecimal getTotalTryPer() {
			return totalTryPer;
		}


		public void setTotalTryPer(BigDecimal totalTryPer) {
			this.totalTryPer = totalTryPer;
		}


		public BigDecimal getTotalSucPer() {
			return totalSucPer;
		}


		public void setTotalSucPer(BigDecimal totalSucPer) {
			this.totalSucPer = totalSucPer;
		}


		


		public BigDecimal getOldMemberTryPer() {
			return oldMemberTryPer;
		}


		public void setOldMemberTryPer(BigDecimal oldMemberTryPer) {
			this.oldMemberTryPer = oldMemberTryPer;
		}


		public BigDecimal getOldMemberSucPer() {
			return oldMemberSucPer;
		}


		public void setOldMemberSucPer(BigDecimal oldMemberSucPer) {
			this.oldMemberSucPer = oldMemberSucPer;
		}


		


		public BigDecimal getNewMemberTryPer() {
			return newMemberTryPer;
		}


		public void setNewMemberTryPer(BigDecimal newMemberTryPer) {
			this.newMemberTryPer = newMemberTryPer;
		}


		public BigDecimal getNewMemberSucPer() {
			return newMemberSucPer;
		}


		public void setNewMemberSucPer(BigDecimal newMemberSucPer) {
			this.newMemberSucPer = newMemberSucPer;
		}





		public double getTotalTryPie() {
			return totalTryPie;
		}


		public void setTotalTryPie(double totalTryPie) {
			this.totalTryPie = totalTryPie;
		}


		public double getTotalSucPie() {
			return totalSucPie;
		}


		public void setTotalSucPie(double totalSucPie) {
			this.totalSucPie = totalSucPie;
		}


		public double getOldMemberTryPie() {
			return oldMemberTryPie;
		}


		public void setOldMemberTryPie(double oldMemberTryPie) {
			this.oldMemberTryPie = oldMemberTryPie;
		}


		public double getOldMemberSucPie() {
			return oldMemberSucPie;
		}


		public void setOldMemberSucPie(double oldMemberSucPie) {
			this.oldMemberSucPie = oldMemberSucPie;
		}


		public double getNewMemberTryPie() {
			return newMemberTryPie;
		}


		public void setNewMemberTryPie(double newMemberTryPie) {
			this.newMemberTryPie = newMemberTryPie;
		}


		public double getNewMemberSucPie() {
			return newMemberSucPie;
		}


		public void setNewMemberSucPie(double newMemberSucPie) {
			this.newMemberSucPie = newMemberSucPie;
		}


		public String getTotalMemberIn() {
			return totalMemberIn;
		}


		public void setTotalMemberIn(String totalMemberIn) {
			this.totalMemberIn = totalMemberIn;
		}


		public String getTotalTry() {
			return totalTry;
		}


		public void setTotalTry(String totalTry) {
			this.totalTry = totalTry;
		}


		public String getTotalSuccess() {
			return totalSuccess;
		}


		public void setTotalSuccess(String totalSuccess) {
			this.totalSuccess = totalSuccess;
		}


		public String getOldMemberTry() {
			return oldMemberTry;
		}


		public void setOldMemberTry(String oldMemberTry) {
			this.oldMemberTry = oldMemberTry;
		}


		public String getOldMemberSuccess() {
			return oldMemberSuccess;
		}


		public void setOldMemberSuccess(String oldMemberSuccess) {
			this.oldMemberSuccess = oldMemberSuccess;
		}


		public String getNewMemberTry() {
			return newMemberTry;
		}


		public void setNewMemberTry(String newMemberTry) {
			this.newMemberTry = newMemberTry;
		}


		public String getNewMemberSuccess() {
			return newMemberSuccess;
		}


		public void setNewMemberSuccess(String newMemberSuccess) {
			this.newMemberSuccess = newMemberSuccess;
		}


}
