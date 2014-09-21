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
                 com.dtv.oss.util.TimestampUtility"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户缴费卡打印</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="500" align="center" height="280">
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
  String portNumber=Postern.getMarketInfoInfoSettingValue(custDTO.getCustomerID());
  if(portNumber==null)portNumber="0";
  
    String detailAddress = WebUtil.NullToString(custAddressDTO.getDetailAddress());
    String distDesc = Postern.getDistrictDescByID(custAddressDTO.getDistrictID());
    if(distDesc == null)distDesc="";
	if(detailAddress == null)detailAddress="";
	System.out.println("detailAddress.length()========"+detailAddress.length());
    detailAddress =distDesc+detailAddress;
  
%>
<TABLE width="500" align="left"  cellspacing="1" cellpadding="1" border="0">
<TR>
    <TD align="left" width="130"  height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	客&nbsp;&nbsp;户&nbsp;&nbsp;证&nbsp;&nbsp;号:
    	<br>
     </SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
     	<tbl:write name="oneline" property="customerID"/>
    </SPAN>
    </TD>
</TR>

<TR>
    <TD align="left" width="130" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	<tbl:write name="oneline" property="name"/>
     </SPAN>
    </TD>
</TR>
<TR>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <%if (detailAddress.length() <=18){ %>
       <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	   <%=detailAddress%>
       </SPAN>
    <%} else { %>
    	 <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	   <%=detailAddress.substring(0,18)%>
       </SPAN>
    <%
      }
    %>
      
    </TD>
</TR>
<TR>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <%if (detailAddress.length() <=18){ %>
        <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    <%} else { %>
    	  <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=detailAddress.substring(18,detailAddress.length())%><br></SPAN>
    <%} %>   	
    </TD>
</TR>
<TR>
    <TD align="left"  height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	<%=portNumber%>终端
    </SPAN>
    </TD>
</TR>
<TR>
    <TD align="left"  height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"MM")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"dd")%>&nbsp
    </SPAN>
    </TD>
</TR>
<TR>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	<%=WebUtil.TimestampToString(custDTO.getCreateTime(),"yyyy")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(custDTO.getCreateTime(),"MM")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(custDTO.getCreateTime(),"dd")%>&nbsp
    </SPAN>
    </TD>
</TR>
<TR>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    	<tbl:write name="oneline" property="catvID"/>
    </SPAN>
    </TD>
</TR>
<TR>
	<TD align="left" colspan="2" height="27">
	<TABLE width="100%"  cellspacing="1" cellpadding="1" border="0">
	<tr>
	 <TD align="left" height="27">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">
    银行户名:<%=bankAccountName%>&nbsp;&nbsp;&nbsp;&nbsp;
    银行帐号:<%=bankAccount%>
    </SPAN>
    </TD> 
  </tr>
  </table>
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
      
