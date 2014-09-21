<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.ProductDTO" %>

<Script language=javascript>
<!--
   function AllChoose(){
     if (document.frmPost.allchoose.checked){
        if (document.frmPost.partchoose[0]){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
              document.frmPost.partchoose[i].checked =true;
           }
       }
     }
      else {
        if (document.frmPost.partchoose[0]){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
               document.frmPost.partchoose[i].checked =false;
            }
       }
     }   
  }
  
function check_form(){
       	if (document.frmPost.txtDateFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtDateFrom, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtDateTo.value != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	if (!check_Num(document.frmPost.txtProductID, true, "��ƷID"))
	    return false;  
	
	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}
function add_submit(){
     self.location.href="product_manage_create.do";
	 
	 
}
function del_submit(){

}
function view_submit(productID){
	document.frmView.action="product_manage_view.do";
	document.frmView.txtProductID.value=productID;
	document.frmView.submit();
}
function property_submit(productID){
	document.frmView.action="product_property_query.do";
	document.frmView.txtProductID.value=productID;
	document.frmView.submit();
}
function relation_submit(productID){
	document.frmView.action="menu_product_relation_query.do";
	document.frmView.txtProductID.value=productID;
	document.frmView.submit();
}

//-->
</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��Ʒ������Ϣ����-��ѯ</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>
<form name="frmView" method="post" action="">
	<input type="hidden" name="txtProductID" value="">
</form>

<form name="frmPost" method="post" action="product_manage_query.do">
    <input type="hidden" name="partchoose" value="">
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">

    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
            <td class="list_bg2"><div align="right">��ƷID</div></td>
            <td class="list_bg1"><input type="text" name="txtProductID" size="25"  value="<tbl:writeparam name="txtProductID"/>" class="text"></td>
            <td class="list_bg2"><div align="right">��Ʒ����</div></td>
            <td class="list_bg1"><input type="text" name="txtProductName" size="25" value="<tbl:writeparam name="txtProductName"/>" class="text" ></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">��Ʒ����</div></td>
            <td class="list_bg1"><d:selcmn name="txtProductClass" mapName="SET_P_PRODUCTCLASS" match="txtProductClass" width="23" /></td>
            <td class="list_bg2"><div align="right">�Ƿ��ܴ���ҵ���ʻ�</div></td>
            <td class="list_bg1"><d:selcmn name="txtNewSAFlag" mapName="SET_G_YESNOFLAG" match="txtNewSAFlag" width="23" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">��Ч��ʼʱ��</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
            <td class="list_bg2"><div align="right">��Ч����ʱ��</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">״̬</div></td>
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
		  	<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��  ѯ" onclick="javascript:query_submit()"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
			
			<td width="20" ></td>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��  ��" onclick="javascript:add_submit()"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
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
    <!--
    <td class="list_head"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
    -->
    <td class="list_head">��ƷID</td>
    <td class="list_head">��Ʒ����</td>
    <td class="list_head">��Ʒ����</td>
    <td class="list_head">״̬</td>
    <td class="list_head">��Ч��ʼʱ��</td>
    <td class="list_head">��Ч����ʱ��</td>
    <td class="list_head" colspan="2">����</td>
  </tr>   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    ProductDTO dto = (ProductDTO)pageContext.getAttribute("oneline");
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >

    <!--
    <td align="center" class="t12"><input type="checkbox" name="partchoose" value="<tbl:write name="oneline" property="productID"/>"></td>  
    -->
    <td align="center"><a href="javascript:view_submit('<tbl:write name="oneline" property="productID"/>')"><tbl:write name="oneline" property="productID"/></a></td>
    <td align="center"><tbl:write name="oneline" property="productName"/></td>
    <td align="center"><d:getcmnname typeName="SET_P_PRODUCTCLASS" match="oneline:productClass" /></td> 
    <td align="center"><d:getcmnname typeName="SET_P_PRODUCTSTATUS" match="oneline:status" /></td> 
    <td align="center"><tbl:writedate name="oneline" property="dateFrom"/></td>
    <td align="center"><tbl:writedate name="oneline" property="dateTo"/></td>
    <td align="center"><a href="javascript:property_submit('<tbl:write name="oneline" property="productID"/>')">���Թ���</a></td>    
    <td align="center"><a href="javascript:relation_submit('<tbl:write name="oneline" property="productID"/>')">��ϵ����</a></td> 
</tbl:printcsstr>

</lgc:bloop>

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>

 <tr>
   <td align="right" class="t12" colspan="10" >
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>
  
  <BR>
 </rs:hasresultset>                 
<BR>
  
</form>