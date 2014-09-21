<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO,
                 com.dtv.oss.dto.NewCustomerInfoDTO ,
                 com.dtv.oss.dto.CustomerDTO ,
                 com.dtv.oss.dto.AddressDTO ,
                 com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<TABLE align="center" width="650">
<TR style="HEIGHT: 56pt">
<TD ALIGN="center">
<B><SPAN style="FONT-SIZE: 20pt; FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体"></SPAN></B>
</TD>
</TR>
</TABLE>

<%
    int currentRecordCount=0;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >    
<%
	CustServiceInteractionDTO custServiceInteractionDTO = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");
	
	String csiType= custServiceInteractionDTO.getType()== null?"":custServiceInteractionDTO.getType();
	NewCustomerInfoDTO newCustomerInfoDTO=new NewCustomerInfoDTO() ;
	CustomerDTO customerDTO=new CustomerDTO();
	AddressDTO addressDTO=new AddressDTO();
	System.out.print(csiType+"**************************");
	
	if(csiType.equals("BK")||csiType.equals("OS")){
		newCustomerInfoDTO=Postern.getNewCustomerInfoDTOByCSIID(custServiceInteractionDTO.getId());
		pageContext.setAttribute("onecust", newCustomerInfoDTO);
		addressDTO=Postern.getAddressDtoByID(newCustomerInfoDTO.getFromAddressID());
	}
		
	if(csiType.equals("BU")||csiType.equals("UO"))	{
		customerDTO=Postern.getCustomerByID(custServiceInteractionDTO.getCustomerID());
		pageContext.setAttribute("onecust", customerDTO);
		addressDTO=Postern.getAddressDtoByID(customerDTO.getAddressID());
	}
		   
	pageContext.setAttribute("custaddr", addressDTO);
	System.out.print(addressDTO+"addressDTO=================");
	
	int iJobCardID = custServiceInteractionDTO.getReferJobCardID();
	com.dtv.oss.dto.JobCardDTO jcdto = Postern.getJobCardByID(iJobCardID);
	
	if (jcdto!=null) pageContext.setAttribute("onejc", jcdto);
    
	String strPkgName = "";
    
    java.util.Collection lst = CsrBusinessUtility.getPackagesByCSIID(custServiceInteractionDTO.getId());
    Iterator it = lst.iterator();
    int i=1;
    while (it.hasNext())
    {
        Integer iId = (Integer)it.next();
        strPkgName = strPkgName + String.valueOf(i) + ":" + Postern.getPackagenameByID(iId.intValue())+" ";
    }
%> 
  
<TABLE align="center" width="650">
<tr>
<TABLE ALIGN="CENTER" WIDTH="650" >
  <TR style="HEIGHT: 24pt">
  <td colspan="6">&nbsp</td>
  </TR>
  </TABLE>
</tr>
<tr>
<TABLE ALIGN="CENTER" WIDTH="650" >
  <TR style="HEIGHT: 24pt">
  <td colspan="6">&nbsp</td>
  </TR>
  </TABLE>
</tr>

  
    
  <TR>
  <TABLE ALIGN="CENTER" WIDTH="650" >
  <TR style="HEIGHT: 25.1pt">  
  <TD colspan="6"
    style=HEIGHT: 25.1pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体">
    &nbsp
  </TD>
  </TR>
  <TR style="HEIGHT: 25.1pt">
  <TD width="70" style=HEIGHT: 25.1pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  &nbsp
  </TD>
  <TD width="188"
  style=font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
   <tbl:write name="onecust" property="Name"/>&nbsp
  </TD>
  <TD width="40" NOWRAP>
  &nbsp
  </TD>
  <TD  width="130" 
  style="font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <tbl:write name="onecust" property="telephone"/>&nbsp
  </TD>
  <TD NOWRAP width="40">
  &nbsp
  </TD>
  <TD  
  style="font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <tbl:write name="onecust" property="mobileNumber"/>&nbsp　
  </TD>
  </TR>
  <TR style="HEIGHT: 25.1pt">
  <TD NOWRAP WIDTH="70"
  style="HEIGHT: 25.1pt" >
  &nbsp
  </TD>
  <TD colspan="5"
  style="HEIGHT: 25.1pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <tbl:write name="custaddr" property="detailAddress" />&nbsp　
  </TD>
  </TR>
  <TR style="HEIGHT: 25.1pt" align="center">
  <TD colspan="6" 
  style="HEIGHT: 25.1pt"  >
  &nbsp
  </TD>
  </TR> 
  </TABLE>
  </TR>
  
    
  
  
 <%String jobType=jcdto.getJobType();
   int referSheetId=jcdto.getReferSheetId();
   Collection deviceID=null;
   TerminalDeviceDTO tdto=null;
   String STBno="";
   String SCno="";
   if("I".equals(jobType)){
   	deviceID=Postern.getDeviceIDByCSIID(referSheetId);
   	Iterator itr=deviceID.iterator();
   	while(itr.hasNext()){
   		int deviceid=((Integer)(itr.next())).intValue();
   		if(deviceid!=0){
   			tdto=Postern.getTerminalDeviceByID(deviceid);
   			if("STB".equals(tdto.getDeviceClass())){
   				STBno=tdto.getSerialNo();
   			}else if("SC".equals(tdto.getDeviceClass())){
   				SCno=tdto.getSerialNo();
   			}
   		}
   	}
   }else if("R".equals(jobType)){
   	int csaid=Postern.getCustomerProblemDto(referSheetId).getCustServiceAccountID();
   	deviceID=Postern.getDeviceInfoByServiceAccountID(csaid);
   	Iterator itr=deviceID.iterator();
   	while(itr.hasNext()){
   		tdto=(TerminalDeviceDTO)(itr.next());
   		if("STB".equals(tdto.getDeviceClass())){
   			STBno=tdto.getSerialNo();
   		}else if("SC".equals(tdto.getDeviceClass())){
   				SCno=tdto.getSerialNo();
   		}   		
   	}
   	
   }
  %> 
  <TR>
  <TABLE ALIGN="CENTER" WIDTH="650">
  <TR style="HEIGHT: 25.1pt">
  <TD  NOWRAP WIDTH="70"
  style="HEIGHT: 25.1pt" >
  &nbsp
  </TD>
  <TD  width="280"
  style="HEIGHT: 25.1pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <%=STBno%>&nbsp
  </TD>
  <TD width="70"   NOWRAP 
  style="HEIGHT: 25.1pt" >
  &nbsp
  </TD>
  <TD 
  style="HEIGHT: 25.1pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <%=SCno%>&nbsp　
  </TD>
  </TR>
  </TABLE>
  </TR>
 
  <%if("R".equals(jobType)){
  CustomerProblemDTO cpDto=Postern.getCustomerProblemDto(referSheetId);
  String problemDesc=cpDto.getProblemDesc();%>
  <TR>
  <TABLE  ALIGN="CENTER" WIDTH="650" >
  <TR style="HEIGHT: 73pt">
  <TD WIDTH="70"
  style="HEIGHT: 73pt" >
  &nbsp
  </TD>
  <TD 
  style="HEIGHT: 73pt;font-size:16px;FONT-FAMILY: 宋体; mso-hansi-font-family: 宋体" >
  <%=problemDesc%>&nbsp
  </TD>  
  </TR>
  </TABLE>
  </TR>
  <%}%>
 </TABLE>      
</rs:isNoTheLastRecord>      
</lgc:bloop>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>