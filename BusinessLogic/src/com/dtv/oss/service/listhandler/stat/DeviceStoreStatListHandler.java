package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class DeviceStoreStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = DeviceStoreStatListHandler.class;

		public DeviceStoreStatListHandler(){
			dao=new CommonStatDAO();
		}
		
		public void setCriteria(Object dto1) throws ListHandlerException {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
			if (dto1 instanceof CommonQueryConditionDTO)
				this.dto = (CommonQueryConditionDTO)dto1;
			else {
				LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
				throw new ListHandlerException("the dto type is not proper...");
			}
			//构造查询字符串
			constructSelectQueryString(dto);
			
			//执行数据查询
			executeSearch(dao, false, false);

		}
		
		private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
			StringBuffer begin = new StringBuffer();
			StringBuffer selectStatement=new StringBuffer();
			
		
			begin.append("select depot.DepotID id,depot.DepotName name,device.DeviceModel subName,count(device.DeviceID) amount ");
			begin.append(" from T_TerminalDevice device,T_Depot depot ");

			selectStatement.append(" where device.AddressID=depot.DepotID " );
			
			makeSQLByStringField("device.Status", CommonConstDefinition.DEVICE_STATUS_STOCK, selectStatement);
			makeSQLByStringField("device.AddressType", CommonConstDefinition.DEVICE_ADDRESSTYPE_DEPOT, selectStatement);
		
		
			
			
			//SpareStr1:流转类型
			//makeSQLByStringField("tran.Action", dto2.getSpareStr1(), selectStatement);
			//SpareStr2:设备类型
			//makeSQLByStringField("device.DeviceClass", dto2.getSpareStr2(), selectStatement);
			//SpareTime1:创建时间1
			//if(dto2.getSpareTime1()!=null)
			//	selectStatement.append(" and tran.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime2:创建时间2
			//if(dto2.getSpareTime2()!=null)
			//	selectStatement.append(" and tran.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			
			selectStatement.append(" group by depot.DepotID,depot.DepotName,device.DeviceModel ");
			
			setRecordDataQueryBuffer(begin.append(selectStatement));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
