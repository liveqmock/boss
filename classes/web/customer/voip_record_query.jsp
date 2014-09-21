<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>

<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.*" %>

<script language=javascript>
function check_form(){
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
	if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return ;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"帐户号"))
		return ;
	document.frmPost.submit();
        
}

function view_detail_click(strId)
{
       self.location.href="customer_view.do?txtCustomerID="+strId;
}
function view_cancledetail_click(strId) {
	self.location.href="cancelled_customer_view.do?txtCustomerID="+strId;
}



</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">语音详单查询</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<%Map mapBillingCycle=Postern.getBillingcycle();
  pageContext.setAttribute("AllBillingCycle",mapBillingCycle);
  Map mapAcctItemType=Postern.getAcctitemtype("");
  pageContext.setAttribute("AllAcctItemType",mapAcctItemType);
%>
<form name="frmPost" method="post" action="voip_record_query.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <input type="hidden" name="txtSourceType" value="VOIP">
   <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="12%" align="right" class="list_bg2">计费周期</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <tbl:select name="txtBillingCycleID" set="AllBillingCycle" width="23" match="txtBillingCycleID"/>
          </font> </td>                      
          <td width="11%" class="list_bg2" align="right">客户证号</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <input type="text" name="txtCustID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustID" />" class="text">
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">帐户号</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtAcctID" maxlength="9" size="25"  value="<tbl:writeparam name="txtAcctID" />" class="text">  
           </font></td>  
           <td  class="list_bg2" align="right">电话号码</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtPointOrigin" maxlength="25" size="25"  value="<tbl:writeparam name="txtPointOrigin" />" class="text">               
           </font></td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">帐目类型</td>
            <td class="list_bg1" align="left" colspan="3"><font size="2">
             <tbl:select name="txtAcctItemTypeID" set="AllAcctItemType" width="23" match="txtAcctItemTypeID"/>
            </font></td>
        </tr>              
        <tr> 
           <td  class="list_bg2" colspan="4">
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
         <td class="list_head">主叫号码</td>
         <td class="list_head">被叫号码</td>
         <td class="list_head">通话起始时间</td>
         <td class="list_head">通话截至时间</td>
         <td class="list_head">通话时长</td>
         <td class="list_head">标准费用</td>
          <td class="list_head">帐目类型</td>
         <td class="list_head">折扣</td>
         <td class="list_head">优惠时长</td>
         <td class="list_head">实际费用</td>
       </tr>
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
      <%
      
      VOIPRecordDTO dto = (VOIPRecordDTO) pageContext.getAttribute("oneline");
       int  units=dto.getUnits();
       String formatTime =  WebOperationUtil.getFormatTime(units);
       double rateAmount=dto.getRateAmount();
       double discount=dto.getDiscount();
          String strAcctItemTypeName=Postern.getAcctItemTypeByAcctItemTypeID(dto.getAcctItemTypeID());
    	  if(strAcctItemTypeName==null)
    		strAcctItemTypeName="";
      %>
     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      <td align="center" ><tbl:write name="oneline" property="pointOrigin" /></td>
      <td align="center" ><tbl:write name="oneline" property="pointTarget" /></td>
      <td align="center" ><tbl:write name="oneline" property="date1" /></td>
      <td align="center" ><tbl:write name="oneline" property="date2" /></td>
      <td align="center" ><%=formatTime%> </td>
      <td align="center" ><tbl:write name="oneline" property="rateAmount" /></td>     
      <td align="center" ><%=strAcctItemTypeName%></td>
      <td align="center" ><tbl:write name="oneline" property="discount" /></td>
      <td align="center" ><tbl:write name="oneline" property="unitsCredited" /></td>
      <td align="center" ><tbl:write name="oneline" property="realValue" /></td>
     </tbl:printcsstr>
    </lgc:bloop>
    <tr>
      <td colspan="10" class="list_foot"></td>
    </tr>
    <tr class="trline" >
       <td align="right" class="t12" colspan="10" >
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



