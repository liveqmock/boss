<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d"%>


<script language=javascript>

function check_form(){	
    <!-- 校验日期-->    
    if (document.frmPost.createDate.value != ''){
		if (!check_TenDate(document.frmPost.createDate, true, "创建日期")){
			return false;
		}
	}
        
   <!-- 数字格式及 不为0 -->
   
   if (isNaN(document.frmPost.id.value)){
			alert('平移小区ID应为数字');	
	 		return false;
	 }
   
   <!-- 长度校验 -->
   
    if (document.frmPost.areaName.value.length>50){
    	alert('平移小区名称长度不能超过50');
    	return false;
    }
    if (document.frmPost.areaCode.value.length>50){
    	alert('平移小区编号长度不能超过50');
    	return false;
    }

    if (document.frmPost.id.value.length>10){
    	alert('计划平移用户数长度不能超过10');
    	return false;
    }    

        
    return true;

}
 
function query_submit()
{
	if(check_form()){
       document.frmPost.action="dtv_migration_query.do";      
	     document.frmPost.submit();
	}
	 
}

function view_detail_click(id)
{
	self.location.href="dtv_migration_detail.do?id="+id;
	 
} 
 
 
function create_submit() {
    
   self.location.href="dtv_migration_create_for_config.screen";
    
}



</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">平移小区-查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>



			<form name="frmPost" method="post">

				<input type="hidden" name="func_flag" value="4081">

				<input type="hidden" name="txtFrom" value="1">
				<input type="hidden" name="txtTo" value="10">


				<table width="98%" border="0" align="center" cellpadding="5"
					cellspacing="1" class="list_bg">

					<tr>
						<td class="list_bg2">
							<div align="right">
								平移小区ID
							</div>
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="id" size="25"
								value="<tbl:writeparam name="id"/>">

						</td>
						<td class="list_bg2">
							<div align="right">
								平移小区编号
							</div>
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="areaCode" size="25"
								value="<tbl:writeparam name="areaCode"/>">

						</td>

					</tr>
					<tr>
						<td class="list_bg2">
							<div align="right">
								平移小区名称
							</div>
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="areaName" size="25"
								value="<tbl:writeparam name="areaName"/>">

						</td>
						<td class="list_bg2">
							<div align="right">
								创建日期
							</div>
						</td>
						<td class="list_bg1">
							<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.createDate)" onblur="lostFocus(this)" type="text" name="createDate" size="25"								value="<tbl:writeparam name="createDate"/>">							<IMG onclick="MyCalendar.SetDate(this,document.frmPost.createDate,'Y')"								src="img/calendar.gif" style=cursor:hand border="0" />
						</td>

					</tr>
					<tr>
						<td class="list_bg2">
							<div align="right">
								所在区域
							</div>
						</td>
				<td class="list_bg1">

					<input type="hidden" name="txtRegionalAreaId"
						value="<tbl:writeparam name="txtRegionalAreaId" />">
					<input type="text" name="txtRegionalAreaDescrpition" size="25"
						maxlength="50" readonly
						value="<tbl:WriteDistrictInfo property="txtRegionalAreaId" />">

					<input name="selDistButton" type="button" class="button" value="选择"
						onClick="javascript:sel_district('P,T,S','txtRegionalAreaId','txtRegionalAreaDescrpition')">

				</td>

				
				
				
				
						<td class="list_bg2">
							<div align="right">
								平移施工单位名称
							</div>
						</td>
						<td class="list_bg1">
							<font size="2"> <input type="text"
									name="migrationTeamName" size="25" maxlength="50"
									value="<tbl:writeparam name="migrationTeamName"/>"> </font>
						</td>

					</tr>
					<tr>
						<td class="list_bg2">
							<div align="right">
								终端编号
							</div>
						</td>
						<td class="list_bg1" align="left">
							<input type="text" name="" size="25" value="" readonly>

						</td>
						<td class="list_bg2">
							<div align="right">
								状态
							</div>
						</td>
        <td class="list_bg1" align="left">
             <d:selcmn name="status" mapName="SET_M_CUSTOMERCAMPAIGNSTATUS" match="status" width="23" />
        </td>  

						</td>

					</tr>




				</table>
				<table width="98%" border="0" align="center" cellpadding="5"
					cellspacing="1" class="list_bg">
					<tr align="center">
						<td class="list_bg1">
							<table border="0" cellspacing="0" cellpadding="0">
	  <tr align="center">
	     <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                        <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">创&nbsp;建</a></td>
                        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                   </tr>
							</table>
						</td>
					</tr>
					
				</table>
	 </td>
  </tr>
  </table>


 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>


				<rs:hasresultset>
					<table width="98%" border="0" align="center" cellpadding="5"
						cellspacing="1" class="list_bg">
						<tr class="list_head">
							<td class="list_head">
								小区编号
							</td>
							<td class="list_head">
								小区名称
							</td>
							<td class="list_head">
								所在区域
							</td>
							<td class="list_head">
								创建日期
							</td>
							<td class="list_head">
								计划平移数
							</td>
							<td class="list_head">
								施工单位
							</td>
						</tr>

						<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

							<tr class="list_bg1" onmouseover="this.className='list_over'"
								onmouseout="this.className='list_bg1'">
								<td align="center">
									<a
										href="javascript:view_detail_click('<tbl:write name="oneline" property="id"/>')"
										class="link12"><tbl:write name="oneline"
											property="areaCode" /> </a>
								</td>
								<td align="center">
									<tbl:write name="oneline" property="areaName" />
								</td>
								<td align="center">
									<tbl:WriteDistrictInfo name="oneline" property="regionalAreaId" />
								</td>
								<td align="center">
									<tbl:writedate name="oneline" property="createDate" />
								</td>
								<td align="center">
									<tbl:write name="oneline" property="planMigrationRoomNum" />
								</td>
								<td align="center">
									<tbl:write name="oneline" property="migrationTeamName" />
								</td>
						</lgc:bloop>

						<tr>
							<td colspan="8" class="list_foot"></td>
						</tr>

						<tr>
							<td align="right" class="t12" colspan="8">
								第
								<span class="en_8pt"><rs:prop property="curpageno" /> </span>页
								<span class="en_8pt">/</span>共
								<span class="en_8pt"><rs:prop property="pageamount" />页
									共有<span class="en_8pt"><rs:prop property="recordcount" />
								</span>条记录&nbsp;&nbsp;&nbsp;&nbsp; <rs:notfirst>
										<img src="img/dot_top.gif" width="17" height="11">
										<a
											href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)">首页
										</a>
										<img src="img/dot_pre.gif" width="6" height="11">
										<a
											href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)">上一页</a>
									</rs:notfirst> <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
										<a
											href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)">下一页</a>
										<img src="img/dot_end.gif" width="17" height="11">
										<a
											href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)">末页</a>
									</rs:notlast> &nbsp; 转到 <input type="text" name="txtPage" class="page_txt">页
									<a
									href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)">
										<input name="imageField" type="image" src="img/button_go.gif"
											width="28" height="15" border="0"> </a>
							</td>
						</tr>
					</table>



				</rs:hasresultset>
		</td>
	</tr>
	</form>
</table>