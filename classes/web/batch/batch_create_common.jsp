<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<Script language=JavaScript>
function check_frm_batch_create_common()
{
     	if (!check_TenDate(document.frmPost.txtCustCreateTime1, true, "客户创建起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCustCreateTime2, true, "客户创建截止时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtAccountCreateTime1, true, "账户创建起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtAccountCreateTime2, true, "账户创建截止时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtAccountInvoiceCreateTime1, true, "账单创建起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtAccountInvoiceCreateTime2, true, "账单创建截止时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCreateTime1, true, "创建的起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCreateTime2, true, "创建的截止时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCampaignStartTime1, true, "促销活动开始起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCampaignStartTime2, true, "促销活动开始截止时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCampaignEndTime1, true, "促销活动结束起始时间")) 
				return false;
			if (!check_TenDate(document.frmPost.txtCPCampaignEndTime2, true, "促销活动结束截止时间")) 
				return false;
     return true;
}
</Script>
 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">客户查询条件</font></td>
	  <td class="import_tit" width="10%"><A href="javascript:drawSubMenu('1000')"><IMG id="arr1000" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu1000" style="display:none">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户类型</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCustTypeListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustTypeListValue" />">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户状态</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCustStatusListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustStatusListValue" />">
	   	<input name="txtCustStatusList" type="hidden" value="<tbl:writeparam name="txtCustStatusList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERSTATUS','txtCustStatusList',document.frmPost.txtCustStatusList.value,'','','')"> 
	   </td>
      </tr>
       	
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >客户创建起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCustCreateTime1)" onblur="lostFocus(this)" name="txtCustCreateTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustCreateTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCustCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >客户创建截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCustCreateTime2)" onblur="lostFocus(this)" name="txtCustCreateTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustCreateTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCustCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
	 
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >开户来源渠道</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCustOpenSourceTypeListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustOpenSourceTypeListValue" />">
	   	<input name="txtCustOpenSourceTypeList" type="hidden" value="<tbl:writeparam name="txtCustOpenSourceTypeList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOPENSOURCETYPE','txtCustOpenSourceTypeList',document.frmPost.txtCustOpenSourceTypeList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >开户来源渠道ID</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCustOpenSourceIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustOpenSourceIDListValue" />">
	   	<input name="txtCustOpenSourceIDList" type="hidden" value="<tbl:writeparam name="txtCustOpenSourceIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('CustOpenSourceTypeID','txtCustOpenSourceIDList',document.frmPost.txtCustOpenSourceIDList.value,'SET_C_CUSTOPENSOURCETYPE','txtCustOpenSourceTypeList',document.frmPost.txtCustOpenSourceTypeList.value)"> 
	   </td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户当前可用积分下限</td>
	   <td width="33%" class="list_bg1"><input name="txtCustCurrentPoints1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustCurrentPoints1" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户当前可用积分上限</td>
	   <td width="33%" class="list_bg1"><input name="txtCustCurrentPoints2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustCurrentPoints2" />"></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户历史总积分下限</td>
	   <td width="33%" class="list_bg1"><input name="txtCustTotalPoints1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustTotalPoints1" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户历史总积分上限</td>
	   <td width="33%" class="list_bg1"><input name="txtCustTotalPoints2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustTotalPoints2" />"></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户姓名</td>
	   <td width="33%" class="list_bg1"><input name="txtCustName" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustName" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户地址</td>
	   <td width="33%" class="list_bg1"><input name="txtCustAddress" type="text" class="text" maxlength="100" size="25" value="<tbl:writeparam name="txtCustAddress" />"></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户证号</td>
	   <td width="33%" class="list_bg1"><input name="txtCustomerID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustomerID" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户所在区域</td>
	   <td width="33%" class="list_bg1">
	   	<input type="hidden" name="txtCustDistrictIDList" value="<tbl:writeparam name="txtCustDistrictIDList" />">
	    	<input type="text" name="txtCustDistrictIDListValue" size="25" maxlength="200" readonly value="<tbl:writeparam name="txtCustDistrictIDListValue" />" class="text">
      		<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:sel_districts('S,T,P','txtCustDistrictIDList','txtCustDistrictIDListValue','checkbox')">	
	   </td>
       </tr>
 </table>
