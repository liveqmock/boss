<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.Map,
                 java.util.HashMap" %>

<script language=javascript>
function check_form(){
    if (document.frmPost.txtPreferedStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtPreferedEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtPreferedStartDate,document.frmPost.txtPreferedEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){

		return false;
	}

    if (document.frmPost.txtCallBackStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCallBackStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCallBackEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCallBackEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCallBackStartDate,document.frmPost.txtCallBackEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){

		return false;
	}
	if(!checkPlainNum(document.frmPost.txtID,true,9,"�������"))
		return false;
	if(!checkPlainNum(document.frmPost.txtUserID,true,9,"�ͻ�֤��"))
		return false;
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="service_interaction_view.do?txtID="+strId;
}
</script>

<%
  String txtType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS+";"+CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB;
  // ԤԼ���� and �ŵ�����
  String txtType2 = CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU+";"+CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO;

  Map csiStatusMap =new HashMap();
  csiStatusMap.put(CommonKeys.CUSTSERVICEINTERACTION_STATUS_FAIL,"�����ɹ�");
  csiStatusMap.put(CommonKeys.CUSTSERVICEINTERACTION_STATUS_SUCCESS,"����ɹ�");
 
  pageContext.setAttribute("CsiStatusMap", csiStatusMap);
                   
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�����طã�������ѯ</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="service_interaction_query_result_for_callback.do" >
     <input type="hidden" name="txtFrom" size="20" value="1">
     <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtType"  value="<%=txtType%>">
     <input type="hidden" name="txtType2"  value="<%=txtType2%>">
     <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td class="list_bg2" width="17%" align="right">�������</td>
          <td class="list_bg1" width="33%" align="left">
             <input type="text" name="txtID" maxlength="9" size="25"  value="<tbl:writeparam name="txtID" />" class="text">
           </td>
           <td class="list_bg2" width="17%" align="right">�طñ�־</td>
	   <td class="list_bg1" width="33%" align="left">
              <d:selcmn name="txtCallBackFlag" mapName="SET_C_CPCALLBACKFLAG" match="txtCallBackFlag" width="25" />
          </td>
        </tr>        
        <tr> 
	   <td class="list_bg2" width="17%" align="right">�ͻ�֤��</td>
	   <td class="list_bg1" width="33%" align="left">
              <input type="text" size="25" maxlength="9" name="txtUserID" value="<tbl:writeparam name="txtUserID"/>" class="text">
          </td>
          <td class="list_bg2" width="17%" align="right">������</td>
          <td class="list_bg1" width="33%" align="left">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	        <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
          </td>
        </tr> 
        
        <tr>
          <td class="list_bg2" width="17%" align="right">ԤԼ��������</td>
          <td class="list_bg1" width="33%" align="left">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedStartDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtPreferedStartDate" value="<tbl:writeparam name="txtPreferedStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedEndDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtPreferedEndDate" value="<tbl:writeparam name="txtPreferedEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
          <td class="list_bg2" width="17%" align="right">����ʱ��</td>
          <td class="list_bg1" width="33%" align="left">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             - 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" width="17%" align="right">�ط�����</td>
          <td class="list_bg1" width="33%" align="left">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCallBackStartDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCallBackStartDate" value="<tbl:writeparam name="txtCallBackStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCallBackStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             --
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCallBackEndDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCallBackEndDate" value="<tbl:writeparam name="txtCallBackEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCallBackEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
          <td  class="list_bg2" width="17%" align="right">����״̬</td>
          <td class="list_bg1" width="33%" align="left">
             <tbl:select name="txtStatus" set="CsiStatusMap" match="txtStatus" width="25" />
          </td>
         </tr>
         <tr>
           <td width="100%" align="center" colspan="4">
             <table  border="0" cellspacing="0" cellpadding="0">
              <tr>
               <td width="10">&nbsp;</td>
               <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
               <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="�� ѯ"></td>
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
      <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td class="list_head">�������</td>
          <td class="list_head">��������</td>
          <td class="list_head">�ͻ�֤��</td>
          <td class="list_head">��װ��ַ</td>
          <td class="list_head">�ͻ�����</td>
          <td class="list_head">�طñ�־</td>
          <td class="list_head">����״̬</td>
        </tr>

     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
           com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap wrap = (com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap)pageContext.getAttribute("oneline");
           pageContext.setAttribute("oneline", wrap.getCsiDto());
           pageContext.setAttribute("onecust", wrap.getNciDto());
           pageContext.setAttribute("custaddr", wrap.getCustAddrDto());
           String strUrl="service_interaction_view.do";
        %>                
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="<%=strUrl%>?txtID=<tbl:write name="oneline" property="Id"/>" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
          <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:Type" /></td>
          <td align="center" ><tbl:writenumber name="oneline" property="CustomerID" digit="7" /></td>
          <td align="center" class="t12" ><tbl:write name="custaddr" property="DetailAddress"/></td>
          <td align="center" class="t12" ><tbl:write name="onecust" property="Name"/></td>
          <td align="center" class="t12" ><d:getcmnname typeName="SET_C_CPCALLBACKFLAG" match="oneline:callBackFlag" /></td>
          <td align="center" class="t12" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="oneline:status" /></td>
        </tbl:printcsstr>            
      </lgc:bloop>    
      <tr>
        <td colspan="7" class="list_foot"></td>
      </tr>
  
      <tr class="trline" >
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
    </rs:hasresultset>         
      
</form>   
  