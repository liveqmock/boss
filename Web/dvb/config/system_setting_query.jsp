<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.dto.SystemSettingDTO" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
function view_detail_click(strId)
{
	self.location.href="system_setting_detail.do?txtName="+strId;
}
 
</SCRIPT>

 

	 
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">系统全局配置信息查询</td>
	</tr>
</table>
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td width="18%" class="list_head">参数名称</td>
			<td class="list_head">描述</td>
			<td class="list_head">取值类型</td>
			<td class="list_head">取值</td>
			<td class="list_head">状态</td>
			<td width="10%" class="list_head">创建时间</td>
		</tr>
		
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		 
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				 <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="name"/>')" class="link12" ><tbl:write  name="oneline" property="name"/></a></td>
				 
				<td align="center"><tbl:write name="oneline" property="description" /></td>
				<td align="center"><d:getcmnname typeName="SET_G_PARAMETERTYPE" match="oneline:valueType"/></td>
				<td align="center"><tbl:write name="oneline" property="value" /></td>
				 
				 <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>    
				<td align="center"><tbl:writedate name="oneline" property="dtCreate" includeTime="true" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="6" class="list_foot"></td>
        </tr>
 
            
	</table>
 
 
	 
 
 
