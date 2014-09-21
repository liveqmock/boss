<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO,
					com.dtv.oss.dto.AddressDTO,
					com.dtv.oss.dto.TerminalDeviceDTO,
					com.dtv.oss.dto.CustServiceInteractionDTO,
					com.dtv.oss.dto.MethodOfPaymentDTO,
					com.dtv.oss.dto.AccountDTO,
					com.dtv.oss.dto.CustomerDTO" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }


-->
</style>
</head>

<body>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
 <%
 	String ss=new String("\u221A".getBytes());
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
 	CustomerDTO custDTO=wrap.getCustDto();
 	pageContext.setAttribute("oneline", custDTO);
 	AddressDTO addrDTO=wrap.getAddrDto();
    pageContext.setAttribute("custaddr",  addrDTO);
    String addrname=Postern.getDistrictNameByID(addrDTO.getDistrictID());
	if(addrname==null||addrname.equals("null"))
		addrname="";
	//受理单
    CustServiceInteractionDTO csiDTO=Postern.getCsiDTOByCSIID(WebUtil.StringToInt(request.getParameter("csiID")));
    pageContext.setAttribute("csi",  csiDTO);
    //帐户
    AccountDTO accDTO=Postern.getAccountDto(csiDTO.getAccountID());
    if(accDTO!=null)
    pageContext.setAttribute("account",  accDTO);  
    //付费方式
  	Map mapBankMop = Postern.getAllMethodOfPayments();
	  MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO) mapBankMop.get(String.valueOf(accDTO.getMopID()));
	  String strMopName = null;
	  if (dtoMOP != null)
		  strMopName = dtoMOP.getName();
	  if (strMopName == null)
		  strMopName = "";
    //邮寄帐单地址
    AddressDTO addressDto=Postern.getAddressDtoByID(accDTO.getAddressID());
    pageContext.setAttribute("accountaddress",  addressDto);
    String accAddrname=Postern.getDistrictNameByID(addressDto.getDistrictID());
    if(accAddrname==null||accAddrname.equals("null"))
    	accAddrname="";
    
    String SCDeviceModel="",SCNO="",STBDeviceModel="",STBNO="";
    java.util.Collection clo=Postern.getDeviceIDByCSIID(csiDTO.getId());
    
    java.util.Iterator it=clo.iterator();
    String flagY="",flagN="";
    while(it.hasNext()){
    	TerminalDeviceDTO terminalDeviceDTO=Postern.getTerminalDeviceByID(WebUtil.StringToInt(it.next().toString()));
    	
    	if("".equals(terminalDeviceDTO.getMatchFlag())){
    		flagY=ss;
    	}else{
    		flagN=ss;
    	}
    	if("SC".equals(terminalDeviceDTO.getDeviceClass())){
    		SCDeviceModel=terminalDeviceDTO.getDeviceModel();
    		SCNO=terminalDeviceDTO.getSerialNo();
    	}
    		
    	if("STB".equals(terminalDeviceDTO.getDeviceClass())){
    		STBDeviceModel=terminalDeviceDTO.getDeviceModel();
    		STBNO=terminalDeviceDTO.getSerialNo();
    	}
    }

		//loud受理原因作为整转/购买的显示
    String csiReasonDes =Postern.getCsiReasonDesByCon(csiDTO.getType(),"N",csiDTO.getCreateReason());
    if(csiReasonDes == null)csiReasonDes="";
    
    
