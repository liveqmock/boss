package com.dtv.oss.web.taglib.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.dtv.oss.util.Constant;
import com.dtv.oss.util.DynamicServeyDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CommonSettingValue;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.util.WebUtil;

/**
 * ��̬����ؼ���
 * �ؼ����Ʒ�װ��ʽ��txtDynamicServey_�����ID
 * author     ��zhouxushun 
 * date       : 2005-12-7
 * description:
 * @author 250713z
 *
 */
public class DynamicServeyTag extends BaseHandlerTag {

	//��������
	private String serveyType;
	//����������
	private String serveySubType;
	//ҵ������ID
	private String serviceID;
	//ƥ�����
	private String match;
	//��ʾ��ʽ��
	private String showType;
	//������ҳ����ʾ����ʽ
	private String wordStyle;
	//�ؼ���ҳ����ʾ����ʽ
	private String controlStyle;
	//�ؼ���С
	private String controlSize;
	//���������ʽ
	private String tdWordStyle;
	//���ؼ���ʽ
	private String tdControlStyle;
	//ÿ����ʾ���������
	private String showRowNO;
	//ҳ��ÿ����ʾ������
	private String pageRowNO;
	//TD�Ŀ��
	private String tdWidth1;
	private String tdWidth2;
	
	//TD�ĸ߶�
	private String tdHeight;
	//���ִ��ڱ���λ��
	private String wordAlign;
	//�ؼ����ڱ���λ��
	private String controlAlign;
	//�ؼ�����
	private String serveyName;
	
	
	//��ǰ��¼������,TAG�ڲ�ʹ��
	private int curRowNo;
	private int curPageNo;
	//pageContext����
	Collection serveyList=null;
	private Map resultMap;
	private Map compareMap;
	//��̬������
	//�ؼ���ʾ�ĳ���
	private static int size=25;
	//ҳ�沼����ÿ����ʾ����ĸ���
	private static int rowNO=2;
	//���ִ��ڱ���λ��
	private static String inWordAlign="right";
	//�ؼ����ڱ���λ��
	private static String inControlAlign="left";
	//�ؼ���
	private static String inServeyName="txtDynamicServey";
	
    private String defaultFlag ="false";
    
    private String checkScricptName ="";
    //�ȽϵĶ���,��matchһ����.
    private String compareTo;
    
    boolean check_ScriptFlag=false;
	
	public int doStartTag() throws JspException{
		
		String serveyIDs="";
		String serviceIDsName="";
		String serveyTypes="";
		
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"invoke doStartTag...");
		
		if(serveyType==null || "".equals(serveyType)){
			WebPrint.PrintErrorInfo(this.getClass().getName(), "���󣺵�������δ֪��");
			return SKIP_BODY;
		}	
		if(!(CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(showType)||
				CommonKeys.SERVEY_SHOW_TYPE_LABEL.equalsIgnoreCase(showType)||
				CommonKeys.SERVEY_SHOW_TYPE_HIDE.equalsIgnoreCase(showType))){
			WebPrint.PrintErrorInfo(this.getClass().getName(), "������ʾ����Ŀǰ��֧�֣�");
			return SKIP_BODY;
		}
		//Ĭ��ÿ����ʾ2������
		curRowNo=WebUtil.StringToInt(showRowNO);
		if(curRowNo==0)
			curRowNo=rowNO;
		curPageNo=WebUtil.StringToInt(pageRowNO);
		if(curPageNo==0)
			curPageNo=curRowNo;
		//ҳ����ʾ��Ԫ�ر���Ϊ��ǰ�ؼ�����������ҳ��Ԫ��Ϊ��<td>����</td><td>ֵ</td>��
		if(curPageNo%curRowNo!=0){
			WebPrint.PrintErrorInfo(this.getClass().getName(), "����ҳ�沼��ʧ�ܣ��޷����пؼ���");
			return SKIP_BODY;
		}
		if(controlSize==null || "".equals(controlSize) || WebUtil.StringToInt(this.controlSize)==0){
			controlSize="" + size;
		}
		
