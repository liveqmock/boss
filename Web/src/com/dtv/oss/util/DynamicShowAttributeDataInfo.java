package com.dtv.oss.util;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import com.dtv.oss.dto.DynamicShowAttributesDTO;
import com.dtv.oss.web.util.WebUtil;
import java.math.BigDecimal;

public class DynamicShowAttributeDataInfo {   
      
   public Collection getDynamicCols(Object operaterDto,String dtoName,String[] primaryKey) 
     throws Exception{
	   Collection dynamicCols =new ArrayList();
	   String[]  primaryKeyValues =new String[primaryKey.length]; 
	   boolean dynamicFlag =true;
	   try{	  
		 System.out.println("primaryKey.length=========="+primaryKey.length);
		 System.out.println("operaterDto==========="+operaterDto.getClass().getName());
		
		 Collection showAttributesCols =Postern.getDynamicShowAttributesDto(dtoName);
		 Iterator attributeIterator =showAttributesCols.iterator();
		 while (attributeIterator.hasNext()){
			 DynamicShowAttributesDTO dynamicAttributeDto =(DynamicShowAttributesDTO)attributeIterator.next();
			 String[] wrapAtrribute =new String[6];
			 wrapAtrribute[0] =dynamicAttributeDto.getLabelName();
			 wrapAtrribute[1] =String.valueOf(dynamicAttributeDto.getLabelCols());
			 wrapAtrribute[2] =dynamicAttributeDto.getTextName();
			 String name=dynamicAttributeDto.getTextValue().trim();
//			 StringBuffer name = new StringBuffer(dynamicAttributeDto.getTextValue());
			 wrapAtrribute[4] ="1";
			 wrapAtrribute[5] =dynamicAttributeDto.getValueSourceType()+":"+dynamicAttributeDto.getTextValue();
//			 char one = dynamicAttributeDto.getTextValue().charAt(0);
//			 one = Character.toUpperCase(one);
//			 name.setCharAt(0, one);
			 Method[] methods = operaterDto.getClass().getMethods();
			 boolean matchFlag =false;
			 Object value =null ;
			 
			 for (int i=0 ;i<methods.length && matchFlag==false;i++){
				 for (int j=0; j<primaryKey.length; j++){
				     if ((methods[i].getName()).equalsIgnoreCase("get"+primaryKey[j])){
					    value =methods[i].invoke(operaterDto,null); 
					    if (value instanceof Integer) value =((Integer)value).toString();
					    if (value.equals("0")) dynamicFlag =false;	
					    primaryKeyValues[j] =value.toString();
					 }
				 }
	
	    		 if (!("get"+name).equalsIgnoreCase(methods[i].getName()))  //原本想用equals，但为了便于配置改为equalsIgnoreCase
	    			continue;
	    		 else
	    			value =methods[i].invoke(operaterDto,null);
	    		    		
	    		 matchFlag =true;
	    	//	 if (value ==null) break;
	    		 if (value instanceof Integer){
	        		if (((Integer)value).intValue() ==0) 
	        			break;
	        		else 
	        			value =((Integer)value).toString();   //将整数转换成String
	        	 } 
	    		 else if (value instanceof String){
	        		if (((String)value).equals("")) break;
	        	 } 
	    		 else if (value instanceof BigDecimal){
	    			 if (((BigDecimal)value).intValue() ==0) 
		        			break;
		        		else 
		        			value =((BigDecimal)value).toString();   //将BigDecimal转换成String
		         }
	    		 switch (dynamicAttributeDto.getValueSourceType().charAt(0)) {
			         case 'A' :
			            wrapAtrribute[3] =(String)value;
			            break;
			         case 'B' :
			            if (value instanceof String)
			                wrapAtrribute[3] =Postern.getHashValueByNameKey(dynamicAttributeDto.getValueTerm(),(String)value);
			            if (value instanceof Timestamp)
			                wrapAtrribute[3] =WebUtil.TimestampToString((Timestamp)value,dynamicAttributeDto.getValueTerm());
			            break;
			         //值为Sql文
			         case 'C' :
			        	String sql =dynamicAttributeDto.getValueTerm();
				        wrapAtrribute[3] =Postern.getStringBySQL(sql,primaryKeyValues);
				        break;    
			         case 'D' :
			            //特殊处理
			            // 本想用反射机制，定位Postern的方法，但是需要new Postern()对象。感觉这样系统的开销比较大，不划算。
					    wrapAtrribute[3] =Postern.getDymamicShowAttribute(value,dynamicAttributeDto.getValueTerm());	  
					    break;
			         default :
			            break;
			      }    
			  }
  			
			 dynamicCols.add(wrapAtrribute);
		 }
	  }
	  catch(Exception e){
		  e.printStackTrace();
		  throw new Exception (e.toString());
	  }
	  
	  if (dynamicFlag)
	      return dynamicCols;
	  else
		  return null;
	}
   
}
