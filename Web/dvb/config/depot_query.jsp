<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DepotDTO"%>

<SCRIPT language="JavaScript">
 function frm_check(){
	 
	if (!checkPlainNum(document.frmPost.txtDepotID, true,9, "仓库ID"))
	    return false;  
	return true;
}

function query_submit()
{
   
    if(frm_check())	
     document.frmPost.submit();
    
}

function depot_create()
{
	location.href = "depot_create.screen";
}

function depot_delete()
{
	if(check_chosen(document.frmPost,document.frmPost.ListID))
	{
		if(confirm("确定要删除选择的记录吗?")){
			document.frmPost.action="depot_delete.do";
			document.frmPost.submit();
		}
		return;
	}else{
		alert("缺少选择,无法完成操作!");
	}
}


function depot_detail(strID)
{
	location.href = "depot_detail_query.do?txtOPDepotID="+strID;
}
</SCRIPT>

<form name="frmPost" method="post" action="depot_query.do">
<input type="hidden" name="txtFrom" value="1"> 
<input type="hidden" name="txtTo" value="10">
 
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">仓库查询</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">仓库ID</td>
		<td class="list_bg1" width="33%"><input name="txtDepotID" type="text"
			class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtDepotID" />"></td>
		<td class="list_bg2" align="right" width="17%">仓库名称</td>
		<td class="list_bg1" width="33%"><input name="txtDepotName"
			type="text" class="text" maxlength="25" size="25"
			value="<tbl:writeparam name="txtDepotName" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">状态</td>
		<td class="list_bg1"><font size="2"> <d:selcmn name="txtDepotStatus"
			mapName="SET_G_GENERALSTATUS" match="txtDepotStatus" width="23" /> </font></td>
		<td class="list_bg2" align="right">仓库所有者</td>
		<td class="list_bg1">
      <input type="hidden" name="txtOwner" value="<tbl:writeparam name="txtOwner" />">
      <input type="text" name="txtOwnerDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOwner" />" class="text">
      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D','txtOwner','txtOwnerDesc')">
	</tr>
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
         <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
            
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:depot_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    
<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<!-- <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></td> -->
			<td class="list_head">仓库ID</td>
			<td class="list_head">仓库名称</td>
			<td class="list_head">仓库地址</td>
			<td class="list_head">状态</td>
			<td class="list_head">仓库所有者</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<!-- <td align="center"><input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="depotID"/>"></td> -->
				<td align="center"><a
					href="javascript:depot_detail('<tbl:write name="oneline" property="depotID"/>')"
					class="link12"><tbl:writenumber name="oneline" property="depotID" digit="6"/></a></td>
				<td><tbl:write name="oneline" property="depotName" /></td>
				<td align="center"><tbl:write name="oneline" property="detailAddress" /></td>
				<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS"
					match="oneline:status" /></td>
				<td align="center">
					<tbl:WriteOrganizationInfo name="oneline" property="ownerID" />
				</td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
			<td colspan="5" class="list_foot"></td>
		</tr>
	 
 
            <tr>
              <td align="right" class="t12" colspan="5" >
	     
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
 
<br>

<!-- 	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="删&nbsp;除" onclick="javascript:depot_delete()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	 -->
 

</rs:hasresultset>  
</form>
