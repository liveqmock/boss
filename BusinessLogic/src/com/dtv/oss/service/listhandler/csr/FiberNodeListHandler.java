package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.FiberNodeDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HelperCommonUtil;


public class FiberNodeListHandler extends ValueListHandler {
	
	private FiberNodeDAO dao=null;
	
	final String tableName=" T_FIBERNODE ";
	
	//查询条件的dto
	private CommonQueryConditionDTO dto=null;
	
	public FiberNodeListHandler(){
		dao=new FiberNodeDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(FiberNodeListHandler.class,LogLevel.DEBUG,"终端设备查询...");
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(FiberNodeListHandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //构造查询字符串
        constructSelectQueryString(this.dto);
        //执行数据查询
        executeSearch(dao,true,true);
    }


	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		

		//编码
		makeSQLByStringField("FiberNodeCode",dto.getSpareStr1(),selectStatement);
		
		//地址
		makeSQLByStringField("DetailAddress",dto.getSpareStr2(),selectStatement,"like");

		//起始序号和终止序号
		if (HelperCommonUtil.StringHaveContent(dto.getBeginStr())&&HelperCommonUtil.StringHaveContent(dto.getEndStr())){
			selectStatement.append(" and FiberReceiverId >= '" +dto.getBeginStr() +"' ");
			selectStatement.append(" and FiberReceiverId <= '" +dto.getEndStr() +"' ");
		}
		else{
			if (HelperCommonUtil.StringHaveContent(dto.getBeginStr()))
				makeSQLByStringField("FiberReceiverId",dto.getBeginStr(),selectStatement);
			if (HelperCommonUtil.StringHaveContent(dto.getBeginStr()))
				makeSQLByStringField("FiberReceiverId",dto.getEndStr(),selectStatement);
		}
		
		appendOrderByString(selectStatement);
		
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
			selectStatement.append(" order by id desc");
		else {
			selectStatement.append(" order by " + orderByString + orderByAscend);
		}
		
	}
}
