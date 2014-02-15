package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ServiceAccountDeviceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.ServiceAccountDeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class SATerminalDeviceListHandler extends ValueListHandler {
	private ServiceAccountDeviceDAO dao = null;
   final private String tableName1 = "T_ServiceAccount a left join t_customerproduct b on a.serviceaccountid=b.serviceaccountid left join t_terminaldevice c on b.deviceid=c.deviceid ";
   //final private String tableName2 = "T_ServiceAccount a left join t_customerproduct b on a.serviceaccountid=b.serviceaccountid ";
   final private String tableName2 = "T_ServiceAccount a";
    private ServiceAccountDeviceDTO dto=null;
    
    public SATerminalDeviceListHandler(){
    	this.dao=new ServiceAccountDeviceDAO();
    }
    
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(SATerminalDeviceListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
        if (dto1 instanceof ServiceAccountDeviceDTO) 
        	this.dto = (ServiceAccountDeviceDTO)dto1;
        else {
        	LogUtility.log(SATerminalDeviceListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
         }
        //构造查询字符串
        constructSelectQueryString(dto);
        //执行数据查询
        executeSearch(dao,false,false);

	}

	private void constructSelectQueryString(ServiceAccountDeviceDTO dto) {
		StringBuffer begin = new StringBuffer();
	//	begin.append("select a.*  from " + tableName);
		StringBuffer selectStatement = new StringBuffer();
		 
		if(dto.getDeviceClass() !=null || dto.getDeviceModel()!=null || dto.getSerialNo()!=null || dto.getServiceID() != 0){
			begin.append("select distinct  a.*  from " + tableName1);
		    selectStatement.append(" where 1=1 and (b.status='I' or b.status='N') ");
		}
		else {
			begin.append("select distinct  a.*  from " + tableName2);
			selectStatement.append(" where 1=1 ");
		}
		
		makeSQLByIntField("a.customerid",dto.getCustomerID(),selectStatement);
		makeSQLByStringFieldIn("a.status",dto.getStatus(),selectStatement);
		
		if(dto.getServiceCode()!= null && !"".equals(dto.getServiceCode())){
			//makeSQLByStringField("a.servicecode",dto.getServiceCode(),selectStatement);
			selectStatement.append(" and exists (select servicecode from T_ServiceAccount account where account.serviceaccountid=a.serviceaccountid and account.servicecode='"+dto.getServiceCode()+"') ");
		}
		if(dto.getServiceID()!= 0){
			//makeSQLByIntField("b.productid",dto.getServiceID(),selectStatement);
			selectStatement.append(" and exists (select productid from t_customerproduct product where product.serviceaccountid=b.serviceaccountid and product.productid="+dto.getServiceID()+") ");
		}
		if(dto.getDeviceClass()!= null && !"".equals(dto.getDeviceClass())){
			//makeSQLByStringField("c.deviceclass",dto.getDeviceClass(),selectStatement);
			selectStatement.append(" and exists (select * from t_terminaldevice device1 where device1.deviceid=c.deviceid and device1.deviceclass='"+dto.getDeviceClass()+"') ");
		}
		if(dto.getDeviceModel()!= null && !"".equals(dto.getDeviceModel())){
			//makeSQLByStringField("c.devicemodel",dto.getDeviceModel(),selectStatement);
			selectStatement.append(" and exists (select * from t_terminaldevice device2 where device2.deviceid=c.deviceid and device2.devicemodel='"+dto.getDeviceModel()+"') ");
		}
		if(dto.getSerialNo()!= null && !"".equals(dto.getSerialNo())){
			//makeSQLByStringField("c.serialno",dto.getSerialNo(),selectStatement);
			selectStatement.append(" and exists (select * from t_terminaldevice device3 where device3.deviceid=c.deviceid and device3.serialno='"+dto.getSerialNo()+"') ");
		}
		
		selectStatement.append(" and a.status<>'" + CommonConstDefinition.SERVICEACCOUNT_STATUS_CANCEL + "'");
		selectStatement.append(" order by a.serviceaccountid desc");
		//setRecordCountQueryTable(tableName);
		//setRecordCountSuffixBuffer(selectStatement);

		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
