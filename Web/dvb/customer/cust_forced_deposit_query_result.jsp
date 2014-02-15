<%@ page import="com.dtv.oss.dto.ForcedDepositDTO"%>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<Script language="javascript">
<!--
 
  function checkSelected(){
	var returnValue=false;
	if(document.frmPost.seqNo==null){
		 alert("当前页面没有数据！");
		return false;
	}else{
		if (document.frmPost.seqNo.length!=null){
			for (var i=0;i<document.frmPost.seqNo.length;i++)
			{
				if (document.frmPost.seqNo[i].checked)
				{
					returnValue= true;
					break;
				}
			}
		}else{
			if(document.frmPost.seqNo.checked){
				returnValue= true;
			}
		}
	}
	if(!returnValue){
		alert("请至少选定一条要退还的压金");
	}
    return returnValue;
}
function frm_submit() {

    if(checkSelected()){
    document.frmPost.submit();
    }
}
//-->
</Script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">押金管理</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 

<form name="frmPost" method="post" action="cust_forced_deposit.do">
  <input type="hidden" name="txtFrom" size="20" value="1">
  <input type="hidden" name="txtTo" size="20" value="10">
  <input type="hidden" name="func_flag"  value="250" >
 
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr class="list_head">
	   <td class="list_head"><div align="center"></div></td>
          <td class="list_head"><div align="center">序号</div></td>
          <td class="list_head"><div align="center">金额</div></td>
          <td class="list_head"><div align="center">创建时间</div></td>
          <td class="list_head"><div align="center">退还时间</div></td>
          <td class="list_head"><div align="center">状态</div></td>
          <td class="list_head"><div align="center">退还金额</div></td>
          <td class="list_head"><div align="center">没收金额</div></td>
          
      </tr>
       <%  String disabled = null;%>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
       <%
           ForcedDepositDTO dto = (ForcedDepositDTO) pageContext.getAttribute("oneline");
           double money= dto.getMoney();
           int csiID = dto.getWithdrawCsiID();
           if(dto.getStatus() != null && (dto.getStatus().equalsIgnoreCase("N")||dto.getStatus().equalsIgnoreCase("P"))) {
               disabled = "";
           } else {
               disabled = "disabled";
           }
       %>
        <input type="hidden" name="txtWithdrawCsiID" size="20" value="<%=csiID%>">
       <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
         
            <td><div align="center"><input type="checkbox" <%=disabled%> name="seqNo" value="<tbl:write name="oneline" property="seqNo"/>"></div></td>
             <td><div align="center"><tbl:write name="oneline" property="seqNo"/></div></td>
          <td><div align="center"><tbl:write name="oneline" property="Money"/></div></td>
          <td><div align="center"><tbl:writedate name="oneline" property="CreateTime"/></div></td>
          <td><div align="center"><tbl:writedate name="oneline" property="processTime"/></div></td>
          <td><div align="center"><d:getcmnname typeName="SET_V_FDSTATUS" match="oneline:Status" /></div></td>
           <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod"/>"/>
          <td> <div align="center"><input type="text" name="txtWithdrawMoney"  value="<tbl:write name="oneline" property="Money"/>"/> </td>
          <td><div align="center"><tbl:write name="oneline" property="seizureMoney"/></td>
       </tr>
      </lgc:bloop>
      </table>
  
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">退还押金</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	  </td>
  </tr>
  </table>
  </form>
 </td></tr><table>  



<tbl:generatetoken />



 

 