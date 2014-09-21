<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator,
                 com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%
    String screenDirect=request.getParameter("txtScreenDirect");
    String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
    String manualCreateTrans= Postern.getSystemsettingValueByName("SET_W_JOBCARD_TRANS");
    String strJobType=request.getParameter("txtJobType");
    String orgRole="";
    if("R".equals(strJobType)){
        orgRole="S";
    }else if("I".equals(strJobType)){
        orgRole="I";
    }else if("C".equals(strJobType)){
       orgRole="M";
    }
    int serviceid=Postern.getAutoNextOrgID(orgRole,request.getParameter("txtSubtype"),WebUtil.StringToInt(request.getParameter("txtServiceAccountID")),WebUtil.StringToInt(request.getParameter("txtAddressID")));
    pageContext.setAttribute("txtServiceID",String.valueOf(serviceid));

%>

<script language=javascript>
<!--
function frm_next(){
    if(document.frmPost.txtAutoNextOrgID==null){
        alert("����ϵϵͳ����Ա�����Զ���ת����,Ȼ�����ȷ�ϲ�����");
	    return false;
    }
    if (check_Blank(document.frmPost.txtAutoNextOrgID, true, "��ת����"))
     	return false;
    document.frmPost.action="manual_open_jobcard_create.do";
    document.frmPost.submit();
}
function back_submit() { 
    <%
    if("calculatefee".equals(screenDirect)){
    %>
	    document.frmPost.action="manual_open_jobcard_payfee.screen";
	<%}else{%>
	    document.frmPost.action="manual_open_jobcard.do";
    <%}%>
	document.frmPost.submit();
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	var orgRole="";
	if("R"==document.frmPost.txtJobType.value){
        orgRole="S";
    }else if("I"==document.frmPost.txtJobType.value){
        orgRole="I";
    }else if("C"==document.frmPost.txtJobType.value){
       orgRole="M";
    }
  	window.open("transfer_org_list.do?strRole="+orgRole,"��ת����",strFeatures);
}
-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
		<table  border="0" align="center" cellpadding="0" cellspacing="10" >
		  <tr>
		    <td><img src="img/list_tit.gif" width="19" height="19"></td>
		    <td class="title">�ֹ�����ȷ��</td>
		  </tr>
		</table>
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		    <td><img src="img/mao.gif" width="1" height="1"></td>
		  </tr>
		</table>
		<br>
		<form name="frmPost"  method="post" action="">
		<input type="hidden" name="confirm_post" value="TRUE">
		<input type="hidden" name="func_flag" value="3" >
		<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
		      <td colspan="4" class="import_tit" align="center"><font size="3">�ͻ���ϵ��Ϣ</font></td>
	        </tr>
	        <tr> 
		      <td colspan="4" class="list_bg2" align="left" ><font color="red">��ʾ�����������Ŵ�����Ҫ��ϸ��д����Ϣ�����һ��ȷ��</font></td>
	        </tr>
			<tr> 
                <td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
                <td class="list_bg1"> 
                <tbl:writeparam name="txtCustomerID" />
               </td>
                <td class="list_bg2"><div align="right">�ͻ�����</div></td>
                <td class="list_bg1">
                <tbl:writeparam name="txtName" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">�̶��绰</div></td>
                <td class="list_bg1"> 
                <tbl:writeparam name="txtTelephone" />
                </td>
                <td class="list_bg2"><div align="right">�ƶ��绰</div></td>
                <td class="list_bg1">
                <tbl:writeparam name="txtTelephoneMobile" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">ԤԼ����ʱ��</div></td>
                <td class="list_bg1"> 
                <tbl:writeparam name="txtPreferedDate" />
                </td>
                <td class="list_bg2"><div align="right">ԤԼ����ʱ���</div></td>
                <td class="list_bg1">
                <d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" />
                </td>
			</tr>
			<tr> 
		      <td colspan="4" class="import_tit" align="center"><font size="3">�ɹ���Ϣ</font></td>
	        </tr>
			<tr> 
                <td class="list_bg2"><div align="right">��������*</div></td>
                <td class="list_bg1"> 
                <d:getcmnname typeName="SET_W_JOBCARDTYPE" match="txtJobType" />
                </td>
                <td class="list_bg2"><div align="right">����������</div></td>
                <td class="list_bg1">
                <d:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="txtSubtype" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">����*</div></td>
                <td class="list_bg1">
						<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
						<tbl:WriteDistrictInfo property="txtDistrictID" />
				</td>
                <td class="list_bg2"><div align="right">��ϸ��ַ*</div></td>
                <td class="list_bg1">
                <tbl:writeparam name="txtDetailAddress" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">�����ʻ�</div></td>
                <td class="list_bg1"> 
                    <tbl:writeparam name="txtAccount" />
                </td>
                <td class="list_bg2"><div align="right">����ԭ��*</div></td>
                <td class="list_bg1">
                <d:getcmnname typeName="SET_W_JOBCARDCREATEREASON" match="txtManualCreateReason" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">ҵ���ʻ�</div></td>
                <td class="list_bg1"> 
                <tbl:writeparam name="txtServiceAccountID" />
                </td>
                <td class="list_bg2"><div align="right">�����˿���</div></td>
                <td class="list_bg1"> 
                <tbl:writeparam name="txtAddPortNumber" />
                </td>
                
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">ԭ����</div></td>
                <td class="list_bg1">
						<input type="hidden" name="txtOldDistrictID" value="<tbl:writeparam name="txtOldDistrictID" />">
						<tbl:WriteDistrictInfo property="txtOldDistrictID" />
				</td>
                <td class="list_bg2"><div align="right">ԭ��ϸ��ַ</div></td>
                <td class="list_bg1">
                <tbl:writeparam name="txtOldDetailAddress" />
                </td>
			</tr>
			<tr> 
                <td class="list_bg2"><div align="right">��ע</div></td>
                <td class="list_bg1" colspan="3">
                <tbl:writeSpeCharParam name="txtComments" />
                </td>
			</tr>
			<lgc:showcontentbysetting settingName="SET_W_JOBCARD_TRANS" >
			<tr> 
		      <td colspan="4" class="import_tit" align="center"><font size="3">����������</font></td>
	        </tr>
			 <%
    	        if(systemSettingValue != null &&("Y").equals(systemSettingValue)){
    	     %> 
    	    <tr>
        	    <td class="list_bg2"><div align="right">��ת����*</td>
        	    <td class="list_bg1" colspan="3">     
        		 <input type="hidden" name="txtAutoNextOrgID" value="<%=String.valueOf(serviceid)%>" >
        		 <input type="text" name="txtNextOrgName" size="30" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(serviceid)%>" class="text">
        		 <input name="selOrgButton" type="button" class="button" value="�޸�" onClick="javascript:mod_organization()">
    	        </td>		
    	    </tr> 
    	    <% }else {%>    
    	    <tr>
        	    <td class="list_bg2"><div align="right">��ת����*</td>
        	    <td class="list_bg1" colspan="3">
    		    <input type="hidden" name="txtAutoNextOrgID" value="<tbl:writeparam name="txtAutoNextOrgID" />">
    		    <input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtAutoNextOrgID" />" class="text">
    		    <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,S,O','txtAutoNextOrgID','txtNextOrgName')">
    	        </td>		
    	    </tr>
    	    <%}
    	    %>  
    	    </lgc:showcontentbysetting> 
    	    <lgc:showcontentbysetting settingName="SET_W_JOBCARD_TRANS" compareValue="N">
    	    <input type="hidden" name="txtAutoNextOrgID" value="<%=String.valueOf(serviceid)%>" >
    	    </lgc:showcontentbysetting> 
    	</table>
        <tbl:hiddenparameters pass="txtJobType/txtSubtype/txtAccount/txtManualCreateReason/txtAddPortNumber/txtServiceAccountID/txtComments/txtCustomizeFee/txtOldDistrictID/txtOldDetailAddress" />
        <tbl:hiddenparameters pass="txtCustomerID/txtCatvID/txtName/txtAddressID/txtDetailAddress/txtDistrictID/txtTelephone/txtTelephoneMobile/txtPreferedDate/txtPreferedTime" />
        <tbl:hiddenparameters pass="txtCustomerType/txtOpenSourceType/txtCreateJobForCust/txtScreenDirect" />
        <%
        if("calculatefee".equals(screenDirect)){
        %>
        <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
           <tr ><td> 
           	 <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.METHODOFPAYMENT_KHGD%>" acctshowFlag ="true" confirmFlag="true"  />		 
           </td></tr> 
        </table>
        <%}%>
         <tbl:generatetoken />
	    </form> 
	    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
        </table>
	    <table align="center" border="0" cellspacing="0" cellpadding="0">
            <BR>
            <tr>
               <td><img src="img/button2_r.gif" width="22" height="20"></td>
               <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="��һ��"></td>
               <td><img src="img/button2_l.gif" width="11" height="20"></td>
               <td width="20" ></td>
               <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
               <td><input name="next" type="button" class="button" onClick="javascript:frm_next()" value="ȷ��"></td>
               <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
            </tr>
       </table>     
    </TD>
</TR>
</TABLE>