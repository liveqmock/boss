<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<script language=javascript>
function query_submit()
{
	if(checkDate()){
	    
		document.frmPost.submit();
	}
}
function checkDate(){
	 
	 
	
	if (document.frmPost.txtPreferedStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtPreferedEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedEndDate, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtPreferedStartDate,document.frmPost.txtPreferedEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	
	
 	 if(!checkPlainNum(document.frmPost.txtJobCardID,true,9,"工单编号"))
		return false; 
	    
	
	return true;
}
	
function view_detail_click(strId)
{   
    
	self.location.href="catv_job_card_view_for_register.do?txtJobCardID="+strId+"&txtStatus="+"B";
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
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">录入施工反馈信息--工单查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="catv_jobcard_query_result_for_register.do" >
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>
           <td  class="list_bg2"><div align="right">预约上门时间</div></td>
           <td class="list_bg1" valign="middle"> 
            <o:datetime name="txtPreferedStartDate" size="10" match="txtPreferedStartDate" onclick="calendar(document.frmPost.txtPreferedStartDate)" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <o:datetime name="txtPreferedEndDate" size="10" match="txtPreferedEndDate" onclick="calendar(document.frmPost.txtPreferedEndDate)" picURL="img/calendar.gif" style="cursor:hand" />
             </td> 
              <td class="list_bg2"><div align="right">工单子类型 </div></td>
             <td class="list_bg1"  valign="middle"> 
            <o:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" width="25" />
           </td>                 
       </tr>  	
        
        <tr>
            <td  class="list_bg2"><div align="right">工单编号</div></td>
            <td class="list_bg1">
           <input type="text" name="txtJobCardID" maxlength="9" size="25" value="<tbl:writeparam name="txtJobCardID" />" >
         </td>
         <td  class="list_bg2"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
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
	  </table></td>
	</tr>
</table>      
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtStatus"  value="B">
      <input type="hidden" name="txtTo"  value="10">
      <input type="hidden" name="txtType"  value="C">
      <input type="hidden" name="actiontype" value="register">
 <rs:hasresultset>    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" width="9%" nowrap>工单号</td>
          <td class="list_head" nowrap>工单子类型</td>

          <td class="list_head" nowrap>联系人</td>
          <td class="list_head" nowrap>联系电话</td>
          <td class="list_head" nowrap>联系地址</td>
          <td class="list_head" nowrap>所在区域</td>
          <td class="list_head" nowrap>工作内容</td>
          <td class="list_head" nowrap>状态</td>
          <td class="list_head" nowrap></td>
        </tr>

         
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
	JobCardDTO dto=wrap.getJobCardDto();
	String type=null;
	 type = Postern.getCSITypeByID(dto.getReferSheetId());
        if (type == null) type = "";
	pageContext.setAttribute("oneline", wrap.getJobCardDto());
        String strAddress = Postern.getAddressById(dto.getAddressId());
         AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressId());
        pageContext.setAttribute("custaddr", addrDto);
	if (strAddress == null) strAddress="";
	
 	 
 	  
	 
%>
		 <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		  <td align="center"><tbl:writenumber name="oneline" property="JobCardId" digit="7"/></td>
		   <td align="center" ><o:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:subType" /></td>
		   <!--     <td align="center"><o:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="<%=type%>" /></td>  -->
      			 
      			<td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
      			<td align="center"><tbl:write name="oneline" property="ContactPhone"/></td>      
      			<td align="center"><%=strAddress%></td>
      			 <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      			 <td align="center"><tbl:write name="oneline" property="description"/></td>
      			<td align="center"><o:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:Status" /></td>
      			<td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="JobCardId"/>')" class="link12" >录入施工信息</a></td>
    		 </tbl:printcsstr>
    		 
  
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