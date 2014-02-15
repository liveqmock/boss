package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PackageLineDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class PackageLineListhandler extends ValueListHandler {
	
	private PackageLineDAO dao=null;
	final String tableName=" T_PACKAGELINE ";
	
	//查询条件的dto
	private ProductPackageDTO dto=null;
	
	public PackageLineListhandler(){
		dao=new PackageLineDAO();
	}
	
	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(PackageLineListhandler.class,LogLevel.DEBUG,"产品线查询...");
        if (dto instanceof ProductPackageDTO) 
        	this.dto = (ProductPackageDTO)dto;
        else {
        	LogUtility.log(PackageLineListhandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //构造查询字符串
        constructSelectQueryString(this.dto);
        //执行数据查询
        executeSearch(dao,false,false);
    }


	private void constructSelectQueryString(ProductPackageDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");

		makeSQLByIntField("PACKAGEID",dto.getPackageID(),selectStatement);
		
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
