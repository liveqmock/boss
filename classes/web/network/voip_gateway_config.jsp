<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.wrap.VOIPEventWrap"%>
<%@ page import="com.dtv.oss.dto.SystemEventDTO"%>

<SCRIPT language="JavaScript">

function modify_submit(devsModel,devsType,desc)
{
	document.location.href = "voip_gateway_modify.screen?txtDeviceModel="+devsModel+"&txtDevsType="+devsType+"&txtdescription="+desc;
}

function add_submit()
{
	document.location.href = "voip_gateway_add.screen";
}
function back_submit(){
	url="voip_info_index.screen";
        document.location.href=url;
}
</SCRIPT>

<form name="frmPost" method="post" action="">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">网关型号列表</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>

<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head"><P align="center">设备型号</P></td>
			<td class="list_head"><P align="center">网关型号标识</P></td>
			<td class="list_head"><P align="center">描述</P></td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<td align="center"><a href="javascript:modify_submit('<tbl:write name="oneline" property="deviceModel" />','<tbl:write name="oneline" property="devsType" />','<tbl:write name="oneline" property="description" />')"><tbl:write name="oneline" property="deviceModel" /></a></td>
				<td align="center"><tbl:write name="oneline" property="devsType" /></td>
				<td align="left"><tbl:write name="oneline" property="description" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="4" class="list_foot"></td>
		</tr>
	</table>
	
	<table border="0" align="right" cellpadding="0" cellspacing="8">
		<tr>
			<td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span
				class="en_8pt">/</span>共<span class="en_8pt"><rs:prop
				property="pageamount" /><span>页</td>
			<td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
			<rs:notfirst>
				<td align="right"><img src="img/dot_top.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)"
					class="link12">首页</a></td>
				<td align="right"><img src="img/dot_pre.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)"
					class="link12">上一页</a></td>
			</rs:notfirst>
			<rs:notlast>
				<td align="right"><img src="img/dot_next.gif" width="6px" height="11px"></td>
				<td><a
					href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)"
					class="link12">下一页</a></td>
				<td align="right"><img src="img/dot_end.gif" width="17px" height="11px"></td>
				<td><a
					href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)"
					class="link12">末页</a></td>
			</rs:notlast>
			<td align="right">跳转到</td>
			<td><input name="txtPage" type="text" class="page_txt"></td>
			<td>页</td>
			<td><input name="imageField" type="image" src="img/button_go.gif"
				width="28px" height="15px" border="0"
				onclick="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
		</tr>
	</table>
</rs:hasresultset>
 <br>
 <br>
 <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:add_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    
  
	   </td>
	</tr>
    </table>   
</form>
