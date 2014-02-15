<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>

<%
			String openCampaignFlag = (request.getAttribute("openCampaignFlag") == null) ? "true"
			: request.getAttribute("openCampaignFlag").toString();
%>
<style type="text/css">
<!--
A {
	COLOR: #111177
}
-->
</style>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

	<tr>
		<%
		if (openCampaignFlag.equals("true")) {
		%>
		<td align="right" width="1%"><img src="" id=opencampaign0 height="20"></td>
		<td height="20" id=opencampaign1 align="center" width="23%"><a href="javascript:changestyle(1)" id=opencampaign2 style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开户套餐活动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
		<td width="1%"><img src="" id=opencampaign3 height="20"></td>
		<%
		}
		%>

		<td align="right" width="1%"><img src="" id="mutiproduct0" height="20"></td>
		<td height="20" id="mutiproduct1" align="center" width="23%"><a href="javascript:changestyle(2)" id="mutiproduct2" style="text-decoration:none;color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;多产品的产品包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
		<td width="1%"><img src="" id="mutiproduct3" height="20"></td>

		<td align="right" width="1%"><img src="img/catch/btn_nav_l.gif" id="singleproduct0" height="20"></td>
		<td height="20" width="23%" id="singleproduct1" background="img/catch/btn_nav_body.gif" align="center"><a href="javascript:changestyle(3)" id="singleproduct2" style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单产品的产品包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
		<td width="1%"><img src="img/catch/btn_nav_r.gif" id="singleproduct3" height="20"></td>

		<td align="right" width="1%"><img src="img/catch/btn_nav_l.gif" id="generalcampaign0" height="20"></td>
		<td height="20" width="23%" id="generalcampaign1" background="img/catch/btn_nav_body.gif" align="center"><a href="javascript:changestyle(4)" id="generalcampaign2" style="text-decoration:none; color: #111177">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;优&nbsp;惠&nbsp;活&nbsp;动&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
		<td width="1%"><img src="img/catch/btn_nav_r.gif" id="generalcampaign3" height="20"></td>

		<%
		if (openCampaignFlag.equals("false")) {
		%>
		<td align="right" width="1%">&nbsp;</td>
		<td height="20" align="center" width="23%">&nbsp;</td>
		<td width="1%">&nbsp;</td>
		<%
		}
		%>

	</tr>

</table>
<table width="100%" valign="top" border="0" align="center" cellpadding="0" cellspacing="0">
	<%
	if (openCampaignFlag.equals("true")) {
	%>
	<tr id="prefered1">
		<td>
			<jsp:include page="list_opencampaign.jsp" />
		</td>
	</tr>
	<%
	}
	%>

	<%
	if (openCampaignFlag.equals("false")) {
	%>
	<tr id="prefered2">
		<%
		} else {
		%>
	
	<tr id="prefered2" style="display:none">
		<%
		}
		%>
		<td>
			<jsp:include page="list_mutiproduct.jsp" />
		</td>
	</tr>
	<tr id="prefered3" style="display:none">
		<td>
			<jsp:include page="list_singleproduct.jsp" />
		</td>
	</tr>
	<tr id="prefered4" style="display:none">
		<td>
			<jsp:include page="list_generalcampaign.jsp" />
		</td>
	</tr>
</table>
	
<table id="buyList" align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
	<tr id="buyListHead" class="list_head">
		<td width="6%" class="list_head">
			序号
		</td>
		<td width="20%" class="list_head">
			套餐
		</td>
		<td width="20%" class="list_head">
			多产品产品包
		</td>
		<td width="20%" class="list_head">
			单产品产品包
		</td>
		<td width="20%" class="list_head">
			优惠
		</td>
		<td width="7%" class="list_head">
			数量
		</td>
		<td width="7%" class="list_head">
			操作
		</td>
	</tr>
  <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="right">
			购买数量:
			<input type="text" name="txtBuyNum" value="1" size="6" maxlength="3">
			<input name="buyButton" id="buyButton" type="button" class="button" onClick="javascript:buy_click()" value="购买">
			<input name="resetButton" type="button" class="button" onClick="javascript:clearAllChoose()" value="重置">
		</td>
	</tr>
</table>
<input type="hidden" name="txtBuyContent" value="<tbl:writeparam name="txtBuyContent" />">
<iframe SRC="check_choose_product.screen" name="FrameCheckBuy"
	width="0" height="0" frameborder="0" scrolling="0"> </iframe>
	
