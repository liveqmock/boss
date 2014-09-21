<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
<%@ page import="com.dtv.oss.dto.*" %> 

 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
    if (check_Blank(document.frmPost.txtPackageName, true, "产品包名称"))
	    return false;
	
    if (check_Blank(document.frmPost.txtGrade, true, "级别"))
	    return false;
    if (check_Blank(document.frmPost.txtPackageClassify, true, "产品包类型"))
	    return false;	    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;	    
 if (!check_Num(document.frmPost.txtMinSelfProdNum, true, "自选产品最小值"))
	    return false;  
  if (!check_Num(document.frmPost.txtMaxSelfProdNum, true, "自选产品最大值"))
	    return false;  	    	        
 
    if (!check_Num(document.frmPost.txtPackagePriority, true, "排列顺序"))
	    return false;
 if (document.frmPost.txtPackageClassify.value=="B" && document.frmPost.txtCampaignId.value=='')
       {
        alert("当选择套餐产品包的时候套餐协议必选!")
        return false;
       } 
     return true;
}
 
 function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
function package_modify_submit(){
 if (window.confirm('您确认要修改该产品包吗?')){
  if (check_frm() && check_date()){
	    document.frmPost.action="modity_package.do";
	    document.frmPost.Action.value="MODIFY";
	    document.frmPost.submit();
	  }
}
}
function   product_for_package__submit() {
  
	    document.frmPost.action="product_for_package.do";
	     
	    document.frmPost.submit();
	  
}
 
function check_date(){
       	if (document.frmPost.txtDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtDateFrom, true, "起始时间")){
			return false;
		}
	}
	if (document.frmPost.txtDateTo != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "结束时间")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	
	return true;
}
    
