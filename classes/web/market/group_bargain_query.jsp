<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.GroupBargainDTO,
                 com.dtv.oss.dto.CampaignDTO,
                 java.util.*,
                 java.sql.Timestamp"
%>


<script language=javascript>
 
    
   
 

function query_submit()
{
        document.frmPost.submit();
}

 
function create_submit(){
	document.frmPost.action="campaign_query.screen";
	document.frmPost.submit();
}
function view_campaign_detail(strId,str2)
{
        if(str2=='A')   
	self.location.href="campaign_detail.do?txtCampaignID="+strId;
	else 
	self.location.href="taocan_detail.do?txtCampaignID="+strId;
}

function ChangeSubCompany()
{
    document.FrameUS.submit_update_select('subagent', document.frmPost.txtOrgID.value, 'txtAgentId', '');
}
function view_detail_click(strId)
{
     
	self.location.href="group_bargain_query_detail.do?txtID="+strId;
}
</script>

 <div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>

 
 
 
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">团购合同查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
  
<form name="frmPost" action="group_bargain_query_result.do" method="post" >    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"> 
      <tr>
       <td class="list_bg2">合同号</td>
        <td class="list_bg1">
          <input type="text" name="txtBargainNo" size="25" value="<tbl:writeparam name="txtBargainNo"/>" >
      </td>
      <td class="list_bg2">状态</td>
      <td class="list_bg1">
       <d:selcmn name="txtStatus" mapName="SET_M_GROUPBARGAINSTATUS" match="txtStatus" width="23" />
      	</td>
    </tr>
    <tr>  
      <td class="list_bg2">团购券号</td>
       <td class="list_bg1">
        <input type="text" name="txtDetailNo" size="25" value="<tbl:writeparam name="txtDetailNo"/>" >
      </td>
      <% 
         Map mapSubcompany = new HashMap();
         mapSubcompany = Postern.getParentCompanys();
         pageContext.setAttribute("SubCompany",mapSubcompany);
      %>
      <td class="list_bg2">发起公司</td>
      <td class="list_bg1">
       <tbl:select name="txtOrgID" set="SubCompany" match="txtOrgID" width="23" onchange="ChangeSubCompany()"/>
       
     </td>
    </tr>  
    <tr> 
       <td class="list_bg2">代理商</td>
       <td class="list_bg1">
          <d:selsubproxy name="txtAgentId" parentMatch="txtOrgID" match="txtAgentId" width="23"/>
       </td>  
       <td class="list_bg2">是否优惠</td>
       <td class="list_bg1">
         <d:selcmn name="txtIsCampaign" mapName="SET_G_YESNOFLAG" match="txtIsCampaign" width="23"/>
       </td>  
    </tr>    
    <!--
    <tr>
       <td class="list_bg2">使用日期</td>
       <td class="list_bg1"><font size="2">
           <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtStartDate" value="<tbl:writeparam name="txtStartDate"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            --
           <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndDate)" onblur="lostFocus(this)" type="text" size="12" maxlength="10" name="txtEndDate" value="<tbl:writeparam name="txtEndDate" />"/><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
       	</font></td>
    -->
    <%
     String campaignType = "'A','B'";
     ArrayList campaignDtoList =  Postern.getCampaignDTO(campaignType);
	   Iterator ite =  campaignDtoList.iterator();
	   Map cpMap =new HashMap();
	   while (ite.hasNext()){
	       CampaignDTO cpdto = (CampaignDTO) ite.next();
	       cpMap.put(String.valueOf(cpdto.getCampaignID()),cpdto.getCampaignName());
	 }
	 pageContext.setAttribute("AllCampaignIDList" , cpMap); 
    %>
    <tr>
      <td class="list_bg2">促销活动</td>
      <td class="list_bg1" colspan="3">
          <tbl:select name="txtCampaignID" set="AllCampaignIDList" match="txtCampaignID" width="23" />
      </td>
    </tr>  
     
    </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		     <td width="20" ></td>  
		  
		  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"><input name="Submit" type="button" class="button" value="新 增" onclick="javascript:create_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			 </tr>
			 
	  </table> 
	  </td>
       </tr>
  </table>   
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">

  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
  <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td  width="8%" class="list_head" >ID</td>
          <td  width="10%" class="list_head">合同号</td>
          <td  width="20%" class="list_head">描述信息</td>    
          <td  width="10%" class="list_head">公司</td>       
          <td  width="10%" class="list_head">代理商</td>
          <td  width="5%" class="list_head">总张数</td>
          <td  width="5%" class="list_head">已使用</td>
          <td  width="5%" class="list_head">未使用</td>
          <td width="5%" class="list_head">状态</td>
          <td width="10%" class="list_head">促销活动</td>
        </tr> 
    <%
    int usedSheets = 0;

    Timestamp begin = null;
    Timestamp end = null;
      
    
    String agentName = null;%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
 if (WebUtil.StringHaveContent(request.getParameter("txtStartDate")) && WebUtil.IsDate(request.getParameter("txtStartDate")))
            begin = WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate"));
        if (WebUtil.StringHaveContent(request.getParameter("txtEndDate")) && WebUtil.IsDate(request.getParameter("txtEndDate")))
            end = WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate"));
        GroupBargainDTO dto = (GroupBargainDTO) pageContext.getAttribute("oneline");
        if (WebUtil.StringHaveContent(request.getParameter("txtStartDate")) || WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
            usedSheets = Postern.getUsedSheetsByTime(dto.getId(),begin,end);
        else
            usedSheets = dto.getUsedSheets();
       
        int haveNotUsedSheets= dto.getTotalSheets()- dto.getUsedSheets()-dto.getAbortSheets(); 
        
        String subCompanyName = Postern.getOrgNameByID(dto.getOrgId()); 
        if(subCompanyName == null ) subCompanyName="";
        agentName = Postern.getOrgNameByID(dto.getAgentId());
        if (agentName == null) agentName = "";	
        int campaignID =dto.getCampaignId();
         String camType=Postern.getCampaignByID(campaignID);
          
	   
	    
				    
%>
	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      	  <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></a></td>
      	  <td align="center"><tbl:write name="oneline" property="BargainNo"/></td>
      	  <td align="center"><tbl:write name="oneline" property="Description"/></td>
      	   <td align="center"><%=subCompanyName%></div></td>
      	   <td align="center"><%=agentName%></div></td>
      	  <td align="center"><tbl:writenumber name="oneline"  hidezero="true" property="totalSheets"/></td>
      	     <td  align="center" ><tbl:writenumber name="oneline" property="usedSheets"/></div></td>
             <td  align="center" ><%=haveNotUsedSheets%></div></td>

      	  
      	  <td align="center"><d:getcmnname typeName="SET_M_GROUPBARGAINSTATUS" match="oneline:Status"/></td>
      	    <td align="center"> 
      	   <a href="javascript:view_campaign_detail('<tbl:write name="oneline" property="campaignId"/>','<%=camType%>')" class="link12">
          <tbl:writenumber name="oneline" digit="7" property="campaignId"/></a></td>
    	</tr>
</lgc:bloop>  
 <tr>
    <td colspan="11" class="list_foot"></td>
  </tr>
</table>

<table  border="0" align="right" cellpadding="0" cellspacing="11">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
    
	  </td>
  </tr>
  </table>
  </rs:hasresultset> 
</form> 
    </td>
  </tr>
  
  
</table>   
 

     
      
      
      
      
      