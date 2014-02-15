package com.dtv.oss.web.taglib.html;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.web.taglib.util.ResponseUtils;
import com.dtv.oss.web.util.WebPrint;

public class CommonFeeAndPaymentTag extends TagSupport{
	protected String includeParameter;         //需要什么样的费用和支付列表 ,必填
	protected String payCsiType ;              //受理单类型，必填
	protected String acctshowFlag ="false";    //帐户是否显示 可选
	protected String checkMoneyFlag ="0";      //检查费用. "0": 不能为负数,"1": 不能为正数,"2"：不检查
	protected String confirmFlag ;             //是否提交，支付时必填
	protected String acceptPayFlag ="true";    //是否接受支付，可选
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
                //  打印相应的JavaScript
		    	ResponseUtils.write(pageContext, getFeeScript());
		        return SKIP_BODY;
			}
			
			if (includeParameter.equalsIgnoreCase("CloseAcctFee_step1")){
				result ="/fee/close_acctfee_step1.jsp";
				pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
				result ="/fee/common_pay.jsp";
        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
                // 打印相应的JavaScript
		    	ResponseUtils.write(pageContext, getFee_PayScript());
		    	return SKIP_BODY;
			}
			
			if (includeParameter.equalsIgnoreCase("CloseAcctFee_step2")){
				pageContext.getRequest().setAttribute("refundFlag", "true");
				result ="/fee/close_acctfee_step2.jsp";
				pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
				result ="/fee/common_pay.jsp";
        		pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
                //  打印相应的JavaScript
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
			
			//以下是关于支付的页面
			if (!"false".equalsIgnoreCase(confirmFlag) && !"true".equalsIgnoreCase(confirmFlag))
	            throw new JspTagException("支付页面没有是否提交的标志，请与开发商联系！");
			
	        //预存不需要内部帐户和费用页面
	        if (includeParameter.equalsIgnoreCase("Fee_Deposite")){
	        	if ("false".equalsIgnoreCase(confirmFlag)){
				   result ="/fee/common_deposit.jsp";  
				   
                   //嵌入相应的JavaScript语句,wangpeng@20080226 
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
                    // 打印相应的JavaScript
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
	              //打印相应的JavaScript
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
                   // 打印相应的JavaScript
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
				   // 打印相应的JavaScript
			       ResponseUtils.write(pageContext, getDepositScript());
	        	} else{
	        	   result ="/fee/common_deposit_confirm.jsp";  
	        	}
	        	pageContext.getRequest().getRequestDispatcher(result).include(pageContext.getRequest(), pageContext.getResponse()); 
	            return SKIP_BODY;
	       }
	       
	       //合并批量支付帐单,单个帐户支付帐单时要用这个.在外层需额外写javascript.将多付费的钱转成预存
	       //此confirmFlag=true的情况目前没有用到
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
		        	    + "         alert("+"\""+"预存金额不能为0!"+"\""+"); \r\n"
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
	                    + "         alert("+"\""+"支付金额与所需金额不符!"+"\""+"); \r\n"
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
	                    + "            alert("+"\""+"退帐户不能选择预存抵扣，请与管理员联系!"+"\""+"); \r\n"
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
	                    + "         alert("+"\""+"现金预存退还金额过大!"+"\""+"); \r\n"
	                    + "         return false ; \r\n"
	                    + "      } \r\n"
	                    + "      if ((acctPreTokenDoposit*1.0 +payToken*1.0) <0){ \r\n "
	                    + "         alert("+"\""+"虚拟货币预存退还金额过大!"+"\""+"); \r\n"
	                    + "         return false ; \r\n"
	                    + "      } \r\n"
	                    + "      if (window.confirm('现金退还总计：'+payCash+'; 虚拟货币退还总计: '+payToken)){ \r\n"
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
                         +"            alert("+"\""+"实际预存金额不能小于强制预存金额"+"\""+");  \r\n"
                         +"            return false;  \r\n"
                         +"         } \r\n"
                         +"      } \r\n"
                         + "     generalFeeTotal =document.all("+"\""+"generalFeeTotal"+"\""+").value ; \r\n"	        	    
 		        	     + "     generalPayTotal =document.all("+"\""+"generalPayTotal"+"\""+").value; \r\n"
                         +"      if (generalFeeTotal*1.0 !=generalPayTotal*1.0){ \r\n"
                         +"         alert("+"\""+"实际支付金额与应付费金额不符!"+"\""+"); \r\n"
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
	                    +"                 alert("+"\""+"内部现金帐户余额不足！"+"\""+"); \r\n"
	                    +"                 return false; \r\n"
	                    +"              } \r\n"
	                    +"           }else{ \r\n"
	                    +"               alert("+"\""+"配置或者内部帐户的指定不正确，请与管理员联系!"+"\""+"); \r\n"
	                    +"               return false; \r\n"
	                    +"           } \r\n"
	                    +"      } \r\n"
	                    +"      insideAcctPreTokenDoposit =document.all("+"\""+"insideAcctPreTokenDoposit"+"\") ; \r\n"
	                    +"      if (insidePayToken >0){ \r\n "
	                    +"          if (insideAcctPreTokenDoposit){ \r\n"
	                    +"             if (insideAcctPreTokenDoposit.value <insidePayToken){ \r\n"
	                    +"                alert("+"\""+"内部虚拟货币帐户余额不足！"+"\""+"); \r\n"
	                    +"                return false; \r\n"
	                    +"             } \r\n"
	                    +"          }else{ \r\n"
	                    +"             alert("+"\""+"配置或者内部帐户的指定不正确，请与管理员联系!"+"\""+"); \r\n"
	                    +"             return false; \r\n"
	                    +"          } \r\n"
	                    +"     } \r\n" ;
	      return scripts;
	 }

}
