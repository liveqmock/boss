<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="java.util.*,
                com.dtv.oss.util.Postern,
                com.dtv.oss.dto.CustomerDTO,
                com.dtv.oss.dto.NewCustomerMarketInfoDTO,
                com.dtv.oss.dto.wrap.customer.Customer2AddressWrap" %>

<%
    String   forwardFlag =(request.getParameter("forwardFlag") ==null) ? "" :request.getParameter("forwardFlag");
%>

<table width="98%" border="0" cellspacing="5" cellpadding="5">
    <tr>
        <td width="100%"><div align="center">
            <p align="center" class="title1">正在获取客户信息，请稍候。。。</strong></p>
        </div></td>
    </tr>
</table>

<form name="frmPost" method="post" action="" >
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">
    <%
        Customer2AddressWrap wrap = (Customer2AddressWrap)pageContext.getAttribute("oneline");
        pageContext.setAttribute("cust", wrap.getCustDto());
        pageContext.setAttribute("custaddr",  wrap.getAddrDto());

        int customerId =((CustomerDTO)wrap.getCustDto()).getCustomerID();
    %>
    
    <%
        if (!"22".equals(forwardFlag) && !"21".equals(forwardFlag)){   //22--->异地过户
    %>
   
    <input type="hidden" name="txtSocialSecCardID" value="<tbl:write name="cust" property="socialSecCardID"/>">  
    <input type="hidden" name="txtEmail" value="<tbl:write name="cust" property="email"/>">
    <input type="hidden" name="txtFaxNumber" value="<tbl:write name="cust" property="fax"/>">
    <input type="hidden" name="txtNationality" value="<tbl:write name="cust" property="nationality"/>">
    <input type="hidden" name="txtGender" value="<tbl:write name="cust" property="gender"/>">   
   <%
       }
   %>
   
    <input type="hidden" name="txtContractNo" value="<tbl:write name="cust" property="contractNo" />">
    <input type="hidden" name="txtCustomerSerialNo" value="<tbl:write name="cust" property="customerSerialNo" />">
    <input type="hidden" name="txtPostcode" value="<tbl:write name="custaddr" property="Postcode"/>">
    <input type="hidden" name="txtAddressID" value="<tbl:write name="custaddr" property="AddressID"/>">
    <input type="hidden" name="txtCatvID" value="<tbl:write name="cust" property="CatvID"/>">
    <input type="hidden" name="txtDistrict" value="<tbl:write name="custaddr" property="DistrictID"/>">
    <input type="hidden" name="txtOldDetailAddress" value="<tbl:write name="custaddr" property="DetailAddress"/>">
    <input type="hidden" name="txtAddressDTlastmod"  value="<tbl:write name="custaddr" property="DtLastmod"/>">
    <input type="hidden" name="txtCustomerDTlastmod" value="<tbl:write name="cust" property="DtLastmod"/>">
    <input type="hidden" name="txtCustomerID" value="<tbl:write name="cust" property="CustomerID"/>">
    <input type="hidden" name="txtDtCreateShow" value="<tbl:writedate name="cust" property="DtCreate" includeTime="true"/>">
    <input type="hidden" name="txtOpenSourceType" value="<tbl:write name="cust" property="openSourceType"/>">
    <input type="hidden" name="txtName" value="<tbl:write name="cust" property="name"/>">
    <input type="hidden" name="txtCardID" value="<tbl:write name="cust" property="cardID"/>">
    <input type="hidden" name="txtBirthday" value="<tbl:writedate name="cust" property="Birthday" includeTime="false"/>">
    <input type="hidden" name="txtTelephone" value="<tbl:write name="cust" property="telephone"/>">
    <input type="hidden" name="txtTelephoneMobile" value="<tbl:write name="cust" property="telephoneMobile"/>">
    <input type="hidden" name="txtGroupCustID" value="<tbl:write name="cust" property="groupCustID"/>">
    <input type="hidden" name="txtType" value="<tbl:write name="cust" property="customerType"/>">
    <input type="hidden" name="txtMobileNumber" value="<tbl:write name="cust" property="telephoneMobile"/>">
    <input type="hidden" name="txtCounty" value="<tbl:write name="custaddr" property="DistrictID"/>">
    <input type="hidden" name="txtCustAddressID" value="<tbl:write name="custaddr" property="AddressID"/>">
    <input type="hidden" name="txtDetailAddress" value="<tbl:write name="custaddr" property="DetailAddress"/>">	

    <tbl:hidden name="txtCatvID" value="cust:CatvID" />
    <tbl:hidden name="txtOldCatvID" value="cust:CatvID" />
    <tbl:hidden name="txtCustomerType" value="cust:CustomerType" />
    <tbl:hidden name="txtCustomerStatus" value="cust:Status" />
    <tbl:hidden name="txtCardType" value="cust:cardType" />
 </lgc:bloop>
</form>

<Script language=Javascript>
    var forward_Flag =<%=forwardFlag%> ;
    if(forward_Flag =="1")
    {
        document.frmPost.action ="customer_move_create.do" ;
        document.frmPost.txtCatvID.value="";
        document.frmPost.txtDetailAddress.value ="";
        document.frmPost.txtPostcode.value ="";
        document.frmPost.txtTelephone.value ="";
        document.frmPost.txtTelephoneMobile.value ="";
    }
    if(forward_Flag =="21")
    {
        document.frmPost.action ="customer_vicinal_transfer_create_info.do?transferType=vicinal" ;
    }
    if(forward_Flag =="22")
    {
        document.frmPost.action ="customer_strange_transfer_create_info.do?transferType=strange" ;
        document.frmPost.txtCatvID.value="";
        document.frmPost.txtDetailAddress.value ="";
    }    
    if(forward_Flag =="3")
    {
        document.frmPost.action ="customer_withdraw_create.do" ;
    }
    if(forward_Flag =="4")
    {
        document.frmPost.action ="customer_close_create.do" ;
    }
    if(forward_Flag =="30")
    {
        document.frmPost.action ="group_to_single_info.do" ;
    }
    document.frmPost.submit();
</Script>