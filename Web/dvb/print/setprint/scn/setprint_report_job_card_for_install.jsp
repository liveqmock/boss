<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO,
				 com.dtv.oss.dto.AddressDTO,
				 com.dtv.oss.dto.CustomerDTO,
				 com.dtv.oss.dto.NewCustomerInfoDTO" %>



<%
    int currentRecordCount=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >  
<TABLE align="center" width="570" height="67">
<TR>
<TD ALIGN="center">
<B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体"></SPAN></B>
</TD>
</TR>
</TABLE>  
<%
	CustServiceInteractionDTO custServiceInteractionDTO = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");

    String csiType=custServiceInteractionDTO.getType()==null?"":custServiceInteractionDTO.getType();
    NewCustomerInfoDTO newCustomerInfoDTO=new NewCustomerInfoDTO() ;
    CustomerDTO customerDTO=new CustomerDTO();
    AddressDTO addressDTO=new AddressDTO();
    System.out.print(csiType+"**************************");
    
    if(csiType.equals("BK")||csiType.equals("OS")){
    	newCustomerInfoDTO=Postern.getNewCustomerInfoDTOByCSIID(custServiceInteractionDTO.getId());
    	pageContext.setAttribute("onecust", newCustomerInfoDTO);
    	addressDTO=Postern.getAddressDtoByID(newCustomerInfoDTO.getFromAddressID());
    }
    	
    if(csiType.equals("BU")||csiType.equals("UO"))	{
    	customerDTO=Postern.getCustomerByID(custServiceInteractionDTO.getCustomerID());
    	pageContext.setAttribute("onecust", customerDTO);
    	addressDTO=Postern.getAddressDtoByID(customerDTO.getAddressID());
    }
    	   
    pageContext.setAttribute("custaddr", addressDTO);
    System.out.print(addressDTO+"addressDTO=================");
    
    int iJobCardID = custServiceInteractionDTO.getReferJobCardID();
    com.dtv.oss.dto.JobCardDTO jcdto = Postern.getJobCardByID(iJobCardID);

    if (jcdto!=null) pageContext.setAttribute("onejc", jcdto);
    //产品
    String strPkgName = "";
    
    java.util.Collection lst = CsrBusinessUtility.getPackagesByCSIID(custServiceInteractionDTO.getId());
    java.util.Iterator it = lst.iterator();
    int i=1;
    while (it.hasNext())
    {
        Integer iId = (Integer)it.next();
        strPkgName = strPkgName + String.valueOf(i++) + ":" + Postern.getPackagenameByID(iId.intValue())+" ";
    }
    //套餐
    String strCampName = "";
    
    java.util.Collection camplst = CsrBusinessUtility.getCampaignsByCSIID(custServiceInteractionDTO.getId());
    java.util.Iterator campIt = camplst.iterator();
    int n=1;
    while (campIt.hasNext())
    {
        Integer iId = (Integer)campIt.next();
        strCampName = strCampName + String.valueOf(n++) + ":" + Postern.getCampaignNameByID(iId.intValue())+" ";
    }
%>    
<TABLE align="center" width="570">
<TR>
<TD width="90">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="300">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><oper:curoper typeName="companyname" /></SPAN>
</TD>
<TD width="180">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
</TR>
</TABLE>
<TABLE align="center" width="570" >
<TR>
<TD width="60">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="80">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:writedate certainDateFlag="NOW" pattern="MM月dd日" /></SPAN>
<!--
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">2005/12</SPAN>
-->
</TD>
<TD width="50">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="60">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="onejc" property="JobCardID"/></SPAN>
</TD>
<TD width="70">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="80">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="onejc" property="workCount"/></SPAN>
</TD>
<TD width="105">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="55">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
</TR>
</TABLE>

