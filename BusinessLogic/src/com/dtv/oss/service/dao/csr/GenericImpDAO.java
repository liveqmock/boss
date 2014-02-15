package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.util.CommonConstDefinition;

public class GenericImpDAO extends GenericDAO {
	int from =0;
	int to =0;
	List rowCountList ;
	boolean speedFlag =false ;
	
	public GenericImpDAO(List rowCountList){
		this.rowCountList =rowCountList;
	}
	
	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		int ct =0;
	    while(rs.next()) {
	       ct =ct +1;
	       int rsLength = rs.getMetaData().getColumnCount();
		   if (speedFlag){
			   if (ct >=from && ct <=to){ 
				  Collection resultList =new ArrayList();
			      for (int i=1; i<=rsLength; i++){
				      resultList.add((rs.getObject(i)==null) ? "" :rs.getObject(i));
			      }
			      list.add(resultList);
			   }
		   }else{
			   if (ct >CommonConstDefinition.EXPORT_MAX){
				   throw new SQLException("记录超过了"+CommonConstDefinition.EXPORT_MAX+"条，不能导出,请改变查询条件后重新查询！");
			   }
			   
			   Collection resultList =new ArrayList();
			   for (int i=1; i<=rsLength; i++){
				   resultList.add((rs.getObject(i)==null) ? "" :rs.getObject(i));
			   }
			   list.add(resultList);
		   }
		}
	    rowCountList.add(new Integer(ct));
	    System.out.println("from--------------->"+from);
		System.out.println("to----------------->"+to);
		System.out.println("speedFlag---------->"+speedFlag);
		System.out.println("rowCountList---------->"+rowCountList);
        return list;
   }
	
	public void setFrom(int from) {
		this.from = from;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public void setSpeedFlag(boolean speedFlag) {
		this.speedFlag = speedFlag;
	}
}
