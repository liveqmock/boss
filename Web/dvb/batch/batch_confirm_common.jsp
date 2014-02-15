<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>



 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">客户查询条件</font></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu1" style="">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户类型</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustTypeListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户状态</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustStatusListValue" /></td>
      </tr>
       	
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >客户创建起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCustCreateTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >客户创建截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCustCreateTime2" /></font></td>
       </tr>
	 
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >开户来源渠道</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustOpenSourceTypeListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >开户来源渠道ID</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustOpenSourceIDListValue" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户当前可用积分下限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustCurrentPoints1" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户当前可用积分上限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustCurrentPoints2" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户历史总积分下限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustTotalPoints1" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户历史总积分上限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustTotalPoints2" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户姓名</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustName" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户地址</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustAddress" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >用户证号</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustomerID" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >客户所在区域</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCustDistrictIDListValue" /></td>
       </tr>
 </table>

 
 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">账户查询条件</font></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu2" style="">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户类型</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountTypeListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户状态</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountStatusListValue" /></td>
      </tr>
      
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户真实货币余额下限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountCashBalance1" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户真实货币余额上限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountCashBalance2" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户虚拟货币余额下限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountTokenBalance1" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户虚拟货币余额上限</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountTokenBalance2" /></td>
       </tr>
       
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >账户创建起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtAccountCreateTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >账户创建截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtAccountCreateTime2" /></font></td>
       </tr>
	 
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单地址所在区域</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountDistirctIDListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账户支付类型</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountMOPIDListValue" /></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >账单创建起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtAccountInvoiceCreateTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >账单创建截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtAccountInvoiceCreateTime2" /></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单状态</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountInvoiceStatusListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >账单详细地址</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountAddress" /></td>
       </tr>
       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >银行账户串配状态</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtAccountBankAccountStatusListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" ></td>
	   <td width="33%" class="list_bg1"></td>
       </tr>       
 </table>


 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">客户产品查询条件</font></td>
      </tr>
 </table>
  <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1"  class="list_bg" id="mnu3" style="">
      <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >状态</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCPStatusListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >对应的运营商产品</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCPProductIDListValue" /></td>
      </tr>
             
      <tr>
	   <td valign="middle" class="list_bg2" align="right" >创建的起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCreateTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >创建的截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCreateTime2" /></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >促销活动开始的起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCampaignStartTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >促销活动开始的截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCampaignStartTime2" /></font></td>
       </tr>
       
       <tr>
	   <td valign="middle" class="list_bg2" align="right" >促销活动结束的起始时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCampaignEndTime1" /></font></td>
	   <td valign="middle" class="list_bg2" align="right" >促销活动结束的截止时间</td>
	   <td class="list_bg1"><font size="2"><tbl:writeparam name="txtCPCampaignEndTime2" /></font></td>
       </tr>

       <tr>
	   <td valign="middle" class="list_bg2" align="right" width="17%" >参加的促销活动列表</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCPCampaignIDListValue" /></td>	   
	   <td valign="middle" class="list_bg2" align="right" width="17%" >产品类别</td>
	   <td width="33%" class="list_bg1"><tbl:writeparam name="txtCPClassIDListValue" /></td>
       </tr>
 </table>
 <!--<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>-->


      
