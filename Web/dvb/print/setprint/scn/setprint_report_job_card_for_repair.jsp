<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO,
					com.dtv.oss.dto.JobCardDTO,
					com.dtv.oss.dto.JobCardProcessDTO,
					com.dtv.oss.dto.TerminalDeviceDTO,
					com.dtv.oss.dto.CustomerDTO" %>

<%
    int currentRecordCount=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
<TABLE align="center" width="570">
<TR>
<TD ALIGN="center">
<B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">&nbsp;</SPAN></B>
</TD>
</TR>
</TABLE>   
<%
	com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
	JobCardDTO jcdto=wrap.getJobCardDto();
	if (jcdto!=null) pageContext.setAttribute("onejc", jcdto);
    pageContext.setAttribute("process", wrap.getJobCardProcessDto());
	int iJobCardID = jcdto.getJobCardId();
    int customerid=jcdto.getCustId();
    CustomerDTO customerDTO=Postern.getCustomerByID(customerid);
    int addressID=jcdto.getAddressId();
    String detailAddress=Postern.getAddressById(addressID);
    if(detailAddress==null){
    	detailAddress="";
    }
   
    if (jcdto!=null) pageContext.setAttribute("onecust", customerDTO);
    CustomerProblemDTO customerProblemDTO=Postern.getCustomerProblemDto(jcdto.getReferSheetId());
    if (jcdto!=null) pageContext.setAttribute("custProblem", customerProblemDTO);
    String deviceclass=Postern.getDeviceByCustServiceAccountID(customerProblemDTO.getCustServiceAccountID(),"deviceclass");
    String devicemodel=Postern.getDeviceByCustServiceAccountID(customerProblemDTO.getCustServiceAccountID(),"devicemodel");
 
%>  
<TABLE align="center" width="570">
<TR>
<TD width="90">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
</TD>
<TD width="300">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="180">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp;<%=iJobCardID%></SPAN>
</TD>
</TR>
</TABLE>
<TABLE align="center" width="570" >
	<TR>
		<TD width="70">
		<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
		</TD>
		<TD width="500">
			<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:writedate certainDateFlag="NOW" /></SPAN>
		</TD>
	</TR>
</TABLE>
<TABLE align="center" width="570"
style="BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; BORDER-BOTTOM: medium none; BORDER-COLLAPSE: collapse; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt" 
cellSpacing=0 cellPadding=0 border=0>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=5>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120>
      <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="onecust:customerType"   />
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=100 >
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <tbl:writedate name="custProblem" property="CreateDate"/>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
       <tbl:write name="onecust" property="CustomerID"/>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <tbl:write name="onejc" property="ContactPhone"/>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <tbl:write name="onecust" property="Name"/>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
       <%=detailAddress%>
    </TD>
  </TR>
    <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 >
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120><%=deviceclass%>
      
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=100 nowrap><%=devicemodel%>
    
    </TD>
  </TR>
    <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=3>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=100 >
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
       <tbl:write name="custProblem" property="Id"/>
    </TD>
  </TR>   
  <TR style="HEIGHT: 32.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
       <tbl:write name="custProblem" property="ProblemDesc"/>
    </TD>
  </TR>
    <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=3>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
    </TD>
  </TR>      
   <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
    </TD>
  </TR>       
  <TR style="HEIGHT: 32.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
    </TD>
  </TR> 
    <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=3>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;<br>&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120>
    <tbl:write name="process" property="currentOperatorId"/>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=100 >
    <d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="onejc:preferedTime"   />
    </TD>
  </TR>      
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <%
      String phoneSuppert="";
      String doorSuppert="";
      String notConnect="";
      String contactResult=Postern.getContactResultByJobCardID(jcdto.getJobCardId());
      if(contactResult!=null &&!"".equals(contactResult)&&!"null".equals(contactResult)){
      	if("G".equals(contactResult)){
      		phoneSuppert=new String("\u221A".getBytes());
      	}else if("R".equals(contactResult)){ 
      		doorSuppert=new String("\u221A".getBytes());
      	}else if("C".equals(contactResult)){ 
      		notConnect=new String("\u221A".getBytes());
      	}
      }
      %>
	      <TR>
	      <TD width="135" ALIGN="left"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=phoneSuppert%></SPAN></TD>
	      <TD width="95" ALIGN="left"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=doorSuppert%></SPAN></TD>
	      <TD width="110" ALIGN="left"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=notConnect%></SPAN></TD>
	      </TR>
      </TABLE>
    </TD>
  </TR>       
  <TR style="HEIGHT: 32.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 32.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0>
      <TR>
      <TD width="80" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="yyyy" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>
      <TD width="50" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="MM" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>
      <TD width="30" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="dd" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>
      <TD></TD>
      </TR>
      <TR>
      <%
      String strMorning="";
      String strAfernoon="";
      if(jcdto.getPreferedTime()!=null && !"".equals(jcdto.getPreferedTime())){
      	if("A".equals(jcdto.getPreferedTime())){
      		strMorning=new String("\u221A".getBytes());
      	}else if("P".equals(jcdto.getPreferedTime())){
      		strAfernoon=new String("\u221A".getBytes());
      	} 
      }
      %>
      
      <TD ALIGN="CENTER"  COLSPAN="2">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">   <br></SPAN>
      </TD>
      <TD COLSPAN="4" ALIGN="CENTER">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">   </SPAN>
      </TD>      
      <TD></TD>
      </TR>
      </TABLE>
    </TD>
  </TR>
    <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=7>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;<br>&nbsp;<br>&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 ><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="process" property="processMan"/></SPAN>
    </TD>
  </TR>
  <TR>
  	<TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 ><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
    </TD>
  </TR>
  <TR>
  	<TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 >
    </TD>
  </TR>
  <TR>
  	<TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 ><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">
    <d:getcmnname typeName="SET_W_ JOBCARDCONTACTRESULT" match="process:workResult"   />
    </SPAN>
    </TD>
  </TR>
  <TR>
  	<TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 >
    </TD>
  </TR>
    <TR style="HEIGHT: 110.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 110.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 110.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD width="220" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>
      <TD width="80" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>
      <TD width="40" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN>
      </TD>
      <TD ALIGN="CENTER">
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN>
      </TD>
      <TD ALIGN="CENTER">
      
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></TD>      
      </TR>
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312;HEIGHT: 16.2pt; mso-bidi-font-size: 10.5pt "><br></SPAN></TD>      
      </TR>
      </TABLE>
    </TD>
  </TR>
  <TR>
  	<TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt">&nbsp;</SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colspan=3 >
    </TD>
  </TR>
</TABLE>    
<TABLE align="center" width="570">
<TR>
<TD width="448">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
</TD>
<TD width="152">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp;</SPAN>
</TD>
</TR>
</TABLE>
<%
	currentRecordCount=currentRecordCount+1;
%>
 <rs:isNoTheLastRecord   itemCount="<%=currentRecordCount%>">	
<p STYLE="page-break-after: always">&nbsp;</p>
</rs:isNoTheLastRecord>     
</lgc:bloop>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>