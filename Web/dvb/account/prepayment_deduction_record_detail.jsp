<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO"%>

<br>

<form name="frmPost" method="post" action="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">Ԥ��ֿۼ�¼��ϸ��Ϣ</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<br>

 <table align="center" width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg" >

  <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
  <%
       PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)pageContext.getAttribute("oneline");
       pageContext.setAttribute("DTO",dto);
    
       String strOpName="";
       strOpName=Postern.getOperatorNameByID(dto.getOpId());
       if(strOpName==null)
    	   strOpName="";
    	   
       String strCustName="";
       strCustName=Postern.getCustomerNameByID(dto.getCustId());
       if(strCustName==null)
       	   strCustName="";
              	   
       String strOrgName=Postern.getOrgNameByCustomerID(dto.getCustId());
       if(strOrgName==null)
       	   strOrgName="";
       
  %>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">��ˮ��</div></td>
    <td class="list_bg1" width="33%"><tbl:write name="DTO" property="seqNo"/></td>
    <td class="list_bg2" width="17%"><div align="right">���</div></td>
    <td class="list_bg1" width="33%"><tbl:write name="DTO" property="amount"/></td>
  </tr>
  
   <tr>
	<td class="list_bg2"><div align="right">����ʱ��</div></td>
    <td class="list_bg1"><tbl:writedate name="DTO" property="createTime" /></td>
  	<td class="list_bg2"><div align="right">Ԥ������</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="DTO:prepaymentType" /></td>
   
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="custId"/></td>
    <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1"><%=strCustName %></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ʻ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="acctId"/></td>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
   </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ֿ۷�ʽ</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="DTO:referRecordType" /></td>
    <td class="list_bg2"><div align="right">�ֿ۶���ID</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="referRecordId" /></td>
  </tr>
  
 <tr>
    <td class="list_bg2"><div align="right">������������</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTREFERTYPE" match="DTO:referSheetType" /></td>
    <td class="list_bg2"><div align="right">�������ݺ�</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="referSheetID" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">���ʱ�־</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:adjustmentFlag" /></td>
    <td class="list_bg2"><div align="right">���ʵ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="adjustmentNo" /></td>
  </tr>
  
   <tr>
    <td class="list_bg2"><div align="right">���ʱ�־</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_G_YESNOFLAG" match="DTO:invoicedFlag" /></td>
    <td class="list_bg2"><div align="right">�ʵ���</div></td>
    <td class="list_bg1"><tbl:write name="DTO" property="invoiceNo" /></td>
  </tr>
  
  <tr>
  	<td class="list_bg2"><div align="right">����Ա</div></td>
    <td class="list_bg1"><%=strOpName%></td>
    <td class="list_bg2"><div align="right">��֯����</div></td>
    <td class="list_bg1"><%=strOrgName%></td>  
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">������Դ</div></td>
    <td class="list_bg1"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
    <td class="list_bg2"><div align="right">�ͻ���������</div></td>
    <td class="list_bg1"><tbl:WriteDistrictInfo name="DTO" property="orgId" /></td>
  </tr>
  <tr>
  	<td class="list_bg2"><div align="right">��ע </div></td>
    <td class="list_bg1" colspan="3">
      <tbl:write  name="DTO" property="comments" />
    </td>
  </tr>
  
  </lgc:bloop>
 </table>
<BR>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
    <bk:canback url="bill_view.do/prepayment_deduction_record_query.do/account_prepaymentdeduction.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="bill_view.do/prepayment_deduction_record_query.do/account_prepaymentdeduction.do" />" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
    </bk:canback> 
</tr>
</table>     
 
 </form>