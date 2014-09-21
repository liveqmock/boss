<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<SCRIPT language="JavaScript">

function back_submit(){
	url="ca_info_index.screen";
    document.location.href= url;

}
</SCRIPT>
<table width="820" border="0" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td width="820">
<form name="frmPost" method="post" action="ca_command_query.do">
<input type="hidden" name="txtFrom" size="20" value="1"> 
<input type="hidden" name="txtTo" size="20" value="10">  
<input type="hidden" name="queryFlag" size="20" value="cacommand">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">CA命令列表</td>
	</tr>
</table>
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
 <rs:hasresultset>
	<table border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg" width="100%">
		<tr class="list_head">
			<td class="list_head" width="10%" >命令ID</td>
			<td class="list_head" width="20%" >命令名称</td>
			<td class="list_head" width="40%">命令参数</td>
			<td class="list_head" width="30%">命令描述</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<td align="center"><tbl:writenumber name="oneline" property="commandID" digit="8"/></td>
				<td align="center"><tbl:write name="oneline" property="commandName" /></td>
				<td style="word-break:break-all"><span><tbl:write name="oneline" property="commandString" /></span></td>
				<td align="left"><tbl:write name="oneline" property="description" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
	<tr>
        <td colspan="4" class="list_foot"></td>
        </tr>
 
            <tr>
              <td align="right" class="t12" colspan="4" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
	</table>

</rs:hasresultset>

		<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
				<td><img src="img/button2_l.gif" width="11" height="20"></td>

			</tr>
		</table>
</form>
</td>
	</tr>
</table>