package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.csr.AgentServiceInteractionDAO;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.domain.Operator;
import com.dtv.oss.service.util.HelperCommonUtil;

import java.util.List;

/**
 * david.Yang
 */
public class AgentCsiListHandler extends ValueListHandler {
    private AgentServiceInteractionDAO dao = null;
    private String selectCriteria = "";
    final private String tableName = "t_custserviceinteraction a,t_newCustomerinfo nci, t_address addr";

    private CommonQueryConditionDTO dto = null;

    public AgentCsiListHandler() {
        this.dao = new AgentServiceInteractionDAO();
    }

    /**
     * Use setCriteria() method to check whether or not the QUERY is the same
     */
    public void setCriteria(Object dto1) throws ListHandlerException {
    	LogUtility.log(AgentCsiListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO)
        	this.dto =(CommonQueryConditionDTO)dto1;
        else {
        	LogUtility.log(AgentCsiListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
        	throw new ListHandlerException("the dto type is not proper...");
        }
        //构造查询字符串
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }
        	

    private void executeSearch(String selectCriteria)
            throws ListHandlerException {
        try {
            if (selectCriteria == null) {
                throw new ListHandlerException("Customer query criteria required...");
            }
            List resultsList = dao.executeSelect(selectCriteria);
            setList(resultsList);
        } catch (Exception e) {
            throw new ListHandlerException(e.getMessage());
        }
    }

    /**
     * Use fillSqlWithPrivilege() method to filter by operator's privilege
     *
     * @return
     */
    private boolean fillSqlWithPrivilege(StringBuffer selectStatement) throws ListHandlerException {
        Operator operator = getOperator();
        return true;

    }


    private boolean isAgent(int operatorID) throws ListHandlerException {
        boolean isAgent = false;
        return isAgent;
    }

    private void constructSelectQueryString(CommonQueryConditionDTO dto) throws ListHandlerException {
        StringBuffer begin = new StringBuffer("select a.*, ");
        begin.append(CommonUtility.getSelectExpression4Address("addr."));
		begin.append(",");
		begin.append(CommonUtility.getSelectExpression4NewCustomerInfo("nci."));
        begin.append(" from "+tableName);
        
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	
    	 //  判断权限
        if (fillSqlWithPrivilege(selectStatement) == false) return ;
      
        // 单据号
        makeSQLByStringField("a.ReferSheetId",dto.getSpareStr1(),selectStatement);
        // 所在区
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr2())){
        	makeSQLByIntField("addr.districtid ",Integer.parseInt(dto.getSpareStr2()),selectStatement);
        }
        // 预约代理商姓名
        makeSQLByStringField("nci.name",dto.getSpareStr3(),selectStatement);

        //cretetime
        if (dto.getBeginTime() != null)
            selectStatement.append(" and a.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

        if (dto.getEndTime() != null)
            selectStatement.append(" and a.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        
        //检查是否为代理商查询
        if (isAgent(operatorID)) {
         //   selectStatement.append(" and a.CREATEOPERATORID=").append(operatorID);
        }
        selectStatement.append(" and a.type='").append(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BK).append("'");
        appendString(" a.customerId =nci.Id ",selectStatement);
        appendString(" nci.fromaddressId =addr.addressId ",selectStatement);
        appendOrderByString(selectStatement);
        //设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
    }
    
    private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) || orderByString.trim().equals(""))
			selectStatement.append(" order by a.createtime desc");
		else {
			selectStatement.append(" order by a." + orderByString + orderByAscend);
		}
		
	}
}
