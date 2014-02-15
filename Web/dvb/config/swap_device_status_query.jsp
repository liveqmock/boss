<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern,
       java.util.*,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
function query_submit()
{
	 
	    
		document.frmPost.submit();
	 
}
 
	  
function view_detail_click(strId)
{
	self.location.href="swap_device_detail_config.do?txtSeqNo="+strId;
}
function create_csireason_submit()
{
	self.location.href="swap_device_status_create.screen";
}
 
 
</script>
 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">设备更换状态管理记录列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<form name="frmPost" method="post" action="csi_reason_query.do" >
    
   
      
      
    
   
 <rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head">流水号</td>
          <td class="list_head">设备更换原因列表</td>
          <td class="list_head">设备更换后的目标状态</td>
          
          <td class="list_head" >状态</td>
         
          
           
        </tr>

       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
    SwapDeviceReason2StatusDTO dto = (SwapDeviceReason2StatusDTO) pageContext.findAttribute("oneline");
    String reasonStrList=dto.getReasonStrList();
    Map strMap = Postern.getCsiReasonByDevice();
    String keyValuepair="";
    String[] reasonArry = reasonStrList.split(",");
    for(int i=0 ;i<reasonArry.length;i++){
      if (reasonArry[i]!=null && reasonArry[i]!=""){
       String value=(String)strMap.get(reasonArry[i]);
       keyValuepair=keyValuepair+reasonArry[i]+":"+value+";";
      
      
   } 
  }
    
       

%>
 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		   <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12" ><tbl:write  name="oneline" property="seqNo"/></a></td>
		   
		   <td align="center"><%=keyValuepair%></td>     
		    
		   <td align="center"><o:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:toStatus" /></td>
		   
                   <td align="center"><o:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>
      		    
    		</tr>
 
</lgc:bloop>
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
   
  
   <tr class="trline" >
         <td align="right" class="t12" colspan="9" >
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
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			 
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:create_csireason_submit()" class="btn12">新&nbsp;增</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
                         
        
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
</rs:hasresultset> 
  </td>
  </tr>
  </form>
  </table>