<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*, com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%
  String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
  int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
%>
<Script language=javascript>
<!--


function check_form()
{
    <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
    <lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >
    if (check_Blank(document.frmPost.txtCustomerSerialNo, true, "客户条形码"))
		return false;
	</lgc:showcontentbysetting>
    </lgc:showcontentbysetting> 
   if (check_Blank(document.frmPost.txtPostcode,true,"新邮编"))
       return false;
       
   if (check_Blank(document.frmPost.txtDetailAddress,true,"新新安装地址"))
       return false;
       
   if (check_Blank(document.frmPost.txtNewDistrict, true, "新所在区域"))
       return false;

   if (check_Blank(document.frmPost.txtCustAccount,true,"有效帐户"))
       return false;
       
   if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtTelephoneMobile.value=='') {
		alert("固定电话和移动电话不能同时为空!");
		document.frmPost.txtTelephone.focus();
		return false;
   }

   return true;
}

function create_submit()
{
    document.frmPost.action="customer_move_fee.do";
    if(check_form()) document.frmPost.submit();
}

function check_submit()
{
    if(check_addressOrcatv())
    {
        var  detailAddress =document.frmPost.txtDetailAddress.value;
        var  catvId =document.frmPost.txtCatvID.value;
        var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
        window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&catvId="+catvId+"&detailAddress="+detailAddress,"<%=catvFieldName%>",strFeatures);
    }
}

function check_addressOrcatv()
{
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "新安装地址")){
	alert("新安装地址和<%=catvFieldName%>，请输入至少一个查询条件");		
	return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
       if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
	  alert("<%=catvFieldName%>必须输入<%=catvIdLength%>位");
	  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "新安装地址")){
	if(document.frmPost.txtDetailAddress.value.length<=3){
	   alert("新安装地址必须至少4位");
	   return false;		
	}
    }		
    return true;
}

//-->
</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">录入迁移信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="txtCustomerDTlastmod" value="<tbl:writeparam name="txtCustomerDTlastmod"/>">
    <input type="hidden" name="txtAddressDTlastmod" value="<tbl:writeparam name="txtAddressDTlastmod"/>">
    <input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID"/>">
    <input type="hidden" name="txtCustomerType" value="<tbl:writeparam name="txtCustomerType"/>" >
    <input type="hidden" name="txtCardType" value="<tbl:writeparam name="txtCardType"/>">
    <input type="hidden" name="txtGender" value="<tbl:writeparam name="txtGender"/>">
    <input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict"/>">
    <input type="hidden" name="txtCustomerStatus" value="<tbl:writeparam name="txtCustomerStatus"/>">
    <input type="hidden" name="txtOpenSourceType" value="<tbl:writeparam name="txtOpenSourceType"/>">
 
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">

        <tr>
            <td class="list_bg2" align="right" width="17%">客户证号</td>
            <td class="list_bg1" align="left" width="33%">
                <input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right" width="17%">&nbsp;<%=catvFieldName%></td>
            <td class="list_bg1" align="left" width="33%">
                <input type="text" name="txtOldCatvID" size="25" maxlength="10" value="<tbl:writeparam name="txtOldCatvID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">客户类型</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCustomerTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="txtCustomerType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">客户状态</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtCustomerStatusName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="txtCustomerStatus"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">来源渠道</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtOpenSourceTypeShow" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="txtOpenSourceType"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">创建日期</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtDtCreateShow" size="25"  value="<tbl:writeparam name="txtDtCreateShow" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">姓名</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">性别</td>
            <td class="list_bg1" align="left">
                <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" selDisabled="true" style="background-color:#E1E1E1"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">证件类型</td>
            <td class="list_bg1" align="left">
                <d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" selDisabled="true" style="background-color:#E1E1E1" />
             </td>
            <td class="list_bg2" align="right">证件号码</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtCardID" size="25"  value="<tbl:writeparam name="txtCardID"/>" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">出生日期</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtBirthday" size="25" value="<tbl:writeparam name="txtBirthday"/>" class="textgray" readonly >
             </td>
            <td class="list_bg2" align="right">原所在区域</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtDistrictName" size="25" value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="textgray" readonly >
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">旧安装地址</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtOldDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtOldDetailAddress"/>" class="textgray" readonly >
            </td>
            <td class="list_bg2" align="right">新邮编*</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtPostcode" maxlength='6' size="25" value="<tbl:writeparam name="txtPostcode"/>">
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">新固定电话</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtTelephone" size="25"  value="<tbl:writeparam name="txtTelephone"/>" class="text">
            </td>
            <td class="list_bg2" align="right">新移动电话</td>
            <td class="list_bg1" align="left">
               <input type="text" name="txtTelephoneMobile" size="25"  value="<tbl:writeparam name="txtTelephoneMobile"/>" class="text">
            </td>
        </tr>        
        <tr>
            <td class="list_bg2" align="right">新安装地址*</td>
            <td class="list_bg1" align="left">
                <input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress"/>">
            </td>
            <td class="list_bg2" align="right">新所在区域*</td>
            <td class="list_bg1" align="left">
              <input type="hidden" name="txtNewDistrict" value="<tbl:writeparam name="txtNewDistrict" />">
	      <input type="text" name="txtNewDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtNewDistrict" />" class="text">
              <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all;leaf','txtNewDistrict','txtNewDistrictDesc')">
            </td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">&nbsp;<%=catvFieldName%></td>
            <td class="list_bg1" align="left"><font size="2">
                <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID"/>" class="text">
            </font><input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()">
            </td>
            <td class="list_bg2" align="right">有效帐户*</div></td>
            <td class="list_bg1" align="left">
               <d:selAccByCustId name="txtCustAccount" mapName="self" canDirect="true"  match="txtAccountID" empty="false" width="23" style="width:185px;"/>
            </td>
        </tr>
       
       </tr>
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
	<tr>
		<td valign="middle" class="list_bg2" align="right" colspan="1">
			客户条形码<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
		</td>
		<td  class="list_bg1" colspan="3">
			<font size="2"> 
			    <input type="text" name="txtCustomerSerialNo" size="25" maxlength="20" value="<tbl:writeparam name="txtCustomerSerialNo" />" class="text"> 
			</font>
		</td>
	</tr>	
	</lgc:showcontentbysetting>
	<tr>
	<td class="list_bg2" align="right">协议编号</td>
    	<td class="list_bg1" align="left" colspan="3">
      	<input type="text" name="txtContractNo" size="25"  value="<tbl:writeparam name="txtContractNo"/>" class="text" >
    	</td>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
			        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			        <td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:create_submit()"></td>
			        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>