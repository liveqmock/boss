<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<Script language=javascript>
function create_submit(){
    document.frmPost.confirm_post.value="true";
    document.frmPost.action="customer_move_create_commit.do";
    document.frmPost.submit();
}

function back_submit(){
    document.frmPost.confirm_post.value="false";
    if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="customer_move_fee.screen";
	}else{
    	document.frmPost.forwardFlag.value = '1';
    	document.frmPost.action="customer_move_pay.screen";
    }
    document.frmPost.submit();
}

</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ȷ��Ǩ����Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="forwardFlag" value="">
    <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption" />" >  
    <tbl:hiddenparameters pass="txtCustomerDTlastmod/txtAddressDTlastmod/txtAddressID/func_flag" />
    <tbl:hiddenparameters pass="txtGender/txtCardType/txtDistrict/txtNewDistrict/txtCustomerType/txtCustomerStatus/txtOpenSourceType/txtNewDistrict" />
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2" align="right" width="17%">�ͻ�֤��</td>
            <td class="list_bg1" align="left" width="33%">
               <input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right" width="17%">��<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
            <td class="list_bg1" align="left" width="33%" >
               <input type="text" name="txtOldCatvID" size="25" maxlength="10" value="<tbl:writeparam name="txtOldCatvID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�ͻ�����</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">�ͻ�״̬</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="txtCustomerStatus"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">��Դ����</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtOpenSourceTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">��������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtDtCreateShow" size="25"  value="<tbl:writeparam name="txtDtCreateShow"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">����</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">�Ա�</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtcustGender" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtGender"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">֤������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtcustCardType" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtCardType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">֤������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtCardID" size="25"  value="<tbl:writeparam name="txtCardID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">��������</td>
            <td class="list_bg1" align="left">
                 <input type="text" name="txtBirthday" size="25"  value="<tbl:writeparam name="txtBirthday"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">ԭ��������</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtcustDistrict" size="25"  value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�̶��绰</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtTelephone" size="25"  value="<tbl:writeparam name="txtTelephone"/>" class="textgray" readonly >
             </td>
            <td class="list_bg2" align="right">�ƶ��绰</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtTelephoneMobile" size="25"  value="<tbl:writeparam name="txtTelephoneMobile"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�ɰ�װ��ַ</td>
            <td class="list_bg1" align="left"><font size="2">
                <input type="text" name="txtOldDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" class="textgray" readonly >
            </font></td>
            <td class="list_bg2" align="right">�ʱ�</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtPostcode" size="25" value="<tbl:writeparam name="txtPostcode"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">�°�װ��ַ</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">��������</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtNewDistrictName" size="25"  value="<tbl:WriteDistrictInfo property="txtNewDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">��<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtCatvID" size="25" maxlength="100" value="<tbl:writeparam name="txtCatvID"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">��Ч�ʻ�</td>
            <td class="list_bg1" align="left">
              <input type="text" name="txtCustAccount" size="25"  value="<tbl:writeparam name="txtCustAccount"/>" class="textgray" readonly >
            </td>	              	
        </tr> 
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
		<tr>
		<td valign="middle" class="list_bg2" align="right" colspan="1">
			�ͻ�������<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
		</td>
		<td class="list_bg1" colspan="3">
			<font size="2"> 
			    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="20" value="<tbl:writeparam name="txtCustomerSerialNo" />" class="textgray" readonly> 
			</font>
		</td>
		</tr>  
		</lgc:showcontentbysetting> 
		
	<tr>
	<td class="list_bg2" align="right">Э����</td>
	    <td class="list_bg1" align="left" colspan="3">
	      <input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="textgray" readonly >
	    </td>
	</tr>
	    
    </table>

<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_MD%>" acctshowFlag ="true" confirmFlag="true" />		 
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	 <tr align="center">
	    <td>
	    <table  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        <td><img src="img/button2_r.gif" width="22" height="20"></td>
		      <td><input name="Submit" type="button" class="button" value="��һ��" onclick="javascript:back_submit()"></td>
		      <td><img src="img/button2_l.gif" width="11" height="20"></td>
		      <td width="20" ></td>
		      <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		      <td><input name="Submit" type="button" class="button" value="ȷ ��" onclick="javascript:create_submit()"></td>
		      <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	      </tr>
	    </table>
	   </td>
	  </tr>
</table>
<tbl:generatetoken />
</form>