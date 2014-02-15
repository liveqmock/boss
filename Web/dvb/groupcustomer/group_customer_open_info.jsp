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
	if (check_Blank(document.frmPost.txtName, true, "集团名称"))
		return false;
  if (check_Blank(document.frmPost.txtType, true, "用户类型"))
		return false;		

  if (check_Blank(document.frmPost.txtTelephone, true, "客户固定电话"))
		return false;

  if (check_Blank(document.frmPost.txtOpenSourceType, true, "来源渠道"))
		return false;

  if (document.frmPost.txtOpenSourceType.value =="<%=CommonKeys.OPENSOURCETYPE_PROXY%>"){
    if (check_Blank(document.frmPost.txtOpenSourceID, true, "来源渠道ID"))
		  return false;
  }	
  
  if (document.frmPost.txtDistrict.value==""){
  	alert("所在区的值必填");
		return false;	
 	}

  if (check_Blank(document.frmPost.txtPostcode, true, "邮编"))
		return false;
		
  if (check_Blank(document.frmPost.txtDetailAddress, true, "详细地址"))
		return false;
		
	if (check_Blank(document.frmPost.txtAgentName, true, "代理人姓名"))
		return false;
		
  if (check_Blank(document.frmPost.txtAgentTelephone, true, "联系电话"))
		return false;

	if (document.frmPost.txtAgentCardType.value=='' && document.frmPost.txtAgentCardID.value=='') {
    alert("请输入证件信息！");
    document.frmPost.txtAgentCardType.focus();
    return false;
  }else if(document.frmPost.txtAgentCardType.value=='') {
    alert("请选择证件类型！");
    document.frmPost.txtAgentCardType.focus();
    return false;
  }else if (document.frmPost.txtAgentCardID.value=='') {
    alert("请输入证件号!");
    document.frmPost.txtAgentCardID.focus();
    return false;
  }
	
  if (check_Blank(document.frmPost.txtMopID, true, "付费类型"))
		return false;
	  
  if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
        if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value) ==''){
            alert("这种付费类型必须输入银行帐户及银行帐户名！");
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
              alert("这种付费类型不允许输入银行帐户及帐户户名!");
              return false;
         }
    }            
           
  if (check_Blank(document.frmPost.txtAccountType, true, "帐户类型"))
		return false;

  if (check_Blank(document.frmPost.txtAccountName, true,"帐户名"))
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
					<font size="2"> 正在获取内容。。。 </font>
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
						集团客户开户
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
							<font size="3">集团客户基本信息</font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" width="17%" align="right">
							合同编号
						</td>
						<td class="list_bg1" width="33%" align="left">
							<input type="text" name="txtContractNo" size="25" class="textgray" readonly
								value="<tbl:writeparam name="txtContractNo" />">
						</td>
						<td class="list_bg2" width="17%" align="right">
							集团名称*
						</td>
						<td class="list_bg1" width="33%" align="left">
							<input type="text" name="txtName" size="25" maxlength="50" value="<tbl:writeparam name="txtName" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							客户类型*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtType" mapName="SET_C_CUSTOMERTYPE" match="txtType" width="23" />
						</td>
						<td class="list_bg2" align="right">
							固定电话*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtTelephone" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							来源渠道*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23"
								onchange="ChangeOpenSourceType()" />
						</td>
						<td class="list_bg2" align="right">
							来源渠道ID
						</td>
						<td class="list_bg1" align="left">
							<d:selopensourceid name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" />
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" width="17%">
							所在区域*
						</td>
						<td class="list_bg1" align="left">
							<input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict" />">
							<input type="text" name="txtDistrictDesc" size="25" readonly
								value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="text">
							<input name="selDistButton" type="button" class="button" value="选择"
								onClick="javascript:sel_district('leaf','txtDistrict','txtDistrictDesc')">
						</td>
						<td class="list_bg2" align="right">
							邮编*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							详细地址*
						</td>
						<td class="list_bg1" align="left" colspan="3">
							<input type="text" name="txtDetailAddress" size="83" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />">
						</td>
					</tr>

					<tr>
						<td colspan="4" class="import_tit" align="center">
							<font size="3">代理人信息</font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							姓名*
						</td>
						<td class="list_bg1" align="left" >
							<input type="text" name="txtAgentName" size="25" maxlength="20" value="<tbl:writeparam name="txtAgentName" />">
						</td>
						<td class="list_bg2" align="right">
							联系电话*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtAgentTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtAgentTelephone" />">
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							证件类型*
						</td>
						<td class="list_bg1" align="left">
							<d:selcmn name="txtAgentCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" width="23" />
						</td>
						<td class="list_bg2" align="right">
							证件号*
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="txtAgentCardID" size="25" maxlength="50" value="<tbl:writeparam name="txtAgentCardID" />">
						</td>
					</tr>
					<tr>
						<td colspan="4" class="import_tit" align="center">
							<font size="3">帐户信息</font>
						</td>
					</tr>
					<tr>
						<td valign="middle" class="list_bg2" align="right">
							帐户类型*
						</td>
						<td class="list_bg1">
							<font size="2"> <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType"
									width="23" /> </font>
						</td>
						<td valign="middle" class="list_bg2" align="right">
							帐户名*
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtAccountName" size="25" maxlength="25"
									value="<tbl:writeparam name="txtAccountName" />" class="text"> </font>
						</td>
					</tr>
					<tr>
					<td valign="middle" class="list_bg2" align="right">
							付费类型*
						</td>
						<td class="list_bg1">
							<font size="2"> <tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" /> </font>
					</td>
					<td valign="middle" class="list_bg2" align="right">
							银行帐号
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtBankAccount" size="25" maxlength="25"
									value="<tbl:writeparam name="txtBankAccount" />" class="text"> </font>
						</td>
					</tr>
					<tr>
						<td valign="middle" class="list_bg2" align="right">
							银行帐户名
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text" name="txtBankAccountName" size="25" maxlength="25"
									value="<tbl:writeparam name="txtBankAccountName" />" class="text"> </font>
						</td>
						<td valign="middle" class="list_bg2">
							<div align="right">
								帐单所在区
							</div>
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
								<input type="text" name="txtbillCountyDesc" size="25" readonly
									value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text"> <input name="selDistButton1"
									type="button" class="button" value="选择"
									onClick="javascript:sel_district('leaf','txtbillCounty','txtbillCountyDesc')"> </font>
						</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">
							帐单寄送地址
						</td>
						<td class="list_bg1">
							<input type="text" name="txtBillDetailAddress" size="25" maxlength="100"
									value="<tbl:writeparam name="txtBillDetailAddress" />" class="text">
						</td>
						<td class="list_bg2" align="right">
							帐单寄送邮编
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
						<td height="20"><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="返　回"></td>
						<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
						<td width="20"></td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:next_submit()" value="下一步"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
					</tr>
				</table>
		</td>
	</tr>
</table>


