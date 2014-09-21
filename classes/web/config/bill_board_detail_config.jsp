<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

 
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>

function check_form ()
{
    if (check_Blank(document.frmPost.txtName, true, "������Ϣ����"))
	    return false;
	
	if (check_Blank(document.frmPost.txtPublishPerson, true, "������"))
	    return false;
	
	if (check_Blank(document.frmPost.txtGrade, true, "��Ҫ��"))
	    return false;

	if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;  
	    
	if (check_Blank(document.frmPost.txtDateFrom, true, "��Ч����ʼ����"))
	    return false;
      
	    
    if (document.frmPost.txtDateFrom.value != ''){
	if (!check_TenDate(document.frmPost.txtDateFrom, true, "��ʼ����")){
			return false;
		}
	}
	
	if (check_Blank(document.frmPost.txtDateTo, true, "��������"))
	    return false;
	if (document.frmPost.txtDateTo.value != ''){
		if (!check_TenDate(document.frmPost.txtDateTo, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	
	if (check_Blank(document.frmPost.txtDesc, true, "������ϸ��Ϣ"))
	    return false;
	    
	if(document.frmPost.txtDesc.value != ''){
	 if(document.frmPost.txtDesc.value.length>250){
	  alert("������ϸ��Ϣ���ܳ���500���ֽ�");
	   return false;
	  }
	}
	
	return true;
}


function bill_board_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}
function back_submit(){
	 
    document.location.href="bill_board_query_config.do";

}

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">������Ϣ�޸�</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="bill_board_op.do" method="post" > 
 
 
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   
   <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod"/>" >
   <input type="hidden" name="func_flag" size="20" value="800"> 
   <input type="hidden" name="txtActionType" size="20" value="UPDATE">
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��ˮ��*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtSeqNo" size="25" value="<tbl:write name="oneline" property="seqNo"/>"  class="textgray" readonly >
	  </td>
      <td class="list_bg2" width="17%"  align="right">������*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtPublishPerson" size="25" value="<tbl:write name="oneline" property="publishPerson"/>" >
     </td>
     </tr>
     <tr>
     
	<td class="list_bg2" align="right">״̬*</td>
	<td class="list_bg1" colspan="3"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/> </font></td>	
			
	   
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">������Ϣ����*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtName" maxlength="50" size="25" value="<tbl:write name="oneline" property="name"/>"  >
	  </td>
       <td class="list_bg2" width="17%"  align="right">����ԭ��</td>
      <td class="list_bg1" width="83%"  align="left">
		<input type="text" name="txtPublishReason" maxlength="100" size="25" value="<tbl:write name="oneline" property="publishReason"/>" >
	  </td>
    </tr>

     
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��Ҫ��*</td>
      <td class="list_bg1" width="33%"  align="left">
      <d:selcmn name="txtGrade"
			mapName="SET_G_BILLBOARDGRADE" match="oneline:grade" width="23"/> </font></td>	
			
 
       <td class="list_bg2" width="17%"  align="right">����ʱ��*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtPublishDate" size="25" value="<tbl:writedate name="oneline" property="publishDate"  includeTime="true"/>" class="textgray" readonly >
	  </td>
    </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">��Ч����ʼ����*</td>
      <td class="list_bg1"><font size="2"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" name="txtDateFrom"			type="text"   size="25"			value="<tbl:writedate name="oneline" property="dateFrom"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
     
	  <td class="list_bg2" align="right">��Ч�ڽ�������*</td>
		<td class="list_bg1"><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" name="txtDateTo"			type="text"   maxlength="10" size="25"			value="<tbl:writedate name="oneline" property="dateTo"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
      
    </tr>
     <tr>
         <td colspan="4" class="import_tit" align="center">��ϸ��Ϣ*</td>
     </tr>
     
      <tr>
         <td colspan="4"  align="center">
			<textarea name="txtDesc" length="5" cols=82 rows=10  maxlength="250"><tbl:write name="oneline" property="description"/></textarea>
		</td>
     </tr>
    
    
 </table>
	</lgc:bloop>   
     <tr> 
     <table width="98%" border="0" align="center" cellpadding="0"
	 cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
  </table> 
  <br>
      	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:bill_board_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
       
 
</form> 

    
   