		if(serveyName==null || "".equals(serveyName))
			serveyName=inServeyName;
		serviceIDsName=serveyName;
		serveyName=serveyName + "_";	
		
	    check_ScriptFlag = !checkScricptName.equalsIgnoreCase("");
		serveyList=Postern.getServeyListByServeyType(serveyType,serveySubType,serviceID);
        //�õ��ű�
		if (check_ScriptFlag){
		   String jsResult= fillingJS(serveyList,serveyName,checkScricptName);
		   ResponseUtils.write(pageContext,jsResult);
		}
		if(serveyList==null || serveyList.size()==0){
			WebPrint.PrintErrorInfo(this.getClass().getName(), "û����Ӧ�ĵ��飡");
			return SKIP_BODY;
		}
		
		if(match!=null && (!"".equals(match)))
			resultMap=(Map)pageContext.getAttribute(match);	
		if(compareTo!=null && (!"".equals(compareTo)))
			compareMap=(Map)pageContext.getAttribute(compareTo);	
		
		if(controlAlign==null || "".equals(controlAlign))
			controlAlign=inControlAlign;
		if(wordAlign==null || "".equals(wordAlign))
			wordAlign=inWordAlign;

		String htmlResult="";
		Iterator itServeyList=serveyList.iterator();
		while(itServeyList.hasNext()){
			
			Collection rowServeyList=new ArrayList();
			//�������
			for(int i=0;i<curRowNo;i++){
				if(itServeyList.hasNext()){
					DynamicServeyDTO dto=(DynamicServeyDTO)itServeyList.next();
					rowServeyList.add(dto);
					
					if(!"".equals(serveyIDs))
						serveyIDs=serveyIDs + ";";
					serveyIDs =serveyIDs + dto.getServeyID();
					
					if (!"".equals(serveyTypes))
						serveyTypes =serveyTypes+ ";";
					serveyTypes =serveyTypes + dto.getShowType();
					if (check_ScriptFlag && dto.getForceWrite()){
						dto.setDescription(dto.getDescription()+"*");
					}
					
				}
			}	
			htmlResult=htmlResult + fillingHtmlRow(rowServeyList);
		}
		
		String hiddenServeyIDs="<input type=hidden name=" + serviceIDsName + "  value="+ serveyIDs + ">";
		String hiddenServeyTypes ="<input type=hidden name=" + serviceIDsName +"_ValueType value="+serveyTypes +">";
		
