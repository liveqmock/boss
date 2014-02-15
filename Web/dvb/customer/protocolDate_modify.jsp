<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO,
                 com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=javascript>
	function frmSubmit(url){
		 document.frmPost.action = url;
     document.frmPost.submit();
  }
	function modify_submit(){
		 if (document.frmPost.txtcpID.length!=null){
		 	   for (var i=0;i<document.frmPost.txtcpID.length;i++){
		 	   	  var txtEndDate= document.getElementsByName("txtEndDate"+i)[0];
		 	   	  if (txtEndDate.value ==''){
		 	   	 	    alert("列表中的维护截止日期不能为空！");
		 	   	 	    return ;
		 	   	  }
		 	   }
		 }else{
		 	  var txtEndDate= document.getElementsByName("txtEndDate0")[0];
		 	  if (txtEndDate.value ==''){
		 	      alert("列表中的维护截止日期不能为空！");
		 	      return ;
		 	  }
		 }
		 document.frmPost.submit();
	}
</Script>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
   <TR>
     <TD>
	<table border="0" align="center" cellpadding="0" cellspacing="10">
	   <tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">协议截止日期维护</td>
	    </tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	    <tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	    </tr>
	</table>
	<br>
	<form name="frmPost" method="post" action="protocolDate_modify_op.do">
	 	 <tbl:hiddenparameters pass="txtCustomerID/txtServiceAccountID/txtCPIDs" />
  	 <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
				<tr class="list_head">
	        <td class="list_head" nowrap>PSID</td>
					<td class="list_head" nowrap>产品名称</td>
					<td class="list_head" nowrap>付费帐户</td>
					<td class="list_head" nowrap>产品包</td>
	        <td class="list_head" nowrap>目前截止日期</td>
	        <td class="list_head" nowrap>维护截止日期</td>
				</tr>
	 <%
		  String txtCPIDs =request.getParameter("txtCPIDs");
		  String[] cpIDs =txtCPIDs.split(";");
			for (int i=0;i<cpIDs.length;i++){
			    CustomerProductDTO cpDto =Postern.getCustomerProductDTOByPSID(Integer.parseInt(cpIDs[i]));
	        ProductDTO pDto = Postern.getProductDTOByProductID(cpDto.getProductID());
	        String strProdName = "";
				  if(pDto != null) strProdName = pDto.getProductName();
				  String strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
				  String endTime = WebUtil.TimestampToString(cpDto.getEndTime(),"yyyy-MM-dd");
				  String txtEndDate ="txtEndDate" +i;			
				  String onclickEvent ="MyCalendar.SetDate(this,document.frmPost." + txtEndDate+",'Y')";
				  String txtOldEndDate ="txtOldEndDate" +i;
	 %>
	    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
						<td align="center" ><input type="hidden" name ="txtcpID" value="<%=cpIDs[i]%>" ><%=cpIDs[i]%></td>
						<td align="center" ><%=strProdName%></td>
				    <td align="center" ><%=cpDto.getAccountID()%></td>
				    <td align="center" ><%=strPackName%></td>
				    <td align="center" ><input type="hidden" name ="<%=txtOldEndDate%>" value="<%=endTime%>" ><%=endTime%></td>
				    <td align="center" ><d:datetime name="<%=txtEndDate%>" size="10" myClass="text" match="<%=txtEndDate%>"  onclick="<%=onclickEvent%>" picURL="img/calendar.gif" style="cursor:hand" /></td>
	    </tbl:printcsstr>
	  <%
	    }
	  %>
	  </table>
	</form>
	   </td>
	  </tr>
</table>
<br>
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
		<td>
			<table border="0" cellspacing="0" cellpadding="0">
  			<tr>
  				<bk:canback url="service_account_query_result_by_sa.do" >
            <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
					  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='service_account_query_result_by_sa.do' />')" value="返 回"></td>
					  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          </bk:canback>
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="Submit1" type="button" class="button" value="确 认" onclick="javascript:modify_submit()"></td>
       		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       		<td width="20" ></td>
       </tr>
     </table>
   </td>
 </tr>
</table>
      
