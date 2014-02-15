package com.dtv.oss.service.listhandler.system;

import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.dao.system.FutureRightDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;

public class FutureRightListHandler extends ValueListHandler {
    private FutureRightDAO dao=null;
    private String selectCriteria = "";
    final private String tableName = "t_futureright t ";
    private CommonQueryConditionDTO dto = null;
    
    public FutureRightListHandler(){
    	this.dao =new FutureRightDAO();
    }
	
    public void setCriteria(Object dto1) throws ListHandlerException {
		if (CommonConstDefinition.DEBUGMODE) {
			System.out.println("begin setCriteria...");
		}
		if (dto1 instanceof CommonQueryConditionDTO) {
			this.dto = (CommonQueryConditionDTO) dto1;
		} else {
			throw new ListHandlerException("the dto type is not proper...");
		}
		
//		check the result
		String select = wrapSQL(getSelectQueryString(dto));
		if (CommonConstDefinition.DEBUGMODE) {
			System.out.println(select);
		}
//		if (CommonConstDefinition.DEBUGMODE) {
//			System.out.println(selectQueryString4Count);
//		}
//		getTotalSizeWithSQL(selectQueryString4Count);

		if (CommonConstDefinition.DEBUGMODE) {
			System.out.println("after getSelectQueryString...");
		}
		if (selectCriteria.equals(select) == false) {
			selectCriteria = select;
			if (CommonConstDefinition.DEBUGMODE) {
				System.out.println("begin executeSearch...");
			}
			executeSearch(selectCriteria);
			if (CommonConstDefinition.DEBUGMODE) {
				System.out.println("end executeSearch...");
			}
		}
	}
    
    private void executeSearch(String selectCriteria) throws ListHandlerException {
		try {
			if (selectCriteria == null) {
				throw new ListHandlerException("FutureRight query criteria required...");
			}
			List resultsList = dao.executeSelect(selectCriteria);
			setList(resultsList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ListHandlerException(e.getMessage());
		}
	}
    
    private String getSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		StringBuffer begin4count = new StringBuffer();
		begin4count.append("select count(*) from " + tableName);
		begin.append("select * from " + tableName);

		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1 ");
		
		if (dto.getNo() !=null && !dto.getNo().equals("")){
			makeSQLByIntField("t.seqno",Integer.parseInt(dto.getNo()),selectStatement);
		}
		
		if (dto.getCustomerID() !=0){
			makeSQLByIntField("t.customerid",dto.getCustomerID(),selectStatement);
		}
		
		if (dto.getSpareTime1() !=null || dto.getSpareTime2() !=null){
	 	   if (dto.getSpareTime1() !=null){
			  selectStatement.append(" and t.createdate >=to_timestamp('"+dto.getSpareTime1()+"','yyyy-MM-dd-HH24:MI:SSxff')");
	 	   }
	 	   if (dto.getSpareTime2() !=null){
	 		  selectStatement.append(" and t.createdate <=to_timestamp('"+dto.getSpareTime2()+"','yyyy-MM-dd-HH24:MI:SSxff')");
	 	   }
		}
		
		if (dto.getSpareTime3() !=null || dto.getSpareTime3() !=null){
			if (dto.getSpareTime3() !=null){
			  selectStatement.append(" and t.excutedate >=to_timestamp('"+dto.getSpareTime3()+"','yyyy-MM-dd-HH24:MI:SSxff')");	
			}
			if (dto.getSpareTime4() !=null){
			  selectStatement.append(" and t.excutedate <=to_timestamp('"+dto.getSpareTime4()+"','yyyy-MM-dd-HH24:MI:SSxff')");		
			}
		}
		
		if (dto.getSpareTime5() !=null || dto.getSpareTime6() !=null){
			if (dto.getSpareTime5() !=null){
			   selectStatement.append(" and t.canceldate >=to_timestamp('"+dto.getSpareTime5()+"','yyyy-MM-dd-HH24:MI:SSxff')");	
			}
			if (dto.getSpareTime6() !=null){
			   selectStatement.append(" and t.canceldate <=to_timestamp('"+dto.getSpareTime6()+"','yyyy-MM-dd-HH24:MI:SSxff')");		
			}
		}
		
		selectStatement.append(" order by t.dt_create desc");
		
		return begin.append(selectStatement).toString();
    }

}
