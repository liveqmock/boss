<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
         com.dtv.oss.dto.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.util.Postern,
                 com.dtv.oss.util.TimestampUtility "%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>北京歌华有线电视帐单</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="700" align="center" height="520">
    <TR><TD align="center"><br></TD></TR>
</TABLE>
	
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
 	  CustomerDTO custDTO=wrap.getCustDto();
 	  pageContext.setAttribute("oneline", custDTO);
 	  //客户地址
   	AddressDTO custAddressDTO=wrap.getAddrDto();
 	  //帐户
 	  int txtAccountID =Integer.parseInt(request.getParameter("txtAccountID"));
    AccountDTO accDTO=Postern.getAccountDto(txtAccountID);
  
    String bankAccount=accDTO.getBankAccount();
    if(bankAccount==null)bankAccount="";
  
    String bankAccountName=accDTO.getBankAccountName();
    if(bankAccountName==null)bankAccountName="";
    	
    String detailAddress = WebUtil.NullToString(custAddressDTO.getDetailAddress());
    String distDesc = Postern.getDistrictDescByID(custAddressDTO.getDistrictID());
	  if(distDesc == null)distDesc="";
	  if(detailAddress == null)detailAddress="";
	  detailAddress =distDesc +detailAddress;
	  
	  String customerSerialNo =(custDTO.getCustomerSerialNo() ==null) ? "" : custDTO.getCustomerSerialNo();  
%>
<TABLE width="725" align="center"  cellspacing="1" cellpadding="1" >
	<TR>
    <TD align="right" width="155">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD width="220">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="right" width="75" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    <TD width="275">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=customerSerialNo%>&nbsp;<br></SPAN>
    </TD>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="customerID"/><br></SPAN>
    </TD>
   
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="name"/><br></SPAN>
    </TD>
    <TD align="right" height="23" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephone"/><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="cardID"/><br></SPAN>
    </TD>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephoneMobile"/><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD colspan="4"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=detailAddress%><br></SPAN>
    </TD>
</TR>

<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=bankAccount%><br></SPAN>
    </TD>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=bankAccountName%><br></SPAN>
    </TD>
</TR>

<TR>
    <TD align="right"  height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="right" height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>

<TR>
    <TD align="right"  height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="right" height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="21">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%><br></SPAN>
    </TD>
</TR>

<TR>
    <TD align="right" colspan="5"  height="108" ><br></TD>
</TR>
<TR>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=customerSerialNo%>&nbsp;<br></SPAN>
    </TD>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="customerID"/><br></SPAN>
    </TD>
   
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="name"/><br></SPAN>
    </TD>
    <TD align="right" height="23" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephone"/><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="cardID"/><br></SPAN>
    </TD>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD   height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephoneMobile"/><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD colspan="4"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=detailAddress%><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=bankAccount%><br></SPAN>
    </TD>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=bankAccountName%><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="right" height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD height="23">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%><br></SPAN>
    </TD>
</TR>
</TABLE>
</lgc:bloop> 
</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
      
