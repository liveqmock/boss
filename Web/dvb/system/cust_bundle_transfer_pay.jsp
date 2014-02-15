<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<Script language=JavaScript>
<!--

	function check_form(){
		if (!check_fee()){
		 	return false;  
		}
		return true;
	}
	
	function button_next(){
		if(check_form()){
			document.frmPost.action="cust_bundle_transfer_confirm.do";
			document.frmPost.submit();	
		}
	}
	function back_submit(){
		document.frmPost.action="cust_bundle_transfer_fee.do";
		document.frmPost.submit();
	}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐转换-支付信息</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">老的客户套餐信息</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">创建时间</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDtCreate" size="25"  value="<tbl:writeparam name="txtDtCreate"/>" class="textgray" readonly >
          </td> 
          <td class="list_bg2" align="right">状态</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtStatus" size="25" value="<tbl:writeparam name="txtStatus"/>" class="textgray" readonly >    
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">账户ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">业务账户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">协议期（开始）</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">协议期（结束）</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐付费方式</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtPaymentType" size="25"  value="<tbl:writeparam name="txtPaymentType"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">NID</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtNbrDate" size="25"  value="<tbl:writeparam name="txtNbrDate"/>" class="textgray" readonly >      
          </td>
        </tr>                
      </table>   
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">新的套餐信息</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">新套餐名称</td>
          <td class="list_bg1"  align="left" width="33%" colspan="3">
		      	<input type="text" name="txtNewCampaignName" size="25" value="<tbl:writeparam name="txtNewCampaignName"/>" readonly class="textgray">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">新套餐转换日期</td>
          <td class="list_bg1"  align="left" width="33%">
		      	<input type="text" name="txtTransferDate" size="25" value="<tbl:writeparam name="txtTransferDate"/>" readonly class="textgray">
          </td>
          <td class="list_bg2" align="right" width="17%">新套餐有效期(结束)</td>
          <td class="list_bg1"  align="left" width="33%">
		      	<input type="text" name="txtCampaignDateTo" size="25" value="<tbl:writeparam name="txtCampaignDateTo"/>" readonly class="textgray">
          </td> 
        </tr>
      </table>
   	 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDS%>" acctshowFlag ="true" confirmFlag="false" checkMoneyFlag="2"/>		 
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
		<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20"></td>
		<lgc:hasnoterror>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:button_next()" class="btn12">下一步</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	  </lgc:hasnoterror>
	</tr>
</table>
<input type="hidden" name="txtActionType"  value="bundleTransfer" >
<input type="hidden" name="txtDoPost"  value="false" >
<tbl:productproperty serveyName="txtProductProperty" showType="hide"  />	
<tbl:hiddenparameters pass="txtCustomerID/txtcsiPayOption/txtCsiCreateReason/txtIsReturn/txtDeviceFee"/>
<tbl:hiddenparameters pass="txtCampaignID/cancelCpID/txtProductID/TerminalDeviceList/deviceModelSerialNo/txtProductID2PackageID" />
<tbl:hiddenparameters pass="txtGrade/itemID/phoneNo"/>

</form>

