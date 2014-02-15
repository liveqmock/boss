package com.dtv.oss.web.taglib.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtv.oss.util.DynamicShowAttributeDataInfo;
import com.dtv.oss.web.taglib.util.RequestUtils;
import com.dtv.oss.web.taglib.util.ResponseUtils;

public class DynamicShowAttributeTag extends TagSupport{
    //	TD的宽度
	private String tdWidth1;
	private String tdWidth2;
	
	//TD的高度
	private String tdHeight;
	private String controlSize;
    //文字处于表格的位置
	private String wordAlign="right";;
	//控件处于表格的位置
	private String controlAlign="left";
	//表格文字样式
	private String tdWordStyle;
	//表格控件样式
	private String tdControlStyle;
	
	private String title ="";
	private String titleClass ="";
	//private String textShowFlag ="false";
	
	//参数
	private String primaryKey;
	private String dtoName;
	private String bean;
	private String scope;
	
	//比较参数
	private String compareBean;
	private String compareScope;
	private String comparePrimaryKey;
	private String compareDtoname;
	
	public String getCompareBean() {
		return compareBean;
	}
	public void setCompareBean(String compareBean) {
		this.compareBean = compareBean;
	}
	
	public String getCompareScope() {
		return compareScope;
	}
	public void setCompareScope(String compareScope) {
		this.compareScope = compareScope;
	}
	
	public String getComparePrimaryKey() {
		return comparePrimaryKey;
	}
	public void setComparePrimaryKey(String comparePrimaryKey) {
		this.comparePrimaryKey = comparePrimaryKey;
	}
	
	public String getCompareDtoname() {
		return compareDtoname;
	}
	public void setCompareDtoname(String compareDtoname) {
		this.compareDtoname = compareDtoname;
	}
	
	public String getDtoName() {
		return dtoName;
	}
	public void setDtoName(String dtoName) {
		this.dtoName = dtoName;
	}
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getBean() {
		return bean;
	}
	public void setBean(String bean) {
		this.bean = bean;
	}
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getControlAlign() {
		return controlAlign;
	}
	public void setControlAlign(String controlAlign) {
		this.controlAlign = controlAlign;
	}
	public String getWordAlign() {
		return wordAlign;
	}
	public void setWordAlign(String wordAlign) {
		this.wordAlign = wordAlign;
	}
	
