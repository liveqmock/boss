<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.DeviceTransitionDetailDTO" %>

 
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备流转历史信息-设备明细</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="" >
  
 
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
        <tr> 
            <td  class="list_bg2" width="20%"><div align="right">流转ID</div></td>
            <td class="list_bg1" width="30%"> <tbl:writeparam name="txtBatchID"/> </td>
            <td  class="list_bg2" width="20%"><div align="right">流转单号</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtBatchNoShow"/> </td>
        </tr>
         <tr> 
            <td  class="list_bg2"><div align="right">创建时间</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtCreateTimeShow"/> </td>
            <td  class="list_bg2"><div align="right">操作员</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtOperatorIDShow"/> </td>
        </tr>
         <tr> 
            <td  class="list_bg2"><div align="right">操作类型</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtActionShow"/> </td>
            <td  class="list_bg2"><div align="right">设备数</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtDeviceNumber"/> </td>
        </tr>
         <tr> 
            <td  class="list_bg2"><div align="right">运出地类型</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtFromtype"/> </td>
            <td  class="list_bg2"><div align="right">运入地类型</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtToType"/> </td>
        </tr>
         <tr> 
            <td  class="list_bg2"><div align="right">运出地</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtFromID"/> </td>
            <td  class="list_bg2"><div align="right">运入地</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtToID"/> </td>
        </tr>
         <tr> 
            <td  class="list_bg2"><div align="right">数据文件</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtDataFileName"/> </td>
            <td  class="list_bg2"><div align="right">记录状态</div></td>
            <td class="list_bg1"> <tbl:writeparam name="txtStatus"/> </td>
        </tr>
         <tr>   
         		<td  class="list_bg2"><div align="right">设备流转原因</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtStatusReason"/> </td>
            <td  class="list_bg2"><div align="right">描述</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtDescription"/> </td>
            
        </tr>
        </table>
        

  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr class="list_head">
      <td class="list_head" ><div align="center">流水号</div></td>
      <td class="list_head" ><div align="center">设备序列号</div></td>
      <td class="list_head" ><div align="center">运出地类型</div></td>
      <td class="list_head" ><div align="center">运出地</div></td>
      <td class="list_head" ><div align="center">运出前状态</div></td>
      <td class="list_head" ><div align="center">运入地类型</div></td>
      <td class="list_head" ><div align="center">运入地</div></td>
      <td class="list_head" ><div align="center">运入后状态</div></td>
      <td class="list_head" ><div align="center">状态</div></td>
   </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
<%
    DeviceTransitionDetailDTO dto = (DeviceTransitionDetailDTO)pageContext.getAttribute("oneline");
    Map mapDepots = Postern.getAllDepot();
    String strFromAddress = null;
        
    if (WebUtil.StringHaveContent(dto.getFromType()))
    {
   	if (dto.getFromType().equals("D"))  //仓库
	{
		strFromAddress = (String)mapDepots.get(String.valueOf(dto.getFromID()));
	}
	else if (dto.getFromType().equals("T"))  //组织机构
	{
		strFromAddress = Postern.getOrganizationDesc(dto.getFromID());
	}
	else if (dto.getFromType().equals("B"))  //用户
	{
		strFromAddress = String.valueOf(dto.getFromID());
	}
    }	
	
    if (strFromAddress==null) strFromAddress="";
    
    String strToAddress = null;
        
    if (WebUtil.StringHaveContent(dto.getToType()))
    {
   	if (dto.getToType().equals("D"))  //仓库
	{
		strToAddress = (String)mapDepots.get(String.valueOf(dto.getToID()));
	}
	else if (dto.getToType().equals("T"))  //组织机构
	{
		strToAddress = Postern.getOrganizationDesc(dto.getToID());
	}
	else if (dto.getToType().equals("B"))  //用户
	{
		strToAddress = String.valueOf(dto.getToID());
	}
    }	
	
    if (strToAddress==null) strToAddress="";
%>	
  <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      <td><div align="center"><tbl:write name="oneline" property="SeqNo"/></div></td>
      <td><div align="center"><tbl:write name="oneline" property="SerialNo"/></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:FromType" /></div></td>
      <td><div align="center"><%=strFromAddress%></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:FromDeviceStatus" /></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:ToType" /></div></td>
      <td><div align="center"><%=strToAddress%></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:ToDeviceStatus" /></div></td>
      <td><div align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status" /></div></td>
   </tr>
</lgc:bloop>    

 
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table> 

		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">     
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
       
        <tr>
        <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="devtransHis_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td> 
           
        </tr>
      </table>		   
  	</td>
	</tr>
</table>   
</form>

</td>
</tr>
</table>


