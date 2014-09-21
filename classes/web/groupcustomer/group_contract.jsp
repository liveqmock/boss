<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,com.dtv.oss.dto.ContractDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>
<%
	String txtContractNo =request.getParameter("txtContractNo")==null?"":request.getParameter("txtContractNo");
	String txtContractName="" ,txtDescription="",txtCustPackage="";

	ContractDTO contractDTO=Postern.getContractDTOByContractNo(txtContractNo);
	txtContractName=contractDTO.getName();
	txtDescription=contractDTO.getDescription();
	
	
	Collection col=Postern.getPackageIDByContractID(txtContractNo);
	Iterator ite=col.iterator();
	int i=0;
	while(ite.hasNext())
	{
	  if(!("".equals(txtCustPackage)))
	  {
	  	txtCustPackage+=";";
	  }
		txtCustPackage = txtCustPackage + Postern.getPackagenameByID(WebUtil.StringToInt(ite.next().toString()));
	   }
	   
	 if(txtContractName == null || "null".equals(txtContractName))
	 	txtContractName="";
	   
	 if(txtDescription == null || "null".equals(txtDescription))
	 	txtDescription="";
	 	if(txtCustPackage == null || "null".equals(txtCustPackage))
	 	txtCustPackage="";
	 	
%>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">合同信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">合同编号</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtContractNo" size="25" value="<tbl:writeparam name="txtContractNo"/>"  class="textgray" readonly >
	  </td>
      <td class="list_bg2" width="17%"  align="right">合同名称</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtContractName" size="25" value="<%=txtContractName%>"  class="textgray" readonly>
	  </td>
    </tr>

     <tr>   
      <td class="list_bg2" width="17%"  align="right">合同描述</td>
      <td class="list_bg1" width="83%" colspan="3"  align="left">
		<input type="text" name="txtDescription" size="25" value="<%=txtDescription%>"  class="textgray" readonly>
	  </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">产品包</td>
      <td class="list_bg1" width="83%" colspan="3" align="left"><%=txtCustPackage%>
		<input type="hidden" name="txtCustPackage" size="25" value="<%=txtCustPackage%>"  class="textgray" readonly >
	  </td>
    </tr>

</table>