<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.DeviceTransitionDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>


<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%"><div align="center"> 
      <p align="center" class="title1">���ڻ�ȡԤԼ��Ϣ�����Ժ򡣡���</strong></p>
    </div></td>
  
</tr>
</table>
      
      
 
<form name="frmPost" method="post" >
       

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
<%
    DeviceTransitionDTO dto = (DeviceTransitionDTO)pageContext.getAttribute("oneline");
    
    String strOperName = Postern.getOperatorNameByID(dto.getOperatorID());
    if (strOperName==null) strOperName="";
    
    Map mapDepots = Postern.getAllDepot();
    String strFromAddress = null;
        
    if (WebUtil.StringHaveContent(dto.getFromType()))
    {
   	if (dto.getFromType().equals("D"))  //�ֿ�
	{
		strFromAddress = (String)mapDepots.get(String.valueOf(dto.getFromID()));
	}
	else if (dto.getFromType().equals("T"))  //��֯����
	{
		strFromAddress = Postern.getOrganizationDesc(dto.getFromID());
	}
	else if (dto.getFromType().equals("B"))  //�û�
	{
		strFromAddress = Postern.getAddressByCustomerID(dto.getFromID());
	}
    }	
	
    if (strFromAddress==null) strFromAddress="";
    
    String strToAddress = null;
        
    if (WebUtil.StringHaveContent(dto.getToType()))
    {
   	if (dto.getToType().equals("D"))  //�ֿ�
	{
		strToAddress = (String)mapDepots.get(String.valueOf(dto.getToID()));
	}
	else if (dto.getToType().equals("T"))  //��֯����
	{
		strToAddress = Postern.getOrganizationDesc(dto.getToID());
	}
	else if (dto.getToType().equals("B"))  //�û�
	{
		strToAddress = Postern.getAddressByCustomerID(dto.getToID());
	}
    }	
	
    if (strToAddress==null) strToAddress="";
%>	
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
        <tr> 
            <input type="hidden" name="txtBatchID" value="<tbl:write name="oneline" property="BatchID" />"  >
          
            
           
           <td  class="list_bg2"><div align="right">��ת����</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden"  name="txtBatchNoShow" size="25" maxlength="10" value="<tbl:write name="oneline" property="BatchNo"/>" class="textgray" readonly >          
           </td>
        
           <input type="text"  type="hidden" name="txtCreateTimeShow" size="25"  value="<tbl:writedate  name="oneline" property="CreateTime" includeTime="true" />" class="textgray" readonly >
           
          <input type="text"  type="hidden"  name="txtOperatorIDShow" size="25" maxlength="10" value="<%=strOperName%>" class="textgray" readonly >
          </td>
          
        </tr>
        <tr> 
          <td class="list_bg2"><div align="right">��������</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtActionShow" size="25"  value="<d:getcmnname typeName="SET_D_DTACTION" match="oneline:Action" />" class="textgray" readonly >          
          </td>
          <td class="list_bg2"><div align="right">�豸��</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtDeviceNumber" size="25"  value="<tbl:write name="oneline" property="DeviceNumber"/> " class="textgray" readonly >
          </td>
        </tr>
        <tr> 
           <td class="list_bg2"><div align="right">�˳�������</div></td>
           <td class="list_bg1"> 
          <input type="text"  type="hidden" name="txtFromtype" size="25" maxlength="10" value="<d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:FromType" />" class="textgray" readonly >
          </td>
          <td class="list_bg2"><div align="right">���������</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtToType" size="25" maxlength="10" value="<d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:ToType" />" class="textgray" readonly >
           </td>
        </tr>
        <tr> 
           <td class="list_bg2"><div align="right">�˳���</div></td>
           <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtFromID" size="25"  value="<%=strFromAddress%>" class="textgray" readonly >          
          </td>
          <td class="list_bg2"><div align="right">�����</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtToID" size="25"  value="<%=strToAddress%>" class="textgray" readonly >
          </td>
        </tr>
        
         <tr> 
          <td class="list_bg2"><div align="right">�����ļ�</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden" name="txtDataFileName" size="25"  value="<tbl:write name="oneline" property="DataFileName"/>" class="textgray" readonly >          
         </td>
          <td class="list_bg2"><div align="right">��¼״̬</div></td>
          <td class="list_bg1"> 
          <input type="text" type="hidden"  name="txtStatus" size="25"  value="<d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status" />" class="textgray" readonly >
          </td>
        </tr>
	<tr>
					<td  class="list_bg2"><div align="right">�豸��תԭ��</div></td>
            <td class="list_bg1">
            <input type="text" type="hidden"  name="txtStatusReason" size="25"  value="<d:getcmnname typeName="SET_D_TRANSFERREASON" match="oneline:StatusReason" />" class="textgray" readonly >
           </td>
            
          <td valign="middle" class="list_bg2" ><div align="right">����</div></td>
          <td class="list_bg1">
          <input type="text" type="hidden" name="txtDescription" size="82"  value="<tbl:write name="oneline" property="Description"/>" class="textgray" readonly >
          </td>
        </tr>
</table>


		 
         
          
 </lgc:bloop>         

</form>
</td></tr>
</table>

<script language=javascript>
 
       document.frmPost.action ="devtransHis_detail_devices.do" ;
  
       document.frmPost.submit(); 
  
</script>
