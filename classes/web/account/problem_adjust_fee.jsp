<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<SCRIPT language="JAVASCRIPT">
	function next_submit(){
		if(check_from()){
			var strUrl='problem_adjust_op.do';
			document.frmPost.action=strUrl;
			document.frmPost.submit();
		}
	}
	function back_submit(){
		document.frmPost.action='problem_adjust.do';
		document.frmPost.submit();
	}
	function check_from(){
		if(check_Blank(document.frmPost.txtAmount, true, "���")||!check_Float(document.frmPost.txtAmount,true,"���")){
			return false;
		}
		if(document.frmPost.txtAmount.value==0){
			alert("��Ч�Ľ��!");
			document.frmPost.txtAmount.focus();
			return false;
		}		
		if(check_Blank(document.frmPost.txtAdjustReason,true,"����ԭ��")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAdjustDate, true, "��������")||!check_TenDate(document.frmPost.txtAdjustDate,true,"��������")){
			return false;
		}
		if(check_Blank(document.frmPost.txtFeeType,true,"��������")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAcctItemType,true,"��Ŀ����")){
			return false;
		}
		return true;
	}	
function ChangeFeeType()
{
   document.FrameUS.submit_update_select('feetype', document.frmPost.txtFeeType.value, 'txtAcctItemType', '');
}		
</SCRIPT>

<%
	pageContext.setAttribute("acctItemTypeMap",Postern.getAcctItemTypeWithAdjust());
 	pageContext.setAttribute("BILLINGCYCLE",Postern.getAllChargeCycle());
	
%>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> ���ڻ�ȡ���ݡ����� </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">���޵�����--Ӧ�շ���</td>
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
		<form name="frmPost" method="post" action="problem_adjust_op.do">
		<tbl:hiddenparameters pass="txtAdjustType" />
		<tbl:generatetoken />
		
		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >    
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					���޵����
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtID" size="25"   value="<tbl:writeparam name="txtID" />" class="textgray" readonly>
				</td>
				<td class="list_bg2" align="right" width='17%'>
					���޵�״̬
				</td>
				<td class="list_bg1" width='33%'>
					<input type='text' name='txtStatusShow' size='25' value='<tbl:writeparam name="txtStatusShow" />' class="textgray" readonly>
				</td>
			</tr>			
	   	<tr>
				<td class="list_bg2" align="right" >
					�ͻ���
				</td>
				<td class="list_bg1" >
					<input type="text" name="txtCustomerID" size="25" value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly>
				</td>
				<td class="list_bg2" align="right" >
					�ͻ�����
				</td>
				<td class="list_bg1"  >
					<input type="text" name="txtSurname" size="25"  value="<tbl:writeparam name="txtSurname" />" class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2" align="right"  >
					�ʻ���
				</td>
				<td class="list_bg1"   colspan="3">
					<input type="text" name="txtAccountID" size="25"  value="<tbl:writeparam name="txtAccountID" />" class="textgray" readonly >
				</td>
			</tr>						  
	   	<tr>
				<td class="list_bg2"  align="right">
					���ʽ��*
				</td>
				<td class="list_bg1" >
					<input type="text" name="txtAmount" size="25" value="<tbl:writeparam name="txtAmount"/>" >
				</td>
        <td class="list_bg2" align="right">
        	�Ʒ�����
        </td>
        <td class="list_bg1" align="left">
					<tbl:select name="txtBillingCycle" set="BILLINGCYCLE"  match="txtBillingCycle" width="23" />
        </td>
			</tr>
	   	<tr>
        <td class="list_bg2" align="right">
        	����ԭ��*
        </td>
        <td class="list_bg1" align="left">
          <d:selcmn name="txtAdjustReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON" match="txtAdjustReason" width="23" />
        </td>
        <td class="list_bg2" align="right">
        	��������*
        </td>
        <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAdjustDate)" onblur="lostFocus(this)" type="text" name="txtAdjustDate" size="25" value="<tbl:writeparam name="txtAdjustDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAdjustDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					��������*
				</td>
				<td class="list_bg1">
					<d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE"  match="txtFeeType" onchange="ChangeFeeType()" width="23" />
				</td>
				<td class="list_bg2"  align="right">
					��Ŀ����*
				</td>
				<td class="list_bg1">
					<tbl:select name="txtAcctItemType" set="acctItemTypeMap" width="23" match="txtAcctItemType" />
				</td>
			</tr>			
			
      <tr>
        <td class="list_bg2" align="right">
        	���ñ�ע
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtFeeReMark" value="<tbl:writeparam name="txtFeeReMark" />" size="83" maxlength='100' class="text">
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	���˱�ע
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtAdjustReMark" value="<tbl:writeparam name="txtadjustReMark" />" size="83" maxlength='100' class="text">
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
        <td background="img/button_bg.gif"><a href="javascript:next_submit()" class="btn12">ȷ&nbsp&nbsp��</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>


 