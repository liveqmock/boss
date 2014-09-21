<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<Script language=javascript>
var listIDValue ="";
function checkSelected(){
       var m=0;
       if (document.frmPost.ListID !=null){
        
          for( i=0; i<document.frmPost.ListID.length; i++){
          
               if (document.frmPost.ListID[i].checked){
                 m=m+1;
                 listIDValue =document.frmPost.ListID[i].value;
              }
          }
      }
    
      if (m==0){
         alert("没有选择积分活动");
	 return false;
      }
      
      if (m>1){
         alert("只能选择一个积分活动!");
	 return false;
      }
      return true;
}
function view_detail_click(strId){

	document.frmPost.action="points_activity_detail.do?txtActivityID="+strId;
	document.frmPost.submit();
}
 
function create_submit()
{
    
    if(checkSelected()){
      document.frmPost.ListIDValue.value =listIDValue ;
      document.frmPost.action="query_goods_by_activity.do";
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
    <td class="title">选择积分兑换活动</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    
     <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
     <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">客户基本信息</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><input type="text" name="txtOldName" size="25"  value="<tbl:writeparam name="txtName"/>" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">地址</div></td>
            <td class="list_bg1"><input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress"/>" class="textgray" readonly ></td>
        </tr>
         <tr>
            <td class="list_bg2"><div align="right">客户类型</div></td>
            <td class="list_bg1" colspan="3"><input type="text" name="txtCustomerType1" size="25"  value="<tbl:writeparam name="txtCustomerTypeShow"/>" class="textgray" readonly ></td>
             
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">证件类型</div></td>
            <td class="list_bg1"><input type="text" name="txtCardType" size="25"  value="<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" />" class="textgray" readonly ></td>
            <td class="list_bg2"><div align="right">证件号码</div></td>
           
            <td class="list_bg1"><input type="text" name="txtCardID" size="25"    value="<tbl:writeparam name="txtCardID"/>" class="textgray" readonly ></td>
        </tr>
       <tr>
          <td class="list_bg2"><div align="right">累计历史总积分</div></td>
          <td class="list_bg1"><input type="text" name="txtTotalPoints" size="25"  value="<tbl:writeparam name="txtTotalPoints"/>" class="textgray" readonly ></td>
          <td class="list_bg2"><div align="right">当前可用积分</div></td>
          <td class="list_bg1"><input type="text" name="txtCurrentPoints" size="25"  value="<tbl:writeparam name="txtCurrentPoints"/>" class="textgray" readonly ></td>
      </tr>
      <tr>
       <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
      <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">当前可用积分兑换活动信息</font></td>
        </tr>   
      
    </table>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
  <br>
 <tbl:hiddenparameters pass="txtCurrentPoints/txtCustomerType/ListID/txtCustomerID" />
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		      <td class="list_head" > <input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div></td>
			<td width="10%" class="list_head">活动ID</td>
			<td width="30%" class="list_head">活动名称</td>
			<td width="48%" class="list_head">活动描述</td>
			<td width="10%" class="list_head">状态</td>      
			 
			 
		</tbl:printcsstr>
		<input type="hidden" name="ListID" value ="" >
		<input type="hidden" name="ListIDValue" value="">
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		
		        <td align="center"><input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="Id"/>"></td>
		        <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7" /></td>
      			<td align="center"><tbl:write name="oneline" property="Name"/></td> 
      			<td align="center"><tbl:write name="oneline" property="Descr"/></td> 
      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      			
      			 
      			 
      		</tr>
	</lgc:bloop>  
	 <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>
 
 
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
		          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
                           <td background="img/button_bg.gif">
	                   <a href="<bk:backurl property="customer_view.do" />" class="btn12">返&nbsp;回</a></td>
                            <td><img src="img/button2_l.gif" width="13" height="20"></td>
			        
			         <td width="20" ></td>
			        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			        <td><input name="Submit" type="button" class="button" value="开始积分兑换" onclick="javascript:create_submit()"></td>
			        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>