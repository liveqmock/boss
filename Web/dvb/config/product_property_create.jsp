<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.dto.ProductPropertyDTO" %>

<Script language=javascript>
<!--
  
function check_form(){	
	if (check_Blank(document.frmPost.txtPropertyName, true, "属性名称"))
		return false;
	if (check_Blank(document.frmPost.txtPropertyCode, true, "属性代码"))
		return false;
	 
	if (!check_Blank(document.frmPost.txtPropertyMode, true, "取值方式"))
	{	
		if(document.frmPost.txtPropertyMode.value=='R')
		{
			if (check_Blank(document.frmPost.txtResourceName, true, "资源名称"))
				return false;
		}
		if(document.frmPost.txtPropertyMode.value=='F')
		{
			if (check_Blank(document.frmPost.txtPropertyValue, true, "固定值"))
				return false;
		}
	}else
	{
		return false;
	}

	
	return true;
}

function add_submit(){
	document.frmPost.action="product_property_op.do";
	if(check_form())
		document.frmPost.submit();
}
function back_submit(){
	document.frmBack.action='<bk:backurl property="product_property_query.do" />';
	document.frmBack.submit();
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
    <td class="title">产品属性管理-添加</td>
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
   
   
   pageContext.setAttribute("pDTO",pDTO);
 %>
<%
//设置资源名称列表内容
   java.util.Map resourceNames=Postern.getResourceNames();
   pageContext.setAttribute("resNames",resourceNames);
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
  
<form name="frmPost" method="post" action="product_property_op.do">

    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">产品属性信息</font></td>
	</tr>
        <tr>
            <td class="list_bg2" ><div align="right">属性名称*</div></td>
            <td class="list_bg1" ><input type="text" name="txtPropertyName" size="25" maxlength="25" value="<tbl:writeparam name="txtPropertyName"/>" class="text"></td>
            <td class="list_bg2" ><div align="right">属性代码*</div></td>
            <td class="list_bg1" ><d:selcmn name="txtPropertyCode" mapName="SET_P_PRODUCTPROPERTYCODE" match="txtPropertyCode" width="23" />
			</td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">取值方式*</div></td>
            <td class="list_bg1"><d:selcmn name="txtPropertyMode" mapName="SET_P_PRODUCTPROPERTYMODE" match="txtPropertyMode" width="23" /></td>
            <td class="list_bg2"><div align="right">资源名称</div></td>
            <td class="list_bg1"><tbl:select name="txtResourceName" set="resNames" match="txtResourceName" width="23"/>
			</td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">固定值</div></td>
            <td class="list_bg1"><input type="text" name="txtPropertyValue" size="25" maxlength="100" value="<tbl:writeparam name="txtPropertyValue"/>" class="text">
			</td>
            <td class="list_bg2"><div align="right">取值类型</div></td>
            <td class="list_bg1"><d:selcmn name="txtPropertyType" mapName="SET_G_PARAMETERTYPE" match="txtPropertyType" width="23" />
			</td>
        </tr> 
         <tr>
          <td class="list_bg2"><div align="right">属性描叙 </div></td>
          <td class="list_bg1" colspan="3"><font size="2">
           <input type="text" name="txtDescription" size="83" maxlength="100" value="<tbl:writeparam name="txtDescription"/>" class="text" >   
              
          </font></td>
            
    <input type="hidden" name="txtProductID" size="20" value="<tbl:writeparam name="txtProductID"/>">
    <input type="hidden" name="txtActionType" size="20" value="CREATE">
    <input type="hidden" name="func_flag" value="103">
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>	
		         <td><img src="img/button2_r.gif" width="20" height="20"></td>
	               <td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
                         <td><img src="img/button2_l.gif" width="11" height="20"></td>
		  	<td width="20" ></td>		
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="新 增" onclick="javascript:add_submit()"></td>
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
  
</form>
