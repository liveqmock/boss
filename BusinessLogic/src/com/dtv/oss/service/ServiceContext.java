/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service;

import java.util.HashMap;

/**
 * @author Leon Liu 2005/09/21
 *
 * ��������Ҫ������ Ϊ�������service �࣬��ҵ��ִ�й����д洢�����Ķ���
 */
public class ServiceContext {
	private HashMap variables=null;
	
	/////////// getter/setter methods //////////////
	public Object get(Object key) {
		return variables.get(key);
	}
	
	public void put(Object key, Object value) {
		variables.put(key, value);
	}
	public ServiceContext(){
		this.variables=new HashMap();
	}
}
