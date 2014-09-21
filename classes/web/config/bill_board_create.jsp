<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
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

function bill_board_create()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	 
    document.location.href="bill_board_query_config.do";

}

</SCRIPT>
 
<form name="frmPost" method="post" action="bill_board_op.do">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="CREATE">
	<input type="hidden" name="func_flag" size="20" value="800">
	 
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">����������Ϣ
		</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="left" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">������Ϣ����*</td>
		<td class="list_bg1" colspan="3"><input name="txtName"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtName" />"></td>
		  
	</tr>
	 <tr> 
           <td  class="list_bg2"><div align="right">������*</div></td>
           <td class="list_bg1">
           <input type="text" class="text" name="txtPublishPerson" maxlength="100" size="25" value="<tbl:writeparam name="txtPublishPerson" />" >
         </td>
          <td  class="list_bg2"><div align="right">��Ҫ��*</div></td>
           <td class="list_bg1">
             <d:selcmn name="txtGrade" mapName="SET_G_BILLBOARDGRADE" match="txtGrade" width="23" /> 
            
         </td>
     
      </tr>
       <tr> 
           <td  class="list_bg2"><div align="right">����ԭ��</div></td>
           <td class="list_bg1">
           <input type="text" class="text" name="txtPublishReason" maxlength="100" size="25" value="<tbl:writeparam name="txtPublishReason" />" >
         </td>
          <td  class="list_bg2"><div align="right">״̬*</div></td>
           <td class="list_bg1">
             <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" /> 
            
         </td>
     
      </tr>
       <tr>
            <td class="list_bg2"><div align="right">��Ч����ʼ����*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" value="<tbl:writeparam name="txtDateFrom"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
            <td class="list_bg2"><div align="right">��Ч�ڽ�������*</div></td>
            <td class="list_bg1"><input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" class="text">	      <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>
	  <tr>
         <td colspan="4" class="import_tit" align="center">��ϸ��Ϣ*</td>
     </tr>
     
      <tr>
         <td colspan="4"  align="center">
			<textarea name="txtDesc"   length="5" cols=82 rows=10  maxlength="250"><tbl:writeSpeCharParam name="txtDesc" /></textarea>
		</td>
     </tr>
	  
</table>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:bill_board_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>