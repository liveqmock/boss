package com.dtv.oss.web.taglib.logic;

/**
 * ҳ��Ԫ�ؿɼ������ñ�ǩ,
 */
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.*;

import com.dtv.oss.dto.DisplayControlDTO;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.taglib.util.*;
import com.dtv.oss.web.util.*;

public class DisplayControlTag extends BodyTagSupport {
	private static final String operateString="not in is not is null not null uninclude UNINCLUDE and or y yes n no check NOT IN IS NOT IS NULL NOT NULL AND OR Y YES N NO CHECK";
	private String id;
	private String bean;
	private String scope;
	private Map beans;
	/**
	 * ��bean���������ַ������õ�����;
	 * @throws JspException
	 */
	private void parseBeans() throws JspException {
		if (bean == null)
			return;
		beans=new java.util.HashMap();
		String[] arrBean = bean.split(",");
		for (int i = 0; i < arrBean.length; i++) {
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "parseBeans.arrBean["+i+"]:::::"+arrBean[i]);
			Object obj=null;
			//��ʹ����session�еĿͻ������ʱ��,ȡ����һ�㲻ͬ
			if(CommonKeys.CURRENT_CUSTOMER_SESSION_NAME.equals(arrBean[i])){
				obj = CurrentCustomer.getCurrentCustomerDTO(pageContext.getSession());
			}else{
				obj = RequestUtils.lookup(pageContext, arrBean[i], scope);
			}
			if (obj == null){
				WebPrint.PrintErrorInfo(this.getClass().getName(), id+">>>>"+"���õĶ�����Ч:"+arrBean[i]);
				continue;
			}

			String className=obj.getClass().getName();
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "parseBeans.className:::::"+className);
			beans.put(className+id.toUpperCase(), obj);
		}
	}
	
	private boolean checkRule(DisplayControlDTO dc) throws JspException {
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"checkRule.DisplayControlDTO:::::"+dc);
		boolean resrult=false;
		
		String operate=dc.getOperate();
		if(operate==null){
			WebPrint.PrintErrorInfo(this.getClass().getName(), id+">>>>"+"��Ч����������,û������operate.");
			return false;
		}
		if(operateString.indexOf(operate)<0){
			WebPrint.PrintErrorInfo(this.getClass().getName(),id+">>>>"+"��Ч����������,operate:"+operate+"��Ч.");
			return false;
		}
		String className=dc.getClassName();
		Object dto=beans.get(className+id.toUpperCase());
		//����õ�������,�ҵ�ǰҪ�ѵ���customerDTO,���Դ�session�л�ȡ,����������ҳ���ϲ�������customerDTO��bean
		if(dto==null&&"com.dtv.oss.dto.CustomerDTO".equals(className)){
			dto=CurrentCustomer.getCurrentCustomerDTO(pageContext.getSession());
		}
		if(dto==null){
			WebPrint.PrintErrorInfo(this.getClass().getName(), id+">>>>"+"��Ч����������,�Ҳ���"+className+"����Ч����.");
			return false;
		}

//		WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRule.dto:::::"+dto);
		Object beanValue=null;
		String strBeanValue="";
		//��ǰ�����Ǹ�string����ʱ,ֱ����������.
		if(dto instanceof String){
			strBeanValue=(String) dto;
		//�����collection,ֻ֧�ֿ���ǿյĲ���
		}else if(dto instanceof java.util.Collection){
			java.util.Collection colValue=(java.util.Collection)dto;
			if(operate.equalsIgnoreCase("is null")){
				resrult=colValue==null||colValue.isEmpty()?true:false;
			}else if(operate.equalsIgnoreCase("not null")){
				resrult=colValue==null||colValue.isEmpty()?false:true;
			}
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRule.colValue:::::"+colValue);
			return resrult;
		}else if(dto instanceof java.util.Map){
			java.util.Map colValue=(java.util.Map)dto;
			if(operate.equalsIgnoreCase("is null")){
				resrult=colValue==null||colValue.isEmpty()?true:false;
			}else if(operate.equalsIgnoreCase("not null")){
				resrult=colValue==null||colValue.isEmpty()?false:true;
			}
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRule.colValue:::::"+colValue);
			return resrult;
		}else{
			String fieldName=dc.getFieldName();
			
			beanValue = WebUtil.excuteMethod(dto,
	                WebUtil.Name2GetMethod(fieldName));
			
			if(beanValue!=null){
				strBeanValue=beanValue.toString().trim();
//				WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRule.beanValue:::::"+beanValue+"\nheckRule.beanValue.getClass:::::"+beanValue.getClass());
			}
		}
		String ruleValue = dc.getValue();
