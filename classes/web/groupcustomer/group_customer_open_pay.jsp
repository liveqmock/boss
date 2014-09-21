<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="privilege" prefix="pri"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%
    Map mapBankMop=Postern.getAllMethodOfPayments();
    MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(request.getParameter("txtMopID"));
    String strMopName = null;
    if (dtoMOP!=null) strMopName=dtoMOP.getName();
    if (strMopName==null) strMopName="";
%>
<Script language=javascript>
function back_submit(){
	 document.frmPost.action="group_customer_open_fee.screen";
   document.frmPost.submit();
}
function next_submit(){
	 document.frmPost.action="group_customer_open_confirm.screen";
   document.frmPost.submit();
}
</SCRIPT>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
	<TR>
		<TD>
			<table border="0" align="center" cellpadding="0" cellspacing="10">
				<tr>
					<td>
						<img src="img/list_tit.gif" width="19" height="19">
					</td>
					<td class="title">
						集团客户信息
					</td>
				</tr>
			</table>
	    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	      <tr>
	        <td><img src="img/mao.gif" width="1" height="1"></td>
	      </tr>
	    </table>
			<br>
			<form name="frmPost" method="post">
        <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >   
        	
				<tbl:hiddenparameters pass="txtContractNo/txtName/txtType/txtTelephone/txtOpenSourceType/txtOpenSourceID/txtDistrict/txtDistrictDesc/txtPostcode/txtDetailAddress" />
				<tbl:hiddenparameters pass="txtAgentName/txtAgentCardType/txtAgentCardID/txtAgentTelephone" />			
				<tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />					
				
					<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
						<tr>
							<td colspan="4" class="import_tit" align="center">
								集团客户基本信息
							</td>
						</tr>
						<tr>
							<td class="list_bg2" width="17%" align="right">
								合同编号
							</td>
							<td class="list_bg1" width="33%" align="left">
								<tbl:writeparam name="txtContractNo" />
							</td>
							<td class="list_bg2" width="17%" align="right">
								集团名称
							</td>
							<td class="list_bg1" width="33%" align="left">
								<tbl:writeparam name="txtName" />
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								客户类型
							</td>
							<td class="list_bg1" align="left">
								<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtType"   />
							</td>
							<td class="list_bg2" align="right">
								固定电话
							</td>
							<td class="list_bg1" align="left">
								<tbl:writeparam name="txtTelephone" />
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								来源渠道
							</td>
							<td class="list_bg1" align="left">
								<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType"   />
							</td>
							<td class="list_bg2" align="right">
								来源渠道ID
							</td>
							<td class="list_bg1" align="left">
								<d:getorgname match="txtOpenSourceID" /> 
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right" >
								所在区域
							</td>
							<td class="list_bg1" align="left">
								<tbl:WriteDistrictInfo property="txtDistrict" />
							</td>
							<td class="list_bg2" align="right">
								邮编
							</td>
							<td class="list_bg1" align="left">
								<tbl:writeparam name="txtPostcode" />
							</td>
						</tr>												
						<tr>
							<td class="list_bg2" align="right">
								详细地址
							</td>
							<td class="list_bg1" align="left" colspan="3">
								<tbl:writeparam name="txtDetailAddress" />
							</td>
						</tr>

						<tr>
							<td colspan="4" class="import_tit" align="center">
								代理人信息
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								姓名
							</td>
							<td class="list_bg1" align="left">
								<tbl:writeparam name="txtAgentName" />
							</td>
							<td class="list_bg2" align="right">
								联系电话
							</td>
							<td class="list_bg1" align="left">
								<tbl:writeparam name="txtAgentTelephone" />
							</td>							
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								证件类型
							</td>
							<td class="list_bg1" align="left">
								<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType"   />		
							</td>
							<td class="list_bg2" align="right">
								证件号
							</td>
							<td class="list_bg1" align="left">
								<tbl:writeparam name="txtAgentCardID" />
							</td>
						</tr>
						<tr>

						</tr>
						<tr>
							<td colspan="4" class="import_tit" align="center">
								帐户信息
							</td>
						</tr>
						
						<tr>
							<td valign="middle" class="list_bg2" align="right">
								帐户类型
							</td>
							<td class="list_bg1">
								<d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType"   />		
							</td>
							<td valign="middle" class="list_bg2" align="right">
								帐户名
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtAccountName" />
							</td>
						</tr>
						
						<tr>
						<td valign="middle" class="list_bg2" align="right">
								付费类型
							</td>
							<td class="list_bg1">
								<%=strMopName%>
							</td>
							<td valign="middle" class="list_bg2" align="right">
								银行帐号
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBankAccount" />
							</td>
						</tr>
						<tr>
							<td valign="middle" class="list_bg2" align="right">
								银行帐户名
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBankAccountName" />
							</td>
							<td valign="middle" class="list_bg2" align="right">
								帐单所在区
							</td>
							<td class="list_bg1">
								<tbl:WriteDistrictInfo property="txtbillCounty" />
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								帐单寄送地址
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBillDetailAddress" />
							</td>
							<td class="list_bg2" align="right">
								帐单寄送邮编
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBillPostcode" />
							</td>
						</tr>
					</table>
					<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        		<tr>
        			<td> 
          			<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_GS%>" acctshowFlag ="false"   confirmFlag="false" />
        			</td>
        		</tr> 
      		</table>
			</form>

				<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
					<tr height="20">
						<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
						<td height="20"><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
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