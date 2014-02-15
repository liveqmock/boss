<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<script language=javascript>
<!--
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
			if (document.frmPost.ProductList.value!="")
		        document.frmPost.ProductList.value = document.frmPost.ProductList.value + ";";
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
function next_step()
{
	if(check_frm()){
    document.frmPost.action ="match_and_preauth_check.do";
    if(document.frmPost.txtPreAuth.value=="Y")
    fillList();
    document.frmPost.submit();    
  }
}

function check_frm()
{
	if (check_Blank(document.frmPost.txtBatchID, true, "操作批号"))
		return false;
	if (check_Blank(document.frmPost.txtSerialNoCollection, true, "主设备/配对设备序列号列表"))
		return false;

	return true;
}
//-->
</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">预授权选择</font></td>
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
      <td class="list_bg2" align="right" width="23%">配对的同时是否进行预授权</td>
      <td class="list_bg1" >
        <d:selcmn name="txtPreAuth" mapName="SET_G_YESNOFLAG" match="txtPreAuth" width="23"  defaultIndex="1" onchange="displayProductList()" />
      </td>
    </tr>
    <%
      String txtPreAuth = (request.getParameter("txtPreAuth")==null) ? "" :request.getParameter("txtPreAuth");
      if (txtPreAuth.equals("Y")||txtPreAuth.equals("")){
    %>
    <tr>
      <td class="list_bg2" align="right" >预授权产品列表</td>
		<td class="list_bg1"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" ></td>
    
      <td class="list_bg1" valign=top><font size="2">
        <iframe SRC="list_product.do?txtStatus=N" name="FrameProduct" width="550" height="160"></iframe>
      </font></td> 
   </tr>
   <% } %>
   <tr> 
    <td class="list_bg2" align="right" >操作批号*</td>
    <td class="list_bg1" width="90%">
        <input type="text" name="txtBatchID" size="20"  value="<tbl:writeparam name="txtBatchID" />" >
        </td>
    </tr>    
   <tr>
     <td class="list_bg2" align="right" >主设备/配对设备序列号列表*</td>
		<td class="list_bg1">(主设备是指需要预授权的设备，如："智能卡",后跟配对设备,如:"智能卡/机顶盒",以"/"分隔)</td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" ></td>
    
	 <td class="list_bg1" > 
	 <textarea name="txtSerialNoCollection"  length="5" cols=75 rows=9><tbl:writeSpeCharParam name="txtSerialNoCollection" /></textarea>
         </td>		
      </tr>
    </table>

		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">设备配对/预授权</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
   </table>
				</td>
			</tr>
		</table>        
   <input type="hidden" name="ProductList" value="">
</form>  

     
 

 

  
 
 

     
      
      
      
      
      