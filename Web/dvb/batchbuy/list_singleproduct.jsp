<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page
	import="com.dtv.oss.dto.ProductPackageDTO,com.dtv.oss.dto.OperatorDTO,com.dtv.oss.web.util.WebCurrentOperatorData"%>

<%@ page
	import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys,com.dtv.oss.web.util.WebKeys,java.util.*"%>

<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
	<tr class="list_head">
		<td width="10%" class="list_head">
			&nbsp;
		</td>
		<td width="23%" class="list_head" align="center">
			产品包名称
		</td>
		<td width="10%" class="list_head">
			&nbsp;
		</td>
		<td width="23%" class="list_head" align="center">
			产品包名称
		</td>
		<td width="10%" class="list_head">
			&nbsp;
		</td>
		<td width="23%" class="list_head" align="center">
			产品包名称
		</td>
	</tr>
	<%
		String openFlag = request.getParameter("OpenFlag");
		String csiType = request.getParameter("txtCsiType");
		String custType = request.getParameter("txtCustType");
		
		if (openFlag != null) {
			if (openFlag.equals(CommonKeys.ACTION_OF_BOOKING)
			|| openFlag.equals(CommonKeys.ACTION_OF_BOOKINGAGENT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK;
			else if (openFlag.equals(CommonKeys.ACTION_OF_BOOKINGACCOUNT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OB;
			else if (openFlag.equals(CommonKeys.ACTION_OF_SHOPACCOUNT))
				csiType = CommonKeys.CUSTSERVICEINTERACTIONTYPE_OS;
		}

		int operatorLevel = 0;
		WebCurrentOperatorData webCurrentOperatorData = (WebCurrentOperatorData) pageContext
				.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME);
		if (webCurrentOperatorData != null)
			operatorLevel = ((OperatorDTO) webCurrentOperatorData
			.getCurrentOperator()).getLevelID();
		String packageClass = "'S'";
		//ArrayList productPackageList =Postern.getProductPackageDTO(packageClass,operatorLevel);
		ArrayList productPackageList = Postern
				.getProductPackageDTOByCondition(packageClass,
				operatorLevel, csiType, null,custType);//null为captureFlag

		Iterator productPackageIter = productPackageList.iterator();
		while (productPackageIter.hasNext()) {
			ProductPackageDTO productPackageDto1 = (ProductPackageDTO) productPackageIter
			.next();
			pageContext.setAttribute("oneline", productPackageDto1);
	%>

	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
		<input type="hidden" name="listSPackageName" value="<tbl:write name="oneline" property="packageName" />">
		<td align="center">
			<tbl:checkbox name="listID3" value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" />
		</td>
		<td align="center">
			<a
				href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write
					name="oneline" property="packageName" />
			</a>
		</td>
		<%
				if (productPackageIter.hasNext()) {
				ProductPackageDTO productPackageDto2 = (ProductPackageDTO) productPackageIter
						.next();
				pageContext.setAttribute("oneline", productPackageDto2);
		%>
		<input type="hidden" name="listSPackageName" value="<tbl:write name="oneline" property="packageName" />">
		<td align="center">
			<tbl:checkbox name="listID3" value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" />
		</td>
		<td align="center">
			<a
				href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write
					name="oneline" property="packageName" />
			</a>
		</td>
		<%
		} else {
		%>
		<td align="center">
			&nbsp;
		</td>
		<td align="center">
			&nbsp;
		</td>
		<%
		}
		%>

		<%
				if (productPackageIter.hasNext()) {
				ProductPackageDTO productPackageDto3 = (ProductPackageDTO) productPackageIter
						.next();
				pageContext.setAttribute("oneline", productPackageDto3);
		%>
		<td align="center">
			<input type="hidden" name="listSPackageName" value="<tbl:write name="oneline" property="packageName" />">
			<tbl:checkbox name="listID3" value="oneline:packageID" match="SingleProductList" multipleMatchFlag="true" />
		</td>
		<td align="center">
			<a
				href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write
					name="oneline" property="packageName" />
			</a>
		</td>
		<%
		} else {
		%>
		<td align="center">
			&nbsp;
		</td>
		<td align="center">
			&nbsp;
		</td>
		<%
		}
		%>
	</tbl:printcsstr>
	<%
	}
	%>
  <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>	
</table>
