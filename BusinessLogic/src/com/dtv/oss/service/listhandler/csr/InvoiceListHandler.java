package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.dao.csr.InvoiceDAO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author 250803y david.Yang
 */

public class InvoiceListHandler extends ValueListHandler {
    private InvoiceDAO dao = null;
    final private String tableName = "t_invoice a";


	private CommonQueryConditionDTO dto = null;

	public InvoiceListHandler() {
	  	this.dao = new InvoiceDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(InvoiceListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CommonQueryConditionDTO) 
       	 this.dto = (CommonQueryConditionDTO)dto1;
       else {
       	 LogUtility.log(InvoiceListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
       }

       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(CommonQueryConditionDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select a.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
        /*
         * search condition:
         * invoiceno, BarCode, acctid, custid, InvoiceSourceType, status
         */
    	if (HelperCommonUtil.StringHaveContent(dto.getNo()))
            makeSQLByIntField("a.invoiceno", Integer.parseInt(dto.getNo()), selectStatement);
        
        makeSQLByStringField("a.BarCode", dto.getSpareStr1(), selectStatement);
        
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr11()))
            makeSQLByIntField("a.AcctID", Integer.parseInt(dto.getSpareStr11()), selectStatement);
        
        
        makeSQLByIntField("a.CustID", dto.getCustomerID(), selectStatement);
        
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr12()))
		    makeSQLByStringFieldIn("a.InvoiceSourceType", dto.getSpareStr12(), selectStatement);
		
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr13())) 
        	appendString("a.InvoiceCycleID in (select t.id from t_billingcycle t where t.name ='"+dto.getSpareStr13()+"')", selectStatement);
        
        if (HelperCommonUtil.StringHaveContent(dto.getSpareStr14()))
            makeSQLByIntField("a.REFERENCENO",Integer.parseInt(dto.getSpareStr14()), selectStatement);
        
        if (dto.getDistrict() !=0)
        	appendString(" a.CustID in (select distinct t.customerID from t_customer t,t_address p where t.addressID=p.addressID and p.districtid in (select id from t_districtsetting "
                        +"  connect by prior id=belongto start with id = "+dto.getDistrict()+")) ",selectStatement);
        
		makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement); 

		if (dto.getBeginTime() != null)
		   	 selectStatement.append(" and a.CREATEDATE>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		     
		if (dto.getEndTime() != null)
	         selectStatement.append(" and a.CREATEDATE<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		if (dto.getSpareStr15()!=null)
			selectStatement.append(" and a.custid in (select customerid from t_customer where addressid in(select addressid from t_address where detailaddress like '%" + dto.getSpareStr15() +"%'))");
		selectStatement.append(" order by a.invoiceno desc");     
       
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}