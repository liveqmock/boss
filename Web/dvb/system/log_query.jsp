<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.SystemLogDTO"%>

<script language=javascript>
function check_form(){
            if (!check_TenDate(document.frmPost.txtStartTimeDatePart, true, "起始时间"))
            {
                
        	return false;	
            }
            if (!check_TenDate(document.frmPost.txtEndTimeDatePart, true, "中止时间"))
            {
                
        	return false;	
            }
            if(!compareDate(document.frmPost.txtStartTimeDatePart,document.frmPost.txtEndTimeDatePart,"结束日期必须大于等于开始日期")){
		
		return false;
	}   	
	 
       	
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}
function detail_query_submit(seqNo)
{
        if(seqNo!="") 
        	window.location.href="log_query_detail.do?txtSequenceNO=" + seqNo;
        else
        	alert(seqNo +"为空，无法查看详情");
}
 
 
</script>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">系统日志查询</td>
  </tr>
</table>
 
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%
  Map allModuleMap = Postern.getSystemModel();
  Iterator it = allModuleMap.values().iterator();
	Map allModuleMapCH = new HashMap();
	while(it.hasNext())
	{
		String value = (String)it.next();
		allModuleMapCH.put(value, value);
	}

	pageContext.setAttribute("allModule",allModuleMapCH);
	pageContext.setAttribute("allOperation",Postern.getOperationOfLog());
%>
<form name="frmPost" action="log_query_result.do" method="post" >    
  <table width="820"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   
     <tr>   
           <td  class="list_bg2"><div align="right">客户证号</div></td>
           <td class="list_bg1"  align="left">
            <input type="text" name="txtCustomerID" size="25"   value="<tbl:writeparam name="txtCustomerID" />"  >
            </td> 
          <td class="list_bg2"><div align="right">客户姓名</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtCustomerName" size="25" value="<tbl:writeparam name="txtCustomerName"/>" >
     	</td>
           
          
      </tr>
     
       
      
    <tr>
      <td class="list_bg2"><div align="right">操作员LoginID</div></td>
      <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID" size="25" value="<tbl:writeparam name="txtLoginID"/>" >
      </td>
       <td class="list_bg2"><div align="right">操作类型</div></td>
         <td class="list_bg1" align="left">
	<tbl:select name="txtOperation" set="allOperation" match="txtOperation" width="23" />
      </td>	
      
    </tr>
     <tr>   
         
            <td class="list_bg2"><div align="right">模块名</div></td>
           <td class="list_bg1" colspan="3">
	  <tbl:select name="txtModuleName" set="allModule" match="txtModuleName" width="23" />
     	  </td>
          
            
    </tr> 
   
       <tr>  
      <td class="list_bg2"><div align="right">起始时间</div></td>
      <td class="list_bg1" align="left">
      <d:datetime name="txtStartTime" size="16" match="txtStartTime" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />       
      </td>
      <td class="list_bg2"><div align="right">结束时间</div></td>
      <td class="list_bg1" align="left">
      <d:datetime name="txtEndTime" size="16" match="txtEndTime" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTimeDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />       
      </td>
    </tr>
      <tr>  
        <td class="list_bg2"><div align="right">操作机器</div></td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtMachineName" size="25" value="<tbl:writeparam name="txtMachineName"/>" >
     	</td>
        <td class="list_bg2"><div align="right">日志重要性级别</div></td>
        <td class="list_bg1" align="left">
	<d:selcmn name="txtLogClass" mapName="SET_S_LOGCLASS" match="txtLogClass" width="23" /> 
        </td>
    </tr>
    
    </tr>
    </table>
    <table width="820"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
         
   
 <table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
<rs:hasresultset>
 <table width="820"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr class="list_head">
         <td class="list_head"  width="65" align="center">流水号</td>
         <td class="list_head"  width="105" align="center">操作员LoginID</td> 
         <td class="list_head"  width="80" align="center">操作机构</td> 
         <td class="list_head" width="65" align="center">模块名</td>
         <td class="list_head" width="65" align="center">客户证号</td>         
         <td class="list_head" width="85"><div align="center">时间</div></td>
         <td class="list_head" width="100" nowrap><div align="center">操作</div></td>
         <td class="list_head" width="280"><div align="center">描述</div></td>
              
     </tr> 
        
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <%
      SystemLogDTO dto = (SystemLogDTO)pageContext.getAttribute("oneline");
      String orgName =Postern.getOrgNameByOrgID(dto.getOrgID());
      if (orgName ==null) orgName ="";
   %>
   
   <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      		<td align="center" class="t12" nowrap><a href="javascript:detail_query_submit('<tbl:write name="oneline" property="SequenceNo" />');"><tbl:writenumber name="oneline" property="SequenceNo" digit="9"/></a></td>
      		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="LoginID"/></td>
      	  <td align="center" class="t12"><%=orgName%></td>
      		<td align="center" class="t12" nowrap><tbl:write name="oneline" property="moduleName"/></td>
      	  <td align="center" class="t12" nowrap><tbl:writenumber name="oneline" property="CustomerID" hideZero="true" digit="7"/></td>
      		<td align="center" class="t12" nowrap>
      		  <tbl:writedate name="oneline" property="CreateDateTime"/><br><tbl:writedate name="oneline" property="CreateDateTime" onlyTime="true"/>
      		</td>
      		<td align="center" class="t12"><tbl:write name="oneline" property="Operation"/></td>
      		<td align="center" class="t12"><tbl:write name="oneline" property="LogDesc"/></td>

    </tr>
</lgc:bloop> 
   
     <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
            <tr>
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
 

      
