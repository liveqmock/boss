<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DepotDTO"%>

<SCRIPT language="JavaScript">
  
function query_submit()
{
   
    
     document.frmPost.submit();
    
}

function manual_create()
{
	location.href = "manual_transfer_create.screen";
}
function ChangeSubType(){    
     if(document.frmPost.txtSheetType.value=="M")
      
       document.frmPost.txtSubType.disabled=false;
        
        else 
        document.frmPost.txtSubType.disabled=true;
     
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
	location.href = "manual_transfer_detail.do?txtSeqNo="+strID;
}
</SCRIPT>

<form name="frmPost" method="post" action="manual_transfer_query.do">
<input type="hidden" name="txtFrom" value="1"> 
<input type="hidden" name="txtTo" value="10">
 
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">手工流转设置查询</td>
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
	      <td class="list_bg2" align="right">流转起源组织</td>
              <td class="list_bg1">
    	      <input type="hidden" name="txtFromOrgId" value="<tbl:writeparam name="txtFromOrgId" />">
	      <input type="text" name="txtFromOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtFromOrgId" />" class="text">
	      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,P,O','txtFromOrgId','txtFromOrgDesc')">
	      </td>
	     <td class="list_bg2"><div align="right">单据类型</div></td>
               <td class="list_bg1"  align="left">
               <d:selcmn name="txtSheetType" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="txtSheetType" width="23"  onchange="ChangeSubType()"/>
             </td>    	
	</tr>
	<tr>
		<td class="list_bg2" align="right">流转目标组织</td>
		<td class="list_bg1"><font size="2">  
		<input type="hidden" name="txtToOrgId" value="<tbl:writeparam name="txtToOrgId" />">
	      <input type="text" name="txtToOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtToOrgId" />" class="text">
	      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,P,O','txtToOrgId','txtToOrgDesc')">
	      </td> 
	       <td class="list_bg2"><div align="right">单据子类型</div></td>
               <td class="list_bg1"  align="left">
               <d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" match="txtSubType" width="23"  />
             </td>    	
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
					value="新&nbsp;增" onclick="javascript:manual_create()"></td>
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
			 
			<td class="list_head">流水号</td>
			<td class="list_head">单据类型</td>
			<td class="list_head">流转起源组织</td>
			<td class="list_head">流转目标组织</td>
			<td class="list_head">排列顺序</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				 
				<td align="center"><a
					href="javascript:depot_detail('<tbl:write name="oneline" property="seqNo"/>')"
					class="link12"><tbl:write name="oneline" property="seqNo"/></a></td>
				<td align="center" ><d:getcmnname typeName="SET_S_ROLEORGNIZATIONORGROLE" match="oneline:sheetType" /></td>    
				<td align="center" ><tbl:WriteOrganizationInfo name="oneline" property="fromOrgId" /></td> 
				<td align="center" ><tbl:WriteOrganizationInfo name="oneline" property="toOrgId" /></td> 
				 
				<td align="center">
					<tbl:write name="oneline" property="priority"/>
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
 
 
 

</rs:hasresultset>  
</form>
