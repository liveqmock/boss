<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="operator" prefix="op"%>

<%@ page
	import="java.util.*,java.util.Map.*,
java.io.ByteArrayInputStream,
javax.xml.parsers.DocumentBuilderFactory,
javax.xml.parsers.DocumentBuilder,
org.w3c.dom.Document,
org.w3c.dom.Element,
org.w3c.dom.NamedNodeMap,
org.w3c.dom.Node,
org.w3c.dom.NodeList"%>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<div id="updselwaitlayer"
	style="position: absolute; left: 650px; top: 150; width: 250px; height: 24px; display: none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS"
	width="0" height="0" frameborder="0" scrolling="0"> </iframe>
<table align="center" width="100%" border="0" cellspacing="0"
	cellpadding="0" class="list_bg">
	<tr>
		<td width="100%">
		<%
String buyInfoContent = request.getParameter("txtBuyContent");
if(buyInfoContent!=null&&!"".equals(buyInfoContent)){
	%>
		<table width="100%" border="0" align="center" cellpadding="4"
			cellspacing="1" class="list_bg">
			<tr>
				<td width="100%" class="import_tit" align="center">购买的产品和设备</td>
			</tr>
		</table>
		<%
	buyInfoContent=buyInfoContent.replaceAll("\\[", "<").replaceAll("\\]", ">").replaceAll("'", "\"");
//	System.out.println("buyInfoContent:"+buyInfoContent);
	
	ByteArrayInputStream in=new ByteArrayInputStream(buyInfoContent.getBytes("UTF-8"));
	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
			.newInstance();
	
	HashMap serialNoMap=new HashMap();
	HashMap macInfoMap=new HashMap();
	HashMap phoneNoMap=new HashMap();
	DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
	Document doc = parser.parse(in);
	Element root=doc.getDocumentElement();
	NodeList buyGroups=root.getChildNodes();
	for(int i=0;i<buyGroups.getLength();i++){
		%>
		<table align="center" width="100%" border="0" cellspacing="1"
			cellpadding="3" class="list_bg">
			<tr id="buyListHead" class="list_head">
				<td width="6%" class="list_head">序号</td>
				<td width="20%" class="list_head">套餐</td>
				<td width="20%" class="list_head">多产品产品包</td>
				<td width="20%" class="list_head">单产品产品包</td>
				<td width="20%" class="list_head">优惠</td>
				<td width="7%" class="list_head">数量</td>
			</tr>
			<tr>
				<% 
	String packageIdList="";
	Node buyGroup=buyGroups.item(i);
	int buyNum=0;
	String buyIndex="";
	if(buyGroup.hasAttributes()){
		NamedNodeMap buyGroupAttributes=buyGroup.getAttributes();
		buyNum=WebUtil.StringToInt(buyGroupAttributes.getNamedItem("buyNum").getNodeValue());
		buyIndex=buyGroupAttributes.getNamedItem("index").getNodeValue();
	}
	StringBuffer bundleHtml=new StringBuffer();
	StringBuffer mPackageHtml=new StringBuffer();
	StringBuffer sPackageHtml=new StringBuffer();
	StringBuffer campaignHtml=new StringBuffer();

	if(buyGroup.hasChildNodes()){
		NodeList buyList=buyGroup.getChildNodes();
		for(int j=0;j<buyList.getLength();j++){
			Node buyItem=buyList.item(j);
			String nodeName=buyItem.getNodeName();
			String buyId="";
			String buyName="";
			if(buyItem.hasAttributes()){
				NamedNodeMap nodeAttributes=buyItem.getAttributes();
				if(nodeAttributes.getNamedItem("id")!=null){
					buyId=nodeAttributes.getNamedItem("id").getNodeValue();
				}
				if(nodeAttributes.getNamedItem("name")!=null){
					buyName=nodeAttributes.getNamedItem("name").getNodeValue();
				}
				if("bundle".equalsIgnoreCase(nodeName)||"campaign".equalsIgnoreCase(nodeName)){
					String subPackageList=nodeAttributes.getNamedItem("packageList").getNodeValue();
					packageIdList=packageIdList+(packageIdList!=null&&!"".equals(packageIdList)&&packageIdList.endsWith(",")?"":",")+subPackageList;
				}else{
					packageIdList=packageIdList+(packageIdList!=null&&!"".equals(packageIdList)&&packageIdList.endsWith(",")?"":",")+buyId;
				}
			}

			if("bundle".equalsIgnoreCase(nodeName)){
				bundleHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
			}else if("mpackage".equalsIgnoreCase(nodeName)){
				mPackageHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
			}else if("spackage".equalsIgnoreCase(nodeName)){
				sPackageHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
			}else if("campaign".equalsIgnoreCase(nodeName)){
				campaignHtml.append(buyId).append("(").append(buyName).append(")").append("<br>");
			}
			if("devices".equalsIgnoreCase(nodeName)){
				//准备回填的设备信息.
				String devicesIndex="";
				if(buyItem.hasAttributes()){
					NamedNodeMap nodeAttributes=buyItem.getAttributes();
					if(nodeAttributes.getNamedItem("index")!=null){
						devicesIndex=nodeAttributes.getNamedItem("index").getNodeValue();
					}
				}
				if(buyItem.hasChildNodes()){
					NodeList deviceList=buyItem.getChildNodes();
					for(int di=0;di<deviceList.getLength();di++){
						Node deviceItem=deviceList.item(di);
						String key=buyIndex+"&"+devicesIndex+"=";
						if(deviceItem.hasAttributes()){
							NamedNodeMap nodeAttributes=deviceItem.getAttributes();
							if(nodeAttributes.getNamedItem("deviceClass")!=null){
								String deviceClass=nodeAttributes.getNamedItem("deviceClass").getNodeValue();
								key+=deviceClass;
							}
							if(nodeAttributes.getNamedItem("serialNo")!=null){
								String serialNo=nodeAttributes.getNamedItem("serialNo").getNodeValue();
								serialNoMap.put(key,serialNo);
							}
							if(nodeAttributes.getNamedItem("deviceInfo")!=null){
								String deviceInfo=nodeAttributes.getNamedItem("deviceInfo").getNodeValue();
								macInfoMap.put(key,deviceInfo);
							}
						}
						
					}
				}
				
			}
			//准备回填的设备信息.
			if("phone".equalsIgnoreCase(nodeName)){
				String phoneIndex="";
				if(buyItem.hasAttributes()){
					String phoneControlName="";
					String phoneValue="";
					NamedNodeMap nodeAttributes=buyItem.getAttributes();
					if(nodeAttributes.getNamedItem("index")!=null
							&&nodeAttributes.getNamedItem("phoneNo")!=null
							&&nodeAttributes.getNamedItem("itemId")!=null){
						phoneIndex=nodeAttributes.getNamedItem("index").getNodeValue();
						phoneControlName="txtPhone_"+buyIndex+"&"+phoneIndex;
						phoneValue=nodeAttributes.getNamedItem("phoneNo").getNodeValue();
						phoneNoMap.put(phoneControlName,phoneValue);
					}
				}
			}
			
		}
	}
	%>
				<td align="center" class="list_bg1"><%=buyIndex %></td>
				<td class="list_bg1"><%=bundleHtml.toString() %></td>
				<td class="list_bg1"><%=mPackageHtml.toString() %></td>
				<td class="list_bg1"><%=sPackageHtml.toString() %></td>
				<td class="list_bg1"><%=campaignHtml.toString() %></td>
				<td align="center" class="list_bg1"><%=buyNum %></td>
			</tr>
		  <tr>
		    <td colspan="6" class="list_foot"></td>
		  </tr>
		</table>
		<%
		System.out.println("packageIdList>>>>>>>>>>>"+packageIdList);
		System.out.println("macInfoMap>>>>>>>>>>>"+macInfoMap);
		ArrayList packageList=WebUtil.getIntegerListByString(packageIdList);
		
		//取当前产品包所需的硬件.
		Map deviceMap=Postern.getDeviceClassMapByPackageIDList(packageList);
		boolean bNeedPhoneNo=false;
		//取当前产品包支持的服务.
		ArrayList serviceIDList =Postern.getServiceIDListByPackageList(packageList);
		System.out.println("serviceIDList=============="+serviceIDList);
		if(serviceIDList != null && serviceIDList.contains(new Integer(3)))
		{
			bNeedPhoneNo = true;
		}
		if(deviceMap==null||deviceMap.isEmpty())continue;
		for(int n=1;n<=buyNum;n++){
%>
		<table align="center" width="100%" border="0" cellspacing="1"
			cellpadding="3" class="list_bg">
			<tr>
				<td width="10%" align="center" class="list_bg2">第<%=n %>套购买</td>
				<td class="list_bg1">
				<table align="center" width="100%" border="0" cellspacing="1"
			cellpadding="1" class="list_bg">
			<%
		boolean isFirstLine=true;
			int rowCount=deviceMap.size()+(bNeedPhoneNo?1:0);
			for(Iterator dit=deviceMap.entrySet().iterator();dit.hasNext();){
				Entry de=(Entry) dit.next();
				Integer productId=(Integer) de.getKey();
				DeviceClassDTO dcDto=(DeviceClassDTO) de.getValue();
				String deviceClass=dcDto.getDeviceClass();
				String macInfoIndex=buyIndex+"&"+n+"="+deviceClass;
				%>
			<tr>
				<td width="20%" class="list_bg1" id="<%=macInfoIndex%>_desc"><%=dcDto.getDescription() %>
				序列号</td>
				<td width="30%" class="list_bg1"><input type="text"
					name="<%=macInfoIndex%>" id="<%=macInfoIndex%>" size="25"
					value="<%=serialNoMap.get(macInfoIndex)!=null?serialNoMap.get(macInfoIndex):"" %>"
					class="textgray" readonly >
				</td>
				<td class="list_bg1"><span id="<%=macInfoIndex+"_" %>div"
					name="sspan"><%=macInfoMap.get(macInfoIndex)!=null?macInfoMap.get(macInfoIndex):"" %></span></td>
			</tr>
			<%
			}
			%>
			<%
			if(bNeedPhoneNo)
			{
				String phoneControlName="txtPhone_"+buyIndex+"&"+n;
		%>
			<tr>
				<td class="list_bg1">电话号码</td>
				<td class="list_bg1"><input type="text"
					id="<%=phoneControlName %>" size="25" maxlength="50" class="textgray" readonly
					value="<%=phoneNoMap.get(phoneControlName)!=null?phoneNoMap.get(phoneControlName):"" %>"> 
				</td>
				<td class="list_bg1"> </td>
			</tr>
			<% }%>
			<%
		for(int pi=0;packageList!=null&&pi<packageList.size();pi++){
			Integer pid=(Integer)packageList.get(pi);
			String propertyName="txtProductProperty_"+buyIndex+"&"+n ;
		%>
			<tbl:productproperty serveyName="<%=propertyName %>"
				packageID="<%=pid.toString()%>" showType="label"
				tdWordStyle="list_bg1" wordAlign="left" tdControlStyle="list_bg1" controlSize="25"
				headStyle="none" column="3"/>
			<%} %>
				</table>
				</td>
			</tr>
		</table>


		<%
		}
	}
}
%>
		</td>
	</tr>
</table>

<!--购买信息在此!-->
<input type="hidden" name="txtBuyContent"
	value="<tbl:writeparam name="txtBuyContent" />">

<Script language="JavaScript">
 <!--

-->
</Script>