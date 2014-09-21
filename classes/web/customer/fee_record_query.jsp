<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO"%>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO"%>
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<!-- 付费记录查询 -->
<Script language="JavaScript" >
 function view_detail_click(aiNO) {
    self.location.href = "fee_detailrecord_query.do?txtID="+aiNO;
 }
 function back_bill_view(){
    var txtInvoiceNo =document.frmPost.txtInvoiceNo.value;
    self.location.href ="bill_view.do?txtInvoiceNo="+txtInvoiceNo;
 }
</Script>


<table align="center" width="810" border="0" cellspacing="1" cellpadding="1" class="list_bg" >
    <tr>
       <td align="center" class="title1">
          <img src="img/list_tit.gif" width="19" height="19"><font size="4"><strong>&nbsp;&nbsp;帐单费用记录查询</strong></font>
         </td>
    </tr>
    <tr><td>
       <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
        </table>
    </td></tr>
</table>
<br>

<form name="frmPost" method="post" action="" >
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <tbl:hiddenparameters pass="txtInvoiceNo" />
    <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">
         <td class="list_head">流水号</td>
         <td class="list_head">发生日期</td>
         <td class="list_head">费用金额</td>
         <td class="list_head">费用名称</td>
	 <td class="list_head">状态</td>
	 <td class="list_head">计费周期</td>
	 <td class="list_head">相关帐单号</td>
	 <td class="list_head">销帐金额</td>
       </tr>
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
      <%
           AccountItemDTO dto = (AccountItemDTO)pageContext.getAttribute("oneline");
           AcctItemTypeDTO  acctItemTypeDto=Postern.getAcctItemTypeDTOByAcctItemTypeID(dto.getAcctItemTypeID());
           String acctItemTypeName =acctItemTypeDto.getAcctItemTypeName();
           String strBillingCycleName=Postern.getBillingCycleNameByID(dto.getBillingCycleID());
    	    if(strBillingCycleName==null)
    	        strBillingCycleName="";
      
      %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="AiNO"/>')" class="link12">
            <div align="center">
               <tbl:writenumber name="oneline" property="AiNO" digit="7" />
            </div>    
          </td>
          <td align="center" ><tbl:writedate name="oneline" property="CreateTime"/></td>
          <td align="center" ><tbl:write name="oneline" property="Value" /></td>
          <td align="center" ><%=acctItemTypeName%></td>
          <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status" /></td>
          <td align="center" ><%=strBillingCycleName%></td>
          <td align="center" ><tbl:write name="oneline" property="InvoiceNO"/></td>
          <td align="center" ><tbl:write name="oneline" property="SetOffAmount"/></td>   
       </tbl:printcsstr>
     </lgc:bloop>
     <tr>
	<td colspan="8" class="list_foot"></td>
     </tr>
    <rs:hasresultset>
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
     </rs:hasresultset> 
      <tr class="trline" >
         <td colspan="8">
         <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
          <tr>
            <td><img src="img/mao.gif" width="1" height="1"></td>
          </tr>
         </table>
       </td></tr>
    </table>              
<br>
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif" >
             <a href="javascript:back_bill_view()" class="btn12">返回帐单</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
        </tr>
      </table>
</form>

