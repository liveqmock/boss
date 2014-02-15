package com.dtv.oss.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.dtv.oss.web.util.CommonSettingValue;

/**
 * 调查用的动态DTO
 * author     ：zhouxushun 
 * date       : 2005-12-5
 * description:
 * @author 250713z
 *
 */
public class DynamicServeyDTO implements  Serializable {

	//调查ID
	private int serveyID;
	//页面显示调查标题
	private String description;
	//调查内容，封装格式为：keyList为DB中的code， valueList为DB中的title
	private ArrayList keyList=null;
	private ArrayList valueList=null;
	
	//调查的默认值
	private Collection defaultList =new ArrayList();
	//调查在页面的显示类型，0为下拉框、1为文本、2为Rdio
	private String showType;
	//调查是否允许多选
	private boolean mutilSelect;
	//是否必填项
	private boolean forceWrite;
	//备注
	private String memo;
	
	
	public DynamicServeyDTO()
	{
		this.keyList=new ArrayList();
		this.valueList=new ArrayList();
	}
	
	public void put(String key,CommonSettingValue value){
		//不允许key值重复
		if(key==null || value==null || "".equals(key) || "".equals(value) || keyList.contains(key))
				return;
		else{
			keyList.add(key);
			valueList.add(value);
		}
	}
	
	public Map getMap() {
		return null;
	}
	
	public Collection getDefaultList() {
		return defaultList;
	}

	public void setDefaultList(String defaultValue) {
		this.defaultList.add(defaultValue);
	}

	public String getDescription() {
		return description;
	}

	public boolean getForceWrite() {
		return forceWrite;
	}

	public void setForceWrite(boolean forceWrite) {
		this.forceWrite = forceWrite;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getServeyID() {
		return serveyID;
	}

	public void setServeyID(int serveyID) {
		this.serveyID = serveyID;
	}

	public Iterator getKeysIterator() {
		return keyList.iterator();
	}
	
	public Collection getKeys(){
		return keyList;
	}
	
	public CommonSettingValue getValue(String key) {
		
		int keyIndex=keyList.lastIndexOf(key);
		if(keyIndex==-1)
			return null;
		
		if(keyList.size()!=valueList.size())
			return null;
		
		return (CommonSettingValue)valueList.get(keyIndex);
		
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public boolean isMutilSelect() {
		return mutilSelect;
	}

	public void setMutilSelect(boolean mutilSelect) {
		this.mutilSelect = mutilSelect;
	}

}
