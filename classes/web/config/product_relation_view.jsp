<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.ProductDependencyDTO" %>

<Script language=javascript>
<!--
  
function check_form(){	
	if (check_Blank(document.frmPost.txtReferProductList, true, "关联的运营商产品"))
		return false;
	if (check_Blank(document.frmPost.txtType, true, "关系类型"))
		return false;
	if (!check_Num(document.frmPost.txtReferProductNum, true, "关联最少数目"))
		return false;	
	
	return true;
}
 


function frmPost_submit(){
	 
	document.frmPost.txtActionType.value="MODIFY";
	if(check_form())
		document.frmPost.submit();
}
function frmPost_del_submit(){
	document.frmPost.action="product_relation_op.do";
	document.frmPost.txtActionType.value="DELETE";
	document.frmPost.submit();
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
<form name="frmBack" method="post">
	<input type="hidden" name="txtProductID" size="20" value="<tbl:writeparam name="txtProductID"/>">
</form>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品关系管理-维护</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<% 
   int productID=0;
   if(!(request.getParameter("txtProductID")==null || "".equals(request.getParameter("txtProductID"))))
   	productID=Integer.parseInt(request.getParameter("txtProductID"));
   	
   ProductDTO pDTO=Postern.getProductDTOByProductID(productID);

   if(pDTO==null){
%>
  
 <%
   }
   pageContext.setAttribute("pDTO",pDTO);
 %>

<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">产品信息</font></td>
	</tr>
	
        <tr>
            <td class="list_bg2" width="25%"><div align="right">产品ID</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="pDTO" property="productID" /></td>
            <td class="list_bg2" width="25%"><div align="right">产品名称</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="pDTO" property="productName" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">产品类型</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_P_PRODUCTCLASS" match="pDTO:productClass"/></td>
            <td class="list_bg2"><div align="right">新建业务帐户标志</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="pDTO:newsaFlag"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">有效期起始</div></td>
            <td class="list_bg1"><tbl:writedate name="pDTO" property="dateFrom" /></td>
            <td class="list_bg2"><div align="right">有效期截止</div></td>
            <td class="list_bg1"><tbl:writedate name="pDTO" property="dateTo" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_P_PRODUCTSTATUS" match="pDTO:status"/></td>
            <td class="list_bg2"></td>
            <td class="list_bg1"></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="product_relation_op_modify.do">
   
 
  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
    
    <input type="hidden" name="txtProductID" size="20" value="<tbl:writeparam name="txtProductID"/>">
    <input type="hidden" name="txtActionType" size="20" value="">
    <input type="hidden" name="func_flag" value="104">
    <input type="hidden" name="txtSeqNoList" size="20" value="<tbl:write name="oneline" property="seqNo" />">
    <input type="hidden" name="txtSeqNo" size="20" value="<tbl:write name="oneline" property="seqNo" />">
    
    <input type="hidden" name="txtDtCreate" value="<tbl:write name="oneline" property="dtCreate" />">
    <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />">
    
   <%
     ProductDependencyDTO prodDependDto = (ProductDependencyDTO) pageContext.findAttribute("oneline");
     
    String productIDstr=prodDependDto.getReferProductIDList();
                 String productName ="";
                 if(productIDstr!=null && !"".equals("productIDstr")){
                   String[] segmentArray=productIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllProduct();
                        
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                     if(nameValue!=null)  {
                       if(productName=="")
                        productName=nameValue;
                       else 
                       productName=productName+","+nameValue;
                        }
                     }
                  if (productName==null)
                     productName="";
                 } 
   	 
   %>
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="import_tit" align="center" colspan="4"><font size="2">产品关系信息</font></td>
        </tr>
          <tr> 
	     <td class="list_bg2" align="right">关联的运营商产品</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtReferProductListValue" type="text" class="text" maxlength="200" size="80" value="<%=productName%>">
	   	<input name="txtReferProductList" type="hidden" value="<tbl:write name="oneline" property="referProductIDList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCT','txtReferProductList',document.frmPost.txtReferProductList.value,'','','')"> 
            </td>  
          </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">关系类型*</div></td>
            
            <td class="list_bg1"><d:selcmn name="txtType" mapName="SET_P_PRODUCTDEPENDENCYTYPE" match="oneline:type" width="23" /></td>
            <td class="list_bg2"><div align="right">全关联标志*</div></td>
            <td class="list_bg1"><d:selcmn name="txtReferAllFlag" mapName="SET_G_YESNOFLAG" match="oneline:referAllFlag" width="23" /></td>
        </tr>
         <tr>
            <td class="list_bg2"><div align="right">关联最少数目</div></td>
            <td class="list_bg1" colspan="3">
 
           <input type="text" name="txtReferProductNum" size="25"  value="<tbl:write name="oneline" property="referProductNum"/>">
           </td>
             
        </tr>
         
       
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		         <td><img src="img/button2_r.gif" width="22" height="20"></td>  
                        <td background="img/button_bg.gif">
	                <a href="<bk:backurl property="menu_product_relation_query.do" />" class="btn12">返&nbsp;回</a></td>
                        <td><img src="img/button2_l.gif" width="13" height="20"></td>
		        
		  	<td width="20" ></td>			
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="保 存" onclick="javascript:frmPost_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<td width="20" ></td>	
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="删 除" onclick="javascript:frmPost_del_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>

<BR>
</lgc:bloop>

</form>
