<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.AccountAdjustmentDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.AccountAdjust2ReferRecordWrap" %>

<br>

<form name="frmPost" method="post" action="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">调帐记录详细信息</td>
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
       AccountAdjust2ReferRecordWrap accountAdjust2dto = (AccountAdjust2ReferRecordWrap)pageContext.getAttribute("oneline");
       AccountAdjustmentDTO dto =accountAdjust2dto.getAcctAdjDTO();
       pageContext.setAttribute("AccountAdjustmentDTO",dto);
       double referAmount= accountAdjust2dto.getReferAmount();
       String strOpName="";
       strOpName=Postern.getOperatorNameByID(dto.getCreateOpID());
       if(strOpName==null)
    	   strOpName="";
    	   
       String strCustName="";
       strCustName=Postern.getCustomerNameByID(dto.getCustID());
       if(strCustName==null)
       	   strCustName="";
       	   
      CustomerDTO custDTO =Postern.getCustomerByID(dto.getCustID());
      AddressDTO addrDTO =null;
      if (custDTO !=null)  addrDTO =Postern.getAddressDtoByID(custDTO.getAddressID());	   
      if (addrDTO ==null) addrDTO =new AddressDTO();
      pageContext.setAttribute("AddrDTO",addrDTO);	   
       	          	   
      String strOrgName=Postern.getOrgNameByCustomerID(dto.getCustID());
      if(strOrgName==null)
       	   strOrgName="";
      String adjustP = dto.getReferRecordType();
      if(adjustP!=null && CommonKeys.ADJUST_REFERCODETYPE_P.equals(adjustP))
      	pageContext.setAttribute("adjustFlag","Y");
      else
      	pageContext.setAttribute("adjustFlag","N");
  %>
  <tr>
    <td valign="middle" class="list_bg2" align="right" width="17%" >流水号</td>
    <td width="33%" class="list_bg1"><font size="2">
        <tbl:write name="AccountAdjustmentDTO" property="seqNo"/>
    </font></td>
     
    <td valign="middle" class="list_bg2" align="right" width="17%" >金额</td>
    <td width="33%" class="list_bg1"><font size="2">
        <%=WebUtil.bigDecimalFormat(referAmount)%>
    </font></td>          
     
  </tr>
  
  <tr>
   <td valign="middle" class="list_bg2" align="right" width="17%" >客户证号</td>
        <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="AccountAdjustmentDTO" property="custID"/>
        </font></td>
     
     <td valign="middle" class="list_bg2" align="right" width="17%" >客户姓名</td>
        <td width="33%" class="list_bg1"><font size="2">
          <%=strCustName%>
        </font></td>          
     
  </tr>
  
  <tr>
     <td valign="middle" class="list_bg2" align="right" width="17%" >帐户号</td>
        <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="AccountAdjustmentDTO" property="acctID"/>
        </font></td>
     
     <td valign="middle" class="list_bg2" align="right" width="17%" >创建日期</td>
        <td width="33%" class="list_bg1"><font size="2">
          <tbl:writedate name="AccountAdjustmentDTO" property="createTime"/>
        </font></td>          
    
  </tr>
  
  <tr>
     <td valign="middle" class="list_bg2" align="right" width="17%" >调帐原因</td>
        <td width="33%" class="list_bg1"><font size="2">
        <d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTREASON" match="AccountAdjustmentDTO:reason" />
             
        </font></td>
     
     <td valign="middle" class="list_bg2" align="right" width="17%" >调帐类型</td>
        <td width="33%" class="list_bg1"><font size="2">
          <d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTTYPE" match="AccountAdjustmentDTO:adjustmentType" />
        </font></td>        
    
  </tr>
  
  <tr>
     <td valign="middle" class="list_bg2" align="right" width="17%" >调帐对象</td>
     <td width="33%" class="list_bg1"><font size="2">
        <d:getcmnname typeName="SET_F_ADJUSTMENTREFERRECORDTYPE" match="AccountAdjustmentDTO:ReferRecordType" />
     </font></td>       
     <td valign="middle" class="list_bg2" align="right" width="17%" >调帐对象ID</td>
        <td width="33%" class="list_bg1"><font size="2"> 
      <tbl:write name="AccountAdjustmentDTO" property="referRecordID"/>
       </font></td>      
  </tr> 
  <tr>            
     <td valign="middle" class="list_bg2" align="right" width="17%" >调帐单号</td>
        <td width="33%" class="list_bg1"><font size="2"> 
      <tbl:write name="AccountAdjustmentDTO" property="referSheetID"/>
       </font></td> 
       <td valign="middle" class="list_bg2" align="right" width="17%" >调帐日期</td>
        <td width="33%" class="list_bg1"><font size="2">
          <tbl:writedate name="AccountAdjustmentDTO" property="adjustmentDate"/>
        </font></td>     
  </tr>

  <tr>
  	 <td valign="middle" class="list_bg2" align="right" width="17%" >预存调整标志</td>
     <td width="33%" class="list_bg1"><font size="2"> 
       <d:getcmnname typeName="SET_G_YESNOFLAG" match="adjustFlag" />
     </font></td> 
     <td valign="middle" class="list_bg2" align="right" width="17%" >状态</td>
     <td width="33%" class="list_bg1"><font size="2">
       <d:getcmnname typeName="SET_F_ACCOUNTADJUSTMENTSTATUS" match="AccountAdjustmentDTO:status" /> 
     </font></td>             
  </tr>
 
  <tr>
     <td valign="middle" class="list_bg2" align="right" width="17%" >组织机构</td>
     <td width="33%" class="list_bg1"><font size="2"> 
     <tbl:WriteOrganizationInfo name="AccountAdjustmentDTO" property="createOrgID" />
     </font></td>
     <td valign="middle" class="list_bg2" align="right" width="17%" >操作员</td>
     <td width="33%" class="list_bg1"><font size="2"> 
       <%=strOpName%>
     </font></td>      
  </tr>

  <tr>       
    <td valign="middle" class="list_bg2" align="right" >客户所在区域</td>
    <td class="list_bg1" colspan="3"><font size="2">
      <tbl:WriteDistrictInfo name="AddrDTO" property="districtID" />
    </font></td>    
               
  </tr>

  <tr>
    <td valign="middle" class="list_bg2" align="right" width="17%" >备注</td>
    <td width="83%"  colspan="3" class="list_bg1"><font size="2">
      <tbl:write name="AccountAdjustmentDTO" property="comments"/>
    </font></td>
  </tr>
	 
  </lgc:bloop>
 </table>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
    <bk:canback url="adjust_account_query.do/custAcct_adjust_account_query.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="adjust_account_query.do/custAcct_adjust_account_query.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
    </bk:canback> 
  </tr>
</table>     
 
 </form>