package com.dtv.oss.dto.stat;

import java.io.Serializable;

public class CustomerProblemSumStatDTO implements Serializable {
	private int operatorID;
	private String name;
	private int catNormalNum;			//N	普通报修
	private int catSelfInstallNum;		//S	自安装失败
	private int catInOneWeekNum;		//W	上门安装一周内
	
	public CustomerProblemSumStatDTO() {
	}
	
	public String toString() {
		return "operatorID:"+operatorID+",name:"+name+
			   ",catNormalNum:"+catNormalNum+",catSelfInstallNum:"+catSelfInstallNum+
			   ",catInOneWeekNum:"+catInOneWeekNum;	
	}
	
	public int getCatInOneWeekNum() {
		return catInOneWeekNum;
	}
	public void setCatInOneWeekNum(int catInOneWeekNum) {
		this.catInOneWeekNum = catInOneWeekNum;
	}
	public int getCatNormalNum() {
		return catNormalNum;
	}
	public void setCatNormalNum(int catNormalNum) {
		this.catNormalNum = catNormalNum;
	}
	public int getCatSelfInstallNum() {
		return catSelfInstallNum;
	}
	public void setCatSelfInstallNum(int catSelfInstallNum) {
		this.catSelfInstallNum = catSelfInstallNum;
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
}
