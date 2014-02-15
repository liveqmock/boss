<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
<%@ page import="com.dtv.oss.web.util.WebUtil" %> 
<%@ page import="com.dtv.oss.dto.UserPointsExchangeCondDTO" %>

<script language=javascript>
 
var ActivityID =0;

function check_frm()
{

    if (check_Blank(document.frmPost.txtGoodTypeID, true, "兑换物品名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
		
		if (!check_Float(document.frmPost.txtPointRange2, true, "积分上限"))
	    return false;	 
 if (!check_Float(document.frmPost.txtPointRange1, true, "积分下限"))
	    return false;  
	 if(""!=document.frmPost.txtPointRange2.value && ""!=document.frmPost.txtPointRange1.value && document.frmPost.txtPointRange2.value<document.frmPost.txtPointRange1.value){
 	
            alert("积分下限不能超过积分上限");
         	 return false;
   } 

	return true;
}
function back_submit()
{
        ActivityID = document.frmPost.txtActivityID.value;
         
	self.location.href='<bk:backurl property="query_points_cond.do" />';
} 
function cond_modify_submit(){
    if (check_frm()){
  if (window.confirm('您确认要修改该条件信息吗?')){
	    document.frmPost.action="cond_edit_done.do";
	    document.frmPost.submit();
	  }
	}
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

 
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">积分兑换条件修改</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
<%
 com.dtv.oss.dto.UserPointsExchangeCondDTO dto = (com.dtv.oss.dto.UserPointsExchangeCondDTO)pageContext.getAttribute("oneline");
  int activityID = dto.getActivityId();
  Map goodsMap = null;
  String pointStr1=null;
   String pointStr2=null;
  int pointsBelow = dto.getPointRange1();
  if (pointsBelow == 0)
      pointStr1 ="";
   else pointStr1 = String.valueOf(pointsBelow);
  int pointsOver= dto.getPointRange2();
  if (pointsOver == 0)
      pointStr2 ="";
   else pointStr2 = String.valueOf(pointsOver); 
     
  goodsMap = Postern.getGoodNameByActivityID(activityID);
  pageContext.setAttribute("GOODNAME",goodsMap);
    String custTypeList = dto.getCustTypeList();
         String totalValue="";
         System.out.println("the type of the customer is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOMERTYPE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
      String acctClassList = dto.getAccountClassList();
      String totalValue1 = "";  
       if(acctClassList!=null)
                 { 
                     String[] custArray=acctClassList.split(",");
                     for(int j=0;j<custArray.length;j++){
                    
                     String value = Postern.getHashValueListByNameKey("SET_F_ACCOUNTCLASS",custArray[j]);
                     if(totalValue1=="")
                      totalValue1=value;
                     else 
                       totalValue1=totalValue1+","+value;
                     }
                     
                 }    
      String mopIDList = dto.getMopIdList();
      String totalValue2 = "";  
       if(mopIDList!=null)
                 { 
                     String[] custArray=mopIDList.split(",");
                     for(int j=0;j<custArray.length;j++){
                    
                     String value = Postern.getNameByMopID(WebUtil.StringToInt(custArray[j]));
                     if(totalValue2=="")
                      totalValue2=value;
                     else 
                       totalValue2=totalValue2+","+value;
                     }
                     
                 }                  
                     
%>
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2" align="right">兑换条件ID</td>
		 <td class="list_bg1">
		 <input type="text" name="txtCondID" size="25"  value="<tbl:write name="oneline" property="condId" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">兑换物品名称*</td>
		<td class="list_bg1">
		   <tbl:select name="txtGoodTypeID" set="GOODNAME" match="oneline:exchangeGoodsTypeID" width="23" />   
		 </td>
	</tr>
	  
	<tr>
		 <td valign="middle" class="list_bg2" align="right" >帐户类别</td>
	          <td  class="list_bg1">
	   	  <input name="txtAccountClassListValue" type="text" class="text" maxlength="200" size="25" value="<%=totalValue1%>" />
	   	  <input name="txtAccountClassList" type="hidden" value="<tbl:write name="oneline" property="accountClassList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_F_ACCOUNTCLASS','txtAccountClassList',document.frmPost.txtAccountClassList.value,'','','')"> 
	          </td>	   
		
		 <td valign="middle" class="list_bg2" align="right" >支付类型</td>
	          <td  class="list_bg1">
	   	  <input name="txtMopIDListValue" type="text" class="text" maxlength="200" size="25" value="<%=totalValue2%>" />
	   	  <input name="txtMopIDList" type="hidden" value="<tbl:write name="oneline" property="mopIdList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('ALLMOPLIST','txtMopIDList',document.frmPost.txtMopIDList.value,'','','')"> 
	   </td>	   
	</tr>
	  
       <tr>
	        <td class="list_bg2" align="right">积分下限</td>
		 <td class="list_bg1">
		 <input type="text" name="txtPointRange1" size="25" maxlength="16" value="<%=pointStr1%>">
		 </td>
		<td class="list_bg2" align="right">积分上限</td>
		<td class="list_bg1">
		 <input type="text" name="txtPointRange2" size="25" maxlength="16" value="<%=pointStr2%>">
		 </td>
	</tr>
       	
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
		 </td>
		 <td valign="middle" class="list_bg2" align="right" >客户类型</td>
	          <td  class="list_bg1">
	   	  <input name="txtCustTypeListValue" type="text" class="text" maxlength="200" size="25" value="<%=totalValue%>" />
	   	  <input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline" property="custTypeList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
	</tr>
        
        
      
  </table>
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
<input type="hidden" name="txtActivityID"   value="<tbl:write name="oneline" property="ActivityId" />">
 
<input type="hidden" name="txtActionType" value="cond_update">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
 <table align="center" border="0" cellspacing="0" cellpadding="0">
 <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="确&nbsp;认" onclick="javascript:cond_modify_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
 	   </td>
	</tr>
</table>  
   </table>   
     
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
</lgc:bloop>   
</form>
