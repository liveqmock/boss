<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%
String systemSettingPrint = Postern.getSystemsettingValueByName("SET_W_CATV_JOBCARD_PRINT");
%>
<script language=javascript>

function check_form(){
 
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"结束日期必须大于等于开始日期")){
		
		return false;
	}
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
	if(!checkPlainNum(document.frmPost.txtJobCardID,true,9,"安装单编号"))
		return false;         
     if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"客户证号"))
		return false;               
	return true;
}

function query_submit()
{
        if (check_form()) {
        	document.frmPost.actiontype.value="query";
        	document.frmPost.submit();
        }
}
function checkSelected(){
	var returnValue=false;
	if(document.frmPost.txtID==null){
		alert("没有需要打印的工单");
		return false;
	}else{
		if (document.frmPost.txtID.length!=null){
			for (var i=0;i<document.frmPost.txtID.length;i++)
			{
				if (document.frmPost.txtID[i].checked)
				{
					returnValue= true;
					if(document.frmPost.txtJobCardIDList.value == "")
						document.frmPost.txtJobCardIDList.value = document.frmPost.txtJobCardId[i].value;
					else
					  document.frmPost.txtJobCardIDList.value = document.frmPost.txtJobCardIDList.value+";"+document.frmPost.txtJobCardId[i].value;
					
				}
			}
		}else{
			if(document.frmPost.txtID.checked){
				document.frmPost.txtJobCardIDList.value=document.frmPost.txtJobCardId.value;
				returnValue= true;
			}
		}
	}
	if(!returnValue){
		alert("请指定打印的工单");
	}
    return returnValue;
}
function jobcard_print_submit(){
document.frmPost.txtJobCardIDList.value = "";
    if(checkSelected()){
    	
    	document.frmPost.actiontype.value="print";
		document.frmPost.target="_blank";
		var setPrint = "";
		var module = "<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="_" />";
		//vod现在要求套打,若不套打 直接将下面两行注释即可
		if(module=="vod_")
			setPrint="setprint_";
		document.frmPost.action=setPrint+module+"in_jobcard_batch_print_query.do";	

		if(module=="gehua_" && "moni"=="<%=systemSettingPrint%>")
		{
			document.frmPost.action="gehua_in_jobcard_batch_print_for_catv.do";	
			document.frmPost.txtTo.value = 500;
		}
		document.frmPost.submit();
		document.all.actiontype.value="query";
		document.frmPost.action="catv_job_card_query_for_print.do";
		document.frmPost.target="_self";
    }
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
    <td class="title">工单打印</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="catv_job_card_query_for_print.do" method="post" >    
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
		<td class="list_bg2" align="right">工单编号</td>
		<td class="list_bg1" align="left">
			 <input name="txtJobCardID" type="text" class="text" maxlength="20" size="25" value="<tbl:writeparam name="txtJobCardID" />">
		</td>
		<td class="list_bg2" align="right">客户号</td>       
		<td class="list_bg1" align="left">
       <input type="text" name="txtCustomerID" maxlength="10" size="25" value="<tbl:writeparam name="txtCustomerID" />" >
		</td>   
  </tr>
  <tr>
     	<td class="list_bg2" align="right">状态</td>
     	<td class="list_bg1" align="left">
			    <d:selcmn name="txtStatus" mapName="SET_W_JOBCARDSTATUS" match="txtStatus" width="23" /> 
			</td>     
      <td class="list_bg2" align="right">用户类型</td>
      <td class="list_bg1" align="left">
			    <d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" />  
      </td>    
  </tr>
  <tr>
      <td  class="list_bg2" align="right">所在区域</td> 
      <td class="list_bg1"  align="left">
          <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    	  <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
          <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
      </td>
      <td class="list_bg2"  align="right">工单子类型</td>
	  	<td class="list_bg1"  align="left"> 
			   <d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" match="txtSubType" width="23" />
		  </td>      
   </tr>
   <tr>
		  <td class="list_bg2" align="right">创建时间</td> 
		  <td class="list_bg1" align="left">  
			   <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateStartDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
			   <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateEndDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" />           
		  </td> 
	   	<td  class="list_bg2" align="right">预约上门时间</td>
		  <td class="list_bg1" align="left"> 
         <d:datetime name="txtPreferedStartDate" size="10" match="txtPreferedStartDate" onclick="calendar(document.frmPost.txtPreferedStartDate)" picURL="img/calendar.gif" style="cursor:hand" />            
          - 
         <d:datetime name="txtPreferedEndDate" size="10" match="txtPreferedEndDate" onclick="calendar(document.frmPost.txtPreferedEndDate)" picURL="img/calendar.gif" style="cursor:hand" />
		   </td>            
		</tr>
    <%
        Map orderStyle =new HashMap();
        orderStyle.put("A","按 地址排序");
        pageContext.setAttribute("orderStyle",orderStyle);
        String txtorderStyle =(request.getParameter("txtorderStyle")==null) ? "" :request.getParameter("txtorderStyle");
     %>
    <tr>
   	   <td class="list_bg2" align="right">排序方式</td>
	     <td class="list_bg1" align="left" colspan="3">
	   	   <tbl:select name="txtorderStyle" set="orderStyle" match="<%=txtorderStyle%>" width="20" />
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
	  <input type="hidden" name="txtFrom" size="22" value="1">
          <input type="hidden" name="txtTo" size="22" value="50">
          <input type="hidden" name="txtType"  value="C">
          <input type="hidden" name="actiontype" value="">
          <input type="hidden" name="txtPreferedDate"  value="<tbl:write name="oneline" property="PreferedDate" />">
          
          <input type="hidden" name="txtJobCardIDList" value="">
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 <rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'txtID', this.checked)"></td>
    <td class="list_head" nowrap>工单编号</td>
    <td class="list_head" nowrap>工单子类型</td>
    <td class="list_head" nowrap>联系人</td>
    <td class="list_head" nowrap>联系电话</td>
    <td class="list_head" nowrap>联系地址</td>
    <td class="list_head" nowrap>所属分区</td>
    <td class="list_head" width="18%" nowrap>工作内容</td>
    <td class="list_head" nowrap>状态</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    JobCardDTO jobCardDTO = (JobCardDTO) pageContext.getAttribute("oneline");
					int addressID = jobCardDTO.getAddressId();
					String subtype = jobCardDTO.getSubType();
					AddressDTO addDTO = Postern.getAddressDtoByID(addressID);
					int districtID = addDTO.getDistrictID();
					String detailAddress = WebUtil.NullToString(addDTO.getDetailAddress());
					String districtDesc = Postern.getDistrictDesc(districtID);
					
	 
%>
 

 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><input type="checkbox" name="txtID" value="<tbl:write name="oneline" property="ReferSheetId"/>"></td>
    <td align="center"><tbl:writenumber name="oneline" property="JobCardId" digit="7" hideZero="true"/>
    <input type="hidden" name="txtJobCardId" value="<tbl:write name="oneline" property="JobCardId"/>">
    </td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="<%=subtype%>" /></td>
    <td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
    <td align="center"><tbl:write name="oneline" property="ContactPhone"/></td>
    <td align="center"><%=detailAddress%></td>
    <td align="center"><%=districtDesc%></td>
    <td align="center"><tbl:write name="oneline" property="description"/></td>
    <td align="center"><d:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:status" /></td>
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
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td ><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="打  印" onclick="javascript:jobcard_print_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table>    
       </rs:hasresultset>         
    
   </form>               
</TD>
</TR>
</TABLE>