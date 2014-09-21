<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.PaymentRecordDTO ,
                 com.dtv.oss.dto.MethodOfPaymentDTO " %>

 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
       <tr class="list_head">
          <td  class="list_head"  width="10%">��ˮ��</td>
          <td  class="list_head"  width="10%">����ʱ��</td>
          <td  class="list_head"  width="8%">�ʻ���</td>
          <td  class="list_head"  width="10%">���ѷ�ʽ</td>
          <td  class="list_head"  width="10%">��������</td>
          <td  class="list_head"  width="8%">���</td>
          <td  class="list_head"  width="8%">�տ���</td>
          <td  class="list_head"  width="10%">�շѻ���</td>        
          <td  class="list_head"  width="8%">״̬</td>
          <td  class="list_head"  width="8%">�˵���</td>
          <!--<td  class="list_head"  width="10%">������Դ</td>-->
          <td  class="list_head"  width="10%">ȯ��</td>
       </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%
        PaymentRecordDTO paymentRecordDto =(PaymentRecordDTO)pageContext.getAttribute("oneline");
        String opName =Postern.getOperatorNameByID(paymentRecordDto.getOpID());
        if (opName ==null) opName="";
        String orgName = Postern.getOrgNameByID(paymentRecordDto.getOrgID());
        if (orgName == null) orgName = "";
        
          //���ѷ�ʽ
        Map mapBankMop=Postern.getAllMethodOfPayments();
        MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get(String.valueOf(paymentRecordDto.getMopID()));
        String strMopName = null;
        if (dtoMOP!=null) strMopName=dtoMOP.getName();
        if (strMopName==null) strMopName="";

     %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><tbl:writenumber name="oneline" property="SeqNo" digit="9" /></td>
          <td align="center" ><tbl:writedate name="oneline" property="createTime"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
          <td align="center" ><tbl:write name="oneline" property="acctID" /></td>
          <td align="center" ><%=strMopName%></td>
          <td align="center" ><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="oneline:payType" /></td> 
          <td align="center" ><tbl:write name="oneline" property="Amount" /></td>
          <td align="center" ><%=opName%></td>
          <td align="center" ><%=orgName%></td>        
          <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:Status" /></td>
          <td align="center" ><tbl:write name="oneline" property="invoiceNo" /></td>
          <!--<td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:creatingMethod" /></td>-->
          <td align="center" ><tbl:write name="oneline" property="TicketNo" /></td>
       </tbl:printcsstr>
 </lgc:bloop>
</table>