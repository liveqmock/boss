<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="operator" prefix="oper" %>

<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.tree.DynamicRootNode" %>
<%@ page import="com.dtv.oss.web.util.*" %>

<form name="frmPost" method="post" action="">
	<table valign="top"  width="210" border="0" cellspacing="0" cellpadding="0" >
		<tr>
		<td>
			<table>
				<lgc:notempty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
				        <tr>
				          <td nowrap>  
				          <font size="2">当前操作员：<oper:curoper property="OperatorName" /></font>
				          </td>          
				        </tr>
				</lgc:notempty> 
				<lgc:empty name="<%=WebKeys.OPERATOR_SESSION_NAME%>" scope="session" >
				        <tr>
				          <td nowrap>  
				          <font size="2">没有当前操作员</font>
				          </td>          
				        </tr>
				</lgc:empty>
			</table>	
		</td>
		</tr>
		<tr>
		<td>
			<table>	
			<a href="district_setting_query_result.do?txtQryBelongTo=0&txtFrom=1&txtTo=10"  target="rightFrame" class="tree-control-unselected" onclick="self.location.href='treeAction.do?key=district_0'">行政区</a>
			</table>
		</td>
		</tr>
	</table>
</form>