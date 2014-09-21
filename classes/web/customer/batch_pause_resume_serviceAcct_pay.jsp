<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.service.commandresponse.CommandResponse,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.dto.ServiceAccountDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%

String strActionType = request.getParameter("txtActionType");
String strCsitype ="";
String strTitle ="";
int iActionType = 0;
if ((strActionType!=null)&&(strActionType.compareTo("")!=0)){
   try
   {
	   iActionType=Integer.valueOf(strActionType).intValue();
   }
   catch (Exception ex)
   {
   }
   switch(iActionType){
	   case CommonKeys.BATCH_SERVICE_ACCOUNT_PAUSE:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_UP;
		   strTitle ="业务帐户批量暂停";
		   break;
	   case CommonKeys.BATCH_SERVICE_ACCOUNT_RESUME:
		   strCsitype =CommonKeys.CUSTSERVICEINTERACTIONTYPE_UR;
		   strTitle ="业务帐户批量恢复";
		   break;
  }
}
%>
<Script language=JavaScript>
<!-- 
function frm_next(){
	 document.frmPost.txtDoPost.value ="true";
   document.frmPost.action="batch_pause_resume_serviceAccount_op.do";
   document.frmPost.submit();
}
function frm_next_1(csiPayOption){
	 document.frmPost.txtDoPost.value ="true";
	 document.frmPost.action="batch_pause_resume_serviceAccount_op_1.do";
	 document.frmPost.submit();
}

function back_submit(){
	document.frmPost.txtDoPost.value ="false";
	document.frmPost.action="batch_pause_resume_serviceAccount.do";
	document.frmPost.submit();
}
//-->
</Script>
<form name="frmPost" method="post" action="" >     
    <table  border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
          <td><img src="img/list_tit.gif" width="19" height="19"></td>
          <td class="title"><%=strTitle%>收费信息-支付</td>
        </tr>
     </table>
     <br>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
     </table>
     <br>
     <table  border="0" align="center" cellpadding="0" cellspacing="10" >
       <tr>
         <td><img src="img/list_tit.gif" width="19" height="19"></td>
         <td class="title">业务帐户信息</td>
       </tr>
     </table>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
       <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
       </tr>
     </table>
     <table width="98%"  border="0" align="center" cellpadding="6" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td class="list_head" >业务帐户ID</td>
          <td class="list_head" >业务名称</td>
          <td class="list_head" >状态</td>          
          <td class="list_head" >创建日期</td>
          <td class="list_head" >产品</td>
        </tr> 
        <%
          String[] str=request.getParameterValues("Index");
		      String serviceAcctIds ="";
		      for (int i=0; i<str.length;i++){
			       if (serviceAcctIds.equals("")){
				        serviceAcctIds =str[i];
			       } else{
				        serviceAcctIds =serviceAcctIds +","+str[i];
			        }
		       } 
           Collection serviceAcctCols =Postern.getServiceAccountDto(serviceAcctIds,null);
           request.setAttribute("ResponseQueryResult",new CommandResponse(serviceAcctCols));
        %>
        <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <% 
           ServiceAccountDTO dto=(ServiceAccountDTO)pageContext.getAttribute("oneline");
           int saID=dto.getServiceAccountID();
           String serviceCode=dto.getServiceCode();
           int seviceID=dto.getServiceID();
           String serviceName=Postern.getServiceNameByID(seviceID);
           String status=Postern.getStatusByAcctServiceID(saID);
           String productName=Postern.getProductByServiceAccountID("'S'",saID);
        %>
          <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        	  <td align="center"><%=saID%></a></td>
      	    <td align="center"><%=serviceName%></td>
      	    <td align="center"><d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="<%=status%>" /></td>
      	    <td align="center"><tbl:write name="oneline" property="createTime"/></td>
      	    <td align="center"><%=productName%></td>
      	   </tbl:printcsstr>
      	</lgc:bloop> 
     </table>
     <br>
     <table border="0" cellspacing="0" cellpadding="0" width="820">
         <tr><td><font color="red">&nbsp;&nbsp;说明：费用为负表示应退给客户</font></td></tr>
     </table>      
     
     <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=strCsitype%>" acctshowFlag ="true" confirmFlag="false" checkMoneyFlag="2"  /> 
     <tbl:hiddenparameters pass="txtProductID/txtStatus1/txtcsiPayOption" />
     <tbl:hiddenparameters pass="txtCustomerID/txtAccount/txtActionType/Index/txtCsiCreateReason" />
     <input type="hidden" name="txtDoPost" >
     <br>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
     </table>
     <br>
     <TABLE width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	     <TR>
 	         <tbl:csiReason csiType="<%=strCsitype%>" name="txtCsiCreateReason" match="txtCsiCreateReason" 
                action="N" forceDisplayTD="true" tdControlColspan="1" tdWidth1="50%" tdWidth2="50%" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1" />
       </TR>
     </TABLE>
     <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
      <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>  
      <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
      <td width="20" ></td>
      <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
      <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="确认"></td>
      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
      <pri:authorized name="service_account_op_jobcard.do" >
      <td width="20" ></td>
      <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
      <td><input name="next" type="button" class="button" onClick="javascript:frm_next_1()" value="确认并开工单"></td>
      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
      </pri:authorized>
      
    </tr>
 </table>
  <tbl:generatetoken />
</form>  
