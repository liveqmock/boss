<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

<script language="JavaScript" >
  function back_submit(){
      document.frmPost.action ="config_serviceInfo_query.do";
      document.frmPost.submit();
  }
  function check_frm(){
     if (check_Blank(document.frmPost.txtServiceName, true, "ҵ������"))
	return false;
     if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	return false;
     if (check_Blank(document.frmPost.txtDateFrom, true, "��Ч����ʼ"))
	return false;
     if (!check_TenDate(document.frmPost.txtDateFrom, true, "��Ч����ʼ")) 
        return false;
     if (check_Blank(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹ"))
	return false;
     if (!check_TenDate(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹ")) 
        return false;
     if(replaceStr(document.frmPost.txtDateFrom.value,"0") > replaceStr(document.frmPost.txtDateTo.value,"0")){
	alert("��Ч�ڽ�ֹ����С����Ч����ʼ��ʱ�䣡");
	return false;
     }       
     return true;      
  }

  function add_submit(){
      if (check_frm()){
         document.frmPost.action ="config_serviceInfo_add.do" ;
         document.frmPost.submit();
      }
  }


</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
   <table  border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
       <td><img src="img/list_tit.gif" width="19" height="19"></td>
       <td  class="title">ҵ�����--��Ϣ����</td>
     </tr>
   </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
   </table>
   <br>
   <form name="frmPost" method="post" action="" >
     <input type="hidden" name ="ActionFlag" value ="add_service" >
     <input type="hidden" name ="func_flag" value ="20001" >
     <input type="hidden" name="confirm_post"  value="true" >
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
           <td  class="list_bg2" align="right">ҵ������*</td>
            <td class="list_bg1"> 
                <input type="text" name="txtServiceName" size="25" maxlength="25" value="<tbl:writeparam name="txtServiceName"/>" class="text" >   
            </td>             
           <td  class="list_bg2" align="right">״̬*</td>
           <td class="list_bg1"> 
               <d:selcmn name="txtStatus" mapName="SET_P_SERVICESTATUS"  match="txtStatus"  width="23" />
           </td>     
        </tr> 
        <tr>
          <td class="list_bg2" align="right"> ��Ч����ʼ* </td>
          <td class="list_bg1"><font size="2">
	     <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25" maxlength="10" value="<tbl:writeparam name="txtDateFrom" />" class="text" >	     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td> 
	  </font></td>      
          <td  class="list_bg2" align="right"> ��Ч�ڽ�ֹ*</td> 
          <td class="list_bg1"><font size="2">
	     <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" maxlength="10" value="<tbl:writeparam name="txtDateTo" />" class="text" >	     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td> 
	  </font></td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">ҵ������ </td>
          <td class="list_bg1" colspan="3"><font size="2">
           <input type="text" name="txtDescription" size="83" maxlength="100" value="<tbl:writeparam name="txtDescription"/>" class="text" >   
              
          </font></td>
        </tr>
       </table>
       <tbl:generatetoken /> 
    </form>
</td></tr>
</table>
 <BR>  
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="back_submit()" value="��  ��"></td>
         <td><img src="img/button2_l.gif" width="11" height="20"></td>
         <td width="20" ></td>  
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="add_submit()" value="ȷ  ��"></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td>          
      </tr>
   </table>	
   