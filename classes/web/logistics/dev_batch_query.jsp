<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<script language=javascript>
<!--
function check_form(){
   	if(check_Blank(document.frmPost.txtBatchSerialNo, true, "设备序列号")){
		document.frmPost.txtBatchSerialNo.focus();
		return false;
	}	
    return true;
}

function query_submit()
{
  if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="dev_detail.do?txtDeviceID="+strId;
}

function view_cust_detail_click(strId){
	self.location.href="customer_view.do?txtCustomerID="+strId;
}
//-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="822" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量设备查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
Map mapDepots = Postern.getAllDepot();
%>
  
<form name="frmPost" action="dev_batch_query_result.do" method="post" >    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">  
    <tr>  
      <td class="list_bg2" colspan="4"><div align="center">设备序列号(序列号最大数1000，序列号以“,”号分割)</div></td>
    </tr> 
    <tr>
      <td colspan="4" class="list_bg1" align="center">
          <textarea name="txtBatchSerialNo" length="5" cols=100 rows=9><tbl:writeparam name="txtBatchSerialNo" /></textarea>
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
		<pri:authorized name="dev_batch_query_result.export">
				<td width="20" ></td>  
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:download(document.frmPost,'批量设备查询')" class="btn12">导出查询结果</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</pri:authorized>
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
          <td class="list_head" width="12%">序列号</td>
          <td class="list_head" width="15%">设备类型</td>
          <td class="list_head" width="20%">设备型号</td>          
          <td class="list_head" width="20%">地址</td>
          <td class="list_head"width="25%">所属区域</td> 
          <td class="list_head" width="8%">状态</td>
        </tr> 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("oneline");
	String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
	if (strClass==null) strClass="";
	String strModel = Postern.getDeviceModelDesc(dto.getDeviceModel());
	String strAddressType = dto.getAddressType();
	if (strAddressType==null) strAddressType="";
	String strAddress = null;
	
	if (strAddressType.equals("D"))  //仓库
	{
		strAddress = (String)mapDepots.get(String.valueOf(dto.getAddressID()));
	}
	else if (strAddressType.equals("T"))  //组织机构
	{
		strAddress = Postern.getOrganizationDesc(dto.getAddressID());
	}
	else if (strAddressType.equals("B"))  //用户
	{
		strAddress = Postern.getCustomerNameById(dto.getAddressID());
	}
	if (strAddress==null) strAddress="";
	
				    
%>
	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      	  <% if(dto.getDeviceID()!=0){%>
      	    <td align="center" nowrap><a href="javascript:view_detail_click('<tbl:write name="oneline" property="DeviceID"/>')" class="link12" ><tbl:write name="oneline" property="SerialNo"/></a></td>
      	  <%}else{%>
      	    <td align="center" nowrap><tbl:write name="oneline" property="SerialNo"/></td>
      	  <%}%>
      	  <td align="center" nowrap><%=strClass%></td>
      	  <td align="center" nowrap><tbl:write name="oneline" property="DeviceModel"/></td>
      	  <% if("B".equals(strAddressType)){%>
            <td align="center"><a href="javascript:view_cust_detail_click('<tbl:write name="oneline" property="addressID" />')" class="link12" ><%=strAddress%></a></td>
          	 <%
          	    String districtName=Postern.getDistrictDescByCustomerID(dto.getAddressID());
          	    if(districtName==null)districtName="";
          	%>
      	    <td align="center"><%=districtName%></td> 
      	  <%}else{%>
      	    <td align="center"><%=strAddress%></td> 
      	    <td align="center"><br></td>
      	  <%}%>                   
      	  <td align="center" nowrap><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:Status"/></td>
    	</tr>
</lgc:bloop>  
 <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>
   <tr>
      <td align="right" class="t12" colspan="12" >
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
    </td>
  </tr>
</table>
 

     
      
      
      
      
      