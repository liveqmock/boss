<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<br>
<br>
<br>
<br>
<br>
<br>
<br>

<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"><img src="img/excuting_p5.gif"></div></td>
</tr>
</table>
  
<form name="frmPost" method="post" action="batch_query_excuted.do" >

     <input type="hidden" name="txtActionType"  value="excute"  >
     <input type="hidden" name="txtQueryIDs" value="<tbl:writeparam name="txtQueryIDs" />"  >
     <input type="hidden" name="func_flag" value="8004">
     
     <tbl:generatetoken />

</form>

<%
	if(request.getParameter("txtQueryIDs")==null || "".equals(request.getParameter("txtQueryIDs"))){
		
%>

<Script language=Javascript>
	alert("参数错误，无法执行！");
	document.frmPost.action="menu_batch_query.do";
	document.frmPost.submit();   
</Script>

<%	}
	else { 
%>

<Script language=Javascript>
	document.frmPost.action="batch_query_excuted.do";
	document.frmPost.submit();   
</Script>

<%
	}
%>