	public String getTdHeight() {
		return tdHeight;
	}
	public void setTdHeight(String tdHeight) {
		this.tdHeight = tdHeight;
	}
	public String getTdWidth1() {
		return tdWidth1;
	}
	public void setTdWidth1(String tdWidth1) {
		this.tdWidth1 = tdWidth1;
	}
	public String getTdWidth2() {
		return tdWidth2;
	}
	public void setTdWidth2(String tdWidth2) {
		this.tdWidth2 = tdWidth2;
	}
	public String getControlSize() {
		return controlSize;
	}
	public void setControlSize(String controlSize) {
		this.controlSize = controlSize;
	}
	public String getTdControlStyle() {
		return tdControlStyle;
	}
	public void setTdControlStyle(String tdControlStyle) {
		this.tdControlStyle = tdControlStyle;
	}
	public String getTdWordStyle() {
		return tdWordStyle;
	}
	public void setTdWordStyle(String tdWordStyle) {
		this.tdWordStyle = tdWordStyle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleClass() {
		return titleClass;
	}
	public void setTitleClass(String titleClass) {
		this.titleClass = titleClass;
	}
	/*
	public String getTextShowFlag() {
		return textShowFlag;
	}
	public void setTextShowFlag(String textShowFlag) {
		this.textShowFlag = textShowFlag;
	}
	*/
	
	public int doStartTag() throws JspTagException {
		DynamicShowAttributeDataInfo dynamicCsiCommonData =new DynamicShowAttributeDataInfo();
		String[] parameter =this.primaryKey.split(",");
		Collection  dynamicCols =new ArrayList();
		
//		  System.out.println("bean==========="+bean);
//		  System.out.println("dtoName==========="+dtoName);
//		  System.out.println("primaryKey==========="+primaryKey);
//		  
//		  System.out.println("compareDtoname==========="+compareDtoname);
//		  System.out.println("compareBean==========="+compareBean);
//		  System.out.println("comparePrimaryKey==========="+comparePrimaryKey);
		
		try{
		  Object obj = RequestUtils.lookup(pageContext, this.bean, scope);
		  ArrayList objectCols =new ArrayList();
		  if (obj instanceof Collection){
			 Iterator objIterator =((Collection)obj).iterator();
			 while (objIterator.hasNext()){
				 Object subObj =  objIterator.next();
				 dynamicCols = dynamicCsiCommonData.getDynamicCols(subObj,dtoName ,parameter);
			//	 htmlTrResult = getTrElement(dynamicCols)+htmlTrResult;
				 objectCols.add(dynamicCols);
			 }
		  }else{
			  dynamicCols = dynamicCsiCommonData.getDynamicCols(obj,dtoName ,parameter);
		    //  htmlTrResult = getTrElement(dynamicCols);
			  objectCols.add(dynamicCols);
		  }
		  
		  ArrayList compareObjCols =new ArrayList();
//		  System.out.println("comparePrimaryKey==========="+comparePrimaryKey);
		  if (comparePrimaryKey !=null){
		      String[] compareParameter =comparePrimaryKey.split(",");
		      Object compareObj =RequestUtils.lookup(pageContext, compareBean, compareScope);
		      Collection  compareDynamicCols =new ArrayList();
//		      System.out.println("compareObj.getClass().getName()***********"+compareObj.getClass().getName());
		      if (compareObj instanceof Collection){
				 Iterator compareObjIterator =((Collection)compareObj).iterator();
				 while (compareObjIterator.hasNext()){
					 Object subCompareObj =  compareObjIterator.next();
					 compareDynamicCols = dynamicCsiCommonData.getDynamicCols(subCompareObj,compareDtoname ,compareParameter);
				//	 htmlTrResult = getTrElement(dynamicCols)+htmlTrResult;
					 if (compareDynamicCols !=null) compareObjCols.add(compareDynamicCols);
				 }
		      }else{
		    	  compareDynamicCols = dynamicCsiCommonData.getDynamicCols(compareObj,compareDtoname ,compareParameter);
			    //  htmlTrResult = getTrElement(dynamicCols);
		    	  if (compareDynamicCols !=null)  compareObjCols.add(compareDynamicCols);
		      }
		  }
		
		  String  htmlTrResult ="";
		  
		  System.out.println("compareObjCols.size()========"+compareObjCols.size());
		  
//		  if(compareObjCols!=null&&!compareObjCols.isEmpty()){
//			  Iterator objIterator =objectCols.iterator();
//			  while (objIterator.hasNext()){
//					 Collection aa =  (Collection)objIterator.next();
//					 Iterator aaIter =aa.iterator();
//					 while (aaIter.hasNext()){
//						 String [] arrObj =(String [])aaIter.next();
//						 arrObj[5]=compareDtoname.trim()+"."+arrObj[5];
//					 }
//			  }	
//		  }
//		  if (compareObjCols.size() ==0 ) {
//			  Iterator objIterator =objectCols.iterator();
//			  while (objIterator.hasNext()){
//				     Collection  compareDynamicCols =new ArrayList();
//					 Collection objStrCol =  (Collection)objIterator.next();
//					 Iterator objStrIter =objStrCol.iterator();
//					 while (objStrIter.hasNext()){ 
//						 String [] objStr =(String [])objStrIter.next();
//						 String [] tempStr =new String[objStr.length];
//						 for (int i=0; i<objStr.length; i++){
//							 tempStr[i] =objStr[i];
//						 }
//						 compareDynamicCols.add(tempStr);
//					 }
//					 compareObjCols.add(compareDynamicCols);
//			  }
//		  }
		  
		  
		  if (false){
			  Iterator objIterator =objectCols.iterator();
			  Iterator compareObjIterator =compareObjCols.iterator();
			  while (objIterator.hasNext()){
					 Collection aa =  (Collection)objIterator.next();
					 Iterator aaIter =aa.iterator();
					 while (aaIter.hasNext()){
						 String [] aa_1 =(String [])aaIter.next();
						 printArrayInfo(aa_1,"objectCols");
					 }
			  }			 
			  while (compareObjIterator.hasNext()){
					 Collection bb =  (Collection)compareObjIterator.next();
					 Iterator bbIter =bb.iterator();
					 while (bbIter.hasNext()){
						 String [] bb_1 =(String [])bbIter.next();
						 printArrayInfo(bb_1,"compareObjCols");
					 }
			  }
		  }

		  Iterator objIterator =objectCols.iterator();
		  Iterator compareObjIterator =compareObjCols.iterator();
		  while (objIterator.hasNext()){
				 Collection subDynamicObj =  (Collection)objIterator.next();
				 Collection subCompareObj = null;
				 if(compareObjIterator.hasNext()){
					 subCompareObj=(Collection)compareObjIterator.next();
				 }
				 //设置页面颜色
				 if(compareDtoname!=null&&!"".equals(compareDtoname.trim())){
					 changeDynamicCols(subDynamicObj,subCompareObj);
				 }
				 htmlTrResult = getTrElement(subDynamicObj)+htmlTrResult;
		  }
		  
		  String htmlResult="";
		  if (htmlTrResult !=null && !htmlTrResult.equals("")){
			  if("JobCardTwoViewDTO".equals(dtoName))	
					 htmlResult = "<table align='center' width='810' border='0' cellspacing='1' cellpadding='3' class='list_bg'  id='mnu1' style='display:none' > \r\n";
				 else
					 htmlResult = "<table align='center' width='810' border='0' cellspacing='1' cellpadding='3' class='list_bg' > \r\n";
			 if(!(this.title==null || "".equals(this.title))){
				  htmlResult =htmlResult+"<tr> \r\n";
				  htmlResult =htmlResult+"   <td colspan='4' class='"+this.titleClass+"' align='center'><font size='3'> "+this.title+" </font> </td> \r\n";
				  htmlResult =htmlResult+"</tr> \r\n";
			  }
			  htmlResult =htmlResult +htmlTrResult;
			  htmlResult =htmlResult+"</table> \r\n";
		  }
		  
		  ResponseUtils.write(pageContext, htmlResult);
	    } catch(Exception e){
	    	e.printStackTrace();
			throw new JspTagException(e.toString());
	    }
		
		return SKIP_BODY;
	}
	private void printArrayInfo(String[]arr,String desc){
		if(arr==null){
			System.out.println(desc+" is null!!!");
		}else{
			for(int i=0;i<arr.length;i++){
				System.out.println(desc+"["+i+"]:"+arr[i]);
			}
		}
	}
	private void changeDynamicCols(Collection dynamicCols,
			Collection compareDynamicCols) {
		// dynamicInfo[0] labelName
		// dynamicInfo[2] 比较的文本
		// dynamicInfo[3] text文本的值
		// dynamicInfo[4] text显示方式
		// dynamicInfo[5] dynamicAttributeDto.getTextValue());
		if(dynamicCols==null||dynamicCols.isEmpty()||compareDynamicCols==null||compareDynamicCols.isEmpty())return;
		Iterator dynamicIterator = dynamicCols.iterator();
		while (dynamicIterator.hasNext()) {
			String[] dynamicInfo = (String[]) dynamicIterator.next();
			// System.out.println("dynamicInfo[4]========="+dynamicInfo[4]);
			Iterator compareDynamicIterator = compareDynamicCols.iterator();
			while (compareDynamicIterator.hasNext()) {
				String[] compareDymicInfo = (String[]) compareDynamicIterator
						.next();
				// System.out.println("compareDymicInfo[4]========"+compareDymicInfo[4]);
				//依赖属性名来判断是不是同一个东西,
//				if (dynamicInfo[5] != null
//						&& dynamicInfo[5].equalsIgnoreCase(compareDymicInfo[5])) {
//					if ((dynamicInfo[3] == null && compareDymicInfo[3] != null)
//							|| (dynamicInfo[3] != null && !dynamicInfo[3]
//									.equals(compareDymicInfo[3]))) {
//						printArrayInfo(dynamicInfo,"dynamicInfo");
//						printArrayInfo(compareDymicInfo,"compareDymicInfo");
//						dynamicInfo[4] = "0";
//					}
					// 只有dynamicInfo[0]能判断要比较的是不是一个东西,即数据库中设置的labelName,
				if (dynamicInfo[0] != null
						&& dynamicInfo[0].equalsIgnoreCase(compareDymicInfo[0])) {
					if ((dynamicInfo[3] == null && compareDymicInfo[3] != null)
							|| (dynamicInfo[3] != null && !dynamicInfo[3]
									.equals(compareDymicInfo[3]))) {
						dynamicInfo[4] = "0";
					}
				
					// if (dynamicInfo[2]!=null &&
					// dynamicInfo[2].equalsIgnoreCase(compareDymicInfo[2])){
					// if (dynamicInfo[3]!=null &&
					// !dynamicInfo[3].equals(compareDymicInfo[3])){
					// dynamicInfo[4] ="0";
					// }
				} else {
					continue;
				}
			}
		}

	}
	
	
	private String getTrElement(Collection dynamicCols) throws Exception{	
		StringBuffer htmlTRBuff=new StringBuffer();
		int tdCount =2;
		Iterator dynamicIterator =dynamicCols.iterator();
		//dynamicInfo[0] Label 名
		//dynamicInfo[1] 该label和它的值所占的格子，现在只支持2和4的格子
		//dynamicInfo[2] 比较的文本
		//dynamicInfo[3] text文本的值
		//dynamicInfo[4] 为1 普通显示,为0 红色字体显示
		while (dynamicIterator.hasNext()){
			String[] dynamicInfo =(String[])dynamicIterator.next();
			//侯修改,当比较信息存在差异的时候,不管有没有内容,都要显示.
			if (!dynamicInfo[4].equals("0")&&(dynamicInfo[3] ==null || dynamicInfo[3].equals(""))) continue;
			String textValue ="";
			String colorString ="<font size='2pt' >";
			if (dynamicInfo[4].equals("0")) colorString ="<font size='2pt' color='red'> ";
				
				
		//	if (this.textShowFlag.equalsIgnoreCase("true"))
		//		textValue ="<input type='text' name='"+dynamicInfo[2]+"' value="+dynamicInfo[3] +" >";
		//	else 
				textValue =dynamicInfo[3];
			if (dynamicInfo[1].equals("2")){
                if (tdCount ==2){
                   htmlTRBuff.append("<tr> \r\n");
                   htmlTRBuff.append(getHtmlTD(dynamicInfo[0],1,tdWidth1,colorString));
                   htmlTRBuff.append(getHtmlTD(textValue,2,tdWidth2,colorString));	
                   tdCount =4;
                } else {
                   htmlTRBuff.append(getHtmlTD(dynamicInfo[0],1,tdWidth1,colorString));
                   htmlTRBuff.append(getHtmlTD(textValue,2,tdWidth2,colorString));
                   htmlTRBuff.append("</tr> \r\n");
                   tdCount =2;
                }
            }else if (dynamicInfo[1].equals("4")){
                if (tdCount ==4){
        			htmlTRBuff.append(getHtmlTD("&nbsp;",1,tdWidth1,colorString));
                    htmlTRBuff.append(getHtmlTD("&nbsp;",2,tdWidth2,colorString));
                    htmlTRBuff.append("</tr> \r\n");
                    tdCount =2;
        		}
                htmlTRBuff.append("<tr> \r\n");
                htmlTRBuff.append(getHtmlTD(dynamicInfo[0],1,tdWidth1,colorString));
                htmlTRBuff.append(getHtmlTD(textValue,3,null,colorString));
                htmlTRBuff.append("</tr> \r\n");
            }
		} 
		if (tdCount ==4){
 			htmlTRBuff.append(getHtmlTD("&nbsp;",1,tdWidth1,""));
            htmlTRBuff.append(getHtmlTD("&nbsp;",2,tdWidth2,""));
            htmlTRBuff.append("</tr> \r\n");
 		} 
		return htmlTRBuff.toString();
	}
	
	
	
	private String getHtmlTD(String str,int type,String td_width,String colorString){
		if(str==null)
			str="";
		
		StringBuffer htmlTDBuff=new StringBuffer();
		htmlTDBuff.append("<td");
		
        //添加高度项
		if(!(this.tdHeight==null || "".equals(this.tdHeight)))
			htmlTDBuff.append(" height=" + this.tdHeight);
		//添加宽度项
		if (!(td_width ==null || "".equals(td_width)))
			htmlTDBuff.append(" width=" + td_width);
		
		if(type==1){
			//添加align项
			htmlTDBuff.append("  align=" + this.wordAlign);
			//添加文字TD样式项
			if(!(this.tdWordStyle==null || "".equals(this.tdWordStyle)))
				htmlTDBuff.append(" class=" + this.tdWordStyle);
			//填充文字内容
			htmlTDBuff.append(" >"  + colorString + str + "</font></td> \r\n");
		}else if (type ==2){
			//添加align项
			htmlTDBuff.append("  align=" + this.controlAlign);
			//添加控件TD样式项
			if(!(this.tdControlStyle==null || "".equals(this.tdControlStyle)))
				htmlTDBuff.append(" class=" + this.tdControlStyle);
			
			htmlTDBuff.append(" >"  + colorString +  str + "</font></td> \r\n");	
		}else if (type ==3){
            // 添加align项
			htmlTDBuff.append("  align=" + this.controlAlign);
			htmlTDBuff.append("  colspan=3 ");
			//添加控件TD样式项
			if(!(this.tdControlStyle==null || "".equals(this.tdControlStyle)))
				htmlTDBuff.append(" class=" + this.tdControlStyle);
			
			htmlTDBuff.append(" >"  + colorString + str + "</font></td> \r\n");	
		}
		
		return htmlTDBuff.toString();
		
	}
	
	/**
	 * Release any acquired resources.
	 */
	 public void release() {
	     super.release();
	     compareBean = null;
	     compareScope =null;
	     comparePrimaryKey =null;
	     compareDtoname =null;
	     primaryKey =null;
	     dtoName =null;
	     bean =null;
	     scope =null;
	 }
}