function back(){
    document.location.href= "package_query_result.do?txtFrom=1&txtTo=10"
} 
 function displayControl(){
 
     
     
       if (document.frmPost.txtHasOptionProductFlag.value=="Y"){
       
             
	      document.frmPost.txtMaxSelfProdNum.disabled=false;
	      document.frmPost.txtMinSelfProdNum.disabled=false;
	      
	    
	    }  else {
              
             document.frmPost.txtMinSelfProdNum.disabled=true;
	     document.frmPost.txtMaxSelfProdNum.disabled=true;
	     
	      
    }
 
}
function ChangeCampaign()
{
       if (document.frmPost.txtPackageClassify.value=="B") 
        
         document.frmPost.txtCampaignId.disabled=false;
         
         else {
         document.frmPost.txtCampaignId.disabled=true;
         document.frmPost.txtCampaignId.value='';
      }
      
      
      }

 
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品包基本信息管理-维护</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td> 
<form name="frmPost" method="post" >   
  
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >  
 <%
    ProductPackageDTO dto = (ProductPackageDTO) pageContext.findAttribute("oneline");
    String csiTypeList = dto.getCsiTypeList();
    String custTypeList =dto.getCustTypeList();
    String csiTypeTotalValue="";
    String custTypeTotalValue ="";
     
    if(csiTypeList!=null){ 
       String[] csiTypeArray=csiTypeList.split(",");
       for(int j=0;j<csiTypeArray.length;j++){
           String value = Postern.getHashValueByNameKey("SET_V_CUSTSERVICEINTERACTIONTYPE",csiTypeArray[j]);
           if(csiTypeTotalValue=="")
              csiTypeTotalValue=value;
           else 
              csiTypeTotalValue=csiTypeTotalValue+","+value;
       }            
    }
    
    if (custTypeList !=null){
       String[] custTypeArray =custTypeList.split(",");
       for (int j=0;j<custTypeArray.length;j++){
           String value = Postern.getHashValueByNameKey("SET_C_CUSTOMERTYPE",custTypeArray[j]);
           if(custTypeTotalValue=="")
              custTypeTotalValue=value;
           else 
              custTypeTotalValue=custTypeTotalValue+","+value;
       }
    }
    
    
    
                 int packageId=dto.getPackageID();
                  String segmentIDstr=Postern.getMarketSegmentIDByPackageID(packageId);
                 String sagmentName ="";
                 if(segmentIDstr!=null && !"".equals("segmentIDstr")){
                 String[] segmentArray=segmentIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllMarketSegmentName();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(sagmentName=="")
                      sagmentName=nameValue;
                     else 
                       sagmentName=sagmentName+","+nameValue;
                     }
                  if (sagmentName==null)
                     sagmentName="";
                 }
                  
    Map campaign = Postern.getBandleCampaign();
    pageContext.setAttribute("AllBUNDLETYPE",campaign);
    int campaignId=Postern.getCampaignIDByPackageID(packageId);
    String campaignIdStr = String.valueOf(campaignId);
    String packagePriority = String.valueOf(dto.getPackagePriority());
    	if("0".equals(packagePriority)) packagePriority = "";
                 %>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr> 
          <td  class="list_bg2"><div align="right">产品包ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageID" size="25"  value="<tbl:writenumber digit="7" name="oneline" property="packageID" />" class="textgray" readonly >
           </td>      
          <td  class="list_bg2"><div align="right">产品包名称*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageName" size="25"  maxlength="25"  value="<tbl:write name="oneline" property="packageName" />" >
           </td>        
      </tr>
       <tr>  
             <td class="list_bg2"><div align="right">有效开始时间</div></td>
             <td class="list_bg1" align="left">
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="DateFrom" />"    ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            
           </td>
             <td class="list_bg2"><div align="right">有效结束时间</div></td>
               <td class="list_bg1" align="left">
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="DateTo" />"    ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	  </td>
      </tr>
      <tr> 
          
           <td  class="list_bg2"><div align="right">产品包类型*</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtPackageClassify" mapName="SET_P_PACKAGECLASS" match="oneline:PackageClass" width="23" onchange="ChangeCampaign()"/>
             </td>   
              <td class="list_bg2"><div align="right">是否可选产品包</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtHasOptionProductFlag" mapName="SET_G_YESNOFLAG" match="oneline:HasOptionProductFlag" width="23"  onchange="displayControl()"/>  
                 </td>     
        </tr>
         <tr> 
          <td  class="list_bg2"><div align="right">套餐协议</div></td>         
          <td class="list_bg1" colspan="3" align="left">
             <tbl:select name="txtCampaignId" set="AllBUNDLETYPE" match="<%=campaignIdStr%>" width="23"  />
           </td>      
           
      </tr>
      
         <tr>   
		 <td  class="list_bg2"><div align="right">自选产品最小值</div></td>         
                 <td class="list_bg1" align="left">
                  <input type="text" name="txtMinSelfProdNum" maxlength="9" size="25" value="<tbl:writenumber  hidezero="true"  name="oneline" property="MinSelProductNum" />" >
                </td> 
                
		 <td  class="list_bg2"><div align="right">自选产品最大值</div></td>  
		 <td class="list_bg1">
		  <input type="text" name="txtMaxSelfProdNum" maxlength="9" size="25" value="<tbl:writenumber hidezero="true" name="oneline" property="MaxSelProductNum" />" >
		  </td> 
           </tr>
       
      
	 <tr>
		 <td class="list_bg2"><div align="right">授权级别*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtGrade" mapName="SET_S_OPERATORLEVEL" match="oneline:grade" width="23" />
		 		 <td class="list_bg2"><div align="right">状态*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_P_PACKAGESTATUS" match="oneline:status" width="23" />
		 
	</tr>
       
	    <tr>   
		 <td  class="list_bg2"><div align="right">排列顺序</div></td>         
                 <td class="list_bg1" align="left">
                  <input type="text" name="txtPackagePriority" maxlength="9" size="25" value="<%=packagePriority%>" >
                </td> 
                
		 <td  class="list_bg2"><div align="right">是否单独捕获</div></td>  
		 <td class="list_bg1"><d:selcmn name="txtCaptureFlag" mapName="SET_G_YESNOFLAG" match="oneline:captureFlag" width="23" /></td>       
           </tr>
           <tr>   
		 <td  class="list_bg2" align="right">使用场合</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCsiTypeListValue" type="text"  readonly maxlength="200" size="83" value="<%=csiTypeTotalValue%>">
	   	<input name="txtCsiTypeList" type="hidden" value="<tbl:write name="oneline"  property="CsiTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_V_CUSTSERVICEINTERACTIONTYPE','txtCsiTypeList',document.frmPost.txtCsiTypeList.value,'','','')"> 
	   </td>	   
           </tr>
           <tr>   
		 <td  class="list_bg2" align="right">使用客户类型</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCustTypeListValue" type="text"  readonly maxlength="200" size="83" value="<%=custTypeTotalValue%>">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline"  property="CustTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
           </tr>
             <tr> 
            <td  class="list_bg2" align="right">允许市场分区</td>
	    <td class="list_bg1" colspan="3">
	        <input name="txtMarketSegmentListValue" type="text"  readonly maxlength="200" size="83" value="<%=sagmentName%>">
	   	<input name="txtMarketSegmentList" type="hidden" value="<%=segmentIDstr%>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('MARKETSEGMENT','txtMarketSegmentList',document.frmPost.txtMarketSegmentList.value,'','','')"> 
            </td>
         </tr>
       
	 <tr>   
		<td class="list_bg2"><div align="right">描述</div></td>
		<td class="list_bg1" colspan="3" align="left"><font size="2">
		 <input type="text" name="txtDescription" size="83"  maxlength="100"  value="<tbl:write name="oneline" property="Description" />" >
			 
		</font></td>
	</tr>
 </table>
 
    
 <input type="hidden" name="func_flag" value="1000" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
   
  
<br>
  
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
             <td><img src="img/button2_r.gif" width="20" height="20"></td>
	    <td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:package_modify_submit()" class="btn12">保&nbsp;存</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
            <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:product_for_package__submit()" class="btn12">明细信息管理</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
            
            
          
         </tr>
      </table>   
     
      
       
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
   </lgc:bloop>
</form>
</td>
</tr>
</table>