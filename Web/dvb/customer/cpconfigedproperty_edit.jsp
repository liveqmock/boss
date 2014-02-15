<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.ProductPropertyDTO"%>

<SCRIPT LANGUAGE="JavaScript">
<!--
	function passwordValidate ()
	{
		if(document.frmPost.passwordFieldName!=null){
			var pass1=document.frmPost.passwordFieldName[0].value;
			var pass2=document.frmPost.passwordFieldName[1].value;
			if(pass1==pass2)
				return true;
			else {
				alert("新密码设置错误,请重新输入.");
				return false;
			}
		}else{
			return true;
		}
		return false;
	}
	
	function update_submit(){
		if(document.frmPost.txtPropertyValue.value==""){
				alert("不能为空!");
				document.frmPost.txtPropertyValue.focus();
				return false;
		}
		
		if (window.confirm('您确认要执行修改吗?')){	
			document.frmPost.action="cpconfigedproperty_op.do";
			document.frmPost.submit();
		}
	}
//-->
</SCRIPT>

<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="820">
	<TR>
		<TD align="center">
		<br>

			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
			  <tr>
				<td><img src="img/list_tit.gif" width="19" height="19"></td>
				<td class="title">查看用户产品属性信息</td>
			  </tr>
			</table>

			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
			  <tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			  </tr>
			</table>

			<br>
			<form name="frmPost" method="post">

				<INPUT TYPE="hidden" name="txtActionType" value="update"> 
				<tbl:hiddenparameters pass="txtServiceAccountID/txtAccountID/txtCustomerID/txtProductID/txtPsID/txtSerialNo/txtDeviceID/txtStatus" />


				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					<tr>
						<td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td>
					</tr>
					<%
					String strProductID=request.getParameter("txtProductID");
					String strStatus=request.getParameter("txtStatus");
					String strPsID=request.getParameter("txtPsID");
					String strPropertyCode=request.getParameter("txtPropertyCode");
					Map match=Postern.getProductPropertyValueByProductID("productProperty_"+strProductID+"_",strPsID);
					//System.out.println("strPsID======"+strPsID);
					System.out.println("match======"+match);
					System.out.println("strStatus======"+strStatus);
					pageContext.setAttribute("mapMatch",match);
					pageContext.setAttribute("strStatus",strStatus);
					
					ProductPropertyDTO properDTO = Postern.getProductPropertyDTObyPrimaryKey(strProductID,strPropertyCode);
					
					%>
					<!--
					<tbl:productproperty serveyName="productProperty" match="mapMatch" showType="text" isChangePassword="true" passName="passwordFieldName" productID="<%=strProductID%>"  headStyle="import_tit"  tdWordStyle="list_bg2" tdControlStyle="list_bg1"/>
					-->
				</table>
				
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					<INPUT TYPE="hidden" name="txtPropertyCode" value="<%=strPropertyCode%>" >
					<INPUT TYPE="hidden" name="txtPsID" value="<%=strPsID%>" >
					<INPUT TYPE="hidden" name="txtProductID" value="<%=strProductID%>" >
					<INPUT TYPE="hidden" name="txtPropertyName" value="<%=properDTO.getPropertyName()%>" >
					<tr>
						<td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td>
					</tr>
					<tr>
						<td colspan="4" algin="right" class="import_tit" ><font size="3"><%=Postern.getProductNameByID(Integer.parseInt(strProductID))%></font></td>
					</tr>
					<tr>
						<td align="right" class="list_bg2"><%=properDTO.getPropertyName()%></td>
						<td align="left" class="list_bg1">
						<%if("30003".equals(strPropertyCode)){%>
							******
						<%}else{%>
							<%=WebUtil.NullToString(Postern.getPropertyValueByPSIDAndPropertyCode(Integer.parseInt(strPsID),strPropertyCode))%>
						<%}%>
						</td>
						<td align="right" class="list_bg2">新<%=properDTO.getPropertyName()%></td>
						<td align="left" class="list_bg1">
							<input type="text" name="txtPropertyValue" size="25"  value="">
						</td>
					</tr>
				</table>

    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

		 <table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
				<bk:canback url="customer_product_view.do" >  
					   <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
					   <td background="img/button_bg.gif"  ><a href="<bk:backurl property="customer_product_view.do" />" class="btn12">返回产品信息</a></td>
					   <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
				</bk:canback>          
					<d:displayControl id="button_customer_product_property_modify" bean="mapMatch,strStatus">
					   <%if("30002".equals(strPropertyCode)){ //用户名%>
					   <pri:authorized name="cpconfigedproperty_band_user_modify.do">
					   <td width="20" ></td>
					   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
					   <td><input name="Submit" type="button" class="button" onclick="javascript:update_submit()" value="修  改"></td>
					   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
					   </pri:authorized>
					   <%} else if("30003".equals(strPropertyCode)){ //密码%>
					   <pri:authorized name="cpconfigedproperty__band_password_modify.do">
					   <td width="20" ></td>
					   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
					   <td><input name="Submit" type="button" class="button" onclick="javascript:update_submit()" value="修  改"></td>
					   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
					   </pri:authorized>
					   <%} else {%>
					   <pri:authorized name="cpconfigedproperty_view.screen">
					   <td width="20" ></td>
					   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
					   <td><input name="Submit" type="button" class="button" onclick="javascript:update_submit()" value="修  改"></td>
					   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
					   </pri:authorized>
					   <%}%>
					</d:displayControl>
					</tr>
				</table>
	   </td>
	</tr>
    </table>    

			</form>

		</td>
	</tr>
</table>
