<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerBillingRuleDTO" %>

<Script language=JavaScript>
function modifyCustBillingRule(id){
	this.parent.location.href = "menu_modify_customer_billingrule.do?txtCBRID="+id;
}
function add_submit(psid,said,cid){
	this.parent.location.href = "menu_add_customer_billingrule.do?txtPSID="+psid+"&&txtServiceAccountID="+said+"&&txtCustomerID="+cid;
}
</Script>
<%	String psid=request.getParameter("txtPSID");
	String said=request.getParameter("txtServiceAccountID");
	String cid=request.getParameter("txtCustomerID");
%>
<form name="frmPost1" method="post" action="menu_add_billingrule.do" > 		
    <!--<table width="820" border="0" align="center" cellpadding="5" cellspacing="0" class="list_bg">
        <tr> 
          <td width="90%" class="import_tit" align="left"><font size="3">客户产品个性化费率</font></td>
          <td width="8%" class="import_tit" align="left">
          	<pri:authorized name="billing_rule_create.do">
          	<input name="Submit" type="button" class="button" onclick="javascript:add_submit('<%=psid%>','<%=said%>','<%=cid%>')" value="新增" >
          	</pri:authorized>
          </td>
        </tr>
    </table>
    -->
        <rs:hasresultset>
        <table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">        
        <tr class="list_head">      
          <td class="list_head">流水号</td>
          <td class="list_head">创建时间</td>
          <td class="list_head">费用类型</td>
          <td class="list_head">账目类型</td>
          
          <td class="list_head">费率</td>
          <td class="list_head">有效期(起)</td>           
          <td class="list_head">有效期(止)</td>
          <td class="list_head">状态</td>          
        </tr>
        <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%     	CustomerBillingRuleDTO dto = (CustomerBillingRuleDTO)pageContext.getAttribute("oneline");%>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td align="center" >
        	<a href="javascript:modifyCustBillingRule(<tbl:write name="oneline" property="id"/>)"><tbl:write name="oneline" property="id"/></a>
        </td>
        <td align="center" ><tbl:writedate name="oneline" property="dtCreate"/></td>        
        
        <td align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="oneline:feeType" /></td>
        <td align="center" ><%=Postern.getAcctItemTypeByAcctItemTypeID(dto.getAcctItemTypeID())%></td>
        <td align="center" ><tbl:write name="oneline" property="value"/></td>
        <td align="center" ><tbl:writedate name="oneline" property="startDate"/></td>
        <td align="center" ><tbl:writedate name="oneline" property="endDate"/></td>
        <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>        
        </tbl:printcsstr>
        </lgc:bloop>        
				<tr>
					<td colspan="8" class="list_foot"></td>
				</tr>        
   </table>
   </rs:hasresultset>
</form>      
<Script language="JavaScript">
parent.document.all("cbrlist").style.height=document.body.scrollHeight; 
</Script>		 


