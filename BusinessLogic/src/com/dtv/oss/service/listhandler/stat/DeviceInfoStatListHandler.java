package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class DeviceInfoStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = DeviceInfoStatListHandler.class;

		public DeviceInfoStatListHandler(){
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
			
			begin.append("select device.DeviceModel id,'' name,device.Status subName,count(device.DeviceID) amount ");
			begin.append(" from T_TerminalDevice device ");

			selectStatement.append(" where 1=1 " );

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
			
			selectStatement.append(" group by device.DeviceModel,device.Status,device.deviceclass ");
			appendOrderByString(selectStatement);
			
			setRecordDataQueryBuffer(begin.append(selectStatement));
		}
		
		private void appendOrderByString(StringBuffer selectStatement) {
			String orderByString = dto.getOrderField();
			String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
			if ((orderByString == null) || orderByString.trim().equals(""))
				selectStatement.append(" order by device.deviceclass asc,device.devicemodel asc");
			else
				selectStatement.append(" order by device." + orderByString+ orderByAscend);
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
