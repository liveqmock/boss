<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>

<BR>
<%
    String sFlag=request.getParameter("func_flag");
    int iFlag=0;
    String sName="";
    //Integer iID=null;
    String sHint=null;
       
    if ((sFlag!=null)&&(sFlag.compareTo("")!=0))
    {
        try
        {
            iFlag=Integer.valueOf(sFlag).intValue();
        }
        catch (Exception ex)
        {
        }
    }
       
    CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
       if (CmdRep!=null)
       {
           try
           {
               switch (iFlag)
       		{
         	   case 110: //cancel booking
         	   case 111: //cancel booking for install
         	     sHint=(String)CmdRep.getPayload();
         	     break;
         	   default:    
                     sHint=(String)CmdRep.getPayload();
                }     
           }
           catch (Exception ex)
           {}
       } 
       
%>
      <table width="100%" border="0" cellspacing="5" cellpadding="5">
        <tr> 
          <td width="80%"><div align="center"> 
              <p align="center" class="title1"><strong>提示信息</strong></p>
            </div></td>
          <td width="20%">&nbsp;</td>
        </tr>
      </table>
      <BR>
      <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr> 
          <td width="80%"><div align="center">
          <p align="center" class="t16">
<lgc:haserror>
        <font color="red"><i>操作不成功，错误信息如下:</i></font>
</lgc:haserror>   

<lgc:hasnoterror>
        操作成功
</lgc:hasnoterror>            
          </p>
          </div></td>
          <td width="20%">&nbsp;</td>
        </tr>
        <tr> 
          <td width="80%" align="center" ><font color="red"><i><tbl:errmsg /></i></font></td>
          <td width="20%">&nbsp;</td>                
        </tr>
      </table>
      <BR>
      <table width="100%" border="0" cellspacing="2" cellpadding="3">
        <tr> 
          <td width="80%" align="center">
       <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
<%
   //-------有错没错都要显示的按钮-------
%>
<%
       //通常是返回
       String strBackUrl = "";
       String strBtnName = "返回查询结果";
       switch (iFlag)
       {
         case 110: //cancel booking
           strBackUrl = "service_interaction_query_result.do";
           break;
         case 111: //cancel booking for install
           strBackUrl = "job_card_query_result_for_contact_of_install.do";
           break;
       }
       
       if (!strBackUrl.equals(""))
       {
%>
<bk:canback url="<%=strBackUrl%>" >
	   <td width="20" ></td>      
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"  ><a href="<bk:backurl property="<%=strBackUrl%>" />" class="btn12"><%=strBtnName%></a></td>           
           <td><img src="img/button_r.gif" border="0" ></td>
</bk:canback>
<%
        }
%>
       </tr>
      </table>
          </td>
          <td width="20%">&nbsp;</td>

        </tr>
      </table>
  
  	
      