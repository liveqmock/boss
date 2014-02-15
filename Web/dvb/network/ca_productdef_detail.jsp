<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.CAProductDefDTO"%>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
 

function ca_productdef_modify()
{
     
        document.frmPost.submit();
   
}

function back_submit(){
	url="<bk:backurl property='ca_productdef_query.do' />";
	 
    document.location.href= url;

}

</SCRIPT>
  
<form name="frmPost" method="post" action="ca_product_op.do">
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="CA_PRODUCTDEF_MODIFY">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">修改CA产品定义</td>
	</tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0"
	 cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
	<%
	CAProductDefDTO dto=(CAProductDefDTO)pageContext.getAttribute("oneline");

				String hostName=(String)Postern.getHostNameById(dto.getHostID());
				if(hostName==null)
				hostName="";
				%>
<input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod" />">
<table width="98%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="left" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	 
	<tr>
		<td class="list_bg2" align="right">主机名称*</td>
		<td class="list_bg1">
		<input name="txtHostName"
			type="text" class="textgray" maxlength="10" size="25" readonly 
			value="<%=hostName%>"></td>
		<td class="list_bg2" align="right">OPI_ID</td>
		<td class="list_bg1">
		<input name="txtOPI_ID"
			type="text" class="textgray" maxlength="10" size="25" readonly 
			value="<tbl:write name="oneline" property="opiID"/>"></td>
	</tr>
	 <input type="hidden" name="txtHostID" size="20" value="<tbl:write name="oneline" property="hostID"/>">
	<tr>
	<td class="list_bg2" align="right">CA产品ID</td>
		<td class="list_bg1"><font size="2"><input name="txtCAProductID"
			 class="textgray" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="caProductID" />"  readonly ></font></td>
		<td class="list_bg2" align="right">CA产品名称</td>	 
		<td class="list_bg1"><font size="2"><input name="txtCaProductName"
			  class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="productName" />"></font></td>
			 
	</tr>		
	 
		 
	
</table>
	</lgc:bloop>
 
	<table width="98%" border="0" align="center" cellpadding="0"
	 cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
  </table> 
  <br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:ca_productdef_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	    

</form>
