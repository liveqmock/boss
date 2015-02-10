<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>

<%@ page import="com.dtv.oss.dto.AccountItemDTO"%>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO"%>
<%@ page import="java.util.*"%>
<!--20140222 fapiao-->
<SCRIPT language="JAVASCRIPT">

    function check_from(){
        var countFee=0.0;
        var loopCount=feeListTable.rows.length-1;
        document.frmPost.rowid.value="";
        for(ri=1;ri<loopCount;ri++){
            var vrid=feeListTable.rows[ri].id.split("_")[1];
            var vAmount=document.getElementsByName("txtAmountList_"+vrid)[0];
            if(ri==loopCount-1){
                if(parseFloat(vAmount.value)!=0&&!check_row(vrid)){
                    return false;
                }
            }else if(!check_row(vrid)){
                return false;
            }
            countFee+=parseFloat(vAmount.value);
        }

        if(countFee<0){
            //	alert("当前的费用总额:"+countFee+",不允许为负.");
            //	return false;
        }
        return true;
    }
    function check_row(rowNo){
//		alert(rowNo);
        var cfeeTypeName="txtFeeTypeList_"+rowNo;
        var citemName="txtItemTypeList_"+rowNo;
        var amountCon=document.getElementsByName("txtAmountList_"+rowNo)[0];

        if(check_Blank(document.getElementsByName(cfeeTypeName)[0],true,"费用类型")){
            return false;
        }
        if(check_Blank(document.getElementsByName(citemName)[0],true,"帐目类型")){
            return false;
        }
        if(check_Blank(amountCon, true, "金额")||!check_Float(amountCon,true,"金额")){
            return false;
        }

        if(parseFloat(amountCon.value)==0){
            alert("无效的金额!");
            amountCon.focus();
            return false;
        }
        document.frmPost.rowid.value+=rowNo+";";
        return true;
    }
    function addRow(){

        var lastRow=feeListTable.rows[feeListTable.rows.length-2];
        var lastNo=lastRow.id.split("_")[1];

        if(!check_row(lastNo)){
            return;
        }

        var curNo=parseInt(lastNo)+1;
        var tdList=lastRow.getElementsByTagName("td");
        var lastClassName=lastRow.className;

        var curRow=feeListTable.insertRow(feeListTable.rows.length-1);
        curRow.id="feeRow_"+curNo;
        var cName=curNo%2==0?"list_bg1":"list_bg2";
        curRow.className=cName;
        curRow.onmouseover=function(){this.className="list_over";};
        curRow.onmouseout=function(){this.className=cName;};

        var feeTypeName="txtFeeTypeList_"+curNo;
        var itemName="txtItemTypeList_"+curNo;

        var feeTypeCell=curRow.insertCell();
        feeTypeCell.align="left";
        feeTypeCell.valign="middle";
        var feeTypeSelect=cloneSelect(tdList[0].getElementsByTagName("select")[0],feeTypeName,"style='width:120px;'");
        feeTypeSelect.name=feeTypeName;
        feeTypeSelect.onchange=function(){ChangeFeeType(feeTypeSelect,itemName);};
        feeTypeCell.appendChild(feeTypeSelect);

        //帐目类型
        var itemCell=curRow.insertCell();
        itemCell.align="left";
        itemCell.valign="middle";
        var itemSel=cloneSelect(tdList[1].getElementsByTagName("select")[0],itemName,"style='width:180px;'");
        itemCell.appendChild(itemSel);

        var productCell=curRow.insertCell();
        productCell.align="left";
        productCell.valign="middle";
        var productSelect=cloneSelect(tdList[2].getElementsByTagName("select")[0],"txtListProductID_"+curNo,"style='width:120px;'");
        productCell.appendChild(productSelect);

        var amountCell=curRow.insertCell();
        amountCell.align="left";
        amountCell.valign="middle";
        amountCell.innerHTML="<input type='text' name='txtAmountList_"+curNo+"' value='0.0'>";

        var optionCell=curRow.insertCell();
        optionCell.align="center";
        optionCell.valign="middle";
        optionCell.innerHTML="<a href=\"javascript:addRow()\">增加</a>";

        var delDesc="<a href=\"javascript:delete_fee('"+lastRow.id+"')\">删除</a>";
        tdList[4].innerHTML=delDesc;

//		alert(curRow.innerHTML);
    }
    function cloneSelect(s,vname,other){
        var newCel=document.createElement("<select name="+vname+" "+other+">");
        for(io=0;io<s.options.length;io++){
            var op=s.options[io];
            newCel.appendChild(op.cloneNode(true));
        }
        return newCel;
    }
    function delete_fee(rid){
        for(i=0;i<feeListTable.rows.length;i++){
            var delRow=feeListTable.rows[i];
            if(rid==delRow.id){
                feeListTable.deleteRow(i);
            }
        }
    }
    function ChangeFeeType(feeType,itemList)
    {
        document.FrameUS.submit_update_select('feetype', feeType.value, itemList, '');
    }
    function window_submit(){
        if(check_from()){
            document.frmPost.submit();
        }
    }
    function window_colse(){
        window.close();
    }
    function window_reload(){
        opener.window.window_reload();
        //	parent.window_reload();
        //location.reload();
    }
    function window.onbeforeunload(){
        //opener.location.reload()="yes";
    }
    function selectFapiao(){
        var  strFeatures = "width=650px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
        var res=showModalDialog("<%=request.getContextPath()%>/fapiao/template.jsp","发票号码检索",strFeatures);
        var arrRes=res.split(',');
        document.getElementById("fapiao_serialno").value=arrRes[1];
        document.getElementById("fapiao_haoma").value=arrRes[2];
    }
