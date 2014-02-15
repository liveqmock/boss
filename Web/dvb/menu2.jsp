
<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="2"><img src="img/mnu_s.gif" width="2" height="19"></td>
		<td width="106" align="center">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer1','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">帐务处理</td>
			</tr>
		</table>
		</td>
		<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
		<td align="center" width="106">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer2','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">批量查询</td>
			</tr>
		</table>
		</td>
		<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
		<td align="center" width="106">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer3','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','show','Layer4','','hide','Layer5','','hide','Layer6','','hide','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">批量操作</td>
			</tr>
		</table>
		</td>
		<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
		<td align="center" width="106">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer4','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','show','Layer5','','hide','Layer6','','hide','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">设备管理</td>
			</tr>
		</table>
		</td>

		<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
		<td align="center" width="106">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer5','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','show','Layer6','','hide','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">市场活动</td>
			</tr>
		</table>
		</td>
		<td width="2"><img src="img/mnu_s.gif" width="3" height="19"></td>
		<td align="center" width="106">
		<table border="0" cellspacing="4" cellpadding="0"
			onMouseOut="MM_showHideLayers('Layer6','','hide')"
			onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide','Layer6','','show','Layer7','','hide')">
			<tr>
				<td width="13" align="right"><img src="img/dot_01.gif" width="4"
					height="4"></td>
				<td width="93" align="center">运行维护</td>
			</tr>
		</table>
		</td>

		<td width="2"><img src="img/mnu_s.gif" width="2" height="19"></td>
	</tr>
</table>

<div id="Layer1" onMouseOut="MM_showHideLayers('Layer1','','hide')"
	onMouseOver="MM_showHideLayers('Layer1','','show')"
	style="position:absolute; left:132px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="ownFeeBatch_query.do?txtJobType=AL1"><span
			class="submenu"> - 一次欠费升级</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="ownTwoFeeBatch_query.do?txtJobType=AL2"><span
			class="submenu"> - 二次欠费升级</span></a></td>
	</tr>
	<!--
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_return_fee.do"  ><span class="submenu"> - 欠费升级维护</span></a></td>
        </tr>
        -->
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a
			href="custProdcutPauseBatch_query.do?txtJobType=AS"><span
			class="submenu"> - 批量欠费关停</span></a></td>
	</tr>
	<!--
          <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td  valign="middle" ><a href="menu_return_fee.do"  ><span class="submenu"> - 欠费关停维护</span></a></td>
        </tr>
        -->
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle" noWrap><a href="batchJobStatus_query.do?"><span
			class="submenu"> - 批量任务状态查询</span></a></td>
	</tr>
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:100px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>


<div id="Layer2" onMouseOut="MM_showHideLayers('Layer2','','hide')"
	onMouseOver="MM_showHideLayers('Layer2','','show')"
	style="position:absolute; left:241px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="menu_batch_query_create.do"><span
			class="submenu"> - 批量查询创建</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle" class=""><a href="menu_batch_query.do"><span
			class="submenu"> - 批量查询维护</span></a></td>
	</tr>
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:50px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer3" onMouseOut="MM_showHideLayers('Layer3','','hide')"
	onMouseOver="MM_showHideLayers('Layer3','','show')"
	style="position:absolute; left:350px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a
			href="pauseProductBatch_query.do?txtJobType=PS"><span
			class="submenu"> - 批量暂停</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="cancleBatch_query.do?txtJobType=PC"><span
			class="submenu"> - 批量取消</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="sendMessageBatch_query.do?txtJobType=SM"><span
			class="submenu"> - 批量发送消息</span></a></td>
	</tr>
	<!--
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_customer_query.do"  ><span class="submenu"> - 批量关停用户</span></a></td>
        </tr>
        <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_cancelled_customer_query.do"  ><span class="submenu"> - 批量关停产品</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="customer_additional_info_query.do" ><span class="submenu"> - 批量关停情况维护</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_pause_customer_product_batched.do" ><span class="submenu"> - 批量信息发送创建</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="customer_additional_info_query.do"  ><span class="submenu"> - 批量信息发送维护</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_pause_customer_product_batched.do"  ><span class="submenu"> -排程任务维护</span></a></td>
        </tr>
         <tr height="22" valign="buttom" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg2'">
          <td valign="middle" ><a href="menu_pause_customer_product_batched.do"  ><span class="submenu"> - 查看促销转换情况</span></a></td>
        </tr> 
      -->
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:75px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer4" onMouseOut="MM_showHideLayers('Layer4','','hide')"
	onMouseOver="MM_showHideLayers('Layer4','','show')"
	style="position:absolute; left:459px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle" noWrap><a href="devicestatus_query.do"><span
			class="submenu"> - 人工改变设备状态</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="dev_into_depot.do"><span class="submenu">
		- 新设备运入</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><span class="submenu"><a href="dev_query.do"><span
			class="submenu"> - 设备查询</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><span class="submenu"><a
			href="devtransHis_query.do"><span class="submenu"> - 设备流转历史查询</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><span class="submenu"><a
			href="match_and_preauth.do"><span class="submenu"> - 机卡配对/预售权</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><span class="submenu"><a href="device_rep.do"><span
			class="submenu"> - 设备送修</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><span class="submenu"><a href="device_return.do"><span
			class="submenu"> - 设备回库</span></a></td>
	</tr>
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:175px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer5" onMouseOut="MM_showHideLayers('Layer5','','hide')"
	onMouseOver="MM_showHideLayers('Layer5','','show')"
	style="position:absolute; left:568px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle" class=""><a href="menu_user_points_exchange.do"><span
			class="submenu"> - 积分兑换查询 </span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="menu_exchange_goods_config.do"><span
			class="submenu"> - 积分兑换管理 </span></a></td>
	</tr>
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:50px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>

<div id="Layer6" onMouseOut="MM_showHideLayers('Layer6','','hide')"
	onMouseOver="MM_showHideLayers('Layer6','','show')"
	style="position:absolute; left:677px; top:92px; width:110px; visibility:hidden">
<table width="110" border="0" cellpadding="3" cellspacing="1"
	class="menu" align="center">
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="menu_event_query.do"><span
			class="submenu"> - 系统事件查询</span></a></td>
	</tr>
	<tr height="22" valign="buttom"
		onmouseover="this.className='list_over'"
		onmouseout="this.className='list_bg2'">
		<td valign="middle"><a href="menu_systemlog.do"><span
			class="submenu"> - 系统日志查询</span></a></td>
	</tr>
</table>
<iframe src="javascript:false"
	style="position:absolute; visibility:inherit; top:0px; left:0px; width:120px; height:50px; z-index:-1; filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)';"></iframe>
</div>


