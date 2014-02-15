package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustServiceInteractionDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.service.util.CsrBusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author david.Yang
 * Created on 2005-10-19
 */

public class CustServiceInteractionListHandler extends ValueListHandler {
	 private CustServiceInteractionDAO dao = null;
	 private final String tableName = "t_custserviceinteraction a, t_address addr,"
	 	                            + "t_address addr2, t_newCustomerinfo nci,t_newcustAccountInfo ncai";
	 private final String tableName2 = "t_custserviceinteraction a, t_address addr, T_Customer nci";
	 
     private CommonQueryConditionDTO dto = null;

	 public CustServiceInteractionListHandler() {
		 this.dao = new CustServiceInteractionDAO();
	 }
	 
	/**
      * Use setCriteria() method to check whether or not the QUERY is the same
      * @return
      */
     public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(CustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
         if (dto1 instanceof CommonQueryConditionDTO) 
         	this.dto = (CommonQueryConditionDTO)dto1;
         else {
         	LogUtility.log(CustServiceInteractionListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
          }
         // added 
         //   if (fillDTOWithPrivilege(dto) == false) return;
         //�����ѯ�ַ���
         constructSelectQueryString(dto);
         //ִ�����ݲ�ѯ
         executeSearch(dao,true,true);
     }

     /**
      * Use fillDTOWithPrivilege() method to filter by operator's privilege
      * @return
     
     private boolean fillDTOWithPrivilege(CommonQueryConditionDTO dto)
	  	throws ListHandlerException {
     	
     	switch(getBelongTo()) {
	      	case OPERATOR_BELONG_TO_GENERAL_COMPANY:
	      		return true;
	      	case OPERATOR_BELONG_TO_SUBCOMPANY:
	      		dto.setFiliale(getOperator().getOrgID());
	      		return true;
	      	case OPERATOR_BELONG_TO_STREET_STATION:
	      		dto.setStreet(getOperator().getOrgID());
	      		return true;
	      	default:
	      		return false;
     	}
     }
      */
     private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
     	StringBuffer begin = new StringBuffer();
    	begin.append("select a.*, ");
    	begin.append(CommonUtility.getSelectExpression4Address("addr."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4Address2("addr2."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4NewCustomerInfo("nci."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4NewCustAccountInfo("ncai."));
		begin.append(" from " + tableName);
		
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	
    	//ԤԼ����
		if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getNo(), selectStatement);
			}else{
				makeSQLByIntField("a.Id", HelperCommonUtil.String2Int(dto.getNo()), selectStatement);
			}
	    }
		
        //������
		if (dto.getStreet() !=0){
		    String distinctIds =CsrBusinessUtility.getSubDistrinctID(dto.getStreet());
		    selectStatement.append(" and addr.DISTRICTID in ( "+distinctIds+" ) " );
		}
		/**-------20070122 �   start-------------*/
		/**
		 * Ĭ������,ȡ�õ�ǰ����Ա������֯,��ȡ�ø���֯�ɹ�������,��ò���Աֻ�ܲ�ѯ�ù��������ڵĿͻ�
		 */
		// ������֯
		appendStringWithOrgGovernedDistrict("addr.districtid", 0,selectStatement);
		/**-------20070122 �   end-------------*/
		
        //�û�֤��
        //	   makeSQLByIntField("a.CustomerId",HelperCommonUtil.String2Int(dto.getSpareStr1()),selectStatement);
		//  ������makeSQLByIntField,��ΪԤԼ��ѯ�������ľ���0��modify by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())){
			selectStatement.append(" and a.CustomerId="+dto.getSpareStr1());
		}
		
		//����״̬
		makeSQLByStringField("a.PaymentStatus",dto.getSpareStr5(),selectStatement);
		
		//�طñ�־
		makeSQLByStringField("a.CallBackFlag",dto.getSpareStr6(),selectStatement);
		
		//��ϵ������
		makeSQLByStringField("nci.Name", dto.getSpareStr11(), selectStatement, "like");
		
