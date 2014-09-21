<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.AccountItemDTO,
                 com.dtv.oss.dto.AcctItemTypeDTO,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.MethodOfPaymentDTO,
                 com.dtv.oss.dto.AccountDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="java.util.*" %>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����軪���ߵ����ʵ�Զ���û�����ϵͳ�շ���Ŀ�嵥</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: ����_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="660" align="center">
	 <tr>
	 	  <td>&nbsp;</td>
	 </tr>
	  <tr>
	 	  <td>&nbsp;</td>
	 </tr>
	 <tr>
	 	  <td>&nbsp;</td>
	 </tr>
   <TR>
      <TD align="center">
          <B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: ����; mso-hansi-font-family: ����">�����軪���ߵ����ʵ�Զ���û�����ϵͳ�շ���Ŀ�嵥</SPAN></B>
      </TD>
   </TR>
</table>

<%
    int acctId =WebUtil.StringToInt(request.getParameter("txtacctID"));
    AccountDTO acctDto =Postern.getAccountDto(acctId);
    CustomerDTO custDto =Postern.getCustomerByID(acctDto.getCustomerID());
    
    //�ͻ���ַ
    AddressDTO custAddressDTO=Postern.getAddressDtoByID(custDto.getAddressID());
 	  String custdistrictdes=Postern.getDistrictDescByID(custAddressDTO.getDistrictID());
 	  if(custdistrictdes==null)custdistrictdes="";
    
    //�ʼ��ʵ���ַ
    AddressDTO acctAddressDto=Postern.getAddressDtoByID(acctDto.getAddressID());
    String accttdistrictdes=Postern.getDistrictDescByID(acctAddressDto.getDistrictID());
 	  if(accttdistrictdes==null)accttdistrictdes="";
%>