<TABLE align="center" width="570"
style="BORDER-RIGHT: medium none; BORDER-TOP: medium none; BORDER-LEFT: medium none; BORDER-BOTTOM: medium none; BORDER-COLLAPSE: collapse; mso-border-alt: solid windowtext .5pt; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt" 
cellSpacing=0 cellPadding=0 border=1>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt" 
    width=100 rowSpan=4>
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=130 >
      <B><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <tbl:write name="onecust" property="Name"/>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt" 
    width=120 >
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: windowtext 0pt solid; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt" 
    width=100 >
      <tbl:write name="onecust" property="CustID"/>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
       <tbl:write name="custaddr" property="detailAddress" />
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
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
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 85.2pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120>
      <d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="onecust:type"   />
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 91.8pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=120>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 78.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=100>
      <d:getcmnname typeName="SET_C_CUSTOPENSOURCETYPE" match="onecust:openSourceType"   />
    </TD>
  </TR>
      
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-top-alt: solid windowtext .5pt" 
    width=100 rowSpan=3>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
    <%=strPkgName%>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <%=strCampName%>
    </TD>
  </TR>
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
    
    </TD>
  </TR>
          
  <TR style="HEIGHT: 30.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 30.4pt; mso-border-top-alt: solid windowtext .5pt" 
    width=100 rowSpan=3>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 30.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 31pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0>
      <TR>
      <TD width="60" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="oneline" property="PreferedDate" pattern="yyyy" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="50" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="oneline" property="PreferedDate" pattern="MM" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="30" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="oneline" property="PreferedDate" pattern="dd" /></SPAN></TD>
      <TD width="40" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD></TD>
      </TR>
      <TR>
      <%
      String strMorning="";
      String strAfernoon="";
      if(custServiceInteractionDTO.getPreferedTime()!=null && !"".equals(custServiceInteractionDTO.getPreferedTime())){
      	if("A".equals(custServiceInteractionDTO.getPreferedTime())){
      		strMorning=new String("\u221A".getBytes());
      	}else if("P".equals(custServiceInteractionDTO.getPreferedTime())){
      		strAfernoon=new String("\u221A".getBytes());
      	} 
      }
      %>
      
      <TD ALIGN="CENTER"  COLSPAN="2">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=strMorning%>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN>
      </TD>
      <TD COLSPAN="4" ALIGN="CENTER">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=strAfernoon%>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN>
      </TD>      
      <TD></TD>
      </TR>
      </TABLE>
    </TD>
  </TR>
  <TR style="HEIGHT: 30.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 30.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 31pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD width="60" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="yyyy" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="50" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="MM" /></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="30" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><tbl:writedate name="onejc" property="PreferedDate" pattern="dd" /></SPAN></TD>
      <TD width="40" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD></TD>
      </TR>
      <%
      String strConfirmMorning="";
      String strConfirmAfernoon="";
      if(jcdto.getPreferedTime()!=null && !"".equals(jcdto.getPreferedTime())){
      	if("A".equals(jcdto.getPreferedTime())){
      		strConfirmMorning=new String("\u221A".getBytes());
      	}else if("P".equals(jcdto.getPreferedTime())){
      		strConfirmAfernoon=new String("\u221A".getBytes());
      	} 
      }
      %>      
      <TR>
      <TD ALIGN="CENTER" COLSPAN="2">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=strConfirmMorning%>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN>
      </TD>
      <TD COLSPAN="4" ALIGN="CENTER">
        <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"><%=strConfirmAfernoon%>&nbsp;&nbsp;&nbsp;&nbsp;</SPAN>
      </TD>      
      <TD></TD>
      </TR>
      </TABLE>
    </TD>
  </TR>
  <TR style="HEIGHT: 30.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 30.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 30.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>      
      <TD><tbl:write name="onejc" property="ContactComments" /></TD>
      </TR>      
      <TR>      
      <TD>&nbsp;</TD>
      </TR>
      </TABLE>
    </TD>
  </TR>
  
  <TR style="HEIGHT: 16.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: windowtext 0pt solid; WIDTH: 77.4pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-top-alt: solid windowtext .5pt" 
    width=100 rowSpan=3>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 16.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD width="80" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="40" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="40" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="40" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="20" ALIGN="RIGHT"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD></TD>
      </TR>
      </TABLE>
      
    </TD>
  </TR>
  <TR style="HEIGHT: 110.4pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 110.4pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 110.4pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD width="220" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="80" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="40" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER">
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER">
      
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>
      <TD ALIGN="CENTER">
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      </TD>
      <TD ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312;HEIGHT: 16.2pt; mso-bidi-font-size: 10.5pt "><br></SPAN></TD>      
      </TR>
      </TABLE>
      
      
    </TD>
  </TR>
  <TR style="HEIGHT: 178.2pt">
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt;   PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 93pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 178.2pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=130>
      <B><SPAN 
      style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></B>
    </TD>
    <TD 
    style="BORDER-RIGHT: windowtext 0pt solid; PADDING-RIGHT: 5.4pt; BORDER-TOP: #d4d0c8; PADDING-LEFT: 5.4pt; PADDING-BOTTOM: 0cm; BORDER-LEFT: #d4d0c8; WIDTH: 255.7pt; PADDING-TOP: 0cm; BORDER-BOTTOM: windowtext 0pt solid; HEIGHT: 178.2pt;   mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" 
    width=340 colSpan=3>
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      <TR>
      <TD><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      </TABLE>
      
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD width="100" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="80" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="80" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>
      <TD width="80" ALIGN="CENTER"><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      </TABLE>
      
      <TABLE cellSpacing=0 cellPadding=0 border=0 >
      <TR>
      <TD><SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN></TD>      
      </TR>
      <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
      <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
       <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
       <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
       <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
       <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      
      <TR>
      <TD>
      <SPAN style="FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 10.5pt"></SPAN>
      </TD>      
      </TR>
      <TR>
      <TD>&nbsp;
      
      </TD>      
      </TR>
      </TABLE>
      
    </TD>
  </TR>
 

</TABLE>
      
<TABLE align="center" width="570">
<TR>
<TD width="448">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
</TD>
<TD width="152">
<SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"></SPAN>
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