		WebPrint.PrintTagDebugInfo(this.getClass().getName(),"���ؽ��Ϊ��" +  htmlResult);
		ResponseUtils.write(pageContext,htmlResult);	
		ResponseUtils.write(pageContext,hiddenServeyIDs);
		ResponseUtils.write(pageContext,hiddenServeyTypes);
		return EVAL_BODY_BUFFERED;
	}
	
	//�����������JS�ű�����ʱû�д���
	private String fillingJS(Collection serveyDTOList, String servey_Name,
			String checkScricptName) {
		String scripts = "<script language=" + "\"" + "javascript" + "\""
				+ "> \r\n";
		scripts += "  function " + checkScricptName + "{ \r\n"
				+ "     tagType_text='" + Constant.SERVEY_TAGTYPE_TEXT
				+ "'; \r\n" + "     tagType_select='"
				+ Constant.SERVEY_TAGTYPE_CHECKBOX + "'; \r\n";
		Iterator serveyIter = serveyDTOList.iterator();
		String checkScript = "";
		while (serveyIter.hasNext()) {
			DynamicServeyDTO dto = (DynamicServeyDTO) serveyIter.next();

			//���ڼ��ű�����,�����ǲ��Ǳ���,��������,����ʱ���ǿ�,���Ǳ���ʱ�ڷǿ�ʱ���������Ч��.
			if (Constant.SERVEY_TAGTYPE_DATE.equals(dto.getShowType())) {
				checkScript = checkScript
						+ "     checkInfo =document.all("
						+ "\""
						+ servey_Name
						+ dto.getServeyID()
						+ "\"); \r\n"
						+ "     description ='"
						+ dto.getDescription()
						+ "' \r\n"
						+ (dto.getForceWrite() ? "     if (check_Blank(checkInfo, true, description)) \r\n"
								+ "        return false; \r\n"
								: "     if (!check_Blank(checkInfo, false, description)) \r\n")
						+ "     if (!check_TenDate(checkInfo, true, description)) \r\n"
						+ "        return false; \r\n";
				continue;
			}

			if (dto.getForceWrite()) {
				if (Constant.SERVEY_TAGTYPE_TEXT.equals(dto.getShowType())
						|| Constant.SERVEY_TAGTYPE_SELECT.equals(dto
								.getShowType())) {
					checkScript = checkScript
							+ "     checkInfo =document.all("
							+ "\""
							+ servey_Name
							+ dto.getServeyID()
							+ "\"); \r\n"
							+ "     description ='"
							+ dto.getDescription()
							+ "' \r\n"
							+ "     if (check_Blank(checkInfo, true, description)) \r\n"
							+ "        return false; \r\n";
				} else {
					checkScript = checkScript + "    checkedFlag =false; \r\n"
							+ "    checkInfo =document.getElementsByName("
							+ "\"" + servey_Name + dto.getServeyID()
							+ "\"); \r\n"
							+ "    for (i=0; i<checkInfo.length;i++){ \r\n"
							+ "      if (checkInfo[i].checked){ \r\n"
							+ "         checkedFlag =true; \r\n"
							+ "      } \r\n" + "    } \r\n"
							+ "    if (!checkedFlag){ \r\n" + "       alert("
							+ "\"" + dto.getDescription() + "����Ϊ�գ�" + "\""
							+ "); \r\n" + "       return false; \r\n"
							+ "    } \r\n";
				}

			}
		}
		scripts = scripts + checkScript;
		scripts = scripts + "     return true; \r\n";
		scripts = scripts + "   } \r\n";
		scripts = scripts + "</script> \r\n";
		return scripts;
	}
	
	/**
	 * �õ�HTMLһ�еĴ���
	 * 
	 * @param dtoList
	 * @return
	 */
	private String fillingHtmlRow(Collection dtoList){
		String result="";
		
		// ����
		if(CommonKeys.SERVEY_SHOW_TYPE_HIDE.equalsIgnoreCase(this.showType)){
			result=fillingHtmlRowHide(dtoList);
		}
		// Label��ʾ��ʽ�� ���� + ����
		else if(CommonKeys.SERVEY_SHOW_TYPE_LABEL.equalsIgnoreCase(this.showType))
		{
			result=fillingHtmlRowHide(dtoList);
			result=result + fillingHtmlRowLabel(dtoList);
		}
		//TEXT��ʽ
		else if(CommonKeys.SERVEY_SHOW_TYPE_TEXT.equalsIgnoreCase(this.showType))
		{
			result=fillingHtmlRowText(dtoList);
		}
		return result;
	}
	
	/**
	 * ���һ�е�Label
	 * @param dtoList
	 * @return
	 */
	private String fillingHtmlRowLabel(Collection dtoList){
		StringBuffer htmlRowBuff=new StringBuffer();
		htmlRowBuff.append("<tr>");
		
		int thisRowNO=dtoList.size();
		//һ��<td>Ԫ����һ��<tr>Ԫ�صĿ��
		int colspan=this.curPageNo/this.curRowNo;
		
		Iterator itDtoList=dtoList.iterator();
		//�������������е�DTO
		while(itDtoList.hasNext()){
			DynamicServeyDTO dto=(DynamicServeyDTO)itDtoList.next();	
			
			//���ؼ�����ı���WEB�е�TD
			String textToLabel="";
			//���ȴ�request��ȡ�������
			textToLabel=Postern.getServeyTitleBySettingIDAndCode(dto.getServeyID(),
					pageContext.getRequest().getParameterValues(this.serveyName + dto.getServeyID()));
			//���request��û������������ٴ�pagecontext��ȡ
			if(textToLabel==null || "".equals(textToLabel)){
				if(this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
					textToLabel=Postern.getServeyTitleBySettingIDAndCode(dto.getServeyID(),
							this.resultMap.get(new Integer(dto.getServeyID())).toString().split("-"));
				}
			}
			System.out.println("compareMap:"+compareMap);
			boolean isColoration=false;
			if(compareMap!=null&&!compareMap.isEmpty()){
				Object compareObj=this.compareMap.get(new Integer(dto.getServeyID()));
				String compareValue=null;
				if(compareObj!=null){
					compareValue=Postern.getServeyTitleBySettingIDAndCode(dto.getServeyID(),
							compareObj.toString().split("-"));
				}
				System.out.println("compareValue:"+compareValue);
				System.out.println("textToLabel:"+textToLabel);
				if((compareValue==null&&textToLabel!=null)||(compareValue!=null&&!compareValue.equals(textToLabel))){
					isColoration=true;
				}
			}
			System.out.println("isColoration:"+isColoration);
			//���������TD
			htmlRowBuff.append(getHtmlTD(dto.getDescription(),1,this.tdWidth1,isColoration));
			//�������
			htmlRowBuff.append(getHtmlTD(textToLabel,2,this.tdWidth2,isColoration));
		}
		
		//���ʣ�µĿ�
		if(this.curRowNo-thisRowNO>0){
			int tdFlag=1;
			for(int n=0;n<(this.curRowNo-thisRowNO)*2;n++){
				htmlRowBuff.append("<td");
				if(tdFlag==1){
					htmlRowBuff.append("  class=" + this.tdWordStyle);
					if (this.tdWidth1 !=null && !this.tdWidth1.equals("")) 
						htmlRowBuff.append(" width="+this.tdWidth1);
				}
				else{
					htmlRowBuff.append("  class=" + this.tdControlStyle);
					if (this.tdWidth2 !=null && !this.tdWidth2.equals("")) 
						htmlRowBuff.append(" width="+this.tdWidth2);
				}
				tdFlag=tdFlag*(-1);
				if(colspan>1)
					htmlRowBuff.append("  colspan="+colspan);
				htmlRowBuff.append(">&nbsp;</td>");
			}
		}
		htmlRowBuff.append("</tr>");
		return htmlRowBuff.toString();
	}
	
	/**
	 * ���һ�е����ش���
	 * @param dtoList
	 * @return
	 */
	private String fillingHtmlRowHide(Collection dtoList){
		StringBuffer htmlRowBuff=new StringBuffer();
		
		Iterator itDtoList=dtoList.iterator();
		//�������������е�DTO
		while(itDtoList.hasNext()){
			DynamicServeyDTO dto=(DynamicServeyDTO)itDtoList.next();	
			String[] textToHiden;
			textToHiden=pageContext.getRequest().getParameterValues(this.serveyName + dto.getServeyID());
			if (textToHiden !=null){
			    for (int i=0 ;i <textToHiden.length; i++){
				   htmlRowBuff.append("<input name=" + this.serveyName + dto.getServeyID()+ "  type=hidden value=");
				   htmlRowBuff.append(textToHiden[i] + ">\r\n");
			    }	
			}
		}
		return htmlRowBuff.toString();
	}
	
	
	//�����������HTML��Text����--��TRΪ��λ���
	private String fillingHtmlRowText(Collection dtoList){
		StringBuffer htmlRowBuff=new StringBuffer();
		htmlRowBuff.append("<tr>");
		
		int thisRowNO=dtoList.size();
		//һ��<td>Ԫ����һ��<tr>Ԫ�صĿ��
		int colspan=this.curPageNo/this.curRowNo;
		
		Iterator itDtoList=dtoList.iterator();
		//�������������е�DTO
		while(itDtoList.hasNext()){
			DynamicServeyDTO dto=(DynamicServeyDTO)itDtoList.next();	
			
			//���������TD
			htmlRowBuff.append(getHtmlTD(dto.getDescription(),1,this.tdWidth1));
			//���ؼ���TD
			htmlRowBuff.append(getHtmlTD(getControlHtmlText(dto),2,this.tdWidth2));
		}
		
		//���ʣ�µĿ�
		if(this.curRowNo-thisRowNO>0){
			int tdFlag=1;
			for(int n=0;n<(this.curRowNo-thisRowNO)*2;n++){
				htmlRowBuff.append("<td");
				if(tdFlag==1){
					htmlRowBuff.append(" class=" + this.tdWordStyle );
					if (this.tdWidth1 !=null && !this.tdWidth1.equals("")) 
						htmlRowBuff.append(" width="+this.tdWidth1);
				}
				else{
					htmlRowBuff.append(" class=" + this.tdControlStyle);
					if (this.tdWidth2 !=null && !this.tdWidth2.equals("")) 
						htmlRowBuff.append(" width="+this.tdWidth2);
				}
				if(colspan>1)
					htmlRowBuff.append("  colspan="+colspan);
				htmlRowBuff.append(">&nbsp;</td>");
				tdFlag=tdFlag*(-1);
			}
		}
		htmlRowBuff.append("</tr>");
		return htmlRowBuff.toString();
	}
	
	private String getControlHtmlText(DynamicServeyDTO dto){
		StringBuffer resultBuff=new StringBuffer();
		
		int longestWord=0;
		
		//�������ʽ
		if(Constant.SERVEY_TAGTYPE_SELECT.equals(dto.getShowType())){			
			resultBuff.append("<select name="+ this.serveyName + dto.getServeyID());
			
			if(!(this.controlSize==null || "".equals(this.controlSize))){
				//resultBuff.append(" size=" + this.controlSize);
				resultBuff.append(" size=1");
			}
			if(!(this.controlStyle==null || "".equals(this.controlStyle)))
				resultBuff.append(" class=" + this.controlStyle);
			
			resultBuff.append(">");
			
			resultBuff.append("<option value=>begin_of_control</option>");
			
            String getKey=pageContext.getRequest().getParameter(this.serveyName  + dto.getServeyID());
			
			if(getKey ==null && this.serveyList!=null && this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
				getKey=this.resultMap.get(new Integer(dto.getServeyID())).toString();
			}
			Iterator itKeys=dto.getKeysIterator();
			while(itKeys.hasNext()){
				
				String curKey=(String)itKeys.next();
				CommonSettingValue curValue=(CommonSettingValue)dto.getValue(curKey);
				
				if(curKey==null || "".equals(curKey) || curValue==null || "".equals(curValue)){
					WebPrint.PrintTagDebugInfo(this.getClass().getName(),"����ѡ��Ϊ��");
					return "";
				}
				//curValue.getValue() ----Description
				if(curValue.getValue().length()>longestWord)
					longestWord=curValue.getValue().length();
		
				if(curKey.equals(getKey)){
					resultBuff.append("<option value=");
					resultBuff.append(curKey);
					resultBuff.append(" selected>");
					resultBuff.append(curValue.getValue()+"</option>");
				}
				else if(getKey==null && dto.getDefaultList().contains(curKey) && defaultFlag.equalsIgnoreCase("true")){
					resultBuff.append("<option value=");
					resultBuff.append(curKey);
					resultBuff.append(" selected>");
					resultBuff.append(curValue.getValue()+"</option>");
				}
				else{
					resultBuff.append("<option value=");
					resultBuff.append(curKey);
					resultBuff.append(">");
					resultBuff.append(curValue.getValue()+"</option>");
				}					
			}
			
			//���ɽ�β
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

		}
		//��ѡ��
		else if (Constant.SERVEY_TAGTYPE_CHECKBOX.equals(dto.getShowType())){
			String[] getKey=pageContext.getRequest().getParameterValues(this.serveyName  + dto.getServeyID());		            
            if (getKey ==null && this.serveyList!=null && this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
				getKey=this.resultMap.get(new Integer(dto.getServeyID())).toString().split("-");
			}
            Collection keyCols =new ArrayList();
            if (getKey !=null){
            	for (int i=0 ;i<getKey.length; i++){
            		keyCols.add(getKey[i]);
            	}
            }
            
			Iterator itKeys=dto.getKeysIterator();
			while(itKeys.hasNext()){
				String curKey=(String)itKeys.next();
				CommonSettingValue curValue=(CommonSettingValue)dto.getValue(curKey);
				
				if(curKey==null || "".equals(curKey) || curValue==null || "".equals(curValue)){
					WebPrint.PrintTagDebugInfo(this.getClass().getName(),"����ѡ��Ϊ��");
					return "";
				}
   		  
                if (keyCols.contains(curKey)){
					resultBuff.append("<input type='checkbox' name='"+this.serveyName +dto.getServeyID()+"' checked");
					if(!(this.controlStyle==null || "".equals(this.controlStyle)))
						resultBuff.append(" class=" + this.controlStyle);
							
				    resultBuff.append(" value="+curKey+"> "+curValue.getValue()+"<br> \r\n");
					
				}else if (dto.getDefaultList().contains(curKey) && keyCols.isEmpty() && defaultFlag.equalsIgnoreCase("true")){
					resultBuff.append("<input type='checkbox' name='"+this.serveyName +dto.getServeyID()+"' checked");
					if(!(this.controlStyle==null || "".equals(this.controlStyle)))
						resultBuff.append(" class=" + this.controlStyle);
					
					resultBuff.append(" value="+curKey+"> "+curValue.getValue()+"<br> \r\n");
				}
				else {
					resultBuff.append("<input type='checkbox' name='"+this.serveyName +dto.getServeyID()+"' ");
					if(!(this.controlStyle==null || "".equals(this.controlStyle)))
						resultBuff.append(" class=" + this.controlStyle);
					
					resultBuff.append(" value="+curKey+"> "+curValue.getValue()+"<br> \r\n");
				}
			}		
				
		}
		
		//�ı������
		else if(Constant.SERVEY_TAGTYPE_TEXT.equals(dto.getShowType())){
			resultBuff.append("<input type=text name=" + this.serveyName + dto.getServeyID() +" maxlength=500");
			if(!(this.controlSize==null || "".equals(this.controlSize)))
				resultBuff.append(" size=" + this.controlSize);
			else
				resultBuff.append(" size=" + size);
			
			if(!(this.controlStyle==null || "".equals(this.controlStyle)))
				resultBuff.append(" class=" + this.controlStyle);
			
			String getKey=pageContext.getRequest().getParameter(this.serveyName  + dto.getServeyID());
			
			if(getKey != null){
				resultBuff.append(" value=" + getKey);
			}
			else if(this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
				resultBuff.append(" value=" + (String)this.resultMap.get(new Integer(dto.getServeyID())));
			}	
			resultBuff.append(">");
		}
		//����ѡ���
		else if(Constant.SERVEY_TAGTYPE_DATE.equals(dto.getShowType())){
			resultBuff.append("<input type=text name=" + this.serveyName + dto.getServeyID() +" maxlength=20");
			resultBuff.append(" id=\"" + this.serveyName + dto.getServeyID() + "_DateId\"");
			//���ҳ��û�е���javascript���,���onchange�¼����,ֻ���Ǽ��,��alert,�޷���ֹ�ύ.
			if(!check_ScriptFlag){
				resultBuff.append(" onchange=\"if (!check_Blank(this, false, '"+dto.getDescription()+"'))");
				resultBuff.append("{check_TenDate(this, true, '"+dto.getDescription()+"')").append(";}\"");
			}
			if(!(this.controlSize==null || "".equals(this.controlSize)))
				resultBuff.append(" size=" + this.controlSize);
			else
				resultBuff.append(" size=" + size);
			
			if(!(this.controlStyle==null || "".equals(this.controlStyle)))
				resultBuff.append(" class=" + this.controlStyle);
			
			String getKey=pageContext.getRequest().getParameter(this.serveyName  + dto.getServeyID());
			
			if(getKey != null){
				resultBuff.append(" value=\"" + getKey+"\"");
			}
			else if(this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
				resultBuff.append(" value=\"" + (String)this.resultMap.get(new Integer(dto.getServeyID()))+"\"");
			}	
			resultBuff.append(" onfocus=\"viewModule(this)\" onKeydown=\"inputDate(document.getElementById('" + this.serveyName + dto.getServeyID() + "_DateId'))\" onblur=\"lostFocus(this)\"");
			resultBuff.append(">").append("\n");//getElementById
			resultBuff.append("<IMG onclick=\"MyCalendar.SetDate(this,document.getElementById('" + this.serveyName + dto.getServeyID() + "_DateId'),'Y')\" src=\"img/calendar.gif\" style=cursor:hand border=\"0\">");
			
		}		
		//��ѡ��
		else if (Constant.SERVEY_TAGTYPE_RADIO.equals(dto.getShowType())){
			String[] getKey=pageContext.getRequest().getParameterValues(this.serveyName  + dto.getServeyID());		            
            if (getKey ==null && this.serveyList!=null && this.resultMap!=null && this.resultMap.containsKey(new Integer(dto.getServeyID()))){
				getKey=this.resultMap.get(new Integer(dto.getServeyID())).toString().split("-");
			}
            Collection keyCols =new ArrayList();
            if (getKey !=null){
            	for (int i=0 ;i<getKey.length; i++){
            		keyCols.add(getKey[i]);
            	}
            }
			Iterator itKeys=dto.getKeysIterator();
            while(itKeys.hasNext()){
				
				String curKey=(String)itKeys.next();
				CommonSettingValue curValue=(CommonSettingValue)dto.getValue(curKey);
				
				if(curKey==null || "".equals(curKey) || curValue==null || "".equals(curValue)){
					WebPrint.PrintTagDebugInfo(this.getClass().getName(),"����ѡ��Ϊ��");
					return "";
				}
             				
				if (keyCols.contains(curKey)){
					resultBuff.append("<input type='radio' name='"+this.serveyName +dto.getServeyID()+"' checked");
					if(!(this.controlStyle==null || "".equals(this.controlStyle)))
						resultBuff.append(" class=" + this.controlStyle);
					 resultBuff.append(" value="+curKey+"> "+curValue.getValue()+"<br> \r\n");
				}
				else if (dto.getDefaultList().contains(curKey) && keyCols.isEmpty() && defaultFlag.equalsIgnoreCase("true")){
					resultBuff.append("<input type='radio' name='"+this.serveyName +dto.getServeyID()+"' checked");
				    if(!(this.controlStyle==null || "".equals(this.controlStyle)))
					   resultBuff.append(" class=" + this.controlStyle);
				    resultBuff.append(" value="+curKey+"> "+curValue.getValue()+"<br> \r\n");
				}
			    else {
					resultBuff.append("<input type='radio' name='"+this.serveyName +dto.getServeyID()+"' ");
					if(!(this.controlStyle==null || "".equals(this.controlStyle)))
					    resultBuff.append(" class=" + this.controlStyle);
				    resultBuff.append(" value="+curKey +" >"+curValue.getValue()+"<br>");
			    }
		   }
		}
		return resultBuff.toString();
	}
		
	/**
	 * ������Ϊtextʱ������td����
	 * @param str
	 * @param type : 1Ϊ���֣�2Ϊ�ؼ�
	 * @return
	 */
	private String getHtmlTD(String str, int type,String td_width){
		return getHtmlTD(str,type,td_width,false);
	}
	private String getHtmlTD(String str, int type,String td_width,boolean isColoration){
		if(str==null)
			str="";
		
		StringBuffer htmlTDBuff=new StringBuffer();
		htmlTDBuff.append("<td");

		//��Ӹ߶���
		if(!(this.tdHeight==null || "".equals(this.tdHeight)))
			htmlTDBuff.append(" height=" + this.tdHeight);
		//��ӿ����
	//	if(!(this.tdWith==null || "".equals(this.tdWith)))
		if (!(td_width ==null || "".equals(td_width)))
			htmlTDBuff.append(" width=" + td_width);
		
		if(type==1){
			//���align��
			htmlTDBuff.append("  align=" + this.wordAlign);
			//�������TD��ʽ��
			if(!(this.tdWordStyle==null || "".equals(this.tdWordStyle)))
				htmlTDBuff.append(" class=" + this.tdWordStyle);
			//�����������
			htmlTDBuff.append(" ><span class=" + this.wordStyle + ">");
			if(isColoration){
				htmlTDBuff.append("<font color='red'>");
			}
			htmlTDBuff.append( str  );
			if(isColoration){
				htmlTDBuff.append("</font>");
			}
			htmlTDBuff.append("</span></td>");
		}
		else
		{
			//���align��
			htmlTDBuff.append("  align=" + this.controlAlign);
			//��ӿؼ�TD��ʽ��
			if(!(this.tdControlStyle==null || "".equals(this.tdControlStyle)))
				htmlTDBuff.append(" class=" + this.tdControlStyle);
			
			htmlTDBuff.append(" >");	
			if(isColoration){
				htmlTDBuff.append("<font color='red'>");
			}			
			htmlTDBuff.append( str  );
			if(isColoration){
				htmlTDBuff.append("</font>");
			}
			htmlTDBuff.append("</td>");	
		}
		
		return htmlTDBuff.toString();
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
		this.controlSize=null;
		this.controlStyle=null;
		this.match=null;
		this.pageRowNO=null;
		this.serveyList=null;
		this.serveyType=null;
		this.showRowNO=null;
		this.showType=null;
		this.curRowNo=0;
		this.tdControlStyle=null;
		this.tdHeight=null;
		this.tdWidth1=null;
		this.tdWidth2=null;
		this.tdWordStyle=null;
		this.wordStyle=null;
		this.curPageNo=0;
		this.wordAlign=null;
		this.controlAlign=null;
		this.serveyName=null;
	}

	public String getControlSize() {
		return controlSize;
	}
	
	public String getControlAlign() {
		return controlAlign;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public void setControlAlign(String controlAlign) {
		this.controlAlign = controlAlign;
	}

	public String getServeyName() {
		return serveyName;
	}

	public void setServeyName(String serveyName) {
		this.serveyName = serveyName;
	}

	public String getWordAlign() {
		return wordAlign;
	}

	public void setWordAlign(String wordAlign) {
		this.wordAlign = wordAlign;
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

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public String getPageRowNO() {
		return pageRowNO;
	}

	public String getServeySubType() {
		return serveySubType;
	}

	public void setServeySubType(String serveySubType) {
		this.serveySubType = serveySubType;
	}

	public void setPageRowNO(String pageRowNO) {
		this.pageRowNO = pageRowNO;
	}

	public String getServeyType() {
		return serveyType;
	}

	public void setServeyType(String serveyType) {
		this.serveyType = serveyType;
	}

	public String getShowRowNO() {
		return showRowNO;
	}

	public void setShowRowNO(String showRowNO) {
		this.showRowNO = showRowNO;
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

	public String getWordStyle() {
		return wordStyle;
	}

	public void setWordStyle(String wordStyle) {
		this.wordStyle = wordStyle;
	}
	
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getCheckScricptName() {
		return checkScricptName;
	}

	public void setCheckScricptName(String checkScricptName) {
		this.checkScricptName = checkScricptName;
	}

	public String getCompareTo() {
		return compareTo;
	}

	public void setCompareTo(String compareTo) {
		this.compareTo = compareTo;
	}

    
    
	
}
