<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*,com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>
 

<Script language=javascript>
<!--
 function check_form(){
    if (check_Blank(document.frmPost.txtCampaignIDList, true, "市场活动"))
        return false;
        
  
           
   
    if (document.frmPost.txtCampaignNum.value != '') {
          if (!check_Num(document.frmPost.txtCampaignNum, true, "活动数量"))
            return false;
       
       }
   
    return true;

}
  
 

function modify_submit(){
	  if(check_form()){
           
            document.frmPost.submit();
            }
	 
}
 
function back_submit(){
	url="campaign_cond_campaign.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
	document.location.href=url;
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

 

//-->
</SCRIPT>
 

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">市场活动条件管理-修改</td>
  </tr>
</table>
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="modify_cond_campaign.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="modify_cond">
    
    <input type="hidden" name="func_flag" value="1017">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    <%
     
   	  int seqNo=WebUtil.StringToInt(request.getParameter("txtSeqNo"));
   	 
           CampaignCondCampaignDTO cDTO=Postern.getCampaignCondCampaignDTO(seqNo);
           pageContext.setAttribute("oneline",cDTO);
           String campaignIDstr=cDTO.getCampaignIDList();
                 String campaignName ="";
                 if(campaignIDstr!=null && !"".equals("campaignIDstr")){
                   String[] segmentArray=campaignIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllCampaignIDAndName();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(campaignName=="")
                      campaignName=nameValue;
                     else 
                       campaignName=campaignName+","+nameValue;
                     }
                  if (campaignName==null)
                     campaignName="";
                 }
     
    %>
        <tr>
           <td class="list_bg2" width="25%"><div align="right">流水号</div></td>
            <td class="list_bg1" width="25%" colspan="3">
             <input type="text" name="txtSeqNO" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="seqNo"/>">
            
          </td>
          </td>
	<tr> 
	     <td class="list_bg2" align="right">市场活动*</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtCampaignIDListValue" type="text" class="text" readonly maxlength="200" size="80" value="<%=campaignName%>">
	   	<input name="txtCampaignIDList" type="hidden" value="<tbl:write name="oneline" property="campaignIDList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('CAMPAIGN','txtCampaignIDList',document.frmPost.txtCampaignIDList.value,'','','')"> 
	   	<!--
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('CAMPAIGN','txtCampaignIDList,document.frmPost.txtCampaignIDList.value,'','','')"> 
	       -->  
            </td>  
         </tr>
        <tr>
            <td class="list_bg2" width="25%"><div align="right">全选标志</div></td>
             <td class="list_bg1"><d:selcmn name="txtHasAllCampaignFlag" mapName="SET_G_YESNOFLAG" match="oneline:hasAllFlag" width="25"/></td>
            
            <td class="list_bg2" width="25%"><div align="right">活动数量</div></td>
            <td class="list_bg1" width="25%">
             <input type="text" name="txtCampaignNum" size="25" value="<tbl:write name="oneline" property="campaignNum"/>">
            
          </td>
              <input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod"/>">
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">新购买标志</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCaptureFlag" mapName="SET_G_YESNOFLAG" match="oneline:newCaptureFlag" width="25" /></td>
            <td class="list_bg2"><div align="right">存在标志</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingCampaignFlag" mapName="SET_G_YESNOFLAG" match="oneline:existingFlag" width="25" /></td>
        </tr>
    
         
        
    
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		        
		        
		         <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:modify_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			 
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>

 
  
</form>
