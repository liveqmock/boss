<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>

<%
	String strType=request.getParameter("type");
	String strTypeName=request.getParameter("typeName");
	String strTypeValue=request.getParameter("typeValue");
	String strParentType=request.getParameter("parentType");
	String strParentTypeName=request.getParameter("parentTypeName");
	String strParentTypeValue=request.getParameter("parentTypeValue");
	String strProductIDList=request.getParameter("txtProductIDList");
	

%>
<Script language=JavaScript>
<!--
function fillList()
{
        document.frmPost.ProductList.value="";
	 
	if (document.frames.FrameProduct.listID!=null)
	{
            if (document.frames.FrameProduct.listID.length > 1) 
	    {
	        for (i=0;i<document.frames.FrameProduct.listID.length;i++)
		    if (document.frames.FrameProduct.listID[i].checked)
		    {
			if (document.frmPost.ProductList.value!="")
		        {
			    document.frmPost.ProductList.value = document.frmPost.ProductList.value + ";";
			    document.frmPost.ProductListName.value = document.frmPost.ProductListName.value + ";";
				
			}
			document.frmPost.ProductList.value=document.frmPost.ProductList.value + document.frames.FrameProduct.listID[i].value;
			document.frmPost.ProductListName.value=document.frmPost.ProductListName.value + document.frames.FrameProduct.productName[i].value;
    	    	    }
	    } 
	    else 
	    {
            	if (document.frames.FrameProduct.listID.checked) {
                    document.frmPost.ProductList.value=document.frames.FrameProduct.listID.value + ";";
	        } else {
                document.frmPost.ProductList.value = '';
            	}
            }
	}
}

function ok_submit()
{
    fillList();
    if(document.frmPost.ProductList.value == null ||document.frmPost.ProductList.value==""){
	alert("您没有选择选项");
     	return;
    }
    var hiddenValue=document.frmPost.ProductList.value;
    var textValue=document.frmPost.ProductListName.value;
    var hiddenName="" + "<%=strTypeName %>";
    var textName=hiddenName + "Value";
    window.opener.frmPost.elements[hiddenName].value=hiddenValue;
       
    window.opener.frmPost.elements[textName].value=textValue;
    self.close();
    
}
//-->
</Script>

<form name="frmPost" method="post" action="device_match.do">

<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预授权产品选择</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  <tr>
	<td class="list_bg1" valign=top>
	    <font size="2">
        <iframe SRC="list_product.do?txtStatus=N&txtProductIDList=<%=strProductIDList%>" name="FrameProduct" width="550" height="160"></iframe>
            </font>
        </td>
  </tr>
</table>
<BR><BR><BR>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="200" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:ok_submit()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <td width="20" ></td>	
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:self.close();"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
  </tr>
</table>
<br>
<input type="hidden" name="ProductList" value="">
<input type="hidden" name="ProductListName" value="">
</form>