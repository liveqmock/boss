<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.dto.ExportCustomerDetailDTO" %>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.util.DistrictSetting"%>

<script language=javascript>
   function query_submit(){
   	 if (document.frmPost.txtCreateStartDate.value != ''){
		    if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "导入开始日期")){
			    return false;
		    }
	   }
	 
	   if (document.frmPost.txtCreateEndDate.value != ''){
		    if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "导入结束日期")){
			    return false;
		    }
	   }
	   if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){
		    return false;
	   }
	   document.frmPost.submit();
   }
  function clear_select(id,name){
	   var idobj=eval("document.frmPost."+id);
	   idobj.value="";
	   var nameobj =eval("document.frmPost."+name);
	   nameobj.value="";
  }
</script>

<table border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">整转客户查询</td>
  </tr>
</table>

<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="exportcustomer_query.do">
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
 <tr>
	 <td class="list_bg2" align="right">所属区域</td>
   <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   </td>
	 <td class="list_bg2" width=17% align="right">客户姓名</td>
   <td class="list_bg1" width=33% align="left"><input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">操作员</td>
    <td class="list_bg1" align="left">
		<input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
		<input type="text" name="txtCollectorName" readonly size="24" value="<tbl:writeparam name="txtCollectorName"/>" >
		<input name="selOperButton" type="button" class="button" value="查询" 
		onClick="javascript:query_window('操作员查询','txtCollectorID','txtCollectorName','query_operator.do')">
		<input name="selClearButton" type="button" class="button" value="清除" 
		onClick="javascript:clear_select('txtCollectorID','txtCollectorName')">
    </td>
    <td class="list_bg2" align="right">导入时间</td>
    <td class="list_bg1" align="left">
    <d:datetime name="txtCreateStartDate" size="9" myClass="text" match="txtCreateStartDate"  onclick="calendar(document.frmPost.txtCreateStartDate)" picURL="img/calendar.gif" style="cursor:hand" />
    -
    <d:datetime name="txtCreateEndDate" size="9" myClass="text" match="txtCreateEndDate"  onclick="calendar(document.frmPost.txtCreateEndDate)" picURL="img/calendar.gif" style="cursor:hand" />
		</td>
  </tr>
  <tr>
  	<td class="list_bg2" align="right">导入备注</td>
  	<td class="list_bg1" align="left" colspan="3">
  		<input name="txtComments" type="text" class="text" maxlength="50" size="30" value="<tbl:writeparam name="txtComments" />">
  	</td>
  </tr>
 </table> 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
		<td class="list_bg1">
		 <table  border="0" cellspacing="0" cellpadding="0">
			 <tr>
	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		   <td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
		   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	    </tr>
	  	</table>
	  </td>
	</tr>
 </table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br> 
<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	<td class="list_head" width="10%" >导入时间</td>
  	<td class="list_head" width="10%" >许可证号</td>
    <td class="list_head" width="10%" >客户姓名</td>
    <td class="list_head" width="10%" >客户所在区</td>
    <td class="list_head" width="10%" >详细地址</td>
    <td class="list_head" width="5%" >客户类型</td>
    <td class="list_head" width="15%" >设备号</td>
    <td class="list_head" width="10%" >发放时间</td>
    <td class="list_head" width="10%" >电话</td>
    <td class="list_head" width="10%" >身份证</td>
  </tr>
<%
  Map custTypeMap =Postern.getCommonDateKeyAndValueMap("SET_C_CUSTOMERTYPE");
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <%
     ExportCustomerDetailDTO dto = (ExportCustomerDetailDTO)pageContext.getAttribute("oneline");
     DistrictSetting dt =(DistrictSetting)Postern.getAllDistrictSettingCache().get(""+dto.getDistrinctId());
		 String distName ="";
		 if (dt !=null){
		     distName =dt.getName();
		     DistrictSetting dt_1 =(DistrictSetting)Postern.getAllDistrictSettingCache().get(""+dt.getBelongTo());
		  	 if(dt_1 !=null){
		  	    distName =dt_1.getName()+"--"+distName;
		  	 }
		 } 
		 String custType =(String)custTypeMap.get(dto.getCustomerType());
		 
   %>
   <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
     <td align="center" ><tbl:writedate name="oneline" property="dt_create"/></td>
     <td align="center" ><tbl:write name="oneline" property="catvid" /> </td>
     <td align="center" ><tbl:write name="oneline" property="name" /> </td>
     <td align="center" ><%=distName%></td>
     <td align="center" ><tbl:write name="oneline" property="detailAddress" /></td>
     <td align="center" ><%=custType%></td>
     <td align="center" ><tbl:write name="oneline" property="serialNo" /></td>
     <td align="center" ><tbl:writedate name="oneline" property="ffcreateTime" /></td>
     <td align="center" ><tbl:write name="oneline" property="tel" /></td>
     <td align="center" ><tbl:write name="oneline" property="cardID" /></td>
   </tbl:printcsstr>
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