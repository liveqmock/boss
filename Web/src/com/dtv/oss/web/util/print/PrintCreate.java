package com.dtv.oss.web.util.print;


import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.GregorianCalendar;

import com.dtv.oss.dto.PrintBlockDetailDTO;
import com.dtv.oss.dto.PrintBlockDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.download.Calculate;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;

public class PrintCreate {
	
	protected static final int MAXRECORDSIZE=65000; 

	protected PrintBlockDTO blockDefine;
	protected List exportDefineList=null;
	protected String querySql="";
	protected Object queryParameter;
	
	protected void finalize()throws Throwable{
		super.finalize();
		blockDefine=null;
		exportDefineList.clear();
		exportDefineList=null;
		queryParameter=null;
	}
	
	public void init(int id,Object para) throws WebActionException{
		if(id != 0){
			Object[] def=Postern.getPrintDataDefine(id);
			if(def==null||def.length!=2||def[0]==null||def[1]==null){
				throw new WebActionException("û����Ч�Ĵ�ӡ����:id="+id+";npara="+para);
			}
			try {
				blockDefine = (PrintBlockDTO) def[0];
				exportDefineList = (List) def[1];
				querySql=blockDefine.getCustomsql();
				queryParameter=para;
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebActionException(e.getMessage());
			}
		}else{
			throw new WebActionException("�������������");
		}
		if(exportDefineList==null||exportDefineList.isEmpty()){
			throw new WebActionException("û����Ч�Ĵ�ӡ����");
		}
	}
	