<TABLE width="600" align="center" style="border:1 solid black"   cellspacing="1" cellpadding="1" >
   <tr>
      <td>
      	 <table width="100%" cellspacing="1" cellpadding="1" >
                <TR>
                    <td colspan="4" style="border-bottom:1 solid black" align="center">
                      <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">������Ϣ</SPAN>
                    </TD>
                </TR>
                <TR>
                    <TD align="right" width="25%"  style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�û�����</SPAN>
                    </TD>
                    <TD width="25%" style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=custDto.getCustomerID()%>&nbsp;<br></SPAN>
                    </TD>
                    <TD width="25%" align="right"  style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�û�����(�ҷ�)</SPAN>
                    </TD>
                     <TD width="25%" style="border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=custDto.getName()%>&nbsp;<br></SPAN>
                    </TD>
                </TR>
                <TR>
                    <TD width="25%" align="right"  style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�̶��绰</SPAN>
                    </TD>
                     <TD width="25%" style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=(custDto.getTelephone()==null)? "":custDto.getTelephone()%>&nbsp;<br></SPAN>
                    </TD>
                    <TD width="25%" align="right" style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�ƶ��绰</SPAN>
                    </TD>
                     <TD width="25%" style="border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=(custDto.getTelephoneMobile()==null) ? "" :custDto.getTelephoneMobile() %>&nbsp;<br></SPAN>
                    </TD>
                </TR>
                <TR>
                    <TD width="25%" align="right" style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���ӵ�ַ</SPAN>
                    </TD>
                    <TD colspan="3" style="border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=custdistrictdes+custAddressDTO.getDetailAddress()%>&nbsp;<br></SPAN>
                    </TD>
                </TR>
                <TR>
                    <TD width="25%" align="right" style="border-right:1 solid black;border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">ͨ�ŵ�ַ</SPAN>
                    </TD>
                    <TD  colspan="3" style="border-bottom:1 solid black">
                    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=accttdistrictdes+acctAddressDto.getDetailAddress()%>&nbsp;<br></SPAN>
                    </TD>
                </TR>
            </TABLE>
        </td>
    </tr>
 <%
   String txtAcctFeeIDList =request.getParameter("txtAcctFeeIDList");
   Collection acctFeeCols =Postern.getAcctFeeForPrint(txtAcctFeeIDList,0,null,null,1,200);
   if (acctFeeCols !=null && !acctFeeCols.isEmpty()){
      request.setAttribute("ResponseFromEJBEvent",new CommandResponse(acctFeeCols));
%>
  <tr>
     <td>
     	 <table width="100%" cellspacing="1" cellpadding="1" >
          <tr>
             <td colspan="3" style="border-bottom:1 solid black" align="center">
                <SPAN style="FONT-SIZE: 13pt; FONT-FAMILY: ����_GB2312 ">�����嵥</SPAN>
             </TD>
          </tr>
          <tr>
             <TD  style="border-right:1 solid black;border-bottom:1 solid black"  align="center">
               <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">��Ŀ����</SPAN>
             </TD>
             <TD style="border-right:1 solid black;border-bottom:1 solid black"  align="center">
               <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���<br></SPAN>
             </TD>
             <TD style="border-bottom:1 solid black"  align="center">
               <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">������Դ</SPAN>
             </TD>
          </tr>
         <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" >
	       <%
	         AccountItemDTO accountItemDto =(AccountItemDTO)pageContext.getAttribute("oneline");  
           AcctItemTypeDTO  acctItemTypeDto=Postern.getAcctItemTypeDTOByAcctItemTypeID(accountItemDto.getAcctItemTypeID());
           String acctItemTypeName =acctItemTypeDto.getAcctItemTypeName();  
         %> 
         <tr>
           <td align="center" ><%=acctItemTypeName%></td>
           <td align="center" ><tbl:write name="oneline" property="Value" /></td>  
           <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:CreatingMethod" /></td>
         </tr> 
        </lgc:bloop>	
        <tr>
          <td align="center"  colspan="3" style="border-bottom:1 solid black">&nbsp;</td>
        </tr> 
      </table>
    </td>
   </tr>
 <%
    }
 %>
 <tr>
 	  <td>
     <table width="100%" cellspacing="1" cellpadding="1" >
        <tr>
          <td colspan="3" style="border-bottom:1 solid black"  align="center">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">֧���嵥</SPAN>
          </TD>
       </tr>
       <tr>
         <TD  style="border-right:1 solid black;border-bottom:1 solid black"  align="center">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���ѷ�ʽ</SPAN>
         </TD>
         <TD style="border-right:1 solid black;border-bottom:1 solid black" align="center">
             <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���<br></SPAN>
         </TD>
         <TD style="border-bottom:1 solid black" align="center">
             <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">��������</SPAN>
          </TD>
       </tr>
      <%
        String txtAcctPayIDList =request.getParameter("txtAcctPayIDList");
        Collection acctPayCols =Postern.getAcctPaymentRecordForPrint(txtAcctPayIDList,0,null,null,1,200);
        request.setAttribute("ResponseFromEJBEvent",new CommandResponse(acctPayCols));
        double printPaySum =0;
      %>
      <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" >
      <%
         Map  payMap =Postern.getAllMethodOfPayments();
         PaymentRecordDTO dto = (PaymentRecordDTO) pageContext.findAttribute("oneline");
         MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(dto.getMopID()));
         String payMopName =paymentDto!=null?paymentDto.getName():"";
         printPaySum =printPaySum +dto.getAmount();
      %>
      <tr>
        <td align="center"><%=payMopName%></td>
        <td align="center"><tbl:write name="oneline" property="Amount"/></td>
        <td align="center"><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="oneline:payType" /></td>
      </tr> 
      </lgc:bloop>	
      <tr>
    	  <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
    	  <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
    	  <td colspan="3" align="right">�ϼƣ�&nbsp;&nbsp;<%=printPaySum%> &nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
    	  <td colspan="3" align="right">��ӡ���ڣ�&nbsp;&nbsp;<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%> &nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
    </table>
 </td>
</tr>
</table>
 </body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
 
 
                