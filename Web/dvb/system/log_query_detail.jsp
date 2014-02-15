<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.SystemLogDTO" %>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">ϵͳ��־��ϸ��Ϣ</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
   <%
      SystemLogDTO dto = (SystemLogDTO)pageContext.getAttribute("oneline");
      String orgName =Postern.getOrgNameByOrgID(dto.getOrgID());
      if (orgName ==null) orgName ="";
   %>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">��ˮ��</font></td> 
          <td class="list_bg1" width="33%"><font size="2" ><tbl:writenumber name="oneline" property="SequenceNo" digit="9"/></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">����ԱLoginID</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="loginID" /></font></td>
       </tr>    
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">����Ա����</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="operatorName" /></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">��������</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="machineName" /></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">��������</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><%=orgName %></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">ģ����</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="moduleName" /></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">��־����</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><d:getcmnname typeName="SET_S_LOGTYPE" match="oneline:logType" /></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">���ؼ���</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><d:getcmnname typeName="SET_S_LOGCLASS" match="oneline:logClass" /></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">ʱ��</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:writedate name="oneline" property="CreateDateTime"/>  <tbl:writedate name="oneline" property="CreateDateTime" onlyTime="true"/></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">����</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="Operation"/></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">����</font></td> 
          <td class="list_bg1" colspan="3"><font size="2"><tbl:write name="oneline" property="LogDesc"/></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">�ͻ�֤��</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="CustomerID"/></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">�ͻ�����</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="CustomerName"/></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">�ʽ��ʺ�</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="AccountID"/></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2">ҵ���ʺ�</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="ServiceAccountID"/></font></td>
       </tr>
       <tr> 
          <td align="right" class="list_bg2" width="17%"><font size="2">�ͻ���ƷID</font></td> 
          <td class="list_bg1" width="33%"><font size="2"><tbl:write name="oneline" property="psID"/></font></td>
          <td align="right" class="list_bg2" width="17%"><font size="2"></font></td> 
          <td class="list_bg1" width="33%"><font size="2"></font></td>
       </tr>
</table>
</lgc:bloop>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
				<bk:canback url="log_query_result.do/log_query_for_cust.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="log_query_result.do/log_query_for_cust.do"/>" class="btn12">��&nbsp;��</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>			
		  	</tr>
	  	</table></td>
	</tr>
</table>
