<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil,
                 java.util.*,
                 com.dtv.oss.dto.ProductDTO,
                  com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%
request.setAttribute("feePageType","1");
%>
<Script language=JavaScript>
<!--  
function check_frm()
{
	return true;
	
}

function frm_submit()
{
	if (check_frm()) document.frmPost.submit();
}
function back_submit()
{
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="service_account_resume_create.do";
	}else{
		document.frmPost.action="service_account_resume_fee.do";
	}	
//	document.frmPost.action="service_account_resume_fee.do";
	document.frmPost.txtDoPost.value="FALSE";
	document.frmPost.submit();
}
function frm_submit_1(){
	 if (check_frm()){
		  document.frmPost.action ="service_account_op_jobcard.do";
		  document.frmPost.submit();
	 }
}
//-->
</Script>
<form name="frmPost" method="post" action="service_account_resume_op.do" >     
 <table  border="0" align="center" cellpadding="0" cellspacing="5">
    <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">确认信息</td>
    </tr>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">业务帐户ID</td>
    <td class="list_bg1" align="left"  width="33%"><input type="text" name="txtSAID" size="25"  value="<tbl:writeparam name="txtSAID"/>" class="textgray" readonly ></td>
    <td class="list_bg2" align="right" width="17%">业务名称</td>
    <td class="list_bg1" align="left"  width="33%"><input type="text" name="txtSAName" size="25"  value="<tbl:writeparam name="txtSAName"/>" class="textgray" readonly ></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right" width="17%">一次性费帐户</div></td>
    <td class="list_bg1" align="left"  width="33%"><input type="text" name="txtAccount" size="25"  value="<tbl:writeparam name="txtAccount"/>" class="textgray" readonly ></td>
    <td class="list_bg2" align="right" width="17%">创建日期</div></td>
    <td class="list_bg1" align="left"  width="33%"><input type="text" name="txtSAC" size="25"  value="<tbl:writeparam name="txtSAC"/>" class="textgray" readonly ></td>
  </tr>
    <%
        int saID =WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
        StringBuffer deviceStrBu = new StringBuffer();
				List devList=Postern.getDeviceInfoByServiceAccountID(saID);
				if(devList!=null){
					for (int i=0;i<devList.size();i++){
						 TerminalDeviceDTO tdto=(TerminalDeviceDTO)devList.get(i);
						 if("C".equals(tdto.getStatus())) continue;
				     String dClass = tdto.getDeviceClass();
				     String serialNo = tdto.getSerialNo();
				     String className="";
				     if (dClass!=null)
				    	  className = Postern.getClassNameByDeviceClass(dClass);
				     deviceStrBu.append(className+":"+serialNo+",");
					}
				}
				String deviceStr = "";
				if(deviceStrBu.toString().length()>1)
					 deviceStr = deviceStrBu.toString().substring(0,deviceStrBu.toString().length()-1);
		  %>
     <tr>
				<td class="list_bg2" align="right" width="17%">设备</td>
				<td class="list_bg1" colspan="3" width="83%"><%=deviceStr%></td>
			</tr>
     <%
       String txtCPIDs =request.getParameter("txtCPIDs");
       Collection  productCols =Postern.getProductListByPsid(txtCPIDs);
       String resumeProduct ="";
       Iterator productIter =productCols.iterator();
       while (productIter.hasNext()){
           ProductDTO  dto =(ProductDTO)productIter.next();
           if (resumeProduct.equals("")) 
              resumeProduct =dto.getProductName();
           else
              resumeProduct =dto.getProductName()+" / "+resumeProduct ;  
       }
   %>
   <tr>
    	<td class="list_bg2"  align="right" width="17%">恢复产品</td>
     	<td class="list_bg1"  align="left" colspan="3" width="83%"><%=resumeProduct%></td>
   </tr>
   <tr>
  	 <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UR%>" name="txtCsiCreateReason" match="txtCsiCreateReason" 
  	                action="N" forceDisplayTD="true" tdControlColspan="3" showType="label"  tdWidth1="17%" tdWidth2="83%"  tdWordStyle="list_bg2" tdControlStyle="list_bg1" />
   </tr>
 </table>
 
 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UR%>" acctshowFlag ="true"  confirmFlag="true" />		 		
 <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtCPIDs/txtSAID/txtSAName/txtSAC/txtActionType/txtCsiCreateReason" />
 <input type="hidden" name="txtDoPost" value="TRUE" >
 <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >
 <tbl:generatetoken />
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确&nbsp;认"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
   <%
      int saId =(request.getParameter("txtSAID")==null) ? 0 :Integer.parseInt(request.getParameter("txtSAID"));
      int serviceId =Postern.getServiceIDByAcctServiceID(saId);
      if (serviceId ==6){
   %>
   <pri:authorized name="service_account_op_jobcard.do" >
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit_1()" value="确认且开工单"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
   </pri:authorized>
   <%
     }
   %>
</tr>
</table>  
</form> 