//		WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRule.strBeanValue:::::"+strBeanValue);


		if(operate.equalsIgnoreCase("not in")){
			resrult=!operateInclude(ruleValue,strBeanValue);
		}else if(operate.equalsIgnoreCase("in")){
			resrult=operateInclude(ruleValue,strBeanValue);
		}else if(operate.equalsIgnoreCase("is")){
			resrult=ruleValue.equalsIgnoreCase(strBeanValue);
		}else if(operate.equalsIgnoreCase("not")){
			resrult=!ruleValue.equalsIgnoreCase(strBeanValue);
		}else if(operate.equalsIgnoreCase("is null")){
			resrult=strBeanValue==null||strBeanValue.equals("")?true:false;
		}else if(operate.equalsIgnoreCase("not null")){
			resrult=strBeanValue==null||strBeanValue.equals("")?false:true;
		}else if(operate.equalsIgnoreCase("include")){
			resrult=operateInclude(strBeanValue,ruleValue);
		}else if(operate.equalsIgnoreCase("uninclude")){
			resrult=!operateInclude(strBeanValue,ruleValue);
		}
		
		return resrult;
	}
	/**
	 * para1�����Ǹ�����,����,�ŷָ�
	 * @param para1
	 * @param para2
	 * @return
	 */
	private boolean operateInclude(String para1,String para2){
		boolean resrult=false;
		String[] col=para1.split(",");
		for(int i=0;i<col.length;i++){
			if(col[i]!=null&&!col[i].equals("")&&col[i].equals(para2)){
				return true;
			}
		}
		return resrult;
	}
	/**
	 * ���÷���,
	 * @param operate
	 * @param para1
	 * @param para2
	 * @return
	 */
	private boolean operatedouble(String operate,String para1,String para2){
		boolean resrult=false;
		
		double f1=Double.valueOf(para1).doubleValue();
		double f2=Double.valueOf(para2).doubleValue();
		if(operate.equalsIgnoreCase("is")){//������������,���޸�
			//resrult=(f1 == f2);
			resrult=(Math.abs(f1 - f2)<0.0001);
		}else if(operate.equalsIgnoreCase("not")){//������������,���޸�
			//resrult=(f1 != f2);
			resrult=(Math.abs(f1 - f2)>0.0001);
		}
		return resrult;
	}
	private boolean checkRules(ArrayList rules) throws JspException{
		if(rules==null||rules.isEmpty())return false;
		boolean resrult=false; 
		boolean forwordRes=true;
		java.util.Iterator it=rules.iterator();
		while (it.hasNext()){
			DisplayControlDTO rule=(DisplayControlDTO) it.next();
//			WebPrint.PrintErrorInfo(this.getClass().getName(),"checkRules.rule:::::"+rule);
			//���visible����
			String strVisible=rule.getIsVisible();
			if(strVisible!=null){
				//�����������ʾ����,yesֱ����ʾ,no����ʾ,ckeck,���ݺ������ý��м��
				if(strVisible.equalsIgnoreCase("n")||strVisible.equalsIgnoreCase("no")){
					return false;
				}else if(strVisible.equalsIgnoreCase("y")||strVisible.equalsIgnoreCase("yes")){
					return true;
				}
			}else{
				WebPrint.PrintErrorInfo(this.getClass().getName(),id+">>>>"+"��Ч����������,visible����û������.");
				return false;
			}
			//��ʼ������õĹ���
			boolean currentRes=false;
			String refer=rule.getReferto();
			//�����ǰ��������������,��ת�������������м��
			if(refer!=null&&!refer.equals("")){
				ArrayList controlRuleList=Postern.getDisplayControlList(refer);
				currentRes=checkRules(controlRuleList);
			}else{
				currentRes=checkRule(rule);
			}
//			WebPrint.PrintDebugInfo(this.getClass().getName(),"checkRules.currentRes:::::"+currentRes);

			String postfix=rule.getPostfix();
			if(operateString.indexOf(postfix)<0){
				WebPrint.PrintErrorInfo(this.getClass().getName(),id+">>>>"+"��Ч����������,"+postfix+"��Ч.");
				return false;
			}

			if("and".equalsIgnoreCase(postfix)){
				resrult=forwordRes&&currentRes;
			}else if("or".equalsIgnoreCase(postfix)){
				resrult=forwordRes||currentRes;
			}
			forwordRes=resrult;
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRules.forwordRes:::::"+forwordRes);
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRules.currentRes:::::"+currentRes);
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRules.postfix:::::"+postfix);
//			WebPrint.PrintDebugInfo(this.getClass().getName(), "checkRules.resrult:::::"+resrult);
		}
		return resrult;
	}
	public int doStartTag() throws JspException {
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"\n>>>>>"+id);
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"doStartTag.id:::::"+id);
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"doStartTag.bean:::::"+bean);
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"doStartTag.scope:::::"+scope);
		boolean isValid=false;
		parseBeans();
//		WebPrint.PrintDebugInfo(this.getClass().getName(),"doStartTag.beans:::::"+beans);

		ArrayList controlRuleList=Postern.getDisplayControlList(id);
		//��������Ч�����õ�ʱ��,�������ü��,����ֱ�Ӷ���Ϊ����ʾ
		if(controlRuleList!=null&&!controlRuleList.isEmpty()){
			isValid=checkRules(controlRuleList);
			if(!isValid)
				WebPrint.PrintDebugInfo(this.getClass().getName(),"\n"+id+">>>>:"+isValid);
		}else{
			isValid=true;
		}

		if (isValid)
			return (EVAL_BODY_BUFFERED);
		else
			return (SKIP_BODY);
	}

	public int doAfterBody() throws JspException {
//		WebPrint.PrintTagDebugInfo(this.getClass().getName(),
//				"doAfterBody enter ...");

		if (bodyContent != null) {
			try {
				JspWriter out = pageContext.getOut();
				if (out instanceof BodyContent)
					out = ((BodyContent) out).getEnclosingWriter();
				out.println(bodyContent.getString());
			} catch (Exception e) {
				WebPrint.PrintErrorInfo(this.getClass().getName(), e
						.getMessage());
			}
			bodyContent.clearBody();
		}

//		WebPrint.PrintTagDebugInfo(this.getClass().getName(),
//				"doAfterBody return.");

		return (SKIP_BODY);

	}

	public void release() {

		super.release();

	}
	/**
	 * @return the bean
	 */
	public String getBean() {
		return bean;
	}

	/**
	 * @param bean the bean to set
	 */
	public void setBean(String bean) {
		this.bean = bean;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
}