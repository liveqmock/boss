<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>

   

<Script language=JavaScript>

function check_form(){
       	
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}
function view_detail_click(strId)
{
	self.location.href="devtransHis_detail.do?txtBatchID="+strId;
}
</Script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备流转历史查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
   <form name="frmPost" method="post" action="devtransHis_query_result.do" >
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
      <tr>
        <td  class="list_bg2"><div align="right">流转ID</div></td>
        <td class="list_bg1" ><font size="2"> 
        <input type="text" size="25" maxlength="10" name="txtBatchID" value="<tbl:writeparam name="txtBatchID"/>">
        </font>
         </td>
          <td  class="list_bg2"><div align="right">批号</div></td>
        <td class="list_bg1" ><font size="2"> 
        <input type="text" size="25" maxlength="50" name="txtBatchNo" value="<tbl:writeparam name="txtBatchNo"/>">
        </font>
         </td>
      </tr> 
       <tr> 
        <td  class="list_bg2"><div align="right">设备ID</div></td>
        <td class="list_bg1" ><font size="2"> 
        <input type="text" size="25" maxlength="10" name="txtDeviceID" value="<tbl:writeparam name="txtDeviceID"/>">
        </font>
         </td> 
       <td class="list_bg2" align="right">序列号</td>
       <td class="list_bg1" >
          <input type="text" name="txtSerialNoBegin" maxlength="25" size="25" value="<tbl:writeparam name="txtSerialNoBegin" />" >
         
      </td>
     <tr> 
        <td  class="list_bg2"><div align="right">设备流转原因</div></td>
        <td class="list_bg1" colspan="3"><font size="2"> 
        <d:selcmn name="txtStatusReason" mapName="SET_D_TRANSFERREASON" match="txtStatusReason" width="23" /> 
        </font>
         </td> 
    </tr> 
        <tr> 
          <td  class="list_bg2"><div align="right">发生日期</div></td>
          <td class="list_bg1"><font size="2"> 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeBegin)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateTimeBegin" value="<tbl:writeparam name="txtCreateTimeBegin"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeBegin,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             -- 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeEnd)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateTimeEnd" value="<tbl:writeparam name="txtCreateTimeEnd" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          	</font> </td>
           <td  class="list_bg2"><div align="right">流转类型</div></td>
           <td class="list_bg1"><font size="2"> 
             <d:selcmn name="txtAction" mapName="SET_D_DTACTION" match="txtAction" width="23" />
           
        </tr> 
       
        <tr>        
             <td  class="list_bg2"><div align="right">设备流出地类型</div></td>
             <td  class="list_bg1"><font size="2">
                <d:selcmn name="txtFromType" mapName="SET_D_DEVICEADDRESSTYPE" match="txtFromType" width="23" /> 
             <td  class="list_bg2"><div align="right">设备流入地类型</div></td>
             <td class="list_bg1"><font size="2">
                <d:selcmn name="txtToType" mapName="SET_D_DEVICEADDRESSTYPE" match="txtToType" width="23" /> 
            
       </tr>        
     
     </table>
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>   
            <input type="hidden" name="txtFrom" size="20" value="1">
            <input type="hidden" name="txtTo" size="20" value="10">
            
            
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
  
   <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" ><div align="center">流转ID</div></td>
          <td class="list_head"><div align="center">批号</div></td>
          <td  class="list_head" ><div align="center">流转类型</div></td>
          <td  class="list_head" ><div align="center">流转原因</div></td>
          <td  class="list_head" ><div align="center">设备数目</div></td>
          <td  class="list_head" ><div align="center">发生时间</div></td>
        </tr>

  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="BatchID"/>')" class="link12" ><tbl:write name="oneline" property="BatchID"/></a></td>
      <td align="center"><tbl:write name="oneline" property="BatchNo"/></td>
      <td align="center"><d:getcmnname typeName="SET_D_DTACTION" match="oneline:Action" /></td>
      <td align="center"><d:getcmnname typeName="SET_D_TRANSFERREASON" match="oneline:StatusReason" /></td>
      <td align="center"><tbl:write name="oneline" property="DeviceNumber"/></td>
      <td align="center"><tbl:writedate name="oneline" property="CreateTime" includeTime="true" /></td>      
     </tr>
    
</lgc:bloop>    
<tr>
    <td colspan="9" class="list_foot"></td>
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
	
</form> 
    </td>
  </tr>
  
  
</table>   
 