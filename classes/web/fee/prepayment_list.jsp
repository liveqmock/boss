<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO" %>
 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
       <tr class="list_head">                
          <td class="list_head"   width="10%">��ˮ��</td>
          <td class="list_head"   width="10%">����ʱ��</td>
          <td  class="list_head"  width="10%">�ʻ���</td>
          <td class="list_head"   width="10%">Ԥ������</td>         
          <td class="list_head"   width="10%">���</td>
          <td class="list_head"   width="10%">״̬</td>
          <td class="list_head"   width="10%">�˵���</td>
          <td class="list_head"   width="10%">�ֿ۷�ʽ</td>
          <td class="list_head"   width="10%">�ֿ۶���</td>
          <td class="list_head"   width="10%">��Դ����</td>
        </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)pageContext.getAttribute("oneline");
    String  referType = dto.getReferRecordType();
    int  referId =dto.getReferRecordId();	
%>				    	                 
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      <td align="center" ><tbl:writenumber name="oneline" property="referRecordId" digit="9" /></td>
      <td align="center" ><tbl:writedate name="oneline" property="CreateTime" /><br><tbl:writedate name="oneline" property="CreateTime" onlyTime="true"/></td>
      <td align="center" ><tbl:write name="oneline" property="acctId" /></td>
      <td align="center" ><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="oneline:prepaymentType" /></td>	
      <td align="center" ><tbl:write name="oneline" property="amount" /></td>
      <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:status" /></td>	
      <td align="center" ><tbl:write name="oneline" property="invoiceNo" /></td>	
      <td align="center" ><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="oneline:referRecordType" /></td>	
      <td align="center" ><tbl:write name="oneline" property="referRecordId" /></td>	
      <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:creatingMethod" /></td>
    </tbl:printcsstr>

</lgc:bloop>

</table>