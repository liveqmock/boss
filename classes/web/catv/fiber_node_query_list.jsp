<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.FiberNodeDTO,
                 com.dtv.oss.dto.DeviceModelDTO,
                 com.dtv.oss.web.util.WebUtil" %>


<script language=javascript>

function check_form(){
	return true;
}


function query_submit(){

   document.frmPost.submit();

}

function frm_return(a,b)
{
	window.returnValue = a+";"+b;
	window.close();
}

function cancel()
{
	window.returnValue = ";";
	window.close();
}
</script>

<html>
<base target="_self"/>
<body>

<form name="frmPost"  action="fiber_node_query_list.do" target="_self" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
 <td  class="querypart" > 
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" >
    <tr>
      <td class="list_bg2" align="right">编号</td>
      <td colspan="2" class="list_bg1" align="left">
      	 <input type="text" class="text" name="txtFiberNodeCode" width="25"  value="<tbl:writeparam name="txtFiberNodeCode" />" />
      </td>
    </tr>
    <tr>  
      <td class="list_bg2" align="right">光接收机ID</td>
      <td colspan="2" class="list_bg1" align="left">
          <input type="text" class="text" name="txtFiberReceiverIdBegin" size="15" maxlength="20" value="<tbl:writeparam name="txtFiberReceiverIdBegin" />" >
           -- <input type="text" class="text" name="txtFiberReceiverIdEnd" size="15" maxlength="20" value="<tbl:writeparam name="txtFiberReceiverIdEnd" />" >      
      </td>
        
    </tr>
    
    <tr>   
      <td class="list_bg2" align="right">地址</td>
      <td colspan="1" class="list_bg1" align="left">
         <input type="text" class="text" name="txtDetailAddress" size="23" maxlength="20" value="<tbl:writeparam name="txtDetailAddress" />" >
      </td>
      	<td align="center" class="list_bg1"><a href="javascript:query_submit()"><font size="2"><strong>查找</strong></font></a></td> 
    </tr>
   </table>
  </td>
  </tr> 
</table>
   
      <input type="hidden" name="txtFrom" value="1">
      <input type="hidden" name="txtTo" value="10">
    
      <table width="100%" border="0" cellpadding="2" cellspacing="1" class="list_bg" >
         <tr class="list_head"> 
           <td width="10%"  class="list_head" align="center">ID</td>
           <td width="15%"  class="list_head" align="center">编号</td>
           <td width="33%"  class="list_head" align="center">描述</td>          
           <td width="15%"  class="list_head" align="center">光接收机ID</td>
           <td width="15%"  class="list_head" align="center">详细地址</td>
           <td width="10%"  class="list_head" align="center"></td>
         </tr> 
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
	   FiberNodeDTO dto = (FiberNodeDTO)pageContext.getAttribute("oneline");
				    
         %>
	  <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      	    <td align="center" ><tbl:write name="oneline" property="Id"/></td>
      	    <td align="center" ><tbl:write name="oneline" property="FiberNodeCode"/></td>
      	    <td align="center" ><tbl:write name="oneline" property="description"/></td>
      	    <td align="center" ><tbl:write name="oneline" property="FiberReceiverId"/></td>      
      	    <td align="center" ><tbl:write name="oneline" property="DetailAddress"/></td>
      	    <td align="center" ><a href="javascript:frm_return('<tbl:write name="oneline" property="Id"/>','<tbl:write name="oneline" property="FiberNodeCode"/>')" class="link12" >选中</a></td>
    	  </tbl:printcsstr>
       </lgc:bloop>  
	
       <rs:hasresultset>
    	   <tr class="trline" >
              <td align="right" class="t12" colspan="6" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;            
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
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
	</rs:hasresultset>
     </table>    

</form>


<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" >
	  <tr align="center">
	  <td >
	  	<table  border="0" cellspacing="0" cellpadding="0">
    	<tr align="right">
		  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="取&nbsp;消" onclick="javascript:cancel()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="22" height="20">&nbsp;&nbsp;</td>
			
		  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:window.close()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="22" height="20">&nbsp;&nbsp;</td>
		</tr>
	   </table> 
	  </td>
  	  </tr>
</table> 

<table>
<tr>
    
    <td  colspan="2" class="tb13" align="left">
    <font size="1" color="red" >注：序列号只填写第一个框则为精确查询</font>
    </td>
</tr>  
</table> 

