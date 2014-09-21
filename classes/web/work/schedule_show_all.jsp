<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.BatchJobItemDTO" %>


<%@ page import="com.dtv.oss.dto.BatchJobDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap"%>


<script language=javascript>
function query_submit(){
	if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"�ͻ�֤��"))
		return ;
	document.frmPost.submit();
}

function view_schedule_detail(batchid){
	self.location.href="schedule_show_detail.do?txtBatchID="+batchid+"&txtActionType=detail&txtTo=10&txtFrom=1";
}

</script>

<form name="frmPost" method="post" action="schedule_show_all.do">

<input type="hidden" name="txtActionType" value="all" >
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="txtReferType" value="C">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ų������ѯ</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%

	HashMap actionTypeMap=new HashMap();
	actionTypeMap.put("PS","��ͣ");	
	actionTypeMap.put("PR","�ָ�");
	actionTypeMap.put("PC","ȡ��");
	pageContext.setAttribute("EventType",actionTypeMap);
%>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">�ͻ�֤��</div></td>
    <td class="list_bg1" width="33%"><input name="txtCustomerID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustomerID" />"></td>
    <td class="list_bg2" width="17%"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1" width="33%"><input name="txtCustomerName" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustomerName" />">
    <A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="չ���¼���ѯ����" src="img/icon_bottom.gif" border=0></A>
    </td>
  </tr>
 </table>
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none"> 
   <tr> 
    <td class="list_bg2" width="17%"><div align="right">��������</div></td>
    <td class="list_bg1" width="33%"><input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
						<input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly
							value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
						<input name="selDistButton" type="button" class="button" value="ѡ��"
							onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
    </td>
    <td class="list_bg2" width="17%"><div align="right">�ͻ���ַ</div></td>
    <td class="list_bg1" width="33%"><input name="txtAddress" type="text" class="text" maxlength="10" size="25"  value="<tbl:writeparam name="txtAddress"/>"></td>
  </tr>
  <tr> 
    <td class="list_bg2" width="17%"><div align="right">��������</div></td>
    <td class="list_bg1" width="33%"><tbl:select name="txtEventType" set="EventType" match="txtEventType" width="23" /></td>
    <td class="list_bg2" width="17%"><div align="right">����ԭ��</div></td>
    <td class="list_bg1" width="33%"><d:selcmn name="txtEventReason" mapName="SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON"  match="txtEventReason"  width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">������ʼʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateDate1)" onblur="lostFocus(this)" name="txtCreateDate1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCreateDate1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateDate1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">��������ʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateDate2)" onblur="lostFocus(this)" name="txtCreateDate2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCreateDate2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateDate2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�趨ִ�п�ʼʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtExecuteDate1)" onblur="lostFocus(this)" name="txtExecuteDate1" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtExecuteDate1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtExecuteDate1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">�趨ִ�н���ʱ��</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtExecuteDate2)" onblur="lostFocus(this)" name="txtExecuteDate2" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtExecuteDate2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtExecuteDate2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ų̱��</div></td>
    <td class="list_bg1"><input name="txtBatchID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtBatchID" />"></td>
    <td class="list_bg2"><div align="right">�ų�״̬</div></td>
    <td class="list_bg1"><d:selcmn name="txtStatus" mapName="SET_B_BATCHJOBSTATUS"  match="txtStatus"  width="23" /></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<rs:hasresultset>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">�������</td>
    <td class="list_head" width="80">�ͻ�����</td>
    <td class="list_head" width="120">��Ʒ</td>
    <td class="list_head">����ʱ��</td>
    <td class="list_head">�趨ִ��ʱ��</td>
    <td class="list_head">������״̬</td>
    <td class="list_head">�¼�����</td>
    <td class="list_head">�¼�ԭ��</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%

	Schedule2CP2CCWrap wrap = (Schedule2CP2CCWrap)pageContext.getAttribute("oneline");
    BatchJobDTO dto = wrap.getBatchJobDTO();
    pageContext.setAttribute("BatchJobDTO",dto);
    String txtReasonShow =(dto.getReasonCode()==null ||"".equals(dto.getReasonCode())) ? "" : Postern.getCsiReasonByCsiTypeAndReason(dto.getJobType(),dto.getReasonCode());
    //��ʾ�ͻ����ƺͲ�Ʒ����
    ArrayList[] colArr = Postern.getCustomerNameAndProductNameWithBatchJob(dto.getBatchId());
    ArrayList productList= colArr[0];
    ArrayList customerNameList= colArr[1];

    String customerNameStr = customerNameList.toString().replaceAll(", ", "<BR>");
    String productStr = productList.toString().replaceAll(", ", "<BR>");
    customerNameStr = customerNameStr.substring(1, customerNameStr.length()-1);
    productStr = productStr.substring(1, productStr.length()-1);
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td align="center"><a href="javascript:view_schedule_detail('<tbl:write name="BatchJobDTO" property="batchId"/>')" class="link12" ><tbl:write name="BatchJobDTO" property="batchId"/></a></td>
    <td align="center" width="80"><%=customerNameStr%></td>
    <td align="center" width="120"><%=productStr%></td>
    <td align="center"><tbl:writedate name="BatchJobDTO" property="createTime" /><br><tbl:writedate name="BatchJobDTO" property="createTime" onlyTime="true"/></td>
    <td align="center"><tbl:writedate name="BatchJobDTO" property="scheduleTime" /><br><tbl:writedate name="BatchJobDTO" property="scheduleTime" onlyTime="true"/></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobDTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="BatchJobDTO:jobType" /></td>
    <%if(!"".equals(txtReasonShow)){%>
    <td align="center"><%=txtReasonShow%></td>  
    <%}else{%>  
    	<td align="center"><tbl:write name="BatchJobDTO" property="description" /></td>    
    <%}%>
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
 </rs:hasresultset>                 
<BR>
</form>  
         

      