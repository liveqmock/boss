<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO,
					com.dtv.oss.dto.JobCardDTO,
					com.dtv.oss.dto.AddressDTO,
					com.dtv.oss.dto.TerminalDeviceDTO,
					com.dtv.oss.dto.CustomerDTO" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE2 {FONT-FAMILY: 楷体_GB2312; mso-bidi-font-size: 14pt;font-size: 16px; }
-->
</style>
</head>
<%
    int currentRecordCount=0;
%>
<body>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >    
<%
	com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
	JobCardDTO jcdto=wrap.getJobCardDto();
	if (jcdto!=null) pageContext.setAttribute("onejc", jcdto);
    pageContext.setAttribute("process", wrap.getJobCardProcessDto());
	String orgname=Postern.getOrgNameByID(wrap.getJobCardProcessDto().getCurrentOrgId());
	String sername=Postern.getServiceNameByServiceAccountID(jcdto.getCustServiceAccountId());
	if(sername==null)
		sername="";
	if(orgname==null)
		orgname="";
    int customerid=jcdto.getCustId();
    CustomerDTO customerDTO=Postern.getCustomerByID(customerid);
    if (jcdto!=null) pageContext.setAttribute("onecust", customerDTO);
    CustomerProblemDTO customerProblemDTO=Postern.getCustomerProblemDto(jcdto.getReferSheetId());
    if (jcdto!=null) pageContext.setAttribute("custProblem", customerProblemDTO);
    
    
    AddressDTO addrDto = Postern.getAddressDtoByID(jcdto.getAddressId());
    pageContext.setAttribute("custaddr", addrDto);
    String strAddress = Postern.getAddressById(jcdto.getAddressId());
    if(strAddress==null){
    	strAddress="";
    }
   
   int serviceCode=Postern.getServiceCodeByAcctServiceID(jcdto.getCustServiceAccountId());
   java.util.Collection clo=Postern.getDeviceInfoByServiceAccountID(jcdto.getCustServiceAccountId()); 
   java.util.Iterator it=clo.iterator();
   String deviceModel="",No="";
   while(it.hasNext()){
	   TerminalDeviceDTO dto=(TerminalDeviceDTO)(it.next());
   		deviceModel=dto.getDeviceModel();
   		No=dto.getSerialNo();
   	}
%>  
<table width="660" height="374" cellspacing="0">
  <tr>
    <td height="40" colspan="4"><div align="center"><B><SPAN style="FONT-SIZE: 14pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">东 方 有 线 维 修 单</SPAN></B></div></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td width="138" height="27" class="STYLE2">分公司名称:</td>
    <td height="27" colspan="3" class="STYLE2"><%=orgname%></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">业务名称:</td>
    <td height="27" colspan="3" class="STYLE2"><%=sername%></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">用户类型:</td>
    <td width="182" height="27" class="STYLE2"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="onecust:customerType"   /></td>
    <td width="122" height="27" class="STYLE2">用户证号:</td>
    <td width="200" height="27" class="STYLE2"><tbl:write name="onecust" property="CustomerID"/></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">用户姓名:</td>
    <td height="27" class="STYLE2"><tbl:write name="onecust" property="Name"/></td>
    <td height="27" class="STYLE2">所属区域:</td>
    <td height="27" class="STYLE2"><tbl:WriteDistrictInfo name="custaddr" property="DistrictID" /></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">联系电话:</td>
    <td height="27" colspan="3" class="STYLE2"><tbl:write name="onejc" property="ContactPhone"/></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">用户地址:</td>
    <td height="27" colspan="3" class="STYLE2"><%=strAddress%></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">业务帐户ID:</td>
    <td height="27" class="STYLE2"><tbl:write name="onejc" property="custServiceAccountId"/></td>
    <td height="27" class="STYLE2">设备类型:</td>
    <td height="27" class="STYLE2"><%=deviceModel%></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">服务号码:</td>
    <td height="27" class="STYLE2"><%=serviceCode%></td>
    <td height="27" class="STYLE2">设备序列号:</td>
    <td height="27" class="STYLE2"><%=No%></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">报修单编号:<br>
    </td>
    <td height="27" class="STYLE2"><tbl:write name="custProblem" property="Id"/></td>
    <td height="27" class="STYLE2">报修时间:</td>
    <td height="27" class="STYLE2"><tbl:writedate name="custProblem" property="CreateDate"/></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">报修类别:</td>
    <td height="27" class="STYLE2"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="custProblem:ProblemCategory"/></td>
    <td height="27" class="STYLE2">收费类别:</td>
    <td height="27" class="STYLE2"><d:getcmnname typeName="SET_C_CPFEECLASS" match="custProblem:FeeClass"/></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">报修内容描述:</td>
    <td height="27" colspan="3" class="STYLE2"><tbl:write name="custProblem" property="ProblemDesc"/></td>
    </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">工单编号:</td>
    <td height="27" class="STYLE2"><tbl:write name="onejc" property="referSheetId"/></td>
    <td height="27" class="STYLE2">联系时间:</td>
    <td height="27" class="STYLE2"><tbl:writedate name="onejc" property="preferedDate" includeTime="false"/></td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">预约结果:</td>
    <td height="27" class="STYLE2"><o:getcmnname typeName="SET_W_JOBCARDCONTACTRESULT" match="onejc:workResult"/></td>
    <td height="27" class="STYLE2">预约上门时间:</td>
    <td height="27" class="STYLE2">
    <o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="onejc:preferedTime" /></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">维修信息反馈:</td>
    <td height="27" colspan="3" class="STYLE2"><tbl:write name="process" property="Memo"/></td>
  </tr>
  <tr>
    <td height="27" colspan="4" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">用户签字:</td>
    <td height="27" colspan="3" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">日期:</td>
    <td height="27" colspan="3" class="STYLE2">&nbsp;</td>
  </tr>
  <tr>
    <td height="27" class="STYLE2">维修人员签字:</td>
    <td height="27" colspan="3" class="STYLE2">&nbsp;</td>
  </tr>
</table>
<%
	currentRecordCount=currentRecordCount+1;
%>
 <rs:isNoTheLastRecord   itemCount="<%=currentRecordCount%>">	
<p STYLE="page-break-after: always">&nbsp;</p>
</rs:isNoTheLastRecord>     
</lgc:bloop>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
</body>
</html>
