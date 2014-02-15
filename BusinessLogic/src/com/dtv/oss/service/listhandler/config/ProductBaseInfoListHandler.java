package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ProductBaseInfoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ProductBaseInfoListHandler extends ValueListHandler {
    private ProductBaseInfoDAO dao =null;
    private final String  tableName = " T_Product "; 
	private ProductDTO dto =null;
	private static Class clazz=ProductBaseInfoListHandler.class;
	
	public ProductBaseInfoListHandler(){
		this.dao =new ProductBaseInfoDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(clazz,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof ProductDTO) 
	       this.dto = (ProductDTO)dto1;
	    else {
	        LogUtility.log(clazz,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //构造查询字符串
	    constructSelectQueryString(dto);
	     //执行数据查询
	     executeSearch(dao,true,true);
	}
	
	 private void constructSelectQueryString(ProductDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select *  from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     
	     if(dto.getProductID()>0)
	    	 makeSQLByIntField("ProductID", dto.getProductID(), selectStatement);
	     else{
	    	 makeSQLByStringField("ProductClass",dto.getProductClass(),selectStatement);
	    	 makeSQLByStringField("NewSAFlag",dto.getNewsaFlag(),selectStatement);
	    	 makeSQLByStringField("Status",dto.getStatus(),selectStatement);
	    	 if(!(dto.getProductName()==null || "".equals(dto.getProductName())))
	    		 selectStatement.append(" and ProductName like '%" + dto.getProductName() + "%'");
	    	 if(dto.getDateFrom()!=null)
	    		 selectStatement.append(" and DateFrom>=to_timestamp('").append(dto.getDateFrom().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	    	 if(dto.getDateTo()!=null)
	    		 selectStatement.append(" and DateTo<=to_timestamp('").append(dto.getDateTo().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	     }
	     appendOrderByString(selectStatement);
	       
		 //设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //设置当前数据查询sql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

	 private void appendOrderByString(StringBuffer selectStatement) {
			selectStatement.append(" order by ProductID desc");
	}
}
