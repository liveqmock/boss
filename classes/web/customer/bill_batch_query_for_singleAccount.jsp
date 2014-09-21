<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<script language=javascript>
function pay_submit(){
	 if(!hasSelect()){
      alert("没有选择记录，无法批量支付")
    	return;
   }
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
  
 function back_submit(url){
   	document.frmPost.action = url;
    document.frmPost.submit();
 }  

</script>
<table border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量支付帐单</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="singleAccount_pay_choose.do" method="post" >   
 <BR>
<input type="hidden" name="txtFrom" size="22" value="1">
<input type="hidden" name="txtTo" size="22" value="200">
<input type="hidden" name="txtAccountID" value="<tbl:writeparam name="txtAccountID" />">
<br>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'Index', this.checked)"></td>
    <td class="list_head" align="center">客户证号</td>
    <td class="list_head" align="center">客户姓名</td>
    <td class="list_head" align="center">帐户号</td>
    <td class="list_head" align="center">客户地址</td>
    <td class="list_head" align="center">帐期</td>
    <td class="list_head" align="center">帐单号</td>
    <td class="list_head" align="center">帐单金额</td>    
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<% 
   Collection resultCols = (Collection)pageContext.getAttribute("oneline");
   Iterator resultIter =resultCols.iterator();
   while (resultIter.hasNext()) {
     Object inVoiceObj =resultIter.next();
     boolean checkedNo=false;
     if(request.getParameter("strInvoiceNo")!=null){
	     String[] strInvoiceNo=request.getParameter("strInvoiceNo").split(";");
	     for(int i=0;i<strInvoiceNo.length;i++){
	   	   if(strInvoiceNo[i].equals(inVoiceObj.toString())){
	   	  	  checkedNo=true;
	   	   }
	     }
     }

%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><input type="checkbox" name="Index" value="<%=inVoiceObj.toString()%>" <%if(checkedNo){%>checked<%}%>></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <%
       Object invoiceCycleObj =resultIter.next();
       int invoiceCycleID =WebUtil.StringToInt(invoiceCycleObj.toString());
    %>
    <td align="center"><%=Postern.getBillingCycleNameByID(invoiceCycleID)%></td>
    <td align="center"><%=inVoiceObj.toString()%></td>
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

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="account_view.do">
     <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit('<bk:backurl property='account_view.do' />')" value="返 回"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	  </bk:canback> 
		<rs:hasresultset>
			<td width="20" ></td> 
	    <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	    <td><input name="next" type="button" class="button" onClick="javascript:pay_submit()" value="批量支付"></td>
	    <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	  </rs:hasresultset>
	</tr>
</table>
 
</form> 
