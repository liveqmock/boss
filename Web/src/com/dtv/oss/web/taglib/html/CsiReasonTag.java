package com.dtv.oss.web.taglib.html;

/*
 * author :david.yang
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.dto.CsiActionReasonDetailDTO;
import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.CommonUtils;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

public class CsiReasonTag extends BaseHandlerTag {
    //	��������(����)
	private String csiType;
    //	����action(����)
	private String action;
    //	��ʾ��ʽ(text/label)(����)
	private String showType;
    //	ѡ����name (����)
	private String name ;
    //  ƥ��ֵ
    protected String match = null;
    // ��Ҫ����javascript
	private String checkScricptName="";
	//  ���û�����ã�ǿ����ʾ�յ�TD 
	private String forceDisplayTD ="false";
	//  �ؼ���ռ�ĸ���
	private String tdControlColspan ;
    //	TD�Ŀ��
	private String tdWidth1;
	private String tdWidth2;
    //	TD�ĸ߶�
	private String tdHeight;
    //	������ҳ����ʾ����ʽ
	private String wordStyle;
    //	�ؼ���ҳ����ʾ����ʽ
	private String controlStyle;
    //	�ؼ���С. Ĭ��25
	private String controlSize;
    //	���������ʽ
	private String tdWordStyle;
    //	���ؼ���ʽ
	private String tdControlStyle;
    //	���ִ��ڱ���λ��,Ĭ��Ϊright
	private String wordAlign ="right";
    //	�ؼ����ڱ���λ��,Ĭ��Ϊleft
	private String controlAlign ="left";

	public int doStartTag() throws JspException{
		//�������ĺϷ���
		if(!(CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(showType)||
				CommonKeys.SERVEY_SHOW_TYPE_LABEL.equalsIgnoreCase(showType))){
			WebPrint.PrintErrorInfo(this.getClass().getName(), "������ʾ����Ŀǰ��֧�֣�");
			return SKIP_BODY;
		}
		
		//�õ�����ԭ�������(map��CSIActionReasonSettingDTO--->Collection)
		Map csiReasonMap =Postern.getCsiReasonBycsitypeAndaction(csiType,action);
		if (csiReasonMap ==null || csiReasonMap.size() ==0){
			if (forceDisplayTD.equalsIgnoreCase("true")){
			   ResponseUtils.write(pageContext,getHtmlTD("",1,this.tdWidth1));
			   ResponseUtils.write(pageContext,getHtmlTD("",2,this.tdWidth2));
			   if (!(checkScricptName.equalsIgnoreCase("")) 
				  && CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(showType)){
				    String jsResult=  fillingJS("Y","");
				    ResponseUtils.write(pageContext,jsResult);
			   }		   
			}
			return (SKIP_BODY);
		}
		String htmlResult= fillingHtmlRow(csiReasonMap,showType);
		
		ResponseUtils.write(pageContext,htmlResult);	
			
		if (!(checkScricptName.equalsIgnoreCase("")) 
				&& CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(showType)){
		   
		   Iterator csiResionIter = csiReasonMap.entrySet().iterator();
		   Map.Entry item = (Map.Entry) csiResionIter.next();
		   CsiActionReasonSettingDTO csiActionReasonSettingDto =(CsiActionReasonSettingDTO)item.getKey();
		   String jsResult=  fillingJS(csiActionReasonSettingDto.getCanEmptyFlag(),csiActionReasonSettingDto.getDisplayName());
		   ResponseUtils.write(pageContext,jsResult);
		}
		
		return (SKIP_BODY);
	}

	private String fillingHtmlRow(Map csiReasonMap,String showType)  throws JspException{
		StringBuffer resultBuff=new StringBuffer();
		//ȡ�Կؼ���������ϢDisplayName
		Iterator csiResionIter = csiReasonMap.entrySet().iterator();
		Map.Entry item = (Map.Entry) csiResionIter.next();
		CsiActionReasonSettingDTO dto =(CsiActionReasonSettingDTO)item.getKey();
        //	���������TD
		String description =dto.getDisplayName();
		if ("N".equals(dto.getCanEmptyFlag()) )
			description =description +"*";
			
		resultBuff.append(getHtmlTD(description,1,this.tdWidth1));
		
		Collection  csiReasonDetailCols =(Collection)item.getValue();
		//Label��ʽ
		if(CommonKeys.SERVEY_SHOW_TYPE_LABEL.equalsIgnoreCase(this.showType)){
			String displayValue =fillingHtmlLabelValue(csiReasonDetailCols);
			resultBuff.append(getHtmlTD(displayValue,2,this.tdWidth2));
		}
		//TEXT��ʽ
		else if(CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(this.showType)){
		    String	controlHtml=getControlHtmlText(csiReasonDetailCols);
		    resultBuff.append(getHtmlTD(controlHtml,2,this.tdWidth2));
		}
		return resultBuff.toString();
	}
	
	
	private String fillingHtmlLabelValue(Collection csiReasonDetailCols){
		String result="";
		Iterator  csiReasonDetailIter =csiReasonDetailCols.iterator();
		while (csiReasonDetailIter.hasNext()){
			CsiActionReasonDetailDTO dto=(CsiActionReasonDetailDTO)csiReasonDetailIter.next();
			String curKey =dto.getKey();
			String curValue =dto.getValue();
			if(curKey==null || "".equals(curKey) || curValue==null || "".equals(curValue)){
				WebPrint.PrintTagDebugInfo(this.getClass().getName(),"����ԭ��ѡ��Ϊ�գ�");
				return "";
			}
			if (curKey.equals(match)){
				result =curValue;
				break;
			}
		}
		return result;
	}
	
	private String getControlHtmlText(Collection csiReasonDetailCols) 
	  throws JspException{
		StringBuffer resultBuff=new StringBuffer();
		int longestWord=0;
		resultBuff.append("<select name="+ name);
		if(!(this.controlStyle==null || "".equals(this.controlStyle)))
			setStyleClass(controlStyle);
		
		resultBuff.append(prepareEventHandlers());
		resultBuff.append(prepareStyles());
		resultBuff.append(">");
		resultBuff.append("<option value=>begin_of_control</option>");
		boolean matchFlag =false;
		Iterator  csiReasonDetailIter =csiReasonDetailCols.iterator();
		while (csiReasonDetailIter.hasNext()){
			CsiActionReasonDetailDTO dto=(CsiActionReasonDetailDTO)csiReasonDetailIter.next();
			String curKey =dto.getKey();
			String curValue =dto.getValue();
			
			if(curKey==null || "".equals(curKey) || curValue==null || "".equals(curValue)){
				WebPrint.PrintTagDebugInfo(this.getClass().getName(),"����ԭ��ѡ��Ϊ�գ�");
				return "";
			}

			if(curValue.length()>longestWord)
				longestWord=curValue.length();
	
			if(curKey.equals(match)){
				resultBuff.append("<option value=");
				resultBuff.append(curKey);
				resultBuff.append(" selected>");
				resultBuff.append(curValue+"</option>");
				matchFlag =true;
			}
			else if(matchFlag ==false 
					&& dto.getDefaultValueFlag().equalsIgnoreCase("Y")){ 
				resultBuff.append("<option value=");
				resultBuff.append(curKey);
				resultBuff.append(" selected>");
				resultBuff.append(curValue+"</option>");
			}
			else{
				resultBuff.append("<option value=");
				resultBuff.append(curKey);
				resultBuff.append(">");
				resultBuff.append(curValue+"</option>");
			}					
		}
		
        // ���ɽ�β
		resultBuff.append(" </select>");
		
		//�滻��begin_of_control�ɡ�----��ѡ��-----����ʽ
		String sign="";
		String replace="";
		
		if(WebUtil.StringToInt(controlSize)>longestWord){
			longestWord=WebUtil.StringToInt(this.controlSize);
		}
		
		longestWord--;
		longestWord--;
		
		if(longestWord>0){
			for(int m=0;m<longestWord/2;m++)
				sign=sign+"-";
		}
		replace=sign + replace + sign;
		if(longestWord>0 && longestWord%2 !=0)
			replace=replace + "-";
		int start=resultBuff.indexOf("begin_of_control");
		resultBuff.replace(start,start + "begin_of_control".length(), replace);
		return resultBuff.toString();
	}
	
	/**
	 * ������Ϊtextʱ������td����
	 * @param str
	 * @param type : 1Ϊ���֣�2Ϊ�ؼ�
	 * @return
	 */
	private String getHtmlTD(String str, int type,String td_width){
		if(str==null)
			str="";
		
		StringBuffer htmlTDBuff=new StringBuffer();
		htmlTDBuff.append("<td");

		//��Ӹ߶���
		if(!(this.tdHeight==null || "".equals(this.tdHeight)))
			htmlTDBuff.append(" height=" + this.tdHeight);
		//��ӿ����
		if (!(td_width ==null || "".equals(td_width)))
			htmlTDBuff.append(" width=" + td_width);
		
		if(type==1){
			//���align��
			htmlTDBuff.append("  align=" + this.wordAlign);
			//�������TD��ʽ��
			if(!(this.tdWordStyle==null || "".equals(this.tdWordStyle)))
				htmlTDBuff.append(" class=" + this.tdWordStyle);
			
			htmlTDBuff.append(" >");
            // �����������
			if (!(this.wordStyle ==null || "".equals(this.wordStyle)))
				htmlTDBuff.append("<span class=" + this.wordStyle + ">" + str + "</span></td>");
			else
				htmlTDBuff.append(str + "</td>");
		}
		else {
			//���align��
			htmlTDBuff.append("  align=" + this.controlAlign);
			//��ӿؼ�TD��ʽ��
			if(!(this.tdControlStyle==null || "".equals(this.tdControlStyle)))
				htmlTDBuff.append(" class=" + this.tdControlStyle);
			//��ӿؼ�TD��ռ�ĸ���
			if(!(this.tdControlColspan==null || "".equals(this.tdControlColspan)))
			htmlTDBuff.append(" colspan=" + this.tdControlColspan);			
			
			htmlTDBuff.append(" >" + str + "</td>");	
		}
		
		return htmlTDBuff.toString();
	}
	
	
	private String fillingJS(String canEmptFlag,String description){
		String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		scripts +=        "   function "+checkScricptName+" { \r\n"
		        +         "      description ='"+description+"'; \r\n"
		        +         "      checkInfo =document.all("+"\""+ name+"\"); \r\n";
		if ("N".equals(canEmptFlag)){
		  scripts =scripts +"       if (check_Blank(checkInfo, true, description)) \r\n"
	    	               +"          return false; \r\n" ;      
		}
		scripts =scripts +"      return true; \r\n" ;   
		scripts =scripts +"   } \r\n";
		scripts =scripts +"</script>\r\n";
		return scripts;
	}
	
	
	public void release(){
		this.csiType =null;
		this.action =null;
		this.showType=null;
		this.checkScricptName =null;
		this.name =null;
		//this.defaultFlag =null;
		this.tdWidth1=null;
		this.tdWidth2=null;
		this.tdHeight=null;
		this.wordStyle=null;
		this.controlStyle=null;
		this.controlSize=null;
		this.tdWordStyle=null;
		this.tdControlStyle=null;
		this.wordAlign=null;
		this.controlAlign=null;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getForceDisplayTD() {
		return forceDisplayTD;
	}
	public void setForceDisplayTD(String forceDisplayTD) {
		this.forceDisplayTD = forceDisplayTD;
	}
	
	public String getTdControlColspan() {
		return tdControlColspan;
	}
	public void setTdControlColspan(String tdControlColspan) {
		this.tdControlColspan = tdControlColspan;
	}

	public String getCheckScricptName() {
		return checkScricptName;
	}
	public void setCheckScricptName(String checkScricptName) {
		this.checkScricptName = checkScricptName;
	}

	public String getControlAlign() {
		return controlAlign;
	}
	public void setControlAlign(String controlAlign) {
		this.controlAlign = controlAlign;
	}

	public String getControlSize() {
		return controlSize;
	}
	public void setControlSize(String controlSize) {
		this.controlSize = controlSize;
	}

	public String getControlStyle() {
		return controlStyle;
	}
	public void setControlStyle(String controlStyle) {
		this.controlStyle = controlStyle;
	}

	public String getCsiType() {
		return csiType;
	}
	public void setCsiType(String csiType) {
		this.csiType = csiType;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        if(match!=null){
            this.match = CommonUtils.GetBeanPropertyReturnString(pageContext,match);
            if(this.match==null){
                this.match = pageContext.getRequest().getParameter(match);
            }

            if(this.match==null){
                this.match = match;
            }
        }
    }
    
	public String getShowType() {
		return showType;
	}
    public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getTdControlStyle() {
		return tdControlStyle;
	}
	public void setTdControlStyle(String tdControlStyle) {
		this.tdControlStyle = tdControlStyle;
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

	public String getTdWordStyle() {
		return tdWordStyle;
	}
	public void setTdWordStyle(String tdWordStyle) {
		this.tdWordStyle = tdWordStyle;
	}

	public String getWordAlign() {
		return wordAlign;
	}
	public void setWordAlign(String wordAlign) {
		this.wordAlign = wordAlign;
	}

	public String getWordStyle() {
		return wordStyle;
	}
	public void setWordStyle(String wordStyle) {
		this.wordStyle = wordStyle;
	}
	
	
}
