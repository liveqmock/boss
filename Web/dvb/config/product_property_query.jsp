<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.ProductDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.ProductPropertyDTO"%>

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
	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}
function add_submit(){
	document.frmOther.action="product_property_create.do";
	document.frmOther.submit();
}

function view_submit(propertyName){
	document.frmOther.action="product_property_view.do";
	document.frmOther.txtPropertyName.value=propertyName;
	document.frmOther.submit();
}

function del_submit(){
	document.frmPost.action="product_property_op.do";
	document.frmPost.txtActionType.value="DELETE";

	var propertyNameList="";
	if (document.frmPost.partchoose[0]){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
            	if(document.frmPost.partchoose[i].checked){
            		if(propertyNameList!="")
            			propertyNameList=propertyNameList +",";
            		propertyNameList=propertyNameList + document.frmPost.partchoose[i].value;
            	}
            }
        }
        
        if(propertyNameList==""){
        	alert("��û��ѡ����������ܽ��в�����");
        	return;
        }
        
        document.frmPost.txtPropertyNameList.value=propertyNameList;
	document.frmPost.submit();
}

function back_submit(productID){
	document.frmOther.action='<bk:backurl property="product_manage_query.do/product_manage_view.do" />';
	document.frmOther.txtProductID.value="";
	document.frmOther.submit();
}
//-->
</SCRIPT>

<form name="frmOther" method="post"><input type="hidden"
	name="txtProductID" size="20"
	value="<tbl:writeparam name="txtProductID"/>"> <input type="hidden"
	name="txtPropertyName" size="20" value=""></form>

<table border="0" cellspacing="0" cellpadding="0" width="820">
	<tr>
		<td>
		<table border="0" align="center" cellpadding="0" cellspacing="10">
			<tr>
				<td><img src="img/list_tit.gif" width="19" height="19"></td>
				<td class="title">��Ʒ���Թ���-��ѯ</td>
			</tr>
		</table>

		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>

		<%int productID = 0;
			if (!(request.getParameter("txtProductID") == null || ""
					.equals(request.getParameter("txtProductID"))))
				productID = Integer.parseInt(request
						.getParameter("txtProductID"));

			ProductDTO pDTO = Postern.getProductDTOByProductID(productID);

			 
			pageContext.setAttribute("pDTO", pDTO);

			%> <%//������Դ�����б�����
			java.util.Map resourceNames = Postern.getResourceNames();
			pageContext.setAttribute("resNames", resourceNames);

			%>
		<table align="center" width="98%" border="0" cellspacing="1"
			cellpadding="5" class="list_bg">
			<tr>
				<td class="import_tit" align="center" colspan="4"><font size="2">��Ʒ��Ϣ</font></td>
			</tr>

			<tr>
				<td class="list_bg2" width="25%">
				<div align="right">��ƷID</div>
				</td>
				<td class="list_bg1" width="25%"><tbl:write name="pDTO"
					property="productID" /></td>
				<td class="list_bg2" width="25%">
				<div align="right">��Ʒ����</div>
				</td>
				<td class="list_bg1" width="25%"><tbl:write name="pDTO"
					property="productName" /></td>
			</tr>
			<tr>
				<td class="list_bg2">
				<div align="right">��Ʒ����</div>
				</td>
				<td class="list_bg1"><d:getcmnname typeName="SET_P_PRODUCTCLASS"
					match="pDTO:productClass" /></td>
				<td class="list_bg2">
				<div align="right">�½�ҵ���ʻ���־</div>
				</td>
				<td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG"
					match="pDTO:newsaFlag" /></td>
			</tr>
			<tr>
				<td class="list_bg2">
				<div align="right">��Ч����ʼ</div>
				</td>
				<td class="list_bg1"><tbl:writedate name="pDTO" property="dateFrom" /></td>
				<td class="list_bg2">
				<div align="right">��Ч�ڽ�ֹ</div>
				</td>
				<td class="list_bg1"><tbl:writedate name="pDTO" property="dateTo" /></td>
			</tr>
			<tr>
				<td class="list_bg2">
				<div align="right">״̬</div>
				</td>
				<td class="list_bg1"><d:getcmnname typeName="SET_P_PRODUCTSTATUS"
					match="pDTO:status" /></td>
				<td class="list_bg2"></td>
				<td class="list_bg1"></td>
			</tr>
		</table>
		
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1">
			<tr align="center">
				<td class="list_bg1">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="img/button2_r.gif" width="22" height="20"></td>
						<td><input name="Submit" type="button" class="button"
							value="��&nbsp;��" onclick="javascript:back_submit()"></td>
						<td><img src="img/button2_l.gif" width="11" height="20"></td>

						<td width="20"></td>

						<td width="11" height="20"><img src="img/button_l.gif" width="11"
							height="20"></td>
						<td><input name="Submit" type="button" class="button"
							value="��&nbsp;��" onclick="javascript:add_submit()"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22"
							height="20"></td>

						<td width="20"></td>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit" type="button" class="button"
								value="ɾ&nbsp;��" onclick="javascript:del_submit()"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>
		
		<form name="frmPost" method="post" action="product_property_query.do">
			<input type="hidden" name="partchoose" value=""> 
			<input type="hidden" name="txtFrom" size="20" value="1">
			<input type="hidden" name="txtTo" size="20" value="10"> 
			<input type="hidden" name="txtActionType" size="20" value=""> 
			<input type="hidden" name="txtPropertyNameList" size="20" value=""> 
			<input type="hidden" name="txtProductID" size="20" value="<tbl:writeparam name="txtProductID"/>"> 
			<input type="hidden" name="func_flag" size="20" value="103">


		<rs:hasresultset>

			<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr>
				<td class="import_tit" align="center" colspan="7"><font size="2">��Ʒ������Ϣ</font></td>
			</tr>

				<tr class="list_head">
					<td class="list_head">
					<div align="center"><input type="checkbox" name="allchoose"
						value="" onclick="AllChoose()"></div>
					</td>
					<td class="list_head">��������</td>
					<td class="list_head">���Դ���</td>
					<td class="list_head">ȡֵ��ʽ</td>
					<td class="list_head">��Դ����</td>
					<td class="list_head">�̶�ֵ</td>
					<td class="list_head">ȡֵ����</td>
				</tr>

				<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
					<tbl:printcsstr style1="list_bg1" style2="list_bg2"
						overStyle="list_over">

						<td align="center" class="t12"><input type="checkbox"
							name="partchoose"
							value="<tbl:write name="oneline" property="propertyName"/>"></td>
						<td><a
							href="javascript:view_submit('<tbl:write name="oneline" property="propertyName"/>')">
						<tbl:write name="oneline" property="propertyName" /></a></td>
						<td><d:getcmnname typeName="SET_P_PRODUCTPROPERTYCODE"
							match="oneline:propertyCode" /></td>
						<td><d:getcmnname typeName="SET_P_PRODUCTPROPERTYMODE"
							match="oneline:propertyMode" /></td>
						<td><tbl:write name="oneline" property="resourceName" /></td>
						<td><tbl:write name="oneline" property="propertyValue" /></td>
						<td><d:getcmnname typeName="SET_G_PARAMETERTYPE"
							match="oneline:propertyType" /></td>
					</tbl:printcsstr>

				</lgc:bloop>

				<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>


        <tr>
              <td align="right" class="t12" colspan="8" >
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
		
		</form>