</SCRIPT>
<div id="updselwaitlayer" style="position:absolute; left:400px; top:20px; width:150px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
            <td width="100%" align="center">
                <font size="2"> 正在获取内容。。。 </font>
            </td>
        </tr>
    </table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<table width="730px" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="top">
            <form name="frmPost" method="post" action="add_fapiao_serialno_op.do">
                <input type="hidden" name="rowid" value="">
                <table border="0" align="center" cellpadding="0" cellspacing="10">
                    <tr>
                        <td>
                            <img src="img/list_tit.gif" width="19" height="19">
                        </td>
                        <td class="title">
                           添加发票号码
                        </td>
                    </tr>
                </table>

                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">

                    <tr>
                        <td><img src="img/mao.gif" width="1" height="1"></td>
                    </tr>
                </table>
                <!--add 20140222 添加发票打印-->
                <%String fapiaostr=Postern.getFapiaoSerialNo();
        String fapiaodaima="";
        String fapiaohaoma="";
     if(fapiaostr!=null&&fapiaostr.split("-").length==2){
         fapiaodaima=fapiaostr.split("-")[0];
         fapiaohaoma=fapiaostr.split("-")[1];
     }%>
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td><label>发票代码</label></td>
                        <td><input type="text" id="fapiao_serialno" name="fapiao_serialno" value="<%=fapiaodaima%>"/></td>
                        <td><label>发票号码</label></td>
                        <td><input type="text" id="fapiao_haoma" name="fapiao_haoma"  value="<%=fapiaohaoma%>" /></td>
                        <td><input type="button" value="选取发票" onclick="selectFapiao()"/> </td>
                    </tr>
                </table>
                <!--end 20140222-->
                <br>
                <%

                    String paidList=(String)request.getParameter("paidList");
                    Map productMap=Postern.getProductIDAndNameByPackIdList(paidList);
                    String pidList=(String)request.getParameter("pList");
                    System.out.println("packageIdList>>>>>>>>:"+paidList+" productIdList>>>:"+pidList);
                    productMap.putAll(Postern.getProductIDAndNameByIdList(pidList));
                    System.out.println("productMap>>>>>>>>"+productMap);

                    pageContext.setAttribute("productMap",productMap);

                    System.out.println("RepCmd>>>>>>>>"+request
                            .getAttribute("ResponseFromEJBEvent"));
                    CommandResponse RepCmd = (CommandResponse) (request
                            .getAttribute("ResponseFromEJBEvent"));
                    if (RepCmd == null)
                        RepCmd = (CommandResponse) (request.getSession()
                                .getAttribute(CommonKeys.FEE_SESSION_NAME));

                    if (RepCmd ==null){
                        ArrayList nullFee=new ArrayList();
                        nullFee.add(new ImmediateCountFeeInfo());
                        RepCmd = new CommandResponse(nullFee);
                    }

                    request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,
                            RepCmd);
                    ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo) ((ArrayList) RepCmd
                            .getPayload()).get(0);
                    Collection acctItemList = new ArrayList(immeCountFeeInfo.getAcctItemList());
                    acctItemList.add(new AccountItemDTO());
                %>
				<span id="feeList" style="visibility: hidden">
					<table id="feeListTable" align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
                        <tr class='list_head'>
                            <td width="20%" class='list_head' align="center">
                                费用类型*
                            </td>
                            <td width="30%" class='list_head' align="center">
                                账目类型*
                            </td>
                            <td width="20%" class='list_head' align="center">
                                产品
                            </td>
                            <td width="15%" class='list_head' align="center">
                                金额(元)*
                            </td>
                            <td class='list_head' align="center">
                                操作
                            </td>
                        </tr>
                        <%
                            if (acctItemList != null) {
                                int rowNo = 0;

                                for (Iterator it = acctItemList.iterator(); it.hasNext(); rowNo++) {
                                    String feeTypeName="txtFeeTypeList_"+rowNo;
                                    String itemTypeName="txtItemTypeList_"+rowNo;
                                    String onChanageEvent="ChangeFeeType("+feeTypeName+",'"+itemTypeName+"')";
                                    String productListName="txtListProductID_"+rowNo;
                                    String groupNoName="txtAccountItemGroupNo"+rowNo;
                                    String sheafNoName="txtAccountItemSheafNo"+rowNo;
                                    String accountItemCcid="txtAccountItemCcID"+rowNo;
                                    AccountItemDTO acctItemDto = (AccountItemDTO) it.next();
                                    String feeType=acctItemDto.getFeeType();
                                    String acctItemTypeId=acctItemDto.getAcctItemTypeID();
                                    if(feeType==null||"".equals(feeType)){
                                        feeType=Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemTypeId).getFeeType();
                                    }
                                    pageContext.setAttribute("acctItem", acctItemDto);
                                    pageContext.setAttribute("acctItemTypeMap",Postern.getAcctItemMapWithFeeType(feeType));

                                    String productID = acctItemDto.getProductID()+"";
                                    if (rowNo % 2 == 0) {
                                        out
                                                .print("<tr id=\"feeRow_"
                                                        + rowNo
                                                        + "\" class=\"list_bg1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list_bg1' \" >");
                                    } else {
                                        out
                                                .print("<tr id=\"feeRow_"
                                                        + rowNo
                                                        + "\" class=\"list_bg2\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list_bg2' \" >");
                                    }
                        %>
                        <td valign="middle" align="left">
                            <d:selcmn name="<%=feeTypeName%>" mapName="SET_F_BRFEETYPE" width="20" match="<%=feeType %>" onchange="<%=onChanageEvent%>" style='width:120px;'/>
                        </td>
                        <td valign="middle" align="left">
                            <tbl:select name="<%=itemTypeName%>" set="acctItemTypeMap" width="20" match="<%=acctItemTypeId %>" style='width:180px;'/>
                        </td>
                        <td valign="middle" align="left">
                            <tbl:select name="<%=productListName%>" set="productMap" width="20" match="<%=productID %>" style='width:120px;'/>
                        </td>
                        <td valign="middle" align="center">
                            <input type='text' name="txtAmountList_<%=rowNo%>" value="<tbl:write name="acctItem" property="value"/>">
                            <input type='hidden' name="<%=groupNoName%>" value="<tbl:write name="acctItem" property="groupNo"/>">
                            <input type='hidden' name="<%=sheafNoName%>" value="<tbl:write name="acctItem" property="sheafNo"/>">
                            <input type='hidden' name="<%=accountItemCcid%>" value="<tbl:write name="acctItem" property="ccID"/>">
                        </td>
                        <td valign="middle" align="center">
                            <%
                                if(!it.hasNext()){
                            %>
                            <a href="javascript:addRow()">增加</a>
                            <%
                            }else{
                            %>
                            <a href="javascript:delete_fee('feeRow_<%=rowNo%>')">删除</a>
                            <%} %>
                        </td>
                        <%
                                    out.print("</tr>");
                                }
                            }
                        %>
                        <tr>
                            <td colspan="5" class="list_foot"></td>
                        </tr>
                    </table> </span>

                <br>
                <table align="center" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><img src="img/button2_r.gif" border="0"></td>
                        <td background="img/button_bg.gif"><a href="javascript:window_submit()" class="btn12">确&nbsp&nbsp认</a></td>
                        <td><img src="img/button2_l.gif" border="0"></td>
                        <td width="20"></td>
                        <td><img src="img/button_l.gif" border="0"></td>
                        <td background="img/button_bg.gif"><a href="javascript:window_colse()" class="btn12">取&nbsp&nbsp消</a></td>
                        <td><img src="img/button_r.gif" border="0"></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
</table>







