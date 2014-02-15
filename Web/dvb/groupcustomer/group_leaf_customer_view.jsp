<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO,
                 com.dtv.oss.dto.ServiceAccountDTO, 
                 com.dtv.oss.dto.CustomerDTO, 
                 com.dtv.oss.dto.TerminalDeviceDTO"%>
                 
<%
	boolean isGroupToSingle=false;
	String actionType=request.getParameter("txtActionType");
	if(actionType!=null&&actionType.equals("groupToSingle")){
		isGroupToSingle=true;
	}
%>                 
<Script language=javascript>
function toSingle_submit(val){
	if(val==null||val==""){
		val=document.frmPost.txtCustomerID.value;
	}
	if(val!=null&&val!=""){
		document.frmPost.action="group_customer_info_prepare.do?txtCustomerID="+val;
		document.frmPost.submit();
	}
}
</SCRIPT>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">集团客户的子客户信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" >
 
<%
   int custId=0;
%>
 
	<tbl:hiddenparameters pass="txtActionType" />
		<input type="hidden" name="forwardFlag" value="30" >
 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("cust", wrap.getCustDto());
    CustomerDTO custDto = wrap.getCustDto();
    custId = custDto.getCustomerID();
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
    
    Map markMap = Postern.getServeyResultByCustIdForRealUser(wrap.getCustDto().getCustomerID(),"T_CUSTMARKETINFO","CUSTOMERID");
    pageContext.setAttribute("serveyMarketMap",markMap);
    
%>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

   <tr>
     <td class="list_bg2" width="17%" align="right">姓名</td>
     <td class="list_bg1" width="33%" align="left"><input type="text" name="txtName" class="textgray"  value="<tbl:write name="cust" property="name" />" readonly ></td>
     <td class="list_bg2" width="17%" align="right">客户ID</td>
     <td class="list_bg1" width="33%" align="left"><input type="text" name="txtCustomerID" class="textgray" value="<tbl:write name="cust" property="customerID" />" readonly></td>
   </tr>
   <tr>
     <td class="list_bg2" width="17%" align="right">性别</td>
     <td class="list_bg1" width="33%" align="left"><input type="text" name="txtGenderDesc" class="textgray" value="<d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="cust:gender" />" readonly> 
     </td>
     <td class="list_bg2" width="17%" align="right">出生日期</td>
     <td class="list_bg1" width="33%" align="left"><input type="text" name="txtBirthday" class="textgray" value="<tbl:writedate name="cust" property="birthday" />" readonly> </td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">国籍</td>
     <td class="list_bg1" align="left"><input type="text" name="txtNationalityDesc" class="textgray" value="<d:getcmnname typeName="SET_C_NATIONALITY" match="cust:nationality" />" readonly>
     </td>
     <td class="list_bg2" align="right">详细地址</td>
     <td class="list_bg1" align="left"><input type="text" name="txtDetailAddress" class="textgray"  value="<tbl:write name="custaddr" property="DetailAddress" />"  readonly></td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">证件类型</td>
     <td class="list_bg1" align="left"><input type="text" name="txtCardTypeDesc" class="textgray" value="<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="cust:cardType" />" readonly>
     </td>
     <td class="list_bg2" align="right">证件号码</td>
     <td class="list_bg1" align="left"><input type="text" name="txtCardID" class="textgray" value="<tbl:write name="cust" property="cardID" />"  readonly></td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">社保卡编号</td>
     <td class="list_bg1" align="left"><input type="text" name="txtSocialSecCardID" class="textgray"  value="<tbl:write name="cust" property="socialSecCardID" />" readonly></td>
     <td class="list_bg2" align="right">传真</div></td>
     <td class="list_bg1" align="left"><input type="text" name="txtFax" class="textgray"  value="<tbl:write name="cust" property="fax" />" readonly></td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">固定电话</td>
     <td class="list_bg1" align="left"><input type="text" name="txtTelephone" class="textgray"  value="<tbl:write name="cust" property="telephone" />"  readonly></td>
     <td class="list_bg2" align="right">移动电话</td>
     <td class="list_bg1" align="left"><input type="text" name="txtTelephoneMobile" class="textgray"  value="<tbl:write name="cust" property="telephoneMobile" />" readonly ></td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">Email 地址</td>
     <td class="list_bg1" align="left"><input type="text" name="txtEmail" class="textgray"  value="<tbl:write name="cust" property="email" />" readonly></td>
     <td class="list_bg2" align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
     <td class="list_bg1" align="left"><input type="text" name="txtCatvID" class="textgray" maxlength="10" value="<tbl:write name="cust" property="CatvID" />" readonly ></td>
   </tr>
   <tr>
     <td class="list_bg2" align="right">所在区域</td>
     <td class="list_bg1" align="left">         
        <input type="text" name="txtDistrictDesc" class="textgray" maxlength="50" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" readonly>
     		<input type="hidden" name="txtCounty" value="<tbl:write name="custaddr" property="DistrictID" />" />
     </td>
     <td class="list_bg2" align="right">邮编</td>
     <td class="list_bg1" align="left"><input type="text" name="txtPostcode" class="textgray" value="<tbl:write name="custaddr" property="Postcode" />" readonly></td>
   </tr>
