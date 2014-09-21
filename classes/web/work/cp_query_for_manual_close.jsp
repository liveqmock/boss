<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>

<script language=javascript>
function query_submit()
{	if(checkDate()){
		
		document.frmPost.submit();
	}
}
function check_form()
{
   if (check_Blank(document.frmPost.txtWorkResultReason, true, "结束原因"))
	return false;
	return true;
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
	return true;
}
function view_detail_click(strId)
{
	self.location.href="cp_query_detail.do?txtCustomerProblemID="+strId;
}
 
 
function checkSelected(){
	var returnValue=false;
	if(document.frmPost.ListID==null){
		alert("没有选中需要结束的报修单");
		return false;
	}else{
		if (document.frmPost.ListID.length!=null){
			for (var i=0;i<document.frmPost.ListID.length;i++)
			{
				if (document.frmPost.ListID[i].checked)
				{
					returnValue= true;
					break;
				}
			}
		}else{
			if(document.frmPost.ListID.checked){
				returnValue= true;
			}
		}
	}
	if(!returnValue){
		alert("请指需要结束报修单");
	}
    return returnValue;
}
function close_submit()
{   
    if(checkSelected()&& check_form()){
	 document.frmPost.action = "cp_manual_close.do"
	 document.frmPost.submit();
	}
}
function ChangeSubcompany()
{

    document.FrameUS.submit_update_select('subctoss', document.frmPost.txtCustOrgID.value, 'txtCustStreetStationID', '');
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

 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">报修单查询--手工结束</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

 
<form name="frmPost" method="post" action="cp_query_for_manual_close.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
          <td class="list_bg2" width="17%"><div align="right">报修单编号</div></td>  
          <td class="list_bg1" width="33%">
          <input type="text" name="txtCustomerProblemID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustomerProblemID" />" >
         </td>
          
          <td  class="list_bg2" width="17%"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </td>
        </tr>
         <tr>         
           <td  class="list_bg2"><div align="right">报修类别</div></td> 
           <td class="list_bg1"> 
            <d:selcmn name="txtProblemCategory" mapName="SET_C_CPPROBLEMCATEGORY" match="txtProblemCategory" width="25"/> 
             </td>
           <td  class="list_bg2"><div align="right">收费类别</div></td>
	  <td class="list_bg1"> 
	 <d:selcmn name="txtFeeClass" mapName="SET_C_CPFEECLASS" match="txtFeeClass" width="25" />
	 </td>  
        </tr>
        <tr>
         <td  class="list_bg2"><div align="right">创建时间</div></td> 
           <td class="list_bg1" colspan="3"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
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
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="func_flag" value="6002" >
      <input type="hidden" name="txtStatus" value="W">
      <input type="hidden" name="txtQueryType" value="queryPart">
  <%int strctr=0;%>       
  <rs:hasresultset>
  
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		 <tr class="list_head">
		      <td class="list_head" > <input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div></td>
			<td class="list_head" nowrap>报修单号</td>
			<td class="list_head" nowrap>所在区域</td>
			<td class="list_head" nowrap>报修类别</td>
			 <td class="list_head" nowrap>收费类别</td>
			<td class="list_head" nowrap>地址</td>
			<td class="list_head" nowrap>问题描述</td>      
	       </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<%
	  //CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	  CustomerProblemDTO dto=(CustomerProblemDTO)pageContext.getAttribute("oneline");;
	   
	  //CustProblemProcessDTO cppDto=wrap.getCppDto();
	  String strAddress = Postern.getAddressById(dto.getAddressID());
	  AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
          pageContext.setAttribute("oneline", dto);
         // pageContext.setAttribute("cpp", cppDto);
          pageContext.setAttribute("custaddr", addrDto);
	if (strAddress==null) strAddress="";
	int operOrgID=CurrentLogonOperator.getOperator(request).getOrgID();
	int nextorgid=Postern.getNextOrgIDByCPID(dto.getId());
	++strctr;
				    
	%>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		
		        <td align="center">
		        <input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="Id"/>">
		        </td>
		        <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      			<td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      			<td align="center"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/></td>
      			<td align="center"><d:getcmnname typeName="SET_C_CPFEECLASS" match="oneline:FeeClass"/></td>
      			<td align="center"><%=strAddress%></td> 
      			<td align="center"><tbl:write name="oneline" property="ProblemDesc"/></td>      			
      		        
      			
      		</tr>
	</lgc:bloop>  
	 <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="7" >
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
  <%if(strctr>0){%>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  
		  <tr>
			<td class="list_bg2" width="17%"><div align="right">结束原因</div></td>			
			<td class="list_bg1"><d:selcmn name="txtWorkResultReason" mapName="SET_W_JOBRESULTREASON" match="txtWorkResultReason" width="23" />
			</td>			 
		  </tr>
		 <tr>
	  		<td class="list_bg2"><div align="right">备&nbsp&nbsp注</div></td>
	  		<td class="list_bg1"><textarea name="txtMemo" cols="80" rows="3" ><tbl:writeparam name="txtMemo" /></textarea></td>	   
		</tr>
	  
  </table>
   
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 <tbl:generatetoken />
 
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	  <tr align="center">
	  <td ><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="手工结束" onclick="javascript:close_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>
 <%}%>
  
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 
