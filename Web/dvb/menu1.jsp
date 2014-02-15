<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<table border="0" cellspacing="0" cellpadding="0">
    <tr>
 	<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td width="106" align="center"><table  border="0" cellspacing="4" cellpadding="0"  onMouseOut="MM_showHideLayers('Layer1','','hide')" onMouseOver="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">业务受理</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer2','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">受理维护</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer3','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','show','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">客户管理</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer4','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','show','Layer5','','hide','Layer6','','hide','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">帐务管理</td>
          </tr>
        </table></td>
        
         <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer5','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','show','Layer6','','hide','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">报修单管理</td>
          </tr>
        </table></td>
          <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer6','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','show','Layer7','','hide','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">维修工单管理</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer7','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','show','Layer8','','hide')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">安装单管理</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
        <td align="center" width="106"><table  border="0" cellspacing="4" cellpadding="0" onMouseOut="MM_showHideLayers('Layer8','','hide')" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide','Layer8','','show')">
          <tr>
            <td width="13" align="right"><img src="img/dot_01.gif" width="4" height="4"></td>
            <td width="93" align="center">帮助</td>
          </tr>
        </table></td>
        <td width="2"><img src="img/mnu_s.gif" width="2" height="19"></td>
   </tr>
  </table>

<div id="Layer1" onMouseOut="MM_showHideLayers('Layer1','','hide')" onMouseOver="MM_showHideLayers('Layer1','','show')" style="position:absolute; left:127px; top:92px; width:110px; visibility:hidden" >
      <table width="110" border="0" cellpadding="3" cellspacing="1" class="menu" align="center" >
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKING%>" ><span class="submenu"> - 预约</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_open_for_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>"  ><span class="submenu">- 预约开户</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="catv_terminal_query_for_branch.do?OpenFlag=<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>" ><span class="submenu">- 门店开户</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_return_fee.do"  ><span class="submenu">- 安装不成功退费</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="agent_csi_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>" ><span class="submenu"> - 代理商预约</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="agent_csi_modify.do" ><span class="submenu">- 代理商修改</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="agent_csi_confirm.do" ><span class="submenu">- 代理商确认</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_customer_info_input_directly.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOK_DIRECTLY%>" ><span class="submenu">- 客户开户</span></a></td>
        </tr>
      </table>

</div>


<div id="Layer2" onMouseOut="MM_showHideLayers('Layer2','','hide')" onMouseOver="MM_showHideLayers('Layer2','','show')" style="position:absolute; left:238px; top:92px; width:110px; visibility:hidden" >
      <table width="110" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_callback.do"  ><span class="submenu">- 开户回访</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_service_interaction_query.do" ><span class="submenu">- 受理单查询</span></a></td>
        </tr>
      </table>
       <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:110px; height:80px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer3" onMouseOut="MM_showHideLayers('Layer3','','hide')" onMouseOver="MM_showHideLayers('Layer3','','show')" style="position:absolute; left:348px; top:92px; width:105px; visibility:hidden" >
      <table width="105" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_customer_query.do"  ><span class="submenu">- 客户查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="schedule_show_all_menu.do" ><span class="submenu">- 排程任务查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="customer_cancle_screen.do"  ><span class="submenu">- 已销户客户查询</span></a></td>
        </tr>
        <!--
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_phoneno_query.do"  ><span class="submenu">- 电话号码查询</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="cust_complain_query.do?actiontype=query&txtFrom=1&txtTo=10"  ><span class="submenu">- 投诉查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="cust_complain_input.do"  ><span class="submenu">- 投诉录入</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="cust_complain_process_query.do?actiontype=process&txtFrom=1&txtTo=10"  ><span class="submenu">- 投诉处理</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="cust_complain_transfer.do?actiontype=transfer"  ><span class="submenu">- 投诉流转</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" noWrap><a href="cust_complain_manualend.do?actiontype=manualend"  ><span class="submenu">- 手工结束投诉单</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="cust_complain_callback.do?actiontype=callback"  ><span class="submenu">- 投诉回访</span></a></td>
        </tr>
        -->
       </table>
      <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:110px; height:250px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>
