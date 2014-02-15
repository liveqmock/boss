package com.dtv.oss.service.listhandler.logistics;

import java.util.List;
import java.util.ArrayList;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.dao.logistics.FaPiaoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
/**
 * <p>Title: BOSS_P4 for SCND</p>
 * <p>Description: 发票查询</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SHDV</p>
 * @author 
 * @version 1.0
 */
public class FaPiaoListHandler
    extends ValueListHandler {

  /**
   * 构造函数，设定DAO对象为FaPiaoDAO
   */
  public FaPiaoListHandler() {
    this.dao = new FaPiaoDAO();
    isManageVolumn = BusinessUtility.getFaPiaoSettingByName("ISVOLUMNMANAGE");

  }

  /**
   * 查询DTO
   */
  private CommonQueryConditionDTO dto;

  /**
   * 查询使用的DAO对象
   */
  private FaPiaoDAO dao = null;

  /**
   * 查询条件
   */
  private String selectCriteria = "";
  private String selectQueryString4Count="";

  /**
   * 查询的表名
   * @param dto
   * @throws com.dtv.oss.service.listhandler.ListHandlerException
   */
  String tableName = "T_FAPIAO a";
    String isManageVolumn;
  /**
   * 设置发票查询条件
   * @param dto
   * @throws com.dtv.oss.service.listhandler.ListHandlerException
   */
  public void setCriteria(Object dto1) throws com.dtv.oss.service.listhandler.
      ListHandlerException {
    /**@todo Implement this com.dtv.oss.service.listhandler.ValueListHandler abstract method*/

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


	private void constructSelectQueryString(CommonQueryConditionDTO dto) {

		StringBuffer begin = new StringBuffer();
			      if(isManageVolumn != null && "Y".equals(isManageVolumn))
            tableName += ",t_fapiaovolumn b ";

		begin.append("select a.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);

		// append additional conditions to where clause
		selectStatement.append(" where 1=1 ");
	  if(isManageVolumn != null && "Y".equals(isManageVolumn))
            selectStatement.append(" and a.VOLUMNSEQNO=b.SEQNO ");

   // 1.发票类型
    if (dto.getSpareStr1() != null && !"".equals(dto.getSpareStr1().trim())) {
        selectStatement.append(" and a.TYPE='" + dto.getSpareStr1().trim()+"'");
    }

    // 2.发票模板
    if (dto.getSpareStr2() != null && !"".equals(dto.getSpareStr2().trim())) {
        selectStatement.append(" and a.MODULENAME='" + dto.getSpareStr2().trim()+"'");
    }

    // 3.发票状态
    if (dto.getSpareStr3() != null && !"".equals(dto.getSpareStr3().trim())) {
        selectStatement.append(" and a.STATUS='" + dto.getSpareStr3().trim()+"'");
    }

    // 4.发票台头
    if (dto.getSpareStr4() != null && !"".equals(dto.getSpareStr4().trim())) {
        selectStatement.append(" and a.TITLE like'%" + dto.getSpareStr4().trim()+"%'");
    }

    // 5.领用操作员
    if (dto.getSpareStr5() != null && !"".equals(dto.getSpareStr5().trim())) {
    	selectStatement.append(" and a.RECIPIENTOPID in(" + dto.getSpareStr5().trim()+")");
    }

    // 6.领用操作员所属组织机构
    if (dto.getSpareStr6() != null && !"".equals(dto.getSpareStr6().trim())) {
        selectStatement.append(" and a.RECIPIENTORGID=" + dto.getSpareStr6().trim());
    }
    
    // 7.运入操作员
    if (dto.getSpareStr7() != null && !"".equals(dto.getSpareStr7().trim())) {
        selectStatement.append(" and a.CREATOROPID in(" + dto.getSpareStr7().trim()+")");
    }

    // 8.运入操作员所属组织机构
    if (dto.getSpareStr8() != null && !"".equals(dto.getSpareStr8().trim())) {
        selectStatement.append(" and a.CREATORORGID=" + dto.getSpareStr8().trim());
    }
    
    // 9.发票内部编码
    if (dto.getSpareStr9() != null && !"".equals(dto.getSpareStr9().trim())) {
        selectStatement.append(" and a.SYSSERIALNO='" + dto.getSpareStr9().trim()+"'");
    }
    
    
    // 10/11.发票字轨号范围 
    if (dto.getSpareStr10() != null && !"".equals(dto.getSpareStr10().trim())) {
    	selectStatement.append(" and a.SERAILNO >='" + dto.getSpareStr10().trim()+"'");
    }
    if (dto.getSpareStr11() != null && !"".equals(dto.getSpareStr11().trim())) {
    	selectStatement.append(" and a.SERAILNO <='" + dto.getSpareStr11().trim()+"'");
    }
    // 12/13 发票总金额
    if (dto.getSpareStr12() != null && !"".equals(dto.getSpareStr12().trim())) {
    	selectStatement.append(" and a.SUMAMOUNT >=" + dto.getSpareStr12().trim());
    }
    if (dto.getSpareStr13() != null && !"".equals(dto.getSpareStr13().trim())) {
    	selectStatement.append(" and a.SUMAMOUNT <=" + dto.getSpareStr13().trim());
    }
    // 14 发票序号（主键）
    if (dto.getSpareStr14() != null && !"".equals(dto.getSpareStr14().trim())) {
        selectStatement.append(" and a.SEQNO=" + dto.getSpareStr14().trim());
    }
	   if(dto.getSpareStr18() != null && !"".equals(dto.getSpareStr18().trim()))
            selectStatement.append(" and b.VOLUMNSN='" + dto.getSpareStr18().trim() + "'");

    //  发票最后打印日期
    if (dto.getSpareBeginTime() != null) {
    	selectStatement.append(" and a.LASTPRINTDATE >= to_timestamp('" +dto.getSpareBeginTime().toString() + "','YYYY-MM-DD-HH24:MI:SSxff')");
    }
    if (dto.getSpareEndTime() != null) {
    	selectStatement.append(" and a.LASTPRINTDATE <= to_timestamp('" +dto.getSpareEndTime().toString() + "','YYYY-MM-DD-HH24:MI:SSxff')");
    }
		
		
		//System.out.println("________===="+selectStatement.toString());
		LogUtility.log(FaPiaoListHandler.class, LogLevel.DEBUG, selectStatement.toString());
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
