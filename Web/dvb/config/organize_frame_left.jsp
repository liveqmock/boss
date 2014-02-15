<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
</head>

<body>

<%
    String flag =(request.getParameter("flag") ==null) ? "0" :request.getParameter("flag");
%>


<form name="frmPost" method="post" action="">
<frameset cols="210,*" frameborder="yes" border="1" framespacing="0">
	<table valign="top"  width="210" border="0" cellspacing="0" cellpadding="0" >
		<tr>
		<td>
			<table>	
			<tr valign="middle">
<td></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=parent_company"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handledownmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="6"> <a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/parent_company_config.do?orgID=0&orgType=C&txtFrom=1&txtTo=10"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=parent_company'">总公司</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_1"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handledownmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="5"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_1'">总公司</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=department_1"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handledownmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="4"> <a href="organize_frame_right.screen"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='javascript:show_detail()'">部门</a></td>
</tr>
<% if (flag.equals("0")){ %>
<tr valign="middle" id ="prefered" style="display:none">
<% } else { %>
<tr>
<% } %>
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_2"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_2'">总师办</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_3"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_3'">市场部</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_4"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_4'">物资部</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_5"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_5'">客服中心</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_6"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_6'">电话中心</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_7"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_7'">营帐中心</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_8"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightlast.gif" alt="close node" border="0"></a></td>
<td colspan="3"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_8'">前端运维</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=cooperate_1"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightmiddle.gif" alt="close node" border="0"></a></td>
<td colspan="4"> <a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/cooperate_config.do?orgID=1&orgType=P&txtFrom=1&txtTo=10"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=cooperate_1'">合作伙伴</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linemiddlenode.gif" alt="close node" border="0"></td>
<td colspan="4"> <a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/business_config.do?orgID=1&orgType=O&txtFrom=1&txtTo=10"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=business_1'">营业网点</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=street_1"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightlast.gif" alt="close node" border="0"></a></td>
<td colspan="4"> <a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/child_company_config.do?orgID=1&orgType=B&txtFrom=1&txtTo=10"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=street_1'">分公司</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/linevertical.gif" alt="" border="0"></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=childKey_117"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightlast.gif" alt="close node" border="0"></a></td>
<td colspan="5"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=childKey_117'">上海分公司</a></td>
</tr>
<tr valign="middle">
<td></td>
<td><a href="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/treeAction.do?key=privilege_controle"><img src="file:///C|/Documents%20and%20Settings/250803y/&#26700;&#38754;/images/tree/handlerightlast.gif" alt="close node" border="0"></a></td>
<td colspan="6"> <a href="#" target="leftFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=privilege_controle'">权限控制</a></td>
</tr>

			</table>
		</td>
		</tr>
	</table>
  <input type ="hidden" name ="flag" value="<%=flag%>" >	

</form>

</body>
</html>
<Script language="Javascript">
   function show_detail(){
       if (document.all("flag").value =="0"){
           document.all("flag").value ="1";
       } else{
           document.all("flag").value ="0";
       }  
       document.frmPost.submit();
   }

</Script>
