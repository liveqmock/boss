<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>

function frmSubmit(url){
		document.frmPost.action = url;
    document.frmPost.submit();
}
</Script>
<%
String txtSerialNo = request.getParameter("txtSerialNoBegin");
Map detailDtoMap = new HashMap();
if(txtSerialNo != null && !"".equals(txtSerialNo))
  detailDtoMap = Postern.getDeviceTransitionDetailDTOBySerialno(txtSerialNo);
Map mapDepots = Postern.getAllDepot();
%>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�豸��ת��ʷ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
   <form name="frmPost" method="post" action="dev_detail.do" >
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
  <br>
  
   <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" nowrap>��¼��</td>
          <td class="list_head" nowrap>��ת����</td>
          <td class="list_head" nowrap>��תԭ��</td>
          <td class="list_head">�˳�������</td>
          <td class="list_head">�˳���</td>
          <td class="list_head" width="7%">�˳�ǰ״̬</td>
          <td class="list_head">���������</td>
          <td class="list_head">�����</td>
          <td class="list_head" width="7%">�����״̬</td>
          <td class="list_head" width="10%">����ʱ��</td>
          <td class="list_head" nowrap>����Ա</td>
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	DeviceTransitionDTO devDto = (DeviceTransitionDTO)pageContext.getAttribute("oneline");
	if(devDto == null) devDto = new DeviceTransitionDTO();
  DeviceTransitionDetailDTO detailDto = new DeviceTransitionDetailDTO();
  if(detailDtoMap.get(devDto.getBatchID()+"") != null)
  	detailDto = (DeviceTransitionDetailDTO)detailDtoMap.get(devDto.getBatchID()+"");
  pageContext.setAttribute("detailDto",  detailDto);
  String strFromAddress = "";
        
  if (WebUtil.StringHaveContent(detailDto.getFromType()))
  {
   	if ("D".equals(detailDto.getFromType()))  //�ֿ�
		{
			strFromAddress = (String)mapDepots.get(String.valueOf(detailDto.getFromID()));
		}
		else if ("T".equals(detailDto.getFromType()))  //��֯����
		{
			strFromAddress = Postern.getOrganizationDesc(detailDto.getFromID());
		}
		else if ("B".equals(detailDto.getFromType()))  //�û�
		{
			strFromAddress = String.valueOf(detailDto.getFromID());
		}
  }	
	if (strFromAddress==null) strFromAddress="";
    
    
  String strToAddress = "";  
  if (WebUtil.StringHaveContent(detailDto.getToType()))
  {
   	if ("D".equals(detailDto.getToType()))  //�ֿ�
	  {
			strToAddress = (String)mapDepots.get(String.valueOf(detailDto.getToID()));
	  }
		else if ("T".equals(detailDto.getToType()))  //��֯����
		{
			strToAddress = Postern.getOrganizationDesc(detailDto.getToID());
		}
		else if ("B".equals(detailDto.getToType()))  //�û�
		{
			strToAddress = String.valueOf(detailDto.getToID());
		}
  }	
  if (strToAddress==null) strToAddress="";
  
  String operatorName = Postern.getOperatorNameByID(devDto.getOperatorID());
  
%>
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td align="center" nowrap><tbl:write name="detailDto" property="SeqNo"/></td>
      <td align="center"><d:getcmnname typeName="SET_D_DTACTION" match="oneline:Action" /></td>
      <td align="center"><d:getcmnname typeName="SET_D_TRANSFERREASON" match="oneline:StatusReason" /></td>
      <td align="center" nowrap><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="detailDto:FromType" /></td>
      <td align="center"><%=strFromAddress%></td>
      <td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="detailDto:FromDeviceStatus" /></td>
      <td align="center" nowrap><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="detailDto:ToType" /></td>
      <td align="center"><%=strToAddress%></div></td>
      <td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="detailDto:ToDeviceStatus" /></td>
      <td align="center"><tbl:writedate name="oneline" property="CreateTime" includeTime="true" /></td>
      <td align="center" nowrap><%=operatorName%></td>   
     </tr>
    
</lgc:bloop>    
<tr>
    <td colspan="11" class="list_foot"></td>
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
<BR>
<BR>
  </rs:hasresultset> 
	

				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr align="center">
         <td><table  border="0" cellspacing="0" cellpadding="0">
        	<tr>
						<td><img src="img/button2_r.gif" border="0"></td>
						<td background="img/button_bg.gif"><a href="javascript:frmSubmit('<bk:backurl property='dev_detail.do' />')" class="btn12">�� ��</a></td>
						<td><img src="img/button2_l.gif" border="0"></td>
		      </tr>
		      </td>
		     </tr>
        </table>
	
		
		
</form> 
    </td>
  </tr>
  
  
</table>   
 
