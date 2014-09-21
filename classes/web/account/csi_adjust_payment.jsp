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
			var strUrl='csi_adjust_op.do';
			document.frmPost.action=strUrl;
			document.frmPost.submit();
		}
	}
	function back_submit(){
		document.frmPost.action='csi_adjust.do';
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
		var mop=document.frmPost.MOP.value;
		var start=mop.indexOf("-")+1;
		var end=mop.indexOf("_");
		var payType=mop.substring(start,end);
		if(payType=='<%=CommonKeys.MOPPAYTYPE_DK%>'){
			if(check_Blank(document.frmPost.txtTicketNO,true,"�ֿ�ȯ��")){
				return false;
			}
		}		
		if(check_Blank(document.frmPost.txtAdjustReason,true,"����ԭ��")){
			return false;
		}
		if(check_Blank(document.frmPost.MOP,true,"֧����ʽ")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAdjustDate, true, "��������")||!check_TenDate(document.frmPost.txtAdjustDate,true,"��������")){
			return false;
		}
		if(check_Blank(document.frmPost.txtCollectorName,true,"�շ���")){
			return false;
		}
		if(!checkCollector(document.frmPost.txtCollectorName))return false;
		if(check_Blank(document.frmPost.txtCollectorDate, true, "�շ�����")||!check_TenDate(document.frmPost.txtCollectorDate,true,"�շ�����")){
			return false;
		}

		return true;
	}	
	function checkCollector(a){
		var operatorLoginIDList="<%=Postern.getOperationLoginIDList()%>";
		var arrCollector=operatorLoginIDList.split(",");
		var curID=trim(a.value);
		for(i=0;i<arrCollector.length;i++){
			if(arrCollector[i]==curID)
				return true;
		}		
		alert("��Ч���շ���!");
		a.focus();
		return false;
	}	
</SCRIPT>
<%
	pageContext.setAttribute("AllMOPList" ,Postern.getOpeningPaymentMop(CommonKeys.METHODOFPAYMENT_SLTZ));
 	pageContext.setAttribute("ACCOUNTMAP",Postern.getAccountNameMapByCustID(Integer.parseInt(request.getParameter("txtCustomerID"))));
	
%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">���޵�����--ʵ��֧��</td>
      </tr>
		</table>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><font size="2" color="red">+��������֧�� -�������֧��</font></td>
      </tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>		
		<form name="frmPost" method="post" action="csi_adjust_op.do">
		<tbl:hiddenparameters pass="txtAdjustType" />
		<tbl:generatetoken />	

		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right">
					�������
				</td>
				<td class="list_bg1">
					<input type="text" name="txtID" size="25" maxlength="25" value="<tbl:writeparam name="txtID" />" class="textgray" readonly>
				</td>
				<td class="list_bg2" align="right" width='17%'>
					��������
				</td>
				<td class="list_bg1" width='33%'>
					<input type='text' name='txtTypeShow' size='25' maxlength=='25' value='<tbl:writeparam name="txtTypeShow" />' class="textgray" readonly>
				</td>
			</tr>			
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					�ͻ���
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtCustomerID" size="25" maxlength="25" value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly>
				</td>
				<td class="list_bg2" align="right" width='17%'>
					�ͻ�����
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtName" size="25" maxlength="25" value="<tbl:writeparam name="txtName" />" class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					֧����ʽ*
				</td>
				<td class="list_bg1" >
					<tbl:select name="MOP" set="AllMOPList" match="MOP" width="23" />
				</td>
				<td class="list_bg2" align="right" width='17%'>
					�ʻ���
				</td>
				<td class="list_bg1"  >
					<tbl:select name="txtAccountID" set="ACCOUNTMAP" match="txtAccountID" empty="false" style="width:185px;"/>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					���ʽ��*
				</td>
				<td class="list_bg1">
					<input type="text" name="txtAmount" size="25" maxlength='8' value="<tbl:writeparam name="txtAmount"/>" >
				</td>
				<td class="list_bg2"  align="right">
					�ֿ�ȯ��
				</td>
				<td class="list_bg1">
					<input type='text' name='txtTicketNO' size='25' maxlength='15' value='<tbl:writeparam name="txtTicketNO" />'>
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
					�շ���*
				</td>
				<td class="list_bg1">
					<input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
					<input type="text" name="txtCollectorName" size="25" value="<tbl:writeparam name="txtCollectorName"/>" >
					<input name="selOperButton" type="button" class="button" value="��ѯ" 
					onClick="javascript:query_window('�շ��˲�ѯ','txtCollectorID','txtCollectorName','query_operator.do?txtStatus=V')">
				</td>
        <td class="list_bg2" align="right">
        	�շ�����*
        </td>
        <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCollectorDate)" onblur="lostFocus(this)" type="text" name="txtCollectorDate" size="25" value="<tbl:writeparam name="txtCollectorDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCollectorDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	֧����ע
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtPayReMark" value="<tbl:writeparam name="txtPayReMark" />" size="83" maxlength='100' class="text">
        </td>
			</tr>			
      <tr>
        <td class="list_bg2" align="right">
        	���˱�ע
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


 