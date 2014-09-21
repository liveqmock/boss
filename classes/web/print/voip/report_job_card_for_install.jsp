<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO,
                 com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.NewCustomerInfoDTO,
                 com.dtv.oss.dto.JobCardDTO"  %>
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
<%
    int currentRecordCount=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >    
<%
    CustServiceInteractionDTO csiDto = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");
    String custname ="";
    String telephone ="";
    String detailAddress ="";
    String custType ="";
    String strOpenSourceTypeName ="";
    String orgName ="";
    
    NewCustomerInfoDTO newCustomerInfoDto =Postern.getNewCustomerInfoDTOByCSIID(csiDto.getId()) ;
    CustomerDTO customerDto =Postern.getCustomerByID(csiDto.getCustomerID());
    JobCardDTO   jobCardDto =Postern.getJobCardDto(csiDto.getReferJobCardID());
    
    pageContext.setAttribute("onejc", jobCardDto);
   
    orgName =Postern.getOrgNameByOrgID(customerDto.getOrgID());
    if (orgName ==null) orgName ="";
    
    //来源渠道
    Map mapOpenSourceType = Postern.getHashKeyValueByName("SET_C_CUSTOPENSOURCETYPE");
    
    System.out.println("csiDto.getType()========="+csiDto.getType());
    
    if ("OB".equals(csiDto.getType()) || "BK".equals(csiDto.getType()) || "OS".equals(csiDto.getType())){
      
        detailAddress =Postern.getAddressById(newCustomerInfoDto.getFromAddressID());
        custType =newCustomerInfoDto.getType();
        strOpenSourceTypeName = (String)mapOpenSourceType.get(newCustomerInfoDto.getOpenSourceType());
        if (strOpenSourceTypeName==null) strOpenSourceTypeName="";
               
    }else {
      
        detailAddress =Postern.getAddressById(customerDto.getAddressID());
        custType =customerDto.getCustomerType();
        strOpenSourceTypeName = (String)mapOpenSourceType.get(customerDto.getOpenSourceType());
        if (strOpenSourceTypeName==null) strOpenSourceTypeName="";
    }
    
    String strPkgName = "";   
    Collection lst = CsrBusinessUtility.getPackagesByCSIID(csiDto.getId());
    Iterator it = lst.iterator();
    int i=1;
    while (it.hasNext())
    {
        Integer iId = (Integer)it.next();
        strPkgName = strPkgName + String.valueOf(i) + ":" + Postern.getPackagenameByID(iId.intValue())+" ";
    }
    
    String terminalDeviceStr ="";
    TerminalDeviceDTO tdto=null;
    Collection deviceID=Postern.getDeviceIDByCSIID(jobCardDto.getReferSheetId());
   	Iterator itr=deviceID.iterator();
   	while(itr.hasNext()){
   		int deviceid=((Integer)(itr.next())).intValue();
   		if (deviceid!=0){
   			 tdto=Postern.getTerminalDeviceByID(deviceid);
   			 terminalDeviceStr =terminalDeviceStr + tdto.getDeviceClass()+": "+tdto.getSerialNo()+" ";
   		}
   	}
   	
%> 
<table align="center" width="641" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD HEIGHT="40" >&nbsp;</TD>
</TR>
<TR>
<TD ALIGN="center">
<B><SPAN style="FONT-SIZE: 16pt; FONT-FAMILY: 黑体; mso-hansi-font-family: 宋体">东 方 有 线 安 装 单</SPAN></B>
</TD>
</TR>
<TR>
<TD HEIGHT="40" >&nbsp;</TD>
</TR>
<tr>
  <td colspan="2" align="center" >  
  
<table width="100%" border=0 align="center" cellpadding=0 cellspacing=0 >
 <tr>
  <td width=117 height="30"  align="left" class="style2">分公司：</td>
  <td width=146 class="style2">&nbsp;<%=orgName%></td>
  <td width=117  class="style2" align="left">安装单号：</div></td>
  <td width=227 class="style2">&nbsp;<tbl:write name="onejc" property="jobCardId" /></td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30"  align="left" class="style2">来源渠道：</td>
  <td colspan="3" class="style2">&nbsp;<%=strOpenSourceTypeName%> </td>
 </tr>
 <tr>
  <td width=117 height="30"  align="left" class="style2">用户姓名：</td>
  <td width=146 class="style2">&nbsp;<tbl:write name="onejc" property="contactPerson" /></td>
  <td width=117  align="left" class="style2">用户证号：</td>
  <td width=227 class="style2">&nbsp;<tbl:write name="oneline" property="customerID" /></td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">安装地址：</td>
  <td colspan="3" class="style2">&nbsp;<%=detailAddress%></td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">联系电话：</td>
  <td colspan="3" class="style2">&nbsp;<tbl:write name="onejc" property="contactPhone" /></td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30"  align="left" class="style2">订购产品：</td>
  <td colspan="3" class="style2">&nbsp;<%=strPkgName%></td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">设备编号：</td>
  <td colspan="3" class="style2">&nbsp;<%=terminalDeviceStr%></td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30"  align="left" class="style2">工单创建日期: </td>
  <td colspan="3" class="style2">&nbsp;<tbl:writedate name="onejc" property="dtCreate" /></td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">预约上门日期：</td>
  <td colspan="3" class="style2">&nbsp;<tbl:writedate name="onejc" property="preferedDate" /></td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">上门安装日期：</td>
  <td colspan="3" class="style2">&nbsp;<tbl:writedate name="onejc" property="dueDate" /></td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="50" align="left" class="style2">安装信息反馈：</td>
  <td colspan="3" class="style2">&nbsp;</td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">用户签字：</td>
  <td colspan="3" class="style2">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30" align="left" class="style2">日期：</td>
  <td colspan="3" class="style2">&nbsp;</td>
 </tr>
 <tr>
   <td colspan="4" height="10">&nbsp;</td>
 </tr>
 <tr>
  <td width=117 height="30"  align="left" class="style2">&nbsp;</td>
  <td width=146 class="style2">&nbsp;</td>
  <td width=117  align="left" class="style2">安装人员签字：</div></td>
  <td width=227 class="style2">&nbsp;</td>
 </tr>
</table>
  </td>
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
 
 
 
 
 
 