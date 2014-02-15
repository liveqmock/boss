<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 

<script language=javascript>

function frm_check(){
	 
	if (!checkPlainNum(document.frmPost.txtBrcntID, true,9, "计费条件ID"))
	    return false;  
	return true;
}

function query_submit()
{
	         if(frm_check())
		document.frmPost.submit();
	 
}

function view_detail_click(strId)
{
	self.location.href="brcondition_detail.do?txtBrcntID="+strId;
}
 
 
 
function create_brcondition_submit() {
    
   document.frmPost.action="billing_condition_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">计费条件管理-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="brcondition_query.do" >
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">计费条件ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtBrcntID" maxlength="10" size="22"  value="<tbl:writeparam name="txtBrcntID" />" >
           </td>      
             <td  class="list_bg2"><div align="right">计费条件类型</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtConditionType" mapName="SET_F_BRCONDITIONCNTTYPE" match="txtConditionType" width="23" />
             </td>      
           
        </tr>
         <tr>  
          
             <td  class="list_bg2"><div align="right">计费条件名称</div></td>         
             <td class="list_bg1" align="left">
             <input type="text" name="txtConditionName" maxlength="10" size="22"  value="<tbl:writeparam name="txtConditionName" />" >
             </td>      
             <td class="list_bg2"><div align="right">计费条件状态</div></td>
               <td class="list_bg1" align="left">
                <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
        </td>
    <tr>
          
      </table>
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                        <td background="img/button_bg.gif"><a href="javascript:query_submit()" class="btn12">查&nbsp;询</a></td>
                        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		       
			 
            <td width="20" ></td>  
			 
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                        <td background="img/button_bg.gif"><a href="javascript:create_brcondition_submit()" class="btn12">新  增</a></td>
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
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">计费条件ID</td>
            <td class="list_head">计费条件名称</td>
            <td class="list_head">计费条件值</td>
            <td class="list_head">计费条件类型</td>
            <td class="list_head">状态</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="brcntID"/>')" class="link12" ><tbl:writenumber name="oneline" property="brcntID" digit="7"/></a></td>
      	     <td><tbl:write name="oneline" property="cntName"/></td>
      	      <td align="center"><tbl:write name="oneline" property="cntValueStr"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_F_BRCONDITIONCNTTYPE" match="oneline:cntType"/></td>
      	      <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
      	    
      	        			
      	  
      	   
    	 
</lgc:bloop>  

<tr>
    <td colspan="5" class="list_foot"></td>
  </tr>
 
            <tr>
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
               <a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
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
 
