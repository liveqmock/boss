<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<Script language=JavaScript>
<!--
function button_next(){
	if(document.frmPost.txtIsReturn!=null){
		if (check_Blank(document.frmPost.txtIsReturn, true, "旧设备是否退还"))
			return ;
		if(!check_Float(document.frmPost.txtDeviceFee,true,"设备费")){
			return ;
		}			
	}
		if(check_ProductProperty()){
			document.frmPost.action="cust_bundle_transfer_device.do";
			document.frmPost.submit();	
		}
}
	function back_submit(){
			document.frmPost.action='cust_bundle_transfer_select.do';
			document.frmPost.submit();
	}
	
function check_ProductProperty(){
	for(i=0;i<document.frmPost.length;i++){
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text" ||(""+element.type)=="password"){
			var strEleName = "" + element.name;
			var arrEleName = element.name.split("_");
			if( arrEleName[0] == "txtProductProperty"){
				if(element.value==""){
				   alert("产品属性输入不完整，请重新检查！");
					 element.focus();
					 return false;
				}
			}
			
		}
	}
	return true;
}	
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐转换-产品信息</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
	<%
		int ccid = Integer.parseInt(request.getParameter("txtCcID"));
		int campaignid = Integer.parseInt(request
				.getParameter("txtCampaignID"));
		//CustomerCampaignDTO ccDto=Postern.getCustomerCampaignByccID(ccid);
		ArrayList allCpInfoList = Postern
				.getAllCustomerProductInfoWithCustomerCampaign(ccid);
		//套餐定义的必须的产品.
		Map campaignAgmtProductMap = Postern
				.getCampaignAgmtProductMap(campaignid);
		Map campaignAgmtPackageMap= Postern.getCampaignAgmtPackageMap(campaignid);
		System.out.println("campaignAgmtProductMap:>>>>>>>>>>>"
				+ campaignAgmtProductMap.size());
		if(campaignAgmtProductMap!=null&&!campaignAgmtProductMap.isEmpty()){
			campaignAgmtPackageMap.put(new Integer(0),campaignAgmtProductMap);
		}
		System.out.println("campaignAgmtPackageMap:>>>>>>>>>>>"
				+ campaignAgmtPackageMap.size());
		//旧套餐中使用,新套餐中不使用的产品
		LinkedHashMap spareCPMap = new LinkedHashMap();
		HashMap cancelDeviceMap=new HashMap();
		//新套餐中定义,旧的相关业务帐户没有包含的产品
		LinkedHashMap addProductMap = new LinkedHashMap();
		LinkedHashMap curAllProductMap = new LinkedHashMap();
		//ArrayList allCpList=new ArrayList();
		//把当前套餐相关业务帐户下所有产品拿出来,
		for (int i = 0; i < allCpInfoList.size(); i++) {
			Object[] arrInfo = (Object[]) allCpInfoList.get(i);
			CustomerProductDTO cpDto = (CustomerProductDTO) arrInfo[0];
			ProductDTO pDto = (ProductDTO) arrInfo[1];
			Integer productID=new Integer(pDto.getProductID());
			curAllProductMap.put(productID,pDto);
		}

		String packageids="";
		ArrayList allCampaignAgmtProductList=new ArrayList();
		ArrayList customerCampaignProductList=new ArrayList();
		//遍历新套餐定义的产品包和产品,找出没有包含在套餐协议中的产品,
		Iterator agmtIt=campaignAgmtPackageMap.keySet().iterator();
		while(agmtIt.hasNext()){
			Integer	packageID=(Integer)agmtIt.next();
			packageids=packageids+packageID;
			if(agmtIt.hasNext()){
				packageids=packageids+",";
			}
			Map productMap=(LinkedHashMap)campaignAgmtPackageMap.get(packageID);
			Iterator pIt=productMap.keySet().iterator();
			while(pIt.hasNext()){
				Integer productID=(Integer)pIt.next();
				allCampaignAgmtProductList.add(productID);
				Object curProduct=productMap.get(productID);
				if (!curAllProductMap.containsKey(productID)) {
					addProductMap.put(curProduct,packageID);
				}
			}
		}
		System.out.println("allCampaignAgmtProductList:" + allCampaignAgmtProductList.size());
		//遍历当前套餐相关业务帐户下产品,如果不在新套餐协议产品集合中,则当是取消产品
		for (int i = 0; i < allCpInfoList.size(); i++) {
			Object[] arrInfo = (Object[]) allCpInfoList.get(i);
			CustomerProductDTO cpDto = (CustomerProductDTO) arrInfo[0];
			ProductDTO pDto = (ProductDTO) arrInfo[1];
			Integer productID=new Integer(pDto.getProductID());
			Integer cpmid=(Integer)arrInfo[3];
			if(cpmid!=null&&cpmid.intValue()!=0){
				customerCampaignProductList.add(productID);
			}
			if(!allCampaignAgmtProductList.contains(productID)){
				spareCPMap.put(cpDto,pDto);
				if(arrInfo[4]!=null&&!"Y".equals(pDto.getNewsaFlag())){
					cancelDeviceMap.put(cpDto,arrInfo[4]);
				}
			}
		}
		Map packageMap=Postern.getProductPackageMapByPackageIDList(packageids);
		System.out.println("addProductMap:"+addProductMap.values());
		System.out.println("addProductMap:" + addProductMap.size());
		System.out.println("spareCpMap:" + spareCPMap.size());
		System.out.println("cancelDeviceMap:" + cancelDeviceMap.size());
		if (spareCPMap != null && !spareCPMap.isEmpty()) {
	%>
	<table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
		<tr>
			<td class="import_tit" align="center">
				<font size="3">旧的客户产品</font>
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
				Iterator spareCpIt = spareCPMap.keySet().iterator();
				while (spareCpIt.hasNext()) {
					CustomerProductDTO cpDto = (CustomerProductDTO) spareCpIt
					.next();
					ProductDTO pDto = (ProductDTO) spareCPMap.get(cpDto);
					Integer productID=new Integer(pDto.getProductID());
					boolean isCancel=false;
					if(!customerCampaignProductList.contains(productID)){
						isCancel=true;
					}
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
				<%if(isCancel){%>
				保留
				<%}else{%>
				取消
				<input type="hidden" name="cancelCpID" value="<tbl:write name="cpDto" property="psID"/>">
				<%}%>
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
	}%>
	
	<%if(cancelDeviceMap!=null&&!cancelDeviceMap.isEmpty()){%>
	  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	     <tr>
	         <td class="import_tit" align="center"><font size="3">涉及的硬件设备</font></td>
	     </tr>
	  </table>	
	<table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
		<tr class="list_head"> 
			<td width="8%" class="list_head"><div align="center">设备ID</div></td>
			<td width="8%" class="list_head"><div align="center">PSID</div></td>
			<td width="15%" class="list_head"><div align="center">设备类型</div></td>                    
			<td width="20%" class="list_head"><div align="center">型号</div></td> 
			<td width="15%" class="list_head"><div align="center">序列号</div></td>         
			<td width="6%" class="list_head"><div align="center">状态</div></td>
			<td class="list_head"><div align="center">MAC地址</div></td>
			<td class="list_head"><div align="center">内部MAC地址</div></td>
		</tr> 
	<%
	Iterator terIt=cancelDeviceMap.keySet().iterator();
	while (terIt.hasNext()){
		CustomerProductDTO cpDto=(CustomerProductDTO)terIt.next();
		TerminalDeviceDTO terDto=(TerminalDeviceDTO)cancelDeviceMap.get(cpDto);
		pageContext.setAttribute("cpDto",cpDto);
		pageContext.setAttribute("terDto",terDto);
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		<td align="center" ><tbl:write name="terDto" property="deviceID"/></td>
		<td align="center" ><tbl:write name="cpDto" property="psID"/></td>
		<td align="center" ><tbl:write name="terDto" property="deviceClass"/></td>
		<td align="center" ><tbl:write name="terDto" property="deviceModel"/></td>
		<td align="center" ><tbl:write name="terDto" property="serialNo"/></td>
		<td align="center" ><d:getcmnname typeName="SET_D_DEVICESTATUS" match="terDto:status" /></td>
		<td align="center" ><tbl:write name="terDto" property="mACAddress"/></td>
		<td align="center" ><tbl:write name="terDto" property="interMACAddress"/></td>
	</tbl:printcsstr>
	<%}%>
	  <tr>
	    <td colspan="8" class="list_foot"></td>
	  </tr>
	  <!--add by david.Yang begin-->
	  <input type="hidden" name="txtIsReturn" value="N" >
	  <input type="hidden" name="txtDeviceFee" >
	  <!--add by david.Yang end-->
	</table>
	
	<!-- modify by david.Yang begin
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">旧设备是否退还*</td>
          <td class="list_bg1"  align="left" width="33%">
          <d:selcmn name="txtIsReturn" mapName="SET_G_YESNOFLAG" match="txtIsReturn" width="23" />
          </td>
          <td class="list_bg2" align="right" width="17%">设备费</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtDeviceFee" size="25"  value="<tbl:writeparam name="txtDeviceFee"/>">
          </td> 
        </tr>
			</table>
 --   modify by david.Yang end-->
        	
	<%}%>	
	
	<%
	if (addProductMap != null && !addProductMap.isEmpty()) {
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
		<%Iterator addIt=addProductMap.keySet().iterator();
		while(addIt.hasNext()){
		Object product=addIt.next();
		Integer packageID=(Integer)addProductMap.get(product);
		Object packageDto=packageMap.get(packageID);
		pageContext.setAttribute("pDto",product);
		if(packageDto!=null){
			pageContext.setAttribute("packageDto",packageDto);
		}else{
			pageContext.removeAttribute("packageDto");
		}
		%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
				<td align="center">
					<tbl:write name="pDto" property="productID" />
					<input type="hidden" name="txtProductID" value="<tbl:write name="pDto" property="productID" />">
					<input type="hidden" name="txtProductID2PackageID" 
					value="<tbl:write name="pDto" property="productID" />-<tbl:write name="packageDto" property="packageID" />">
				</td>
				<td align="center">
					<tbl:write name="packageDto" property="packageName" />
					<input type="hidden" name="txtPackageID" value="<tbl:write name="packageDto" property="packageID" />">
				</td>
				<td align="center">
					<tbl:write name="pDto" property="productName" />
				</td>
			</tbl:printcsstr>
			<%} %>
		<tr>
			<td colspan="3" class="list_foot"></td>
		</tr>
	</table>

     <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>	
		<%Iterator proIt=addProductMap.keySet().iterator();
		while(proIt.hasNext()){
		ProductDTO pDto = (ProductDTO) proIt.next();
		String pid=pDto.getProductID()+"";
		%>
		<tbl:productproperty serveyName="txtProductProperty" productID="<%=pid%>"
			showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
		<%} %>
<%} %>
	</table>

	<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  <td width="20" ></td>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:button_next()" class="btn12">下一步</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	</tr>
</table>
<input type="hidden" name="txtActionType"  value="bundleTransfer" >
<input type="hidden" name="txtDoPost"  value="false" >
<tbl:hiddenparameters pass="txtCcID/txtCampaignName/txtDtCreate/txtStatus/txtAccoutID/txtServiceAccoutID/txtDateFrom/txtDateTo/txtPaymentType/txtNbrDate/txtCustomerID"/>
<tbl:hiddenparameters pass="txtCampaignID/txtTransferDate/txtCampaignDateTo/txtNewCampaignName/txtCsiCreateReason" />
</form>

