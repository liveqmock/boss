package com.dtv.oss.service.listhandler.groupcustomer;
 
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.csr.ProductPackageListhandler;
import com.dtv.oss.service.dao.csr.ProductPackageDAO;
import com.dtv.oss.service.util.HelperCommonUtil;
import com.dtv.oss.util.TimestampUtility;

public class GroupPakageListHandler extends ValueListHandler {
	private ContractDTO dto=null;
	private ProductPackageDAO dao=null;
	private final String tableName="t_productpackage a,t_contracttopackage b ";
	
	public GroupPakageListHandler() {
		dao=new ProductPackageDAO();
	}

	public void setCriteria(Object dto) throws ListHandlerException {
		LogUtility.log(GroupPakageListHandler.class,LogLevel.DEBUG,"产品包查询...");
        if (dto instanceof ContractDTO) 
        	this.dto = (ContractDTO)dto;
        else {
        	LogUtility.log(GroupPakageListHandler.class,LogLevel.DEBUG,"传入的查找参数有误...");
			throw new ListHandlerException("the dto type is not proper...");
         }

        //构造查询字符串
        constructSelectQueryString(this.dto);
        //执行数据查询
        executeSearch(dao,false,false);

	}
	private void constructSelectQueryString(ContractDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * from " + tableName);
      
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where a.packageid=b.productpackageid ");
		makeSQLByStringField("b.contractno",dto.getContractNo(),selectStatement);
		appendOrderByString(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		selectStatement.append(" order by packageid desc");
	}
}
