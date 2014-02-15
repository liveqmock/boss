package com.dtv.oss.web.taglib.html;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

public class ShowCommonSettingDataTag extends BodyTagSupport {

	//���ҳ��ʱcheckbox������
	private String controlName="";
	//���ҳ��ʱcheckbox��λ��
	private String controlAlign="";
	//���ҳ��ʱcheckbox����ʽ
	private String controlStyle="";
	//���ҳ��ʱ���ֵ���ʽ
	private String worldStyle="";
	//���ҳ��ʱ���ֵ�λ��
	private String worldAlign="";
	//ÿ�е���ʽ1
    private String style1 ="";
    //ÿ�е���ʽ2
    private String style2 ="";
    //���ŵ�����ʱ��Ӧ����ʽ
    private String overStyle ="";
    //CommonSettingData��keyֵ
    private String mapName="";
    //�Լ�װ���map������pageContxt��Χ��
    private String setName="";
    //ƥ���CommonSettingData���á���������
    private String match="";
    
    private int curNO=0;
    private Map map=null;
    private HashSet set=null;
	private static String controlNameConst="partChoose";
	private static String align="center";
	
	public int doStartTag() throws JspException{
		if((mapName==null || "".equals(mapName)) && (setName==null || "".equals(setName)))
			return SKIP_BODY;
		
		if(!(mapName==null || "".equals(mapName))){
			this.map=Postern.getHashKeyValueByName(mapName);
			if(map==null || map.isEmpty())
				return SKIP_BODY;
		}
		else{
			this.map=(Map) pageContext.getAttribute(setName);
			if(map==null || map.isEmpty())
				return SKIP_BODY;
		}
		
		//����ҳ�����
		if(controlName==null || "".equals(controlName))
			controlName=controlNameConst;
		if(controlAlign==null || "".equals(controlAlign))
			controlAlign=align;
		if(worldAlign==null || "".equals(worldAlign))
			worldAlign=align;
		
		//��ƥ��ֵ�ŵ�Set��,match�� �������ָ�
		set=new HashSet();
		if(!(match==null || "".equals(match))){
			String matchValues=(String)pageContext.getAttribute(match);
			if(!(matchValues==null || "".equals(matchValues))){
				String matchValue[]=matchValues.split(",");
				for(int i=0;i<matchValue.length;i++)
					set.add(matchValue[i]);
			}
		}
		
		//��ʼ���ҳ����
		String htmlResult=fillHTML();
		
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"���ؽ��Ϊ��" + htmlResult);
		ResponseUtils.write(pageContext,htmlResult);	
		return EVAL_BODY_BUFFERED;
	}
	
	//���HTML
	private String fillHTML(){
		StringBuffer buff=new StringBuffer();
		
		
		Set keySet=this.map.keySet();
		Iterator itKey=keySet.iterator();
		while(itKey.hasNext()){
			String keyValue=(String)itKey.next();
			String entryValue=(String)map.get(keyValue);
			
			if(keyValue==null)
				keyValue="";
			if(entryValue==null)
				entryValue="";
			
			//�õ�DT(<tr>)ͷ
			buff.append(getDTHead());
			
			//����ؼ�
			buff.append("<td align=\""+controlAlign +"\" ");
			if(!(controlStyle==null || "".equals(controlStyle)))
				buff.append(" class=\"" + controlStyle + "\" ");
			buff.append(">");
			buff.append("<input type=\"checkbox\" name=\"" + controlName + "\" value=\"" + keyValue + "\" " );
			if(set.contains(keyValue))
				buff.append(" checked ");
			buff.append("></td>");
			//����ؼ���
			buff.append("<td align=\""+worldAlign +"\" ");
			if(!(worldStyle==null || "".equals(worldStyle)))
				buff.append(" class=\"" + worldStyle + "\" ");
			buff.append(">");
			buff.append(keyValue);
			buff.append("</td>");
			//����������������������Ϊ: controlName_Value(controlNameΪ��̬��)
			buff.append("<td align=\""+worldAlign +"\" ");
			if(!(worldStyle==null || "".equals(worldStyle)))
				buff.append(" class=\"" + worldStyle + "\" ");
			buff.append(">");
			buff.append("<input type=\"hidden\" name=\"" + controlName +"_Value\"  value=\"" + entryValue +"\" > ");
			buff.append(entryValue);
			buff.append("</td>");
			
			buff.append("</tr>");
		}
		return buff.toString();
	}
	
	//�õ�<tr>ͷ
	private String getDTHead(){
		String result="";
		
		//���style1��style2��overStyle����Ϊ��
		if((!(style1==null || "".equals(style1))) && (!(style2==null || "".equals(style2))) && (!(overStyle==null || "".equals(overStyle)))){
			curNO++;
			if(curNO%2==0)
				result ="<tr class=\""+ style2 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style2+"' \" >";
			else
				result ="<tr class=\""+ style1 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style1+"' \" >";
			
			return result;
		}
		//���style1��style2��ȫΪ�գ�����ͬʱ��ֵ��overStyle��Ϊ��
		else if((!(style1==null || "".equals(style1))) || (!(style2==null || "".equals(style2))) && (!(overStyle==null || "".equals(overStyle)))){
			curNO++;
			if(!(style1==null || "".equals(style1)))
				result ="<tr class=\""+ style1 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style1+"' \" >";
			else
				result ="<tr class=\""+ style2 +"\" onmouseover=\"this.className='" + overStyle+ "'\" onmouseout=\"this.className='"+style2+"' \" >";
			
			return result;
		}
		//���style1��style2��Ϊ��
		else if((!(style1==null || "".equals(style1))) && (!(style2==null || "".equals(style2)))){
			curNO++;
			if (curNO % 2 ==0)
				result ="<tr class=\""+ style2 +"\" >";
			else
				result ="<tr class=\""+ style1 +"\" >";
			
			return result;
		}
		//���style1��style2��ȫΪ��
		else if((!(style1==null || "".equals(style1))) || (!(style2==null || "".equals(style2)))){
			curNO++;
			if(!(style1==null || "".equals(style1)))
				result=result ="<tr class=\""+ style1 +"\" >";
			else
				result ="<tr class=\""+ style2 +"\" >";
			
			return result;
		}
		//����
		else{
			curNO++;
			result ="<tr>";
			return result;
		}
	}
	
	public int doEndTag() throws JspException {
        WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doAfterBody ...");
        bodyContent =getBodyContent();
        if (bodyContent != null)
		{
        	 try {
	            bodyContent.writeOut(bodyContent.getEnclosingWriter());		        
	         } catch(Exception e){
	        		WebPrint.PrintErrorInfo(this.getClass().getName(), "JspWriter error:"+e.getMessage());
		     }         	
	         bodyContent.clearBody();
		}
		return (SKIP_BODY);
	}
	
	
	public void release(){		
		controlName="";
		controlAlign="";
		controlStyle="";
		worldStyle="";
		worldAlign="";
	    style1 ="";
	    style2 ="";
	    overStyle ="";
	    mapName="";
	    setName="";
	    match=""; 
	    curNO=0;
	    map=null;
	    set=null;
	}
	
	
	
	public String getControlAlign() {
		return controlAlign;
	}

	public void setControlAlign(String controlAlign) {
		this.controlAlign = controlAlign;
	}

	public String getControlStyle() {
		return controlStyle;
	}

	public void setControlStyle(String controlStyle) {
		this.controlStyle = controlStyle;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getOverStyle() {
		return overStyle;
	}

	public void setOverStyle(String overStyle) {
		this.overStyle = overStyle;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getStyle1() {
		return style1;
	}

	public void setStyle1(String style1) {
		this.style1 = style1;
	}

	public String getStyle2() {
		return style2;
	}

	public void setStyle2(String style2) {
		this.style2 = style2;
	}

	public String getWorldAlign() {
		return worldAlign;
	}

	public void setWorldAlign(String worldAlign) {
		this.worldAlign = worldAlign;
	}

	public String getWorldStyle() {
		return worldStyle;
	}

	public void setWorldStyle(String worldStyle) {
		this.worldStyle = worldStyle;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
