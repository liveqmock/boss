<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%
	String valueID=request.getParameter("valueControlID");
	String descID=request.getParameter("descControldID");
	System.out.println("valueID:"+valueID);
	System.out.println("descID:"+descID);
	String showPerson = "收费人";
	if(request.getParameter("showPerson") != null)
	{
		showPerson = request.getParameter("showPerson");
	}
%>
<script language=javascript>
function selected_submit(){
	var selValue="";
	var selDesc='';
	var showPerson = "<%=showPerson%>";
	var selList=document.frmPost.selectedID;
	if(selList.type=='radio'&&selList.checked){
		selValue=selList.value;
		selDesc=document.frmPost.txtOperatorAccount.value;
	}
	if(typeof(selList.length)=="undefined")
	{
		var cur=selList;
		if(cur.checked){
			selValue=cur.value;
			selDesc=document.frmPost.txtSelLoginID.value;
		}
	}
	else
	{
	for(var i=0;i<selList.length;i++){
		var cur=selList[i];
		if(cur.checked){
			selValue=cur.value;
			selDesc=document.frmPost.txtSelLoginID[i].value;
		}
	}
}
	//
	if(selValue!=null&&selValue!=''){
		window.opener.frmPost.elements['<%=valueID%>'].value=selValue;
		window.opener.frmPost.elements['<%=descID%>'].value=selDesc;
		window.close();
	}else{
		alert("请选择一个"+showPerson+"!");	
	}
}

function query_submit(){
	document.frmPost.submit();
}
</script>
<form name="frmPost" action="query_operator.do" method="post" >  
	<tbl:hiddenparameters pass="valueControlID/descControldID/txtStatus/showPerson" />
  <input type="hidden" name="txtFrom" size="22" value="1">
  <input type="hidden" name="txtTo" size="22" value="10">
	<br>
	<table width="580px" border="0" align="center" cellpadding="0" cellspacing="0" >
		<tr><td width="100%">
		<table  border="0" align="center" cellpadding="0" cellspacing="10" >
		  <tr>
		    <td><img src="img/list_tit.gif" width="19" height="19"></td>
		    <td class="title"><%=showPerson%>查询</td>
		  </tr>
		</table>		
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>	
		<br>
		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right" width='15%'>
					ID
				</td>
				<td class="list_bg1" width='35%'>
					<input type="text" name="txtOperatorID" size="20"  value="<tbl:writeparam name="txtOperatorID" />" >
				</td>
				<td class="list_bg2" align="right" width='15%'>
					姓名
				</td>
				<td class="list_bg1" width='35%'>
					<input type="text" name="txtOperatorName" size="20" maxlength="25" value="<tbl:writeparam name="txtOperatorName" />">
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2" align="right" >
					登陆帐号
				</td>
				<td class="list_bg1" >
					<input type="text" name="txtOperatorAccount" size="20"  value="<tbl:writeparam name="txtOperatorAccount" />">
				</td>
				<td class="list_bg2" align="right" >
					所属组织
				</td>
				<td class="list_bg1" >
		    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			    <input type="text" name="txtOrgDesc" size="20" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	   		 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
				</td>
			</tr>
		</table>	
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table> 
	<br>
<rs:hasresultset>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	  <tr class="list_head">
	    <td class="list_head" width="5%"></td>
	    <td class="list_head" width="10%">ID</td>
	    <td class="list_head" width="15%">姓名</td>
	    <td class="list_head" width="20%">登陆帐号</td>
	    <td class="list_head">所属组织</td>
		</tr>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   	<%
   	//OperatorDTO dto=(OperatorDTO)pageContext.getAttribute("oneline");
   	%>
        <tr>
          <td class="list_bg1" align="left">
          	<input type="radio" name="selectedID" value="<tbl:write name="oneline" property="operatorID"/>">
	        </td>                      
          <td class="list_bg1" align="left">
          	<tbl:write name="oneline" property="operatorID"/>
          </td>                      
          <td class="list_bg1" align="left">
            <tbl:write name="oneline" property="operatorName"/>
            <input type="hidden" name="operatorNames" value="<tbl:write name="oneline" property="operatorName"/>">	
          </td>
           <td class="list_bg1" align="left">
             <tbl:write name="oneline" property="loginID"/>
            <input type="hidden" name="txtSelLoginID" value="<tbl:write name="oneline" property="loginID"/>">	
           </td>  
           <td class="list_bg1" align="left">
           	<tbl:WriteOrganizationInfo property="orgID" name="oneline"/>
           </td>
        </tr>
     </lgc:bloop>
		  <tr>
		    <td colspan="5" class="list_foot"></td>
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
       <br>
       <br>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" >
		<tr align="center">
		  <td >
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
			   <td><img src="img/button2_r.gif" width="22" height="20"></td>
			   <td><input name="prev" type="button" class="button" onClick="javascript:selected_submit()" value="确 定"></td>
			   <td><img src="img/button2_l.gif" width="11" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table> 
</rs:hasresultset> 

  </td></tr></table>
</form>