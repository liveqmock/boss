<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern,java.text.DecimalFormat" %>

<SCRIPT language="JAVASCRIPT">
	function next_submit(){
		 document.frmPost.action='account_adjust_specialfee_op.do';
		 document.frmPost.submit();
	}
	function back_submit(){
		 document.frmPost.action='account_adjust_specialfee.screen';
		 document.frmPost.submit();
	}
	
</SCRIPT>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">�ʻ�����--Ӧ�շ���ȷ��</td>
      </tr>
		</table>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><font size="2" color="red">+��������Ӧ�շ��� -�������Ӧ�շ���</font></td>
      </tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>		
		<form name="frmPost" method="post" action="account_adjust_fee.do">
		<tbl:hiddenparameters pass="txtAdjustType/txtCustomerID" />
		<tbl:hiddenparameters pass="txtAccountID/txtAccountName/txtAmount/txtServiceAccountID/txtAdjustReason" />
		<tbl:hiddenparameters pass="txtBillingCycle/txtAdjustDate/txtFeeType/txtAcctItemType/txtFeeDate1/txtFeeDate2" />
		<tbl:hiddenparameters pass="txtFeeReMark/txtAdjustReMark" />
		<tbl:generatetoken />
		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					�ʻ���
				</td>
				<td class="list_bg1" width='33%'>
					<tbl:writeparam name="txtAccountID" />
				</td>
				<td class="list_bg2" align="right" width='17%'>
					�ʻ�����
				</td>
				<td class="list_bg1" width='33%'>
					<tbl:writeparam name="txtAccountName" />
				</td>
			</tr>
			<%
			   pageContext.setAttribute("acctItemTypeMap",Postern.getAcctItemTypeWithAdjust());
			   int accountID =WebUtil.StringToInt(request.getParameter("txtAccountID"));
			   String oldbalanceStr = Postern.getBalanceByAcctId(accountID);
			   double amount =WebUtil.StringToDouble(request.getParameter("txtAmount"));
			   double newbalance =WebUtil.StringToDouble(oldbalanceStr) - amount;			   
			   DecimalFormat df = new DecimalFormat("#0.00");
			%>
			<tr>
			  <td class="list_bg2" align="right" width='17%'>
					<font size="2" color="red">ԭ�ʻ������</font>
				</td>
				<td class="list_bg1" width='33%'>
				  <font size="2" color="red"><%=oldbalanceStr%></font>
				</td>
				<td class="list_bg2" align="right" width='17%'>
				  <font size="2" color="red">���ʻ������</font>
				</td>
				<td class="list_bg1" width='33%'>
				  <font size="2" color="red"><%=df.format(newbalance)%></font>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					���*
				</td>
				<td class="list_bg1" colspan="3">
					<font size="2" color="red"><tbl:writeparam name="txtAmount" /></font>
				</td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	ҵ���˻�
        </td>
        <td class="list_bg1" align="left">
		      <tbl:writeparam name="txtServiceAccountID" />
        </td>
        <td class="list_bg2" align="right">
        	����ԭ��*
        </td>
        <td class="list_bg1" align="left">
          <d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTREASON"  match="txtAdjustReason"  />
        </td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	�Ʒ�����
        </td>
        <td class="list_bg1" align="left">
					<tbl:writeparam name="txtBillingCycle"  />
        </td>
        <td class="list_bg2" align="right">
        	��������*
        </td>
        <td class="list_bg1" align="left">
          <tbl:writeparam name="txtAdjustDate" />
        </td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					��������*
				</td>
				<td class="list_bg1">
					<d:getcmnname typeName="SET_F_BRFEETYPE" match="txtFeeType" />
				</td>
				<td class="list_bg2"  align="right">
					��Ŀ����*
				</td>
				<td class="list_bg1">
					<d:getcmnname typeName="acctItemTypeMap" match="txtAcctItemType" />
				</td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	��������1
        </td>
        <td class="list_bg1" align="left">
          <tbl:writeparam name="txtFeeDate1" />
        </td>
        <td class="list_bg2" align="right">
        	��������2
        </td>
        <td class="list_bg1" align="left">
          <tbl:writeparam name="txtFeeDate2" />
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	���ñ�ע
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <tbl:writeparam name="txtFeeReMark" />
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	���˱�ע
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <tbl:writeparam name="txtAdjustReMark" />
        </td>
			</tr>
		</table>
		<br>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>      
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">��һ��</a></td>
        <td><img src="img/button2_l.gif" border="0" ></td>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:next_submit()" class="btn12">ȷ&nbsp;��</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>


 


