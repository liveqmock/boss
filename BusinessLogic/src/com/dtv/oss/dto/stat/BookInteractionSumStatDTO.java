package com.dtv.oss.dto.stat;

import java.io.Serializable;

public class BookInteractionSumStatDTO  implements Serializable {
	private int operatorID;
	private String name;
	private int totalNum;		//受理量
	private int installNum;		//上门量
	private int successNum;		//受理成功量
	private int failNum;		//受理失败量
	private int c1Num;			//受理预约取消
	private int c2Num;			//安装预约取消
	private int processNum;		//正在处理量
	private int waitNum;		//待处理量
	
	public BookInteractionSumStatDTO() {
	}
	
	public String toString() {
		return "operatorID:"+operatorID+",name:"+name+
			   ",totalNum:"+totalNum+",installNum:"+installNum+",successNum:"+successNum+
			   ",failNum:"+failNum+"c1Num:"+c1Num+"c2Num:"+c2Num+"processNum:"+processNum+"waitNum:"+waitNum;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getInstallNum() {
		return installNum;
	}
	public void setInstallNum(int installNum) {
		this.installNum = installNum;
	}
	/**
	 * @return Returns the c1Num.
	 */
	public int getC1Num() {
		return c1Num;
	}
	/**
	 * @param num The c1Num to set.
	 */
	public void setC1Num(int num) {
		c1Num = num;
	}
	/**
	 * @return Returns the c2Num.
	 */
	public int getC2Num() {
		return c2Num;
	}
	/**
	 * @param num The c2Num to set.
	 */
	public void setC2Num(int num) {
		c2Num = num;
	}
	/**
	 * @return Returns the failNum.
	 */
	public int getFailNum() {
		return failNum;
	}
	/**
	 * @param failNum The failNum to set.
	 */
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	/**
	 * @return Returns the processNum.
	 */
	public int getProcessNum() {
		return processNum;
	}
	/**
	 * @param processNum The processNum to set.
	 */
	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}
	/**
	 * @return Returns the waitNum.
	 */
	public int getWaitNum() {
		return waitNum;
	}
	/**
	 * @param waitNum The waitNum to set.
	 */
	public void setWaitNum(int waitNum) {
		this.waitNum = waitNum;
	}
}
