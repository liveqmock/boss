<%@ taglib uri="bookmark" prefix="bk" %>
<SCRIPT language="JavaScript">
function once_submit()
{
    if (check_form())
    {
    	document.frmPost.txtAction.value = "create";
        document.frmPost.action = "product_stop_confirm.do";
        document.frmPost.submit();
    }
}
function update_submit()
{
    document.frmPost.action = "batchJob_query.do";
    document.frmPost.submit();
}
</SCRIPT>

<form name="frmPost" method="post" action="">
<input type="hidden" name="txtAction" size="20" value="">
<input type="hidden" name="func_flag" value="1103">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">创建批量任务单</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<jsp:include page="batchJob_create_common.jsp" />
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
		  		<bk:canback url="ownFeeBatch_query_result.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="ownFeeBatch_query_result.do"/>" class="btn12">上一步</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>
				<td width="20" ></td>		
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="批量欠费关停" onclick="javascript:once_submit()"></td>
		    	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  	</tr>
	  	</table></td>
	</tr>
</table> 
