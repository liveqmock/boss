<%
    response.setHeader("Pragma", "No-cache");
    response.setDateHeader("Expires", 1);
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "private");
%> 

<%@ taglib uri="/WEB-INF/template.tld" prefix="template" %>
<%@ page contentType="text/html;charset=GBK"%>

<%
String strExpires=request.getParameter("ExpirePage");
if ((strExpires!=null)&&(strExpires.compareTo("")!=0))
{  
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");      
    response.setDateHeader("Expires", 0);   
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">

<SCRIPT src="common/check.js" type="text/javascript"></SCRIPT>
<SCRIPT src="common/commonFunc.js" type="text/javascript"></SCRIPT>
<SCRIPT src="common/display.js" type="text/javascript"></SCRIPT>
<SCRIPT src="common/turn.js" type="text/javascript"></SCRIPT>
<script src="common/search.js"></script>
<script src="common/treeConstructor.js"></script>
<script src="common/menu2.js"></script>
<script src="common/calendar.js"></script>

<title><template:insert parameter="title" /></title>
<link href="css/oss.css" rel="stylesheet" type="text/css">
</head>

 
<body leftmargin="0" topmargin="0" background="images/kfpage/dot.gif" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <template:insert parameter="banner" />
  </tr>
</table>   

<template:insert parameter="menu" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top" width="180" > 
      <template:insert parameter="sidebar" />
    </td> 
    <td valign="top" width="819" align="left">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr>
        <td height="20"></td>  
        </tr>
        <tr>
        <td valign="top">
          <template:insert parameter="hintbar" />
        </td>  
        </tr>
        <tr>
        <td valign="top" align="center" style="word-break:break-all">
          <template:insert parameter="body" />
        </td> 
        </tr>
      </table>  
    </td>
    <td></td> 
  </tr>
     
</table>
    
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <template:insert parameter="footer" />
  </tr>
</table>  

</body>
</html>