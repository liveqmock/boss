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
<%@ page import="com.dtv.oss.dto.CustomerDTO"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<div id="updselwaitlayer"
	style="position: absolute; left: 650px; top: 150; width: 250px; height: 24px; display: none"></div>

<iframe SRC="get_mac_intermac_batchbuy.screen" name="FrameVendorDeviceUS"
	width="0" height="0" frameborder="0" scrolling="0"> </iframe>
<iframe SRC="iframeFileUpload.screen" name="FrameFileUpload"
	width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<table align="center" width="100%" border="0" cellspacing="0"
	cellpadding="0" class="list_bg">
	<tr>
		<td width="100%">
		<%
String fillContent="";
		
String buyInfoContent = request.getParameter("txtBuyContent");
if(buyInfoContent!=null&&!"".equals(buyInfoContent)){
	%>
		<table width="100%" border="0" align="center" cellpadding="4"
			cellspacing="1" class="list_bg">
			<tr>
				<td width="100%" class="import_tit" align="center">为购买的产品选择硬件</td>
				<td width="20px" class="import_tit" align="right">
					<A href="javascript:showSerialInput()"><IMG id="arr1" alt="快速输入" src="img/icon_bottom.gif" border=0></A>
				</td>
			</tr>
		</table>
		<table align="center" width="100%" border="0" cellspacing="1"
			cellpadding="3" class="list_bg" id="SerialInput" style="display:none">
			<tr>
				<td width="20%" align="center" class="list_bg2">
					<div>快速输入设备序列号</div><br>
					<div align="left">
						<font color="#FF0000">
							1.每套购买占一行,多个设备间以"/"或空格分隔;<br>
							2.设备输入顺序参照购买列表设备出现顺序;<br>
							3.输入完成,点击确认.<br>
						</font>
					</div>
					</td>
				<td class="list_bg1">
					<textarea id="txtSerialNoCollection"  length="5" cols=80 rows=9></textarea>
					<input type="button" name="uploadButton" onclick="inputSerialNo(document.getElementById('txtSerialNoCollection'))" value="确认">
				</td>
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
	HashMap phoneItemMap=new HashMap();
	HashMap phoneGradeMap=new HashMap();
	ArrayList allPackageList=new ArrayList();
	DocumentBuilder parser = docBuilderFactory.newDocumentBuilder();
	Document doc = parser.parse(in);
	Element root=doc.getDocumentElement();
	NodeList buyGroups=root.getChildNodes();
	//测试用，修改debug为true可自动填充设备序列号,
	boolean debug=false;
	int buyCount=0;
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
		fillContent+=buyIndex+"_"+buyNum+",";
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
					String gradeControlName="",phoneControlName="",itemControlName="";
					String gradeValue="",phoneValue="",itemValue="";
					NamedNodeMap nodeAttributes=buyItem.getAttributes();
					if(nodeAttributes.getNamedItem("index")!=null
							&&nodeAttributes.getNamedItem("phoneNo")!=null
							&&nodeAttributes.getNamedItem("itemId")!=null){
						phoneIndex=nodeAttributes.getNamedItem("index").getNodeValue();
						gradeControlName="txtGrade_"+buyIndex+"&"+phoneIndex;
						phoneControlName="txtPhone_"+buyIndex+"&"+phoneIndex;
						itemControlName="txtItem_"+buyIndex+"&"+phoneIndex;
						phoneValue=nodeAttributes.getNamedItem("phoneNo").getNodeValue();
						gradeValue=nodeAttributes.getNamedItem("grade").getNodeValue();
						itemValue=nodeAttributes.getNamedItem("itemId").getNodeValue();
						phoneNoMap.put(phoneControlName,phoneValue);
						phoneItemMap.put(itemControlName,itemValue);
						phoneGradeMap.put(gradeControlName,gradeValue);
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
		String groupDeviceClassList="";
//		System.out.println("packageIdList>>>>>>>>>>>"+packageIdList);
//		System.out.println("macInfoMap>>>>>>>>>>>"+macInfoMap);
		ArrayList packageList=WebUtil.getIntegerListByString(packageIdList);
		allPackageList.addAll(packageList);
		
		//取当前产品包所需的硬件.
		Map deviceMap=Postern.getDeviceClassMapByPackageIDList(packageList);
		boolean bNeedPhoneNo=false;
		//取当前产品包支持的服务.
		ArrayList serviceIDList =Postern.getServiceIDListByPackageList(packageList);
		//测试用1
		List serialList=new ArrayList();
		if(debug){
			serialList=Postern.getSerialno(deviceMap,buyCount+1,buyCount+buyNum);
			buyCount+=buyNum;
			System.out.println("buyCount=============="+buyCount);
			System.out.println("buyNum=============="+buyNum);
			System.out.println("serialList=============="+serialList);
		}
		if(serviceIDList != null && serviceIDList.contains(new Integer(3)))
		{
			bNeedPhoneNo = true;
		}
		//if(deviceMap==null||deviceMap.isEmpty())continue;
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
			if(deviceMap!=null&&!deviceMap.isEmpty()){
			for(Iterator dit=deviceMap.entrySet().iterator();dit.hasNext();){
				Entry de=(Entry) dit.next();
				Integer productId=(Integer) de.getKey();
				DeviceClassDTO dcDto=(DeviceClassDTO) de.getValue();
				String deviceClass=dcDto.getDeviceClass();
				if(n==1){
					groupDeviceClassList=("".equals(groupDeviceClassList)?"":groupDeviceClassList+",")+deviceClass;
				}
				String macInfoIndex=buyIndex+"&"+n+"="+deviceClass;
				String serialNo=serialNoMap.get(macInfoIndex)!=null?(String)serialNoMap.get(macInfoIndex):"";
				//测试用．
				if(debug){
					if(serialNo==null||"".equals(serialNo)){
						Map serialMap=serialList.get(n-1)!=null?(Map)serialList.get(n-1):new HashMap();
						serialNo=(String)serialMap.get(deviceClass);
					}
				}
				%>
					<tr>
						<td width="20%" class="list_bg1" id="<%=macInfoIndex%>_desc"><%=dcDto.getDescription() %>
						序列号</td>
						<td width="35%" class="list_bg1"><input type="text"
							name="<%=macInfoIndex%>" id="<%=macInfoIndex%>" size="25"
							value="<%=serialNo%>"
							onchange="javascript:getMacInmac('<%=deviceClass %>','<%=macInfoIndex %>')">
						<input type="button" value="选择" class="button"
							onclick="javascript:query_device_item('<%=productId.intValue() %>','<%=deviceClass %>','','<%=macInfoIndex %>');">
						</td>
						<td class="list_bg1"><span id="<%=macInfoIndex+"_" %>div"
							name="sspan"><%=macInfoMap.get(macInfoIndex)!=null?macInfoMap.get(macInfoIndex):"" %></span></td>
					</tr>
					<%
			}
			}
			%>
					<%
			if(bNeedPhoneNo)
			{
				String gradeControlName="txtGrade_"+buyIndex+"&"+n;
				String phoneControlName="txtPhone_"+buyIndex+"&"+n;
				String itemControlName="txtItem_"+buyIndex+"&"+n;
				String gradeMatch=phoneGradeMap.get(gradeControlName)!=null?(String)phoneGradeMap.get(gradeControlName):"" ;
		%>
					<tr>
						<td class="list_bg1">号码级别</td>
						<td class="list_bg1"><d:selcmn name="<%=gradeControlName %>"
							id="<%=gradeControlName %>" mapName="SET_R_PHONENOGRADE"
							match="<%=gradeMatch %>" width="23" /></td>
						<td class="list_bg1"></td>
					</tr>
					<tr>
						<td class="list_bg1">电话号码</td>
						<td class="list_bg1"><input type="text"
							id="<%=phoneControlName %>" size="25" maxlength="50"
							value="<%=phoneNoMap.get(phoneControlName)!=null?phoneNoMap.get(phoneControlName):"" %>">
						<input name="Submit" type="button" class="button" value="查询"
							onclick="javascript:phoneNo_Search('<%=gradeControlName %>',
					'<%=phoneControlName %>','<%=itemControlName %>')">
						</td>
						<td class="list_bg1">支持模糊查询，“_”代表一位，“%”代表多位。</td>
					</tr>
					<input type="hidden" id="<%=itemControlName %>"
						value="<%=phoneItemMap.get(itemControlName)!=null?phoneItemMap.get(itemControlName):"" %>">
					<% }%>
					<%
		for(int pi=0;packageList!=null&&pi<packageList.size();pi++){
			Integer pid=(Integer)packageList.get(pi);
			String propertyName="txtProductProperty_"+buyIndex+"&"+n ;
		%>
					<tbl:productproperty serveyName="<%=propertyName %>"
						packageID="<%=pid.toString()%>" showType="text"
						tdWordStyle="list_bg1" wordAlign="left" tdControlStyle="list_bg1"
						controlSize="25" headStyle="none" column="3" />
					<%} %>
				</table>
				</td>
			</tr>
		</table>


		<%
		}
		%> <input type="hidden" id="txtGroupDeviceClassList_<%=buyIndex %>"
			value="<%=groupDeviceClassList %>"> <br><%
	}
		//全部的购买产品包,在费用调整时用.
	%> <input type="hidden" name="txtAllPackageList"
			value="<%=WebUtil.getStringByAbstractCollection(allPackageList) %>">
		<%
}
%>
		</td>
	</tr>
