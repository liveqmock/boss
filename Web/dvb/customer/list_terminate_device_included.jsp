<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.dto.DeviceModelDTO,
                 com.dtv.oss.web.util.WebUtil
                 com.dtv.oss.web.util.CommonKeys" %>


<script language=javascript>


function check_form(){
	return true;
}


function query_submit(){
    if (check_form()){ 
        document.frmPost.submit();
    }
}

function frm_return(a)
{
	window.returnValue = a;
	window.close();
}
</script>

<%
    int productId =(!WebUtil.StringHaveContent(request.getParameter("txtProductId"))) ? 0 :Integer.parseInt(request.getParameter("txtProductId"));
    String  deviceClass =(request.getParameter("txtDeviceClass")==null) ? "" :request.getParameter("txtDeviceClass");
    String  matchFlag ="";
    Map  deviceMap =new HashMap();
    List deviceModelList =Postern.getDeviceModelDTOByProductID(productId);
    if(deviceModelList == null) deviceModelList = new ArrayList(); 
    Iterator deviceModelIter =deviceModelList.iterator();
    while (deviceModelIter.hasNext()){
        DeviceModelDTO dto =(DeviceModelDTO)deviceModelIter.next();
        if (!CommonKeys.DeviceClass_CM.equals(deviceClass)){  
           deviceMap.put(dto.getDeviceModel(),dto.getDeviceModel()); 
        }else{
        	 String cmModel =Postern.getCmModelByStbModel(dto.getDeviceModel());
        	 deviceMap.put(cmModel,cmModel);
        }
    }
    //�����CM����ֻ��ѡû��ƥ���CM,�����STB,SC��������
    if (CommonKeys.DeviceClass_CM.equals(deviceClass)){
        matchFlag ="N";
    }
    
    pageContext.setAttribute("DeviceMap", deviceMap);

%>

<form name="frmPost"  action="list_terminate_device_included.do" target="_self">
<input type="hidden" name="txtProductId" value=<%=productId%> >
<input type="hidden" name="txtMatchFlag" value="<%=matchFlag%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
 <td  class="querypart" > 
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" >
    <tr>   
      <td class="list_bg2" align="right">�ͺ�</td>
      <td colspan="3" class="list_bg1" align="left">
      	 <tbl:select name="txtDeviceModel" set="DeviceMap" match="txtDeviceModel" width="23" empty="false" />
      </td>
    </tr>
    <tr>  
      <td class="list_bg2" align="right">���к�</td>
      <td colspan="3" class="list_bg1" align="left">
          <input type="text" class="text" name="txtSerialNoBegin" size="15" maxlength="20" value="<tbl:writeparam name="txtSerialNoBegin" />" >
           -- <input type="text" class="text" name="txtSerialNoEnd" size="15" maxlength="20" value="<tbl:writeparam name="txtSerialNoEnd" />" >      
      </td>
        
    </tr>
    
    <tr>   
      <td class="list_bg2" align="right">Mac��ַ</td>
      <td colspan="3" class="list_bg1" align="left">
         <input type="text" class="text" name="txtMacAddress" size="23" maxlength="20" value="<tbl:writeparam name="txtMacAddress" />" >
      </td>
    </tr>
    <tr>   
      <td class="list_bg2" align="right">�ڲ�Mac��ַ</td>
      <td colspan="2" class="list_bg1" align="left">
         <input type="text" class="text" name="txtInterMacAddress" size="23" maxlength="20" value="<tbl:writeparam name="txtInterMacAddress" />" >
      </td>
<!--
    </tr>
    

    <tr>  
      
      <td class="list_bg2" align="right">�ֿ�</td>
      <td class="list_bg1" align="left">
      <%
    Map mapDepots = Postern.getAllDepot();
    pageContext.setAttribute("AllDepots",mapDepots);
%>          
        <tbl:select name="txtDepotID" set="AllDepots" match="txtDepotID" width="23" />
      	
      </td>
      -->
      <td align="center" class="list_bg1" width="10%"><a href="javascript:query_submit()"><font size="2"><strong>����</strong></font></a></td> 
    </tr>
   </table>
  </td>
  </tr> 
</table>
   
      <tbl:hiddenparameters pass="txtDeviceClass/txtCsiType/txtCsiCreateReason" />
      <input type="hidden" name="txtFrom" value="1">
      <input type="hidden" name="txtTo" value="10">
      <input type="hidden" name="txtStatus" value="W">
      
    
      <table width="100%" border="0" cellpadding="2" cellspacing="1" class="list_bg" >
         <tr class="list_head"> 
           <td width="10%"  class="list_head" align="center">ID</td>
           <td width="15%"  class="list_head" align="center">����</td>
           <td width="33%"  class="list_head" align="center">�ͺ�</td>          
           <td width="15%"  class="list_head" align="center">���к�</td>
           <td width="15%"  class="list_head" align="center">״̬</td>
           <td width="10%"  class="list_head" align="center"></td>
         </tr> 
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
	   TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("oneline");
	   String strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
				    
         %>
	  <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	    <td align="center" ><tbl:write name="oneline" property="DeviceID"/></td>
      	    <td align="center" ><%=strClass%></td>
      	    <td align="center" ><tbl:write name="oneline" property="deviceModel"/></td>
      	    <td align="center" ><tbl:write name="oneline" property="SerialNo"/></td>      
      	    <td align="center" ><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:Status"/></td>
      	    <td align="center" ><a href="javascript:frm_return('<tbl:write name="oneline" property="SerialNo"/>')" class="link12" >ѡ��</a></td>
    	  </tbl:printcsstr>
       </lgc:bloop>  
	
       <rs:hasresultset>
    	   <tr class="trline" >
              <td align="right" class="t12" colspan="6" >
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;            
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
           </tr>    
	</rs:hasresultset>
     </table>    
  
</form>


<table>
<tr >
    
    <td  colspan="2" class="tb13" align="left">
    <font size="1" color="red" >ע�����к�ֻ��д��һ������Ϊ��ȷ��ѯ</font>
    </td>
    <td  width="40" ></td>
    <td align="center" class="tb13" >
    <a href="#"onClick="window.close()">
                       <font size="2">�رմ���</font></a></p>
    </td>
</tr>  
</table> 