<div id="Layer4" onMouseOut="MM_showHideLayers('Layer4','','hide')" onMouseOver="MM_showHideLayers('Layer4','','show')" style="position:absolute; left:460px; top:92px; width:105px;height:210px;visibility:hidden" >
      <table width="105" border="0" cellpadding="3" cellspacing="1" class="menu" align="center" >
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle"  ><a href="menu_bill_query.do"  ><span class="submenu"> - 支付帐单</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle"  ><a href="menu_payment_record_all_query.do"  ><span class="submenu"> - 支付记录查询</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_fee_record_all_query.do"  ><span class="submenu"> - 费用记录查询</span></a></td>
        </tr>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" noWrap><a href="menu_prepayment_deduction_record_query.do"  ><span class="submenu"> - 预存抵扣记录查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_adjust_account_query.do"  ><span class="submenu"> - 调帐记录查询</span></a></td>
        </tr>
        <!--
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_setoff_record_query.do"  ><span class="submenu"> - 销账记录查询</span></a></td>
        </tr>
        -->
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_voip_record_query.do"  ><span class="submenu"> - 语音详单查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_bill_batch_pay.do"  ><span class="submenu"> - 帐单批量支付</span></a></td>
        </tr>
      </table>
      <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:105px; height:200px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>

</div>
 
<div id="Layer5" onMouseOut="MM_showHideLayers('Layer5','','hide')" onMouseOver="MM_showHideLayers('Layer5','','show')" style="position:absolute; left:565px; top:92px; width:110px; visibility:hidden" >
      <table width="110" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">
       <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="cp_query.do"  ><span class="submenu"> -  报修单查询</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
           <td valign="middle" ><a href="assignrep_query.do" ><span class="submenu"> - 报修派工</span></a></td>
        </tr> 
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_cp_callback.do"  ><span class="submenu"> - 报修回访</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_cp_manual_close.do"><span class="submenu"> - 手工结束报修单</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_cp_terminate.do"><span class="submenu">  - 终止报修单</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle"  ><a href="menu_sheetrep_query.do"  ><span class="submenu"> - 报修手工流转</span></a></td>
        </tr>
        </table>
      <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:105px; height:200px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>

</div>
 
        
        
     <div id="Layer6" onMouseOut="MM_showHideLayers('Layer6','','hide')" onMouseOver="MM_showHideLayers('Layer6','','show')" style="position:absolute; left:680px; top:92px; width:110px; visibility:hidden" >
      <table width="110" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">   
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="rep_job_card_query.do"  ><span class="submenu"> - 维修工单查询</span></a></td>
        </tr> 
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="contactrep_query.do"  ><span class="submenu"> - 维修预约</span></a></td>
        </tr>
          <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="register_repair_info_query.do"  ><span class="submenu"> - 录入维修信息</span></a></td>
        </tr>
         </tr>
          <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="close_repair_info_query.do"  ><span class="submenu"> - 结束维修工单</span></a></td>
        </tr>
          
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="rep_job_card_print_query.do"  ><span class="submenu"> - 维修单打印</span></a></td>
        </tr>
       </table>
       <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:110px; height:120px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer7" onMouseOut="MM_showHideLayers('Layer7','','hide')" onMouseOver="MM_showHideLayers('Layer7','','show')" style="position:absolute; left:780px; top:92px; width:110px; visibility:hidden" >
      <table width="100" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">
      <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="in_job_card_query.do"  ><span class="submenu"> - 安装工单查询</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_jc_contact_of_install.do"  ><span class="submenu"> - 安装预约</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_jc_contact_of_reinstall.do"  ><span class="submenu"> - 安装重预约</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_jc_pi.do"  ><span class="submenu"> - 安装反馈信息</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_jc_ci_close.do"  ><span class="submenu"> - 结束安装工单</span></a></td>
        </tr>
         
        
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="in_job_card_query_for_print.screen"  ><span class="submenu"> - 安装单打印</span></a></td>
        </tr>
       </table>
       <iframe src="javascript:false" style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:150px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>
<div id="Layer8" onMouseOut="MM_showHideLayers('Layer8','','hide')" onMouseOver="MM_showHideLayers('Layer8','','show')" style="position:absolute; left:900px; top:92px; width:90px; visibility:hidden" >
      <table width="100" border="0" cellpadding="3" cellspacing="1" class="menu" align="center">
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle"  ><a href="license_info_query.screen"  ><span class="submenu"> - 版本信息</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="change_pwd.do"  ><span class="submenu"> - 修改密码</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="bill_board_query.do"  ><span class="submenu"> - 公告信息查询</span></a></td>
        </tr>
         
       </table>
</div>

