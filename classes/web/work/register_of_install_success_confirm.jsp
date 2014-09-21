<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="o" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript> 
   function frm_submit(){
	   	 document.frmPost.action ="install_info_update.do";
	     document.frmPost.submit();
   }
   
   function back_submit(){
	    document.frmPost.action="register_of_install_success_pay.screen";
	    document.frmPost.txtDoPost.value ="false";
	    document.frmPost.submit();
   }

</Script>

<form name="frmPost" method="post" action="" >     
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
       <tr>
         <td><img src="img/list_tit.gif" width="19" height="19"></td>
         <td class="title">录入安装反馈--确认信息</td>
       </tr>
    </table>
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr>
      <td class="list_bg2" align="right" width="17%">工单编号</td>
      <td class="list_bg1" align="left"  width="33%">
	       <tbl:writeparam name="txtJobCardID" />
	    </td>
	    <td class="list_bg2" align="right" width="17%">受理单编号</td>
      <td class="list_bg1" align="left"  width="33%">
	       <tbl:writeparam name="txtReferSheetID" />
	    </td>
	  </tr>
	  <tr>
		  <td class="list_bg2"  align="right" width="17%">预约上门时间</td>
		  <td class="list_bg1"  align="left"  width="33%"> 
			   <tbl:writeparam name="txtPreferedDate" /> 
      </font></td>
		  <td class="list_bg2"  align="right" width="17%">预约上门时间段</td>
		  <td class="list_bg1"  align="left"  width="33%">
		     <tbl:writeparam name="txtPreferedTime" /> 
		  </td>
	  </tr>
	  <tr>
		  <td class="list_bg2"  align="right" width="17%">上门时间*</td>
		  <td class="list_bg1"  align="left"  width="33%"> 
			   <tbl:writeparam name="txtWorkDate" /> 
		  </td>
		  <td class="list_bg2" align="right" width="17%">上门时间段*</td>
		  <td class="list_bg1" align="left"  width="33%">
		     <o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="txtWorkTime" />
		  </td>
	  </tr>
	  <tr>
		  <td class="list_bg2" align="right" width="17%">安装人员*</td>
	  	<td class="list_bg1" colspan="3" align="left" width="83%"> 
			   <tbl:writeparam name="txtProcessMan" />
		  </td>
	  </tr>
	  <tr>
		  <td class="list_bg2" align="right" width="17%">安装成功描述</td>
	  	<td class="list_bg1" colspan="3" align="left" width="83%"> 
	  	  <tbl:writeparam name="txtMemo" />
		  </td>
	  </tr>
	  </table>
	  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <%
       String csiType = request.getParameter("txtCsiType");
       String serviceID = request.getParameter("txtServiceID")==null?"":request.getParameter("txtServiceID");
    %>     
    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=csiType%>" acctshowFlag ="true" confirmFlag="true" /> 
    </table>
    <tbl:hiddenparameters pass="txtJobCardID/txtReferSheetID/txtPreferedDate/txtPreferedTime/txtWorkDate/txtWorkTime/txtProcessMan/txtMemo/txtIsSuccess/txtDtLastmod" />
    
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tbl:dynamicservey serviceID="<%=serviceID%>" serveyName="txtDynamicServey" serveyType="U"  serveySubType="IU" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="23" tdWidth1="17%" tdWidth2="33%" defaultFlag="true" />
    </table>
	
    <input type="hidden" name="txtDoPost" value="true" >
    <input type="hidden" name="txtCsiType" value="<%=csiType%>" >
    <tbl:hiddenparameters pass="txtcsiPayOption/txtCustomerID/txtAccountID" />  
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  认"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>
 <tbl:generatetoken />
</form> 

