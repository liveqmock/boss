package com.dtv.oss.service.listhandler.network;

import com.dtv.oss.dto.VOIPProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.network.VOIPProductDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class QueryVOIPProductListHandler extends ValueListHandler {
	private VOIPProductDAO dao=null;
	
	private VOIPProductDTO dto =null;
	
	private final String tableName = " t_ssif_product a ";
	
	public QueryVOIPProductListHandler(){
		this.dao=new VOIPProductDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
		"in setCriteria begin setCriteria...");
		  if (dto1 instanceof VOIPProductDTO) 
		       this.dto = (VOIPProductDTO)dto1;
		    else {
		        LogUtility.log(QueryVOIPProductListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
				throw new ListHandlerException("the dto type is not proper...");
		    }

		    //构造查询字符串
		    constructSelectQueryString(dto);
		     //执行数据查询
		     executeSearch(dao,true,true);
		
	}
	private void constructSelectQueryString(VOIPProductDTO dto)  {
		StringBuffer begin = new StringBuffer();
		begin.append("select *  from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
	  	selectStatement.append(" where 1=1  ");
	    makeSQLByIntField("a.productid", dto.getProductID(), selectStatement);
	    makeSQLByStringField("a.sssrvcode", dto.getSssrvCode(), selectStatement);
	  	appendOrderByString(selectStatement);
	  	setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.dt_create desc");
	}
}
