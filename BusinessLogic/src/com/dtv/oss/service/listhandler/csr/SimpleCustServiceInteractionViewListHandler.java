/*
 * author :david.Yang 
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.SimpleCustServiceInteractionViewDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class SimpleCustServiceInteractionViewListHandler extends ValueListHandler {
    private SimpleCustServiceInteractionViewDAO dao = null;
    private String tableName = "";

    private CommonQueryConditionDTO dto = null;
    private String orgIDs =null;
    private StringBuffer begin = new StringBuffer("select a.id id,a.type type,a.customerid customerid,a.createtime createtime,a.status status,a.agentname agentname,a.agenttelephone agenttelephone,a.agentcardtype agentcardtype,a.agentcardid agentcardid ");
    private StringBuffer selectStatement = new StringBuffer();

    public SimpleCustServiceInteractionViewListHandler() {
        this.dao = new SimpleCustServiceInteractionViewDAO();
    }

    public void setCriteria(Object dto1) throws ListHandlerException {
        LogUtility.log(SimpleCustServiceInteractionViewListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO) 
      	    this.dto = (CommonQueryConditionDTO)dto1;
        else {
      	    LogUtility.log(SimpleCustServiceInteractionViewListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        //���µ��ж�����������p4���������p5�в�̫��������ע�͵�---yangchen 2006/09/28----
        //if (!isParentCompany()) orgIDs = getOrgIDs();
        
        constructSelectQueryString(dto);
        //ִ�����ݲ�ѯ
        executeSearch(dao,true,true);
    }
    
    private void constructSelectQueryString(CommonQueryConditionDTO dto)throws ListHandlerException {
    	constructSelectQueryHead(dto);
    	constructSelectQueryBody(dto);
//Ϊ���ֹ����ԣ���������ע�͵����ѹ�ͬ������ȡ����---yangchen 2006/09/28----    
        appendOrderByString(selectStatement);
        //���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
    }
   
    private void constructSelectQueryHead(CommonQueryConditionDTO dto)throws ListHandlerException {
    	//����Ŀǰ�Ŀͻ��ǰ��������ģ��������еĲ�ѯ���漰���ͻ�/�ͻ���ַ/������Ϣ���ж�����Ҳ��û�б�Ҫ������ע�͵�---yangchen 2006/09/28----
    	//if (dto.getStreet() ==0){
    	//	tableName =" t_custserviceinteraction a ,t_address c ";
    	//} else{
    	//�����������֯������������֯��������ļ�������������dto.getFilialeΪ0��ʱ���Ե�ǰ����Ա����֯Ϊ��׼
		//tableName =" custserviceteractionView a,T_ADDRESS addr," +
		//			"(select org.orgid from t_organization org connect by prior org.orgid = org.parentorgid start with org.orgid = "+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+ ") organization ";
    	//tableName =" custserviceteractionView a,T_ADDRESS addr," +
    	//			"(select id from t_districtsetting connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+ ")) districtset ";
		//�����������֯��������ѯ����֯��������֯����������,�������Ϊ��,��ȡ��ǰ����Ա��֯����������֯����������
		
    	tableName =" custserviceteractionView a,T_ADDRESS addr ";
    	
    	//��֯����
    	if(dto.getFiliale()!=0){
			tableName =tableName +
						", (select org.orgid from t_organization org connect by prior org.orgid = org.parentorgid start with org.orgid = "+dto.getFiliale()+") organization ";
			//tableName =" custserviceteractionView a,T_ADDRESS addr," +
			//			"(select id from t_districtsetting connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+dto.getFiliale()+ ")) districtset ";
		}
    	
    	//������
		if (dto.getStreet() !=0){
			tableName =tableName +
					", (select id from t_districtsetting " +
					" connect by prior id=belongto start with id ="+dto.getStreet()+") district ";
       		//appendStringWithDistrictID("addr.districtid",dto.getStreet(), selectStatement);
        }
		else{	//���ݲ���Ա������֯�Ĺ�����������������
			tableName =tableName +
				", (select id from t_districtsetting " +
				" connect by prior id=belongto start with id in (SELECT districtid FROM T_ORGGOVERNEDDISTRICT where orgid="+BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) district ";
		}
		
    	begin.append(" from "+tableName);
    }
    
    
    private void constructSelectQueryBody(CommonQueryConditionDTO dto) {
    	
    	selectStatement.append(" where 1=1 ");
    	
    	//if (dto.getStreet() !=0)  //������
    		selectStatement.append(" and district.Id= addr.districtid "); 
    	
    	if(dto.getFiliale()!=0) //��֯����
    		selectStatement.append(" and organization.orgid=a.createorgid ");
    	
    	//�������
    	if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.id", dto.getNo(), selectStatement);
			}else{
				makeSQLByStringField("a.id", dto.getNo(), selectStatement);
			}
	    }
    	
        //��������
    	makeSQLByStringFieldIn("a.type", dto.getType(), selectStatement);
    	
        //����״̬
   		makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement);
    	
        //����Ա��Ϣ
    	 if (HelperCommonUtil.StringHaveContent(dto.getOperator())){
    		selectStatement.append(" and a.id in (select log.csiid from t_operator sub_oper,t_csiprocesslog log where log.operatorid =sub_oper.operatorid ");
			selectStatement.append(" and sub_oper.operatorname like '%").append(dto.getOperator()).append("%')");
    	 }
         //�طñ�־
    	 makeSQLByStringField("a.callbackflag", dto.getSpareStr1(), selectStatement);
   
    	 //���ѱ�־
    	 makeSQLByStringField("a.paymentstatus", dto.getSpareStr2(), selectStatement);
    
         //�û�֤��  ��12-04�޸�,���ſͻ���ѯʱ,�������ӿͻ�
    	 if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
    		 appendStringWithGroupCustomerID("a.customerid",dto.getSpareStr5(),selectStatement);
    	 }
    	 //����customerstyle 2007/06/26 ������������ �ע�͵�
    	 /*if(dto.getSpareStr12()!=null&&!dto.getSpareStr12().equals("")){
    		 appendStringWithGroupCustomerStyle("a.CUSTOMERID",dto.getSpareStr12(),selectStatement);
    	 }*/
    	 
    	 //ȡ��ԭ��
    	 makeSQLByStringField("a.statusreason", dto.getSpareStr9(), selectStatement);
         //����ԭ��
    	 makeSQLByStringField("a.createreason", dto.getSpareStr14(), selectStatement);
    	 
         //����ʱ��
         if (dto.getBeginTime() != null)
             selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

         if (dto.getEndTime() != null)
             selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
    	 
         //ԤԼ����ʱ��
         if (dto.getSpareBeginTime() != null)
             selectStatement.append(" and a.prefereddate>=to_timestamp('").append(dto.getSpareBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

         if (dto.getSpareEndTime() != null)
             selectStatement.append(" and a.prefereddate<=to_timestamp('").append(dto.getSpareEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
         
         //����Ƿ�Ϊ�����̲�ѯ
         if (CsrBusinessUtility.isAgent(operatorID)) {
             selectStatement.append(" and a.id in (select distinct csiid from t_csiprocesslog where operatorId ="+operatorID+") ");
         }
         //������Ų�ѯ
         if (HelperCommonUtil.StringHaveContent(dto.getSpareStr11())){
        	 selectStatement.append(" and a.referjobcardid =(select jobcardid from t_jobcard  where jobcardid ="+dto.getSpareStr11()+") ");   	 
         }
         //����������־������������װ���ɹ��˷���
         if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())){
        	 if("returnfee".equalsIgnoreCase(dto.getSpareStr13())){
        		 selectStatement.append("and ( a.paymentstatus='D' And a.Status ='F') ");  
        		 if(dto.getNo()!=null &&!"".equals(dto.getNo()))
        			 selectStatement.append(" or (a.installationtype ='S' and a.paymentstatus='W' And a.status ='F' And a.id="+dto.getNo()+")");  
        		 else
        			 selectStatement.append(" or (a.installationtype ='S' and a.paymentstatus='W' And a.status ='F' )");  
        	 }
         }
         
        //���������ǶԿͻ�/��ַ�Ĳ�ѯ���������
 		selectStatement.append(" and ");
 		//***selectStatement.append(" a.a_customerid in (select CUSTOMERID from T_CUSTOMER customer ,T_ADDRESS addr");
 		selectStatement.append(" a.customeraddressid = addr.ADDRESSID");
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr7())) {
 			selectStatement.append(" and addr.DETAILADDRESS like '%" + dto.getSpareStr7() + "%' ");
        }
	   	 //����customerstyle 2007/06/26 �׷��
	   	 if(dto.getSpareStr12()!=null&&CommonConstDefinition.CUSTOMERSTYLE_GROUP.equals(dto.getSpareStr12())){
	   		selectStatement.append(" and a.customerstyle ='"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"'");
	   	 }else{
	   		selectStatement.append(" and (a.customerstyle<>'"+CommonConstDefinition.CUSTOMERSTYLE_GROUP+"' Or a.customerstyle Is Null)");
	   		 
	   	 }
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr6())) {
            selectStatement.append(" and a.customername='");
            selectStatement.append(dto.getSpareStr6());
            selectStatement.append("'");
 		}
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr8())) {
            selectStatement.append(" and (a.customertelephone like '%");
            selectStatement.append(dto.getSpareStr8());
            selectStatement.append("%' or a.customermobile like '%" + dto.getSpareStr8() + "%') ");
        }
 		//�ͻ���������
 		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr10())) {
            selectStatement.append(" and a.customertype='");
            selectStatement.append(dto.getSpareStr10());
            selectStatement.append("'");
        }
 		//����������������
 		//������֯
        try {
       	 //����ѡ���������������֯�����������
       	 //if (dto.getStreet() !=0){
       	//	appendStringWithDistrictID("addr.districtid",dto.getStreet(), selectStatement);
        // }
       	
       	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr26())&&(dto.getSpareStr26().equalsIgnoreCase("agentBk"))){
       	 selectStatement.append(" and a.customer_opensourcetype='")
       	 .append(CommonConstDefinition.OPENSOURCETYPE_PROXY).append("'");
       	 
        }
        
       	//�����������֯������������֯��������ļ�������������dto.getFilialeΪ0��ʱ���Ե�ǰ����Ա����֯Ϊ��׼
//			appendStringWithOrgGovernedDistrict("addr.districtid",dto.getFiliale(),selectStatement);
       	//�����������֯��������ѯ����֯��������֯����������,�������Ϊ��,��ȡ��ǰ����Ա��֯����������֯����������
       
       	//selectStatement.append(" and addr.districtid=districtset.id");
		//appendStringWithOrgAndSubOrg("a.createorgid",dto.getFiliale(),selectStatement);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void appendOrderByString(StringBuffer selectStatement) {		
		//�����customerid����ʱ����������,
		if(dto.getSpareStr5()!=null&&!dto.getSpareStr5().equals("")){
			selectStatement.append(" order by a.customerid, a.id desc");
			return;
		}
		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by a.id desc");
		else {
			selectStatement.append(" order by a." + orderByString + orderByAscend);
		}
		
	}
}