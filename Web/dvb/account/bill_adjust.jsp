<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<SCRIPT language="JAVASCRIPT">
	function next_submit(){
		var adjustType=document.frmPost.txtAdjustType.value;
		var d='<%=CommonKeys.ADJUST_REFERCODETYPE_D%>';//抵扣记录
		var f='<%=CommonKeys.ADJUST_REFERCODETYPE_F%>';//费用记录
		var p='<%=CommonKeys.ADJUST_REFERCODETYPE_P%>';//预存记录
		var c='<%=CommonKeys.ADJUST_REFERCODETYPE_C%>';//支付记录
		var strUrl='';
		if(adjustType==d){
			strUrl='bill_adjust_prepaymentdeduction.screen';
		}else if (adjustType==f){
			strUrl='bill_adjust_fee.screen';
		}else if (adjustType==p){
			strUrl='bill_adjust_prepayment.screen';
		}else if (adjustType==c){
			strUrl='bill_adjust_payment.screen';
		}
		if(strUrl!=''){
			document.frmPost.action=strUrl;
			document.frmPost.submit();			
		}else{
			alert("请选择一种调帐类型!");
			document.frmPost.txtAdjustType.focus();
		}
	}
</SCRIPT>
<%
//暂时没有实现预存抵扣调帐,先临时拼一个MAP,
LinkedHashMap AdjustTypeMap=new LinkedHashMap();
AdjustTypeMap.put("C","支付调帐");
AdjustTypeMap.put("F","费用调帐");
AdjustTypeMap.put("P","预存调帐");
pageContext.setAttribute("AdjustType",AdjustTypeMap);
%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">帐单调帐--选择调帐类型</td>
      </tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>		
		<form name="frmPost" method="post" action="account_adjust.do">
		<tbl:hiddenparameters pass="txtCustomerID" />

		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					帐户号
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtAccountID" size="25"  value="<tbl:writeparam name="txtAccountID" />" class="textgray" readonly >
				</td>
				<td class="list_bg2" align="right" width='17%'>
					客户号
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtCustomerID" size="25" maxlength="25" value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					帐单号
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtInvoiceNo" size="25"  value="<tbl:writeparam name="txtInvoiceNo" />" class="textgray" readonly >
				</td>
				<td class="list_bg2" align="right" width='17%'>
				</td>
				<td class="list_bg1" width='33%'>
				</td>
			</tr>			
      <tr>
        <td class="list_bg2" align="right">
        	帐单条形码
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtBarCode" value="<tbl:writeparam name="txtBarCode" />" size="83" maxlength='100' class="textgray" readonly>
        </td>
			</tr>			
	   	<tr>
				<td class="list_bg2" align="right">
					待付金额
				</td>
				<td class="list_bg1">
					<input type="text" name="txtBillAmount" size="25" maxlength="25" value="<tbl:writeparam name="txtBillAmount" />" class="textgray" readonly>
				</td>
				<td class="list_bg2" align="right" width='17%'>
					上期余额结转
				</td>
				<td class="list_bg1" width='33%'>
					<input type='text' name='txtBillBcf' size='25' maxlength=='25' value='<tbl:writeparam name="txtBillBcf" />' class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2" align="right">
					调帐类型
				</td>
				<td class="list_bg1">
					<tbl:select name="txtAdjustType" set="AdjustType" width="23" match="txtAdjustType" />
					<!--<d:selcmn name="txtAdjustType" mapName="SET_F_ADJUSTMENTREFERRECORDTYPE" match="txtAdjustType" width="23" />-->
				</td>
				<td class="list_bg2">
				</td>
				<td class="list_bg1">
				</td>
			</tr>
		</table>
		<br>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>      
    		<bk:canback url="bill_view.do" >
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="bill_view.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
        </bk:canback>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:next_submit()" class="btn12">下一步</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>


 


