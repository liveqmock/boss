<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>

<%
    Map mapBankMop=Postern.getAllMethodOfPayments();
    MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(request.getParameter("txtMopID"));
    String strMopName = null;
    if (dtoMOP!=null) strMopName=dtoMOP.getName();
    if (strMopName==null) strMopName="";
    

%>


<Script language=JavaScript>
<!--

function back_submit(){
	document.frmPost.action ="group_to_single_info.do";
	document.frmPost.submit();
}

function check_frm()
{

}
        
function next_submit()
{
  document.frmPost.submit();
}

//-->
</Script>

<form name="frmPost" method="post" action="group_to_single_confirm.do">
	<input type="hidden" name="txtActionType" value="groupToSingleConfirm" />
	<input type="hidden" name="txtHiddenValues" value="txtCustomerID/txtGroupCustID/txtName/txtGender/txtBirthday/txtNationality/txtType/txtSocialSecCardID/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtFaxNumber/txtEmail/txtCounty/txtPostcode/txtDetailAddress/txtCatvID/txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName/txtCustAddressID/txtCustomerDTlastmod/txtAddressDTlastmod" />
	<tbl:hiddenparameters pass="txtCustomerID/txtGroupCustID/txtName/txtGender/txtBirthday/txtNationality/txtType/txtSocialSecCardID/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtFaxNumber/txtEmail/txtCounty/txtPostcode/txtDetailAddress/txtCatvID/txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName/txtCustAddressID/txtCustomerDTlastmod/txtAddressDTlastmod" />
	<tbl:hiddenparameters pass="txtOpenSourceType" />
	<table border="0" align="center" cellpadding="0" cellspacing="5">
		<tr>
			<td><img src="img/list_tit.gif" width="19" height="19"></td>
			<td class="title">
				�ͻ�������Ϣ
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>

	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">
				<font size="3">�ͻ�������Ϣ</font>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">
				����*
			</td>
			<td width="33%" class="list_bg1">
				<tbl:writeparam name="txtName" />
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">
				�Ա�
			</td>
			<td width="33%" class="list_bg1">
				<d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtGender"   />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				��������
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtBirthday" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				����
			</td>
			<td class="list_bg1">
				<d:getcmnname typeName="SET_C_NATIONALITY" match="txtNationality"   />
			</td>
		</tr>
		<tr>
		  <td valign="middle" class="list_bg2" align="right" >�û�����*</td>
		  <td class="list_bg1">
				<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtType"   />
			</td>			
			<td valign="middle" class="list_bg2" align="right">
				�籣�����
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtSocialSecCardID" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				֤������*
			</td>
			<td class="list_bg1">
				<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtCardType"   />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				֤����*
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtCardID" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				��ϵ�绰*
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtTelephone"/>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�ƶ��绰
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtMobileNumber" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				����
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtFaxNumber" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				EMAIL��ַ
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtEmail" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				������*
			</td>
			<td class="list_bg1">
				<tbl:WriteDistrictInfo property="txtCounty" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�ʱ�*
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtPostcode" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				��ϸ��ַ*
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtDetailAddress" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%>
			</td>
			<td class="list_bg1">
				<tbl:writeparam name="txtCatvID" />
			</td>
		</tr>

		<tbl:dynamicservey serveyType="M" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" />

						<tr>
							<td colspan="4" class="import_tit" align="center">
								�ʻ���Ϣ
							</td>
						</tr>
						<tr>
							<td class="list_bg2" align="right">
								�ʵ����͵�ַ
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBillDetailAddress" />
							</td>
							<td class="list_bg2" align="right">
								�ʵ������ʱ�
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBillPostcode" />
							</td>
						</tr>
						<tr>
							<td valign="middle" class="list_bg2" align="right">
								�ʵ�������
							</td>
							<td class="list_bg1">
								<tbl:WriteDistrictInfo property="txtbillCounty" />
							</td>
							<td valign="middle" class="list_bg2" align="right">
								��������
							</td>
							<td class="list_bg1">
								<%=strMopName%>
							</td>
						</tr>
						<tr>
							<td valign="middle" class="list_bg2" align="right">
								�����ʻ�
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBankAccount" />
							</td>
							<td valign="middle" class="list_bg2" align="right">
								�����ʻ���
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtBankAccountName" />
							</td>
						</tr>
						<tr>
							<td valign="middle" class="list_bg2" align="right">
								�ʻ�����
							</td>
							<td class="list_bg1">
								<d:getcmnname typeName="SET_F_ACCOUNTTYPE" match="txtAccountType"   />		
							</td>
							<td valign="middle" class="list_bg2" align="right">
								�ʻ���
							</td>
							<td class="list_bg1">
								<tbl:writeparam name="txtAccountName" />
							</td>
						</tr>
	</table>
					<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        		<tr>
        			<td> 
          			<jsp:include page="customer_buyFreeInfomation.jsp"/>
        			</td>
        		</tr> 
      		</table>
      			<BR>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="back" type="button" class="button" onClick="back_submit()" value="��һ��"></td>
			<td><img src="img/button2_l.gif" width="11" height="20"></td>
			<td width="20"></td>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="next" type="button" class="button" onClick="next_submit()" value="ȷ ��"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	<tbl:generatetoken />
</form>
