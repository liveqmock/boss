package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CustomerProblemSumStatDAO;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;

public class CustomerProblemSumStatListHandler extends ValueListHandler {
	private CustomerProblemSumStatDAO dao = null;
	private CommonQueryConditionDTO dto = null;
	private static final Class clazz=CustomerProblemSumStatListHandler.class;
    private static final String tableName=" t_CustomerProblem cp, t_operator op ";

	public CustomerProblemSumStatListHandler() {
		this.dao = new CustomerProblemSumStatDAO();
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
		
		begin.append("select op.OperatorID,op.OperatorName,cp.ProblemCategory, count(*) as curCount from ").append(tableName);
		selectStatement.append(" where 1=1 and cp.createOPID = op.OperatorID ");

		if(dto.getBeginTime() != null)
			selectStatement.append(" and cp.CreateDate >=to_timestamp('").append(dto.getBeginTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	    if(dto.getEndTime() != null)
	    	selectStatement.append(" and cp.CreateDate <=to_timestamp('").append(dto.getEndTime()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	    //注意这里面保存的是loginid
	    if (!(dto.getOperator()== null || "".equals(dto.getOperator())))
	    		selectStatement.append(" and op.loginid like '%").append(dto.getOperator()).append("%'");
	    //根据前台选择的组织查看查询结果，若没有选择查询范围为所有的数据 
	    if (!(dto.getSpareStr1()== null || "".equals(dto.getSpareStr1())))
	    	selectStatement.append(" and op.OrgID in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid=").append(dto.getSpareStr1()).append(")");
	    //根据操作员所在的组织显示所有的符合条件的记录
        //selectStatement.append(" and op.OrgID in (select orgid from t_organization connect by prior orgid=parentorgid start with orgid=").append(getCurrentOperatorSubjectOrg()).append(")");
    	selectStatement.append(" group by op.OperatorID,op.OperatorName,cp.ProblemCategory ");
	  
	    	
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));	    
	}
	
}



