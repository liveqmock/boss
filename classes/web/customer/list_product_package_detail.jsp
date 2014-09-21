<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.PackageLineDTO" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>包产品明细</title>
</head>
<table align="center" width="95%" border="0" cellspacing="5" cellpadding="5">
    <tr>
       <td align="center"> 
         <font size="3"><strong><tbl:writeparam name="txtPackageName" />产品明细</strong></font> 
      </td>
    </tr>
</table>
<table align="center" width="95%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   <tr class="list_head">
     <td width="70%"  align="center" class="list_head">名称</td>
     <td width="30%"  align="center" class="list_head">数量</td>          
   </tr>
        
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline"> 
   <%
       
	PackageLineDTO packageLineDTO=(PackageLineDTO)pageContext.getAttribute("oneline");
	String productName=Postern.getProductNameByID(packageLineDTO.getProductId());
    %>
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td  align="center" ><%=productName%></td>
        <td  align="center" ><tbl:write name="oneline" property="productNum" /></td>
     </tbl:printcsstr>
   </lgc:bloop>
</table>
<br>
 <p align="center"><a href="#"onClick="window.close()"><font size="2">关闭窗口</font></a></p>
</body>
</html>