<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.BrconditionDTO,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_frm ()
{
    if (check_Blank(document.frmPost.txtConditionType, true, "计费条件类型"))
	    return false;
    if (check_Blank(document.frmPost.txtConditionName, true, "计费条件名称"))
	    return false;
   if (check_Blank(document.frmPost.txtStatus, true, "计费条件状态"))
	    return false;
 if (check_Blank(document.frmPost.txtCustTypeList, true, "计费条件内容"))
	   return false;
	return true;
}

function brcondition_edit_submit(){
 if (window.confirm('您确认要修改该计费条件吗?')){
  if (check_frm()){
	    document.frmPost.action="edit_brcondition.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
function billing_condition_delete(){
 if (window.confirm('您确认要删除该计费条件吗?')){
  if (check_frm()){
	    document.frmPost.action="delete_brcondition.do";
	    document.frmPost.Action.value="deleteDetail";
	    document.frmPost.func_flag.value="110";
	    document.frmPost.submit();
	  }
}
}
  

function open_select(typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	if(document.frmPost.txtConditionType.value=='TT')
	 
	 var type="SET_A_CATVTERMTYPE";
	 
	 if(document.frmPost.txtConditionType.value=='S')
	 
	 var type="SET_C_USER_TYPE";
	 
	 if(document.frmPost.txtConditionType.value=='G')
	 
	 var type="MARKETSEGMENT";
	 
	 if(document.frmPost.txtConditionType.value=='CT')
	 
	 var type="SET_A_CABLETYPE";
	 
	 if(document.frmPost.txtConditionType.value=='BT')
	 
	 var type="SET_G_YESNOFLAG"; 
	if(document.frmPost.txtConditionType.value=='C')
	 
	 var type="SET_C_CUSTOMERTYPE";
	if(document.frmPost.txtConditionType.value=='A')
	 
	 var type="SET_F_ACCOUNTTYPE"; 
	if(document.frmPost.txtConditionType.value=='M') 
	 var type="CAMPAIGN";
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
function ChangeType()
{
      document.frmPost.action="brcondition_detail.do"
      
      document.frmPost.submit();
     
}
 function back_submit()
{
	self.location.href="brcondition_query.do?txtFrom=1&txtTo=10";
} 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="111" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
       <td colspan="2" height="8"></td>
     </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">计费条件管理-维护</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
	<%
	
         BrconditionDTO dto = (BrconditionDTO) pageContext.findAttribute("oneline");
         
         String cntValueStr = dto.getCntValueStr();
       
         String cntType = dto.getCntType();
         String value = "";
         String totalValue="";
        
                 if(cntValueStr!=null)
                 { 
                     String[] cntValueArray=cntValueStr.split(",");
                     for(int j=0;j<cntValueArray.length;j++){
                    
                     if("TT".equals(cntType))
                          value = Postern.getHashValueByNameKey("SET_A_CATVTERMTYPE",cntValueArray[j].trim());
                     if("CT".equals(cntType))
                          value = Postern.getHashValueByNameKey("SET_A_CABLETYPE",cntValueArray[j].trim());
                     if("BT".equals(cntType))
                          value = Postern.getHashValueByNameKey("SET_G_YESNOFLAG",cntValueArray[j].trim());
                     if("C".equals(cntType))
                          value = Postern.getHashValueByNameKey("SET_C_CUSTOMERTYPE",cntValueArray[j].trim());
                     if ("M".equals(cntType)) 
                           value = Postern.getCampaignNameByID(WebUtil.StringToInt(cntValueArray[j].trim()));  
                     if ("A".equals(cntType)) 
                           value = Postern.getHashValueByNameKey("SET_F_ACCOUNTTYPE",cntValueArray[j].trim());
                     if("S".equals(cntType))
                          value = Postern.getHashValueByNameKey("SET_C_USER_TYPE",cntValueArray[j].trim());
                     if("G".equals(cntType))
                          value = Postern.getMarketSegmentName(WebUtil.StringToInt(cntValueArray[j].trim()));            
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
                 else 
                 cntValueStr="";
 %>    
 
	<tr>
	          <td  class="list_bg2"><div align="right">计费条件ID</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtBrcntID" size="25"  value="<tbl:write name="oneline" property="brcntID" />" class="textgray" readonly >
                  </td>       
		 <td class="list_bg2"><div align="right">计费条件状态*</div></td>
                 <td class="list_bg1" align="left">
                  <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
                </td>
		 
	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">计费条件类型*</div></td>         
                <td class="list_bg1" align="left">
                 <d:selcmn name="txtConditionType" mapName="SET_F_BRCONDITIONCNTTYPE" match="oneline:cntType" width="23" disabled="true" />
                 
                </td> 
                 <td  class="list_bg2"><div align="right">计费条件名称*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtConditionName" maxlength="200" size="22"   value="<tbl:write name="oneline" property="cntName"/>">
                 </td>        
       </tr>
	 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
	
	<tr>
	           <td valign="middle" class="list_bg2" align="left" >计费条件内容*</td>
	           <td class="list_bg1" colspan="4"> 
	           <textarea name="txtCustTypeListValue"  readonly length="5" cols=80 rows=9><%=totalValue%></textarea>
	           <input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline" property="cntValueStr" />" >
	           <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
                   </td>		
	   	   
	    </tr>   
	  </lgc:bloop>  
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="brcondition_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>    
            

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="修&nbsp; 改" onclick="javascript:brcondition_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td> 
             <td width="20" ></td>    
             <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="删&nbsp;除" onclick="javascript:billing_condition_delete()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>        
        </tr>
         
      </table>	

</form>
