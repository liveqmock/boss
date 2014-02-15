package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.ProductPackageDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;
import com.dtv.oss.util.TimestampUtility;

public class ProductPackageListhandler extends ValueListHandler {
	private ProductPackageDAO dao=null;
	final String tableName=" T_PRODUCTPACKAGE ";
	
	//��ѯ������dto
	private ProductPackageDTO dto=null;
	
	public ProductPackageListhandler(){
		dao=new ProductPackageDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(ProductPackageListhandler.class,LogLevel.DEBUG,"��Ʒ����ѯ...");
        if (dto instanceof ProductPackageDTO) 
        	this.dto = (ProductPackageDTO)dto;
        else {
        	LogUtility.log(ProductPackageListhandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);
        //ִ�����ݲ�ѯ
        executeSearch(dao,false,false);
    }


	private void constructSelectQueryString(ProductPackageDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		if (HelperCommonUtil.StringHaveContent(dto.getStatus()))
			makeSQLByStringField("STATUS",dto.getStatus(),selectStatement);
		makeSQLByStringField("PACKAGECLASS",dto.getPackageClass(),selectStatement);
		makeSQLByStringField("PACKAGENAME",dto.getPackageName(),selectStatement,"like");
		makeSQLByIntField("PACKAGEID",dto.getPackageID(),selectStatement);
		 if(dto.getDateFrom()!=null)
   		 selectStatement.append(" and DateFrom>=to_timestamp('").append(dto.getDateFrom().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
   	    if(dto.getDateTo()!=null)
   		 selectStatement.append(" and DateTo<=to_timestamp('").append(dto.getDateTo().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	   if(!"config".equalsIgnoreCase(dto.getDescription())){
		selectStatement.append(" and DATEFROM<=to_date('").append(TimestampUtility.TimestampToDateString(TimestampUtility.getCurrentDate()).toString()).append("', 'YYYY-MM-DD')");
		selectStatement.append(" and DATETO>=to_date('").append(TimestampUtility.TimestampToDateString(TimestampUtility.getCurrentDate()).toString()).append("', 'YYYY-MM-DD-HH')");
	   }
		appendOrderByString(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		selectStatement.append(" order by packagepriority ");
	}

}
