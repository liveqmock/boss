<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_form(){
	if (!check_TenDate(document.frmPost.txtPublishStartDate, true, "发布起始日期"))
     	return false;	
  	if (!check_TenDate(document.frmPost.txtPublishEndDate, true, "发布结束日期"))
    	return false;	

  	if(!compareDate(document.frmPost.txtPublishStartDate,document.frmPost.txtPublishEndDate,"发布起始日期必须小于等于发布结束日期"))
		return false;  	

	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}


function cust_campaign_detail_click(seqNo){
	self.location.href="bill_board_detail.do?txtSeqNo="+seqNo;
}
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">公告信息查询</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
    
<form name="frmPost" action="bill_board_query.do" method="post" >    
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" class="list_bg">
     <tr>  
      <td class="list_bg2" width="17%"><div align="right">公告信息名称</div></td>
      <td class="list_bg1" width="83%" colspan="3" align="left">
		<input name="txtName"  value="<tbl:writeparam name="txtName" />" size="82">
		</td>
    </tr>
    <tr>   
      <td class="list_bg2"><div align="right">发布人</div></td>
      <td class="list_bg1"  align="left" >
        <input type="text" name="txtPublishPerson" size="25" value="<tbl:writeparam name="txtPublishPerson"/>" >
		</td>
		<td class="list_bg2"><div align="right">重要度</div></td>
      <td class="list_bg1"  align="left" >
      	<input type="text" name="txtGrade" size="25" value="<tbl:writeparam name="txtGrade"/>" >
      </td>     	  	
    </tr>
    <tr>  
      <td class="list_bg2" width="17%"><div align="right">发布原因</div></td>
      <td class="list_bg1" width="83%" colspan="3" align="left">
		<input name="txtPublishReason" size="82" value="<tbl:writeparam name="txtPublishReason" />">
    </tr>
    <tr>   
      <td class="list_bg2"><div align="right">发布起始日期</div></td>
      <td class="list_bg1"  align="left" >
        <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPublishStartDate)" onblur="lostFocus(this)" type="text" name="txtPublishStartDate" size="25" value="<tbl:writeparam name="txtPublishStartDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPublishStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
      <td class="list_bg2"><div align="right">发布结束日期</div></td>
      <td class="list_bg1"  align="left" >
      	<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPublishEndDate)" onblur="lostFocus(this)" type="text" name="txtPublishEndDate" size="25" value="<tbl:writeparam name="txtPublishEndDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPublishEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>     	  	
    </tr>
    <tr>  
      <td class="list_bg2" width="17%"><div align="right">状态</div></td>
      <td class="list_bg1" width="83%" colspan="3" align="left">
		<input name="txtStatus" size="25"  value="<tbl:writeparam name="txtStatus" />">
    </tr>
    <tr>  
      <td class="list_bg2" colspan="4" align="center">
          <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查    询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="22"></td>
			  </tr>
	      </table>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>  
 <br>
 
<rs:hasresultset>

   <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
      <tr class="list_head"> 
          <td width="10%" class="list_head"><div align="center">记录号</div></td>
          <td width="25%" class="list_head"><div align="center">公告信息名称</div></td>                    
          <td width="10%" class="list_head"><div align="center">发布人</div></td> 
          <td width="25%" class="list_head"><div align="center">发布原因</div></td>         
          <td width="10%" class="list_head"><div align="center">重要度</div></td>
          <td width="10%" class="list_head"><div align="center">发布日期</div></td>
          <td width="10%" class="list_head"><div align="center">状态</div></td>
      </tr>   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	 <td align="center" ><a href="javascript:cust_campaign_detail_click('<tbl:write name="oneline" property="seqNo"/>');"><tbl:write name="oneline" property="seqNo"/></a></td>		
      	 <td align="center" ><tbl:write name="oneline" property="name"/></td>
      	 <td align="center" ><tbl:write name="oneline" property="publishPerson"/></td>
      	 <td align="center" ><tbl:write name="oneline" property="publishReason"/></td>
      	 <td align="center" ><d:getcmnname typeName="SET_G_BILLBOARDGRADE" match="oneline:grade" /></td>
      	 <td align="center" ><tbl:writedate name="oneline" property="publishDate" /></td>
      	 <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>
    </tbl:printcsstr>
</lgc:bloop> 
  <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
  </table>

<table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
 
 </rs:hasresultset> 
 <input type="hidden" name="txtFrom" >
<input type="hidden" name="txtTo" >           
</form> 
 
 

     
      
      
      
      
      