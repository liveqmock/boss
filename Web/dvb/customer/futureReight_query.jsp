<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.FutureRightDTO" %>

<script language=javascript>
function check_form(){
       	if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "创建开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "创建结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"创建结束日期必须大于等于创建开始日期")){
		
		return false;
	}
	
	if (document.frmPost.txtExcuteStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtExcuteStartDate, true, "实际行权开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtExcuteEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtExcuteEndDate, true, "实际行权结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtExcuteStartDate,document.frmPost.txtExcuteEndDate,"实际行权结束日期必须大于等于实际行权开始日期")){
		
		return false;
	}
	
	if (document.frmPost.txtCancelStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCancelStartDate, true, "收回取消开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCancelEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCancelEndDate, true, "收回取消结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCancelStartDate,document.frmPost.txtCancelEndDate,"收回取消结束日期必须大于等于收回取消开始日期")){
		
		return false;
	}
	return true;
}

function query_submit()
{
    if (check_form()) {
          document.frmPost.action="futureRight_query_result.do" ;
          document.frmPost.submit();
    }
}

function view_detail_click(strId)
{
	self.location.href="futureRight_for_prepare.do?txtSeqNo="+strId;
}
function create_submit(){
    document.frmPost.action ="futureRight_for_create.screen";
    document.frmPost.submit();
}

</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">期权查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="" method="post" >    
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <input type="hidden" name="txtCustomerID"   value="<tbl:writeparam name="txtCustomerID" />"  >
   <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>           
          <td width="17%" class="list_bg2" align="right">创建日期</td>          
          <td width="33%" class="list_bg1" align="left">
             <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             ---
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
          </td>       
          <td width="17%" class="list_bg2" align="right">实际行权日期</td>
          <td width="33%"class="list_bg1" align="left">  
             <d:datetime name="txtExcuteStartDate" size="10" match="txtExcuteStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtExcuteStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />                     
             ---
             <d:datetime name="txtExcuteEndDate" size="10" match="txtExcuteEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtExcuteEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
          </td>            
        </tr> 
        <tr>           
          <td width="17%" class="list_bg2" align="right">收回取消日期</td>          
          <td width="83%"colspan="3" class="list_bg1" align="left">
             <d:datetime name="txtCancelStartDate" size="10" match="txtCancelStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCancelStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             ---
             <d:datetime name="txtCancelEndDate" size="10" match="txtCancelEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCancelEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
         </td>  
         </tr>
    </table>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr> 
                 <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                 <td><input name="Submit" type="button" class="button" value="查    询" onclick="javascript:query_submit()"></td>
				 <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                 <td width="20" ></td>
                 <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                 <td><input name="Submit" type="button" class="button" value="创建期权" onclick="javascript:create_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table>  
     
<rs:hasresultset>
      <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td  class="list_head" >期权号</td>
          <td  class="list_head" >创建日期</td>
          <td class="list_head" >单据号</td>
          <td  class="list_head" >金额</td> 
          <td  class="list_head" >状态</td>
          <td class="list_head" >期权锁定日期</td>
          <td  class="list_head" >描述</td>
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    FutureRightDTO futureRightDto = (FutureRightDTO)pageContext.getAttribute("oneline");
   
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
            <td><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo" />')" class="link12" ><tbl:writenumber name="oneline" property="seqNo" digit="7"/></a></td>
            <td><tbl:writedate name="oneline" property="createDate" /></td>
            <td><tbl:write name="oneline" property="referSheetID" /></td>
            <td><tbl:write name="oneline" property="value" /></td>
            <td><d:getcmnname typeName="SET_F_FUTURERIGHTSTATUS" match="oneline:status" /></td>
            <td><tbl:writedate name="oneline" property="lockToDate" /></td>
            <td><tbl:write name="oneline" property="description" /></td>
</tbl:printcsstr>                                                                    
</lgc:bloop>                 
		 <tr>
    		<td colspan="8" class="list_foot"></td>
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
</TD>
</TR>
</TABLE>
</form> 

     
      
      
      
      
      
