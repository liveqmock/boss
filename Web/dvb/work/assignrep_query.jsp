<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.*,com.dtv.oss.web.util.LogonWebCurrentOperator,com.dtv.oss.dto.OperatorDTO" %>


<script language=javascript>
function query_submit()
{	if(checkDate()){
		
		document.frmPost.submit();
	}
}
function checkDate(){
	if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCreateEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtCustomerProblemID,true,9,"���޵����"))
		return false; 
	return true;
}
 
//function view_diagnosis_info(strId){
//  self.location.href="diagnosis_info_view_n.do?txtCustomerProblemID="+strId;
//} 
function view_detail_click(strId)
{
	self.location.href="cp_query_detail.do?txtCustomerProblemID="+strId;
} 
function checkSelected(){
	var returnValue=false;
	if(document.frmPost.ListID==null){
		alert("û����Ҫ���������ı��޵�");
		return false;
	}else{
		if (document.frmPost.ListID.length!=null){
			for (var i=0;i<document.frmPost.ListID.length;i++)
			{
				if (document.frmPost.ListID[i].checked)
				{
					returnValue= true;
					break;
				}
			}
		}else{
			if(document.frmPost.ListID.checked){
				returnValue= true;
			}
		}
	}
	if(!returnValue){
		alert("��ָ����Ҫ���������ı��޵�");
	}
    return returnValue;
}
function create_submit()
{   
    if(checkSelected()){
    	if(check_transfertype()){
		 document.frmPost.action = "assign_repair.do"
		 document.frmPost.submit();
	}
   }
}
function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("�޷�ƥ����ʵĴ����ţ����ֹ���ת��ָ����ת���ţ�");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("�ֹ���ת��ָ����ת���ţ�");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function ChangeSubcompany()
{

    document.FrameUS.submit_update_select('subctoss', document.frmPost.txtCustOrgID.value, 'txtCustStreetStationID', '');
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=R","��ת����",strFeatures);
}
</script>


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

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">�ɹ������޵���ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br> 
<form name="frmPost" method="post" action="assignrep_query_result.do" >
 <input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>">
 <input type="hidden" name="txtQueryType" value="queryPart">
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
          <td class="list_bg2"><div align="right">���޵����</div></td>  
          <td class="list_bg1">
          <input type="text" name="txtCustomerProblemID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustomerProblemID" />" >
         </td>
          <td  class="list_bg2"><div align="right">����ʱ��</div></td>
           <td class="list_bg1"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
            </td>
        </tr>
        <tr>         
           <td  class="list_bg2"><div align="right">�������</div></td>
           <td class="list_bg1"> 
            <d:selcmn name="txtProblemCategory" mapName="SET_C_CPPROBLEMCATEGORY" match="txtProblemCategory" width="25"/> 
             </td>
           <td  class="list_bg2"><div align="right">�շ����</div></td>
	  <td class="list_bg1"> 
	 <d:selcmn name="txtFeeClass" mapName="SET_C_CPFEECLASS" match="txtFeeClass" width="25" />
	 </td>  
        </tr>
      </table>
      <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="txtStatus" value="W">
      <input type="hidden" name="func_flag" value="1002">
         
 
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>  
  <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		 <tr class="list_head">
		      <td class="list_head" > <input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div></td>
			<td class="list_head" nowrap>���޵���</td>
			<td class="list_head" nowrap>�������</td>
			<td class="list_head" nowrap>��ַ</td>
			<td class="list_head" nowrap>��������</td>      
		</tr>
		<%
		  int count=0;
		  CustomerProblemDTO obj=null;%>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<%
		CustomerProblemDTO dto = (CustomerProblemDTO)pageContext.getAttribute("oneline");
		String strAddress = Postern.getAddressById(dto.getAddressID());
		String problemCategory= dto.getProblemCategory();
		 
		if (strAddress==null) strAddress="";
		System.out.println("***********************"+dto.getId()); 
	        java.util.List diaType = Postern.getDiaTypeByCustProbID(dto.getId());
		System.out.println("***********************"+diaType); 
		//int operOrgID=CurrentLogonOperator.getOperator(request).getOrgID();
		++count;
		if(count==1)
			obj = dto;
				    
	%>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">		
		        <td align="center">		        
		        <input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="Id"/>">
		        </td>
		        <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></td>
      			<td align="center"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/></td>
      			<td align="center"><%=strAddress%></td> 
      			<td align="center"><tbl:write name="oneline" property="ProblemDesc"/></td>      			
      		</tr>
	</lgc:bloop>  
	 <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="7" >
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
<%if(count>0){

String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
if(systemSettingPrecise != null&&("Y").equals(systemSettingPrecise)){
String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
if(systemSettingValue != null &&("Y").equals(systemSettingValue)){
int autoOrgid=Postern.getAutoNextOrgID("S",null,null,null,null,Postern.getDistrictIDByAddressID(obj.getAddressID()),0);%>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr> 
		    <td colspan="2" class="import_tit" align="center"><font size="3"><input type="hidden" name="selType" value="auto">�����Զ���ת</font></td>
	        </tr>    
		<tr>
		    <td class="list_bg2"><div align="right">��ת����</td>
		    <td class="list_bg1" > 
		 	<input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
	    		<input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(autoOrgid)%>" class="text">
	    		<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:mod_organization()">
		    </td>		
		</tr>		
</table>
<% }else {%> 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr> 
		    <td colspan="2" class="import_tit" align="center"><font size="3"><input type="hidden" name="selType" value="manual">�����ֹ���ת</font></td>
	        </tr>    
		<tr>
		    <td class="list_bg2"><div align="right">��ת����</td>
		    <td class="list_bg1" > <!--
			 <tbl:select name="txtNextOrgID" set="ServiceRepOrg" match="txtNextOrgID" width="23" />
		 -->
		 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
	    <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
		    </td>		
		</tr>		
</table> 
<%
}}
%>    
 <tbl:generatetoken />
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td ><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"><input name="Submit" type="button" class="button" value="��������" onclick="javascript:create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>
<%}%>
  </rs:hasresultset> 
  </td>
  </tr>
</form>  
</table>  
 <script language=javascript>
function window_open(){
	var selstatus=document.frmPost.transferType.value;
	var l=document.getElementsByName("selType") ;
	for(i=0;i<l.length;i++){  
		if(l[i].value==selstatus){
			l[i].checked=true;
		} 
	}	
}
window_open();
