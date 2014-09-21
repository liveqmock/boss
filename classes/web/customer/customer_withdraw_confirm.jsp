<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,com.dtv.oss.web.util.WebKeys" %>

<%request.setAttribute("feePageType","1");%>
<Script language=javascript>
function frm_submit(){
    document.frmPost.confirm_post.value="true";
    document.frmPost.action="customer_withdraw_op.do";
    document.frmPost.target="_self";
    document.frmPost.submit();
}
 
function back_submit(){
	<%
  	  String requestAttribute =(request.getAttribute(WebKeys.REQUEST_ATTRIBUTE) ==null) ? "0" : "1";
  %>
   request_attr ="<%=requestAttribute%>";
   if (request_attr =="0"){
      document.frmPost.action ="customer_withdraw_fee_step2.screen";
   } else{
      document.frmPost.confirm_post.value ="false";
      document.frmPost.action ="customer_withdraw_fee_wrap_step1data.do";
   }
	 document.frmPost.submit();
}


</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户退户--信息确认</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="func_flag" value="4">
    <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="forwardFlag" value=""> 
    <tbl:hiddenparameters pass="txtAccount/txtName/txtDeviceID/txtRealDeviceFee/txtRentFee/txtRealRentFee/txtCustomerType/txtComments" />
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">客户证号</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">客户类型</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<tbl:writeparam name="txtCustomerTypeShow"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">创建日期</div></td>
            <td class="list_bg1"><input type="text" name="txtDtCreateShow" size="25"  value="<tbl:writeparam name="txtDtCreateShow"/>" class="textgray" readonly ></td>
        </tr>
 </table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        <tr class='list_head'>       
          <td colspan="4" align="center" class="import_tit"><font size="3">要退还的设备</font></td>          
        </tr>
        <tr class='list_head'>
          <td align="center" class='list_head'>设备ID</td>
          <td align="center" class='list_head'>设备类型</td>
          <td align="center" class='list_head'>设备型号</td>    
          <td align="center" class='list_head'>设备序列号</td>
       </tr>
<%
    String[] aDeviceID=request.getParameterValues("txtDeviceID");
    if ((aDeviceID!=null)&&(aDeviceID.length>0))
    {
        for (int i=0; i<aDeviceID.length; i++)
        {                
            TerminalDeviceDTO dto = Postern.getTerminalDeviceByID(WebUtil.StringToInt(aDeviceID[i]));
            String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
	    String strModel = dto.getDeviceModel();
%>      
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
          <td align="center" ><%=aDeviceID[i]%></td>
          <td align="center" ><%=strClass%></td>
          <td align="center" ><%=strModel%></td>
          <td align="center" ><font size="2"><%=dto.getSerialNo()%></font></td>
       </tbl:printcsstr>
<%             
        }
    }    
%>  
 </table>       
 <br>

  <tbl:CommonFeeAndPaymentInfo includeParameter="CloseAcctFee_step3" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_WC%>"  />		 		
 	
  <tbl:generatetoken /> 
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
<tr>
   <td><img src="img/button2_r.gif" width="22" height="20"></td>
   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
   <td><img src="img/button2_l.gif" width="11" height="20"></td>
   <td width="20" ></td>
   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 认"></td>
   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
</tr>
</table>     
</form>