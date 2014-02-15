<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO,
				 com.dtv.oss.dto.AddressDTO,
				 com.dtv.oss.dto.CustomerDTO,
				 com.dtv.oss.dto.NewCustomerInfoDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.taglib.util.QueryPageProp" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>




<%
    int currentRecordCount=0;
    int pageCount = 1;
    //指定多少记录一页
    int pageBreakCount = 10;
    QueryPageProp beanProp = (QueryPageProp) pageContext.getRequest().getAttribute(WebKeys.CURRENT_RESULTSET_PROPERTY);
    int recordCount = beanProp.getRecordCount();
    //System.out.println("_____recordCount="+recordCount);
    int allPageCount = recordCount/pageBreakCount + 1;
    if(recordCount%pageBreakCount==0)
    {
    	allPageCount = recordCount/pageBreakCount;
    }
    String titleDemo = request.getParameter("titleDemo");
    if(titleDemo == null) titleDemo = "";
%>





<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >    
<%
    JobCardDTO jobCardDTO = (JobCardDTO)pageContext.getAttribute("oneline");
    
    pageContext.setAttribute("oneline", jobCardDTO);

    
					int addressID = jobCardDTO.getAddressId();
					//String type = jobCardDTO.getSubType();
					String typeDesc = Postern.getHashValueByNameKey("SET_W_JOBCARDSUBTYPE",jobCardDTO.getSubType());
					if(typeDesc == null)
					  typeDesc = Postern.getHashValueByNameKey("SET_W_JOBCARDTYPE",jobCardDTO.getJobType());
					if(typeDesc == null) typeDesc ="";
					AddressDTO addDTO = Postern.getAddressDtoByID(addressID);
					String detailAddress = WebUtil.NullToString(addDTO.getDetailAddress());
					String districtDesc = Postern.getDistrictDesc(addDTO.getDistrictID());
					String distDesc = districtDesc.substring(districtDesc.indexOf("◇")+1,districtDesc.length());
					if(detailAddress == null)detailAddress="";
					if(!"".equals(detailAddress))detailAddress = "◇"+detailAddress;
					
					AddressDTO oldAddDTO = Postern.getAddressDtoByID(jobCardDTO.getOldAddressId());
					String oldDetailAddress = WebUtil.NullToString(oldAddDTO.getDetailAddress());
					String oldDistrictDesc = Postern.getDistrictDesc(oldAddDTO.getDistrictID());
					String oldDistDesc = oldDistrictDesc.substring(oldDistrictDesc.indexOf("◇")+1,oldDistrictDesc.length());
					if(oldDetailAddress == null)oldDetailAddress="";
					if(!"".equals(oldDetailAddress))oldDetailAddress = "◇"+oldDetailAddress;
					
					
					//拼工作内容
					String workContent = jobCardDTO.getDescription();
					if(workContent==null)workContent="";
					
					
					
					
					if(workContent.indexOf("端口数")<0)//jobCardDTO.getCustId()
				  {
					  if(typeDesc.indexOf("安装")>-1)//是安装单
						{
							String portNum = Postern.getMarketInfoInfoSettingValue(jobCardDTO.getCustId());
							if(portNum!=null && !"".equals(portNum.trim()))
							{
					  	if(!"".equals(workContent))workContent=workContent+";";
					  	workContent = workContent+"安装端口数:"+portNum;
					  	}
						}
					
						if(typeDesc.indexOf("增端")>-1)//是增端单
						{
								int addPortNumber = jobCardDTO.getAddPortNumber();
								if(addPortNumber != 0)
								{
						  		if(!"".equals(workContent))workContent=workContent+";";
						  		workContent = workContent+"增加端口数:"+addPortNumber;
						  	}
						}
					}
					
					
					
					
	 

if((currentRecordCount%pageBreakCount == 0) ||(currentRecordCount%pageBreakCount!=0 && recordCount==currentRecordCount && recordCount>pageBreakCount))
{
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td class="title"><P>北京市歌华有线(_______)<%=titleDemo%>派工单</P></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" nowrap>打印日期:<%=TimestampUtility.getSystemDate(2)%></td>
    <td align="left" nowrap>总户数:<rs:prop property="recordcount" /></b></td>
    <td align="left" nowrap>签发员:</td>
    <td align="left" nowrap>施工队负责人:</td>
    
  </tr>
</table>
<table width="1020" style="BORDER-COLLAPSE: collapse" borderColor="#111111" cellSpacing="0" cellPadding="2" border="1">
<tr>
    <td align="center" width="6%" nowrap><b>序号</b></td>
    <td align="center" width="8%" nowrap><b>工单类型</b></td>
    <td align="center" width="6%" nowrap><b>姓名</b></td>
    <td align="center" width="13%" nowrap><b>客户原地址</b></td>  
    <td align="center" width="15%" nowrap><b>客户当前地址</b></td>
   
    <td align="center" width="12%" nowrap><b>工作内容</b></td>
    <td align="center" width="8%" nowrap><b>联系电话</b></td>
  
    <td align="center" width="3%" nowrap><b>复式</b></td>
    <td align="center" width="6%" nowrap><b>安装时间</b></td>
    <td align="center" width="6%" nowrap><b>安装员</b></td>
    <td align="center" width="6%" nowrap><b>用户签名</b></td>
    <td align="center" width="7%" nowrap><b>用户意见</b></td>
    <td align="center" width="12%" nowrap><b>备注</b></td>
  </tr>
 <%}%>
<tr>
    <td align="center" nowrap><tbl:writenumber name="oneline" property="JobCardId" digit="7" hideZero="true"/></td>
    <td align="center"><%=typeDesc%></td>
    <td align="center" nowrap><tbl:write name="oneline" property="ContactPerson"/></td>
    <td align="center"><%=oldDistDesc%><%=oldDetailAddress%></td>
    <td align="center"><%=distDesc%><%=detailAddress%></td>
    <td align="center"><%=workContent%></td>
    <td align="center" nowrap><tbl:write name="oneline" property="ContactPhone"/></td>
    
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td>
    <td align="center"></td> 
</tr> 
<% 
currentRecordCount++;
if((currentRecordCount%pageBreakCount == 0 ) ||(currentRecordCount%pageBreakCount!=0 && recordCount==currentRecordCount))
{
%>
</table> 
<table> 
<tr>
<td align="center">第<%=pageCount++%>页，共<%=allPageCount%>页</td>
</tr>
</table> 
<rs:isNoTheLastRecord   itemCount="<%=currentRecordCount%>">	
<p STYLE="page-break-after: always">&nbsp;</p>
</rs:isNoTheLastRecord>
<%}%>
</lgc:bloop>    



<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>