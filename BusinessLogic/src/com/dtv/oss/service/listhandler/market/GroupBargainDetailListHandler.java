/*
 * Created on 2006-4-6
 *  
 */
package com.dtv.oss.service.listhandler.market;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.market.GroupBargainDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class GroupBargainDetailListHandler extends ValueListHandler {
	private GroupBargainDetailDAO dao = null;
	private String selectCriteria = "";
	final private String tableName = "t_groupbargaindetail t ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public GroupBargainDetailListHandler() {
		this.dao = new GroupBargainDetailDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	  public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(GroupBargainDetailListHandler.class,LogLevel.DEBUG,"团购券查询...");  
        if (dto instanceof CommonQueryConditionDTO) 
        	this.dto = (CommonQueryConditionDTO)dto;
        else {
        	LogUtility.log(GroupBargainListHandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");	
        }
        //构造查询字符串
        constructSelectQueryString(this.dto);   
        //执行数据查询
        executeSearch(dao,true,true);
	  }
	 
	/**
	 * executes search. Client can invoke this provided that the search criteria has been properly set. Used to perform search to
	 * refresh the list with the latest data.
	 */
	  
	 
	  private void constructSelectQueryString(CommonQueryConditionDTO dto) {
        StringBuffer begin = new StringBuffer();
       	begin.append("select t.* "  + " from " + tableName);
   
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		 
		if (dto.getNo() != null && !dto.getNo().equals("")) {
			selectStatement.append(" and t.groupbargainid=").append(dto.getNo());			
		}
		
		if (dto.getSpareStr1() != null && !dto.getSpareStr1().equals("")) {
			selectStatement.append(" and t.id=").append(dto.getSpareStr1());			
		}		
       
      
           
		appendOrderByString(selectStatement);
        // 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
    }
	  
	  private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by t.Id desc");
			 
	}
    
    
}
