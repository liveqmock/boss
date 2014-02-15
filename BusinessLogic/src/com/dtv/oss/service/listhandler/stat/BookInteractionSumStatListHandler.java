package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.BookInteractionSumStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BookInteractionSumStatListHandler extends ValueListHandler{
	private BookInteractionSumStatDAO dao = null;
	private CommonQueryConditionDTO dto = null;
	private static final Class clazz=BookInteractionSumStatListHandler.class;
    private static final String tableName=" T_CustServiceInteraction csi, t_operator op ";
    
	public BookInteractionSumStatListHandler() {
		this.dao = new BookInteractionSumStatDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if(getOperatorID()==0)
			throw new ListHandlerException("无法获取操作员的信息，不能进行统计操作！");
		
		if (dto1 instanceof CommonQueryConditionDTO) 
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);	
		//执行数据查询
		executeSearch(dao, false, false);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException{
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer();
		
//		if("CA".equals(dto.getSpareStr2()))
//		{
//			begin.append("select op.OperatorID,op.OperatorName,csi.status,csi.installationtype, count(*) as curCount from ").append(tableName);
//			begin.append(",T_CSIProcessLog log");
//			selectStatement.append(" where csi.ID=log.CSIID and log.Action='" +CommonConstDefinition.CSIPROCESSLOG_ACTION_BOOKINGCANCEL+
//					"' and csi.Status='" + CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL +
//					"' and log.OperatorID = op.OperatorID ");
//		}
//		else
//		{
		begin.append("select op.OperatorID,op.OperatorName,csi.status,csi.installationtype, count(*) as curCount from ").append(tableName);
		selectStatement.append(" where csi.CreateOperatorID = op.OperatorID ");
//		}
		
		
		selectStatement.append(" and csi.type in ('").append(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK).append("') ");
		
        //创建时间条件
	    if(dto.getBeginTime() != null)
	    	selectStatement.append(" and csi.CreateTime >=to_timestamp('").append(dto.getBeginTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	    if(dto.getEndTime() != null)
	    	selectStatement.append(" and csi.CreateTime <=to_timestamp('").append(dto.getEndTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        //预约上门时间条件
        if(dto.getSpareBeginTime() != null)
        	selectStatement.append(" and csi.PreferedDate >=to_timestamp('").append(dto.getSpareBeginTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        if(dto.getSpareEndTime() != null)
        	selectStatement.append(" and csi.PreferedDate <=to_timestamp('").append(dto.getSpareEndTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        //来源渠道条件
        if(!(dto.getType()==null || "".equals(dto.getType())))
        	selectStatement.append(" and csi.id in (select cust.csiid from t_newcustomerinfo cust where cust.csiid=csi.id and cust.opensourcetype='").append(dto.getType()).append("')");
    	
        //根据操作员所在的组织显示所有的符合条件的记录
        //selectStatement.append(" and op.OrgID in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid=").append(getCurrentOperatorSubjectOrg()).append(")");
        if (!(dto.getSpareStr1()== null || "".equals(dto.getSpareStr1())))
	    	selectStatement.append(" and op.OrgID in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid=").append(dto.getSpareStr1()).append(")");
        
    	selectStatement.append(" group by op.OperatorID,op.OperatorName,csi.status,csi.installationtype ");

		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}



