<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>
 
<Script language=javascript>
<!--

function ok_submit(productID,productName){
	
	var controlNo="<tbl:writeparam name="txtControlNo"/>";
	
	productIDControlName="txtReferProductID" + controlNo;
	productNameControlName="txtReferProductName" + controlNo;
	
	//给主页设置值
       window.opener.frmPost.elements[productIDControlName].value=productID;
       window.opener.frmPost.elements[productNameControlName].value=productName;
	
	self.close();
}

function query_submit(){
	document.frmPost.submit();
}
function reset_submit(){
          var controlNo="<tbl:writeparam name="txtControlNo"/>";
	  productIDControlName="txtReferProductID" + controlNo;
	  productNameControlName="txtReferProductName" + controlNo;
          window.opener.frmPost.elements[productIDControlName].value='';
          window.opener.frmPost.elements[productNameControlName].value='';
          window.close();
	}
//-->
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">请选择产品</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



<form name="frmPost" method="post" action="product_relation_select_product.do">

    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    
    <input type="hidden" name="txtControlNo" size="20" value="<tbl:writeparam name="txtControlNo"/>">

    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">产品ID</div></td>
            <td class="list_bg1"><input type="text" name="txtProductID" size="25"  value="<tbl:writeparam name="txtProductID"/>" class="text"></td>
            <td class="list_bg2"><div align="right">产品名称</div></td>
            <td class="list_bg1"><input type="text" name="txtProductName" size="25" value="<tbl:writeparam name="txtProductName"/>" class="text" ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">产品类型</div></td>
            <td class="list_bg1"><d:selcmn name="txtProductClass" mapName="SET_P_PRODUCTCLASS" match="txtProductClass" width="23" /></td>
            <td class="list_bg2"><div align="right">是否能创建业务帐户</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewSAFlag" mapName="SET_G_YESNOFLAG" match="txtNewSAFlag" width="23" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">有效开始时间</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
            <td class="list_bg2"><div align="right">有效结束时间</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_P_PRODUCTSTATUS" match="txtStatus" width="23" /></td>
            <td class="list_bg2"></td>
            <td class="list_bg1"></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>

<rs:hasresultset>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    
    <td class="list_head">产品ID</td>
    <td class="list_head">产品名称</td>
    <td class="list_head">产品类型</td>
    <td class="list_head">状态</td>
    <td class="list_head">有效开始时间</td>
    <td class="list_head">有效结束时间</td>
    <td class="list_head">操作</td>
  </tr>   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td><tbl:write name="oneline" property="productID"/></td>
    <td><tbl:write name="oneline" property="productName"/></td>
    <td><d:getcmnname typeName="SET_P_PRODUCTCLASS" match="oneline:productClass" /></td> 
    <td><d:getcmnname typeName="SET_P_PRODUCTSTATUS" match="oneline:status" /></td> 
    <td><tbl:writedate name="oneline" property="dateFrom"/></td>
    <td><tbl:writedate name="oneline" property="dateTo"/></td>
    <td><a href="javascript:ok_submit('<tbl:write name="oneline" property="productID"/>','<tbl:write name="oneline" property="productName"/>')">选择</a></td>    
</tbl:printcsstr>

</lgc:bloop>

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
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
  
  <BR>
 </rs:hasresultset>    
  
      
 <table border="0" cellspacing="0" align="center" cellpadding="0" class="list_bg"> 
  <tr>
    	
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:self.close();"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <td width="20" ></td>	
    <td><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button"
     value="取  消" onclick="javascript:reset_submit()"></td>
     <td><img src="img/button_r.gif" width="22" height="20"></td>      
  </tr>
</table>           
<BR>
  
</form>