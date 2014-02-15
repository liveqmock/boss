<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>

<%
	String actiontype=request.getParameter("actiontype");
	String title="";
	if("transfer".equals(actiontype)){
		title="�ͻ�Ͷ����ת---��ѯ";
	}else if("query".equals(actiontype)){
		title="�ͻ�Ͷ��---��ѯ";
	}else if("manualend".equals(actiontype)){
		title="�ֹ�����Ͷ�ߵ�---��ѯ";
	}else if("callback".equals(actiontype)){
		title="Ͷ�߻ط�---��ѯ";
	}else if("process".equals(actiontype)){
		title="�ͻ�Ͷ�ߴ���---��ѯ";
	}else if("custQuery".equals(actiontype)){
		title="�ͻ�Ͷ�߲�ѯ";
	}
%>
<script language=javascript>

function cust_complain_query(actiontype){
	if(!checkPlainNum(document.frmPost.txtCustomerId,true,9,"�ͻ�֤��"))
		return ;
	if(document.frmPost.txtCustComplainId !=null)
	{
	if(!checkPlainNum(document.frmPost.txtCustComplainId,true,9,"Ͷ�ߵ�ID"))
		return ;
	}
	if(actiontype == "query"||actiontype == "custQuery"){
		if(query_all()){
			document.frmPost.txtQueryType.value = 'queryAll';			
		}else{
			document.frmPost.txtQueryType.value = 'queryPart';
		}
	}else
		document.frmPost.txtQueryType.value = '';
	document.all.frmPost.action="cust_complain_queryAction.do?actiontype="+actiontype;
	document.all.frmPost.submit();
}
function cust_camplain_detail(complainID,actiontype){
	//if(actiontype=="custQuery"){
		//if(query_all()){
			document.frmPost.txtQueryType.value = 'queryAll';			
		//}else{
		//	document.frmPost.txtQueryType.value = 'queryPart';
		//}
		self.location.href="cust_complain_detail.do?txtCustComplainId="+complainID+"&actiontype="+actiontype+"&txtCustomerId="+document.all.txtCustomerId.value+"&txtQueryType="+document.frmPost.txtQueryType.value;
	//}else{		
		//self.location.href="cust_complain_detail.do?txtCustComplainId="+complainID+"&actiontype="+actiontype+"&txtQueryType="+document.frmPost.txtQueryType.value;
	//}	
}
function query_all(){
	if(document.frmPost.queryType.checked == true)
		return true;
	return false;
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title%></td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="cust_complain_queryAction.do" method="post" >
<input type="hidden" name="txtQueryType" value="<tbl:writeparam name="txtQueryType"/>"> 
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<input type="hidden" name="actiontype" size="25" value="<%=actiontype%>" >  
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">�ͻ���Ϣ</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">�ͻ�����</td>
      <td class="list_bg1" width="33%"  align="left">
	<input type="text" name="txtCustomerName" size="25" value="<tbl:writeparam name="txtCustomerName"/>" >
      </td>
      <td class="list_bg2" width="17%"  align="right">�ͻ�֤��</td>
      <td class="list_bg1" width="33%"  align="left">
      <%if("custQuery".equals(actiontype)){
    	  String txtCustomerId=request.getParameter("txtCustomerId");%>
		<input type="text" name="txtCustomerId" size="25" value="<%=txtCustomerId%>"  class="textgray" readonly>
		<%}else{ %>
		<input type="text" name="txtCustomerId" size="25" value="<tbl:writeparam name="txtCustomerId"/>" maxlength="9" >
		<%} %>
      </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��ϵ��</td>
      <td class="list_bg1" width="33%"  align="left">
	<input type="text" name="txtContactPerson" size="25" value="<tbl:writeparam name="txtContactPerson"/>" >
      </td>
      <td class="list_bg2" width="17%"  align="right">��ϵ�绰</td>
      <td class="list_bg1" width="33%"  align="left">
	<input type="text" name="txtContactPhone" size="25" value="<tbl:writeparam name="txtContactPhone"/>" >
      </td>
     </tr>
     <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">Ͷ����Ϣ</font></td>
     </tr>
     <%if("query".equals(actiontype)){%>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">Ͷ�ߵ�ID</td>
      <td class="list_bg1" colspan="3"  align="left">
            <input type="text" name="txtCustComplainId" size="25" maxlength="9" value="<tbl:writeparam name="txtCustComplainId"/>" >
      </td>
     </tr>
    <%}%>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��Ͷ����֯</td>
      <td class="list_bg1" width="33%"  align="left">
      <font size="2">
          <input type="hidden" name="txtComplainedOrgId" value="<tbl:writeparam name="txtComplainedOrgId" />">
	  <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtComplainedOrgId" />" class="text">
	  <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S,D','txtComplainedOrgId','txtOrgDesc')">
      </font>
      </td>
      <td class="list_bg2" width="17%"  align="right">��Ͷ�߸���</td>
      <td class="list_bg1" width="33%"  align="left">
	<input type="text" name="txtComplainedPerson" size="25" value="<tbl:writeparam name="txtComplainedPerson"/>" >
      </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">Ͷ�ߴ�����ʼ��</td>
      <td class="list_bg1" width="33%"  align="left">
      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDtCreateF)" onblur="lostFocus(this)" type="text" name="txtDtCreateF" size="25" value="<tbl:writeparam name="txtDtCreateF"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDtCreateF,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
      <td class="list_bg2" width="17%"  align="right">Ͷ�ߴ�����ֹ��</td>
      <td class="list_bg1" width="33%"  align="left">
      <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDtCreateT)" onblur="lostFocus(this)" type="text" name="txtDtCreateT" size="25" value="<tbl:writeparam name="txtDtCreateT"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDtCreateT,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
    </tr>  
     <tr>   
        <td class="list_bg2" width="17%"  align="right">Ͷ������</td>
        <td class="list_bg1" width="33%"  align="left">
      	<d:selcmn name="txtType" mapName="SET_C_CUSTOMERCOMPLAINTYPE" match="txtType" width="23" />
	</td>
     <%if("process".equals(actiontype)){%>
	<td class="list_bg1" width="50%"  colspan="2" align="right"></td>
     <%}else{%>
        <td class="list_bg2" width="17%"  align="right">����״̬</td>
        <td class="list_bg1" width="33%"  align="left">
      	<d:selcmn name="txtStatus" mapName="SET_C_CUSTOMERCOMPLAINSTATUS" match="txtStatus" width="23" />
    </td>
     <%}%>	
    </tr> 
     <% if("query".equals(actiontype)||("custQuery").equals(actiontype)){%>
     <tr>
        <td class="list_bg1" colspan="4" align="center"> 
	<input type="checkbox" name="queryType" >&nbsp��ѯ����
	</td>     
     </tr>
     <%}%>
    <tr> 
      <td class="list_bg2" colspan="4" align="center">
      	<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td background="img/button_bg.gif"><a href="javascript:cust_complain_query('<%=actiontype%>')" class="btn12">��    ѯ</a></td> 
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
         </tr>
        </table>
       </td>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	    <td><img src="img/mao.gif" width="1" height="1"></td>
	  </tr>
  </table>   
  <br>  
  <rs:hasresultset>
  <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">   
     <tr> 
          <td width="8%"  class="list_head"  align="center">ID</td>
          <td width="11%" class="list_head"  align="center">�ͻ�����</td>                    
          <td width="11%"  class="list_head"  align="center">��ϵ��</td> 
          <td width="11%"  class="list_head"  align="center">Ͷ������</td>         
          <td width="15%"  class="list_head"  align="center">��Ͷ����֯</td>
          <td width="11%"  class="list_head"  align="center">��Ͷ�߸���</td>
          <td width="11%"  class="list_head"  align="center">״̬</td>
          <td width="11%"  class="list_head"  align="center">�ط�״̬</td>
          <td class="list_head"  align="center">����ʱ��</td>
     </tr> 
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
 CustomerComplainDTO customerComplainDTO=(CustomerComplainDTO)pageContext.getAttribute("oneline");
 String custName=Postern.getCustomerNameByID(customerComplainDTO.getCustomerId());
 String str="";
 if("custQuery".equals(actiontype)){
	   Map map=Postern.getCallbackInfo(CommonKeys.CALLBACKINFOTYPE_C,customerComplainDTO.getCustComplainId());
	   str=map.toString();
	   str=(String) str.subSequence(1, str.length()-1);
	   String [] strs=str.split(",");
		for(int i =0;i<strs.length;i++){
			System.out.println(strs[i]);
			str=str+"&txtDynamicServey_"+strs[i].trim();
			System.out.println(str);
		}
}
int orgID=customerComplainDTO.getComplainedOrgId();
String orgName=Postern.getProviderNameByID(orgID);
if(orgName==null) orgName="";
%>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	 <td align="center" ><a href="javascript:cust_camplain_detail('<tbl:write name="oneline" property="custComplainId"/>','<%=actiontype%>');">
      	 <tbl:write name="oneline" property="custComplainId"/></a></td>  		
       	 <td align="center" ><%=custName%></td>
      	 <td align="center" ><tbl:write name="oneline" property="contactPerson"/></td>
      	 <td align="center" ><d:getcmnname typeName="SET_C_CUSTOMERCOMPLAINTYPE" match="oneline:type" /></td>
      	 <td align="center" ><tbl:WriteOrganizationInfo name="oneline" property="complainedOrgId"/></td>
      	 <td align="center" ><tbl:write name="oneline" property="complainedPerson"/></td>
      	 <td align="center" ><d:getcmnname typeName="SET_C_CUSTOMERCOMPLAINSTATUS" match="oneline:status" /></td>
      	 <td align="center" ><d:getcmnname typeName="SET_C_CPCALLBACKFLAG" match="oneline:callBackFlag" /></td>
      	 <td align="center" ><tbl:writedate name="oneline" property="dtCreate"/></td>
    </tbl:printcsstr>
</lgc:bloop> 
  <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>
</table> 

<table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table> 
 </rs:hasresultset>
</form> 
<script language=javascript>
function window_open(){
	if(document.frmPost.txtQueryType.value == 'queryAll')
		document.frmPost.queryType.checked = true;
}
window_open();
</script>    