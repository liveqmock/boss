<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
    <tr class="list_head">
        <td width="10%" class="list_head">流水号</td>
        <td width="15%" class="list_head">创建时间</td> 
        <td width="10%" class="list_head">帐户号</td>
        <td width="10%" class="list_head">费用类型</td> 
        <td width="15%" class="list_head">账目类型</td>
        <td width="10%" class="list_head">金    额</td>
        <td width="10%" class="list_head">状    态</td>
        <td width="10%" class="list_head">单据号</td>
        <td width="10%" class="list_head">数据来源</td>
     </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%
          AccountItemDTO acctItemDto =(AccountItemDTO)pageContext.getAttribute("oneline");
          AcctItemTypeDTO acctemTypeDto =Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemDto.getAcctItemTypeID());  
     %>

     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
         <td align="center" ><tbl:writenumber name="oneline" property="AiNO" digit="9" /></td>
         <td align="center" ><tbl:writedate name="oneline" property="createTime"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
         <td align="center" ><tbl:write name="oneline" property="acctID" /></td> 
         <td align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="oneline:feeType" /></td>
         <td align="center" ><%=acctemTypeDto.getAcctItemTypeName()%></td>
         <td align="center" ><tbl:write name="oneline" property="value"/></td>
         <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:Status" /></td>
         <td align="center" ><tbl:write name="oneline" property="referID" /></td>
         <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:creatingMethod" /></td>   
     </tbl:printcsstr>
</lgc:bloop>
</table>