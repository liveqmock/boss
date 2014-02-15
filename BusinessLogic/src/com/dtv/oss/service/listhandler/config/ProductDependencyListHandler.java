package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ProductDependencyDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ProductDependencyListHandler extends ValueListHandler {
    private ProductDependencyDAO dao =null;
    private final String  tableName = " T_ProductDependency "; 
	private ProductDependencyDTO dto =null;
	private static Class clazz=ProductDependencyListHandler.class;
	
	public ProductDependencyListHandler(){
		this.dao =new ProductDependencyDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(clazz,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof ProductDependencyDTO) 
	       this.dto = (ProductDependencyDTO)dto1;
	    else {
	        LogUtility.log(clazz,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //�����ѯ�ַ���
	    constructSelectQueryString(dto);
	     //ִ�����ݲ�ѯ
	     executeSearch(dao,true,true);
	}
	
	 private void constructSelectQueryString(ProductDependencyDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select *  from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     
	     if(dto.getSeqNo()>0){
	    	 makeSQLByIntField("SeqNo", dto.getSeqNo(), selectStatement);
	     }
	     else{
	    	 makeSQLByStringField("Type",dto.getType(),selectStatement);
	    	 
	    	 makeSQLByIntField("ProductId",dto.getProductId(),selectStatement);
	    	 
	    	 if(dto.getDtCreate()!=null)
		    	 selectStatement.append(" and dt_create>=to_timestamp('").append(dto.getDtCreate().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     if(dto.getDtLastmod()!=null)
		    	 selectStatement.append(" and dt_create<=to_timestamp('").append(dto.getDtLastmod().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     
	     }
	   
	     appendOrderByString(selectStatement);
	       
		 //���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //���õ�ǰ���ݲ�ѯsql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

	 private void appendOrderByString(StringBuffer selectStatement) {
			selectStatement.append(" order by SeqNo desc");
	}
}
