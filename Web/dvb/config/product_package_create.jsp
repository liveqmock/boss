<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
    if (check_Blank(document.frmPost.txtPackageName, true, "产品包名称"))
	    return false;
    if (check_Blank(document.frmPost.txtPackageClassify, true, "产品包类型"))
	    return false;	    
	    
 
    if (!check_Num(document.frmPost.txtPackagePriority, true, "排列顺序"))
	    return false;    
     if (!check_Num(document.frmPost.txtMinSelfProdNum, true, "自选产品最小值"))
	    return false;  
  if (!check_Num(document.frmPost.txtMaxSelfProdNum, true, "自选产品最大值"))
	    return false;  	    
	    
    if (check_Blank(document.frmPost.txtGrade, true, "级别"))
	    return false;
     if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;
    if (document.frmPost.txtPackageClassify.value=="B" && document.frmPost.txtCampaignId.value=='')
       {
        alert("当选择套餐产品包的时候套餐协议必选!")
        return false;
       } 
          
	    	
    return true;
 
}
 
function package_create_submit(){
  if (check_frm() && checkDate()){
	    document.frmPost.action="create_package.do";
	    document.frmPost.Action.value="CREATE";
	    document.frmPost.submit();
	  }
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
function checkDate(){
 
	if (document.frmPost.txtDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtDateFrom, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtDateTo.value != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"结束日期必须大于等于开始日期")){
		
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
 
function back(){
    document.location.href= "package_query_result.do?txtTo=10&txtFrom=1"
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
    <td class="title">产品包基本信息管理-添加</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 
   
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">产品包名称*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageName" size="25"  maxlength="25" value="<tbl:writeparam name="txtPackageName" />" >
           </td>      
           <td  class="list_bg2"><div align="right">产品包类型*</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtPackageClassify" mapName="SET_P_PACKAGECLASS" match="txtPackageClassify" width="23" onchange="ChangeCampaign()"/>
             </td>      
      </tr>
      <%
        
    
    Map campaign = Postern.getBandleCampaign();
    pageContext.setAttribute("AllBUNDLETYPE",campaign);
    
      
      %>
       <tr> 
          <td  class="list_bg2"><div align="right">套餐协议</div></td>         
          <td class="list_bg1" colspan="3" align="left">
             <tbl:select name="txtCampaignId" set="AllBUNDLETYPE" match="txtCampaignId" width="23"  />
           </td>      
           
      </tr>
      
        <tr>  
             <td class="list_bg2"><div align="right">有效开始时间</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </td>
             <td class="list_bg2"><div align="right">有效结束时间</div></td>
               <td class="list_bg1" align="left">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	  </td>
      </tr>
      
	 <tr>
		 <td class="list_bg2"><div align="right">授权级别*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtGrade" mapName="SET_S_OPERATORLEVEL" match="txtGrade" width="23" />
		 
		 <td class="list_bg2"><div align="right">状态*</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtStatus" mapName="SET_P_PACKAGESTATUS" match="txtStatus" width="23" />
		
	</tr>
       
        <tr>
		 <td  class="list_bg2"><div align="right">排列顺序</div></td>         
                 <td class="list_bg1" align="left">
                  <input type="text" name="txtPackagePriority"  maxlength="9" size="25" value="<tbl:writeparam name="txtPackagePriority" />" >
               </td>     
		 <td class="list_bg2"><div align="right">是否可选产品包</div></td>
                 <td class="list_bg1" align="left">
                 <d:selcmn name="txtHasOptionProductFlag" mapName="SET_G_YESNOFLAG" match="txtHasOptionProductFlag" width="23"  onchange="displayControl()"/>
		 
		
	</tr>
	 <tr>
		
		 <td  class="list_bg2"><div align="right">自选产品最小值</div></td>         
                 <td class="list_bg1" align="left" >
                  <input type="text" name="txtMinSelfProdNum" size="25" value="<tbl:writeparam name="txtMinSelfProdNum" />" >
               </td>     
		  <td class="list_bg2"><div align="right">自选产品最大值</div></td>
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtMaxSelfProdNum" size="25" value="<tbl:writeparam name="txtMaxSelfProdNum" />" >
		
	</tr> 
	<tr>
		
		 <td  class="list_bg2"><div align="right">是否单独捕获</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                 <d:selcmn name="txtCaptureFlag" mapName="SET_G_YESNOFLAG" match="txtCaptureFlag" width="23"/>
                  
               </td>     
		 
	</tr>
	 <tr> 
	     <td class="list_bg2" align="right">使用场合</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtCustTypeListValue" type="text" class="text" readonly maxlength="200" size="83" value="<tbl:writeparam name="txtCustTypeListValue"/>">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_V_CUSTSERVICEINTERACTIONTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
            </td>  
          </tr>
	  <tr> 
            <td  class="list_bg2" align="right">市场分区</td>
	    <td class="list_bg1" colspan="3">
	        <input name="txtMarketSegmentListValue" type="text" class="text" readonly maxlength="200" size="83" value="<tbl:writeparam name="txtMarketSegmentListValue"/>">
	   	<input name="txtMarketSegmentList" type="hidden" value="<tbl:writeparam name="txtMarketSegmentList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('MARKETSEGMENT','txtMarketSegmentList',document.frmPost.txtMarketSegmentList.value,'','','')"> 
            </td>
         </tr>
	 
	 <tr>   
		<td  class="list_bg2"><div align="right">描述</div></td>
		 <td class="list_bg1" colspan="3" align="left"> 
 
		   <input type="text" name="txtDescription" maxlength="100"  size="83" value="<tbl:writeparam name="txtDescription" />" >
 
                </td>
	</tr>
 </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 <input type="hidden" name="func_flag" value="5069" >
  <input type="hidden" name="Action" value=""/>
 
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><img src="img/button2_r.gif" width="20" height="20"></td>
	    <td><input name="Submit" type="button" class="button"
		value="返&nbsp;回" onclick="javascript:back()"></td>
		 <td><img src="img/button2_l.gif" width="11" height="20"></td>
	    <td width="20" ></td>
            
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:package_create_submit()" class="btn12">保&nbsp;存</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
          
          
         </tr>
      </table>   
    
       <br>      
   
</form>