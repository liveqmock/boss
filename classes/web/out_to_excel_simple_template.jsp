<!--查询并且打印的公共文件-->
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="java.util.*,java.math.BigDecimal,java.text.DecimalFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.util.*" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.AddressDTO" %>
                 
<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys,
                 com.dtv.oss.web.util.WebOperationUtil" %>

<%
    Throwable ex = (Throwable)request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE);
    if (ex!=null){
%>
<BR>
	<table border=0 cellpadding=2 width="80%">
  	<caption><font color="red"><i>发生错误，错误信息如下:</i></font></caption>
  	        <tr><td align="center" colspan=2><font color="red"><%=ex.getMessage()%></font></td></tr>
  	</table>
  	<input type="hidden" name="error_excel_flag" value ="true" > 
<%  
     }else {
%>
                         
<%
   System.out.println("begin-----------");
   String pageCode = request.getParameter("pageCode");
   String strTitle ="";
   String tableTitle ="";
   StringBuffer printInfo = new StringBuffer(1024);
   String colTypes ="";
   
   //首先查询结果
   List dataList = new ArrayList();
   QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
	 System.out.println("RepCmd================"+RepCmd);
	 Collection responseResultCols = (Collection) RepCmd.getPayload();
	 System.out.println("responseResultCols.size()========"+responseResultCols.size());
	 Iterator responseResultIter =responseResultCols.iterator();
    
   printInfo.append("打印日期:"+TimestampUtility.getDate(new Date(),"yyyy-MM-dd"));
  
   if (pageCode.equals("real_income_excel")){
      strTitle ="实收记录查询";
      tableTitle ="1;序号;0;0;0;0;客户姓名;0;1;0;1;客户证号;0;2;0;2;所属区域;0;3;0;3;详细地址;0;4;0;4;帐号;0;5;0;5;创建时间;0;6;0;6;费用类型;0;7;0;7;"
                 +"帐目类型;0;8;0;8;金额;0;9;0;9;";
      colTypes = "s;s;s;s;s;s;s;s;s;s"; 
	    dataList.add(tableTitle); 
	    int dataCount =0;
	    double sumAmount =0;
	    while (responseResultIter.hasNext()){
	      List subdata =(List)responseResultIter.next();
	      double sub_data =((BigDecimal)subdata.get(9)).doubleValue();
	      sumAmount =sumAmount + sub_data;
	      dataList.add(subdata);
	      dataCount =dataCount+1;
      }
      List sumMoneydata = new ArrayList();
      sumMoneydata.add(" 总计 ");
      sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");
      sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");
      DecimalFormat formartdata= new DecimalFormat("0.00");
      sumMoneydata.add(formartdata.format(sumAmount));
      dataList.add(sumMoneydata);
      printInfo.append(" 总数：").append(dataCount+"");  
      int collectorID =WebUtil.StringToInt(request.getParameter("txtCollectorID"));
      String collectorName =Postern.getOperatorNameByID(collectorID);
      if (collectorName !=null && !collectorName.equals("")){
         printInfo.append("  操作员：").append(collectorName);  
      }
   }
   
   if (pageCode.equals("out_to_owe_suspend_excel")){
       //下面为导出设置变量
      strTitle = "停机缴费情况客户查询";
      tableTitle = "1;客户证号;0;0;0;0;姓名;0;1;0;1;业务帐户号;0;2;0;2;客户类型;0;3;0;3;详细地址;0;4;0;4;停机原因;0;5;0;5;联系电话;0;6;0;6;"
                  + "帐户总金额;0;7;0;7;客户备注;0;8;0;8;";                 
	    printInfo.append("     区域:").append(request.getParameter("txtCountyDesc"));
	    //定义数据在excel中的类型：数字－n;字符－s
	    colTypes = "s;s;s;s;s;s;s;s;s"; 
	    dataList.add(tableTitle); 
	    while (responseResultIter.hasNext()){
	       List subdata =(List)responseResultIter.next();
	       dataList.add(subdata);
	    }
   }
   
   //分公司整转免税数据统计表
   if (pageCode.equals("freetaxi_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="分公司整转免税数据统计表";
       tableTitle = "1;客户证号;0;0;0;0;客户姓名;0;1;0;1;缴费日期;0;2;0;2;缴费金额;0;3;0;3;支付方式;0;4;0;4;收款人;0;5;0;5;支票号码;0;6;0;6"; 
       colTypes = "s;s;s;s;s;s;s;"; 
       printInfo.append("  区域：" +txtDistName);
       String txtStatBeginTime =(request.getParameter("txtStatBeginTime")==null) ? "" :request.getParameter("txtStatBeginTime");
       String txtStatEndTime =(request.getParameter("txtStatEndTime")==null) ? "" :request.getParameter("txtStatEndTime");
       if (!txtStatBeginTime.equals("") || !txtStatEndTime.equals("") ){
          printInfo.append("  统计时间段:").append(txtStatBeginTime +"~"+txtStatEndTime);
       }
       String opid =(request.getParameter("txtCollectorID")==null) ? "" :request.getParameter("txtCollectorID");
       String opName ="";
       if (opid.equals("")) {
          opName = "全部" ;
       } else{
       	  opName = (String)Postern.getAllOperator().get(opid);
       }
       printInfo.append("  收费人：" +opName);
       dataList.add(tableTitle); 
       List custList =new ArrayList();
       int freetotalMoney =0;
       while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        String subCustId =subdata.get(1).toString();
	        if (!custList.contains(subCustId)){
	             custList.add(subCustId);
	        }
	        int subMoney =Integer.parseInt(subdata.get(4).toString());
	        freetotalMoney =freetotalMoney +subMoney;
	        dataList.add(subdata);
	     }   
	     List totalList =new ArrayList();
	     totalList.add("合计缴费客户数");
	     totalList.add(String.valueOf(custList.size()));
	     totalList.add("合计缴费金额");
	     totalList.add(String.valueOf(freetotalMoney));
	     totalList.add("");
	     totalList.add("");
	     totalList.add("");
	     dataList.add(totalList); 
    }
    
    if (pageCode.equals("csistat_for_huairou_query_excel")){
      strTitle = "怀柔业务受理统计";
      tableTitle = "3;区域;0;0;2;0;新开用户;0;1;0;8;老用户缴费;0;9;0;24;本月主动暂停业务帐户数;0;25;2;25;客户数;0;26;2;26"
                 +  ";正常业务帐户数;0;27;2;27;主动暂停业务帐户数;0;28;2;28;强制暂停业务帐户数;0;29;2;29"                
                 +  ";初装费>0;1;1;1;3;初装费=0,收视费≠0;1;4;1;6;初装费=0，收视费=0;1;7;1;8"
                 +  ";收视费;1;9;1;10;信号断通费;1;11;1;12;端口费;1;13;1;14;终端费;1;15;1;16;工本费;1;17;1;18;迁移费;1;19;1;20;其它费;1;21;1;22;小计;1;23;1;24"
                 +  ";客户数;2;1;2;1;业务帐户数;2;2;2;2;缴费金额;2;3;2;3;客户数;2;4;2;4;业务帐户数;2;5;2;5;缴费金额;2;6;2;6;客户数;2;7;2;7;业务帐户数;2;8;2;8"
                 +  ";金额;2;9;2;9;次数;2;10;2;10;金额;2;11;2;11;次数;2;12;2;12;金额;2;13;2;13;次数;2;14;2;14;金额;2;15;2;15;次数;2;16;2;16"
                 +  ";金额;2;17;2;17;次数;2;18;2;18;金额;2;19;2;19;次数;2;20;2;20;金额;2;21;2;21;次数;2;22;2;22;金额;2;23;2;23;次数;2;24;2;24";                   
      colTypes = "s;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n"; 
  
   
      String txtStatBeginTime =(request.getParameter("txtStatBeginTime")==null) ? "" :request.getParameter("txtStatBeginTime");
      String txtStatEndTime =(request.getParameter("txtStatEndTime")==null) ? "" :request.getParameter("txtStatEndTime");
      if (!txtStatBeginTime.equals("") || !txtStatEndTime.equals("") ){
          printInfo.append("  统计时间段:").append(txtStatBeginTime +"~"+txtStatEndTime);
      }
      dataList.add(tableTitle); 
      while (responseResultIter.hasNext()){
	       List subdata =(List)responseResultIter.next();
	       dataList.add(subdata);
	    }   
   }
   
   //歌华有限用户情况统计表(延庆,怀柔)
   if (pageCode.equals("customer_info_stat_query_excel")){
       int districtID =WebUtil.StringToInt(request.getParameter("txtDistrictID"));
       Map reportMp = new LinkedHashMap();
       Map distTreeMap =Postern.getDistrictSettingForCreateTree();
       int hrdistinctId =(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC")==null) ? -2 :Integer.parseInt(Postern.getSystemSettingValue("SET_HRDISTICNTID_FOR_STATIC"));
		   int yqdistinctId =(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC")==null) ? -3 :Integer.parseInt(Postern.getSystemSettingValue("SET_YQDISTINCTID_FOR_STATIC"));

       ArrayList yqCol =(ArrayList)distTreeMap.get(new Integer(yqdistinctId));
       ArrayList hrCol =(ArrayList)distTreeMap.get(new Integer(hrdistinctId));
   
       if ((yqCol !=null &&yqCol.contains(new Integer(districtID))) || districtID ==yqdistinctId){
          reportMp= WebQueryUtility.getReportCustomerInfo(yqdistinctId);  
          strTitle= "歌华有线延庆用户情况统计表"; 
       } else if ((hrCol !=null && hrCol.contains(new Integer(districtID))) || districtID ==hrdistinctId){
   	      reportMp= WebQueryUtility.getReportCustomerInfo(hrdistinctId); 
   	      strTitle= "歌华有线怀柔用户情况统计表"; 
       } else{
       	  strTitle ="该报表没有做相关的配置";
       }
       tableTitle = "1;区站或地区;0;0;0;0;总户数;0;1;0;1;";
       colTypes ="";
       int v_i =1;
       Iterator it=reportMp.keySet().iterator();
       while (it.hasNext()){
            String columnName =(String)it.next();
            v_i =v_i +1;
            tableTitle =tableTitle+columnName+";0;"+v_i+";0;"+v_i+";";
       }
       v_i =v_i +1;
       tableTitle =tableTitle+"本月退网户数"+";0;"+v_i+";0;"+v_i+";";
       v_i =v_i +1;
       tableTitle =tableTitle+"本月新增户数"+";0;"+v_i+";0;"+v_i;
       for (int i=0; i<=v_i;i++){
           if (i<v_i)
               colTypes =colTypes +"s;";
           else
           	   colTypes =colTypes +"s";
       }
       
       System.out.println("tableTitle---->"+tableTitle);
       System.out.println("colTypes---->"+colTypes);
       
       printInfo.append("  统计地区:").append(request.getParameter("txtDistrictDesc"));
       dataList.add(tableTitle); 
       int[] sumInt =new int[v_i+1];
       while (responseResultIter.hasNext()){
	        ArrayList subdata =(ArrayList)responseResultIter.next();
	        for (int i=1;i<subdata.size();i++) 
	            sumInt[i] =sumInt[i]+Integer.parseInt(subdata.get(i).toString());      
	        dataList.add(subdata);
	     }
	     ArrayList sumdata =new ArrayList(v_i+1);
	     sumdata.add("合计");
	     for (int i=1; i<sumInt.length; i++){
	        sumdata.add(String.valueOf(sumInt[i]));
	     }
	     dataList.add(sumdata);
   }
   // 收看维护费缴费及结转账收入汇总
   if (pageCode.equals("financial_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="收看维护费缴费及结转账收入汇总表";
       tableTitle = "1;区域;0;0;0;0;本期收费金额;0;1;0;1;退费金额;0;2;0;2;本期实收金额;0;3;0;3;本期应计入收入金额;0;4;0;4;上期应转入本期收入金额;0;5;0;5;本期收入合计;0;6;0;6;上期预存余额;0;7;0;7;本期预存余额;0;8;0;8"; 
       colTypes = "s;s;s;s;s;s;s;s;s;"; 
       printInfo.append("  统计区域：" +txtDistName);
       String txtSessionName =(request.getParameter("txtSessionName")==null) ? "" :request.getParameter("txtSessionName");
       printInfo.append("  会计期间:").append(txtSessionName);
       dataList.add(tableTitle); 
	     while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
    //收视费、户数统计
   if (pageCode.equals("financial_detail_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="收视费、户数统计表";
       tableTitle = "1;项目;0;0;0;0;应收费户数;0;1;0;1;欠费户数;0;2;0;2;已收户数;0;3;0;3;人工缴费金额;0;4;0;4;银行划转金额;0;5;0;5;合计;0;6;0;6"; 
       colTypes = "s;s;s;s;s;s;s;"; 
       printInfo.append("  统计区域：" +txtDistName);
       String txtSessionName =(request.getParameter("txtSessionName")==null) ? "" :request.getParameter("txtSessionName");
       printInfo.append("  会计期间:").append(txtSessionName);
       dataList.add(tableTitle); 
	     while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
   //银行代缴费统计
   if (pageCode.equals("payFromblank_excel")){
      strTitle ="银行代缴费统计";
      tableTitle = "1;客户证号;0;0;0;0;客户姓名;0;1;0;1;客户类型;0;2;0;2;区域;0;3;0;3;详细地址;0;4;0;4;电话;0;5;0;5;服务截止期;0;6;0;6;业务账户状态;0;7;0;7;付费银行;0;8;0;8;";
      colTypes = "s;s;s;s;s;s;s;s;s"; 
      String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
      String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
      printInfo.append("  选择区域：" +txtDistName);
      dataList.add(tableTitle); 
      while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
   
   //延庆收视费月报表
   if (pageCode.equals("watchfee_month_excel")){
      strTitle ="歌华收视费月报表";
      tableTitle = "1;区域;0;0;0;0;收费终端数;0;1;0;1;单位金额;0;2;0;2;合计金额;0;3;0;3";
      colTypes = "s;n;s;n"; 
      String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
      String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
      printInfo.append("  选择区域：" +txtDistName);
      dataList.add(tableTitle); 
      int serAcctCout =0;
      double serAcctMoneySum =0;
	    while (responseResultIter.hasNext()){
	       List subdata =(List)responseResultIter.next();
	       serAcctCout =serAcctCout +Integer.parseInt(subdata.get(2).toString());
	       serAcctMoneySum =serAcctMoneySum +Double.parseDouble(subdata.get(4).toString());
	       dataList.add(subdata);
	     }
	     List sumdata =new ArrayList();
	     sumdata.add("合计：");
	     sumdata.add(String.valueOf(serAcctCout));
	     sumdata.add("");
	     sumdata.add(String.valueOf(serAcctMoneySum));
	     dataList.add(sumdata);         
   }

   dataList.add(new Integer(responseResultCols.size()));
   System.out.println("dataList.size()============="+dataList.size());
   //在后台生成文件 配置开始
   String fileName = strTitle+"_"+TimestampUtility.getDate(new Date(),"yyyy-MM-dd")+".xls";
   //直接输出作为弹出框    
   response.reset();
   response.setContentType("application/vnd.ms-excel;charset=gb2312");
   response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1")); 
   WebOperationUtil.writeExcel(response.getOutputStream(),dataList,strTitle,printInfo.toString(),colTypes);
   }
%>