</table>

<!--文件导入设备序列号时使用,为购买组号和购买数量.-->
<input type="hidden" id="txtFillContent"
	value="<%=fillContent%>">
<Script language="JavaScript">
 <!--
function showSerialInput(){
	var si=document.getElementById("SerialInput");
	if(si.style.display=="none"){
		si.style.display="";
		document.getElementById("arr1").src="img/icon_top.gif";
	}else{
		si.style.display="none";
		document.getElementById("arr1").src="img/icon_bottom.gif";
	}
}
function inputSerialNo(serialIn)
{
	serialCol=superTrim(serialIn.value);
	if(!serialCol){
		alert("没有有效的内容.");
		return;
	}
	//记录购买的组和数量 ,以逗号分隔,
	var serialInput=document.getElementById('txtFillContent').value;
	var siArr=serialInput.split(",");
	//输入框内容
	var serialArr=serialCol.split("\n");
	//购买组循环,li行索引
	var li=0;
	for(var sii=0;sii<siArr.length;sii++){
		var groupInfo=siArr[sii].split("_");
		var groupIndex=groupInfo[0];
		var groupBuyNum=groupInfo[1];
		//本组购买设备
		var groupDCList=document.getElementById('txtGroupDeviceClassList_'+groupIndex).value;
		var dcArr=groupDCList.split(",");
		//购买套循环,
		for(var bi=1;bi<=groupBuyNum;bi++){
			if(!serialArr[li])return;
			
			var lineSerial=serialArr[li];
			var lineArr=lineSerial.replace(new RegExp(" |\t+","g"),'/').split("/");
			var isPhone=false;
			//每行输入循环,
			for(var sli=0;sli<lineArr.length;sli++){
				var tempSerialNo=superTrim(lineArr[sli]);
				if(sli<dcArr.length){
					var inputName=groupIndex+"&"+bi+"="+dcArr[sli];
					var inputObj=document.getElementById(inputName);
					if(inputObj){
						inputObj.value=tempSerialNo;
						//getMacInmac(dcArr[sli],inputName);//调用检查方法,因为太连续,上一个检查请求未完成,而当前请求发起的时候,服务器取消上一个请求.
					}
				}
				var phoneName="txtPhone_"+groupIndex+"&"+bi;
				var phoneObj=document.getElementById(phoneName);
				var itemName="txtItem_"+groupIndex+"&"+bi;
				var itemObj=document.getElementById(itemName);
				if(itemObj){itemObj.value="";}
				if(phoneObj&&sli==(dcArr.length=1)){
					isPhone=true;
					phoneObj.value=tempSerialNo;
				}
			}
			//判断本行输入够数不,
			if((isPhone&&dcArr.length+1!=lineArr.length)||(!isPhone&&dcArr.length!=lineArr.length)){
				alert("第"+(li+1)+"行输入不对应第"+groupIndex+"组第"+bi+"套购买.");
				var ran=serialIn.createTextRange();
				ran.findText(lineSerial);
				ran.select();
				return ;
			}
			li++;
		}
	}
} 

