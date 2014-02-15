package com.dtv.oss.web.util.download;

import java.io.File;
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

import com.dtv.oss.dto.ExportDataColumnDefineDTO;
import com.dtv.oss.dto.ExportDataHeadDefineDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.WebActionException;

public abstract class ExcelCreate {
	
	protected static final int MAXRECORDSIZE=65000; 
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//���ݳ�ʼ������,ȡ�ļ�����,����ȡ�ı�����,
	public abstract String getExcelContent( ) throws WebActionException;
	//�õ��ļ�,���� �ļ���,�����ɳ�ʼ����������.
	public abstract File getExcelFile( String fileName)throws WebActionException;
	
	protected ExportDataHeadDefineDTO headDefine;
	protected List exportDefineList=null;
	protected Map statCalculateMap=null;
	protected Map statDefineMap=null;
	protected List captionList=null;
	protected List captionCellList=null;
//	protected Map calculateMap=null;
	protected int rowIndex=0;
	protected boolean isStat=false;
	protected int colCount=0;
	protected String querySql="";
	protected Object queryParameter;
	
	protected void finalize()throws Throwable{
		super.finalize();
		headDefine=null;
		exportDefineList.clear();
		exportDefineList=null;
		statCalculateMap.clear();
		statCalculateMap=null;
		statDefineMap.clear();
		statDefineMap=null;
		captionList.clear();
		captionList=null;
		captionCellList.clear();
		captionCellList=null;
		queryParameter=null;
	}
	