	/**
	 * �Ƿ���Чֵ,Ŀǰֻ����java.lang�º�Date,String����,��������.
	 * @param obj
	 * @return
	 */
	protected boolean isValidValue(Object obj) {
		if (obj != null) {
			String valClassName = obj.getClass().getName();
//			System.out.println("valClassName "+valClassName);
//			System.out.println("objval "+obj);
			if (!valClassName.startsWith("java.lang.Class")
					&& (valClassName.startsWith("java.lang") || valClassName
							.startsWith("java.sql.Timestamp"))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * ����һ��dto,�õ�һ������,
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	protected Map object2StringMap(Map objMap) throws WebActionException{
//		Map objMap=object2ObjectMap(obj);
//		System.out.println("ExcelCreate.object2StringMap.objMap  "+objMap);
		Map strMap=new LinkedHashMap();
		//if(isStat){
		//	strMap.put("", getIndexCell());
		//}
//		String objName=obj.getClass().getName();
		//��ѭ��,
		for(Iterator it=exportDefineList.iterator();it.hasNext();){
			PrintBlockDetailDTO define=(PrintBlockDetailDTO) it.next();
			String valueDefine=define.getValue();
			Object objValue=null;
			String key=define.getName();
			String value=null;
			String type = define.getType();
			if(CommonKeys.SET_V_PRINTBLOCKDETAILTYPE_T.equals(type)){
				value = define.getValue();
			}
			else{
			//���õڸ�ȡֵ��ʵ����һ��,
			Calculate cal=Calculate.init(valueDefine);
			if(cal==null){
				String paraName=valueDefine.toUpperCase();
				objValue=objMap.get(paraName);
			}else{
				//ÿ��һ���������,
//				Calculate cal=(Calculate) calculateMap.get(key);
//				System.out.println("ExcelCreate.object2StringMap.cal  "+cal.getCommonDataName());
				String[]para=cal.getParameterName();
				Object[] paraArr=null;
				if(para!=null){
					paraArr=new Object[para.length];
					for(int i=0;i<para.length;i++){
						String paraName=para[i];
//						System.out.println("ExcelCreate.object2StringMap.paraName  "+paraName);
						Object paraValue=objMap.get(paraName);
//						System.out.println("ExcelCreate.object2StringMap.paraValue  "+paraValue);
						paraArr[i]=paraValue;
					}
				}
				objValue=cal.getValue(paraArr);
			}
			value=formatValue(define.getFormat(),objValue);
			}
			
			PrintCell cell=getPrintCell(define,value);
			strMap.put(key, cell);
			
		}
		return strMap;
	}
	
	
	
	private PrintCell getPrintCell(PrintBlockDetailDTO define,String value){
		PrintCell cell=new PrintCell();
		cell.setValue(value);
		cell.setName(define.getName());
		cell.setAlign(define.getAlign());
		cell.setLeft(String.valueOf(define.getLeft()));
		cell.setTop(String.valueOf(define.getTop()));
		cell.setWidth(String.valueOf(define.getWidth()));
		cell.setMigrationRow(String.valueOf(define.getMigrationRow()));
		cell.setType(define.getType());
		return cell;
	}
	

	

	/**
	 * ��ʽ��һ��ֵ,��������/����/�ַ������ָ�ʽ,ʹ��java.text.format��ط���,
	 * @param format ��ʽ�ַ���,
	 * @param obj ��ʽ ��ֵ,
	 * @return
	 */
	private String formatValue(String format,Object obj){
		if(obj==null)return "";
		boolean isDefaultFormat=false;
		if(format==null||"".equals(format)){
			isDefaultFormat=true;
		}
		//����������ҪӦ�ø�ʽ,
		if(obj instanceof java.util.Date){
			if(isDefaultFormat){
				format="yyyy-MM-dd";
			}
			SimpleDateFormat f = new SimpleDateFormat(format);
			return f.format(obj);
		}
		//��ֵ�͵�,��С��λ��,��ҪӦ�ø�ʽ.
		if(obj instanceof Number){
			if(isDefaultFormat){
				format="0";
			}
			NumberFormat f = new DecimalFormat(format);
			return f.format(obj);
		}
		//�ַ������͵Ĳ�����Ĭ�ϸ�ʽ,
		if(obj instanceof String){
			if(!isDefaultFormat){
				return MessageFormat.format(format,new Object[]{obj});
			}
		}
		return obj.toString();
	}



	public String getHtmlString() throws WebActionException {
		StringBuffer res=new StringBuffer();
		
		int MarginLeft = blockDefine.getMarginLeft();
		int MarginTop = blockDefine.getMarginTop();
		int textSize = blockDefine.getTextSize();
		
		int i=0;
		try {
			PrintDAO dao = PrintDAO.init(querySql, queryParameter);
			while (dao.hasNext()) {
				Map rowData = object2StringMap(dao.next());
				//rowContent = generateHtml(rowData);
				System.out.println("::::::rowData:"+rowData);
				for(Iterator rit=rowData.entrySet().iterator();rit.hasNext();){
					Entry entry=(Entry) rit.next();
					PrintCell cell=(PrintCell) entry.getValue();
					String cellContent=cell.getValue();
					if(cellContent==null) cellContent="";
					String cellLeft=cell.getLeft();
					String cellTop=cell.getTop();
					String cellWidth=cell.getWidth();
					String printType=cell.getType();
					String migrationRow=cell.getMigrationRow();
					String type=cell.getType();
					
					java.util.Calendar gc = GregorianCalendar.getInstance();
					gc.setTimeInMillis(System.currentTimeMillis());
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.YEAR));
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.MONTH)+1);
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.DAY_OF_MONTH));
					
					
					res.append("<div style=\"position:absolute;left:"+(WebUtil.StringToInt(cellLeft)+MarginLeft)+"px; top:"+( WebUtil.StringToInt(cellTop) + (WebUtil.StringToInt(migrationRow)*i) + MarginTop)+"px; font-size:"+textSize+"px; FONT-FAMILY: ����_GB2312; \" ");
					
					if(cellWidth!=null && !"".equals(cellWidth) && !"0".equals(cellWidth))
						res.append("style=\"width:"+cellWidth+"px;\"");
					res.append(">"+cellContent+"</div>").append("\n");
					}
				res.append("\n");
				i=i+1;
				System.out.println("::::::res:"+res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		return new String(res);
	}
	
	
	public String getHtmlString(int rowHeight) throws WebActionException {
		StringBuffer res=new StringBuffer();
		
		int MarginLeft = blockDefine.getMarginLeft();
		int MarginTop = blockDefine.getMarginTop();
		int textSize = blockDefine.getTextSize();
		
		int i=0;
		try {
			PrintDAO dao = PrintDAO.init(querySql, queryParameter);
			while (dao.hasNext()) {
				Map rowData = object2StringMap(dao.next());
				//rowContent = generateHtml(rowData);
				System.out.println("rowData:"+rowData);
				for(Iterator rit=rowData.entrySet().iterator();rit.hasNext();){
					Entry entry=(Entry) rit.next();
					PrintCell cell=(PrintCell) entry.getValue();
					String cellContent=cell.getValue();
					if(cellContent==null) cellContent="";
					String cellLeft=cell.getLeft();
					String cellTop=cell.getTop();
					String cellWidth=cell.getWidth();
					String printType=cell.getType();
					String migrationRow=cell.getMigrationRow();
					
					java.util.Calendar gc = GregorianCalendar.getInstance();
					gc.setTimeInMillis(System.currentTimeMillis());
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.YEAR));
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.MONTH)+1);
					if("��".equals(cell.getName())) cellContent = String.valueOf(gc.get(java.util.Calendar.DAY_OF_MONTH));
					
					res.append("<div style=\"position:absolute;left:"+(WebUtil.StringToInt(cellLeft)+MarginLeft)+"px; top:"+( WebUtil.StringToInt(cellTop) + (WebUtil.StringToInt(migrationRow)*i) + MarginTop + rowHeight)+"px; font-size:"+textSize+"px; FONT-FAMILY: ����_GB2312; \" ");
					
					if(cellWidth!=null && !"".equals(cellWidth) && !"0".equals(cellWidth))
						res.append("style=\"width:"+cellWidth+"px;\"");
					res.append(">"+cellContent+"</div>").append("\n");
					}
				res.append("\n");
				i=i+1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		return new String(res);
	}

}
