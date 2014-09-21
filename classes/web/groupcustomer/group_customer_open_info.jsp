<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%
	pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 
	StringBuffer lstBankMop = Postern.getBankMopBuffer();
%>
<Script language=javascript>
var listBankMop=["9#9"<%=lstBankMop%>];	
function check_form(){
	if (check_Blank(document.frmPost.txtName, true, "��������"))
		return false;
  if (check_Blank(document.frmPost.txtType, true, "�û�����"))
		return false;		

  if (check_Blank(document.frmPost.txtTelephone, true, "�ͻ��̶��绰"))
		return false;

  if (check_Blank(document.frmPost.txtOpenSourceType, true, "��Դ����"))
		return false;

  if (document.frmPost.txtOpenSourceType.value =="<%=CommonKeys.OPENSOURCETYPE_PROXY%>"){
    if (check_Blank(document.frmPost.txtOpenSourceID, true, "��Դ����ID"))
		  return false;
  }	
  
  if (document.frmPost.txtDistrict.value==""){
  	alert("��������ֵ����");
		return false;	
 	}

  if (check_Blank(document.frmPost.txtPostcode, true, "�ʱ�"))
		return false;
		
  if (check_Blank(document.frmPost.txtDetailAddress, true, "��ϸ��ַ"))
		return false;
		
	if (check_Blank(document.frmPost.txtAgentName, true, "����������"))
		return false;
		
  if (check_Blank(document.frmPost.txtAgentTelephone, true, "��ϵ�绰"))
		return false;

	if (document.frmPost.txtAgentCardType.value=='' && document.frmPost.txtAgentCardID.value=='') {
    alert("������֤����Ϣ��");
    document.frmPost.txtAgentCardType.focus();
    return false;
  }else if(document.frmPost.txtAgentCardType.value=='') {
    alert("��ѡ��֤�����ͣ�");
    document.frmPost.txtAgentCardType.focus();
    return false;
  }else if (document.frmPost.txtAgentCardID.value=='') {
    alert("������֤����!");
    document.frmPost.txtAgentCardID.focus();
    return false;
  }
	
  if (check_Blank(document.frmPost.txtMopID, true, "��������"))
		return false;
	  
  if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
        if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value) ==''){
            alert("���ָ������ͱ������������ʻ��������ʻ�����");
            if (trim(document.frmPost.txtBankAccount.value)==''){
               document.frmPost.txtBankAccount.focus();
               return false;
            }
            if (trim(document.frmPost.txtBankAccountName.value)==''){
				       document.frmPost.txtBankAccountName.focus();
				       return false;
		        }
         } else if (document.frmPost.txtMopID.value == '<%=CommonKeys.METHOD_OF_PAYMENT_ICBC%>') {
             if (!validateICBCAccount(document.frmPost.txtBankAccount)) {
                 return false;
             }
         }   
    }else{
         if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
              alert("���ָ������Ͳ��������������ʻ����ʻ�����!");
              return false;
         }
    }            
           
  if (check_Blank(document.frmPost.txtAccountType, true, "�ʻ�����"))
		return false;

  if (check_Blank(document.frmPost.txtAccountName, true,"�ʻ���"))
    return false;
    
  return true;
}

function IndexOfBankMop(str)
{
    for (var i=0; i<listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;
    }

    return -1;
}
function back_submit(){
	document.frmPost.action="<bk:backurl property='group_customer_open_contract_query_result.do' />";
  document.frmPost.submit();
}

function next_submit(){
	if(check_form()){
		document.frmPost.action="group_customer_open_fee.do";
	  document.frmPost.submit();
	}
}

