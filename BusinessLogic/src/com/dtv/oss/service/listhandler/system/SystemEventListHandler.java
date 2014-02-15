/*
 * Created on 2005-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.system;

 
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.system.SytemEventDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;
 

 

/**
 * @author 241115c
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SystemEventListHandler extends ValueListHandler {

	 private SytemEventDAO dao = null;
	 final private String tableName = "t_systemevent a";
	 private SystemEventDTO dto = null;
  /**
   * 构造函数，设定DAO对象为DeviceDAO
   */
  public SystemEventListHandler() {
  	   this.dao=new SytemEventDAO();
  }
  private String selectCriteria = "";
  
  public void setCriteria(Object dto1)  throws ListHandlerException{
	  LogUtility.log(SystemEventListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
      if (dto1 instanceof SystemEventDTO) 
 	     this.dto = (SystemEventDTO)dto1;
      else {
 	  LogUtility.log(SystemEventListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		throw new ListHandlerException("the dto type is not proper...");
  }
  
 //构造查询字符串
  constructSelectQueryString(dto);
 //执行数据查询
 executeSearch(dao,true,true);
}
  private void constructSelectQueryString(SystemEventDTO dto) {
	StringBuffer begin = new StringBuffer();
	begin.append("select a.* ");
	begin.append(" from " + tableName);
	
	StringBuffer selectStatement = new StringBuffer();
	selectStatement.append(" where 1=1 ");
    /*
     * search condition:
     * invoiceno, BarCode, acctid, custid, InvoiceSourceType, status
     */
	makeSQLByIntField("a.SEQUENCENO", dto.getSequenceNo(), selectStatement);
    makeSQLByIntField("a.EVENTCLASS", dto.getEventClass(), selectStatement);
    makeSQLByIntField("a.ACCOUNTID", dto.getAccountID(), selectStatement);
    makeSQLByIntField("a.SERVICEACCOUNTID", dto.getServiceAccountID(), selectStatement);
    makeSQLByIntField("a.PRODUCTID", dto.getProductID(), selectStatement);
	makeSQLByIntField("a.OPERATORID", dto.getOperatorID(), selectStatement);
	makeSQLByIntField("a.CUSTOMERID", dto.getCustomerID(), selectStatement);
	//2007-6-6,Jason
	makeSQLByIntField("a.CSIID", dto.getCsiID(), selectStatement);
	makeSQLByIntField("a.PSID", dto.getPsID(), selectStatement);
	
	if(dto.getScDeviceID()>0){
		selectStatement.append(" and a.SCDEVICEID="+dto.getScDeviceID()+" or a.STBDEVICEID="+dto.getScDeviceID()+" or a.OLDSCDEVICEID="+dto.getScDeviceID()+" or a.OLDSTBDEVICEID="+dto.getScDeviceID());
				
	}
	if(dto.getScSerialNo()!=null){
		selectStatement.append(" and a.SCSERIALNO='"+dto.getScSerialNo()+"' or a.STBSERIALNO='"+dto.getScSerialNo()+"' or a.OLDSCSERIALNO='"+dto.getScSerialNo()+"' or a.OLDSTBSERIALNO='"+dto.getScSerialNo()+"'");
				
	}
	if(dto.getEventReason()!=null){
		String[] col=dto.getEventReason().split(";");
       String reasonCode=col[0];
       String eventClass=col[1];
       selectStatement.append(" and a.EVENTCLASS="+HelperCommonUtil.String2Int(eventClass)+" and a.EVENTREASON='"+reasonCode+"'");
	}
	 
	 
     if(dto.getDtCreate()!=null){
     	selectStatement.append(" and a.dt_create>=to_timestamp('").append(dto.getDtCreate().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
        }
    
     
      if(dto.getDtLastmod()!=null){
      	selectStatement.append(" and a.dt_lastmod<=to_timestamp('").append(dto.getDtLastmod().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
      
      }

	 	
	
	selectStatement.append(" order by a.SEQUENCENO desc");     
   
	//设置构造取得当前查询总纪录数的sql
	setRecordCountQueryTable(tableName);
	setRecordCountSuffixBuffer(selectStatement);
	//设置当前数据查询sql
	setRecordDataQueryBuffer(begin.append(selectStatement));
  }

}
  