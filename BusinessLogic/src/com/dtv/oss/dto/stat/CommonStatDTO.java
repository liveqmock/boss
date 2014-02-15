package com.dtv.oss.dto.stat;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 统计通用结果保存DTO
 * author     ：Jason.Zhou 
 * date       : 2006-2-20
 * description:
 * @author 250713z
 *
 */
public class CommonStatDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private HashMap keyValue=null;

	public CommonStatDTO(){
		keyValue=new HashMap();		
	}
	
	public String getId() {
		return id;
	}
	

	public void setId(String id) {
		this.id = id;
	}


	public HashMap getKeyValue() {
		return keyValue;
	}


	public void setKeyValue(HashMap keyValue) {
		this.keyValue = keyValue;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
