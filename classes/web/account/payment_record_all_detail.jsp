<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%@ page import="com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.AddressDTO "%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>

<br>

<form name="frmPost" method="post" action="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">֧����¼��ϸ��Ϣ</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >

  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
  <%
       PaymentRecordDTO dto = (PaymentRecordDTO)pageContext.getAttribute("oneline");
       pageContext.setAttribute("DTO",dto);
    
       String strOpName="";
       strOpName=Postern.getOperatorNameByID(dto.getOpID());
       if(strOpName==null)
    	   strOpName="";
    	   
       String strCustName="";
       strCustName=Postern.getCustomerNameByID(dto.getCustID());
       if(strCustName==null)
       	   strCustName="";
       
       CustomerDTO custDTO =Postern.getCustomerByID(dto.getCustID());
       AddressDTO addrDTO =null;
       if (custDTO !=null)  addrDTO =Postern.getAddressDtoByID(custDTO.getAddressID());	   
       if (addrDTO ==null) addrDTO =new AddressDTO();
       pageContext.setAttribute("AddrDTO",addrDTO);	
       	   
              
       //���ѷ�ʽ
       Map mapBankMop=Postern.getAllMethodOfPayments();
       MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get("" + dto.getMopID());
       String strMopName = null;
       if (dtoMOP!=null) strMopName=dtoMOP.getName();
       if (strMopName==null) strMopName="";
  %>
  <tr>
    <td class="list_bg2"><div align="right">��ˮ��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="seqNo"/></td>
    <td class="list_bg2"><div align="right">���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="amount"/></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="custID"/></td>
    <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1"><%=strCustName %></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ʻ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="acctID"/></td>
    <td class="list_bg2"><div align="right">����ʱ��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="createTime" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">�շѻ���</div></td>
    <td class="list_bg1"><tbl:WriteOrganizationInfo name="DTO" property="orgID" /></td>    
    <td class="list_bg2"><div align="right">�շ���</div></td>
    <td class="list_bg1"><%=strOpName%></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">���ѷ�ʽ</div></td>
    <td class="list_bg1"><%=strMopName%></td>
    <td class="list_bg2"><div align="right">֧�����</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="DTO:payType" /></td>
  </tr>
 
  <tr>
    <td class="list_bg2"><div align="right">����ȯ����</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PAYMENTTICKETTYPE" match="DTO:ticketType" /></td>
    <td class="list_bg2"><div align="right">Ʊ�ݱ��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="ticketNo" /></td>
  </tr>
 <tr>
    <td class="list_bg2"><div align="right">������������</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTREFERTYPE" match="DTO:referType" /></td>
    <td class="list_bg2"><div align="right">�������ݺ�</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="referID" /></td>
 </tr>
 
  <tr>
    <td class="list_bg2"><div align="right">Դ��¼����</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PAYMENTRECORDSOURCETYPE" match="DTO:sourceType" /></td>
    <td class="list_bg2"><div align="right">Դ��¼��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="sourceRecordID" /></td>
  </tr>
   <tr>
    <td class="list_bg2"><div align="right">���ʱ�־</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:invoicedFlag" /></td>
    <td class="list_bg2"><div align="right">�ʵ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="invoiceNo" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">���ʱ�־</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:adjustmentFlag" /></td>
    <td class="list_bg2"><div align="right">���ʵ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="adjustmentNo" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">������Դ</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">ʵ�ʸ���ʱ��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="paymentTime" /></td>
    <td class="list_bg2"><div align="right">Ԥ�������</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="DTO:prepaymentType" /></td>
  </tr>
 <tr>
    <td class="list_bg2"><div align="right">֧������ID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="paidTo" /></td>    
    <td class="list_bg2"><div align="right">�ͻ���������</div></td>
    <td class="list_bg1">
      <tbl:WriteDistrictInfo name="AddrDTO" property="districtID" />
    </td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">��ע </div></td>
    <td class="list_bg1" colspan="3">
      <tbl:write  name="DTO" property="comments" />
    </td>
  </tr>
  </lgc:bloop>
 </table>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
    <bk:canback url="payment_record_all_query.do/payment_record_conditionQuery.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="payment_record_all_query.do/payment_record_conditionQuery.do" />" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
    </bk:canback> 
</tr>
</table>     
 
 </form>