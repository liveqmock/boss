package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

public class CommonFeeAndPaymentTag extends TagSupport{
	protected String includeParameter;         //��Ҫʲô���ķ��ú�֧���б� ,����
	protected String payCsiType ;              //�������ͣ�����
	protected String acctshowFlag ="false";    //�ʻ��Ƿ���ʾ ��ѡ
	protected String checkMoneyFlag ="0";      //������. "0": ����Ϊ����,"1": ����Ϊ����,"2"�������
	protected String confirmFlag ;             //�Ƿ��ύ��֧��ʱ����
	protected String acceptPayFlag ="true";    //�Ƿ����֧������ѡ
	protected String packageList="" ;
	protected String productList="";
	

	public String getProductList() {
		return productList;
	}

	public void setProductList(String productList) {
		this.productList = productList;
	}

	public String getPackageList() {
		return packageList;
	}

	public void setPackageList(String packageList) {
		this.packageList = packageList;
	}

	public String getIncludeParameter() {
		return includeParameter;
	}

	public void setIncludeParameter(String includeParameter) {
		this.includeParameter = includeParameter;
	}
	
	public String getPayCsiType() {
		return payCsiType;
	}

	public void setPayCsiType(String payCsiType) {
		this.payCsiType = payCsiType;
	}
	
	public String getAcctshowFlag() {
		return acctshowFlag;
	}

	public void setAcctshowFlag(String acctshowFlag) {
		this.acctshowFlag = acctshowFlag;
	}
	
	public String getCheckMoneyFlag() {
		return checkMoneyFlag;
	}

	public void setCheckMoneyFlag(String checkMoneyFlag) {
		this.checkMoneyFlag = checkMoneyFlag;
	} 

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	
	public String getAcceptPayFlag() {
		return acceptPayFlag;
	}

	public void setAcceptPayFlag(String acceptPayFlag) {
		this.acceptPayFlag = acceptPayFlag;
	}

