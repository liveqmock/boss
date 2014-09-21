<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.dto.CsiProcessLogDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function check_form(){
	if(!checkPlainNum(document.frmPost.txtID,true,9,"受理单编号"))
		return false;
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
       if (document.frmPost.txtUserID.value != '') {
          if(!checkPlainNum(document.frmPost.txtUserID,true,9,"客户证号"))
			return false;
       
       }
	
       if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){

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

	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
       self.location.href="customer_view.do?txtCustomerID="+strId;
}
function view_cancledetail_click(strId) {
	self.location.href="cancelled_customer_view.do?txtCustomerID="+strId;
}

function ChangeCsiReason() {
   document.FrameUS.submit_update_select('csireasonid', document.frmPost.txtType.value, 'txtCsiReason', '');
}

</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">受理单查询</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="service_interaction_query_result.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <input type="hidden" name="txtActionType"  value="select">
   <%
      String txtCustomerID =request.getParameter("txtCustomerID");
   %>
   <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="12%" align="center" class="list_bg1">基本信息</td>
          <td width="11%" class="list_bg2" align="right">受理单编号</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtID" maxlength="9" size="25"  value="<tbl:writeparam name="txtID" />" class="text">
          </font> </td>                      
          <td width="11%" class="list_bg2" align="right">受理单类型</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <d:selcmn name="txtType" mapName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="txtType" width="23" onchange="ChangeCsiReason();" />
          </font> </td>
        </tr> 
        <tr>
        	 <td align="center" class="list_bg1">&nbsp;</td>
        	 <td class="list_bg2" align="right">受理原因</td>
           <td class="list_bg1" align="left"><font size="2">
              <tbl:selectCommonMethodByPostern name="txtCsiReason" posternMethod="getCsiReasonByCsitype" parentMatch="txtType" match="txtCsiReason"  width="23" />
           </font></td>
           <td class="list_bg2" align="right">受理单状态</td>
           <td class="list_bg1" align="left"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="txtStatus" width="23" />
           </font></td>   
        </tr>
        <tr>  
           <td  align="center" class="list_bg1">&nbsp;</td> 
           <td  class="list_bg2" align="right">付费标志</td>
           <td  class="list_bg1" align="left"><font size="2">
             <d:selcmn name="txtPaymentStatus" mapName="SET_V_CUSTSERVICEINTERACTIONPAYSTATUS" match="txtPaymentStatus" width="23" />  
           </font></td>  
           <td  class="list_bg2" align="right">回访标志</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <d:selcmn name="txtCallBackFlag" mapName="SET_G_YESNOFLAG" match="txtCallBackFlag" width="23" />               
           </font></td>
        </tr>
        <tr>
            <td align="center" class="list_bg1">&nbsp;</td>
            <td class="list_bg2" align="right">客户证号</div></td>
            <td class="list_bg1" align="left"><font size="2">
              <input type="text" name="txtUserID" value="<tbl:writeparam name="txtUserID"/>" size="25" class="text" maxlength="9" />
            </font></td>
            <td class="list_bg2" align="right">客户姓名</td>
            <td class="list_bg1" align="left"><font size="2">
              <input type="text" name="txtCustomerName" maxlength="50" size="25"  value="<tbl:writeparam name="txtCustomerName" />" class="text" >
            </font> </td>
        </tr>
        <tr>
          <td align="center" class="list_bg1">&nbsp;</td>
          <td class="list_bg2" align="right">联系电话</td>
          <td class="list_bg1" align="left"><font size="2">
            <input type="text" name="txtTelephone" maxlength="50" size="25"  value="<tbl:writeparam name="txtTelephone" />" class="text" >
          </font> </td>
          <td class="list_bg2" align="right">安装地址</td>
          <td class="list_bg1" align="left">
            <input type="text" name="txtDetailAddress" maxlength="100" size="25"  value="<tbl:writeparam name="txtDetailAddress" />" class="text" >
          </font> </td>
        </tr>
        <tr>
          <td align="center" class="list_bg1">&nbsp;</td>
          <td class="list_bg2" align="right">所在区</td>
          <td class="list_bg1"  align="left">
             <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	           <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
             <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
          </td>
          <td class="list_bg2" align="right">创建组织</td>
   		    <td class="list_bg1">
	    	     <input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			       <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			       <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S,D','txtOrgID','txtOrgDesc')">
	        </td>			 
        </tr> 
        <tr>
           <td align="center" class="list_bg1">&nbsp;</td>
           <td class="list_bg2" align="right">状态变化原因</td>
           <td class="list_bg1" align="left" colspan="3"><font size="2">        
            <d:selcmn name="txtCancleReason" mapName="SET_V_CUSTSERVICEINTERACTIONSTATUSREASON" match="txtCancleReason" width="23" />               
           </font></td>
        </tr>  
        <tr>
           <td  align="center" class="list_bg1">时间信息</td>
           <td  class="list_bg2" align="right">创建时间</td>
           <td  class="list_bg1" align="left"><font size="2">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             - 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            
            </font></td>
            
           <td  class="list_bg2" align="right">预约上门时间</td>
           <td  class="list_bg1" align="left"><font size="2"> 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtPreferedStartDate" value="<tbl:writeparam name="txtPreferedStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             - 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtPreferedEndDate" value="<tbl:writeparam name="txtPreferedEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            </font></td>            
           
        </tr>       
        <tr> 
           <td  align="center" class="list_bg1">操作员信息</td>          
           <td  class="list_bg2" align="right">操作员姓名</td>
           <td  class="list_bg1" align="left"><font size="2"> 
            <input type="text" name="txtCreateOperatorName" maxlength="10" size="25"  value="<tbl:writeparam name="txtCreateOperatorName" />" class="text" >
           </font></td>          
           <td  class="list_bg2" colspan="2">
             <table  border="0" cellspacing="0" cellpadding="0" align="center">
             <tr>
               <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
               <td align="center"><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="查 询"></td>
               <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
              </tr>
             </table>
           </td>   
        </tr>  
     </table>
     <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	     <td><img src="img/mao.gif" width="1" height="1"></td>
	   </tr>
    </table>
    <rs:hasresultset>
    <br>
    <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">                
         <td class="list_head">受理单编号</td>
         <td class="list_head">类型</td>
         <td class="list_head">客户证号</td>
         <td class="list_head">用户姓名</td>
         <td class="list_head">创建时间</td>
         <td class="list_head">受理单状态</td>
         <td class="list_head">操作员姓名</td>
         <td class="list_head">协议编号</td>
       </tr>
        
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%
	      CustServiceInteractionDTO csidto = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");
        CsiProcessLogDTO logdto =Postern.getCsiProcessLogDTO(csidto.getId());
        
	      String status = "";
	      String strName = "";
	      String conNO=Postern.getNewCustomerConNOByCsiID(csidto.getId());
	      if(conNO==null||"null".equals(conNO)) conNO="";
	      boolean isSubCustomer=false;
        String oprName =Postern.getOperatorNameByID(logdto.getOperatorID());
        if (oprName == null) oprName = "";
         
        if (csidto.getCustomerID()!=0) {
            CustomerDTO custDto = Postern.getCustomerByID(csidto.getCustomerID());
	          strName = custDto.getName();    
	          status  = custDto.getStatus();  
	    
	          if(custDto.getCustomerStyle()!=null&&custDto.getCustomerStyle().equals("G")&&custDto.getGroupCustID()!=0)
	    	       isSubCustomer=true;
	      }
	      
	      if (strName==null || strName.equals("")) strName = Postern.getNewCustomerNameByCsiID(csidto.getId());    
        if (strName==null) strName="";

	      String strType = csidto.getType();
	      if (strType==null) strType="";
	
	      String strUrl="service_interaction_view.do?txtID="+csidto.getId();
	      if (status ==null) status =""; 
    %>				    	                 
     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      <td align="center" ><a href="<%=strUrl%>" class="link12"><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
      <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:type" /></td>
      <% if (status.equalsIgnoreCase("c")||isSubCustomer) {%>
       <td align="center" ><tbl:writenumber name="oneline" property="customerID" digit="8" hideZero="true"/></td>
      <% } else {%>
       <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12"><tbl:writenumber name="oneline" property="customerID" digit="8" hideZero="true" /></a></td>
      <% }%>
      <td align="center" ><%=strName%> </td>
      <td align="center" ><tbl:writedate name="oneline" property="CreateTime" includeTime="true" /></td>
      <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="oneline:status" /></td>
      <td align="center" ><%=oprName%></td>
      <td align="center" ><%=conNO%></td>
     </tbl:printcsstr>
   </lgc:bloop>
    <tr>
      <td colspan="8" class="list_foot"></td>
    </tr>
    <tr class="trline" >
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
   </rs:hasresultset>      
</form>   