<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<script language=javascript>
<!--
function check_frm()
{
		if (check_Blank(document.frmPost.txtBatchID, true, "操作批号"))
		return false;
		
		return true;
	} 
	
function back_submit()
{

	document.frmPost.action='<bk:backurl property="dev_detail.do" />';
	document.frmPost.submit();
	
}
function displayProductList()
{
    document.frmPost.action ="";
    document.frmPost.submit();    
}
function fillList()
{
        document.frmPost.ProductList.value="";
	 
	if (document.frames.FrameProduct.listID!=null)
	{
        if (document.frames.FrameProduct.listID.length > 1) {
	    for (i=0;i<document.frames.FrameProduct.listID.length;i++)
		if (document.frames.FrameProduct.listID[i].checked)
		{
			if (document.frmPost.ProductList.value!="") document.frmPost.ProductList.value = document.frmPost.ProductList.value + ";";
			document.frmPost.ProductList.value=document.frmPost.ProductList.value + document.frames.FrameProduct.listID[i].value;
		}
        } else {
            if (document.frames.FrameProduct.listID.checked) {
                document.frmPost.ProductList.value=document.frames.FrameProduct.listID.value + ";";
            } else {
                document.frmPost.ProductList.value = '';
            }
        }
    }
}
function preauth_device()
{
	 if(check_frm()&&confirm("确定要对该设备进行预授权操作吗?")){
    document.frmPost.action ="device_preauth.do";
    fillList();
    document.frmPost.submit();    
  }
}
//-->
</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">设备预授权</font></td>
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
      <td class="list_bg2" align="right"  width="20%">预授权产品列表</td>
      	<td class="list_bg1">
        <iframe SRC="list_product.do?txtStatus=N" name="FrameProduct" width="500" height="300"></iframe>
      </td> 
   </tr>
       
   <%
   	String ossProductName=Postern.getOSSIAuthorizationProductNameByDeviceID(WebUtil.StringToInt(request.getParameter("txtDeviceID")));
   	if(ossProductName!=null && !"".equals(ossProductName)){
   %>
   <tr> 
    <td class="list_bg2" align="right">已预授权的产品</td>
    <td class="list_bg1" width="90%"><%=ossProductName %></td>
    </tr>    
   <%}%>
   
   <tr> 
    <td class="list_bg2" align="right">操作批号*</td>
    <td class="list_bg1" width="90%">
        <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtPreauthBatchID" />" >
        </td>
    </tr>
    
    </table>
    
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
			     <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
			       <td><img src="img/button2_l.gif" border="0" ></td>
			        <td width="20"></td>	         	

            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:preauth_device()" class="btn12">预授权</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
   </table>
				</td>
			</tr>
		</table>      
   <input type="hidden" name="ProductList" value="">
   <input type="hidden" name="txtSerialNo" value="<tbl:writeparam name="txtSerialNo"/>" >
   <input type="hidden" name="txtDeviceID" value="<tbl:writeparam name="txtDeviceID"/>" >
   <input type="hidden" name="func_flag" value="1007">
</form>  

     
 

 

  
 
 

     
      
      
      
      
      