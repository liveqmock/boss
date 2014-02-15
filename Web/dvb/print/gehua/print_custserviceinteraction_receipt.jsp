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
<title>北京歌华有线用户付费频道订购单</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<body>
<TABLE width="660" align="center" height="40">
    <TR>
        <TD align="center">
        <!--
            <B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">北京歌华有线用户付费频道订购单</SPAN></B>
        -->    
        <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">  <%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"MM")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"dd")%>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</SPAN>
        </TD>
    </TR>
</TABLE>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
 	CustomerDTO custDTO=wrap.getCustDto();
 	pageContext.setAttribute("oneline", custDTO);
 	//客户地址
 	AddressDTO custAddressDTO=wrap.getAddrDto();
	//受理单
    CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("csiID")));
    //帐户
    AccountDTO accDTO=Postern.getAccountDto(csiDTO.getAccountID());
    //邮寄帐单地址
    AddressDTO acctAddressDto=Postern.getAddressDtoByID(accDTO.getAddressID());
    String SCNO="";
    String stbNo="";
    //根据受理单取得本次相关的设备信息
    java.util.Collection clo=Postern.getDeviceIDByCSIID(csiDTO.getId());
    java.util.Iterator it=clo.iterator();
    while(it.hasNext()){
    	TerminalDeviceDTO terminalDeviceDTO=Postern.getTerminalDeviceByID(WebUtil.StringToInt(it.next().toString()));
    	if("SC".equals(terminalDeviceDTO.getDeviceClass())){
    		SCNO=terminalDeviceDTO.getSerialNo();
    	}
    	if ("STB".equals(terminalDeviceDTO.getDeviceClass())){
             stbNo =terminalDeviceDTO.getSerialNo();
        }
    } 
    
    /*
    if(SCNO==null || "".equals(SCNO))
        SCNO=Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"SC");
    if(stbNo==null || "".equals(stbNo))
        stbNo=Postern.getDeviceSerialNoByCSIandClass(csiDTO.getId(),"STB");
     */  
    Map packageAndCampaignMap=Postern.getPackageCampaignMapByCsiID(csiDTO.getId());
    

    
%>
<TABLE width="660" align="center"   cellspacing="1" cellpadding="1" >
<tr>
<td>
<table width="660" cellspacing="1" cellpadding="1" >
<TR height="40">
    <TD width="10" rowspan="4"  >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="right" width="130" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD width="110" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="name"/><br></SPAN>
    </TD>
    <TD align="right" width="70" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD width="135" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephone"/><br></SPAN>
    </TD>
    <TD align="right" width="70" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD width="135" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="telephoneMobile"/><br></SPAN>
    </TD>
</TR>
<TR height="40">
    <TD align="right" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="left" colspan="2">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=stbNo%><br></SPAN>
    </TD>
    <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD colspan="2" align="left" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=SCNO%><br></SPAN>
    </TD>
</TR>
<TR height="40">
    <TD align="right" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=custAddressDTO.getDetailAddress() %><br></SPAN>
    </TD>
    <TD align="right" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR height="40">
    <TD align="right" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=acctAddressDto.getDetailAddress()%><br></SPAN>
    </TD>
    <TD align="right" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><tbl:write name="oneline" property="CustomerID"/><br></SPAN>
    </TD>
</TR>
</TABLE>
</td>
</tr>
<tr>
<td>
<TABLE align="center" width="660">
<TR>
    <TD align="center" width="10" rowspan="17">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  align="center" width="210"  >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD  align="center" width="130"  >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <TD align="center" width="130" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD align="center" width="180"  >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD height="400" width="650" colspan="4">
        <TABLE height="300" width="650" valign="top" cellspacing="0" cellpadding="3" >
        <%
        Iterator mapIter=packageAndCampaignMap.keySet().iterator();
		while(mapIter.hasNext()){
			Integer packageID=(Integer)mapIter.next();
			Integer campaignID=(Integer)packageAndCampaignMap.get(packageID);
			String packageName=Postern.getPackagenameByID(packageID.intValue());
		%>
		<TR>
            <TD height="10"  align="center" width="210" >
            <SPAN align="center" width="210" style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=packageName%></SPAN>
            </TD>
		<%	
			if(campaignID.intValue()!=0){
				CampaignDTO campaignDTO=Postern.getCampaignDTOByCampaignID(campaignID.intValue());
				String description=campaignDTO.getDescription();
				if(description==null)description="";
		%>
			<TD height="10"  align="center" width="130" >
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%= description%></SPAN>
            </TD>
            <TD height="10" align="center" width="130">
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=campaignDTO.getTimeLengthUnitNumber()+Postern.getCommonSettingDataValueByNameAndKey("SET_M_CAMPAIGNTIMELENGTHUNITTYPE", campaignDTO.getTimeLengthUnitType())%></SPAN>
            </TD>
            <TD height="10"  align="center" width="180" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=TimestampUtility.getSystemDate(csiDTO.getCreateTime(),2)%></SPAN>
            </TD>	
		<%		
			}else{
		%>
	        <TD height="10"  align="center" width="130" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR></SPAN>
            </TD>
            <TD height="10" align="center" width="130">
            <SPAN   style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR></SPAN>
            </TD>
            <TD height="10" align="center" width="180" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=TimestampUtility.getSystemDate(csiDTO.getCreateTime(),2)%></SPAN>
            </TD>
		<%	
		}
		%>
	    </TR>	
		<%
		}
        %>
        <TR>
            <TD  align="center" width="210" >
            <SPAN  style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="130">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="130">
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR></SPAN>
            </TD>
            <TD align="center" width="180" >
            <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><BR<BR></SPAN>
            </TD>
         </TR>
        </TABLE>
    <TD>
</TR>
<TR>
    <TD align="right" colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
    <%
       String value=Postern.getSumFeeValue(csiDTO.getId());
       if(value==null) value="0.00";
       if(value.indexOf(".")==-1) value=value+".00";
    %>
    <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><%=value%><br></SPAN>
    </TD>
</TR>
<!--
<TR>
    <TD align="right"  colspan="3"  >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">签字确认（请仔细阅读本申请表及背面所附协议，并予以签字确认）</SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">本人及代办人（如有）已认真阅读并完全认可本订购单、背面协议，自愿承担相应责任。</SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right" colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">用户或代办人签字：</SPAN>
    </TD>
     <TD colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</SPAN>
    </TD>
</TR>
<TR>
    <TD align="center" colspan="4">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">受理单位：北京歌华有线电视网络股份有限公司</SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD align="right"  colspan="3" >
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"> 经办人：</SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
</TR>
<TR>
    <TD colspan="3">
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312"><br></SPAN>
    </TD>
     <TD>
    <SPAN style="FONT-SIZE: 12pt; FONT-FAMILY: 楷体_GB2312">&nbsp&nbsp&nbsp年&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</SPAN>
    </TD>
</TR>
-->
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
      
