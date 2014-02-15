/*
 * Created on 2004-8-9
 *  
 */
package com.dtv.oss.service.listhandler.market;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.market.GroupBargainDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class GroupBargainListHandler extends ValueListHandler {
	private  GroupBargainDAO dao = null;
	 
	final private String tableName = "t_groupbargain t ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CommonQueryConditionDTO dto = null;

	public GroupBargainListHandler() {
		this.dao = new  GroupBargainDAO();
	}

	/**
	 * Use setCriteria() method to check whether or not the QUERY is the same
	 * 
	 * @return
	 */
	  public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(GroupBargainListHandler.class,LogLevel.DEBUG,"团购券查询...");  
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
	  private void constructSelectQueryString(CommonQueryConditionDTO dto) {
        StringBuffer begin = new StringBuffer();
       	begin.append("select t.* "  + " from " + tableName);
   
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		 
		makeSQLByStringFieldSuffix("t.bargainno", dto.getNo(), selectStatement, "like");
//		status	
		makeSQLByStringFieldIn("t.status", dto.getStatus(), selectStatement);
		 //代理商 
		makeSQLByStringField("t.AGENTID", dto.getSpareStr2(), selectStatement); 
		makeSQLByIntField("t.ORGID", dto.getFiliale(), selectStatement); 
       
        //是否优惠
        if (dto.getSpareStr3() != null && !dto.getSpareStr3().equals("")) {
            selectStatement.append(" and t.ISCAMPAIGN='").append(dto.getSpareStr3()).append("'");
        }
        
        if (dto.getSpareStr4() !=null && !dto.getSpareStr4().equals("")){
        	selectStatement.append(" and t.CAMPAIGNID =").append(dto.getSpareStr4());
        }
       
        if (dto.getSpareStr1() != null) {
            selectStatement.append(" and t.id in (select groupbargainid from t_groupbargaindetail a " +
                    "where t.id=a.groupbargainid and a.detailno='"+dto.getSpareStr1()+"')");
        }
        if (dto.getBeginTime() != null || dto.getEndTime() != null) {
                
        	 
             if (dto.getBeginTime() != null) {
                 selectStatement.append(" and t.dataFrom<=to_timestamp('"+dto.getBeginTime()+"','yyyy-MM-dd-HH24:MI:SSxff')");
             }
             if (dto.getEndTime() != null) {
                 selectStatement.append(" and t.dateto>=to_timestamp('"+dto.getEndTime()+"','yyyy-MM-dd-HH24:MI:SSxff')");
             }
           
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

               
	 

 

	
	  
         
