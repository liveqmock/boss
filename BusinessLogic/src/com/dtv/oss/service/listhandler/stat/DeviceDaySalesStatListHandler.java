package com.dtv.oss.service.listhandler.stat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.dao.statistics.DeviceDaySalesStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;

public class DeviceDaySalesStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private DeviceDaySalesStatDAO dao = null;
	private static final Class clazz = DeviceDaySalesStatListHandler.class;
	
	private String batchNo = "";
	
	public DeviceDaySalesStatListHandler(){
		dao=new DeviceDaySalesStatDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		
		try{
			batchNo = String.valueOf(System.currentTimeMillis()) + "_" + String.valueOf(getOperatorID());
			
			//java.util.Calendar gc = GregorianCalendar.getInstance();
			//String filldate = String.valueOf(gc.get(Calendar.YEAR)) + String.valueOf(gc.get(Calendar.MONTH)+1) + String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String filldate = dateFormat.format(dto.getSpareTime1());
			
			System.out.println("batchNo:"+batchNo);
			System.out.println("filldate:"+filldate);
			BusinessUtility.callProcedureForAreaSellStat(batchNo,filldate);
			
		}catch (Exception e) {}
		
		//构造查询字符串
		constructSelectQueryString(dto);
		
		//执行数据查询
		executeSearch(dao, false, false);

	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer selectStatement=new StringBuffer();
		
		selectStatement.append(" select * ");
		selectStatement.append(" from dist_sellstat_integration ");
		selectStatement.append(" where batchNo = '"+ batchNo +"'" );

		setRecordDataQueryBuffer(selectStatement);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}