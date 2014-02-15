// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2008-5-19 13:53:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FapiaoTransDetailListHandler.java

package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.FapiaoTransitionDetailDTO;
import com.dtv.oss.service.dao.logistics.FapiaoTransDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import java.io.PrintStream;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.log.LogLevel;

import com.dtv.oss.log.LogUtility;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
public class FapiaoTransDetailListHandler extends ValueListHandler
{

    public FapiaoTransDetailListHandler()
    {
        dao = null;
        selectCriteria = "";
        tableName = "t_fapiaotransitiondetail a";
        dao = new FapiaoTransDetailDAO();
    }

    public void setCriteria(Object dto1)
        throws ListHandlerException
    {
    
    if (CommonConstDefinition.DEBUGMODE) {
      System.out.println("begin setCriteria...");
    }

    // 是正确的DTO类型吗？
    if (dto1 instanceof CommonQueryConditionDTO) {
      this.dto = (CommonQueryConditionDTO) dto1;
      System.out.println("for testing|| dto type is " + dto.getClass().getName());
    }
    else {
      throw new ListHandlerException("the dto type is not proper..." +
                                     dto.getClass().getName());
    }
 // 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
    }



 private CommonQueryConditionDTO dto;
    private FapiaoTransDetailDAO dao;
    private String selectCriteria;
    String tableName;

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {

		StringBuffer begin = new StringBuffer();
	
		begin.append("select a.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);

		// append additional conditions to where clause
		selectStatement.append(" where 1=1 ");
	 if (dto.getSpareStr14() != null && !"".equals(dto.getSpareStr14().trim())) {
        selectStatement.append(" and a.FAPIAOSEQNO=" + dto.getSpareStr14().trim());
    }
		
		
		//System.out.println("________===="+selectStatement.toString());
		LogUtility.log(FapiaoTransDetailListHandler.class, LogLevel.DEBUG, selectStatement.toString());
		appendOrderByString(selectStatement);
		
		// appendOrderByString(end);
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");

		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by a.SEQNO desc");
		else
			selectStatement.append(" order by a." + dto.getOrderField() + orderByAscend);

		orderByAscend = null;
	}
}