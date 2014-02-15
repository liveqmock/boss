<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
<!-- 
 

function checkForm() {
    document.frmPost.productID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.ExistedProduct.txtProductID != null) {
        if (document.frames.ExistedProduct.txtProductID.length > 1) {
            for (i = 0; i < document.frames.ExistedProduct.txtProductID.length; i++) {
                if (document.frames.ExistedProduct.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.ExistedProduct.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.productID.value=document.frames.ExistedProduct.txtProductID[i].value+ ";" + document.frmPost.productID.value ;
                    
                }
               
            }
        } else {
            if (document.frames.ExistedProduct.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.ExistedProduct.Flags.value;
                document.frmPost.productID.value = document.frames.ExistedProduct.txtProductID.value;
            }
        }
        if (document.frmPost.productID.value == '') {
            alert("请选定产品!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.productID.value = '';
     document.frmPost.selectProductID.value = '';
    document.frmPost.checkFlags.value = ";";
      document.frmPost.selectFlags.value = ";";
    if (document.frames.OptionalProduct.txtProductID != null) {
        if (document.frames.OptionalProduct.txtProductID.length > 1) {
            for (i = 0; i < document.frames.OptionalProduct.txtProductID.length; i++) {
                if (document.frames.OptionalProduct.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalProduct.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.productID.value=document.frames.OptionalProduct.txtProductID[i].value+ ";" + document.frmPost.productID.value ;
                }
                if (document.frames.OptionalProduct.Flags[i].checked && document.frames.OptionalProduct.selectFlags[i].checked) {
                    
                    document.frmPost.selectFlags.value=document.frames.OptionalProduct.selectFlags[i].value+";"+document.frmPost.selectFlags.value;
                    document.frmPost.selectProductID.value=document.frames.OptionalProduct.txtProductID[i].value+ ";" + document.frmPost.selectProductID.value ;
                    
                }
            }
        } else {
            if (document.frames.OptionalProduct.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalProduct.Flags.value;
                document.frmPost.productID.value = document.frames.OptionalProduct.txtProductID.value;
            }
             if (document.frames.OptionalProduct.Flags.checked && document.frames.OptionalProduct.selectFlags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalProduct.Flags.value;
                document.frmPost.selectProductID.value = document.frames.OptionalProduct.txtProductID.value;
            }
        }
        if (document.frmPost.productID.value == '') {
            alert("请选定产品!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
  
    if (document.frames.ExistedProduct.txtProductID != null) {
       for (i = 0; i < document.frames.ExistedProduct.txtProductID.length; i++) {
           document.frmPost.existedProductID.value=document.frames.ExistedProduct.txtProductID[i].value+";"+document.frmPost.existedProductID.value;
       }
    }
    
    return true;
}
function delete_product() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteProduct";
        document.frmPost.action="delete_product.do";
        document.frmPost.submit();
    }
}

function add_product() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddProduct";
        document.frmPost.action="add_product.do";
        document.frmPost.submit();
    }
} 
 
//-->
 
</script>
 <%
 String checkFlags = request.getParameter("checkFlags");
 String selectFlags = request.getParameter("selectFlags");
if (selectFlags == null)
    selectFlags = ""; 
 
if (checkFlags == null)
    checkFlags = "";%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">产品包明细信息管理</td>
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
          <td  class="list_bg2"><div align="right">产品包ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageID" size="25"  value="<tbl:writeparam name="txtPackageID"/>" class="textgray" readonly >
           </td>   
            <td  class="list_bg2"><div align="right">产品包名称</div></td>         
            <td class="list_bg1" align="left">
           <input type="text" name="txtPackageName" size="25" value="<tbl:writeparam name="txtPackageName"/>"  class="textgray" readonly >
           </td>         
       </tr>
      
      
        <tr>  
             <td class="list_bg2"><div align="right">有效开始时间</div></td>
             <td class="list_bg1" align="left">
             <input type="text" name="txtDateFrom" size="25"  class="textgray" readonly value="<tbl:writeparam name="txtDateFrom"/>"> 
             </td>
             <td class="list_bg2"><div align="right">有效结束时间</div></td>
               <td class="list_bg1" align="left">
             <input type="text" name="txtDateTo" size="25" class="textgray" readonly value="<tbl:writeparam name="txtDateTo"/>"> 
     	  </td>
      </tr>
     
      <tr>
          <td class="import_tit"   colspan="4"><font size="3"><div align="center"> 产品列表</div></font></td>
     </tr>
      <tr>
          <td class="list_bg2" colspan="2"><div align="center">已有产品</div></td>
            
          <td class="list_bg2" colspan="2"><div align="center">可选产品</div></td>
     </tr>
   </table>
   
  
  <input type="hidden" name="Action" value=""/>
   <input type="hidden" name="productID" value=""/>
    <input type="hidden" name="selectProductID" value=""/>
    <input type="hidden" name="existedProductID" value=""/>
  <input type="hidden" name="checkFlags" value=""/>
  <input type="hidden" name="selectFlags" value=""/>
  <input type="hidden" name="packageID" value="<tbl:writeparam name="txtPackageID"/>"/>
  <tbl:hiddenparameters pass="txtOptionFlag/txtPackageID/txtPackageName/txtDateFrom/txtDateTo/txtPackageClass"/> 
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
     <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <br>
          <iframe SRC="existed_product.screen?txtPackageID=<tbl:writeparam name="txtPackageID"/>&checkFlags=<%=checkFlags%>"
            id="ExistedProduct" name="ExistedProduct" width="320" height="300">
  	      </iframe><br><br>
          </font></td>
          <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:add_product()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr><tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="删&nbsp;除" onclick="javascript:delete_product()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
           
         </tr>
      </table>   
      </td>
          <td valign="middle" class="list_bg2" align="center" ><font size="2">
          <br>
          <iframe SRC="optional_product.screen?txtPackageID=<tbl:writeparam name="txtPackageID" />&checkFlags=<%=checkFlags%>&selectFlag=<%=selectFlags%>"
            id="OptionalProduct" name="OptionalProduct" width="320" height="300">
  	      </iframe>
  	      <br><br>
          </font></td>
     </tr>
      
   
      
   </table> 
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
                        
           <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="package_query_result.do/product_package_detail.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" border="0" ></td>
         </tr>
      </table>   
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
    
</form>
