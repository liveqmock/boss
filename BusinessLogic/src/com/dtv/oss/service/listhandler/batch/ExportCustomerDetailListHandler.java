package com.dtv.oss.service.listhandler.batch;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.batch.ExportCustomerDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.work.JobCardListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;

public class ExportCustomerDetailListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
    private ExportCustomerDAO dao=null;
    private static final Class clazz=ExportCustomerDetailListHandler.class;
    final private String tableName ="t_exportcustomerhead head,t_exportcustomerdetail detail";
    
    public ExportCustomerDetailListHandler(){
    	this.dao =new ExportCustomerDAO();
    }
    
    public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);

	}
    
    private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer();
		
		begin.append("select detail.* from " + tableName);
		selectStatement.append(" where 1=1 and head.batchNo =detail.batchNo and head.status='D' ");
		
		//����ΪspareStr3,������֯ΪspareStr16
		if (dto.getSpareStr3() == null || "".equals(dto.getSpareStr3()))
			dto.setSpareStr16(String.valueOf(BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())));	
			
		String  contectTable=	BusinessUtility.getAsTableShowByOrgIDAndDistrictID(dto.getSpareStr3(),dto.getSpareStr16());	
		begin.append(" , "+contectTable+" districtInfo" );
		
		selectStatement.append(" and detail.distrinctid= districtInfo.id ");
		
        // �ͻ�����
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1()))
			makeSQLByStringField("detail.name", dto.getSpareStr1(),selectStatement, "like");
		
		// ����Ա
		if (HelperCommonUtil.StringHaveContent(dto.getOperator()))
			makeSQLByIntField("head.createOpid",HelperCommonUtil.String2Int(dto.getOperator()),selectStatement);
        
		//����ʱ��
		if (HelperCommonUtil.StringHaveContent(dto.getBeginStr()))
			selectStatement.append(" and trunc(detail.dt_createTime)>=to_date('").append(dto.getBeginStr()).append("','yyyy-mm-dd')");

		if (HelperCommonUtil.StringHaveContent(dto.getEndStr()))
			selectStatement.append(" and trunc(detail.dt_createTime)<=to_date('").append(dto.getEndStr()).append("','yyyy-mm-dd')");

		//���뱸ע
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2()))
			makeSQLByStringField("head.comments", dto.getSpareStr2(),selectStatement, "like");

        // ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName+" , "+contectTable+" districtInfo");
		setRecordCountSuffixBuffer(selectStatement);
		
		appendOrderByString(selectStatement);
		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
    }
    
    private void appendOrderByString(StringBuffer selectStatement) {
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		if ((orderByString == null) || orderByString.trim().equals(""))
			selectStatement.append(" order by head.createTime desc");
		else
			selectStatement.append(" order by a." + orderByString+ orderByAscend);
	}
}

