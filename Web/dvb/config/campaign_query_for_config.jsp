<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 

<script language=javascript>

 
function query_submit()
{

          
	  document.frmPost.submit();
	 
	 
}

function view_detail_click(strId)
{
	self.location.href="campaign_detail.do?txtCampaignID="+strId;
	 
}
 
 
 
function create_campaign_submit() {
    
   self.location.href="campaign_create_for_config.screen?txtCampaignType=A";
    
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">促销活动-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="campaign_query.do" >
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">促销活动名称</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtCampaignName" size="25" value="<tbl:writeparam name="txtCampaignName"/>" >
          
           </td>      
             <td  class="list_bg2"><div align="right">状态</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
             </td>      
           
        </tr>
       <tr>  
          <td class="list_bg2"><div align="right">起始时间</div></td>
           <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
          </td>
           <td class="list_bg2"><div align="right">截止时间</div></td>
           <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
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
                        <td background="img/button_bg.gif"><a href="javascript:create_campaign_submit()" class="btn12">新&nbsp;增</a></td>
                        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                   </tr>
	  </table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     <input type="hidden" name="txtCampaignType" size="20" value="A">
     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">促销活动ID</td>
            <td class="list_head">类型</td>
            <td class="list_head">活动名称</td>
            <td class="list_head">起始时间</td>
            <td class="list_head">截止时间</td>
            <td class="list_head">状态</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="campaignID"/>')" class="link12" ><tbl:writenumber name="oneline" property="campaignID" digit="7"/></a></td>
      	      <td align="center"><d:getcmnname typeName="SET_M_CAMPAIGNCAMPAIGNTYPE" match="oneline:campaignType"/></td>
      	      <td align="center"><tbl:write name="oneline" property="campaignName"/></td>
      	      <td align="center"><tbl:writedate name="oneline" property="dateFrom" /></td>
      	     <td align="center"><tbl:writedate name="oneline" property="dateTo" /></td> 
      	      <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
      	    
      	        			
      	  
      	   
    	 
</lgc:bloop>  

<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="8" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   
 
   
	 
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 