		//��ϵ�绰
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr12()))
			selectStatement.append(" and (nci.TELEPHONE like '%").append(dto.getSpareStr12()).append("%' or nci.MOBILENUMBER like '%").append(dto.getSpareStr12()).append("%')");
		
		//��Դ���� �� ��Դ����ID
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())) {
            selectStatement.append(" and nci.OPENSOURCETYPE='").append(dto.getSpareStr13());
            selectStatement.append("'");
            if (HelperCommonUtil.StringHaveContent(dto.getSpareStr14())) {
                selectStatement.append(" and nci.OPENSOURCEID=");
                selectStatement.append(dto.getSpareStr14());
            }
        }
		
        //�������
		makeSQLByStringField("a.ReferJobCardID", dto.getSpareStr15(), selectStatement);
		
		//��ϸ��ַ
		makeSQLByStringField("addr.DETAILADDRESS", dto.getSpareStr16(),  selectStatement, "like");
		
        //����ʱ��
		if (dto.getBeginTime() != null)
			selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		
		//ԤԼ��������
		if (dto.getSpareTime1() != null)
			selectStatement.append(" and a.PREFEREDDATE>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getSpareTime2() != null)
			selectStatement.append(" and a.PREFEREDDATE<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendString(" a.id=nci.csiid ",selectStatement);
		makeSQLByStringFieldIn(" a.type",dto.getType(),selectStatement);
		makeSQLByStringFieldIn(" a.status", dto.getStatus(), selectStatement);
		appendString(" a.id=ncai.csiid", selectStatement);
		appendString(" nci.FromAddressID=addr.addressid", selectStatement);
		appendString(" ncai.addressid=addr2.addressid", selectStatement);
		
		//appendOrderByString(selectStatement);
		
		
		//���� ԤԼ�������ŵ����� �Ļطò�ѯsql ��ǰ��Ľṹ����һ�� begin
		
		StringBuffer begin2 = new StringBuffer();
    	begin2.append("select a.*, ");
    	begin2.append(CommonUtility.getSelectExpression4Address("addr."));
		begin2.append(",");
		//ƴ�ճ��յ�addr2
		begin2.append(CommonUtility.getSelectNullAddress2());
		begin2.append(",");
		//Ϊȡ�ͻ���Ϣ��ƴ�յ�sql
		begin2.append(CommonUtility.getSelectExpression4Customer("nci."));
		begin2.append(",");
		//ƴ�ճ��յĿͻ��ʻ���Ϣ ��ԤԼ�����ŵ�����Ӧ����ʾ�����ʻ���Ϣ������Ҫ��˵����ƴ�յģ�
		begin2.append(CommonUtility.getSelectNullCustAccountInfo());
		begin2.append(" from " + tableName2);
		
    	StringBuffer selectStatement2 = new StringBuffer();
    	selectStatement2.append(" where 1=1 ");
    	
    	//ԤԼ����
		if(dto.getNo()!=null &&!"".equals(dto.getNo())){
			if(dto.getNo().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getNo(), selectStatement2);
			}else{
				makeSQLByIntField("a.Id", HelperCommonUtil.String2Int(dto.getNo()), selectStatement2);
			}
	    }
		
        //������
		if (dto.getStreet() !=0){
		    String distinctIds =CsrBusinessUtility.getSubDistrinctID(dto.getStreet());
		    selectStatement2.append(" and addr.DISTRICTID in ( "+distinctIds+" ) " );
		}
		/**-------20070122 �   start-------------*/
		/**
		 * Ĭ������,ȡ�õ�ǰ����Ա������֯,��ȡ�ø���֯�ɹ�������,��ò���Աֻ�ܲ�ѯ�ù��������ڵĿͻ�
		 */
		// ������֯
		appendStringWithOrgGovernedDistrict("addr.districtid", 0,selectStatement2);
		/**-------20070122 �   end-------------*/
		
        //�û�֤��
        //	   makeSQLByIntField("a.CustomerId",HelperCommonUtil.String2Int(dto.getSpareStr1()),selectStatement2);
		//  ������makeSQLByIntField,��ΪԤԼ��ѯ�������ľ���0��modify by david.Yang
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1())){
			selectStatement2.append(" and a.CustomerId="+dto.getSpareStr1());
		}
		
		//����״̬
		makeSQLByStringField("a.PaymentStatus",dto.getSpareStr5(),selectStatement2);
		
		//�طñ�־
		makeSQLByStringField("a.CallBackFlag",dto.getSpareStr6(),selectStatement2);
		
		//��ϵ������
		makeSQLByStringField("nci.Name", dto.getSpareStr11(), selectStatement2, "like");
		
		//��ϵ�绰
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr12()))
			selectStatement2.append(" and (nci.TELEPHONE like '%").append(dto.getSpareStr12()).append("%' or nci.TelephoneMobile like '%").append(dto.getSpareStr12()).append("%')");
		
		//��Դ���� �� ��Դ����ID
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())) {
            selectStatement2.append(" and nci.OPENSOURCETYPE='").append(dto.getSpareStr13());
            selectStatement2.append("'");
        }
		
        //�������
		makeSQLByStringField("a.ReferJobCardID", dto.getSpareStr15(), selectStatement2);
		
		//��ϸ��ַ
		makeSQLByStringField("addr.DETAILADDRESS", dto.getSpareStr16(),  selectStatement2, "like");
		
        //����ʱ��
		if (dto.getBeginTime() != null)
			selectStatement2.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getEndTime() != null)
			selectStatement2.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		
		//ԤԼ��������
		if (dto.getSpareTime1() != null)
			selectStatement2.append(" and a.PREFEREDDATE>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

		if (dto.getSpareTime2() != null)
			selectStatement2.append(" and a.PREFEREDDATE<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		appendString(" a.CustomerID=nci.CustomerID ",selectStatement2);
		makeSQLByStringFieldIn(" a.type",dto.getSpareStr7(),selectStatement2);
		//��������²�����ԤԼ�������ŵ������Ľ�� ʹ�ⲿ��(union�ĺ�벿��)���Ϊ��
		if(dto.getSpareStr7() == null || "".equals(dto.getSpareStr7().trim()))
		{
			selectStatement2.append(" and a.id = -1");
		}
		makeSQLByStringFieldIn(" a.status", dto.getStatus(), selectStatement2);
		
		appendString(" nci.addressid=addr.addressid", selectStatement2);
		

		//���� ԤԼ�������ŵ����� �Ļطò�ѯsql ��ǰ��Ľṹ����һ�� end
		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		//setRecordCountQueryTable(tableName);
		//setRecordCountSuffixBuffer(selectStatement);
		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		StringBuffer querySql = new StringBuffer("select * from (").append(begin).append(selectStatement).append(" union ").append(begin2).append(selectStatement2).append(")");
		appendOrderByString(querySql);
		setRecordCountSuffixCondBuffer(querySql);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(querySql);
	}
     
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by DT_CREATE desc");
		else {
			selectStatement.append(" order by " + orderByString + orderByAscend);
		}
		
	}

}
