<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 
 

<script language=javascript>

  

function query_submit()
{
	 
	    
		document.frmPost.submit();
	 
}
 
	
function view_detail_click(strId)
{
	self.location.href="fiber_receiver_detail_config.do?txtID="+strId;
}
function create_submit()
{
	self.location.href="fiber_receiver_create.screen";
}
 
 
</script>
  <%
 
  
    Map transCode = Postern.getFiberTransmitterCodeMap();
    pageContext.setAttribute("CODE",transCode);
    
    

%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">光接收机查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="fiberreceiver_query.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr>
          <td class="list_bg2"><div align="right">光接收机编号</div></td>
          <td class="list_bg1"> 
            <input type="text" name="txtFiberReceiverCode" maxlength="50" size="25" value="<tbl:writeparam name="txtFiberReceiverCode" />" >
           </td>      
          
           <td  class="list_bg2"><div align="right">光发机编号</div></td>
           <td class="list_bg1">
           <tbl:select name="txtFiberTransmitterId" set="CODE" match="txtFiberTransmitterId" width="23"/></td>
         </td>
        </tr>
       <tr>
        <td class="list_bg2" align="right">所属区域</td>
        <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
         </td>
          
          
           <td  class="list_bg2"><div align="right">详细地址</div></td>
           <td class="list_bg1">
           <input type="text" name="txtDetailAddress" maxlength="100" size="25" value="<tbl:writeparam name="txtDetailAddress" />" >
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
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">新&nbsp;增</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
                         
        
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
    
      
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <br>
   <rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head">记录ID</td>
          <td class="list_head">光接收机编号</td>
          <td class="list_head">光发机编号</td>
          <td class="list_head">所属区域</td>
          <td class="list_head">地址信息</td>
           
        </tr>

       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
        FiberReceiverDTO dto = (FiberReceiverDTO) pageContext.getAttribute("oneline");
	int fiberReceiverId=dto.getFiberTransmitterId();						
        String recCode="";
        if(transCode!=null)
         recCode=(String) transCode.get(String.valueOf(fiberReceiverId));
        if(recCode==null)
          recCode="";
 
  %>
 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		   <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="id"/>')" class="link12" ><tbl:write  name="oneline" property="id"/></a></td>
		   <td align="center"><tbl:write name="oneline" property="fiberReceiverCode"/></td>
		   <td align="center"><%=recCode%></td>  
		    <td align="center"><tbl:WriteDistrictInfo name="oneline" property="districtId" /></td>   
		   <td align="center"><tbl:write name="oneline" property="detailAddress"/></td>
		   
    		</tr>
 
</lgc:bloop>
  <tr>
    <td colspan="5" class="list_foot"></td>
  </tr>
   
  
   <tr class="trline" >
         <td align="right" class="t12" colspan="5" >
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