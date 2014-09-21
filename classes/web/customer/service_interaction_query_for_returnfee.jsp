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
	if(!checkPlainNum(document.frmPost.txtUserId,true,9,"客户证号"))
		return false;   	
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId)
{
	self.location.href="service_interaction_view.do?txtID="+strId;
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">退费受理单查询</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="service_interaction_query_result_for_returnfee.do" >  
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <input type="hidden" name="txtCondition" size="20" value="returnfee">
    <input type="hidden" name="txtStatus" size="20" value="<%=CommonKeys.CUSTSERVICEINTERACTION_STATUS_FAIL%>">
    <%
    	String csitype=CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS + ";" + CommonKeys.CUSTSERVICEINTERACTIONTYPE_UO + ";"
    		     + CommonKeys.CUSTSERVICEINTERACTIONTYPE_CAO + ";" + CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB + ";"
    		     + CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU + ";" + CommonKeys.CUSTSERVICEINTERACTIONTYPE_CAS + ";"
    		     + CommonKeys.CUSTSERVICEINTERACTIONTYPE_CAR + ";" + CommonKeys.CUSTSERVICEINTERACTIONTYPE_CAA;
    %>
    <input type="hidden" name="txtType" value="<%=csitype%>" >
    <input type="hidden" name="txtPaymentStatus" value="<%=CommonKeys.CUSTSERVICEINTERACTION_PAYSTATUS_PAYED%>" >
    <table width="98%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr> 
          <td class="list_bg2" align="right" width="17%">受理单编号</td>   
          <td class="list_bg1" align="left" width="33%"> 
             <input type="text" name="txtID" maxlength="9" size="25"  value="<tbl:writeparam name="txtID" />" class="text">
          </td>
          <td class="list_bg2" align="right" width="17%">详细地址</td>
          <td class="list_bg1" align="left" width="33%">  
             <input type="text" name="txtDetailAddress" maxlength="100" size="25" value="<tbl:writeparam name="txtDetailAddress" />" class="text">
          </td>
        </tr>        
        <tr>       
          <td class="list_bg2" align="right">姓名</td>
          <td class="list_bg1" align="left"> 
             <input type="text" name="txtName" maxlength="10" size="25" value="<tbl:writeparam name="txtName" />" class="text">
          </td>
          <td class="list_bg2" align="right">客户证号</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtUserId" maxlength="9" size="25" value="<tbl:writeparam name="txtUserId" />" class="text">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">创建日期</td>
          <td class="list_bg1" align="left"> 
          <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          - 
          <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
          <td class="list_bg1" colspan="2" >
             <table  border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                  <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="查 询"></td>
                  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                </tr>
              </table>
          </td> 
        </tr>         
     </table>
     <br>
     <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
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
      <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
      <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:type" /></td>
      <% if (status.equalsIgnoreCase("c")||isSubCustomer) {%>
       <td align="center" ><tbl:writenumber name="oneline" property="customerID" digit="8" hideZero="true"/></td>
      <% } else {%>
       <td align="center" ><tbl:writenumber name="oneline" property="customerID" digit="8" hideZero="true" /></td>
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


     