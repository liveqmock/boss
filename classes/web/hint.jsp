<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>系统提示</title>

</head>

<a name=top></a> 

<table width="410" border="0" cellspacing="0" cellpadding="0">
  <tr >
    <td >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        <td>&nbsp</td>
        </tr>
        <tr align="center">         
    	<td class="title"><strong><font color="red" >通知</font></strong></td>          
        </tr>
        <tr>
        <tr>
        <td>&nbsp</td>
        </tr>
        <tr>
        <td>&nbsp</td>
        </tr>
        <tr>
        <td>
        <table width="100%" border="0" cellpadding="5" cellspacing="1">
  <rs:hasresultset>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <%BillBoardDTO dto=(BillBoardDTO)pageContext.getAttribute("oneline");
      String titlename="title"+dto.getSeqNo();
      String grade=dto.getGrade();%>
        <tr> 
          <td width="40%">           
          <font color="blue"><a href="#<%=titlename%>"><img src="img/dot_next.gif" width="5" height="7" border="0">
          <strong><tbl:write name="oneline" property="name" /> </strong>
          </a>        
	  </font>
         </td>
         <td width="18%">
         <strong>级别：</strong><%if((CommonKeys.BILLBOARD_GRADE_I).equals(grade)){%><font color="red"><d:getcmnname typeName="SET_G_BILLBOARDGRADE" match="oneline:grade" /></font><%}
         else{%><d:getcmnname typeName="SET_G_BILLBOARDGRADE" match="oneline:grade" /><%}%>
         </td>
         <td width="37%">
         <strong>发布人：</strong>
        <tbl:write name="oneline" property="publishPerson"/>
         </td>
        </tr>
        <tr>        
    </tbl:printcsstr>
    </lgc:bloop>      
  </rs:hasresultset>  
  </table>  
  </td>
  </tr>
  <tr>
  <td>
  <table width="100%" border="0" cellpadding="5" cellspacing="1">
  <rs:hasresultset>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline1" >
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <tr>
    <td colspan="3" align="center">
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   </td>
   </tr>
    <%BillBoardDTO dto1=(BillBoardDTO)pageContext.getAttribute("oneline1");
      String titlename1="title"+dto1.getSeqNo();%>
      <tr>
        <td colspan="3">&nbsp</td>
        </tr> 
        <tr>
        <td  colspan="3" align="center">
        <a name=<%=titlename1%>><strong><tbl:write name="oneline1" property="name"/></strong></a>
        </td>
        </tr>
        <tr> 
          <td colspan="2">          
          <tbl:write name="oneline1" property="description" />  
         </td>
         <td align="right"><a href="#top"><img src="img/icon_top.gif" width="15" height="15" border="0"></a>
         </td>
        </tr> 
        <tr>
        <td ><strong>发布原因：</strong>
        <tbl:write name="oneline1" property="publishReason"/>
        </td>
        <td ><strong>发布时间：</strong>
        <tbl:write name="oneline1" property="dtCreate"/>
        </td>
        <td>&nbsp</td>        
        </tr>        
        <tr>
                     
    </tbl:printcsstr>
    </lgc:bloop>      
  </rs:hasresultset>  
  </table>
  </td>
  </tr>
  </table>  
   </td>
  </tr>  
</table>

</html>