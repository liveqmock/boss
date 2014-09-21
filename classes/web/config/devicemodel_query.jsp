<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DeviceModelDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
	return true;
}

function query_submit()
{
    if (check_form())
    {
    	 
        document.frmPost.submit();
    }
}

function devicemodel_create()
{
	location.href = "devicemodel_create.screen";
}

function devicemodel_delete()
{
	if(check_chosen(document.frmPost,document.frmPost.ListID))
	{
		if(confirm("ȷ��Ҫɾ��ѡ��ļ�¼��?")){
			document.frmPost.action="devicemodel_delete.do";
			document.frmPost.submit();
		}
		return;
	}else{
		alert("ȱ��ѡ��,�޷���ɲ���!");
	}
}

function devicemodel_detail(strID)
{
        document.frmPost.action="devicemodel_detail_query.do";
        document.frmPost.txtOPDeviceModel.value=strID;
       document.frmPost.submit();
        
} 
</SCRIPT>

<form name="frmPost" method="post" action="devicemodel_query.do">
<input type="hidden" name="txtFrom" size="20" value="1"> 
<input type="hidden" name="txtTo" size="20" value="10">

	<input type="hidden" name="txtActionType" size="20" value="DELETE">
	<input type="hidden" name="txtOPDeviceModel" size="20" value="">
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�豸�ͺŲ�ѯ</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<%
    Map mapDeviceClasses = Postern.getAllDeviceClasses();
    pageContext.setAttribute("AllDeviceClasses",mapDeviceClasses);

    Map mapAllProvider = Postern.getAllProvider();
    pageContext.setAttribute("AllProvider",mapAllProvider);

%>
<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">�豸�ͺ�����</td>
		<td class="list_bg1" width="33%"><input name="txtDeviceModelName" type="text"
			class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtDeviceModelName" />"></td>
		<td class="list_bg2" align="right" width="17%">�豸����</td>
		<td class="list_bg1" width="33%"><tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">״̬</td>
		<td class="list_bg1"><font size="2"> <d:selcmn name="txtDeviceModelStatus"
			mapName="SET_D_DEVICEMODELSTATUS" match="txtDeviceModelStatus" width="23" /> </font></td>
		<td class="list_bg2" align="right">�豸��Ӧ��</td>
		<td class="list_bg1"><tbl:select name="txtProvider" set="AllProvider" match="txtProvider" width="23"/></td>
	</tr>
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
             
             <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:devicemodel_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr class="list_head">
			<!-- <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></td> -->
			<td class="list_head">�豸�ͺ�����</td>
			<td class="list_head">����</td>
			<td class="list_head">״̬</td>
			<td class="list_head">��Ӧ��</td>
			<td class="list_head">���кų���</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%DeviceModelDTO dto = (DeviceModelDTO) pageContext
							.getAttribute("oneline");
					String strDeviceClass=(String)mapDeviceClasses.get(dto.getDeviceClass());
					if (strDeviceClass==null)
					strDeviceClass="";
					String strProvider=(String)mapAllProvider.get(dto.getProviderID()+"");
					if(strProvider==null)
					strProvider="";
					 
					%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				 
				<td><a
					href="javascript:devicemodel_detail('<tbl:write name="oneline" property="deviceModel"/>')"
					class="link12"><tbl:write name="oneline" property="deviceModel"/></a></td>
				<td align="center"><%=strDeviceClass%></td>
				<td align="center"><d:getcmnname typeName="SET_D_DEVICEMODELSTATUS"
					match="oneline:status" /></td>
				<td align="center"><%=strProvider%></td>
				<td align="center"><tbl:write name="oneline" property="serialLength"/></td>
				 
			</tbl:printcsstr>
		</lgc:bloop>
	<tr>
    		<td colspan="5" class="list_foot"></td>
  	</tr>
				 
            		       
  
        <tr>
              <td align="right" class="t12" colspan="5" >
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
 


<!-- 	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="ɾ&nbsp;��" onclick="javascript:devicemodel_delete()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	 -->
 </td>
  	</tr>	  
</table>  
</form>
