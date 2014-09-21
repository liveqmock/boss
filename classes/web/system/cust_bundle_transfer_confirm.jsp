<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<Script language=JavaScript>
<!--

function setValue(vType,vLength){
	document.frmPost.txtCampaignDateTo.value=transferDate(null,vType,vLength);
}
function check_form(){
	return true;
}

function button_next(){
	if(check_form()){
		document.frmPost.action="cust_bundle_transfer_op.do";
		document.frmPost.submit();	
	}
}
	function back_submit(){
		document.frmPost.txtDoPost.value="false";
		if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
			document.frmPost.action="cust_bundle_transfer_fee.do";
		}else{
			document.frmPost.action="cust_bundle_transfer_pay.screen";
		}		
//		document.frmPost.action='cust_bundle_transfer_fee.do';
		document.frmPost.submit();
	}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐转换-确认信息</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">老的客户套餐信息</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">创建时间</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDtCreate" size="25"  value="<tbl:writeparam name="txtDtCreate"/>" class="textgray" readonly >
          </td> 
          <td class="list_bg2" align="right">状态</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtStatus" size="25" value="<tbl:writeparam name="txtStatus"/>" class="textgray" readonly >    
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">账户ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">业务账户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">协议期（开始）</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">协议期（结束）</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">套餐付费方式</td>
          <td class="list_bg1" align="left">
             <input type="text" name="txtPaymentType" size="25"  value="<tbl:writeparam name="txtPaymentType"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">NID</td>
          <td class="list_bg1"  align="left">
          	<input type="text" name="txtNbrDate" size="25"  value="<tbl:writeparam name="txtNbrDate"/>" class="textgray" readonly >      
          </td>
        </tr>                
      </table>   
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">新的套餐信息</font></td>
     </tr>
  </table>	
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">新套餐名称</td>
          <td class="list_bg1"  align="left" width="33%" colspan="3">
		      	<input type="text" name="txtNewCampaignName" size="25" value="<tbl:writeparam name="txtNewCampaignName"/>" readonly class="textgray">
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right" width="17%">新套餐转换日期</td>
          <td class="list_bg1"  align="left" width="33%">
		      	<input type="text" name="txtTransferDate" size="25" value="<tbl:writeparam name="txtTransferDate"/>" readonly class="textgray">
          </td>
          <td class="list_bg2" align="right" width="17%">新套餐有效期(结束)</td>
          <td class="list_bg1"  align="left" width="33%">
		      	<input type="text" name="txtCampaignDateTo" size="25" value="<tbl:writeparam name="txtCampaignDateTo"/>" readonly class="textgray">
          </td> 
        </tr>
        <tr>
           <tbl:csiReason csiType="BDS" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="label" controlSize="25" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdControlColspan="3"/>
        </tr>        
      </table>

	<%
		String[] cancelPsids = request.getParameterValues("cancelCpID");

		String psids = "";

		for (int i = 0; cancelPsids != null && i < cancelPsids.length; i++) {
			psids += cancelPsids[i]+",";
		}
		Map cancelCpMap = Postern.getCustomerProductMapByPsIDList(psids);
		if (cancelCpMap != null && !cancelCpMap.isEmpty()) {
	%>
	<table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
		<tr>
			<td class="import_tit" align="center">
				<font size="3">取消的客户产品</font>
			</td>
		</tr>
	</table>
	<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
		<tr class="list_head">
			<td width="10%" class="list_head" align="center">
				PSID
			</td>
			<td width="20%" class="list_head" align="center">
				业务帐户ID
			</td>
			<td class="list_head" align="center">
				产品名称
			</td>
			<td width="10%" class="list_head" align="center">
				付费帐户
			</td>
			<td width="10%" class="list_head" align="center">
				状态
			</td>
			<td width="18%" class="list_head" align="center">
				操作
			</td>
		</tr>
		<%
				String[] cancelCpID = request.getParameterValues("cancelCpID");
				ArrayList arrCancelCpID = new ArrayList();
				for (int i = 0; cancelCpID != null && i < cancelCpID.length; i++) {
					arrCancelCpID.add(cancelCpID[i]);
				}
				Iterator spareCpIt = cancelCpMap.keySet().iterator();
				while (spareCpIt.hasNext()) {
					CustomerProductDTO cpDto = (CustomerProductDTO) spareCpIt
					.next();
					ProductDTO pDto = (ProductDTO) cancelCpMap.get(cpDto);
					pageContext.setAttribute("cpDto", cpDto);
					pageContext.setAttribute("pDto", pDto);
		%>
		<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
			<td align="center">
				<tbl:write name="cpDto" property="psID" />
			</td>
			<td align="center">
				<tbl:write name="cpDto" property="serviceAccountID" />
			</td>
			<td align="center">
				<tbl:write name="pDto" property="productName" />
			</td>
			<td align="center">
				<tbl:write name="cpDto" property="accountID" />
			</td>
			<td align="center">
				<d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="cpDto:status" />
			</td>
			<td align="center">
				取消
			</td>
		</tbl:printcsstr>
		<%
		}
		%>
		<tr>
			<td colspan="6" class="list_foot"></td>
		</tr>
	</table>
	<%
		}
		String []addProductId2PackageId=request.getParameterValues("txtProductID2PackageID");
		String productids="",packageids="";
		Map pd2paMap=new HashMap();
		for(int i=0;addProductId2PackageId!=null&&i<addProductId2PackageId.length;i++){
			String pp=addProductId2PackageId[i];
			String []arrPP=pp.split("-");
			Integer productID=new Integer(arrPP[0]!=null?arrPP[0]:"0"); 
			Integer packageID=new Integer(arrPP.length==2&&arrPP[1]!=null?arrPP[1]:"0"); 
			pd2paMap.put(productID,packageID);
			if(productID!=null&&productID.intValue()!=0){
				productids+=productID+",";
			}
			if(packageID!=null&&packageID.intValue()!=0){
				packageids+=packageID+",";
			}
		}
		System.out.println("packageids:"+packageids);
		System.out.println("productids:"+productids);
		Map packageMap=Postern.getProductPackageMapByPackageIDList(packageids);
		Map productMap=Postern.getProductMapByProductIDList(productids);
		System.out.println("productMap:"+productMap);
		if (productMap != null && !productMap.isEmpty()) {
	%>
	<table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">新增加的产品</font></td>
     </tr>
  </table>	
	<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
		<tr class="list_head">
			<td width="10%" class="list_head" align="center">
				ID
			</td>
			<td width="30%" class="list_head" align="center">
				产品包
			</td>
			<td class="list_head" align="center">
				产品名称
			</td>
		</tr>
		<%
				Iterator addIt = productMap.keySet().iterator();
				while (addIt.hasNext()) {
					Object productID = addIt.next();
					ProductDTO pDto = (ProductDTO) productMap.get(productID);
					Object packageDto = packageMap.get(pd2paMap.get(productID));
					pageContext.setAttribute("pDto", pDto);
					if (packageDto != null) {
				pageContext.setAttribute("packageDto", packageDto);
					} else {
				pageContext.removeAttribute("packageDto");
					}
		%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center">
					<tbl:write name="pDto" property="productID" />
				</td>
				<td align="center">
					<tbl:write name="packageDto" property="packageName" />
				</td>
				<td align="center">
					<tbl:write name="pDto" property="productName" />
				</td>
			</tbl:printcsstr>
			<%
			}
			%>
		<tr>
			<td colspan="3" class="list_foot"></td>
		</tr>
	</table>

     <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>	
		<%
				addIt = productMap.values().iterator();
				while (addIt.hasNext()) {
					ProductDTO pDto = (ProductDTO) addIt.next();
					String pid=pDto.getProductID()+"";
		%>
		<tbl:productproperty serveyName="txtProductProperty" productID="<%=pid%>"
			showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
		<%
		}
		%>
	</table>
	<%
	}
	%>
<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BDS%>" acctshowFlag ="true" confirmFlag="true"/>	
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
		<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20"></td>
		<lgc:hasnoterror>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:button_next()" class="btn12">确&nbsp;认</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	  </lgc:hasnoterror>
	</tr>
</table>
<tbl:generatetoken />	  
<input type="hidden" name="txtActionType"  value="bundleTransfer" >
<input type="hidden" name="txtDoPost"  value="true" >
<tbl:hiddenparameters pass="txtCustomerID/txtcsiPayOption/txtCsiCreateReason/txtIsReturn/txtDeviceFee"/>
<tbl:hiddenparameters pass="txtCampaignID/cancelCpID/txtProductID/TerminalDeviceList/deviceModelSerialNo/txtProductID2PackageID" />
<tbl:hiddenparameters pass="txtGrade/itemID/phoneNo"/>

</form>

