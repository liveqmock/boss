<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ page import="com.dtv.oss.dto.AddressDTO,
					com.dtv.oss.dto.TerminalDeviceDTO,
					com.dtv.oss.dto.NewCustomerInfoDTO,
					com.dtv.oss.dto.NewCustAccountInfoDTO,
					com.dtv.oss.dto.CustServiceInteractionDTO" %><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>有线数字电视整体转换受理登记表</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>

<%
    int currentRecordCount=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    System.out.println(pageContext.getAttribute("oneline").getClass().getName());
	  CustServiceInteractionWrap wrap = (CustServiceInteractionWrap)pageContext.getAttribute("oneline");
	  CustServiceInteractionDTO  csidto = wrap.getCsiDto();
    pageContext.setAttribute("csi",csidto);
    
    pageContext.setAttribute("newcustInfo", wrap.getNciDto());
    pageContext.setAttribute("custaddr", wrap.getCustAddrDto());
    pageContext.setAttribute("newcustAcctInfo" , wrap.getNcaiDto());
    pageContext.setAttribute("acctaddr",wrap.getAcctAddrDto());
//  邮寄帐单地址
    AddressDTO addressDto=Postern.getAddressDtoByID(wrap.getNcaiDto().getAddressID());
    pageContext.setAttribute("accountaddress",  addressDto);
    String acctAddrDist = Postern.getDistrictNameByID(addressDto.getDistrictID());
    if (acctAddrDist ==null) acctAddrDist ="";
    
    String comment=csidto.getComments();
    if(comment==null)
    	comment="";
 	  NewCustomerInfoDTO newCustInfo=wrap.getNciDto();
    
    String custAddrDist = Postern.getDistrictNameByID(wrap.getCustAddrDto().getDistrictID());
    if (custAddrDist ==null) custAddrDist ="";
    
    
    
    //付费方式
  	Map mapBankMop = Postern.getAllMethodOfPayments();
	  MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO) mapBankMop.get(String.valueOf(wrap.getNcaiDto().getMopID()));
	  String strMopName = null;
	  if (dtoMOP != null)
		  strMopName = dtoMOP.getName();
	  if (strMopName == null)
		  strMopName = "";
		//机顶盒信息  
	  String scNo ="";
      String stbNo="";
      String stbModel ="";
      Collection deviceIDCol=Postern.getDeviceIDByCSIID(csidto.getId());
      Iterator deviceIDIter =deviceIDCol.iterator();
      while (deviceIDIter.hasNext()){
          Integer deviceID =(Integer)deviceIDIter.next();
          TerminalDeviceDTO terminalDto =Postern.getTerminalDeviceByID(deviceID.intValue());
          if ("STB".equals(terminalDto.getDeviceClass())){
             stbModel =terminalDto.getDeviceModel();
             stbNo =terminalDto.getSerialNo();
          }
          if ("SC".equals(terminalDto.getDeviceClass())){
             scNo =terminalDto.getSerialNo();
          }
      }
	  //性别初始化
//  String ss=new String("\u221A".getBytes());
//  String custm="",custf="",angentm="",angentf="";
//  if ("M".equals(newCustInfo.getGender())){
//  	custm=ss;
//  }else if("F".equals(newCustInfo.getGender())){
//  	custf=ss;
//  }
//  
//String custTypeY="",custTypeN="";
//if("ZDB".equals(newCustInfo.getType())){
//	custTypeY=ss;
//}else{
//	custTypeN=ss;
//}
	   
	  //loud受理原因作为整转/购买的显示
    String csiReasonDes =Postern.getCsiReasonDesByCon(csidto.getType(),"N",csidto.getCreateReason());
    if(csiReasonDes == null)
    {
    	csiReasonDes="";
    }
  else
  	{
  		csiReasonDes = "["+csiReasonDes+"]";
  	}
%>

