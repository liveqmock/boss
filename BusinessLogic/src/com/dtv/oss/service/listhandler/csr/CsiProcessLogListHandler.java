/*
 * author :david.Yang 
 */
package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CsiProcessLogDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

public class CsiProcessLogListHandler extends ValueListHandler {
	private CsiProcessLogDAO dao = null;
	final private String tableName = "t_csiprocesslog log ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public CsiProcessLogListHandler() {
		this.dao = new CsiProcessLogDAO();
	}

	/**
     * Use setCriteria() method to check whether or not the QUERY is the same
     * @return
     */
    public void setCriteria(Object dto1)  throws ListHandlerException{
  	    LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof CommonQueryConditionDTO) 
      	    this.dto = (CommonQueryConditionDTO)dto1;
        else {
      	    LogUtility.log(CsiProcessLogListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
        
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }
    
    private void constructSelectQueryString(CommonQueryConditionDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select log.* ");
    	begin.append(" from " + tableName);
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField(" id",HelperCommonUtil.String2Int(dto.getSpareStr1()),selectStatement);
    	makeSQLByIntField(" csiid",HelperCommonUtil.String2Int(dto.getSpareStr2()),selectStatement);
    	makeSQLByStringField(" OPERATORID",dto.getSpareStr3(),selectStatement);
    	appendOrderByString(selectStatement);
		//appendOrderByString(end);
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
    	
    }
    
    private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
		String orderByAscend = (dto.getIsAsc()? " asc":" desc");
		
		if ((orderByString == null) ||
				orderByString.trim().equals(""))
			selectStatement.append(" order by log.csiid,id desc");
		else {
			selectStatement.append(" order by log." + orderByString + orderByAscend);
		}
		
	}


}