<!-- <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>-->
 
 
 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">账户查询条件</font></td>
	  <td class="import_tit" width="10%"><A href="javascript:drawSubMenu('1001')"><IMG id="arr1001" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu1001" style="display:none">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户类型</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtAccountTypeListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountTypeListValue" />">
	   	<input name="txtAccountTypeList" type="hidden" value="<tbl:writeparam name="txtAccountTypeList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_ACCOUNTTYPE','txtAccountTypeList',document.frmPost.txtAccountTypeList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户状态</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtAccountStatusListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountStatusListValue" />">
	   	<input name="txtAccountStatusList" type="hidden" value="<tbl:writeparam name="txtAccountStatusList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_ACCOUNTSTATUS','txtAccountStatusList',document.frmPost.txtAccountStatusList.value,'','','')"> 
	   </td>
      </tr>
      
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户真实货币余额下限</td>
	   <td width="33%" class="list_bg1"><input name="txtAccountCashBalance1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountCashBalance1" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户真实货币余额上限</td>
	   <td width="33%" class="list_bg1"><input name="txtAccountCashBalance2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountCashBalance2" />"></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户虚拟货币余额下限</td>
	   <td width="33%" class="list_bg1"><input name="txtAccountTokenBalance1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountTokenBalance1" />"></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户虚拟货币余额上限</td>
	   <td width="33%" class="list_bg1"><input name="txtAccountTokenBalance2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountTokenBalance2" />"></td>
       </tr>
       
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >账户创建起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAccountCreateTime1)" onblur="lostFocus(this)" name="txtAccountCreateTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountCreateTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAccountCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >账户创建截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAccountCreateTime2)" onblur="lostFocus(this)" name="txtAccountCreateTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountCreateTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAccountCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
	 
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单地址所在区域</td>
	   <td width="33%" class="list_bg1">
	   	<input type="hidden" name="txtAccountDistirctIDList" value="<tbl:writeparam name="txtAccountDistirctIDList" />">
	    	<input type="text" name="txtAccountDistirctIDListValue" size="25" maxlength="200" readonly value="<tbl:writeparam name="txtAccountDistirctIDListValue" />" class="text">
      		<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:sel_districts('S,T,P','txtAccountDistirctIDList','txtAccountDistirctIDListValue','checkbox')">	
      		
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户支付类型</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtAccountMOPIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountMOPIDListValue" />">
	   	<input name="txtAccountMOPIDList" type="hidden" value="<tbl:writeparam name="txtAccountMOPIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('ALLMOPLIST','txtAccountMOPIDList',document.frmPost.txtAccountMOPIDList.value,'','','')"> 
	   </td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >账单创建起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAccountInvoiceCreateTime1)" onblur="lostFocus(this)" name="txtAccountInvoiceCreateTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountInvoiceCreateTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAccountInvoiceCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >账单创建截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAccountInvoiceCreateTime2)" onblur="lostFocus(this)" name="txtAccountInvoiceCreateTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountInvoiceCreateTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAccountInvoiceCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单状态</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtAccountInvoiceStatusListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountInvoiceStatusListValue" />">
	   	<input name="txtAccountInvoiceStatusList" type="hidden" value="<tbl:writeparam name="txtAccountInvoiceStatusList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_INVOICESTATUS','txtAccountInvoiceStatusList',document.frmPost.txtAccountInvoiceStatusList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单详细地址</td>
	   <td width="33%" class="list_bg1"><input name="txtAccountAddress" type="text" class="text" maxlength="100" size="25" value="<tbl:writeparam name="txtAccountAddress" />"></td>
       </tr>
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >银行账户串配状态</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtAccountBankAccountStatusListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtAccountBankAccountStatusListValue" />">
	   	<input name="txtAccountBankAccountStatusList" type="hidden" value="<tbl:writeparam name="txtAccountBankAccountStatusList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_ACCOUNTBANKACCOUNTSTATUS','txtAccountBankAccountStatusList',document.frmPost.txtAccountBankAccountStatusList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" ></td>
	   <td width="33%" class="list_bg1"></td>
       </tr>
 </table>


 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">客户产品查询条件</font></td>
	  <td class="import_tit" width="10%"><A href="javascript:drawSubMenu('1002')"><IMG id="arr1002" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu1002" style="display:none">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >状态</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCPStatusListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCPStatusListValue" />">
	   	<input name="txtCPStatusList" type="hidden" value="<tbl:writeparam name="txtCPStatusList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERPRODUCTPSTATUS','txtCPStatusList',document.frmPost.txtCPStatusList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >对应的运营商产品</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCPProductIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCPProductIDListValue" />">
	   	<input name="txtCPProductIDList" type="hidden" value="<tbl:writeparam name="txtCPProductIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCT','txtCPProductIDList',document.frmPost.txtCPProductIDList.value,'','','')"> 
	   </td>
      </tr>
             
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >创建的起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCreateTime1)" onblur="lostFocus(this)" name="txtCPCreateTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCreateTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >创建的截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCreateTime2)" onblur="lostFocus(this)" name="txtCPCreateTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCreateTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >促销活动开始起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCampaignStartTime1)" onblur="lostFocus(this)" name="txtCPCampaignStartTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCampaignStartTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCampaignStartTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >促销活动开始截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCampaignStartTime2)" onblur="lostFocus(this)" name="txtCPCampaignStartTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCampaignStartTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCampaignStartTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >促销活动结束起始时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCampaignEndTime1)" onblur="lostFocus(this)" name="txtCPCampaignEndTime1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCampaignEndTime1" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCampaignEndTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
	   <td valign="middle" class="list_bg2" align="right" >促销活动结束截止时间</td>
	   <td class="list_bg1"><font size="2">
		<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCPCampaignEndTime2)" onblur="lostFocus(this)" name="txtCPCampaignEndTime2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCampaignEndTime2" />">    		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCPCampaignEndTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
	   </font></td>
       </tr>
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >参加的促销活动列表</td>
	   <td width="33%" class="list_bg1">
	   	<input name="txtCPCampaignIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCPCampaignIDListValue" />">
	   	<input name="txtCPCampaignIDList" type="hidden" value="<tbl:writeparam name="txtCPCampaignIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('CAMPAIGN','txtCPCampaignIDList',document.frmPost.txtCPCampaignIDList.value,'','','')"> 
	   </td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >产品类别</td>
	   <td width="33%" class="list_bg1"><input name="txtCPClassIDListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCPClassIDListValue" />">
	   	<input name="txtCPClassIDList" type="hidden" value="<tbl:writeparam name="txtCPClassIDList" />">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_P_PRODUCTCLASS','txtCPClassIDList',document.frmPost.txtCPClassIDList.value,'','','')"></td>
       </tr>
 </table>
 <script language="JavaScript">
 	if(document.all.txtCustCurrentPoints1.value=="0")
 		document.all.txtCustCurrentPoints1.value="";
 	if(document.all.txtCustCurrentPoints2.value=="0")
 		document.all.txtCustCurrentPoints2.value="";
 	if(document.all.txtCustTotalPoints1.value=="0")
 		document.all.txtCustTotalPoints1.value="";
 	if(document.all.txtCustTotalPoints2.value=="0")
 		document.all.txtCustTotalPoints2.value=""; 		
 	if(document.frmPost.txtCustomerID.value=="0")
 		document.frmPost.txtCustomerID.value="";
 	if(document.frmPost.txtAccountCashBalance1.value=="0.00")
 		document.frmPost.txtAccountCashBalance1.value="";	
 	if(document.frmPost.txtAccountCashBalance2.value=="0.00")
 		document.frmPost.txtAccountCashBalance2.value="";	
 	if(document.frmPost.txtAccountTokenBalance1.value=="0.00")
 		document.frmPost.txtAccountTokenBalance1.value="";	
 	if(document.frmPost.txtAccountTokenBalance2.value=="0.00")
 		document.frmPost.txtAccountTokenBalance2.value="";	
 </script>
<!--<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>-->