<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		document.frmPost.submit();
	}
}

function view_callback_click(strId)
{
	self.location.href="cp_callback_detail.do?txtCustomerProblemID="+strId;
}
function register_callback_click(strId)
{   
    
	self.location.href="create_callback_view.do?txtCustomerProblemID="+strId;
}

function checkDate(){
 
	if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	 if(!checkPlainNum(document.frmPost.txtCustomerProblemID,true,9,"报修单编号"))
		return false; 
     if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return false;         
	return true;
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">报修回访－报修单查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

 
<form name="frmPost" method="post" action="cp_query_for_callback.do" >
 <input type="hidden" name="txtQueryType" value="queryAll"> 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
          <td  class="list_bg2"><div align="right">报修单编号</div></td>      
          <td class="list_bg1">
           <input type="text" name="txtCustomerProblemID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustomerProblemID" />" >
           </td>      
           <td  class="list_bg2"><div align="right">创建时间</div></td>
           <td class="list_bg1"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
           
        </tr>
        <tr>
              <td class="list_bg2"><div align="right">客户证号</div></td>
	      <td class="list_bg1">
              <input type="text" size="25" maxlength="9" name="txtCustID" value="<tbl:writeparam name="txtCustID"/>">
             </td>
              <td class="list_bg2"><div align="right">报修类别</div></td>
	      <td class="list_bg1"> 
	     <d:selcmn name="txtProblemCategory" mapName="SET_C_CPPROBLEMCATEGORY" match="txtProblemCategory" width="25" />
	     </td>   
        </tr>
          
        <tr>
         <td  class="list_bg2"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </td>
         <td class="list_bg2"><div align="right">回访标志</div></td>
	 <td class="list_bg1">
           <d:selcmn name="txtCallBackFlag" mapName="SET_C_CPCALLBACKFLAG" match="txtCallBackFlag" width="25" />
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
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="txtStatus" value="D;T">       
    
  <rs:hasresultset>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
  <br> 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">报修单号</td>
           <td class="list_head">客户证号</td>
           <td class="list_head">所在区域</td>
           <td class="list_head">联系地址</td>
           <td class="list_head">报修类别</td>
            <td class="list_head">状态</td>
             <td class="list_head">回访标志</td>
           <td class="list_head"></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
<% 
	CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	CustomerProblemDTO dto=wrap.getCpDto();
	String flag = dto.getCallBackFlag();
	CustProblemProcessDTO cppDto=wrap.getCppDto();
	 String strAddress = Postern.getAddressById(dto.getAddressID());
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
         pageContext.setAttribute("oneline", dto);
          pageContext.setAttribute("custaddr", addrDto);
          pageContext.setAttribute("cpp", cppDto);
	if (strAddress==null) strAddress="";
%>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	    <td align="center"><tbl:writenumber name="oneline" property="Id" digit="7"/></td>
      	     <td align="center"><tbl:writenumber name="oneline" property="CustID" digit="7" hideZero="true"/></td>
      	     <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      	     <td align="center"><%=strAddress%></td>
      	     <td align="center"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_C_PROBLEMSTATUS" match="oneline:Status"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_C_CPCALLBACKFLAG" match="oneline:CallBackFlag" /></td> 
      	      <% 
      	       if(flag.equals("Y"))
      	       {
      	     %>
      	     <td><a href="javascript:view_callback_click('<tbl:write name="oneline" property="Id"/>')" class="link12" >查看回访信息</a></td>
      	     <%}
      	        else if (flag.equals("N")){
      	     %>
      	     <td><a href="javascript:register_callback_click('<tbl:write name="oneline" property="Id"/>')" class="link12" >录入回访信息</a></td>
      	     <%}
      	         
      	     %>
      	     
      	     
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 
