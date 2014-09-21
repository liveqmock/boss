<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<script language=javascript>
function check_form(){
	  if(!check_Num(document.frmPost.txtCustomerID,true,"客户证号"))
		    return false;
	  if (document.frmPost.txtAcctBalance1.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance1,true,"帐单起始金额")){
	  	 	  return false;
	  	 }
	  	 if (document.frmPost.txtAcctBalance1.value*1 <0){
	  	    alert("帐单起始金额不能小于0");
	  	    return false;
	  	 }
	  }else{
	  	 alert("帐单起始金额不能为空");
	  	 return false;
	  }
	  
	  if (document.frmPost.txtAcctBalance2.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance2,true,"帐单结束金额")){
	  	 	  return false;
	  	 }
	  }
	  
	  if (document.frmPost.txtAcctBalance1.value !='' && document.frmPost.txtAcctBalance2.value !=''){
	     if (document.frmPost.txtAcctBalance1.value*1 > document.frmPost.txtAcctBalance2.value*1){
	        alert("帐单起始金额大于帐单结束金额");
	        return false;
	     }	
	  }
	  return true;
}

function query_submit(){
    if (check_form()){
    	 document.frmPost.submit();
    } 
}

function pay_submit(){
	 if(!hasSelect()){
      alert("没有选择记录，无法批量支付")
    	return;
   }
	 document.frmPost.action="mergebill_batch_pay_choose.do";
	 document.frmPost.submit();
}
function hasSelect(){
    var flag =false;
    if (document.frmPost.Index!=null){
    	if(document.frmPost.Index.length!=null){
       		for (i=0 ;i< document.frmPost.Index.length ;i++){
           		if (document.frmPost.Index[i].checked){
              		flag =true;
              		break;
              }
           	}
       	}
       	else{
       		if (document.frmPost.Index.checked){
              flag =true;
          }
       	}
    }
    if(!flag){
    	return false;
    }
    return true;
 }
 
 function view_acct_invoice_detail(acctId){
 	  window.open('list_acct_invoice_detail.do?txtAccountID='+acctId,'','width=300,height=250,resizable=no,toolbar=no,scrollbars=yes');
 }

</script>
<%
  Map queryStyle =new HashMap();
  queryStyle.put("A","按 地址排序");
  queryStyle.put("C","按 姓名排序");
  pageContext.setAttribute("queryStyle",queryStyle);
  String txtqueryStyle =(request.getParameter("txtqueryStyle")==null) ? "1" :request.getParameter("txtqueryStyle");
  String strAcctIds =(request.getParameter("strAcctIds")==null) ? "" :request.getParameter("strAcctIds");
  String[] strAcctId=strAcctIds.split(";");
%>
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
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">合并帐单批量查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="mergebill_batch_pay_query.do" method="post">   
<table width="822"> 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">客户证号</td>
    <td class="list_bg1" width=33% align="left"><input name="txtCustomerID" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtCustomerID" />"></td>
    <td class="list_bg2" width=17% align="right">客户类型</td>
    <td class="list_bg1" width=33% align="left"><d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" /></td>
  </tr>
  <tr>
    <%
        Map mapStatus = new LinkedHashMap();
        mapStatus.put("V", "至少一个正常");
        pageContext.setAttribute("mapStatus", mapStatus);
        String txtServiceAcctStatus =(request.getParameter("txtServiceAcctStatus")==null) ? "V" :request.getParameter("txtServiceAcctStatus");
    %>
    <td class="list_bg2" align="right">收费终端状态</td>
    <td class="list_bg1" align="left"> <tbl:select name="txtServiceAcctStatus" set="mapStatus" match="<%=txtServiceAcctStatus%>" width="20"/></td>
    <td class="list_bg2" align="right">客户姓名</td>
    <td class="list_bg1" align="left"><input name="txtCustomerName" type="text" class="text"  maxlength="20" size="22" value="<tbl:writeparam name="txtCustomerName" />"></td>  
  </tr>
  <tr>
  	<td class="list_bg2" align="right">固定电话</td>
    <td class="list_bg1" align="left"><input type="text" name="txtTelephone" size="22" class="text"  value="<tbl:writeparam name="txtTelephone" />"  ></td>  
    <td class="list_bg2" align="right">移动电话</td>
    <td class="list_bg1" align="left"><input type="text" name="txtTelephoneMobile" size="22" class="text" value="<tbl:writeparam name="txtTelephoneMobile" />"  ></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">查询方式</td>
	  <td class="list_bg1" align="left"><tbl:select name="txtqueryStyle" set="queryStyle" match="<%=txtqueryStyle%>" width="20" /></td>
    <td class="list_bg2" align="right">所属区域</td>
    <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>   
  </tr>
  <tr>
    <td class="list_bg2" align="center" colspan="2">
    	 <%
    	   String  txtAcctBalance1 =(request.getParameter("txtAcctBalance1") ==null) ? "0" :request.getParameter("txtAcctBalance1");
    	 %>
       <input name="txtAcctBalance1" type="text" class="text" maxlength="10" size="10" value="<%=txtAcctBalance1%>">
         < 帐单总金额 <=
       <input name="txtAcctBalance2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtAcctBalance2" />">
	  </td> 
	  <td class="list_bg2" align="right">详细地址</td> 
	  <td class="list_bg1" width=33% align="left"><input name="txtAddressDetail" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtAddressDetail" />"></td> 
	</tr>
</table>

 <BR>
 <table  border="0" cellspacing="0" cellpadding="0">
	  <tr>			
	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	     <td><input name="Submit1" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 </tr>
 </table>    
 

<input type="hidden" name="txtFrom" size="22" value="1">
<input type="hidden" name="txtTo" size="22" value="40">
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
    <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'Index', this.checked)"></td> 
    <td class="list_head" align="center">帐户号</td>
    <td class="list_head" align="center">客户姓名</td>
    <td class="list_head" align="center">客户证号</td>
    <td class="list_head" align="center">客户地址</td>
    <td class="list_head" align="center">帐单金额</td>    
  </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<% 
   Collection resultCols = (Collection)pageContext.getAttribute("oneline");
   Iterator resultIter =resultCols.iterator();
   while (resultIter.hasNext()) {
     Object acctIdObj =resultIter.next();
     boolean checkedNo=false;
	   for(int i=0;i<strAcctId.length;i++){
	  	   if(strAcctId[i].equals(acctIdObj.toString())){
	  	  	  checkedNo=true;
	  	  	  break;
	   	   }
	   }
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><input type="checkbox" name="Index" value="<%=acctIdObj.toString()%>" <%if(checkedNo){%>checked<%}%>></td>
    <td align="center"><a href="JavaScript:view_acct_invoice_detail('<%=acctIdObj.toString()%>')"><%=acctIdObj.toString()%></a></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center" style="text-align:right"><%=resultIter.next()%></td>
</tbl:printcsstr>
<%
   }    
%>
</lgc:bloop>

  <tr>
    <td colspan="9" class="list_foot"></td>
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
</TD>
</TR>
</TABLE>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="100%">
<tr>
<td>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:pay_submit()" value="批量支付"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</td>
</tr>
</table>
</rs:hasresultset> 
</table>
</form> 
