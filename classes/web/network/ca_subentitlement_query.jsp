<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CasubentitlementDTO"%>

<SCRIPT language="JavaScript">
function check_blank(){
	if(document.frmPost.cardSN.value==''){
		alert("请输入智能卡序列号");
		document.frmPost.cardSN.focus();
		return false;
	}
	return true;
}
function query_submit()
{
	if(check_blank())
		document.frmPost.submit();
	
    
}

function event_detail(strID)
{
	location.href = "ca_event_detail.do?queryFlag=event&OPEventID="+strID;
}
function back_submit(){
	url="ca_info_index.screen";
    document.location.href= url;
}
function checkChoose(){
	var result="";
	if(document.frmPost.chooses[0]){
		for(i=1 ;i< document.frmPost.chooses.length ;i++){
			if(document.frmPost.chooses[i].checked){
				result=result + document.frmPost.chooses[i].value+"";
			}
		}
	}
	if(result==""){
		alert("没有选择相关的项！batch_query_show_result");
		return false;
	}
	else 
		document.frmPost.txtQueryResultItemNOs.value=result;
		return true;
}
function cancleSubentitilement(){
	
	
}
function addSubentitilement(){
	
}

</SCRIPT>

<form name="frmPost" method="post" action="ca_subentitlement_query.do">
<input type="hidden" name="txtFrom" size="20" value="1"> 
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="chooses"  value="">
<input type="hidden" name="txtQueryResultItemNOs"  value="">
<INPUT TYPE="hidden" name="queryFlag" value="subentitlement">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">查询智能卡有效授权</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">智能卡序列号*</td>
		<td class="list_bg1" width="33%"><input name="cardSN"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="cardSN" />"></td>
	</tr>
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="查&nbsp;询" onclick="javascript:query_submit()"></td>
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
		<!--	<td class="list_head"></td>  -->
			<td class="list_head">主机ID</td>
			<td class="list_head">SUB_ID</td>
			<td class="list_head">OPI_ID</td>
			<td class="list_head">CA产品ID</td>
			<td class="list_head">CA产品名称</td>
			 <td class="list_head">授权时间</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		
		<% 
		   CasubentitlementDTO dto = (CasubentitlementDTO)pageContext.getAttribute("oneline");
		    String CaProductName =Postern.getCaProductNameByCaId(dto.getCaproductid());
		    if(CaProductName==null)
		    CaProductName=""; 
		   
		%>
			
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
			<!--	<td><tbl:checkbox name="chooses"  value="oneline:cardsn"  multipleMatchFlag="true" /></td> -->
				 
				<td align="center"><tbl:write name="oneline" property="hostId"/></td>
				<td align="center"><tbl:write name="oneline" property="subscriberId"/></td>
				<td align="center"><tbl:write name="oneline" property="opiID"/></td>
				<td align="center"><tbl:write name="oneline" property="caproductid"/></td>
				<td align="center"><%=CaProductName%></td> 
				<td align="center"><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
        <td colspan="6" class="list_foot"></td>
        </tr>
 
            <tr>
              <td align="right" class="t12" colspan="6" >
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
	<!--
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="取消授权" onclick="javascript:cancleSubentitilement()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="增加授权" onclick="javascript:addSubentitilement()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>          
        </tr>
      </table>
      -->	
     
 
	