	public void init(String url,String subType,String sql,Object para) throws WebActionException{
		if(url!=null&&!"".equals(url)){
			Object[] def=Postern.getExportDataDefine(url,subType);
			if(def==null||def.length!=2||def[0]==null||def[1]==null){
				throw new WebActionException("û����Ч�ĵ�������:action="+url+";\nsubType="+subType+";\nsql="+sql+";\npara="+para);
			}
			try {
				headDefine = (ExportDataHeadDefineDTO) def[0];
				fileName=headDefine.getExportfilename();
				exportDefineList = (List) def[1];
				if(headDefine.getCustomsql()==null||"".equals(headDefine.getCustomsql())){
					querySql=sql;
				}else{
					querySql=headDefine.getCustomsql();
				}
				queryParameter=para;
			} catch (Exception e) {
				e.printStackTrace();
				throw new WebActionException(e.getMessage());
			}
		}else{
			throw new WebActionException("�������������");
		}
		if(exportDefineList==null||exportDefineList.isEmpty()){
			throw new WebActionException("û����Ч������");
		}
		initStatDefineList();
	}
	/**
	 * ��ͳ���з������.
	 * @throws Exception 
	 */
	private void initStatDefineList() throws WebActionException{
		statDefineMap=new HashMap();
		statCalculateMap=new LinkedHashMap();
//		calculateMap=new HashMap();
		captionList=new ArrayList();
		captionCellList=new ArrayList();
		Object[]export=exportDefineList.toArray(); 
		for(int i=0;i<export.length;i++){
			ExportDataColumnDefineDTO define=(ExportDataColumnDefineDTO) export[i];
			String captionName=define.getCaptionname();
			String valueDefine=define.getValue();
			Calculate cal=Calculate.init(valueDefine);
			if("row".equalsIgnoreCase(define.getCaptiontype())){
				statDefineMap.put(captionName,define);
				exportDefineList.remove(define);
				statCalculateMap.put(captionName, cal);
			}else{
//				calculateMap.put(captionName, cal);
				captionList.add(captionName);
				captionCellList.add(getHtmlCell(define, ""));
			}
		}
//		System.out.println("statDefineMap:"+statDefineMap);
//		System.out.println("statCalculateMap:"+statCalculateMap);
//		System.out.println("exportDefineList:"+exportDefineList.size());
		colCount=exportDefineList.size();
		isStat = !statCalculateMap.isEmpty();
		if (isStat) {
			colCount++;
			List li=new ArrayList();
			li.add(this.getIndexCell());
			li.addAll(captionCellList);
			captionCellList.clear();
			captionCellList=li;
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
		if(isStat){
			strMap.put("", getIndexCell());
		}
//		String objName=obj.getClass().getName();
		//��ѭ��,
		for(Iterator it=exportDefineList.iterator();it.hasNext();){
			ExportDataColumnDefineDTO define=(ExportDataColumnDefineDTO) it.next();
			String valueDefine=define.getValue();
			Object objValue=null;
			String key=define.getCaptionname();
			String value=null;
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
			if(isStat){
				calculateStat(key,objValue);
			}
			value=formatValue(define.getFormat(),objValue);
			HtmlCell cell=getHtmlCell(define,value);
			strMap.put(key, cell);
			
		}
		return strMap;
	}
	private void calculateStat(String captionName,Object val) throws WebActionException{
//		System.out.println("calculateStat.val:"+val);
		for(Iterator it=statCalculateMap.values().iterator();it.hasNext();){
			Calculate cal=(Calculate) it.next();
			if(cal.getStatParameter().equalsIgnoreCase(captionName)){
				Object sval=cal.getValue(new Object[]{val});
//				System.out.println("calculateStat.sval:"+sval);
			}
		}
	}
	private HtmlCell getHtmlCell(ExportDataColumnDefineDTO define,String value){
		HtmlCell cell=new HtmlCell();
		cell.setValue(value);
		cell.setCaption(define.getCaptionname());
		cell.setAlign(define.getAlign());
		cell.setCaptionbgcolor(define.getCaptionbgcolor());
		cell.setValuebgcolor(define.getValuebgcolor());
		return cell;
	}
	
	private HtmlCell getIndexCell(){
		HtmlCell cell=new HtmlCell();
		cell.setValue(rowIndex+"");
		cell.setCaption("");
		cell.setValuebgcolor("Silver");
		cell.setAlign("center");
		rowIndex++;
		return cell;
	}
	protected List getStatRow(){
		if(!isStat)return null;
		List list=new ArrayList();
		//��ѭ��,ȡ����ͳ�ƶ���,
		for (Iterator rit = statCalculateMap.entrySet().iterator(); rit
				.hasNext();) {
			Entry ren = (Entry) rit.next();
			// ����Ǳ���,���׵����� .
			String rkey = (String) ren.getKey();
			// �����ͳ�Ƽ��� ����,��ͳ�Ƶ�ֵ������,
			Calculate cal = (Calculate) ren.getValue();
			// ȡͳ�ƽ��,
			Object statValue = cal.getStatValue();
			// �����ͳ�Ƶ�������,
			String statColumnName = cal.getStatParameter();
//			System.out.println("statValue:" + statValue);
			// ���ɵ�Ԫ���map,
			Map strMap = new LinkedHashMap();
			// ȡ����Ҫͳ�ƵĶ���,
			ExportDataColumnDefineDTO statDefine = (ExportDataColumnDefineDTO) statDefineMap
					.get(rkey);
//			System.out.println("statDefine:" + statDefine);
			//ͳ�Ƶı���,
			String captionName = statDefine.getCaptionname();

			// ��ѭ��,�����ͳ�ƵĻ�,����ǰ���һ��,colIndex���һ,
			for (int colIndex = 0; statDefine != null && colIndex < colCount; colIndex++) {
				String val = "";
				HtmlCell cell = new HtmlCell();
				cell.setValue(val);
//				System.out.println("colIndex:" + colIndex);
				if (colIndex == captionList.indexOf(statColumnName) + 1) {
					val = formatValue(statDefine.getFormat(), statValue);
					cell.setValue(val);
					cell.setCaption(captionName);
					cell.setCaptionbgcolor(statDefine.getCaptionbgcolor());
					cell.setAlign(statDefine.getAlign());
					cell.setValuebgcolor(statDefine.getValuebgcolor());
				}
				// �����Ǳ���,
				if (colIndex == 0) {
					cell.setValue(rkey);
					cell.setCaption(captionName);
					cell.setValuebgcolor(statDefine.getCaptionbgcolor());
					cell.setAlign("center");
				}
				strMap.put(colIndex + "", cell);
			}
			list.add(strMap);
		}
		return list;
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




}
