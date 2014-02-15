package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.csr.SimpleCustServiceInteractionDAO;
import com.dtv.oss.dto.LongCommonQueryConditionDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

public class CustServiceInteractionOnlyListHandler extends ValueListHandler {
    private SimpleCustServiceInteractionDAO dao = null;
    final private String tableName = "t_custserviceinteraction a ";
    private StringBuffer begin = new StringBuffer("select a.* ");

    /**
     * use DTO as a template to determine
     * search criteria
     */
	private LongCommonQueryConditionDTO dto = null;

	public CustServiceInteractionOnlyListHandler() {
	  	this.dao = new SimpleCustServiceInteractionDAO();
	}

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto1)  throws ListHandlerException{
    	  LogUtility.log(this.getClass(), LogLevel.DEBUG,
  		"in setCriteria begin setCriteria...");
          if (dto1 instanceof LongCommonQueryConditionDTO) 
          	this.dto = (LongCommonQueryConditionDTO)dto1;
          else 
			throw new ListHandlerException("the dto type is not proper...");
          
          //check the result
          
          constructSelectQueryString(dto);
          //执行数据查询
          executeSearch(dao,true,true);
      }

      private void constructSelectQueryString(LongCommonQueryConditionDTO dto2) {
    	  StringBuffer selectStatement = new StringBuffer();
    	  begin.append(" from "+tableName);
  	  	  selectStatement.append(" where 1=1 ");
  	      if(dto.getExtraStr1()!=null &&!"".equals(dto.getExtraStr1())){
			 if(dto.getExtraStr1().indexOf(";")!=-1){
				makeSQLByIntFieldIn("a.Id", dto.getExtraStr1(), selectStatement);
			 }else{
				makeSQLByStringField("a.Id", dto.getExtraStr1(), selectStatement);
			 }
		  }
  	      
  	  	  appendOrderByString(selectStatement);
  	  	  
  	  	  setRecordCountQueryTable(tableName);
  		  setRecordCountSuffixBuffer(selectStatement);
  		  //设置当前数据查询sql
  		  setRecordDataQueryBuffer(begin.append(selectStatement));
		
	 }
      

	 private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by a.id desc");
	 }
}