<div align="center">
  <table width="645" height="22" border="0" cellspacing="0">
    <tr>
      <td height="30" colspan="5" class="STYLE2"><div align="center" class="STYLE7"></div></td>
    </tr>
    <tr>
      <td width="86" height="25" class="STYLE2">&nbsp;</td>
      <td width="146" class="STYLE2"><span class="STYLE2">
        <tbl:write name="newcustInfo" property="custID" />
      </span></td>
      <td width="218" class="STYLE2"><table width="200" border="0" cellspacing="0">
          <tr>
            <td width="48" height="25" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="yyyy" /></div></td>
            <td width="8" class="STYLE2">&nbsp;</td>
            <td width="25" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="MM" /></div></td>
            <td width="17" class="STYLE2">&nbsp;</td>
            <td width="18" class="STYLE2"><div align="center"><tbl:writedate name="csi" property="dtCreate" pattern="dd" /></div></td>
            <td width="72" class="STYLE2">&nbsp;</td>
          </tr>
      </table></td>
      <td width="63" class="STYLE2">&nbsp;</td>
      <td width="122" class="STYLE2">&nbsp;</td>
    </tr>
  </table>
  <table width="645" border="0" cellspacing="0">
    <tr>
      <td height="22" colspan="5" class="STYLE2"><div align="center"></div></td>
    </tr>
    <tr>
      <td width="50" rowspan="8" class="STYLE2">&nbsp;</td>
      <td width="70" height="27" class="STYLE2">&nbsp;</td>
      <td width="296" class="STYLE2"><span class="STYLE2">
        <tbl:write name="newcustInfo" property="name" />
      </span></td>
      <td colspan="2" class="STYLE2"><div align="center" class="STYLE2">
          <table width="110" height="25" border="0" cellspacing="0">
            <tr>
              <td width="33" height="25">&nbsp;</td>
              <td width="73" class="STYLE2"><d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="newcustInfo:gender" /></td>
              </tr>
          </table>
      </div></td>
    </tr>
    <tr>
      <td height="30" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <tbl:write name="newcustInfo" property="telephone" />
      </span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        <tbl:write name="newcustInfo" property="mobileNumber" />
      </span></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2"><div align="center">
        <table width="358" height="24" cellspacing="0">
          <tr>
            <td width="10" height="22" class="STYLE2">&nbsp;</td>
            <td width="76" class="STYLE2"></td>
            <td height="22" class="STYLE2">
                <d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="newcustInfo:cardType" />
				</td>
            </tr>
        </table>
      </div>
        <div align="center"></div></td>
      </tr>
    <tr>
      <td height="29" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2"><%=newCustInfo.getCardID()%></span></td>
      <td colspan="2" class="STYLE2"><div align="center">
        <table width="97" height="25" border="0" cellspacing="0">
          <tr>
            <td height="25" class="STYLE2">
			<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="newcustInfo:type" />
			</td>
            </tr>
        </table>
      </div></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
       <tbl:write name="newcustAcctInfo" property="bankAccountName" />
      </span></td>
      <td colspan="2" class="STYLE2"><%=strMopName%></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2"><tbl:write name="newcustAcctInfo" property="bankAccount" /></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="2" class="STYLE2"><table width="294" height="25" cellspacing="0">
          <tr>
            <td width="61" height="25" class="STYLE2"><%=custAddrDist%></td>
            <td width="227" class="STYLE2"><tbl:write name="custaddr" property="detailAddress" /></td>
          </tr>
      </table></td>
      <td width="173" class="STYLE2"><span class="STYLE2">
        <tbl:write name="custaddr" property="postcode" />
      </span></td>
    </tr>
    <tr>
      <td class="STYLE2"></td>
      <td height="22" colspan="2" class="STYLE2"><table width="294" height="25" cellspacing="0">
          <tr>
            <td width="62" height="25" class="STYLE2"><%=acctAddrDist%></td>
            <td width="226" class="STYLE2"><tbl:write name="accountaddress" property="detailAddress"/></td>
          </tr>
      </table></td>
      <td class="STYLE2"><span class="STYLE2"><tbl:write name="accountaddress" property="postcode" /></span></td>
    </tr>
    <tr>
      <td rowspan="5" class="STYLE2">&nbsp;</td>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <tbl:write name="csi" property="agentName"  />
      </span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        
      </span>
        <div align="center">
          <table width="113" height="25" border="0" cellspacing="0">
            <tr>
              <td width="41" height="25">&nbsp;</td>
              <td width="68">
			  <d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="" /></td>
              </tr>
          </table>
        </div></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"></td>
      <td colspan="2" color="#990099" class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><table width="202" height="25" cellspacing="0">
          <tr>
            <td width="42" class="STYLE2">&nbsp;</td>
            <td width="52" class="STYLE2"></td>
            <td width="100" height="25" class="STYLE2">
			</td>
            </tr>
      </table></td>
      <td colspan="2" class="STYLE2"><tbl:write name="csi" property="agentCardID" /></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2">&nbsp;</td>
    </tr>
  </table>
  <table width="645" height="192" border="0" cellspacing="0">
    <tr>
      <td height="32" colspan="4" class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td width="150" height="27" class="STYLE2">&nbsp;</td>
      <td width="227" class="STYLE2"><div align="center" class="STYLE2">
          <div align="center">
            <table width="130" height="25" border="0" cellspacing="0">
              <tr>
                <td width="35" height="25" class="STYLE2"></td>
                <td width="91" class="STYLE2"><%=comment%></td>
                </tr>
            </table>
          </div>
      </div></td>
      <td width="77" class="STYLE2">&nbsp;</td>
      <td width="183" class="STYLE2"><div align="center" class="STYLE2">
          <div align="center">
            <table width="122" height="25" border="0" cellspacing="0">
              <tr>
                <td width="37" height="25" class="STYLE2">&nbsp;</td>
                <td width="81" class="STYLE2"><%=comment%></td>
                </tr>
            </table>
          </div>
      </div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2">
          <div align="center"><%=stbModel%><%=csiReasonDes%></div>
      </div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2">
          <div align="center"><%=stbNo%></div>
      </div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2"><%=scNo%></div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2"></div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2"></div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2"></div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center"></div></td>
      <td class="STYLE2">&nbsp;</td>

      <td class="STYLE2">&nbsp;</td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2"><div align="center" class="STYLE2"></div>
          <div align="center" class="STYLE2"></div>
        <div align="center" class="STYLE2"></div></td>
    </tr>
  </table>
  <p>&nbsp;</p>
</div>
<%
	currentRecordCount=currentRecordCount+1;
%>
 <rs:isNoTheLastRecord   itemCount="<%=currentRecordCount%>">	
<p STYLE="page-break-after: always">&nbsp;</p>
</rs:isNoTheLastRecord>     
</lgc:bloop>
</body>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>

