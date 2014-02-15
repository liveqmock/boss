<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility " %>

<SCRIPT language="JAVASCRIPT">
	function next_submit(){
		if(check_from()){
			var strUrl='account_adjust_fee.do';
			document.frmPost.action=strUrl;
			document.frmPost.submit();
		}
	}
	function back_submit(){
		document.frmPost.action='account_adjust.do';
		document.frmPost.submit();
	}
	function check_from(){
		if(check_Blank(document.frmPost.txtAmount, true, "金额")||!check_Float(document.frmPost.txtAmount,true,"金额")){
			return false;
		}
		if(document.frmPost.txtAmount.value==0){
			alert("无效的金额!");
			document.frmPost.txtAmount.focus();
			return false;
		}		
		if(check_Blank(document.frmPost.txtAdjustReason,true,"调帐原因")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAdjustDate, true, "调帐日期")||!check_TenDate(document.frmPost.txtAdjustDate,true,"调帐日期")){
			return false;
		}
		if(check_Blank(document.frmPost.txtFeeType,true,"费用类型")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAcctItemType,true,"帐目类型")){
			return false;
		}
		if(!check_Blank(document.frmPost.txtFeeDate1, false, "费用日期1")&&!check_TenDate(document.frmPost.txtFeeDate1,true,"费用日期1")){
			return false;
		}
		if(!check_Blank(document.frmPost.txtFeeDate2, false, "费用日期2")&&!check_TenDate(document.frmPost.txtFeeDate2,true,"费用日期2")){
			return false;
		}
		if(!check_Blank(document.frmPost.txtFeeDate1, false, "费用日期1")&&!check_Blank(document.frmPost.txtFeeDate2, false, "费用日期2")&&!compareDate(document.frmPost.txtFeeDate1,document.frmPost.txtFeeDate2,"费用日期2必须大于等于费用日期1")){
			return false;
		}				
		return true;
	}
function ChangeFeeType()
{
   document.FrameUS.submit_update_select('feetype', document.frmPost.txtFeeType.value, 'txtAcctItemType', '');
}	
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<%
	pageContext.setAttribute("acctItemTypeMap",Postern.getAcctItemTypeWithAdjust());
 	pageContext.setAttribute("SAMAP",Postern.getServiceAccountIDMapByCustID(Integer.parseInt(request.getParameter("txtCustomerID"))));
 	pageContext.setAttribute("BILLINGCYCLE",Postern.getAllChargeCycle());
	String txtAdjustDate =request.getParameter("txtAdjustDate");
	if (txtAdjustDate ==null){
	   txtAdjustDate =WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd");
	}
%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">帐户调帐--应收费用</td>
      </tr>
		</table>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><font size="2" color="red">+代表增加应收费用 -代表减少应收费用</font></td>
      </tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>		
		<form name="frmPost" method="post" action="account_adjust_fee.do">
		<tbl:hiddenparameters pass="txtAdjustType/txtCustomerID" />
		<tbl:generatetoken />

		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					帐户号
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtAccountID" size="25"  value="<tbl:writeparam name="txtAccountID" />" class="textgray" readonly >
				</td>
				<td class="list_bg2" align="right" width='17%'>
					帐户名称
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtAccountName" />" class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					金额*
				</td>
				<td class="list_bg1" colspan="3">
					<input type='text' name='txtAmount' size='25' maxlength='8' value='<tbl:writeparam name="txtAmount" />'>
				</td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	业务账户
        </td>
        <td class="list_bg1" align="left">
		      <tbl:select name="txtServiceAccountID" set="SAMAP" match="txtServiceAccountID" empty="false" width="23" style="width:185px;"/>
        </td>
        <td class="list_bg2" align="right">
        	调账原因*
        </td>
        <td class="list_bg1" align="left">
          <d:selcmn name="txtAdjustReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON" match="txtAdjustReason" width="23" />
        </td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	计费周期
        </td>
        <td class="list_bg1" align="left">
					<tbl:select name="txtBillingCycle" set="BILLINGCYCLE"  match="txtBillingCycle" width="23" />
        </td>
        <td class="list_bg2" align="right">
        	调账日期*
        </td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtAdjustDate" size="25" value="<%=txtAdjustDate%>" >&nbsp;<IMG onclick="calendar(document.frmPost.txtAdjustDate)" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					费用类型*
				</td>
				<td class="list_bg1">
					<d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE"  match="txtFeeType" onchange="ChangeFeeType()" width="23" />
				</td>
				<td class="list_bg2"  align="right">
					帐目类型*
				</td>
				<td class="list_bg1">
					<tbl:select name="txtAcctItemType" set="acctItemTypeMap" width="23" match="txtAcctItemType" />
				</td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	费用日期1
        </td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtFeeDate1" size="25" value="<tbl:writeparam name="txtFeeDate1"/>" >&nbsp;<IMG onclick="calendar(document.frmPost.txtFeeDate1)" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
        <td class="list_bg2" align="right">
        	费用日期2
        </td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtFeeDate2" size="25" value="<tbl:writeparam name="txtFeeDate2"/>" >&nbsp;<IMG onclick="calendar(document.frmPost.txtFeeDate2)" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	费用备注
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtFeeReMark" value="<tbl:writeparam name="txtFeeReMark" />" size="83" maxlength='100' class="text">
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	调账备注
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtAdjustReMark" value="<tbl:writeparam name="txtAdjustReMark" />" size="83" maxlength='100' class="text">
        </td>
			</tr>
		</table>
		<br>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>      
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
        <td><img src="img/button2_l.gif" border="0" ></td>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:next_submit()" class="btn12">确&nbsp&nbsp认</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>


 


