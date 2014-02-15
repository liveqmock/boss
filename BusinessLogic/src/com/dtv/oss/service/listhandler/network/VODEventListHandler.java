package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VODEventDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author Chen jiang
 */

public class VODEventListHandler extends ValueListHandler {
	
	 
	private final String tableName = "t_vodqueue t";

	private VODEventDAO dao = null;

	private CommonQueryConditionDTO dto = null;

	public VODEventListHandler() {
		this.dao = new VODEventDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// added
		// if (fillDTOWithPrivilege(dto) == false) return;
		// 构造查询字符串
		 
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao,true,true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		 
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		 
		makeSQLByStringField("hostid",dto.getSpareStr3(),selectStatement);
		makeSQLByStringField("status",dto.getStatus(),selectStatement);
//		事件类型
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr1()))
			appendString("t.eventid in (select sequenceno from t_systemevent where eventclass="+ dto.getSpareStr1() + ")", selectStatement);
		
//		customerID
		if (dto.getCustomerID()>0)
			appendString("t.eventid in (select sequenceno from t_systemevent where customerid="+ dto.getCustomerID() + ")", selectStatement);
//		序列号
		if  (HelperCommonUtil.StringHaveContent(dto.getSpareStr2()))
			appendString("t.eventid in (select sequenceno from t_systemevent where SCSerialNo='"+ dto.getSpareStr2()+ "' or stbserialno='"+ dto.getSpareStr2()+"')", selectStatement);	 		
//		创建时间
		if (dto.getBeginTime()!=null) 
		        selectStatement.append("and t.CreateTime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
		
		if (dto.getEndTime()!=null) 
			   selectStatement.append("and t.CreateTime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");  
		appendOrderByString(selectStatement);
		 
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement)); 
		
		 
	}
	/**
	 * @param selectStatement
	 */
	private void appendOrderByString(StringBuffer selectStatement) {
		 
		selectStatement.append(" order by queueid desc" );

   }
}