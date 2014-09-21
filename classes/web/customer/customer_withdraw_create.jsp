<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<script language=javascript>
<!--
function check_form(){
   
	if (check_Blank(document.frmPost.txtRealDeviceFee, true, "设备费"))
	      return false;
	if (!check_Float(document.frmPost.txtRealDeviceFee,true,"设备费"))
              return false;
	return true;
}

function create_submit(){
	 document.frmPost.action="customer_withdraw_fee_step1.do";
	 if (check_form()) {
	    document.frmPost.submit();
	 }    
}

//-->
</SCRIPT>
 
<%
  double txtRealDeviceFee = (double)((request.getParameter("txtRealDeviceFee")==null) ? 0.0 :Double.parseDouble(request.getParameter("txtRealDeviceFee")));
%>
 
<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">录入退户信息</td>
</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="0" width="820"><tr><td><font color="red">&nbsp;&nbsp;说明：费用为负表示应退给客户</font></td></tr></table>
<br>
<form name="frmPost" method="post">	 
     <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">客户证号</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">客户类型</div></td>
            <td class="list_bg1"><input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly ><input type="hidden" name="txtCustomerType" value="<tbl:writeparam name="txtCustomerType"/>"></td>
            <td class="list_bg2"><div align="right">创建日期</div></td>
            <td class="list_bg1"><input type="text" name="txtDtCreateShow" size="25"  value="<tbl:writeparam name="txtDtCreateShow" />" class="textgray" readonly ></td>
        </tr>
         <tr>  
          <td class="list_bg2"><div align="right">设备费*</div></td>
          <td class="list_bg1">           
          <input type="text" name="txtRealDeviceFee" size="25" maxlength="8" value="<%=WebUtil.bigDecimalFormat(txtRealDeviceFee)%>" >
          </td>
          <td class="list_bg2"><div align="right">为一次性费指定帐户*</div></td>
          <td class="list_bg1"> 
          <d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" style="width:186" />
          </td>
        </tr> 
        <tr>
			  	<td class="list_bg2" ><div align="right">备注</div></td>
			  	<td class="list_bg1" colspan="3" >
			     <input type="text" name="txtComments"  size="88" maxlength="250" value="<tbl:writeparam name="txtComments" />" >
			    </td>
  		  </tr>
        
        </table>
        
        <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
         <tr>
           <td colspan="4" class="import_tit" align="center"><font size="3">要退还的设备</font></td>
        </tr>  
         
        
          
<%
    Collection lstDevice = Postern.getAllDeviceByCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")),"C");
    pageContext.setAttribute("AllCustomerDeviceList", lstDevice);
%>
          
        <tr class="list_head">
          
          <td class="list_head" ><div align="center">ID</div></td>
          <td class="list_head" ><div align="center">类型</div></td>
          <td class="list_head"><div align="center">型号</div></td>          
          <td class="list_head"><div align="center">序列号</div></td>
          
          </tr> 
<lgc:loop property="AllCustomerDeviceList" item="onedevice" >
<%
	TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("onedevice");
	String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
	String strModel = dto.getDeviceModel();			    
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	
	  <input type="hidden" name="txtDeviceID"  value="<tbl:write name="onedevice" property="DeviceID" />" >
      	  <td align="center"><tbl:write name="onedevice" property="DeviceID"  /></td>
      	  <td align="center"><%=strClass%></td>
      	  <td align="center"><%=strModel%></td>
      	  <td align="center"><tbl:write name="onedevice" property="SerialNo"/></td>
  </tbl:printcsstr>    	  
    	 
</lgc:loop>  
<tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
          </table>  	      
<BR>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>     
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">下一步</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>
        </tr>
      </table>

</form>

 </td></tr></table>