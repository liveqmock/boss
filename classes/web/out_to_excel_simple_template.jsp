<!--��ѯ���Ҵ�ӡ�Ĺ����ļ�-->
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
  	<caption><font color="red"><i>�������󣬴�����Ϣ����:</i></font></caption>
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
   
   //���Ȳ�ѯ���
   List dataList = new ArrayList();
   QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
	 System.out.println("RepCmd================"+RepCmd);
	 Collection responseResultCols = (Collection) RepCmd.getPayload();
	 System.out.println("responseResultCols.size()========"+responseResultCols.size());
	 Iterator responseResultIter =responseResultCols.iterator();
    
   printInfo.append("��ӡ����:"+TimestampUtility.getDate(new Date(),"yyyy-MM-dd"));
  
   if (pageCode.equals("real_income_excel")){
      strTitle ="ʵ�ռ�¼��ѯ";
      tableTitle ="1;���;0;0;0;0;�ͻ�����;0;1;0;1;�ͻ�֤��;0;2;0;2;��������;0;3;0;3;��ϸ��ַ;0;4;0;4;�ʺ�;0;5;0;5;����ʱ��;0;6;0;6;��������;0;7;0;7;"
                 +"��Ŀ����;0;8;0;8;���;0;9;0;9;";
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
      sumMoneydata.add(" �ܼ� ");
      sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");
      sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");sumMoneydata.add("");
      DecimalFormat formartdata= new DecimalFormat("0.00");
      sumMoneydata.add(formartdata.format(sumAmount));
      dataList.add(sumMoneydata);
      printInfo.append(" ������").append(dataCount+"");  
      int collectorID =WebUtil.StringToInt(request.getParameter("txtCollectorID"));
      String collectorName =Postern.getOperatorNameByID(collectorID);
      if (collectorName !=null && !collectorName.equals("")){
         printInfo.append("  ����Ա��").append(collectorName);  
      }
   }
   
   if (pageCode.equals("out_to_owe_suspend_excel")){
       //����Ϊ�������ñ���
      strTitle = "ͣ���ɷ�����ͻ���ѯ";
      tableTitle = "1;�ͻ�֤��;0;0;0;0;����;0;1;0;1;ҵ���ʻ���;0;2;0;2;�ͻ�����;0;3;0;3;��ϸ��ַ;0;4;0;4;ͣ��ԭ��;0;5;0;5;��ϵ�绰;0;6;0;6;"
                  + "�ʻ��ܽ��;0;7;0;7;�ͻ���ע;0;8;0;8;";                 
	    printInfo.append("     ����:").append(request.getParameter("txtCountyDesc"));
	    //����������excel�е����ͣ����֣�n;�ַ���s
	    colTypes = "s;s;s;s;s;s;s;s;s"; 
	    dataList.add(tableTitle); 
	    while (responseResultIter.hasNext()){
	       List subdata =(List)responseResultIter.next();
	       dataList.add(subdata);
	    }
   }
   
   //�ֹ�˾��ת��˰����ͳ�Ʊ�
   if (pageCode.equals("freetaxi_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="�ֹ�˾��ת��˰����ͳ�Ʊ�";
       tableTitle = "1;�ͻ�֤��;0;0;0;0;�ͻ�����;0;1;0;1;�ɷ�����;0;2;0;2;�ɷѽ��;0;3;0;3;֧����ʽ;0;4;0;4;�տ���;0;5;0;5;֧Ʊ����;0;6;0;6"; 
       colTypes = "s;s;s;s;s;s;s;"; 
       printInfo.append("  ����" +txtDistName);
       String txtStatBeginTime =(request.getParameter("txtStatBeginTime")==null) ? "" :request.getParameter("txtStatBeginTime");
       String txtStatEndTime =(request.getParameter("txtStatEndTime")==null) ? "" :request.getParameter("txtStatEndTime");
       if (!txtStatBeginTime.equals("") || !txtStatEndTime.equals("") ){
          printInfo.append("  ͳ��ʱ���:").append(txtStatBeginTime +"~"+txtStatEndTime);
       }
       String opid =(request.getParameter("txtCollectorID")==null) ? "" :request.getParameter("txtCollectorID");
       String opName ="";
       if (opid.equals("")) {
          opName = "ȫ��" ;
       } else{
       	  opName = (String)Postern.getAllOperator().get(opid);
       }
       printInfo.append("  �շ��ˣ�" +opName);
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
	     totalList.add("�ϼƽɷѿͻ���");
	     totalList.add(String.valueOf(custList.size()));
	     totalList.add("�ϼƽɷѽ��");
	     totalList.add(String.valueOf(freetotalMoney));
	     totalList.add("");
	     totalList.add("");
	     totalList.add("");
	     dataList.add(totalList); 
    }
    
    if (pageCode.equals("csistat_for_huairou_query_excel")){
      strTitle = "����ҵ������ͳ��";
      tableTitle = "3;����;0;0;2;0;�¿��û�;0;1;0;8;���û��ɷ�;0;9;0;24;����������ͣҵ���ʻ���;0;25;2;25;�ͻ���;0;26;2;26"
                 +  ";����ҵ���ʻ���;0;27;2;27;������ͣҵ���ʻ���;0;28;2;28;ǿ����ͣҵ���ʻ���;0;29;2;29"                
                 +  ";��װ��>0;1;1;1;3;��װ��=0,���ӷѡ�0;1;4;1;6;��װ��=0�����ӷ�=0;1;7;1;8"
                 +  ";���ӷ�;1;9;1;10;�źŶ�ͨ��;1;11;1;12;�˿ڷ�;1;13;1;14;�ն˷�;1;15;1;16;������;1;17;1;18;Ǩ�Ʒ�;1;19;1;20;������;1;21;1;22;С��;1;23;1;24"
                 +  ";�ͻ���;2;1;2;1;ҵ���ʻ���;2;2;2;2;�ɷѽ��;2;3;2;3;�ͻ���;2;4;2;4;ҵ���ʻ���;2;5;2;5;�ɷѽ��;2;6;2;6;�ͻ���;2;7;2;7;ҵ���ʻ���;2;8;2;8"
                 +  ";���;2;9;2;9;����;2;10;2;10;���;2;11;2;11;����;2;12;2;12;���;2;13;2;13;����;2;14;2;14;���;2;15;2;15;����;2;16;2;16"
                 +  ";���;2;17;2;17;����;2;18;2;18;���;2;19;2;19;����;2;20;2;20;���;2;21;2;21;����;2;22;2;22;���;2;23;2;23;����;2;24;2;24";                   
      colTypes = "s;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n;n"; 
  
   
      String txtStatBeginTime =(request.getParameter("txtStatBeginTime")==null) ? "" :request.getParameter("txtStatBeginTime");
      String txtStatEndTime =(request.getParameter("txtStatEndTime")==null) ? "" :request.getParameter("txtStatEndTime");
      if (!txtStatBeginTime.equals("") || !txtStatEndTime.equals("") ){
          printInfo.append("  ͳ��ʱ���:").append(txtStatBeginTime +"~"+txtStatEndTime);
      }
      dataList.add(tableTitle); 
      while (responseResultIter.hasNext()){
	       List subdata =(List)responseResultIter.next();
	       dataList.add(subdata);
	    }   
   }
   
   //�軪�����û����ͳ�Ʊ�(����,����)
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
          strTitle= "�軪���������û����ͳ�Ʊ�"; 
       } else if ((hrCol !=null && hrCol.contains(new Integer(districtID))) || districtID ==hrdistinctId){
   	      reportMp= WebQueryUtility.getReportCustomerInfo(hrdistinctId); 
   	      strTitle= "�軪���߻����û����ͳ�Ʊ�"; 
       } else{
       	  strTitle ="�ñ���û������ص�����";
       }
       tableTitle = "1;��վ�����;0;0;0;0;�ܻ���;0;1;0;1;";
       colTypes ="";
       int v_i =1;
       Iterator it=reportMp.keySet().iterator();
       while (it.hasNext()){
            String columnName =(String)it.next();
            v_i =v_i +1;
            tableTitle =tableTitle+columnName+";0;"+v_i+";0;"+v_i+";";
       }
       v_i =v_i +1;
       tableTitle =tableTitle+"������������"+";0;"+v_i+";0;"+v_i+";";
       v_i =v_i +1;
       tableTitle =tableTitle+"������������"+";0;"+v_i+";0;"+v_i;
       for (int i=0; i<=v_i;i++){
           if (i<v_i)
               colTypes =colTypes +"s;";
           else
           	   colTypes =colTypes +"s";
       }
       
       System.out.println("tableTitle---->"+tableTitle);
       System.out.println("colTypes---->"+colTypes);
       
       printInfo.append("  ͳ�Ƶ���:").append(request.getParameter("txtDistrictDesc"));
       dataList.add(tableTitle); 
       int[] sumInt =new int[v_i+1];
       while (responseResultIter.hasNext()){
	        ArrayList subdata =(ArrayList)responseResultIter.next();
	        for (int i=1;i<subdata.size();i++) 
	            sumInt[i] =sumInt[i]+Integer.parseInt(subdata.get(i).toString());      
	        dataList.add(subdata);
	     }
	     ArrayList sumdata =new ArrayList(v_i+1);
	     sumdata.add("�ϼ�");
	     for (int i=1; i<sumInt.length; i++){
	        sumdata.add(String.valueOf(sumInt[i]));
	     }
	     dataList.add(sumdata);
   }
   // �տ�ά���ѽɷѼ���ת���������
   if (pageCode.equals("financial_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="�տ�ά���ѽɷѼ���ת��������ܱ�";
       tableTitle = "1;����;0;0;0;0;�����շѽ��;0;1;0;1;�˷ѽ��;0;2;0;2;����ʵ�ս��;0;3;0;3;����Ӧ����������;0;4;0;4;����Ӧת�뱾��������;0;5;0;5;��������ϼ�;0;6;0;6;����Ԥ�����;0;7;0;7;����Ԥ�����;0;8;0;8"; 
       colTypes = "s;s;s;s;s;s;s;s;s;"; 
       printInfo.append("  ͳ������" +txtDistName);
       String txtSessionName =(request.getParameter("txtSessionName")==null) ? "" :request.getParameter("txtSessionName");
       printInfo.append("  ����ڼ�:").append(txtSessionName);
       dataList.add(tableTitle); 
	     while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
    //���ӷѡ�����ͳ��
   if (pageCode.equals("financial_detail_report_excel")){
       String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
       String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
       strTitle ="���ӷѡ�����ͳ�Ʊ�";
       tableTitle = "1;��Ŀ;0;0;0;0;Ӧ�շѻ���;0;1;0;1;Ƿ�ѻ���;0;2;0;2;���ջ���;0;3;0;3;�˹��ɷѽ��;0;4;0;4;���л�ת���;0;5;0;5;�ϼ�;0;6;0;6"; 
       colTypes = "s;s;s;s;s;s;s;"; 
       printInfo.append("  ͳ������" +txtDistName);
       String txtSessionName =(request.getParameter("txtSessionName")==null) ? "" :request.getParameter("txtSessionName");
       printInfo.append("  ����ڼ�:").append(txtSessionName);
       dataList.add(tableTitle); 
	     while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
   //���д��ɷ�ͳ��
   if (pageCode.equals("payFromblank_excel")){
      strTitle ="���д��ɷ�ͳ��";
      tableTitle = "1;�ͻ�֤��;0;0;0;0;�ͻ�����;0;1;0;1;�ͻ�����;0;2;0;2;����;0;3;0;3;��ϸ��ַ;0;4;0;4;�绰;0;5;0;5;�����ֹ��;0;6;0;6;ҵ���˻�״̬;0;7;0;7;��������;0;8;0;8;";
      colTypes = "s;s;s;s;s;s;s;s;s"; 
      String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
      String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
      printInfo.append("  ѡ������" +txtDistName);
      dataList.add(tableTitle); 
      while (responseResultIter.hasNext()){
	        List subdata =(List)responseResultIter.next();
	        dataList.add(subdata);
	     }   
   }
   
   //�������ӷ��±���
   if (pageCode.equals("watchfee_month_excel")){
      strTitle ="�軪���ӷ��±���";
      tableTitle = "1;����;0;0;0;0;�շ��ն���;0;1;0;1;��λ���;0;2;0;2;�ϼƽ��;0;3;0;3";
      colTypes = "s;n;s;n"; 
      String txtDistrictID =(request.getParameter("txtDistrictID")==null) ? "0" :request.getParameter("txtDistrictID");
      String txtDistName =Postern.getDistrictNameByID(Integer.parseInt(txtDistrictID));
      printInfo.append("  ѡ������" +txtDistName);
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
	     sumdata.add("�ϼƣ�");
	     sumdata.add(String.valueOf(serAcctCout));
	     sumdata.add("");
	     sumdata.add(String.valueOf(serAcctMoneySum));
	     dataList.add(sumdata);         
   }

   dataList.add(new Integer(responseResultCols.size()));
   System.out.println("dataList.size()============="+dataList.size());
   //�ں�̨�����ļ� ���ÿ�ʼ
   String fileName = strTitle+"_"+TimestampUtility.getDate(new Date(),"yyyy-MM-dd")+".xls";
   //ֱ�������Ϊ������    
   response.reset();
   response.setContentType("application/vnd.ms-excel;charset=gb2312");
   response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1")); 
   WebOperationUtil.writeExcel(response.getOutputStream(),dataList,strTitle,printInfo.toString(),colTypes);
   }
%>






