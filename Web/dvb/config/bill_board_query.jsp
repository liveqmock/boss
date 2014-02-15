<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
	    
		document.frmPost.submit();
	}
}
function checkDate(){


	if (document.frmPost.txtPublishStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPublishStartDate, true, "发布日期开始时间")){
			return false;
		}
	}
	 
	if (document.frmPost.txtPublishEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPublishEndDate, true, "发布日期结束时间")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtPublishStartDate,document.frmPost.txtPublishEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	return true;
}
	 
	
function view_detail_click(strId)
{
	self.location.href="bill_board_detail_config.do?txtSeqNo="+strId;
}
function create_board_submit()
{
	self.location.href="bill_board_create.screen";
}
 
 
</script>
 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">公告信息查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="bill_board_query_config.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr>
          <td class="list_bg2"><div align="right">公告信息名称 </div></td>
          <td class="list_bg1" colspan="3"> 
            <input type="text" name="txtName" maxlength="50" size="25" value="<tbl:writeparam name="txtName" />" >
           </td>      
         </tr>
          <tr> 
           <td  class="list_bg2"><div align="right">发布人</div></td>
           <td class="list_bg1">
           <input type="text" name="txtPublishPerson" maxlength="100" size="25" value="<tbl:writeparam name="txtPublishPerson" />" >
         </td>
          <td  class="list_bg2"><div align="right">重要度</div></td>
           <td class="list_bg1">
             <o:selcmn name="txtGrade" mapName="SET_G_BILLBOARDGRADE" match="txtGrade" width="25" /> 
            
         </td>
     
      </tr>
      <tr>
          <td  class="list_bg2"><div align="right">发布日期</div></td>
          <td class="list_bg1"> 
            <o:datetime name="txtPublishStartDate" size="9" match="txtPublishStartDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPublishStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <o:datetime name="txtPublishEndDate" size="9" match="txtPublishEndDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtPublishEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
          </td>      
           <td  class="list_bg2"><div align="right">状态</div></td>
           <td class="list_bg1">
             <o:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="25" /> 
            
         </td>      
      </tr>
      </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  <td width="20" ></td>
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:create_board_submit()" class="btn12">新&nbsp;增</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
                         
        
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
    
      
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <br>
   <rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head">流水号</td>
          <td class="list_head">公告信息名称</td>
          <td class="list_head">发布人</td>
          <td class="list_head">发布原因</td>
          <td class="list_head" >重要度</td>
          <td class="list_head" >发布日期</td>
          <td class="list_head" >开始日期</td>
          <td class="list_head" >结束日期</td>
          <td class="list_head" >状态</td>
          
           
        </tr>

       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		   <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12" ><tbl:write  name="oneline" property="seqNo"/></a></td>
		   <td align="center"><tbl:write name="oneline" property="name"/></td>
		   <td align="center"><tbl:write name="oneline" property="publishPerson"/></td>     
		   <td align="center"><tbl:write name="oneline" property="publishReason"/></td>
		   <td align="center"><o:getcmnname typeName="SET_G_BILLBOARDGRADE" match="oneline:grade" /></td>
		   <td align="center"><tbl:writedate name="oneline" property="publishDate"/></td>
		   <td align="center"><tbl:writedate name="oneline" property="dateFrom"/></td>
		   <td align="center"><tbl:writedate name="oneline" property="dateTo"/></td>
		   <td align="center"><o:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status" /></td>
      			 
    		</tr>
 
</lgc:bloop>
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
   
  
   <tr class="trline" >
         <td align="right" class="t12" colspan="9" >
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
  </td>
  </tr>
  </form>
  </table>
