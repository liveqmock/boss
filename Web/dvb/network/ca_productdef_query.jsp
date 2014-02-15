<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CAProductDefDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
	return true;
}

function query_submit()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function ca_product_create()
{
	location.href = "ca_productdef_create.screen";
}
function view_detail_click(strId,strOpid,strProductid)
{
	self.location.href="ca_productdef__detail.do?txtHostID="+strId+"&txtOPI_ID="+strOpid+"&txtCAProductID="+strProductid+"&queryFlag=caproductdef";
}

function delete_object(strId,strOpid,strProductid){
    if( confirm("确定要删除该记录吗?") ){
        self.location.href="ca_product_op.do?txtHostID="+strId+"&txtOPI_ID="+strOpid+"&txtCAProductID="+strProductid+"&txtActionType=CA_PRODUCTDEF_DELETE";
    }
	
}

 

function back_submit(){
	url="ca_info_index.screen";
    document.location.href= url;

}
</SCRIPT>

<form name="frmPost" method="post" action="ca_productdef_query.do">
<input type="hidden" name="txtFrom" value="1"> 
<input type="hidden" name="txtTo" value="10"> 
<input type="hidden" name="queryFlag" value="caproductdef"> 
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">CA产品定义列表</td>
	</tr>
</table>
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 <rs:hasresultset>
 
 
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head">主机名称</td>
			<td class="list_head">OPI_ID</td>
			<td class="list_head">CA产品ID</td>
			<td class="list_head">CA产品名称</td>
			<td class="list_head" width="10%">操作</td> 
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<%
				CAProductDefDTO dto=(CAProductDefDTO)pageContext.getAttribute("oneline");

				String hostName=(String)Postern.getHostNameById(dto.getHostID());
				if(hostName==null)
				hostName="";
                                
				%>
				<td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="hostID"/>','<tbl:write name="oneline" property="opiID"/>','<tbl:write name="oneline" property="caProductID"/>')" class="link12" ><%=hostName%></a></td>
				 
				<td align="center"><tbl:write name="oneline" property="opiID" /></td>
				<td align="center"><tbl:write name="oneline" property="caProductID" /></td>
				<td align="center"><tbl:write name="oneline" property="productName" /></td>
				<td align="center" ><a href="javascript:delete_object('<tbl:write name="oneline" property="hostID"/>','<tbl:write name="oneline" property="opiID"/>','<tbl:write name="oneline" property="caProductID"/>')"  class="link12">删除</a></td>
				 
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


		<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
				<td><img src="img/button2_l.gif" width="11" height="20"></td>

				<td width="20"></td>

				<td><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:ca_product_create()"></td>
				<td><img src="img/button_r.gif" width="22" height="20"></td>
			</tr>
		</table>
</rs:hasresultset>
</form>