</table>
<%
Collection saList =Postern.getServiceAccountDTOByCustomerID(wrap.getCustDto().getCustomerID());
if (saList !=null && !saList.isEmpty()){
   Iterator saIter =saList.iterator();
   while (saIter.hasNext()){
        ServiceAccountDTO saDto =(ServiceAccountDTO)saIter.next();
        pageContext.setAttribute("sa",saDto);
        int saId=saDto.getServiceAccountID();
        String contractNo = Postern.getContractNoBySaIdAndCustId(saId,custId);
        
%>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr> 
	    <td colspan="4" class="import_tit" align="center"><font size="3">业务帐户信息</font></td>
   </tr>
   <tr>
   	  <td class="list_bg2" align="right">业务帐户ID</td>
   	  <td class="list_bg2" align="left">
   	  	<input type="text" name="txtServiceAccountID" size="25" value="<tbl:write name="sa" property="serviceAccountID" />"  class="textgray" readonly>
   	  </td>
   	  <td class="list_bg2" align="right">业务名称</td>
   	  <td class="list_bg2" align="left">
   	  	<input type="text" name="txtSAName"  class="textgray" readonly  size="25" value="<%=Postern.getServiceNameByID(saDto.getServiceID())%>">
			</td>
   </tr>
   <tr>
   	  <td class="list_bg2" align="right">状态</td>
   	  <td class="list_bg2" align="left">
   	  	<input name="txtSAStatus" type="text" class="textgray" readonly size="25"  value="<d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="sa:status" />"></td>
   	  <td class="list_bg2" align="right">创建日期</td>
   	  <td class="list_bg2" align="left">
   	  	<input name="txtSAC" type="text" class="textgray" size="25" readonly value="<tbl:write name='sa' property='createTime' />">
   	  </td>
   </tr>
   <tr>
   	  <td class="list_bg2" align="right">服务号码</td>
   	  <td class="list_bg2" align="left" colspan="3"><input name="txtServiceCode" type="text" class="textgray" readonly maxlength="20" size="25" value="<tbl:write name='sa' property='serviceCode' />"></td>
   </tr>
   <%
     if(contractNo!=null){
     
     String name = Postern.getContractNameByContractNo(contractNo);
   %>
  
    <tr>
   	  <td class="list_bg2" align="right">合同号</td>
   	  <td class="list_bg2" align="left">
   	  <input type="text" name="txtContractNo"  class="textgray" readonly size="25" value="<%=contractNo%>">
   	  	 
   	  </td>
   	  <td class="list_bg2" align="right">合同名称</td>
   	  <td class="list_bg2" align="left">
   	  <input type="text" name="txtContractName"  class="textgray" readonly size="25" value="<%=name%>">
   	  	 
			</td>
   </tr>
   <%}%>
</table>
<%
      Collection cpList =Postern.getCustomerProductDTOByServiceAccountID(saDto.getServiceAccountID());
      if (cpList !=null && !cpList.isEmpty()){
%>
<table  width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	     <tr>
    	 	  <td class="list_head">客户产品ID</td>
					<td class="list_head">产品名称</td>
					<td class="list_head">状态</td>
					<td class="list_head">创建时间</td>
					<td class="list_head">激活时间</td>
					<td class="list_head">产品包名称</td>
					<td class="list_head">设备类型</td>
					<td class="list_head">设备序列号</td>
					<td class="list_head">付费帐户</td>
    	 </tr>
<%
        Iterator cpIter =cpList.iterator();
        while (cpIter.hasNext()){
           CustomerProductDTO cpDto =(CustomerProductDTO)cpIter.next();
           pageContext.setAttribute("cp",cpDto);
           String strProdName =Postern.getProductNameByID(cpDto.getProductID());
           String strPackName =Postern.getPackagenameByID(cpDto.getReferPackageID());
           TerminalDeviceDTO  tdDTO = Postern.getTerminalDeviceByID(cpDto.getDeviceID());
           String acctName = null;
					 if (Postern.getAcctNameById(cpDto.getAccountID())!=null)
					    acctName=Postern.getAcctNameById(cpDto.getAccountID());
					 else
					    acctName = String.valueOf(cpDto.getAccountID()); 
%>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	    <td><tbl:write name="cp" property="PsID" /></td>
    	    <td><%=strProdName%></td>
    	    <td><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="cp:status" /></td>
    	    <td><tbl:writedate name="cp" property="createTime" /></td>
					<td><tbl:writedate name="cp" property="activateTime" /></td>
    	    <td><%=strPackName%></td>
    	      <% 
    	        String dclass = tdDTO.getDeviceClass();
    	        String className="";
    	        if(dclass!=null)
    	          className = Postern.getClassNameByDeviceClass(dclass);
    	      
    	       %>              
    	    <td><%= className %></td>
    	    <td><%=(tdDTO.getSerialNo() ==null) ? "" :tdDTO.getSerialNo() %></td>
    	    <td><%=acctName%></td>
       </tbl:printcsstr>
<%  
         }
%>        
       <tr>
					<td colspan="9" class="list_foot"></td>
			 </tr>
</table>
<%
      } 
     }
   }
%>
</lgc:bloop>
</form>
  </td>
 </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="0">
  		<tr>
			     <%if(isGroupToSingle){%>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif" ><a href="<bk:backurl property="group_to_single_query.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
			     <td width="20" ></td>
           <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td background="img/button_bg.gif" ><input name="next" type="button" class="button" onClick="toSingle_submit(<tbl:writeparam name="txtCustomerID"/>)" value="转个人客户"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
           <%}else{%>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif" ><a href="<bk:backurl property="group_cust_query.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
			     <td width="20" ></td>
			     <%}%>
      </tr>
</table>