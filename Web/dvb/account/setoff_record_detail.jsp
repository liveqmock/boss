<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%@ page import="com.dtv.oss.dto.FinanceSetOffMapDTO ,
                 com.dtv.oss.dto.CustomerDTO ,
                 com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.FinanceSetoffMap2AcctItemTypeWrap" %>

<br>

<form name="frmPost" method="post" action="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">销账记录详细信息</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >

  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" >
  <%
       FinanceSetoffMap2AcctItemTypeWrap dto = (FinanceSetoffMap2AcctItemTypeWrap)pageContext.getAttribute("oneline");
       FinanceSetOffMapDTO setoffDTO=dto.getSetOffDTO();
       pageContext.setAttribute("DTO",setoffDTO);
       pageContext.setAttribute("ALLDTO",dto);

       String strOpName=Postern.getOperatorNameByID(setoffDTO.getOpId());
       if(strOpName==null)
    	   strOpName="";
    
       String strCustName=Postern.getCustomerNameByID(setoffDTO.getCustId());
       if(strCustName==null)
    	   strCustName="";
           	   
      CustomerDTO custDTO =Postern.getCustomerByID(setoffDTO.getCustId());
       pageContext.setAttribute("cust",custDTO);
      AddressDTO addrDTO =null;
      if (custDTO !=null)  addrDTO =Postern.getAddressDtoByID(custDTO.getAddressID());	   
      if (addrDTO ==null) addrDTO =new AddressDTO();
      pageContext.setAttribute("AddrDTO",addrDTO);
           	   
      String strOrgName=Postern.getOrgNameByCustomerID(setoffDTO.getCustId());
       if(strOrgName==null)
       	   strOrgName="";
       
  %>
  <tr>
    <td class="list_bg2"><div align="right">序号</div></td>
    <td class="list_bg1"><input name="txtSeqNo" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="seqNo" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">销账金额</div></td>
    <td class="list_bg1"><input name="txtValue" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="value" />" readonly=true ></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">客户证号</div></td>
    <td class="list_bg1"><input name="txtCustId" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="custId" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">客户姓名</div></td>
    <td class="list_bg1"><input name="txtCustName" type="text" class="textgray" maxlength="200" size="25" value="<%=strCustName %>" readonly=true ></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">帐户号</div></td>
    <td class="list_bg1"><input name="txtAcctID" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="acctId" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">销帐日期</div></td>
    <td class="list_bg1"><input name="txtCreateTime" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writedate name="DTO" property="createTime" />" readonly=true ></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">组织机构</div></td>
    <td class="list_bg1">
    <input type="text" name="txtOrgName" size="25" maxlength="50" class="textgray" readonly value="<tbl:WriteOrganizationInfo name="cust" property="orgID" />" class="text">
    </td> 
    <td class="list_bg2"><div align="right">区域</div></td>
    <td class="list_bg1">
       <input name="txtDistrictName" type="text" class="textgray" maxlength="200" size="25" value="<tbl:WriteDistrictInfo name="AddrDTO" property="districtID" />" readonly=true >
    </td>
  </tr>  
 
  <tr>
    <td class="list_bg2"><div align="right">操作员</div></td>
    <td class="list_bg1"><input name="txtOpName" type="text" class="textgray" maxlength="200" size="25" value="<%=strOpName%>" readonly=true ></td>
    <td class="list_bg2"><div align="right">状态</div></td>
    <td class="list_bg1"><input name="txtStatus" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_G_GENERALSTATUS" match="DTO:status" />" readonly=true ></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">实收关联类型</div></td>
    <td class="list_bg1"><input name="txtPlusReferType" type="text" class="textgray" maxlength="200" size="25" value="<d:getcmnname typeName="SET_F_SETOFFREFERTYPE" match="DTO:plusReferType" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">支付方式/预存类型/帐目类型</div></td>
    
    <td class="list_bg1"><input name="txtLinkTable" type="text" class="textgray" maxlength="200" size="25" 
    <% if(dto.getPayAcctItemType()==null || "".equals(dto.getPayAcctItemType())) {
    %>
    	value="<d:getcmnname typeName="<%=dto.getPaymentCommonSettingName()%>" match="ALLDTO:payCommonSettingValue" />"
    <%} else {%>
    	value="<tbl:write name="ALLDTO" property="payAcctItemType" />"
    <%}%> readonly=true >
   </td>
   
  </tr>

  <tr>
    <td class="list_bg2"><div align="right">实收关联记录ID </div></td>
    <td class="list_bg1"><input name="txtPlusReferID" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="plusReferId" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">实收金额</div></td>
    <td class="list_bg1"><input name="txtPaymentAmount" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="ALLDTO" property="paymentAmount" />" readonly=true ></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">支付日期</div></td>
    <td class="list_bg1"><input name="txtPaymentTime" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writedate name="ALLDTO" property="paymentTime" />" readonly=true ></td>
    <td class="list_bg2"></td>
    <td class="list_bg1"></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">帐目类型</div></td>
    
    <td class="list_bg1"><input name="txtMinusReferType" type="text" class="textgray" maxlength="200" size="25" 
    <% if(dto.getFeeAcctItemType()==null || "".equals(dto.getFeeAcctItemType())) {
    %>
    	value="<d:getcmnname typeName="<%=dto.getFeeCommonSettingName()%>" match="ALLDTO:feeCommonSettingValue" />"
    <%} else {%>
    	value="<tbl:write name="ALLDTO" property="feeAcctItemType" />"
    <%}%>
    </td>
    
    <td class="list_bg2"><div align="right">应收关联记录ID</div></td>
    <td class="list_bg1"><input name="txtPlusReferID" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="DTO" property="minusReferId" />" readonly=true ></td>
  </tr>

  <tr>
    <td class="list_bg2"><div align="right">费用金额</div></td>
    <td class="list_bg1"><input name="txtFeeAmount" type="text" class="textgray" maxlength="200" size="25" value="<tbl:write name="ALLDTO" property="feeAmount" />" readonly=true ></td>
    <td class="list_bg2"><div align="right">费用产生日期</div></td>
    <td class="list_bg1"><input name="txtFeeTime" type="text" class="textgray" maxlength="200" size="25" value="<tbl:writedate name="ALLDTO" property="feeTime" />" readonly=true ></td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">备注 </div></td>
    <td class="list_bg1" colspan="3">
      <input type="text" name="txtComments"  class="textgray" readonly  size="90" value="<tbl:write  name="DTO" property="comments" />" >
    </td>
  </tr>
  
 </lgc:bloop>
  
 </table>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
    <bk:canback url="setoff_record_query.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="setoff_record_query.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
    </bk:canback> 
</tr>
</table>     
 
 </form>