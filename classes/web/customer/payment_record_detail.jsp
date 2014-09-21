<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/operator.tld" prefix="oper" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="java.util.*" %>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ�֧����¼</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="">
   <tbl:hiddenparameters pass="txtInvoiceNo" />
   <%
     Map  payMap =Postern.getAllMethodOfPayments();
   %>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="obj" IsOne="true">
   <%
       PaymentRecordDTO dto = (PaymentRecordDTO) pageContext.findAttribute("obj");
       String operatorName =Postern.getOperatorNameByID(dto.getOpID());
       String orgName = Postern.getOrgNameByID(dto.getOrgID());
       if (operatorName == null) operatorName = "";
       if (orgName == null) orgName = "";
       MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(dto.getMopID()));
       String payMopName =paymentDto.getName();
   %>
   <table width="810" border="0" align="center" cellpadding="3" cellspacing="1" class="list_bg">
       <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >��ˮ��</td>
          <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="obj" property="seqNo"/>
          </font></td>
          <td valign="middle" class="list_bg2" align="right" >��������</td>
          <td class="list_bg1"><font size="2">
          <tbl:writedate name="obj"  includeTime="false" property="CreateTime"/>
          </font></td>
         
        </tr>
        <tr>
           <td valign="middle" class="list_bg2" align="right" >״̬</td>
	  <td class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_F_FTSTATUS" match="obj:Status"/>
          </font></td>
          <td valign="middle" class="list_bg2" align="right" >֧������</td>
          <td class="list_bg1"><font size="2">
          <tbl:writedate name="obj"  includeTime="false" property="paymentTime"/>
          </font></td>
         
        </tr>
        
        
        <tr>
	 <td valign="middle" class="list_bg2" align="right" >����Ա</td>
          <td class="list_bg1" ><font size="2"><%=operatorName%></font></td>
          <td valign="middle" class="list_bg2" align="right" >����</td>
          <td class="list_bg1"><font size="2">
          <%=orgName%>
          </font></td>
          </tr>
           <tr>
           <td valign="middle" class="list_bg2" align="right" >�ͻ���</td>
	  <td class="list_bg1"><font size="2">
                <tbl:write name="obj" property="custID"/>
          </font></td>
         
         
            <td valign="middle" class="list_bg2" align="right" >�ʻ���</td>
	  <td class="list_bg1"><font size="2">
                <tbl:write name="obj" property="acctID"/>
          </font></td>
        </tr>
        <tr>
           <td valign="middle" class="list_bg2" align="right" >MOP</td>
	  <td class="list_bg1"><font size="2">
             <%=payMopName%>
          </font></td>
          <td valign="middle" class="list_bg2" align="right" >ȯ����</td>
          <td class="list_bg1"><font size="2">
             <d:getcmnname typeName="SET_F_PAYMENTTICKETTYPE" match="obj:ticketType"/>
            </font></td>
         
         
        </tr>
         <tr>
           <td valign="middle" class="list_bg2" align="right" >֧������</td>
	  <td class="list_bg1"><font size="2">
	    <d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="obj:PayType"/>
              
          </font></td>
         <td valign="middle" class="list_bg2" align="right" >ȯ��</td>
          <td class="list_bg1"><font size="2">
          <tbl:write name="obj" property="ticketNo"/>
          </font></td>
           
        </tr>  
          <tr>
         
          <td valign="middle" class="list_bg2" align="right" >���</td>
          <td class="list_bg1"><font size="2">
          <tbl:write name="obj" property="Amount"/>
          </font></td>
           <td valign="middle" class="list_bg2" align="right" >Ԥ�������</td>
	  <td class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="obj:prepaymentType"/>
          </font></td>
        </tr>
         <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >��������</td>
        <td width="33%" class="list_bg1"><font size="2">
         <d:getcmnname typeName="SET_F_FTREFERTYPE" match="obj:referType" />
             
        </font></td>   
        <td valign="middle" class="list_bg2" align="right" width="17%" >���ݺ�</td>
         <td width="33%" class="list_bg1"><font size="2">
            <tbl:write name="obj" property="referID"/>
         </font></td> 
        </tr>  
          <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >���ʱ��</td>
         <td width="33%" class="list_bg1"><font size="2">
            <d:getcmnname typeName="SET_G_YESNOFLAG" match="obj:invoicedFlag" />
         </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >�ʵ���</td>
	      <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="obj" property="invoiceNo"/>
        </font></td>
        
      </tr> 
         <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >Դ��¼����</td>
	  <td width="33%" class="list_bg1"><font size="2">
	   <d:getcmnname typeName="SET_F_PAYMENTRECORDSOURCETYPE" match="obj:SourceType"/>
              
          </font></td>
        <td valign="middle" class="list_bg2" align="right" width="17%" >Դ��¼��</td>
	  <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="obj" property="SourceRecordID"/>
          </font></td>
	
           
	</tr>
	 
        
      
       </table>
   </lgc:bloop>

      <br>
     <bk:canback url="bill_view.do/payment_record_conditionQuery.do">
      <table align="center" width="50" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>      
           <td background="img/button_bg.gif"  height="20" >
            <a href="<bk:backurl property="bill_view.do/payment_record_conditionQuery.do" />" class="btn12">��&nbsp;��</a></td>
           <td><img src="img/button2_l.gif" width="13" height="20"></td>
        </tr>
      </table>
    </bk:canback> 
</form>