<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<SCRIPT LANGUAGE="JavaScript">
<!--

function view_open_click()
{	
	var contractNo=document.frmPost.txtContractNo.value;
	if(contractNo!=null&&contractNo!=""){
		document.frmPost.action="group_customer_open_info.do?txtContractNo="+contractNo;
		document.frmPost.submit();	
	}
} 

//-->
</SCRIPT>

<form name="frmPost" method="post">
 
 <%
pageContext.setAttribute("AllMOPList" ,Postern.getOpeningPaymentNoWrapMop());
%>

 <input type="hidden" name="Action" value="update">
  
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">合同明细</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
       
 <br>
 <%
  String status = "";
 %>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
 com.dtv.oss.dto.ContractDTO dto = (com.dtv.oss.dto.ContractDTO )pageContext.getAttribute("oneline");
 
 
 String prepayAmount =String.valueOf(dto.getPrepayAmount());
  status = dto.getStatus();
 
 if(prepayAmount.equals("0.0"))
   prepayAmount="";

StringBuffer packageListDesc=new StringBuffer();
Map packageMap=Postern.getProductPackageListWithContractNO(dto.getContractNo());
Iterator it =packageMap.keySet().iterator();
while(it.hasNext()){
	String key=(String)it.next();
	String value=(String)packageMap.get(key);
	//packageListDesc.append(key).append(",");
	 if(!"".equals(packageListDesc.toString()))
	 		packageListDesc.append(";");
	packageListDesc.append(value);
}
%>

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
        <tr>
         <td  class="list_bg2" width="17%"><div align="right">合同编号</div></td>
          <td class="list_bg1" width="33%">
          	<tbl:write name="oneline" property="contractNo"/>
          <input type="hidden" name="txtContractNo" value="<tbl:write name="oneline" property="contractNo"/>">
          		
          </td>
         
          <td class="list_bg2" width="17%"><div align="right">开户截止日期</div></td>
          <td class="list_bg1" width="33%">
           <tbl:writedate name="oneline" property="normaldate"/>
          </td>
        </tr>
        <tr>
         <td  class="list_bg2" ><div align="right">合同名称</div></td>
          <td class="list_bg1" colspan="3">
              <tbl:write name="oneline" property="name"/>
          </td>
         </tr>
         <tr>
         <td class="list_bg2"><div align="right">有效期起始时间</div></td>
          <td class="list_bg1">
           <tbl:writedate name="oneline" property="datefrom"/>
          </td>
          <td class="list_bg2"><div align="right">有效期截止时间</div></td>
          <td class="list_bg1">
          <tbl:writedate name="oneline" property="dateto"/>
          </td>
        </tr>
        <tr>
	 <td  class="list_bg2"><div align="right">一次性费总额</div></td>
           <td class="list_bg1">
               <tbl:write name="oneline" property="oneOffFeeTotal"/>
          </td>
          <td  class="list_bg2"><div align="right">月租费总额</div></td>
          <td  class="list_bg1">
               <tbl:write name="oneline" property="rentFeeTotal"/>
          </td>
        </tr>
        <tr>
	   <td  class="list_bg2"><div align="right">预付总额</div></td>
	   <td  class="list_bg1">
        <%=prepayAmount%>
     </td>
            
           <td class="list_bg2" align="right">支付方式</td>
           <td class="list_bg1">
           <d:getcmnname typeName="AllMOPList" match="oneline:prepayMopID"   />
	</td>
          
        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">纸制合同编号</div></td>
           <td class="list_bg1">
           <tbl:write name="oneline" property="sheetNo"/>
          </td>
           <td  class="list_bg2"><div align="right">个体用户数</div></td>
           <td class="list_bg1">
           <tbl:write name="oneline" property="userCount"/>
          </td>
        </tr>
     
         <tr>
          <td  class="list_bg2"><div align="right">实际用户数</div></td>
           <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="usedCount"/>
          </font></td>
           <td class="list_bg2"><div align="right">发起部门</div></td>
          <td class="list_bg1">
	    <tbl:WriteOrganizationInfo name="oneline" property="sourceOrg" />
        </td>		
         </tr>
         <tr>  
           <td class="list_bg2"><div align="right">状态</div></td>
           <td class="list_bg1" colspan="3">
            <d:getcmnname typeName="SET_C_CONTRACTSTATUS" match="oneline:status"/></td>
        </tr>
        <tr> 
          <td  class="list_bg2"><div align="right">合同描述</div></td>
          <td class="list_bg1" colspan="3">
              <tbl:write name="oneline" property="Description"/>
              </td>
        </tr>
 
        <tr> 
          <td  class="list_bg2"><div align="right">产品包选择</div></td>
          <td class="list_bg1" colspan="3">
              <%=packageListDesc.toString()%>
              </td>
        </tr>
       
    </table>

 </lgc:bloop>  

<br>   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="group_customer_open_contract_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td> 
           <%if(CommonKeys.CONTRACTSTATUS_E.equals(status)){%> 
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:view_open_click()" class="btn12">开&nbsp;户</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <%}%>
          </tr>
    </table>        
</form>