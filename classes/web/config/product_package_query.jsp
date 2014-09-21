<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		document.frmPost.submit();
	}
}

function view_detail_click(strId)
{
	self.location.href="product_package_detail.do?txtPackageID="+strId;
}
function product_detail(strId)
{
	self.location.href="product_for_package.do?txtPackageID="+strId;
}
function market_detail(strId)
{
	self.location.href="market_segment_for_package.do?txtPackageID="+strId;
}
 
function checkDate(){
 
	if (document.frmPost.txtDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtDateFrom, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtDateTo.value != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	if (!checkPlainNum(document.frmPost.txtPackageID, true,9, "产品包ID"))
	    return false;  
	 
	            
	return true;
}
function create_package_submit() {
    
   document.frmPost.action="product_package_create.screen";
   document.frmPost.submit();
}

</script>
 <TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">产品包基本信息管理-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="package_query_result.do" >
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">产品包ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtPackageID" maxlength="10" size="25"  value="<tbl:writeparam name="txtPackageID" />" >
           </td>      
           <td class="list_bg2"><div align="right">产品包名称</div></td>
	      <td class="list_bg1" align="left">
              <input type="text" size="25" maxlength="25" name="txtName" value="<tbl:writeparam name="txtName"/>">
             </td>
           
        </tr>
         <tr>  
          
             <td  class="list_bg2"><div align="right">产品包类型</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtPackageClassify" mapName="SET_P_PACKAGECLASS" match="txtPackageClassify" width="23" />
             </td>      
             <td class="list_bg2"><div align="right">状态</div></td>
               <td class="list_bg1" align="left">
                <d:selcmn name="txtStatus" mapName="SET_P_PACKAGESTATUS" match="txtStatus" width="23" />
           </td>
    </tr>
    <tr>  
             <td class="list_bg2"><div align="right">有效开始时间</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </td>
             <td class="list_bg2"><div align="right">有效结束时间</div></td>
               <td class="list_bg1" align="left">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	</td>
    </tr>
        
          
      </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
      <br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr> 
             <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="查  询" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>           
             
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="新  增" onclick="javascript:create_package_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
<br>
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
     
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">产品包ID</td>
           <td class="list_head">产品包名称</td>
            <td class="list_head">产品包类型</td>
             <td class="list_head">授权级别</td>
            <td class="list_head">排列顺序</td>
            <td class="list_head">状态</td>
            <td class="list_head">开始时间</td>
           <td class="list_head">结束时间</td>
           <td class="list_head">操 作</td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="packageID"/>')" class="link12" ><tbl:writenumber name="oneline" property="packageID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="packageName"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_P_PACKAGECLASS" match="oneline:packageClass"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_S_OPERATORLEVEL" match="oneline:grade"/></td>
      	     <td align="center"><tbl:write name="oneline" property="packagePriority"/></td>
      	     <input type="hidden" name="txtPackageClass" value="<tbl:write name="oneline" property="packageClass" />" >  
      	      <td align="center"><d:getcmnname typeName="SET_P_PACKAGESTATUS" match="oneline:Status"/></td>   
      	     <td align="center"><tbl:writedate name="oneline" property="dateFrom" /></td>
      	     <td align="center"><tbl:writedate name="oneline" property="dateTo" /></td>
      	        			
      	  
      	      <td align="center"> 
      	     <a href="product_package_detail_manage.screen?&txtOptionFlag=<tbl:write name="oneline" property="hasOptionProductFlag"/>&txtPackageID=<tbl:write name="oneline" property="packageID"/>&txtPackageClass=<tbl:write name="oneline" property="packageClass"/>&txtPackageName=<tbl:write name="oneline" property="packageName"/>&txtDateFrom=<tbl:writedate name="oneline" property="dateFrom"/>
      	     &txtDateTo=<tbl:writedate name="oneline" property="dateTo"/>"/>明细管理</a>  
             </td>
              
    	     
    	 
</lgc:bloop>  

<tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="10" >
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
 