	public int doStartTag() throws JspTagException {
		String  result ="";	
		pageContext.getRequest().setAttribute("acctshowFlag", acctshowFlag);
		pageContext.getRequest().setAttribute("checkMoneyFlag",checkMoneyFlag);
		pageContext.getRequest().setAttribute("PayCsiType",payCsiType);
		pageContext.getRequest().setAttribute("acceptPayFlag",acceptPayFlag);
		pageContext.getRequest().setAttribute("includeParameter",includeParameter);
		pageContext.getRequest().setAttribute("packageList",packageList);
		pageContext.getRequest().setAttribute("productList",productList);
		pageContext.getRequest().setAttribute("confirmFlag", confirmFlag);
		
		try { 
			if (includeParameter.equalsIgnoreCase("Fee")){
	 	        result ="/fee/common_fee.jsp";
		        pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());     
                //  ��ӡ��Ӧ��JavaScript
		    	ResponseUtils.write(pageContext, getFeeScript());
		        return SKIP_BODY;
			}
			
			if (includeParameter.equalsIgnoreCase("CloseAcctFee_step1")){
				result ="/fee/close_acctfee_step1.jsp";
				pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
				result ="/fee/common_pay.jsp";
        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
                // ��ӡ��Ӧ��JavaScript
		    	ResponseUtils.write(pageContext, getFee_PayScript());
		    	return SKIP_BODY;
			}
			
			if (includeParameter.equalsIgnoreCase("CloseAcctFee_step2")){
				pageContext.getRequest().setAttribute("refundFlag", "true");
				result ="/fee/close_acctfee_step2.jsp";
				pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
				result ="/fee/common_pay.jsp";
        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
                //  ��ӡ��Ӧ��JavaScript
		    	ResponseUtils.write(pageContext, getFeeReturnScript());
		    	return SKIP_BODY;
			}
			
			if (includeParameter.equalsIgnoreCase("CloseAcctFee_step3")){
				result ="/fee/close_acctfee_step1.jsp";
				pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
				result ="/fee/close_acctpay_confirm.jsp";
        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
        		return SKIP_BODY;
			}
			
			//�����ǹ���֧����ҳ��
			if (!"false".equalsIgnoreCase(confirmFlag) && !"true".equalsIgnoreCase(confirmFlag))
	            throw new JspTagException("֧��ҳ��û���Ƿ��ύ�ı�־�����뿪������ϵ��");
			
	        //Ԥ�治��Ҫ�ڲ��ʻ��ͷ���ҳ��
	        if (includeParameter.equalsIgnoreCase("Fee_Deposite")){
	        	if ("false".equalsIgnoreCase(confirmFlag)){
				   result ="/fee/common_deposit.jsp";  
				   
                   //Ƕ����Ӧ��JavaScript���,wangpeng@20080226 
			       ResponseUtils.write(pageContext, getDepositScript());
			       
	        	} else{
	        	   result ="/fee/common_deposit_confirm.jsp";  
	        	}
	        	pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	            return SKIP_BODY;
	        }
	        
	        if (includeParameter.equalsIgnoreCase("Pay")){
	        	if ("false".equalsIgnoreCase(confirmFlag)){
	        		result ="/fee/common_pay.jsp";
	        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
                    // ��ӡ��Ӧ��JavaScript
			    	ResponseUtils.write(pageContext, getFee_PayScript());
	        	}else {
	        		result ="/fee/common_pay_confirm.jsp";
	        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	        	}
	            return SKIP_BODY;
	        }
	        
	        if (includeParameter.equalsIgnoreCase("Fee_Pay")){
 	           result ="/fee/common_fee.jsp";
	           pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());     
	           if ("false".equalsIgnoreCase(confirmFlag)){
		    	  result ="/fee/common_pay.jsp";
	              pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());
	              //��ӡ��Ӧ��JavaScript
		    	  ResponseUtils.write(pageContext, getFee_PayScript());
		       } else {
		    	  result ="/fee/common_pay_confirm.jsp";
		    	  pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());
		       }
	           return SKIP_BODY;
	       }
	       
	       if (includeParameter.equalsIgnoreCase("Fee_PayAndPrep")){
	    	   pageContext.getRequest().setAttribute("forceFeeFlag","true");
	    	   result ="/fee/common_fee.jsp";
	    	   pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(),pageContext.getResponse());
	    	   if ("false".equalsIgnoreCase(confirmFlag)){
	    		   result ="/fee/common_payAndprepay.jsp";
	    		   pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());
                   // ��ӡ��Ӧ��JavaScript
			       ResponseUtils.write(pageContext, getFee_PayAndPreScript());
	    	   } else{
	    		   result ="/fee/common_payAndprepay_confirm.jsp";
	    		   pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse());
	    	   }
	    	   return SKIP_BODY;
	       }
	       
	       if (includeParameter.equalsIgnoreCase("Fee_BatchDeposite")){
	        	if ("false".equalsIgnoreCase(confirmFlag)){
				   result ="/fee/common_batchdeposit.jsp";  
				   // ��ӡ��Ӧ��JavaScript
			       ResponseUtils.write(pageContext, getDepositScript());
	        	} else{
	        	   result ="/fee/common_deposit_confirm.jsp";  
	        	}
	        	pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	            return SKIP_BODY;
	       }
	       
	       //�ϲ�����֧���ʵ�,�����ʻ�֧���ʵ�ʱҪ�����.����������дjavascript.���ึ�ѵ�Ǯת��Ԥ��
	       //��confirmFlag=true�����Ŀǰû���õ�
	       if (includeParameter.equalsIgnoreCase("SinglePay")){
	    	   if ("false".equalsIgnoreCase(confirmFlag)){
	    	      result ="/fee/common_singlePay.jsp";
       		      pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	    	   }else {
	        	  result ="/fee/common_pay_confirm.jsp";
	        	  pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	           }   
       		   return SKIP_BODY;
	       }
	       
		} catch(Exception e){
			  e.printStackTrace();
	          WebPrint.PrintErrorInfo(this.getClass().getName(), "doStartTag exception "+e.getMessage());
	    }
		return SKIP_BODY;
    }
	
	public void release() {

         super.release();
         acctshowFlag  =null;
         checkMoneyFlag = null;
         confirmFlag = null;
         includeParameter =null;
     }
	 private String getDepositScript(){
		 
		 String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		        scripts +="  function check_pay(){ \r\n"
		        	    + "      generalPayTotal =document.all("+"\""+"generalPayTotal"+"\""+").value; \r\n"
		        	    + "      if (generalPayTotal*1.0 ==0){   \r\n"
		        	    + "         alert("+"\""+"Ԥ�����Ϊ0!"+"\""+"); \r\n"
	                    + "         return false; \r\n"
                        + "      } \r\n"
                        + "      return true; \r\n"
		        	    + "  } \r\n";
		 scripts =scripts  +"</script> \r\n";
		 return  scripts;      	    
	 }
	
	 private String getFeeScript(){
		 String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		        scripts +="  function check_fee(){ \r\n"
		        	    + "      var returnFlag =false ; \r\n"
		        	    + "      forceFeeTotal =document.all("+"\""+"forceFeeTotal"+"\""+"); \r\n"
              		 	+ "      generalFeeTotal =document.all("+"\""+"generalFeeTotal"+"\""+").value ; \r\n"
              		 	+ "      if (generalFeeTotal*1.0 !=0) { \r\n"
		 	            + "         returnFlag =true; \r\n"
		 	   	        + "      } \r\n" 
		 	   	        + "      if (forceFeeTotal) { \r\n "
		 	   	        + "         if (forceFeeTotal.value*1.0 !=0) { \r\n "
		 	   	        + "             returnFlag =true; \r\n "
		 	   	        + "         } \r\n"
		 	   	        + "      } \r\n"
                        + "      return returnFlag; \r\n"
		 	            + "  } \r\n" ;
		 	    scripts =scripts +"</script> \r\n";
		return  scripts; 
	 }
	 
	 private String getFee_PayScript(){
		 String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		        scripts +="  function check_fee(){ \r\n"
		        	    +"       generalFeeTotal =document.all("+"\""+"generalFeeTotal"+"\""+").value ; \r\n"	        	    
	                    + "      generalPayTotal =document.all("+"\""+"generalPayTotal"+"\""+").value; \r\n"
	                    + "      if (generalPayTotal*1.0 !=generalFeeTotal*1.0){  \r\n"
	                    + "         alert("+"\""+"֧��������������!"+"\""+"); \r\n"
	                    + "         return false; \r\n"
	                    + "      } \r\n"
	                    +        getInsideAcctPayCheck_Script()
	                    + "      return true; \r\n"
		        	    + "  } \r\n";
		 scripts =scripts  +"</script> \r\n";
		 return  scripts;      	    
	 }
	 
	 private  String  getFeeReturnScript(){
		 String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		        scripts +="  function check_fee(){ \r\n"
		                + "     pay_realMOP =document.getElementsByName("+"\""+"pay_realMOP"+"\");  \r\n"	
		                + "     pay_pay =document.getElementsByName("+"\"" +"pay_realpay"+"\");  \r\n"
		                + "     var payCash =0, payToken=0; \r\n"
	                    + "     for (i=0; i<pay_realMOP.length;i++){  \r\n"
	                    + "        pay_mopid =pay_realMOP[i].value.substring(0,pay_realMOP[i].value.indexOf("+"\""+"-"+"\""+")); \r\n"
	                    + "        pay_paymentType =pay_realMOP[i].value.substr(pay_realMOP[i].value.indexOf("+"\""+"_"+"\""+")+1); \r\n"
	                    + "        if (pay_mopid ==10 || pay_mopid ==11) { \r\n"
	                    + "            alert("+"\""+"���ʻ�����ѡ��Ԥ��ֿۣ��������Ա��ϵ!"+"\""+"); \r\n"
	                    + "            pay_pay[i].value =0; \r\n "
	                    + "            pay_pay[i].focus(); \r\n "
	                    + "            return false; \r\n"
	                    + "        } \r\n"
	                    + "        if (pay_paymentType =='Y'){ \r\n"
	                    + "            payCash =payCash+pay_pay[i].value*1.0; \r\n"
	                    + "        } else { \r\n"
	                    + "            payToken =payToken+pay_pay[i].value*1.0; \r\n"
	                    + "        } \r\n"
	                    + "      } \r\n"
	                    + "      var acctPreCashDoposit  =document.all("+"\""+"insideAcctPreCashDoposit"+"\").value ; \r\n"
	                    + "      var acctPreTokenDoposit =document.all("+"\""+"insideAcctPreTokenDoposit"+"\").value ; \r\n"
	                    + "      if ((acctPreCashDoposit*1.0 +payCash*1.0) <0){ \r\n "
	                    + "         alert("+"\""+"�ֽ�Ԥ���˻�������!"+"\""+"); \r\n"
	                    + "         return false ; \r\n"
	                    + "      } \r\n"
	                    + "      if ((acctPreTokenDoposit*1.0 +payToken*1.0) <0){ \r\n "
	                    + "         alert("+"\""+"�������Ԥ���˻�������!"+"\""+"); \r\n"
	                    + "         return false ; \r\n"
	                    + "      } \r\n"
	                    + "      if (window.confirm('�ֽ��˻��ܼƣ�'+payCash+'; ��������˻��ܼ�: '+payToken)){ \r\n"
	                    + "         return true; \r\n"
	                    + "      } else { \r\n"
	                    + "         return false; \r\n"
	                    + "      } \r\n"
	                    +"   } \r\n";         
		        scripts =scripts  +"</script> \r\n";
		 return   scripts;   	
	 }
	
	 
	 private String getFee_PayAndPreScript(){
		 String scripts  ="<script language="+"\""+"javascript"+"\""+"> \r\n";
		        scripts +="  function check_fee(){ \r\n"
		                 +"      forcePayTotal =document.all("+"\""+"forcePayTotal"+"\""+").value; \r\n"
		                 +"      forceFeeTotal =document.all("+"\""+"forceFeeTotal"+"\""+"); \r\n"
                         +"      if (forceFeeTotal){ \r\n"
		                 +"         if (forceFeeTotal.value*1.0 >forcePayTotal*1.0) { \r\n"
                         +"            alert("+"\""+"ʵ��Ԥ�����С��ǿ��Ԥ����"+"\""+");  \r\n"
                         +"            return false;  \r\n"
                         +"         } \r\n"
                         +"      } \r\n"
                         + "     generalFeeTotal =document.all("+"\""+"generalFeeTotal"+"\""+").value ; \r\n"	        	    
 		        	     + "     generalPayTotal =document.all("+"\""+"generalPayTotal"+"\""+").value; \r\n"
                         +"      if (generalFeeTotal*1.0 !=generalPayTotal*1.0){ \r\n"
                         +"         alert("+"\""+"ʵ��֧�������Ӧ���ѽ���!"+"\""+"); \r\n"
                         +"         return false; \r\n"
                         +"      }  \r\n"
                         + getInsideAcctPayCheck_Script()
                         +"      return true; \r\n"
                         +"  } \r\n";
		 scripts =scripts +"</script> \r\n";
		 return  scripts;      	
	 }
	 
	 
	 private String getInsideAcctPayCheck_Script(){
	     String scripts ="       pay_realMOP =document.getElementsByName("+"\""+"pay_realMOP"+"\");  \r\n"
	                    +"       pay_pay =document.getElementsByName("+"\"" +"pay_realpay"+"\");  \r\n"
	                    +"       var insidePayCash =0, insidePayToken=0; \r\n"
	                    +"       for (i=0; i<pay_realMOP.length;i++){  \r\n"
	                    +"           pay_mopid =pay_realMOP[i].value.substring(0,pay_realMOP[i].value.indexOf("+"\""+"-"+"\""+")); \r\n"
	                    +"           pay_paymentType =pay_realMOP[i].value.substr(pay_realMOP[i].value.indexOf("+"\""+"_"+"\""+")+1); \r\n"
	                    +"           if (pay_mopid !=10 & pay_mopid !=11) continue; \r\n"
	                    +"           if (pay_paymentType =='Y'){ \r\n"
	                    +"               insidePayCash =insidePayCash+pay_pay[i].value*1.0; \r\n"
	                    +"           } else { \r\n"
	                    +"               insidePayToken =insidePayToken+pay_pay[i].value*1.0; \r\n"
	                    +"           } \r\n"
	                    +"       } \r\n"
	                    +"       insideAcctPreCashDoposit  =document.all("+"\""+"insideAcctPreCashDoposit"+"\") ; \r\n"
	                    +"       if (insidePayCash >0){ \r\n"
	                    +"           if (insideAcctPreCashDoposit){ \r\n"
	                    +"              if (insideAcctPreCashDoposit.value <insidePayCash){ \r\n"
	                    +"                 alert("+"\""+"�ڲ��ֽ��ʻ����㣡"+"\""+"); \r\n"
	                    +"                 return false; \r\n"
	                    +"              } \r\n"
	                    +"           }else{ \r\n"
	                    +"               alert("+"\""+"���û����ڲ��ʻ���ָ������ȷ���������Ա��ϵ!"+"\""+"); \r\n"
	                    +"               return false; \r\n"
	                    +"           } \r\n"
	                    +"      } \r\n"
	                    +"      insideAcctPreTokenDoposit =document.all("+"\""+"insideAcctPreTokenDoposit"+"\") ; \r\n"
	                    +"      if (insidePayToken >0){ \r\n "
	                    +"          if (insideAcctPreTokenDoposit){ \r\n"
	                    +"             if (insideAcctPreTokenDoposit.value <insidePayToken){ \r\n"
	                    +"                alert("+"\""+"�ڲ���������ʻ����㣡"+"\""+"); \r\n"
	                    +"                return false; \r\n"
	                    +"             } \r\n"
	                    +"          }else{ \r\n"
	                    +"             alert("+"\""+"���û����ڲ��ʻ���ָ������ȷ���������Ա��ϵ!"+"\""+"); \r\n"
	                    +"             return false; \r\n"
	                    +"          } \r\n"
	                    +"     } \r\n" ;
	      return scripts;
	 }

}