function superTrim(para){
	if(para==null||para=='')
		return '';
	var newStr=para.substring(para.length-1,para.length);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(0,para.length-1);
		newStr=para.substring(para.length-1,para.length);
	}

	newStr=para.substring(0,1);
	while(newStr=='\n'||newStr==' '||newStr.charCodeAt(0)==13){
		para=para.substring(1,para.length);
		newStr=para.substring(0,1);
	}

	return para;
}
-->
</Script>
<!--购买信息在此!-->
<input type="hidden" name="txtBuyContent"
	value="<tbl:writeparam name="txtBuyContent" />">

<Script language="JavaScript">
 <!--
 
 	var xmlBuyContent;
function phoneNo_Search(grade,phone,item)
{
  var  phoneNo= '';
  if(document.getElementById(phone)){
	  phoneNo=document.getElementById(phone).value;
  }
  var  countyID ='';
  if(document.getElementsByName('txtDistrictID')[0]){
  	countyID =document.getElementsByName('txtDistrictID')[0].value;
  }
  if(countyID==null||countyID==''){
  	alert('没有找到有效的区域信息!');
  	return ;
  }
  var  grade='';
  if(document.getElementById(grade)){
  	grade=document.getElementById(grade).value;
  }
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  var res=showModalDialog("phoneNo_query_batchbuy.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+countyID+"&grade="+grade,"电话号码查询",strFeatures);
  var arrRes=res.split(',');
  document.getElementById(phone).value=arrRes[0];
  document.getElementById(item).value=arrRes[1];
}
 	
//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	 strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	 txtCsiCreateReason="";
   if(document.frmPost.txtCsiCreateReason)
      txtCsiCreateReason=document.frmPost.txtCsiCreateReason.value;
	 var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass
	                           +"&txtProductId="+txtProductId+"&txtDevicemodel="+txtDevicemodel
	                           +"&txtCsiType=UO&txtCsiCreateReason="+txtCsiCreateReason,window,strFeatures);
	 if (result!=null){
	 		document.getElementById(inputObject).value=result;
	 	}
	 getMacInmac(deviceClass,inputObject);
}
function getMacInmac(deviceClass,inputObject){
	//alert("deviceClass:"+deviceClass+"   inputObject:"+inputObject);
  document.FrameVendorDeviceUS.submit_update_vendor_device(inputObject,deviceClass);
}

	function saveBuyContent(content){
		document.all("txtBuyContent").value=content.replace(new RegExp("<","g"),'\[').replace(new RegExp(">","g"),'\]').replace(new RegExp("\"","g"),'\'');
	}
	
	function getBuyContent(){
		var strBuyContext=document.all("txtBuyContent").value;
		strBuyContext=strBuyContext.replace(new RegExp("\\[","g"),"<").replace(new RegExp("\\]","g"),">");
		return strBuyContext;
	} 

