<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*" %>
 
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.ProductDependencyDTO" %>

<%
String checkFlags = request.getParameter("checkFlags");
if (checkFlags == null)
    checkFlags = "";%>
    

 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品业务信息管理</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<% 
   int productID=Integer.parseInt(request.getParameter("txtProductID"));
   	
   ProductDTO pDTO=Postern.getProductDTOByProductID(productID);
   pageContext.setAttribute("pDTO",pDTO);
 %>
<form name="frmPost" method="post" action="">
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
            <td class="list_bg2"><div align="right">是否能创建业务帐户</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="pDTO:newsaFlag"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">有效开始时间</div></td>
            <td class="list_bg1"><tbl:writedate name="pDTO" property="dateFrom" /></td>
            <td class="list_bg2"><div align="right">有效结束时间</div></td>
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
 


    <input type="hidden" name="partchoose" value="">
    <input type="hidden" name="txtProdClass" value="<tbl:writeparam name="txtProductClass"/>">
    <tbl:hiddenparameters pass="txtProductClass/txtProductID"/> 
    <input type="hidden" name="productID" size="20" value="<tbl:writeparam name="txtProductID"/>">
     <input type="hidden" name="txtProductID" size="20" value="<tbl:writeparam name="txtProductID"/>">
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="import_tit" align="center" colspan="6"><font size="2">业务选择</font></td>
        </tr>
        <tr>
          <td class="list_bg2" colspan="2"><div align="center">已有业务</div></td>
            
          <td class="list_bg2" colspan="2"><div align="center">可选业务</div></td>
     </tr>
     </table>
       <input type="hidden" name="Action" value=""/>
       <input type="hidden" name="serviceID" value=""/>
       <input type="hidden" name="existedServiceID" value=""/>
       <input type="hidden" name="checkFlags" value=""/>
   
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
      <tr>
           <td class="list_bg2" align="center" colspan="2"><font size="2">
            <iframe SRC="existed_service.screen?txtProductID=<tbl:writeparam name="txtProductID"/>&checkFlags=<%=checkFlags%>"
            id="ExistedService" name="ExistedService" width="320" height="300">
  	      </iframe>
          </font></td>
           <td class="list_bg2">
            <table align="center" border="0" cellspacing="0" cellpadding="0">
    	     <tr>
    	         <td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
		value="添&nbsp;加" onclick="javascript:add_service()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr>
           <tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="去&nbsp;除" onclick="javascript:delete_service()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
           
         </tr>
      </table>   
      </td>
          <td valign="middle" class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_service.screen?txtProductID=<tbl:writeparam name="txtProductID" />&checkFlags=<%=checkFlags%>"
            id="OptionalService" name="OptionalService" width="320" height="300">
  	      </iframe><br><br>
          </font></td>
          </tr>
      </table> 
 
   
      
   
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		         <td><img src="img/button2_r.gif" width="20" height="20"></td>
	                 <td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back()"></td>
                         <td><img src="img/button2_l.gif" width="11" height="20"></td>
		        
		  	 
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
     
  
 
   
  
</form>
<Script language=javascript>
<!--
 
function back(){
    document.location.href= "product_manage_view.do?txtProductID=<tbl:writeparam name="txtProductID"/>"
} 
function checkForm() {
     document.frmPost.serviceID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.ExistedService.txtServiceID != null) {
        if (document.frames.ExistedService.txtServiceID.length > 1) {
            for (i = 0; i < document.frames.ExistedService.txtServiceID.length; i++) {
                if (document.frames.ExistedService.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.ExistedService.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.serviceID.value=document.frames.ExistedService.txtServiceID[i].value+ ";" + document.frmPost.serviceID.value ;
                }
            }
        } else {
            if (document.frames.ExistedService.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.ExistedService.Flags.value;
                document.frmPost.serviceID.value = document.frames.ExistedService.txtServiceID.value;
            }
        }
        if (document.frmPost.serviceID.value == '') {
            alert("请选定业务!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.serviceID.value = '';
    
    document.frmPost.checkFlags.value = ";";
      
    if (document.frames.OptionalService.txtServiceID != null) {
        if (document.frames.OptionalService.txtServiceID.length > 1) {
            for (i = 0; i < document.frames.OptionalService.txtServiceID.length; i++) {
                if (document.frames.OptionalService.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalService.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.serviceID.value=document.frames.OptionalService.txtServiceID[i].value+ ";" + document.frmPost.serviceID.value ;
                }
                
            }
        } else {
            if (document.frames.OptionalService.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalService.Flags.value;
                document.frmPost.serviceID.value = document.frames.OptionalService.txtServiceID.value;
            }
              
        }
        if (document.frmPost.serviceID.value == '') {
            alert("请选定业务!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
 
    if (document.frames.ExistedService.txtServiceID != null) {
       
           document.frmPost.existedServiceID.value=document.frames.ExistedService.txtServiceNum.value;
       
    }
      
    return true;
}
 function add_service() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddService";
        document.frmPost.action="add_servie.do";
        document.frmPost.submit();
    }
} 
 function delete_service() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteService";
        document.frmPost.action="delete_service.do";
        document.frmPost.submit();
    }
}

 
//-->
</SCRIPT>
