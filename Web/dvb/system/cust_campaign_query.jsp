<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<script language=javascript>
<!--
function check_form(){
	if(!check_Num(document.frmPost.txtAccountID,false,"资金帐户"))
		return false;
		
	if(!check_Num(document.frmPost.txtServiceAccountID,false,"业务帐户"))
		return false;

	if (!check_TenDate(document.frmPost.txtDateFromF, true, "促销开始日期起始时间"))
     	return false;	
  	if (!check_TenDate(document.frmPost.txtDateFromT, true, "促销开始日期结束时间"))
    	return false;	
 	if (!check_TenDate(document.frmPost.txtDateToF, true, "促销结束日期起始时间"))
 		return false;	
    if (!check_TenDate(document.frmPost.txtDateToT, true, "促销结束日期结束时间"))
    	return false;	


  	if(!compareDate(document.frmPost.txtDateFromF,document.frmPost.txtDateFromT,"促销开始日期起始时间必须小于等于结束时间"))
		return false;  	
	if(!compareDate(document.frmPost.txtDateToF,document.frmPost.txtDateToT,"促销结束日期起始时间必须小于等于结束时间"))
		return false;  


	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}


function cust_campaign_detail_click(ccID){
	self.location.href="cust_campaign_detail.do?txtActionType=campaign&txtCCID="+ ccID ;
}

//-->
</script>
<%
	int customerID=WebUtil.StringToInt(request.getParameter("txtCustomerID"));
 	pageContext.setAttribute("CAMPAIGNMAP",Postern.getCampaignMapByTypeORCustID(CommonKeys.CAMPAIGNCAMPAIGNTYPE_NORMAL,customerID));
 	pageContext.setAttribute("SAMAP",Postern.getServiceAccountIDMapByCustID(customerID));
%>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户促销活动查询</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
    
<form name="frmPost" action="cust_campaign_query.do" method="post" >    
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" class="list_bg">
     <tr>  
      <td class="list_bg2" width="17%"><div align="right">帐户</div></td>
      <td class="list_bg1" width="33%"  align="left">
	    	<d:selAccByCustId name="txtAccountID" mapName="self"  match="txtAccountID" width="23"/>
	    </td>
      <td class="list_bg2" width="17%"><div align="right">业务帐户</div></td>
      <td class="list_bg1" width="33%" align="left">
          <tbl:select name="txtServiceAccountID" set="SAMAP" match="txtServiceAccountID" width="23" />	
     	</td>
    </tr>
     <tr>  
      <td class="list_bg2" width="17%"><div align="right">促销活动</div></td>
      <td class="list_bg1" width="33%"  align="left">
				<tbl:select name="txtCampaignID" set="CAMPAIGNMAP" match="txtCampaignID" width="23" />
      </td>
      <td class="list_bg2" width="17%"><div align="right">状态</div></td>
      <td class="list_bg1" width="33%" align="left">
          <d:selcmn name="txtStatus" mapName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="txtStatus" width="23" />
     	</td>
    </tr>
    <tr>   
      <td class="list_bg2"><div align="right">促销开始日期起</div></td>
      <td class="list_bg1"  align="left" >
        <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFromF)" onblur="lostFocus(this)" type="text" name="txtDateFromF" size="25" value="<tbl:writeparam name="txtDateFromF"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFromF,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		</td>
		<td class="list_bg2"><div align="right">促销开始日期止</div></td>
      <td class="list_bg1"  align="left" >
      	<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFromT)" onblur="lostFocus(this)" type="text" name="txtDateFromT" size="25" value="<tbl:writeparam name="txtDateFromT"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFromT,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>     	  	
    </tr>
    <tr>   
      <td class="list_bg2"><div align="right">促销结束日期起</div></td>
      <td class="list_bg1"  align="left" >
        <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateToF)" onblur="lostFocus(this)" type="text" name="txtDateToF" size="25" value="<tbl:writeparam name="txtDateToF"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateToF,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
      <td class="list_bg2"><div align="right">促销结束日期止</div></td>
      <td class="list_bg1"  align="left" >
      	<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateToT)" onblur="lostFocus(this)" type="text" name="txtDateToT" size="25" value="<tbl:writeparam name="txtDateToT"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateToT,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>     	  	
    </tr>
    <tr>  
      <td class="list_bg2" colspan="4" align="center">
          <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查    询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="22"></td>
			  </tr>
	      </table>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>  
 <br>
 
<rs:hasresultset>

   <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
      <tr class="list_head"> 
          <td width="10%" class="list_head"><div align="center">客户促销号</div></td>
          <td width="30%" class="list_head"><div align="center">促销活动名称</div></td>                    
          <td width="10%" class="list_head"><div align="center">账户ID</div></td> 
          <td width="10%" class="list_head"><div align="center">业务账户ID</div></td>         
          <td width="10%" class="list_head"><div align="center">状态</div></td>
          <td width="15%" class="list_head"><div align="center">优惠开始日期</div></td>
          <td width="15%" class="list_head"><div align="center">优惠结束日期</div></td>
      </tr> 
 <%
 	Map accountMap=new HashMap();
 	accountMap=Postern.getAccountNameMapByCustID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
 %>       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	CustomerCampaignDTO dto = (CustomerCampaignDTO)pageContext.getAttribute("oneline");
	String ccName=Postern.getCustCampaignOrGroupBarginNameByCCID(dto.getCcID());
	if(ccName==null)
		ccName="";
%>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	 <td align="center" ><a href="javascript:cust_campaign_detail_click('<tbl:write name="oneline" property="ccID"/>');"><tbl:write name="oneline" property="ccID"/></a></td>
      	 <td align="center" ><%=ccName%></td>      		
      	 <td align="center" ><tbl:write name="oneline" property="AccountID"/></td>
      	 <td align="center" ><tbl:write name="oneline" property="serviceAccountID"/></td>
      	 <td align="center" ><d:getcmnname typeName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="oneline:status" /></td>
      	 <td align="center" ><tbl:writedate name="oneline" property="dateFrom" /></td>
      	 <td align="center" ><tbl:writedate name="oneline" property="dateTo" /></td>
    </tbl:printcsstr>
</lgc:bloop> 
  <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
  </table>

<table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
 
 </rs:hasresultset>
  
 <tbl:hiddenparameters pass="txtCustomerID" />
 <input type="hidden" name="txtActionType" value="campaign">   
 <input type="hidden" name="txtFrom" >
<input type="hidden" name="txtTo" >           
</form> 
 
 

     
      
      
      
      
      