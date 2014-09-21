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
    if (check_Blank(document.frmPost.txtProductList, true, "�Ѿ�������Ʒ"))
        return false;
        
  
           
   
    if (document.frmPost.txtProductNum.value != '') {
          if (!check_Num(document.frmPost.txtProductNum, true, "��Ʒ����"))
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
	url="product_cond_campaign.screen?txtCampaignID=<tbl:writeparam name="txtCampaignID"/>";
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
    <td class="title">��Ʒ��������-�޸�</td>
  </tr>
</table>
 
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post" action="modify_cond_product.do">
    
    <input type="hidden" name="txtCampaignID" size="20" value="<tbl:writeparam name="txtCampaignID"/>">
    <input type="hidden" name="txtActionType" size="20" value="modify_cond">
   
    <input type="hidden" name="func_flag" value="1015">
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    <%
     
   	  int seqNo=WebUtil.StringToInt(request.getParameter("txtSeqNo"));
   	 
           CampaignCondProductDTO cDTO=Postern.getCampaignCondProductDTO(seqNo);
           pageContext.setAttribute("oneline",cDTO);
           String productIDstr=cDTO.getProductIdList();
                 String productName ="";
                 if(productIDstr!=null && !"".equals("productIDstr")){
                   String[] segmentArray=productIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllProduct();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(productName=="")
                      productName=nameValue;
                     else 
                       productName=productName+","+nameValue;
                     }
                  if (productName==null)
                     productName="";
                 }
    
    %>
        <tr>
           <td class="list_bg2" ><div align="right">��ˮ��</div></td>
            <td class="list_bg1" colspan="3">
             <input type="text" name="txtSeqNO" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="seqNo"/>">
            
          </td>
          </td>
	<tr> 
	     <td class="list_bg2" align="right">�Ѿ�������Ʒ*</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtProductListValue" type="text"  readonly maxlength="100" size="80" value="<%=productName%>">
	   	<input name="txtProductList" type="hidden" value="<tbl:write name="oneline" property="productIdList"/>">
	   	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('PRODUCT','txtProductList',document.frmPost.txtProductList.value,'','','')"> 
	         
            </td>  
         </tr>
        <tr>
            <td class="list_bg2"><div align="right">ȫѡ��־</div></td>
             <td class="list_bg1"><d:selcmn name="txtHasAllProductFlag" mapName="SET_G_YESNOFLAG" match="oneline:hasAllFlag" width="23"/></td>
            
            <td class="list_bg2"><div align="right">��Ʒ����</div></td>
            <td class="list_bg1">
             <input type="text" name="txtProductNum" size="25" value="<tbl:write name="oneline" property="productNum"/>">
            
          </td>
              <input type="hidden" name="txtDtLastmod"  value="<tbl:write name="oneline" property="dtLastmod"/>">
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">�²����־</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewCaptureProductFlag" mapName="SET_G_YESNOFLAG" match="oneline:newCaptureFlag" width="23" /></td>
            <td class="list_bg2"><div align="right">���ڱ�־</div></td>
            <td class="list_bg1"><d:selcmn name="txtExistingProductFlag" mapName="SET_G_YESNOFLAG" match="oneline:existingFlag" width="23" /></td>
        </tr>
    
         
        
    
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		        
		        
		         <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��  ��" onclick="javascript:modify_submit()"></td>
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
