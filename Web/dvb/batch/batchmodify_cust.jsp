<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<script language=javascript>
function check_form(){
	 //所属区域
	 if (document.frmPost.txtDistrictID.value ==''){
	     alert("所属区域选择不能为空!");
	     return false;
	 }
   return true;
}

function query_submit(){
   if (check_form()) {
      document.frmPost.submit();
   }
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

function next_submit(){
	if(!hasSelect()){
      alert("没有选择记录，无法批量操作！")
    	return;
   }
   document.frmPost.action="batchmodify_cust_confirm.do";
	 document.frmPost.submit();
}

function KeyDown(){
   if(event.keyCode==13){
   	  event.returnValue=false; 
      event.cancel = true; 
   	  query_submit();
   }
}
</script>

<%
  String strCustIds =(request.getParameter("strCustIds")==null) ? "" :request.getParameter("strCustIds");
  String[] strCustId=strCustIds.split(";");
%>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量修改客户查询</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="batchmodify_cust_query.do" onkeydown="KeyDown()">    
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">所属区域</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
    <td class="list_bg2" width=17% align="right">客户类型</td>
    <td class="list_bg1" width=33% align="left">
    	<d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" />
    </td>
  </tr>
  <tr>
  	<td class="list_bg2" align="right" width="17%">安装地址</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input name="txtDetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtDetailAddress" />">
    </td>
  	<td class="list_bg2" align="right" width="17%">客户备注信息</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input name="txtComments" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtComments" />"></td>
  </tr> 
</table>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td>
				<a href="javascript:javascript:query_submit()" >
					<input name="search" type="button" class="button" value="查 询" onclick="javascript:query_submit()">
				</a>
				</td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table>      
	<input type="hidden" name="txtFrom" size="22" value="1">
  <input type="hidden" name="txtTo" size="22" value="200">
<br>      
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<rs:hasresultset>
<br>
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	<td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'Index', this.checked)"></td> 
    <td class="list_head" width="80" nowrap>客户证号</td>
    <td class="list_head" width="80" nowrap>姓名</td>
    <td class="list_head" width="80" nowrap>类型</td>
    <td class="list_head" width="80" nowrap>证件号</td>
    <td class="list_head" width="120" nowrap>所属分区</td>
    <td class="list_head" width="170" nowrap>地址</td>
    <td class="list_head" width="80" nowrap>创建日期</td>
    <td class="list_head" width="120" nowrap>备注</td>
  </tr>
  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  <%
     com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
     pageContext.setAttribute("oneline", wrap.getCustDto());
     pageContext.setAttribute("custaddr",  wrap.getAddrDto());
     boolean checkedNo=false;
	   for(int i=0;i<strCustId.length;i++){
	  	   if(strCustId[i].equals(String.valueOf(wrap.getCustDto().getCustomerID()))){
	  	  	  checkedNo=true;
	  	  	  break;
	   	   }
	   }
  %>
  <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
  	<td align="center"><input type="checkbox" name="Index" value="<%=wrap.getCustDto().getCustomerID()%>" <%if(checkedNo){%>checked<%}%>></td>
    <td align="center"><tbl:write name="oneline" property="customerID" /></td>
    <td align="center"><tbl:write name="oneline" property="name" /></td>
    <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
    <td align="center"><tbl:write name="oneline" property="CardID" /></td>
    <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
    <td align="center"><tbl:write name="custaddr" property="detailAddress" /></td>
    <td align="center"><tbl:writedate name="oneline" property="CreateTime" /></td>
    <td align="center"><tbl:write name="oneline" property="comments" /></td>
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
	   <td><input name="next" type="button" class="button" onClick="javascript:next_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</rs:hasresultset>                  
</form> 