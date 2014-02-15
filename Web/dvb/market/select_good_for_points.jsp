<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
 
 
<Script language=javascript>
  var listIDValue =0;
  function checkSelected(){
       var m=0;
       
       if (document.frmPost.ListIDGood.length !=null){
          
          for( i=0; i<document.frmPost.ListIDGood.length; i++){
               if (document.frmPost.ListIDGood[i].checked){
                 m=m+1;
                 listIDValue =document.frmPost.ListIDGood[i].value;
              }
          }
           
      } 
      
     
      if (m==0){
         alert("没有选择物品");
	 return false;
      }
      
      if (m>1){
         alert("只能选择一个物品!");
	 return false;
      }
      return true;
} 
 
function create_submit()
{
    
    if(checkSelected()){
       document.frmPost.ListIDGoodValue.value =listIDValue ;
      document.frmPost.action="create_points_activity.do";
      document.frmPost.submit();
    }
}
    

</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">积分兑换活动详细信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="txtFrom" size="22" value="1">
    <input type="hidden" name="txtTo" size="22" value="10">
    <input type="hidden" name="func_flag" value="1005">     
  <rs:hasresultset>
   <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
   
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      
                <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListIDGood', this.checked)"></div></td>
                <td class="list_head">物品ID</td>
		<td class="list_head">物品名称 </td>
		<td class="list_head">物品描述 </td>
		<td class="list_head">单次兑换数量 </td> 
		<td class="list_head">状态</td>
		 
	</tbl:printcsstr>
	      <input type="hidden" name="ListIDGood" value ="" >     
	      <input type="hidden" name="ListIDGoodValue" value ="" >     
              <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">  
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		        <td align="center"><input type="checkbox" name="ListIDGood" value="<tbl:write name="oneline" property="Id"/>"></td>
		        <td align="center"><tbl:write name="oneline" property="id"/></td> 
		        <td align="center"><tbl:write name="oneline" property="name"/></td> 
      			<td align="center"><tbl:write name="oneline" property="descr"/></td> 
      			<td align="center"><tbl:write name="oneline" property="amount"/></td> 
      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td> 
      			<input type="hidden" name="txtActivityId" size="22" value="<tbl:write name="oneline" property="activityId"/>"> 
      		</tr>
      		</lgc:bloop> 
 
	 <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>
 <tbl:hiddenparameters pass="txtCurrentPoints/txtCustomerType/txtCustomerID" />
 <tbl:generatetoken />
 <table  border="0" align="center" cellpadding="0" cellspacing="8">
            <tr>
              <td align="right" class="t12" colspan="7">
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
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
		        
		          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
                          <td background="img/button_bg.gif">
	                  <a href="<bk:backurl property="select_activity_for_points.do" />" class="btn12">返&nbsp;回</a></td>
                          <td><img src="img/button2_l.gif" width="13" height="20"></td>
                          <td width="20" ></td>
                          <td><img src="img/button_l.gif" border="0" ></td>
                           <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">确 定</a></td>
                            <td><img src="img/button_r.gif" border="0" ></td>
		                 
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>