<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>
<%
String catvName=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
%>
<script language=javascript>
function check_form(){
	if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"�ͻ�֤��")){
		document.frmPost.txtCustomerID.focus();
		return false;
		}
   if (document.frmPost.txtStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtEndDate != ''){
		if (!check_TenDate(document.frmPost.txtEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtStartDate,document.frmPost.txtEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	if(check_Blank(document.frmPost.txtSelTo, true, "ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtSelTo,true,3,"ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(document.frmPost.txtSelTo.value == 0 || document.frmPost.txtSelTo.value == 1){
		alert("ҳ���¼������Ϊ0��1��");
		document.frmPost.txtSelTo.focus();
		return false;
	}
	
	return true;
}


function query_submit(){
var catvName = '<%=catvName%>';
 if (trim(document.frmPost.txtSerialNo.value) != '' && trim(document.frmPost.txtSerialNo1.value) != ''){
		  alert("�豸���к�(ģ����ѯ)���豸���к�(��ȷ��ѯ��ѯ)ֻ����дһ��");
			return;
	}
  if (trim(document.frmPost.txtCatvID.value) != '' && trim(document.frmPost.txtCatvID1.value) != ''){
		  alert(catvName+"(ģ����ѯ)��"+catvName+"(��ȷ��ѯ��ѯ)ֻ����дһ��");
			return;
	}
	document.frmPost.txtTo.value = document.frmPost.txtSelTo.value;
	setCookie("customer_query.do",document.frmPost.txtTo.value);
	document.frmPost.submit();
}

function view_detail_click(strId){
	self.location.href="customer_view.do?txtCustomerID="+strId;
}

function back_query(){
	self.location.href="menu_customer_query.do";
}

function form_Reset(){
	var els=document.frmPost.elements;
	for( i=0;i<els.length;i++){
		var control=els[i];
		if(control.type!='button' && control.type!='submit'&&control.name!='txtTo'&&control.name!='txtFrom')
			els[i].value="";
	}
}

function query_order(selOrder){
	if (check_form()){
		if(document.frmPost.selOrder.value == selOrder){
			if(document.frmPost.chkOrderDesc.value == "desc")
				document.frmPost.chkOrderDesc.value = "";
			else if(document.frmPost.chkOrderDesc.value == "")
				document.frmPost.chkOrderDesc.value = "desc";
		}
		else{
			document.frmPost.selOrder.value = selOrder;
			document.frmPost.chkOrderDesc.value = "desc";
		}
		document.frmPost.txtTo.value = document.frmPost.txtSelTo.value;
		
        document.frmPost.submit();
    }
}

function OnBlur(){
	if(check_Blank(document.frmPost.txtSelTo, true, "ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtSelTo,true,3,"ҳ���¼��")){
		document.frmPost.txtSelTo.focus();
		return false;
	}
	if(document.frmPost.txtSelTo.value == 0 || document.frmPost.txtSelTo.value == 1){
		alert("ҳ���¼������Ϊ0��1��");
		document.frmPost.txtSelTo.focus();
		return false;
	}
}

// д��cookie
function setCookie(name,value)   
{
        var Days = 30;   
        var exp = new Date();   //new   Date("December   31,   9998");
        exp.setTime(exp.getTime() + Days*24*60*60*1000);   
        document.cookie = name + "="+ escape(value) + ";expires=" + exp.toGMTString();   
}   
  
//��ȡcookie
function getCookie(name)   
{
		//��ȡcookie�ַ���
		var strCookie=document.cookie;
		//����cookie�и�Ϊ�����/ֵ��
		var arrCookie=strCookie.split("; ");
		//����cookie���飬����ÿ��cookie��
		for(var i=0;i<arrCookie.length;i++){
      		var arr=arrCookie[i].split("=");
      		//�ҵ�����ΪuserId��cookie������������ֵ
      		if(name==arr[0]){
             	return arr[1];
            	break;
      		}
		}
		return null;
}

</script>
<style TYPE="text/css">
<!--
A:link{text-decoration:none}
A:visited{text-decoration:none}
A:hover {color: #ff00ff;text-decoration:underline}
 -->
</style>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          ���ڻ�ȡ���ݡ�����
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ���ѯ</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="customer_query_result.do" method="post" onsubmit="return check_form()">    
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">�ͻ�����</td>
    <td class="list_bg1" width=33% align="left"><input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />"></td>
    <td class="list_bg2" width=17% align="right">�ͻ�֤��</td>
    <td class="list_bg1" width=33% align="left"><input name="txtCustomerID" type="text" class="text" maxlength="9" size="22" value="<tbl:writeparam name="txtCustomerID" />">
	  &nbsp;&nbsp;<A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="չ���¼���ѯ����" src="img/icon_bottom.gif" border=0></A></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%" align="right">֤����</td>
    <td class="list_bg1" width="33%" align="left">
    	  <input name="txtCardID" type="text" class="text" size="22"  maxlength="25" value="<tbl:writeparam name="txtCardID" />">
    </td>
    <td class="list_bg2" align="right">��װ��ַ</td>
    <td class="list_bg1" align="left"><input name="txtDetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtDetailAddress" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right"></td>
    <td class="list_bg1" align="left"></td>
    
    <td class="list_bg2" align="right">��������</td>
    <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
  </tr>
 
	<tr>
	  <td class="list_bg2" align="right">�豸���к�(ģ����ѯ)</td>
    <td class="list_bg1" align="left"><input name="txtSerialNo" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtSerialNo" />"></td>
      <td class="list_bg2" align="right">�豸���к�(��ȷ��ѯ)</td>
    <td class="list_bg1" align="left"><input name="txtSerialNo1" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtSerialNo1" />"></td>
	</tr>
	
  <tr>
    <td class="list_bg2" align="right">&nbsp;<%=catvName%>(ģ����ѯ)</td>
    <td class="list_bg1" ><input type="text" name="txtCatvID" size="22" maxlength="20" value="<tbl:writeparam name="txtCatvID" />" class="text"></td>
		<td class="list_bg2" align="right">&nbsp;<%=catvName%>(��ȷ��ѯ)</td>
    <td class="list_bg1" ><input type="text" name="txtCatvID1" size="22" maxlength="20" value="<tbl:writeparam name="txtCatvID1" />" class="text"></td>
	</tr>
	
</table>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none">
  <tr>
    <td class="list_bg2" width=17% align="right">�ͻ�����</td>
    <td class="list_bg1" width=33% align="left"><d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" /></td>
    <td class="list_bg2" width=17% align="right">�ͻ�״̬</td>
    <td class="list_bg1" width=33%>
	<%
    Map mapType = new HashMap();
    mapType.put("N", "����");
    mapType.put("P", "Ǳ��");
    pageContext.setAttribute("CertainStatusList", mapType);
	%>           
    <tbl:select name="txtStatus" set="CertainStatusList" match="txtStatus" width="20" />   </td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%" align="right">�籣�����</td>
    <td class="list_bg1" width="33%" align="left"><input name="txtSocialSecCardID" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />"></td>
  	<td class="list_bg2" align="right">��Դ����</td>
    <td class="list_bg1" align="left"><d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="20"  /></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">�̶��绰</td>
    <td class="list_bg1" align="left"><input name="txtTelephone" type="text" class="text" maxlength="50" size="22" value="<tbl:writeparam name="txtTelephone" />"></td>
    <td class="list_bg2" align="right">�ƶ��绰</td>
    <td class="list_bg1" align="left"><input name="txtTelephoneMobile" type="text" class="text" maxlength="50" size="22" value="<tbl:writeparam name="txtTelephoneMobile" />"></td>
  </tr>
  
  <tr>
    <td class="list_bg2" align="right">������֯</td>
    <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	    <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
    </td>	

    <td class="list_bg2" align="right">Э����</td>
    <td class="list_bg1" align="left"><input name="txtContractNO" type="text" class="text" maxlength="20" size="22"  value="<tbl:writeparam name="txtContractNO" />" class="text"></td>
  </tr>
			
  <%
    
    Map mapBankMop=Postern.getAllMethodOfPayments();
    Iterator itBank = mapBankMop.entrySet().iterator();
    Map mapMop=new HashMap();
    MethodOfPaymentDTO dtoMOP=null;
     
    while (itBank.hasNext())
    {
        Map.Entry BankItem = (Map.Entry)itBank.next();
        dtoMOP =(MethodOfPaymentDTO)BankItem.getValue();
        mapMop.put(BankItem.getKey(), dtoMOP.getName());
    }  
    
    //pageContext.setAttribute("AllMOPList" , mapMop); 
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 
    pageContext.setAttribute("AllServiceList" , Postern.getAllServiceByStatus("N")); 

%>                      
  <tr>
    <td class="list_bg2" align="right">��������</td>
    <td class="list_bg1" align="left"><tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="20" /></td>
    <td class="list_bg2" align="right">�����ʻ�</td>
    <td class="list_bg1" align="left"><input name="txtBankAccount" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtBankAccount" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">�������</td>
    <td class="list_bg1" align="left"><input name="txtServiceCode" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtServiceCode" />"></td>
    <td class="list_bg2" align="right">�豸MAC��ַ</td>
    <td class="list_bg1" align="left"><input name="txtMacAddress" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtMacAddress" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">ҵ������</td>
    <td class="list_bg1" align="left"><tbl:select name="txtServiceID" set="AllServiceList" match="txtServiceID" width="20" /></td>
    <td class="list_bg2" align="right"><%=Postern.getSystemsettingValueByName("SET_V_USERLOGINIDFIELDNAME")%></td>
    <td class="list_bg1" align="left"><input name="txtBroadbandName" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtBroadbandName" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">�ʵ���ַ�Ƿ����</td>
    <td class="list_bg1" align="left"><d:selcmn name="txtBillAddressFlag" mapName="SET_G_YESNOFLAG" match="txtBillAddressFlag" width="20" /></td>
    <td class="list_bg2" align="right">��ַ��Чԭ��</td>
    <td class="list_bg1">
    	<d:selcmn name="txtInvalidAddressReason" mapName="SET_F_INVALIDADDRESSREASON" match="txtInvalidAddressReason" width="20" /></td>			 
  </tr>
  <tr>
    <td class="list_bg2" align="right">��������</td>
    <td class="list_bg1" align="left" colspan="3"><d:datetime name="txtStartDate" size="10" myClass="text" match="txtStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             -
             <d:datetime name="txtEndDate" size="10" myClass="text" match="txtEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
		</td>
    		 
  </tr>
  <tbl:dynamicservey serveyType="M" showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" />
</table>
  
	<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
				<td><input name="Reset" type="button" class="button" value="�� ��" onclick="form_Reset()" ></td>
				<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
				<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td>
				<input name="Submit" type="button" class="button" value="�� ѯ" onclick="query_submit()">
				</td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<pri:authorized name="customer_query_result.export">
				<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:download(document.frmPost,'�ͻ���ѯ')" class="btn12">������ѯ���</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</pri:authorized>
			  </tr>
	  	</table></td>
		</tr>
	</table>
	
	<% if(request.getParameter("selOrder") != null){ %>
	<input type="hidden" name="selOrder" size="22" value="<%=request.getParameter("selOrder")%>">
	<%}else{%><input type="hidden" name="selOrder" size="22" value="CUSTOMERID"><%}%>
	
	<%if(request.getParameter("chkOrderDesc") != null){ %>
	<input type="hidden" name="chkOrderDesc" size="22" value="<%=request.getParameter("chkOrderDesc")%>">
	<%}else{%><input type="hidden" name="chkOrderDesc" size="22" value="desc"><%}%>
	
	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">

<br>      
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
    //���������ڿͻ���ѯ�б����Ƿ���ʾCATVIDģʽ�����û�֤��
    String listShowCatvID=Postern.getSystemsettingValueByName("CUSTOMER_QUERY_LIST_SHOW_CATVID");
%>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	<%if("CUSTOMERID".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERID')"><font color="white">�ͻ�֤�š�</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERID')"><font color="white">�ͻ�֤�š�</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERID')"><font color="white">�ͻ�֤��</font></a></td><%}%>
	
	<% if("Y".equals(listShowCatvID)){ %>
	<%if("CATVID".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CATVID')"><font color="white"><%=catvName%>��</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CATVID')"><font color="white"><%=catvName%>��</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('CATVID')"><font color="white"><%=catvName%></font></a></td><%}%>
    <%}%>

	<%if("NAME".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('NAME')"><font color="white">������</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('NAME')"><font color="white">������</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('NAME')"><font color="white">����</font></a></td><%}%>
	
	<%if("CUSTOMERTYPE".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERTYPE')"><font color="white">���͡�</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERTYPE')"><font color="white">���͡�</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('CUSTOMERTYPE')"><font color="white">����</font></a></td><%}%>
	
	<%if("OPENSOURCETYPE".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('OPENSOURCETYPE')"><font color="white">��Դ������</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('OPENSOURCETYPE')"><font color="white">��Դ������</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('OPENSOURCETYPE')"><font color="white">��Դ����</font></a></td><%}%>
	
	<%if("CARDID".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CARDID')"><font color="white">֤���š�</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CARDID')"><font color="white">֤���š�</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('CARDID')"><font color="white">֤����</font></a></td><%}%>
	
	<%if("DISTRICTID".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="120" nowrap><a href="javascript:query_order('DISTRICTID')"><font color="white">����������</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="120" nowrap><a href="javascript:query_order('DISTRICTID')"><font color="white">����������</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="120" nowrap><a href="javascript:query_order('DISTRICTID')"><font color="white">��������</font></a></td><%}%>
	
	<%if("DETAILADDRESS".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="170" nowrap><a href="javascript:query_order('DETAILADDRESS')"><font color="white">��ַ��</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="170" nowrap><a href="javascript:query_order('DETAILADDRESS')"><font color="white">��ַ��</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="170" nowrap><a href="javascript:query_order('DETAILADDRESS')"><font color="white">��ַ</font></a></td><%}%>
	
	<%if("CREATETIME".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CREATETIME')"><font color="white">�������ڡ�</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="80" nowrap><a href="javascript:query_order('CREATETIME')"><font color="white">�������ڡ�</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="80" nowrap><a href="javascript:query_order('CREATETIME')"><font color="white">��������</font></a></td><%}%>

	<%if("STATUS".equals(request.getParameter("selOrder"))){%>
    <%if(request.getParameter("chkOrderDesc") != null){ %>
    <%if("desc".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="40" nowrap><a href="javascript:query_order('STATUS')"><font color="white">״̬��</font></a></td><%}%>
	<%if("".equals(request.getParameter("chkOrderDesc"))){ %>
	<td class="list_head" width="40" nowrap><a href="javascript:query_order('STATUS')"><font color="white">״̬��</font></a></td><%}%>
	<%}}else{%><td class="list_head" width="40" nowrap><a href="javascript:query_order('STATUS')"><font color="white">״̬</font></a></td><%}%>
	
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getCustDto());
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12" ><tbl:write name="oneline" property="customerID" /></a></td>
    <% if("Y".equals(listShowCatvID)){ %>
    <td align="center"><tbl:write name="oneline" property="catvID" /></td>
    <%}%>
    <td align="center"><tbl:write name="oneline" property="name" /></td>
    <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
    <td align="center"><d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="oneline:openSourceType" /></td>
    <td align="center"><tbl:write name="oneline" property="CardID" /></td>
    <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
    <td align="center"><tbl:write name="custaddr" property="detailAddress" /></td>
    <td align="center"><tbl:writedate name="oneline" property="CreateTime" /></td>
    <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="oneline:status" /></td>
</tbl:printcsstr>
</lgc:bloop>
			<tr>
			    <% 
			        //����CATVID��ʾ�Ͳ���ʾʱ��col���
			        int footBarConNum=9;
			        if("Y".equals(listShowCatvID))footBarConNum=10;
			     %>
				<td colspan="<%=footBarConNum%>" class="list_foot"></td>
			</tr>
        	<tr>
				<td align="right" class="t12" colspan="<%=footBarConNum%>" >
				 <%if(request.getParameter("txtSelTo")==null){%>
			     ҳ���¼����<input type="text" name="txtSelTo" size="4" maxlength="3" value="10" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}else{%>
	    		 ҳ���¼����<input type="text" name="txtSelTo" size="4" maxlength="3" value="<%=request.getParameter("txtSelTo")%>" class="text" onkeydown="if(event.keyCode==13) query_submit();" onblur="OnBlur()">
	    		 <%}%>
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
				<a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
		</table>        
</TD>
</TR>
</TABLE>
</form>

<script language=javascript>
	var cookieValue = getCookie("customer_query.do");
	if(cookieValue!=null)
		document.frmPost.txtSelTo.value = cookieValue;
	else
		document.frmPost.txtSelTo.value = 10;
</script>
