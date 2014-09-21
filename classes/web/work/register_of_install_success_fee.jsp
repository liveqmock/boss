<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript>
   function back_submit(jobCardID){
   	  var workDate =document.frmPost.txtWorkDate.value;
   	  var workTime =document.frmPost.txtWorkTime.value;
   	  var processMan =document.frmPost.txtProcessMan.value;
   	  var memo =document.frmPost.txtMemo.value;
	    self.location.href="job_card_view_for_register_of_install.do?txtJobCardID="+jobCardID+"&txtStatus=B"
	                      +"&txtWorkDate="+workDate+"&txtWorkTime="+workTime+"&txtProcessMan="+processMan
	                      +"&txtMemo="+memo;
   }

   function frm_submit(){
	    if (check_fee()){
	       document.frmPost.action="register_of_install_success_pay.screen"; 
	    }else{
	  //  	 document.frmPost.txtDoPost.value ="true";
	 	 document.frmPost.action="register_of_install_success_confirm.screen";
	    }
	    document.frmPost.submit();
   }  

</Script>

<form name="frmPost" method="post" action="" >     
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td><img src="img/list_tit.gif" width="19" height="19"></td>
         <td class="title">录入安装反馈信息--费用信息</td>
       </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <%
       String csiType = request.getParameter("txtCsiType");
       String txtMemo = (request.getParameter("txtMemo")==null) ? "" :request.getParameter("txtMemo");
    %>
     
    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=csiType%>" acctshowFlag ="true"  /> 
    <tbl:hiddenparameters pass="txtJobCardID/txtReferSheetID/txtPreferedDate/txtPreferedTime/txtWorkDate/txtWorkTime/txtProcessMan/txtIsSuccess/txtDtLastmod/txtServiceID" />
    <tbl:dynamicservey serveyName="txtDynamicServey" serveyType="U" serveySubType="IU" showType="hide" />
    <input type="hidden" name="txtDoPost" value="false" >
    <input type="hidden" name="txtCsiType" value="<%=csiType%>" >
    <input type="hidden" name="txtMemo" value="<%=txtMemo%>" >
    <tbl:hiddenparameters pass="txtcsiPayOption/txtCustomerID/txtAccountID" />
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
<!--
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit(<tbl:writeparam name="txtJobCardID" />)" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
   <td width="20" ></td>
   -->
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td background="img/button_bg.gif"><a href="<bk:backurl property="job_card_view_for_register_of_install.do/catv_job_card_view_for_register.do"/>" class="btn12">上一步</a></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>
<tbl:generatetoken />
</form> 

