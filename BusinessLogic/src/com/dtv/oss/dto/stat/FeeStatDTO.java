/*
 * Created on 2006-2-15
 *
 * @author Stone Liang
 */
package com.dtv.oss.dto.stat;

import java.io.Serializable;
import java.util.HashMap;

public class FeeStatDTO implements Serializable {
	private int orgID;
	private String orgName;
	private HashMap mapValue;
	
	public FeeStatDTO() {
		mapValue = new HashMap();
	}
	
	
	public String toString() {
		return "orgID:"+orgID+",orgName:"+orgName;
	}
	
	public static void main(String[] args) {
	}
	public int getOrgID() {
		return orgID;
	}
	public void setOrgID(int orgID) {
		this.orgID = orgID;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return Returns the mapValue.
	 */
	public HashMap getMapValue() {
		return mapValue;
	}
	/**
	 * @param mapValue The mapValue to set.
	 */
	public void setMapValue(HashMap mapValue) {
		this.mapValue = mapValue;
	}
}
