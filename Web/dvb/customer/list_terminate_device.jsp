<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.dto.DeviceModelDTO,
                 com.dtv.oss.web.util.WebUtil" %>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
     <%
	 String txtDeviceModel =request.getParameter("txtDevicemodel");
    	String txtDeviceClass =request.getParameter("txtDeviceClass");
    	int productId =(!WebUtil.StringHaveContent(request.getParameter("txtProductId"))) ? 0 :Integer.parseInt(request.getParameter("txtProductId"));
    	List deviceModelList =Postern.getDeviceModelDTOByProductID(productId);
    	if(deviceModelList == null) deviceModelList = new ArrayList();
        	Iterator deviceModelIter =deviceModelList.iterator();
          while (deviceModelIter.hasNext()){
            	DeviceModelDTO dto =(DeviceModelDTO)deviceModelIter.next();
            	if (txtDeviceModel ==null || txtDeviceModel.equals("") || txtDeviceModel.equals("null")){
               		if (Postern.getTerminalCountByModelAndClass(dto.getDeviceModel(),txtDeviceClass) ==0) 
               			continue; 
               		txtDeviceModel =dto.getDeviceModel() ;
            	}
         }
         
           //受理原因，add by zhouxushun 2007-5-17
           String txtCsiType=request.getParameter("txtCsiType");
    	   String txtCsiCreateReason=request.getParameter("txtCsiCreateReason");
    	%>
      
      <iframe name=head width=100% height=640 frameborder=0 scrolling=no src="list_terminate_device_included.screen?txtProductId=<%=productId%>&txtDeviceClass=<%=txtDeviceClass%>&txtDeviceModel=<%=txtDeviceModel%>&txtForm=1&txtTo=10&txtStatus=W&txtCsiType=<%=txtCsiType%>&txtCsiCreateReason=<%=txtCsiCreateReason%>" ></iframe>
    </td>
  </tr>
  
</table>