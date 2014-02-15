<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.Customer2AddressWrap" %>
<%@ page import="com.dtv.oss.dto.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>有线数字电视整体转换受理登记表</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    //客户信息
    pageContext.setAttribute("cust", wrap.getCustDto());
    //客户地址信息
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
    String custAddrDist = Postern.getDistrictNameByID(wrap.getAddrDto().getDistrictID());
    if (custAddrDist ==null) custAddrDist ="";
    //帐户信息
    AccountDTO accountDTO =Postern.getAccountDTOByCustID(wrap.getCustDto().getCustomerID());
    pageContext.setAttribute("acct" , accountDTO);
    //帐户地址信息
    AddressDTO addressDTO=Postern.getAddressDtoByID(accountDTO.getAddressID());
    pageContext.setAttribute("acctaddr",addressDTO);
    String acctAddrDist = Postern.getDistrictNameByID(addressDTO.getDistrictID());
    if (acctAddrDist ==null) acctAddrDist ="";
    //付费方式
  	Map mapBankMop = Postern.getAllMethodOfPayments();
	MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO) mapBankMop.get(String.valueOf(accountDTO.getMopID()));
	String strMopName = null;
	if (dtoMOP != null)
		  strMopName = dtoMOP.getName();
	if (strMopName == null)
		strMopName = "";
%>

<div align="center">
  <table width="645" height="22" border="0" cellspacing="0">
    <tr>
      <td height="30" colspan="5" class="STYLE2"><div align="center" class="STYLE7"></div></td>
    </tr>
    <tr>
      <td width="86" height="25" class="STYLE2">&nbsp;</td>
      <td width="146" class="STYLE2"><span class="STYLE2">
        <tbl:write name="cust" property="customerID" />
      </span></td>
      <td width="218" class="STYLE2"><table width="200" border="0" cellspacing="0">
          <tr>
            <td width="48" height="25" class="STYLE2"><div align="center"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy")%></div></td>
            <td width="8" class="STYLE2">&nbsp;</td>
            <td width="25" class="STYLE2"><div align="center"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"MM")%></div></td>
            <td width="17" class="STYLE2">&nbsp;</td>
            <td width="18" class="STYLE2"><div align="center"><%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"dd")%></div></td>
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
        <tbl:write name="cust" property="name" />
      </span></td>
      <td colspan="2" class="STYLE2"><div align="center" class="STYLE2">
          <table width="110" height="25" border="0" cellspacing="0">
            <tr>
              <td width="33" height="25">&nbsp;</td>
              <td width="73" class="STYLE2"><d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="cust:gender" /></td>
              </tr>
          </table>
      </div></td>
    </tr>
    <tr>
      <td height="30" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <tbl:write name="cust" property="telephone" />
      </span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        <tbl:write name="cust" property="telephoneMobile" />
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
                <d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="cust:cardType" />
				</td>
            </tr>
        </table>
      </div>
        <div align="center"></div></td>
      </tr>
    <tr>
      <td height="29" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2"><tbl:write name="cust" property="cardID" /></span></td>
      <td colspan="2" class="STYLE2"><div align="center">
        <table width="97" height="25" border="0" cellspacing="0">
          <tr>
            <td height="25" class="STYLE2">
			<d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="cust:customerType" />
			</td>
            </tr>
        </table>
      </div></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
       <tbl:write name="acct" property="bankAccountName" />
      </span></td>
      <td colspan="2" class="STYLE2"><%=strMopName%></td>
    </tr>
    <tr>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td colspan="3" class="STYLE2"><tbl:write name="acct" property="bankAccount" /></td>
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
            <td width="226" class="STYLE2"><tbl:write name="acctaddr" property="detailAddress"/></td>
          </tr>
      </table></td>
      <td class="STYLE2"><span class="STYLE2"><tbl:write name="acctaddr" property="postcode" /></span></td>
    </tr>
    <tr>
      <td rowspan="5" class="STYLE2">&nbsp;</td>
      <td height="27" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><span class="STYLE2">
        <br>
      </span></td>
      <td colspan="2" class="STYLE2"><span class="STYLE2">
        
      </span>
        <div align="center">
          <table width="113" height="25" border="0" cellspacing="0">
            <tr>
              <td width="41" height="25">&nbsp;</td>
              <td width="68">
			  <br>
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
      <td colspan="2" class="STYLE2"><br></td>
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
                <td width="91" class="STYLE2"><br></td>
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
                <td width="81" class="STYLE2"><br></td>
                </tr>
            </table>
          </div>
      </div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2">
          <div align="center"><br></div>
      </div></td>
      <td class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2">
          <div align="center"><br></div>
      </div></td>
    </tr>
    <tr>
      <td height="28" class="STYLE2">&nbsp;</td>
      <td class="STYLE2"><div align="center" class="STYLE2"><br></div></td>
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
</lgc:bloop>
</body>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>

