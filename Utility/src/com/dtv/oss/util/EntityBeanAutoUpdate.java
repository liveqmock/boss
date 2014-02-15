package com.dtv.oss.util;

import javax.ejb.EntityBean;
import java.lang.reflect.Method;
import com.dtv.oss.dto.ReflectionSupport;

public class EntityBeanAutoUpdate{

  private EntityBeanAutoUpdate(){
  }

  //update entity bean according to DTO
  public synchronized static int update(ReflectionSupport dto, EntityBean bean) throws Exception{
	Method method = null;

	Object[] names = dto.getMap().keySet().toArray();
	for(int i = 0; i < names.length; i++){
	  StringBuffer name = new StringBuffer((String)names[i]);
	  char one = name.charAt(0);
	  one = Character.toUpperCase(one);
	  name.setCharAt(0, one);

	  //find the DTO getter method
	  Method[] methods = dto.getClass().getMethods();
	  int j;
	  for(j = 0; j < methods.length; j++){
		if(methods[j].getName().equals("get" + name.toString())){
		  method = methods[j];
		  break;
		}
	  }
	  if(j == methods.length){
		throw new Exception("No proper getter method for DTO attribute "
							+ name + " found!");
	  }
	  Object value = method.invoke(dto, null);
	  if(Constant.DEBUGMODE){
		System.out.println("DTO getter method:" + method.getName() + " value:" + value);

		//find the ENTITY BEAN setter method
	  }
	  methods = bean.getClass().getMethods();
	  for(j = 0; j < methods.length; j++){
		if(methods[j].getName().equals("set" + name.toString())){
		  method = methods[j];
		  break;
		}
	  }

	  if(j == methods.length){
		throw new Exception("No proper setter method for Entitybean attribute "
							+ name + " found!");
	  }
	  //set value
	  Object[] argus = new Object[1];
	  argus[0] = value;
	  if(Constant.DEBUGMODE){
		System.out.println(bean.toString() + "\n setter method:\n" + method.getName() + " argv:\n" + value);
	  }
	  method.invoke(bean, argus);

	}
	return 0;
  }

}