package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ForcedDepositDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.ForcedDepositDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * <p> Title: </p>
 * <p> Description: 押金列表查询</p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company: Digivision</p>
 * User: Nile.chen
 * Date: 2005-12-7
 * Time: 13:57:18
 * To change this template use File | Settings | File Templates.
 */
public class ForcedDepositListHandler extends ValueListHandler {
    private String selectCriteria = "";
    private ForcedDepositDAO dao = null;
    private ForcedDepositDTO dto = null;
    private String tableName = "T_FORCEDDEPOSIT a ";

    public ForcedDepositListHandler() {
        dao = new ForcedDepositDAO();
    }

    public void setCriteria(Object dto) throws ListHandlerException {
		
		LogUtility.log(ForcedDepositListHandler.class,LogLevel.DEBUG,"押金查找...");
        
		ForcedDepositDTO dto1=(ForcedDepositDTO)dto;
        //构造查询字符串
        constructSelectQueryString(dto1);
        //执行数据查询
        executeSearch(dao,false,false);

	}
   

    private void constructSelectQueryString(ForcedDepositDTO dto) {
		
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
		
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	makeSQLByIntField("CUSTOMERID",dto.getCustomerID(),selectStatement);
    	makeSQLByIntField("SEQNO",dto.getSeqNo(),selectStatement);
        selectStatement.append(" order by SEQNO desc");
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}

    