<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
         com.dtv.oss.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>�����軪���ߵ����ʵ�</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: ����_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="660" align="center">
    <TR>
        <TD align="center">
            <B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: ����; mso-hansi-font-family: ����">�����軪���ߵ����ʵ�</SPAN></B>
        </TD>
    </TR>
</TABLE>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
 	CustomerDTO custDTO=wrap.getCustDto();
 	pageContext.setAttribute("oneline", custDTO);
 	//�ͻ���ַ
 	AddressDTO custAddressDTO=wrap.getAddrDto();
	//����
    CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("csiID")));
    //�ʻ�
    AccountDTO accDTO=Postern.getAccountDto(csiDTO.getAccountID());
    //�ʼ��ʵ���ַ
    AddressDTO acctAddressDto=Postern.getAddressDtoByID(accDTO.getAddressID());
    String SCNO="";
    //��������ȡ�ñ�����ص��豸��Ϣ
    java.util.Collection clo=Postern.getDeviceIDByCSIID(csiDTO.getId());
    java.util.Iterator it=clo.iterator();
    while(it.hasNext()){
    	TerminalDeviceDTO terminalDeviceDTO=Postern.getTerminalDeviceByID(WebUtil.StringToInt(it.next().toString()));
    	if("SC".equals(terminalDeviceDTO.getDeviceClass())){
    		SCNO=terminalDeviceDTO.getSerialNo();
    	}
    } 
    /*
    if(SCNO==null || "".equals(SCNO))
        SCNO=Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"SC");
    if(SCNO==null || "".equals(SCNO))
        SCNO="";
    */
    Map packageAndCampaignMap=Postern.getPackageCampaignMapByCsiID(csiDTO.getId());
    

    
%>
<TABLE width="660" align="center" style="border:1 solid black"   cellspacing="1" cellpadding="1" >
<tr>
<td>
<table width="660" cellspacing="1" cellpadding="1" >
<TR>
    <TD width="10" rowspan="4"  style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">������Ϣ</SPAN>
    </TD>
    <TD align="right" width="130" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�û�����(�ҷ�)</SPAN>
    </TD>
    <TD width="110" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><tbl:write name="oneline" property="name"/><br></SPAN>
    </TD>
    <TD align="right" width="70" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�̶��绰</SPAN>
    </TD>
     <TD width="135" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><tbl:write name="oneline" property="telephone"/><br></SPAN>
    </TD>
    <TD align="right" width="70" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�ƶ��绰</SPAN>
    </TD>
     <TD width="135" style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><tbl:write name="oneline" property="telephoneMobile"/><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���ܿ�����</SPAN>
    </TD>
    <TD colspan="5" style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=SCNO%><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���ӵ�ַ</SPAN>
    </TD>
    <TD colspan="3" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=custAddressDTO.getDetailAddress() %><br></SPAN>
    </TD>
    <TD align="right" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���</SPAN>
    </TD>
     <TD style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">ͨ�ŵ�ַ</SPAN>
    </TD>
    <TD  colspan="3" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=acctAddressDto.getDetailAddress()%><br></SPAN>
    </TD>
    <TD align="right" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�û�����</SPAN>
    </TD>
    <TD style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><tbl:write name="oneline" property="CustomerID"/><br></SPAN>
    </TD>
</TR>
</TABLE>
</td>
</tr>
<tr>
<td>
<TABLE align="center" width="660">
<TR>
    <TD align="center" width="10" rowspan="17" style="border-right:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">֧����Ϣ</SPAN>
    </TD>
    <TD  align="center" width="210"  style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">֧����ʽ</SPAN>
    </TD>
    <TD  align="center" width="130"  style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
    <TD align="center" width="130" style="border-right:1 solid black;border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���</SPAN>
    </TD>
     <TD align="center" width="180"  style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">֧������</SPAN>
    </TD>
</TR>
<TR>
    <TD height="400" width="650" colspan="4">
        <TABLE height="300" width="650" valign="top" cellspacing="0" cellpadding="3" >
        <%
        Collection paymentCol=Postern.getAllCsiPaymentListByCsiAndType(csiDTO.getId());
        if(paymentCol!=null && !paymentCol.isEmpty()){
        Iterator paymentIter=paymentCol.iterator();
		while(paymentIter.hasNext()){
			PaymentRecordDTO dto=(PaymentRecordDTO)paymentIter.next();
			String payName=Postern.getNameByMopIDForPay(dto.getMopID());
			
		%>
		<TR>
            <TD height="10"  align="center" width="210" >
            <SPAN align="center" width="210" style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=payName%></SPAN>
            </TD>
	        <TD height="10"  align="center" width="130" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><BR></SPAN>
            </TD>
            <TD height="10" align="center" width="130">
            <SPAN   style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=dto.getAmount()%><BR></SPAN>
            </TD>
            <TD height="10" align="center" width="180" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=TimestampUtility.getSystemDate(0)%><BR></SPAN>
            </TD>
	    </TR>	
	    <%}
	    
	    }%>
        <TR>
            <TD  align="center" width="210" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="130">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="130">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="180" >
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><BR<BR></SPAN>
            </TD>
         </TR>
        </TABLE>
    <TD>
</TR>
<TR>
    <TD align="right" colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�ܽ�</SPAN>
    </TD>
    <%
       String value=Postern.getSumPaymentValue(csiDTO.getId());
       if(value==null) value="0.00";
       if(value.indexOf(".")==-1) value=value+".00";
    %>
    <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><%=value%><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3"  style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4" style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">ǩ��ȷ�ϣ�����ϸ�Ķ����������������Э�飬������ǩ��ȷ�ϣ�</SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">���˼������ˣ����У��������Ķ�����ȫ�Ͽɱ�������������Э�飬��Ը�е���Ӧ���Ρ�</SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">�û��������ǩ�֣�</SPAN>
    </TD>
     <TD colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD colspan="3" style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD style="border-bottom:1 solid black">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��</SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">����λ�������軪���ߵ�������ɷ����޹�˾</SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"> �����ˣ�</SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD colspan="3">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: ����_GB2312">&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp��&nbsp&nbsp&nbsp&nbsp��</SPAN>
    </TD>
</TR>
</TABLE>
</td>
</tr>
</table>
</lgc:bloop> 
</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
      
