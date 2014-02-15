<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 

<script language=javascript>

 
function query_submit()
{

          
	  document.frmPost.submit();
	 
	 
}

function view_detail_click(strCode)
{
	self.location.href="ippv_detail.do?txt_ippv_code="+strCode;
	 
}

function delete_click(strCode)
{
	if(confirm('确定要删除记录吗?'))
		self.location.href="ippv_delete.do?caWalletCode="+strCode+"&func_flag=4003";
	 
}
 
 
 
function create_ippv_submit() {
    
   self.location.href="ippv_create_for_config.screen";
    
}



</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">IPPV钱包-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="ippv_query.do">

<input type="hidden" name="func_flag" value="4003" >

    <input type="hidden" name="txtFrom" value="1">
    <input type="hidden" name="txtTo" value="10">
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">钱包编码</div>
		  </td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txt_ippv_code" size="25" value="<tbl:writeparam name="txt_ippv_code"/>" >
          
           </td>      
            <td  class="list_bg2"><div align="right">钱包名称</div>
			</td>         
              <td class="list_bg1" align="left">
                   <input type="text" name="txt_ippv_name" size="25" value="<tbl:writeparam name="txt_ippv_name"/>" >
          
              </td>     
           
        </tr>
       <tr>  
	      <td  class="list_bg2"><div align="right">依赖的设备</div>
		     </td>         
            <td class="list_bg1" align="left">
                <input type="text" name="txt_ippv_dependent_eqp" size="25" value="<tbl:writeparam name="txt_ippv_dependent_eqp"/>" >
             </td> 
			 
		<td  class="list_bg2"><div align="right">是否必须</div></td>         
        <td class="list_bg1" align="left">
             <d:selcmn name="txt_require_y_n" mapName="SET_G_YESNOFLAG" match="txt_require_y_n" width="23" />
        </td>  
       </tr> 
        
         
          
   </table>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	     <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                        <td background="img/button_bg.gif"><a href="javascript:create_ippv_submit()" class="btn12">创&nbsp;建</a></td>
                        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                   </tr>
	  </table> 
	 </td>
  </tr>
  </table>

     
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">钱包编码</td>
            <td class="list_head">钱包名称</td>
            <td class="list_head">兑换率</td>
            <td class="list_head">是否必须</td>
            <td class="list_head">依赖设备列表</td>
            <td class="list_head">操作</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="caWalletCode"/>')" class="link12" ><tbl:write name="oneline" property="caWalletCode"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="caWalletName"/></td> 
			 <td align="center"><tbl:write name="oneline" property="rate"/></td> 
			 <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:required"/></td>
      	     <td align="center"><tbl:write name="oneline" property="deviceModelList"/></td> 
			 <td align="center"><a href="javascript:delete_click('<tbl:write name="oneline" property="caWalletCode"/>')" class="link12" >删除</a></td> 	 
					      	 
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
 
