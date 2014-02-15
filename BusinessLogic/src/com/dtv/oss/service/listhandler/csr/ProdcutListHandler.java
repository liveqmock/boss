package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.ProdcutDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ProdcutListHandler extends ValueListHandler {
	
	private ProdcutDAO dao=null;
	final String tableName=" T_PRODUCT ";
	
	//��ѯ������dto
	private ProductDTO dto=null;
	
	public ProdcutListHandler(){
		dao=new ProdcutDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(ProdcutListHandler.class,LogLevel.DEBUG,"��Ʒ��ѯ...");
        if (dto instanceof ProductDTO) 
        	this.dto = (ProductDTO)dto;
        else {
        	LogUtility.log(ProdcutListHandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);
       
        //ִ�����ݲ�ѯ
        executeSearch(dao,false,false);
    }


	private void constructSelectQueryString(ProductDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByStringField("STATUS", dto.getStatus(), selectStatement);
		if(!"all".equalsIgnoreCase(dto.getProductClass()))
		selectStatement.append(" and productclass = 'S' ");
		appendOrderByString(selectStatement);
		
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by productid asc");
}


}