%>
<div align="center">
  <table width="645" height="22" cellspacing="0">
    <tr>
      <td height="45" colspan="5" class="STYLE2"><div align="center" >&nbsp;</div></td>
    </tr>
    <tr>
      <td width="63" height="25" class="STYLE2">&nbsp;</td>
      <td width="149" class="STYLE2"><span class="STYLE2">
        <tbl:write name="oneline" property="CustomerID"/>
      </span></td>
      <td width="241" class="STYLE2"><table width="200" border="0" cellspacing="0">
        <tr>
          <td width="48" height="25" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="yyyy" /></div></td>
          <td width="14" class="STYLE2">&nbsp;</td>
          <td width="28" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="MM" /></div></td>
          <td width="15" class="STYLE2">&nbsp;</td>
          <td width="25" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="dd" /></div></td>
          <td width="58" class="STYLE2">&nbsp;</td>
        </tr>
      </table>        </td>
      <td width="62" class="STYLE2">&nbsp;</td>
      <td width="118" class="STYLE2">&nbsp;</td>
    </tr>
  </table>
  <table width="645" cellspacing="0">
    <tr>
      <td height="25" colspan="5"><div align="center" class="STYLE2">&nbsp;</div></td>
      </tr>
    <tr>
      <td width="66" rowspan="7" class="STYLE2">&nbsp;</td>
      <td width="61" height="27" class="STYLE2">&nbsp;</td>
      <td width="320" class="STYLE2"><span class="STYLE2">
        <tbl:write name="oneline" property="name"/></span></td>
      <td colspan="2" class="STYLE2"><div align="center" class="STYLE2">
        <table width="97" height="25" border="0" cellspacing="0">
          <tr >
            <td height="25" class="STYLE2" ><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
			 </tr>
        </table>
      </div></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <tbl:write name="oneline" property="telephone"/></span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        <tbl:write name="oneline" property="telephoneMobile"/>
      </span></td>
      </tr>
    <tr>
      <td height="29" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><table width="283" height="25" cellspacing="0">
          <tr>
            <td width="130" height="25" class="STYLE2">&nbsp;</td>
            <td width="147" class="STYLE2">           
            <d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="oneline:cardType" />            </td>
          </tr>
        </table></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        <tbl:write name="oneline" property="cardID"/>
      </span></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><tbl:write name="account" property="accountName"/></td>
      <td colspan="2" class="STYLE2"><%=strMopName%></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2"><tbl:write name="account" property="bankAccount"/></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="2" class="STYLE2"><table width="311" height="25" cellspacing="0">
          <tr>
            <td width="61" height="25" class="STYLE2"><%=addrname %></td>
            <td width="244" class="STYLE2"><tbl:write name="custaddr" property="detailAddress"/></td>
          </tr>
        </table></td>
      <td width="141" class="STYLE2"><span class="STYLE2">
        <tbl:write name="custaddr" property="postcode"/>
      </span></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td height="22" colspan="2" class="STYLE2"><table width="312" height="25" cellspacing="0">
          <tr>
            <td width="62" height="25" class="STYLE2"><%=accAddrname%></td>
            <td width="244" class="STYLE2"><tbl:write name="accountaddress" property="detailAddress"/></td>
          </tr>
        </table></td>
      <td class="STYLE2"><tbl:write name="accountaddress" property="postcode"/></td>
    </tr>
    <tr>
      <td rowspan="3" class="STYLE2">&nbsp;</td>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <tbl:write name="csi" property="agentName"/>
      </span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
      </span></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><table width="298" height="25" cellspacing="0">
        <tr>
          <td width="130" height="25" class="STYLE2">&nbsp;</td>
          <td width="162" class="STYLE2">         
          <d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="csi:agentCardType" />          
		  </td>
        </tr>
      </table></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        <tbl:write name="csi" property="agentCardID"/>
      </span></td>
      </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2">&nbsp;</td>
      </tr>
    <tr>
      <td height="40" colspan="2" class="STYLE2">&nbsp;</td>
      <td height="35" class="STYLE2"><table width="297" height="25" border="0" cellspacing="0">
        <tr>
          <td width="132" height="25" class="STYLE2">&nbsp;</td>
          <td width="161" class="STYLE2">
          <div align="center"><%=csiReasonDes%></div>
         </td>
		 
        </tr>
      </table></td>
      <td colspan="2" class="STYLE2"><table width="98" height="25" border="0" cellspacing="0">
        <tr>
          <td width="8" height="25" class="STYLE2">&nbsp;</td>
          <td width="55" class="STYLE2"></td>
          <td width="29" class="STYLE2"></td>
        </tr>
      </table></td>
      </tr>
  </table>
  <table width="645" height="43" cellspacing="0" >
    <tr>
      <td width="65" height="20" class="STYLE2">&nbsp;</td>
      <td width="117" height="20" class="STYLE2">&nbsp;</td>
      <td width="92" class="STYLE2"><div align="center" class="STYLE2"></div></td>
      <td width="177" class="STYLE2">&nbsp;</td>
      <td width="86" class="STYLE2"><div align="center" class="STYLE2"></div></td>
      <td width="82" class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td height="20" class="STYLE2">&nbsp;</td>
      <td height="20" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center"></div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center"></div></td>
      <td class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td height="45" class="STYLE2">&nbsp;</td>
      <td height="40" colspan="5" class="STYLE2">&nbsp;</td>
      </tr>
  </table>
  <table width="645" height="154" cellspacing="0">
    <tr>
      <td height="27" colspan="4" class="STYLE26">&nbsp;</td>
    </tr>
    <tr>
      <td width="150" height="27" >&nbsp;</td>
      <td width="221" class="STYLE2"><div align="center" >
        <div align="center"><%=STBDeviceModel%></div>
      </div></td>
      <td width="73" >&nbsp;</td>
      <td width="191" class="STYLE2"><div align="center" >
        <div align="center"><%=STBNO%></div>
      </div></td>
    </tr>
    <tr>
      <td height="27" >&nbsp;</td>
      <td class="STYLE2"><div align="center">
        <div align="center"><%=SCDeviceModel%></div>
      </div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" >
        <div align="center"><%=SCNO%></div>
      </div></td>
    </tr>
    <tr>
      <td height="27" >&nbsp;</td>
      <td class="STYLE2"><div align="center" ></div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" ></div></td>
    </tr>
    <tr>
      <td height="27" >&nbsp;</td>
      <td class="STYLE2"><div align="center" ></div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" ></div></td>
    </tr>
    <tr>
      <td height="27" class="STYLE35">&nbsp;</td>
      <td colspan="3" ><div align="center" class="STYLE2"></div>
          <div align="center" class="STYLE2"></div>
        <div align="center" class="STYLE2"></div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</div>
 
</lgc:bloop>
</body>
</html>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
