<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>


<Script language=javascript>
function checkStrLen(value){
 var str,Num = 0;
 for (var i=0;i<value.length;i++){
  str = value.substring(i,i+1);
  if (str<="~")  //�ж��Ƿ�˫�ֽ�
   Num+=1;
  else
   Num+=2;
 }
 return Num;
}
function send_mail_submit()
{
	if(document.frmPost.content.value == "")
    {
    	alert("����д�������ݣ�");
    	document.frmPost.content.focus();
    	return false;
    }
    if(checkStrLen(document.frmPost.content.value)>84){
    
    	alert("�������ݲ��ܳ���84���ֽڣ�");
    	document.frmPost.content.focus();
    	return false;
    }
    
	document.frmPost.action="send_mail_confirm.do";
	document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_SENDMAIL%>";
	document.frmPost.submit();
}

</SCRIPT>

<form name="frmPost" method="post">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID"/>">
<input type="hidden" name="txtProdID" value="<tbl:writeparam name="txtProdID"/>">  
<input type="hidden" name="func_flag" value="<tbl:writeparam name="func_flag"/>">
<input type="hidden" name="Action" value="<tbl:writeparam name="Action"/>">
<input type="hidden" name="txtActionType" value="<tbl:writeparam name="txtActionType"/>">
<input type="hidden" name="confirm_post" value="true">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">������Ϣ</td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr>
		<td valign="middle" class="list_bg2"  align="right" width="17%">��Ʒ���к�</td>
		<td class="list_bg1" width="33%">
			<font size="2">
			<input type="text" name="txtPsID" size="25" value="<tbl:writeparam name="txtPsID"/>" class="textgray" readonly >
			</font>
		</td> 
		<td valign="middle" class="list_bg2"  align="right" width="17%">��Ʒ����</td>
		<td class="list_bg1" width="33%">
			<font size="2">
			<input type="text" name="txtProductName" size="25" value="<tbl:writeparam name="txtProductName"/>" class="textgray" readonly >
			</font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >��Ʒ������</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtPackageName" size="25" value="<tbl:writeparam name="txtPackageName"/>" class="textgray" readonly >         
			</font>
		</td>
		<td valign="middle" class="list_bg2"  align="right" >��ͬ��</td>
		<td class="list_bg1" >
			<font size="2">
			<input type="text" name="txtContractNo" size="25" value="<tbl:writeparam name="txtContractNo"/>" class="textgray" readonly >
			</font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >�û���</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtCustomerName" size="25" value="<tbl:writeparam name="txtCustomerName"/>" class="textgray" readonly >         
			</font>
		</td>
		<td valign="middle" class="list_bg2"   align="right" >״̬</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtStatusShow" size="25" value="<tbl:writeparam name="txtStatusShow"/>" class="textgray" readonly >
			</font>
		</td>
	</tr> 
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >ҵ���ʺ�</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtServiceAccountID" size="25" value="<tbl:writeparam name="txtServiceAccountID"/>" class="textgray" readonly >
			</font>
		</td>
		<td valign="middle" class="list_bg2"   align="right">�ʺ�</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtAccount" size="25" value="<tbl:writeparam name="txtAccountID"/>" class="textgray" readonly >
			</font>
		</td>  
	</tr>
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >�豸���к�</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtSerialNo" size="25" value="<tbl:writeparam name="txtSerialNo"/>" class="textgray" readonly >
			</font>
		</td>
		<td valign="middle" class="list_bg2"  align="right" >�豸��</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtDeviceID" size="25" value="<tbl:writeparam name="txtDeviceID"/>" class="textgray" readonly >
			</font>
		</td>
	</tr> 
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >�豸����</td>
		<td class="list_bg1">
			<font size="2">
			<input type="hidden" name="txtDeviceClass" value="<tbl:writeparam name="txtDeviceClass"/>" >
			<input type="text" name="txtDeviceClassShow" size="25" value="<tbl:writeparam name="txtDeviceClassShow"/>" class="textgray" readonly >
			</font>
		</td>
		<td valign="middle" class="list_bg2"  align="right" >�豸�ͺ�</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtDeviceModelShow" size="25" value="<tbl:writeparam name="txtDeviceModelShow"/>" class="textgray" readonly >
			</font>
		</td>
	</tr>          
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >����ʱ��</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtCreateTime" size="25" value="<tbl:writeparam name="txtCreateTime"/>" class="textgray" readonly >
			</font>
		</td>
		<td valign="middle" class="list_bg2"  align="right" >��ͨʱ��</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtActivateTime" size="25" value="<tbl:writeparam name="txtActivateTime"/>" class="textgray" readonly >
			</font>
		</td>
	</tr>
	<tr>
		<td valign="middle" class="list_bg2"  align="right" >��ͣʱ��</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtPauseTime" size="25" value="<tbl:writeparam name="txtPauseTime"/>" class="textgray" readonly >
			</font>
		</td>
		<td valign="middle" class="list_bg2"  align="right" >ȡ��ʱ��</td>
		<td class="list_bg1">
			<font size="2">
			<input type="text" name="txtCancelTime" size="25" value="<tbl:writeparam name="txtCancelTime"/>" class="textgray" readonly >
			</font>
		</td>
	</tr>
	<tr>
	<td valign="middle" class="list_bg2"  align="right" >��ʼ��Ч����</td>
	<td class="list_bg1">
		<font size="2">
		<input type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
		</font>
	</td>
	<td valign="middle" class="list_bg2"  align="right" >��ֹ��Ч����</td>
	<td class="list_bg1">
		<font size="2">
		<input type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
		</font>
	</td>
	</tr>
	<tr>
      	<td class="list_bg2" align="right">��������</td>
      	<td class="list_bg1" colspan=3><font color="red">��ע�⣺���͵����ݲ��ܳ���84���ֽڣ����лس�����ռ2���ֽ�</font><br>
      	<textarea id=content name="content" cols="81" rows="2" value=""></textarea>
      	</td>
    </tr>
</table>
<BR>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
		<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<bk:canback url="customer_product_view.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="customer_product_view.do"/>" class="btn12">��һ��</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>
				<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" onclick="javascript:send_mail_submit()" value="������Ϣ"></td>
				<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
			</tr>
		</table></td>
	</tr>
</table>
</form>