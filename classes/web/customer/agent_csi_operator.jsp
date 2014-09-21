<%@ page import="com.dtv.oss.dto.CsiProcessLogDTO,
                 com.dtv.oss.dto.AddressDTO,
                 com.dtv.oss.util.Postern,
                 com.dtv.oss.util.Organization,
                 com.dtv.oss.web.util.WebKeys,
                 com.dtv.oss.web.util.WebCurrentOperatorData,
                 com.dtv.oss.dto.OperatorDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%
    String linkURL = null;

    WebCurrentOperatorData wrapOper = (WebCurrentOperatorData)session.getAttribute(WebKeys.OPERATOR_SESSION_NAME);
    OperatorDTO dtoOper = (OperatorDTO)wrapOper.getCurrentOperator();
    Organization org =new Organization();    //Postern.getOrganizationByID(dtoOper.getDepartmentID());

    boolean isAgent = false;
    if (org != null) {
        if (CommonKeys.ORGANIZATIONORGTYPE_PARTNER.equals(org.getOrgType()))
            isAgent = true;
    }

%>
 <table  border="0" align="center" cellpadding="0" cellspacing="10">
   <tr>
      <td><img src="img/list_tit.gif" width="19" height="19"></td>
      <td class="title">代理商预约单查询</td>
   </tr>
 </table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="agent_csi_operator.do" >
   <input type="hidden" name="txtFrom" size="20" value="1">
   <input type="hidden" name="txtTo" size="20" value="10">
   <input type="hidden" name="OpenFlag" value="<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">     
      <tr>
         <td class="list_bg2" width="17%"  align="right">所在区</td>
         <td class="list_bg1" width="33%" align="left"><font size="2">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	        <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </font></td>
         <td  class="list_bg2" width="17%" align="right">预约代理商</td>
         <td  class="list_bg1" width="33%" align="left"><input type="text" name="txtAgentDealer" maxlength="10" size="25" value="<tbl:writeparam name="txtAgentDealer" />" class="text">  </td>
      </tr>
      <tr> 
        <td  class="list_bg2" align="right">创建日期</td>
         <td class="list_bg1" align="left" colspan="3"> 
           <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           - 
           <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
         </td>
         
       </tr>
   </table>
     
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	   <tr align="center">
	      <td class="list_bg1">
	      <table  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		      <td width="20"></td>
		    	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    	<td><input name="Submit" type="button" class="button" value="增 加" onclick="javascript:add()"></td>
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
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">
          <td class="list_head">预约单号</td>
          <td class="list_head">相关工单</td>
          <td class="list_head">所在区域</td>
          <td class="list_head">地址</td>
          <td class="list_head">用户姓名</td>
          <td class="list_head">预约代理商</td>
          <td class="list_head">状态</td>
          <td class="list_head">创建日期</td>        
       </tr>

     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%
        com.dtv.oss.dto.CustServiceInteractionDTO csidto = (com.dtv.oss.dto.CustServiceInteractionDTO)pageContext.getAttribute("oneline");
        String districtName ="";
        AddressDTO addressDto=Postern.getAddressDTO(csidto.getId());
         pageContext.setAttribute("address", addressDto);
        if (addressDto !=null) districtName =Postern.getDistrictNameByID(addressDto.getDistrictID());
        String status =csidto.getStatus();
        CsiProcessLogDTO logdto =Postern.getCsiProcessLogDTO(csidto.getId());
        Organization orgDto =null;
        if (logdto !=null)   orgDto= Postern.getOrganizationByID(logdto.getOrgID());
    
        if (logdto ==null) logdto =new CsiProcessLogDTO();
        if (orgDto ==null) orgDto =new Organization();
        if (addressDto ==null) addressDto =new AddressDTO();
    
        String strName = "";
        if (csidto.getCustomerID()!=0)
            strName = Postern.getCustomerNameByID(csidto.getCustomerID());    
        if (strName==null || strName.equals("")) strName = Postern.getNewCustomerNameByCsiID(csidto.getId());    
        if (strName==null) strName="";
    
        if (districtName==null) districtName="";
        if (status ==null) status = "";

        linkURL ="service_interaction_view.do?txtID="+csidto.getId()+"&OpenFlag="+CommonKeys.ACTION_OF_BOOKINGAGENT ;

      %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="<%=linkURL%>" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
          <td align="center" ><a href="job_card_view_by_agent.do?txtJobCardID=<tbl:write name="oneline" property="ReferJobCardID"/>" class="link12" ><tbl:writenumber name="oneline" property="ReferJobCardID" digit="7" hideZero="true" /></a></td>
           <td><tbl:WriteDistrictInfo name="address" property="districtID" /></td>
          
          <td align="center" ><%=(addressDto.getDetailAddress()==null) ? "" : addressDto.getDetailAddress()%></td>
          <td align="center" ><%=strName%></td>
          <td align="center" ><%=(orgDto.getOrgName()==null) ? "" : orgDto.getOrgName()%></td>
          <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="<%=status%>"/></td>
          <td align="center" ><tbl:writedate name="oneline" property="CreateTime" />  </td>
          <%linkURL = null;%>
        </tbl:printcsstr>
     </lgc:bloop>
     <tr>
       <td colspan="8" class="list_foot"></td>
     </tr>
     <tr class="trline" >
         <td align="right" class="t12" colspan="8" >
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
<br>

<SCRIPT LANGUAGE="JAVASCRIPT">
<!--
function check_frm() {
    if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "创建日期")) {
        return false;
    }
    if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "创建日期")) {
        return false;
    }
    if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"创建截止日期必须大于等于创建起始日期")){
        return false;
    }
    return true;
}
function query_submit() {
    if (check_frm())
        document.frmPost.submit();
}

function add(){
    document.frmPost.action ="agent_csi_booking.do?OpenFlag="+<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>
    document.frmPost.submit();
}
//-->
</SCRIPT>