<script language="Javascript">
	var openCampaignFlag ='<%=openCampaignFlag%>';
  function changestyle(styleflag){
     if (styleflag ==1){
     	  document.all("prefered1").style.display ="";
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="none";    
     	  fill_picture('opencampaign','true');
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','false');
	
     }else if (styleflag ==2){
     	  if (openCampaignFlag =='true'){  
     	      document.all("prefered1").style.display ="none";
     	      fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="none";    	      
     	  fill_picture('mutiproduct','true');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','false');
     }else if (styleflag ==3){
     	  if (openCampaignFlag =='true'){  
     	     document.all("prefered1").style.display ="none";
     	     fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="";
     	  document.all("prefered4").style.display ="none";    	     
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','true');
     	  fill_picture('generalcampaign','false');
     }else if (styleflag ==4){
     	  if (openCampaignFlag =='true') {
     	     document.all("prefered1").style.display ="none";
     	     fill_picture('opencampaign','false');
     	  }
     	  document.all("prefered2").style.display ="none";
     	  document.all("prefered3").style.display ="none";
     	  document.all("prefered4").style.display ="";
     	  fill_picture('mutiproduct','false');
     	  fill_picture('singleproduct','false');
     	  fill_picture('generalcampaign','true');
     }
  }
  
  
  function campaignchoose(clearPackageIds){

  }
  
  
  function view_package_detail(strID,strName){
    window.open('list_product_package_detail.do?txtPackageID='+strID+'&txtPackageName='+strName,'','width=300,height=250,resizable=no,toolbar=no,scrollbars=yes');
  }
 
  if (openCampaignFlag =='true'){
 	     fill_picture('opencampaign','true');
 	     fill_picture('mutiproduct','false');
  }else {
       fill_picture('mutiproduct','true');
  }
  
  
  function fill_picture(idName,chooseFlag){
  	 if (chooseFlag =='true'){
  	 	 // alert(document.getElementById(idName+"1").srcElement.style);
  	    document.getElementById(idName+"0").src='img/catch/btn_nav1_l.gif';
  	    document.getElementById(idName+"1").style.backgroundImage='url(img/catch/btn_nav1_body.gif)';
  	    document.getElementById(idName+"2").style.color ='#FFFFFF';
  	    document.getElementById(idName+"3").src='img/catch/btn_nav1_r.gif';
  	 }else{
  	 	  document.getElementById(idName+"0").src='img/catch/btn_nav_l.gif';
  	    document.getElementById(idName+"1").style.backgroundImage='url(img/catch/btn_nav_body.gif)';
  	    document.getElementById(idName+"2").style.color ='#111177';
  	    document.getElementById(idName+"3").src='img/catch/btn_nav_r.gif';
  	 }
  	
  }
 
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
  var buyIndex=0;
	var xmlBuyContent;
	var buyCount=0;
	/**
	* 引用页面调用的检查方法,用于当前是否存在有效的购买信息,包括在选择列表中选择的内容.
	*/
	function check_buyProduct(){
  	var buyNum=document.all("txtBuyNum");
  	if(!check_Blank(buyNum, false, "购买数量")&&check_Num(buyNum,true,"购买数量")){
			if(!add_buy(buyNum.value,false)){
				return false;
			}
  	}

  	var buyGroups=xmlBuyContent.selectSingleNode("//group");
		if(buyGroups==null||buyGroups.length<1){
			alert("没有有效的产品购买信息.");
			return false;
		}

		return true;
	}
	/**
	* 异常检查购买内容,只检查当前选择内容.需要页面提供txtOpenSourceType/txtCsiType/txtDistrictID/txtCustType
	*/
	function check_buyContent(buyInfo){
		buyInfo=buyInfo.replace(new RegExp("<","g"),'\[').replace(new RegExp(">","g"),'\]').replace(new RegExp("\"","g"),'\'');
		var url = 'check_choose_product.do';
		var operSourceType = document.getElementsByName('txtOpenSourceType')[0].value;
		var csiType = document.getElementsByName('txtCsiType')[0].value;
		var custType = document.getElementsByName('txtCustType')[0].value;
		var county = document.getElementsByName('txtDistrictID')[0].value;
		var pars = 'txtOpenSourceType=' + operSourceType + '&txtCsiType=' +csiType
		+'&txtCustType='+custType+'&txtDistrictID='+county+'&txtBuyContent='+buyInfo
		+'&buyIndex='+buyIndex;
		
		document.FrameCheckBuy.frmPost.action=url+'?'+pars;
		document.FrameCheckBuy.frmPost.submit();
	}
	
	var errorIndex=-1;
	/**
	* 异步检查出错时的处理,先弹出错误提示,再禁用购买按钮,并给错误数据高亮显示.
	*/
	function errorProcess(i,msg){
		alert('第 '+i+' 组选择产品出错,错误描述如下:'+msg);
		document.getElementById('buyButton').disabled=true;		
		errorIndex=i;
		var rid='buyList_'+errorIndex;
		document.getElementById(rid).runtimeStyle.backgroundColor ='#00FF80';
	}
	
	/**
	* 添加当前选择到购买内容.
	*/	
	function add_buy(buyNum,isCheck){
  	var xmlGroup=getChooseProduct();
  	if(xmlGroup.childNodes.length>0){
  		xmlGroup.setAttribute('buyNum',buyNum);
  		buyIndex++;
  		
			if((buyCount+parseInt(buyNum))>50){
				alert("购买总数不能大于50.");
				return false;
			}

  		xmlGroup.setAttribute('index',buyIndex);
  		if(isCheck){
  			var xmlCheckBuy=xmlBuyContent.createElement('groups');
  			xmlCheckBuy.appendChild(xmlGroup);
  			check_buyContent(xmlCheckBuy.xml);
  		}
  		xmlBuyContent.selectSingleNode("/groups").appendChild(xmlGroup);
  		refurbishBuyList();
  		saveBuyContent(xmlBuyContent.xml);
  		clearAllChoose();
  	}else if(isCheck){
  		alert('没有选择任何产品或套餐.');
  		return false;
  	}
  	return true;		
	}

	/**
	* 购买按钮点击,添加当前选择到购买内容,检查有效性.
	*/	
  function buy_click(){
  	var buyNum=document.all("txtBuyNum");
  	if(check_Blank(buyNum, true, "购买数量")||!check_Num(buyNum,false,"购买数量")){
  		return false;
  	}

  	if(!add_buy(buyNum.value,true)){
  		return false;
  	}

  	buyNum.value=1;
  	return true;
  }
  
  /*
  * 取得当前选择的购买内容,返回一个描述购买内容的xml节点.
  */
  function getChooseProduct(){
  	var xmlGroup=xmlBuyContent.createElement("group");

  	var bundleIDList=document.all("listID1");
  	var bundleNameList=document.all("listBundleName");
  	var bundleSubPackageList=document.all("sub_listID1");
  	 if (bundleIDList){
	    if (bundleIDList.length > 1) {
        for (i=0;i<bundleIDList.length;i++)
	        if (bundleIDList[i].checked){
	           var xmlBundle=xmlBuyContent.createElement("bundle");
	           xmlBundle.setAttribute('id',bundleIDList[i].value);
	           xmlBundle.setAttribute('name',bundleNameList[i].value);
	           xmlBundle.setAttribute('packageList',bundleSubPackageList[i].value);
	           xmlGroup.appendChild(xmlBundle);
	       }
      } else {
        if (bundleIDList.checked) {
	           var xmlBundle=xmlBuyContent.createElement("bundle");
	           xmlBundle.setAttribute('id',bundleIDList.value);
	           xmlBundle.setAttribute('name',bundleNameList.value);
	           xmlBundle.setAttribute('packageList',bundleSubPackageList.value);
	           xmlGroup.appendChild(xmlBundle);
       }
      }
    }

	var mPackageIDList=document.all("listID2");
	var mPackageNameList=document.all("listMPackageName");
  	 if (mPackageIDList){
  	    if (mPackageIDList.length > 1) {
	         for (i=0;i<mPackageIDList.length;i++)
		         if (mPackageIDList[i].checked){
 		           var xmlMPackage=xmlBuyContent.createElement("mpackage");
		           xmlMPackage.setAttribute('id',mPackageIDList[i].value);
		           xmlMPackage.setAttribute('name',mPackageNameList[i].value);
		           xmlGroup.appendChild(xmlMPackage);
		         }
       } else {
          if (mPackageIDList.checked) {
	           var xmlMPackage=xmlBuyContent.createElement("mpackage");
	           xmlMPackage.setAttribute('id',mPackageIDList.value);
	           xmlMPackage.setAttribute('name',mPackageNameList.value);
	           xmlGroup.appendChild(xmlMPackage);
          }
       }
     }
    
	var sPackageIDList=document.all("listID3");
	var sPackageNameList=document.all("listSPackageName");
	if(sPackageIDList){
		if (sPackageIDList.length > 1) {
			for (i=0;i<sPackageIDList.length;i++)
				if (sPackageIDList[i].checked){
					var xmlSPackage=xmlBuyContent.createElement("spackage");
					xmlSPackage.setAttribute('id',sPackageIDList[i].value);
					xmlSPackage.setAttribute('name',sPackageNameList[i].value);
					xmlGroup.appendChild(xmlSPackage);			         
				}
		} else {
			if (sPackageIDList.checked) {
				var xmlSPackage=xmlBuyContent.createElement("spackage");
				xmlSPackage.setAttribute('id',sPackageIDList.value);
				xmlSPackage.setAttribute('name',sPackageNameList.value);
				xmlGroup.appendChild(xmlSPackage);			        
			}
		}
	}

	var campaignIDList=document.all("listID4");
	var campaignNameList=document.all("listCampaignName");     
	var campaignSubPackageList=document.all("sub_listID4");     
     if (campaignIDList){
       if (campaignIDList.length > 1) {
	        for (i=0;i< campaignIDList.length;i++)
		       if (campaignIDList[i].checked){
 		           var xmlCampaign=xmlBuyContent.createElement("campaign");
		           xmlCampaign.setAttribute('id',campaignIDList[i].value);
		           xmlCampaign.setAttribute('name',campaignNameList[i].value);
		           xmlCampaign.setAttribute('packageList',campaignSubPackageList[i].value);
		           xmlGroup.appendChild(xmlCampaign);			  		       	
		       }
       } else {
           if (campaignIDList.checked) {
 		           var xmlCampaign=xmlBuyContent.createElement("campaign");
		           xmlCampaign.setAttribute('id',campaignIDList.value);
		           xmlCampaign.setAttribute('name',campaignNameList.value);
		           xmlCampaign.setAttribute('packageList',campaignSubPackageList.value);
	           xmlGroup.appendChild(xmlCampaign);			  		       	
           }
      }
      
    }
    return xmlGroup;
  }
  
  
  function refurbishBuyList(){
  	var listTable=document.getElementById('buyList');
  	
  	for(ri=listTable.rows.length-2;ri>0;ri--){
 			listTable.deleteRow(ri);
  	}
  	buyCount=0;
  	var xmlGroupList=xmlBuyContent.selectSingleNode('/groups').childNodes;
  	for(i=0;i<xmlGroupList.length;i++){
  		var xmlBuyGroup=xmlGroupList[i];
  		//alert(xmlBuyGroup.xml);
	  	var curRow=listTable.insertRow(listTable.rows.length-1);
	  	var buyGroupId=xmlBuyGroup.getAttribute("index");
	  	curRow.id="buyList_"+buyGroupId;
 			var cName=(i%2==0)?"list_bg1":"list_bg2";
			curRow.className=cName;
	    curRow.onmouseover=function(){this.className="list_over";};
	    curRow.onmouseout=function(){this.className=cName;};
	    
	    var indexCell=curRow.insertCell();
	    indexCell.align="center";
			indexCell.valign="middle";
			indexCell.innerHTML=buyGroupId;
			
			var bundleCell=curRow.insertCell();
	    bundleCell.align="left";
			bundleCell.valign="middle";
			var mPackageCell=curRow.insertCell();
	    mPackageCell.align="left";
			mPackageCell.valign="middle";			
			var sPackageCell=curRow.insertCell();
	    sPackageCell.align="left";
			sPackageCell.valign="middle";				
			var campaignCell=curRow.insertCell();
	    campaignCell.align="left";
			campaignCell.valign="middle";			

			var xmlBuyList=xmlBuyGroup.childNodes;
			if(xmlBuyList!=null){
				for(li=0;li<xmlBuyList.length;li++){
					var xmlBuyItem=xmlBuyList[li];
					var nodeName=xmlBuyItem.nodeName;
					var buyId=xmlBuyItem.getAttribute('id');
					var buyName=xmlBuyItem.getAttribute('name');
					if('bundle'==nodeName){
						bundleCell.innerHTML=bundleCell.innerHTML+buyId+'('+buyName+');<br>';
					}else if('mpackage'==nodeName){
						mPackageCell.innerHTML=mPackageCell.innerHTML+buyId+'('+buyName+');<br>';
					}else if('spackage'==nodeName){
						sPackageCell.innerHTML=sPackageCell.innerHTML+buyId+'('+buyName+');<br>';
					}else if('campaign'==nodeName){
						campaignCell.innerHTML=campaignCell.innerHTML+buyId+'('+buyName+');<br>';
					}
				}
			}

			var buyNumCell=curRow.insertCell();
	    buyNumCell.align="center";
			buyNumCell.valign="middle";				
			var buyNum=xmlBuyGroup.getAttribute("buyNum");
			buyNumCell.innerHTML=buyNum;
			buyCount=buyCount+parseInt(buyNum);
			
			var operatorCell=curRow.insertCell();
	    operatorCell.align="center";
			operatorCell.valign="middle";				
			operatorCell.innerHTML="<a href=\"JavaScript:deleteBuyRow("+buyGroupId+")\"><%="删除"%></a>";
  	}
  }
  
  function deleteBuyRow(rid){
		var xmlGroupList=xmlBuyContent.selectSingleNode('/groups').childNodes;
  	for(i=0;i<xmlGroupList.length;i++){
  		var xmlBuyGroup=xmlGroupList[i];
  		var buyGroupId=xmlBuyGroup.getAttribute("index");
  		if(buyGroupId==rid){
  			xmlBuyContent.selectSingleNode('/groups').removeChild(xmlBuyGroup);
  			if(buyGroupId==errorIndex){
	  			document.getElementById('buyButton').disabled=false;
  			}
  		}
  	}
  	saveBuyContent(xmlBuyContent.xml);
  	refurbishBuyList();
  }
  
  
  function clearAllChoose(){

  	var bundleIDList=document.all("listID1");
  	 if (bundleIDList){
  	    if (bundleIDList.length > 1) {
	        for (i=0;i<bundleIDList.length;i++)
		        bundleIDList[i].checked=false;
        } else {
          bundleIDList.checked=false;
      }
    }

	var mPackageIDList=document.all("listID2");
  	 if (mPackageIDList){
  	    if (mPackageIDList.length > 1) {
	         for (i=0;i<mPackageIDList.length;i++)
		         mPackageIDList[i].checked=false;
       } else {
		         mPackageIDList.checked=false;
       }
     }
    
	var sPackageIDList=document.all("listID3");
     if(sPackageIDList){
       if (sPackageIDList.length > 1) {
	        for (i=0;i<sPackageIDList.length;i++)
		         sPackageIDList[i].checked=false;
       } else {
		         sPackageIDList.checked=false;
       }
     }

	var campaignIDList=document.all("listID4");
     if (campaignIDList){
       if (campaignIDList.length > 1) {
	        for (i=0;i< campaignIDList.length;i++)
		       campaignIDList[i].checked=false;
       } else {
		       campaignIDList.checked=false;
      }
    }
  }

	function saveBuyContent(content){
		document.all("txtBuyContent").value=content.replace(new RegExp("<","g"),'\[').replace(new RegExp(">","g"),'\]').replace(new RegExp("\"","g"),'\'');
	}
	
	function getBuyContent(){
		var strBuyContext=document.all("txtBuyContent").value;
		strBuyContext=strBuyContext.replace(new RegExp("\\[","g"),"<").replace(new RegExp("\\]","g"),">");
		return strBuyContext;
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
    //如果初始为空,先构造根节点.
    if(xmlBuyContent.selectSingleNode("/groups")==null){
    	var root=xmlBuyContent.createElement('groups');
			xmlBuyContent.appendChild(root);
    }
    
    var buyGroups=xmlBuyContent.selectSingleNode("/groups").childNodes;
    if(buyGroups!=null){
    	for(gi=0;gi<buyGroups.length;gi++){
    		var buyId=buyGroups[gi].getAttribute("index");
    		if(buyId>buyIndex){
    			buyIndex=buyId;
    		}
    	}
    }
	}

	parseXML();
	refurbishBuyList();

	function writeX(a){
		document.write(a+'<br>');
	}


//-->
</SCRIPT>