function ChangeOpenSourceType()
{
  document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');
}
</SCRIPT>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%">
				<div align="center">
					<font size="2"> ���ڻ�ȡ���ݡ����� </font>
			</td>
		</tr>
	</table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
	<TR>
		<TD>
			<table border="0" align="center" cellpadding="0" cellspacing="10">
				<tr>
					<td>
						<img src="img/list_tit.gif" width="19" height="19">
					</td>
					<td class="title">
						���ſͻ�����
					</td>
				</tr>
			</table>
	    <br>
	    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	      <tr>
	        <td><img src="img/mao.gif" width="1" height="1"></td>
	      </tr>
	    </table>
	    <br>
			<form name="frmPost" method="post">
				<input type="hidden" name="txtActionType" value="openFee">
				<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					<tr>
						<td colspan="4" class="import_tit" align="center">
							<font size="3">���ſͻ�������Ϣ</font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" width="17%" align="right">
							��ͬ���
						</td>
						<td class="list_bg1" width="33%" align="left">
							<input type="text" name="txtContractNo" size="25" class="textgray" readonly
								value="<tbl:writeparam name="txtContractNo" />">
						</td>
						<td class="list_bg2" width="17%" align="right">
							��������*
						</td>
						<td class="list_bg1" width="33%" align="left">
							<input type="text" name="txtName" size="25" maxlength="50" value="<tbl:writeparam name="txtName" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							�ͻ�����*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtType" mapName="SET_C_CUSTOMERTYPE" match="txtType" width="23" />
						</td>
						<td class="list_bg2" align="right">
							�̶��绰*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtTelephone" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							��Դ����*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23"
								onchange="ChangeOpenSourceType()" />
						</td>
						<td class="list_bg2" align="right">
							��Դ����ID
						</td>
						<td class="list_bg1" align="left">
							<d:selopensourceid name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" />
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" width="17%">
							��������*
						</td>
						<td class="list_bg1" align="left">
							<input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict" />">
							<input type="text" name="txtDistrictDesc" size="25" readonly
								value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="text">
							<input name="selDistButton" type="button" class="button" value="ѡ��"
								onClick="javascript:sel_district('leaf','txtDistrict','txtDistrictDesc')">
						</td>
						<td class="list_bg2" align="right">
							�ʱ�*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							��ϸ��ַ*
						</td>
						<td class="list_bg1" align="left" colspan="3">
							<input type="text" name="txtDetailAddress" size="83" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />">
						</td>
					</tr>

					<tr>
						<td colspan="4" class="import_tit" align="center">
							<font size="3">��������Ϣ</font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							����*
						</td>
						<td class="list_bg1" align="left" >
							<input type="text" name="txtAgentName" size="25" maxlength="20" value="<tbl:writeparam name="txtAgentName" />">
						</td>
						<td class="list_bg2" align="right">
							��ϵ�绰*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtAgentTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtAgentTelephone" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							֤������*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtAgentCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" width="23" />
						</td>
						<td class="list_bg2" align="right">
							֤����*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtAgentCardID" size="25" maxlength="50" value="<tbl:writeparam name="txtAgentCardID" />">
						</td>
					</tr>
					<tr>
						<td colspan="4" class="import_tit" align="center">
							<font size="3">�ʻ���Ϣ</font>
						</td>
					</tr>
					<tr>
						<td valign="middle" class="list_bg2" align="right">
							�ʻ�����*
						</td>
						<td class="list_bg1">
							<font size="2"> <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType"
									width="23" /> </font>
						</td>
						<td valign="middle" class="list_bg2" align="right">
							�ʻ���*
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtAccountName" size="25" maxlength="25"
									value="<tbl:writeparam name="txtAccountName" />" class="text"> </font>
						</td>
					</tr>
					<tr>
					<td valign="middle" class="list_bg2" align="right">
							��������*
						</td>
						<td class="list_bg1">
							<font size="2"> <tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" /> </font>
					</td>
					<td valign="middle" class="list_bg2" align="right">
							�����ʺ�
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtBankAccount" size="25" maxlength="25"
									value="<tbl:writeparam name="txtBankAccount" />" class="text"> </font>
						</td>
					</tr>
					<tr>
						<td valign="middle" class="list_bg2" align="right">
							�����ʻ���
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtBankAccountName" size="25" maxlength="25"
									value="<tbl:writeparam name="txtBankAccountName" />" class="text"> </font>
						</td>
						<td valign="middle" class="list_bg2">
							<div align="right">
								�ʵ�������
							</div>
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
								<input type="text" name="txtbillCountyDesc" size="25" readonly
									value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text"> <input name="selDistButton1"
									type="button" class="button" value="ѡ��"
									onClick="javascript:sel_district('leaf','txtbillCounty','txtbillCountyDesc')"> </font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							�ʵ����͵�ַ
						</td>
						<td class="list_bg1">
							<input type="text" name="txtBillDetailAddress" size="25" maxlength="100"
									value="<tbl:writeparam name="txtBillDetailAddress" />" class="text">
						</td>
						<td class="list_bg2" align="right">
							�ʵ������ʱ�
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtBillPostcode" size="25" maxlength="6"
									value="<tbl:writeparam name="txtBillPostcode" />" class="text"> </font>
						</td>
					</tr>
				</table>
			</form>
				<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
					<tr height="20">
						<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
						<td height="20"><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="������"></td>
						<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
						<td width="20"></td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:next_submit()" value="��һ��"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
					</tr>
				</table>
		</td>
	</tr>
</table>


