package com.dtv.oss.dto.stat;

import java.io.Serializable;

public class DeviceDaySalesStatDTO   implements Serializable {
	private String batchNo;
	private String filldate;
	private String belongName;
	
	private long sxgq_2860;
	private long chgq_1888;
	private long chgq_2360;
	private long chbq_666;
	private long chbq_598;
	
	private long bzbq_666;
	private long bzbq_598;
	private long hwbq_666;
	private long hwbq_598;
	private long znkbk;
	
	private long zxpkbq;
	private long zxpkgq;
	private long totalmoney;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getBelongName() {
		return belongName;
	}
	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}
	public long getBzbq_598() {
		return bzbq_598;
	}
	public void setBzbq_598(long bzbq_598) {
		this.bzbq_598 = bzbq_598;
	}
	public long getBzbq_666() {
		return bzbq_666;
	}
	public void setBzbq_666(long bzbq_666) {
		this.bzbq_666 = bzbq_666;
	}
	public long getChbq_598() {
		return chbq_598;
	}
	public void setChbq_598(long chbq_598) {
		this.chbq_598 = chbq_598;
	}
	public long getChbq_666() {
		return chbq_666;
	}
	public void setChbq_666(long chbq_666) {
		this.chbq_666 = chbq_666;
	}
	public long getChgq_1888() {
		return chgq_1888;
	}
	public void setChgq_1888(long chgq_1888) {
		this.chgq_1888 = chgq_1888;
	}
	public long getChgq_2360() {
		return chgq_2360;
	}
	public void setChgq_2360(long chgq_2360) {
		this.chgq_2360 = chgq_2360;
	}
	public String getFilldate() {
		return filldate;
	}
	public void setFilldate(String filldate) {
		this.filldate = filldate;
	}
	public long getHwbq_598() {
		return hwbq_598;
	}
	public void setHwbq_598(long hwbq_598) {
		this.hwbq_598 = hwbq_598;
	}
	public long getHwbq_666() {
		return hwbq_666;
	}
	public void setHwbq_666(long hwbq_666) {
		this.hwbq_666 = hwbq_666;
	}
	public long getSxgq_2860() {
		return sxgq_2860;
	}
	public void setSxgq_2860(long sxgq_2860) {
		this.sxgq_2860 = sxgq_2860;
	}
	public long getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(long totalmoney) {
		this.totalmoney = totalmoney;
	}
	public long getZnkbk() {
		return znkbk;
	}
	public void setZnkbk(long znkbk) {
		this.znkbk = znkbk;
	}
	public long getZxpkbq() {
		return zxpkbq;
	}
	public void setZxpkbq(long zxpkbq) {
		this.zxpkbq = zxpkbq;
	}
	public long getZxpkgq() {
		return zxpkgq;
	}
	public void setZxpkgq(long zxpkgq) {
		this.zxpkgq = zxpkgq;
	}
	
}