//检查选择的设备和电话号码属性等内容.如果正确,则形成xml,
  function checkDeviceInfo(){
  	var xmlGroupList=xmlBuyContent.selectSingleNode('/groups').childNodes;
  	for(i=0;i<xmlGroupList.length;i++){
  		var xmlBuyGroup=xmlGroupList[i];
  		//alert(xmlBuyGroup.xml);
	  	var buyIndex=xmlBuyGroup.getAttribute("index");
	  	var buyNum=parseInt(xmlBuyGroup.getAttribute("buyNum"));
	    
	    //清楚掉这几种节点 ,重新设置
	    var itemList=xmlBuyGroup.childNodes;
	    for(del=itemList.length-1;del>=0;del--){
	    	var delItem=itemList[del];
	    	var delName=delItem.nodeName;
	    	if('devices'==delName||'phone'==delName||'propertys'==delName){
	    		xmlBuyGroup.removeChild(delItem);
	    	}
	    }
	    
	    var groupDeviceClassList=document.getElementById('txtGroupDeviceClassList_'+buyIndex).value.split(',');
	    //遍历购买组
	    for(bn=1;bn<=buyNum;bn++){
	    	//每一组的设备
	    	var xmlDevices=xmlBuyContent.createElement("devices");
	    	xmlDevices.setAttribute('index',bn);
	    	for(di=0;di<groupDeviceClassList.length;di++){
		    	var xmlDevice=xmlBuyContent.createElement("device");
	    		var deviceClass=groupDeviceClassList[di];
	    		if(''==deviceClass){continue;}
			    var deviceInputId=buyIndex+'&'+bn+'='+deviceClass;
			    var deviceDesc=document.getElementById(deviceInputId+'_desc').innerHTML;
			    var errorDesc="第"+buyIndex+"组,第"+bn+"套,"+deviceDesc;
					if (check_Blank(document.getElementById(deviceInputId), true, errorDesc)){
	        	return false;
					}
			    xmlDevice.setAttribute('deviceClass',deviceClass);
			    var serialNo=document.getElementById(deviceInputId).value;
			    xmlDevice.setAttribute('serialNo',serialNo);
	    		var deviceInfo=document.getElementById(deviceInputId+'_div').innerHTML;
			    xmlDevice.setAttribute('deviceInfo',deviceInfo);
			    if(!check_repeat(xmlDevice)){
			    	alert(errorDesc+'已经被选用了,不能重复使用!');
			    	document.getElementById(deviceInputId).focus();
			    	return false;
			    }
			    xmlDevices.appendChild(xmlDevice);
	    	}
	    	
	    	//ip电话
		    var xmlPhone=xmlBuyContent.createElement("phone");
		    xmlPhone.setAttribute('index',bn);
		    var phoneControlName='txtPhone_'+buyIndex+'&'+bn;
		    var itemControlName="txtItem_"+buyIndex+"&"+bn;
		    var gradeControlName="txtGrade_"+buyIndex+"&"+bn;
		    var phoneControl=document.getElementById(phoneControlName);
		    var itemControl=document.getElementById(itemControlName);
		    var gradeControl=document.getElementById(gradeControlName);
		    if(phoneControl!=null){
		    	var errorDesc="第"+buyIndex+"组,第"+bn+"套,电话号码";
					if (check_Blank(phoneControl, true, errorDesc)){
	        	return false;
					}
			    xmlPhone.setAttribute('phoneNo',phoneControl.value);
			    xmlPhone.setAttribute('itemId',itemControl?itemControl.value:"");
			    xmlPhone.setAttribute('grade',gradeControl?gradeControl.value:"");
			    if(!check_repeat(xmlPhone)){
			    	alert(errorDesc+'已经被选用了,不能重复使用!');
			    	phoneControl.focus();
			    	return false;
			    }
		    	xmlBuyGroup.appendChild(xmlPhone);
		    }

	    	//产品属性
	    	var xmlPropertys=xmlBuyContent.createElement("propertys");
	    	xmlPropertys.setAttribute('index',bn);
	    	var proControlName='txtProductProperty_'+buyIndex+'&'+bn;
	    	var proHeadControl=document.getElementsByName(proControlName);
	    	if(proHeadControl!=null){
	    		for(phi=0;phi<proHeadControl.length;phi++){
		    		var proHeadValue=proHeadControl[phi].value;
		    		var arrHeadValue=proHeadValue.split(';');
		    		for(pri=0;pri<arrHeadValue.length;pri++){
		    			if(arrHeadValue[pri]!=null&&arrHeadValue[pri]!=''){
		    				var tempArr=arrHeadValue[pri].split('_');
		    				var proProductId=tempArr.length>0?tempArr[0]:'';
		    				var proPropertyCode=tempArr.length>1?tempArr[1]:'';
		    				var proItemControlName=proControlName+'_'+arrHeadValue[pri];
		    				var proItemControl=document.getElementById(proItemControlName);
		    				if (check_Blank(proItemControl, true, '属性')){
				        	return false;
								}
								var proItemValue=proItemControl.value;
								var xmlProperty=xmlBuyContent.createElement("property");
								xmlProperty.setAttribute('productId',proProductId);
								xmlProperty.setAttribute('propertyCode',proPropertyCode);
								xmlProperty.setAttribute('value',proItemValue);
						    if('30002'==proPropertyCode&&!check_repeat(xmlProperty)){
						    	alert('用户名不能重复!');
						    	proItemControl.focus();
						    	return false;
								}
								xmlPropertys.appendChild(xmlProperty);
		    			}
		    		}
		    	}
	    	}
    		xmlBuyGroup.appendChild(xmlPropertys);
	    	xmlBuyGroup.appendChild(xmlDevices);
	    }
  	}
  	saveBuyContent(xmlBuyContent.xml);
  	return true;
  } 
  //检查有没有重复选择的设备和号码
  function check_repeat(node){
  	var nodeName=node.nodeName;
  	var idv='';
  	var attName='';
  	if('device'==nodeName){
  		idv=node.getAttribute('serialNo');
  		attName="//device[@serialNo='";
  	}
  	if('phone'==nodeName){
  		idv=node.getAttribute('phoneNo');
  		attName="//phone[@phoneNo='";
  	}
  	if('property'==nodeName){
  		idv=node.getAttribute('value');
  		attName="//property[@productId='"+node.getAttribute('productId')+"'][@propertyCode='"+node.getAttribute('propertyCode')+"'][@value='";
  	}
  	var deviceNode=xmlBuyContent.selectSingleNode(attName+idv+"']");
  	if(deviceNode!=null){
//	  	alert(deviceNode.xml);
  		return false;
  	}

		return true;
  }
	function getXMLDOM(){
		var xmldomversions = ['MSXML2.DOMDocument.5.0', 'MSXML2.DOMDocument.4.0', 'MSXML2.DOMDocument.3.0','MSXML2.DOMDocument', 'Microsoft.XMLDOM'];
		for(var i=xmldomversions.length-1;i>=0;i--)
			try{
				return new ActiveXObject(xmldomversions[i]);
			}catch(e){
			}
		return document.createElement("XML");
	}


	function parseXML(){
		var strBuyContext=getBuyContent();

    xmlBuyContent = getXMLDOM();
    if(xmlBuyContent){
        xmlBuyContent.loadXML(strBuyContext);
    }else{
        var parser = new DOMParser();
        xmlBuyContent = parser.parseFromString(strBuyContext, "text/xml");
    }

	}

	parseXML();  
-->
</Script>