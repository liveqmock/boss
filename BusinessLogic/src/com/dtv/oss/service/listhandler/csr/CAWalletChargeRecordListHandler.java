package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CAWalletChargeRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.batch.BankDeductionHeaderListHandler;

public class CAWalletChargeRecordListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto = null;
	private CAWalletChargeRecordDAO dao = null;
	private static final Class clazz = BankDeductionHeaderListHandler.class;
	final private String tableName = " T_CaWalletChargeRecord ";
	
	public CAWalletChargeRecordListHandler(){
		this.dao=new CAWalletChargeRecordDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//构造查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		
		begin.append("select * from " + tableName);
		selectStatement.append(" where 1=1 ");
		
		//SpareStr1小钱包序号 walletId
		if(dto.getSpareStr1()!=null && dto.getSpareStr1().trim().length()>0)
			makeSQLByIntField("walletID", Integer.parseInt(dto.getSpareStr1()), selectStatement);
		//SpareStr2:支付方式
		if(dto.getSpareStr2()!=null && dto.getSpareStr2().trim().length()>0)
			makeSQLByIntField("mopId", Integer.parseInt(dto.getSpareStr2()), selectStatement);
		//SpareStr3:设备序列号
		makeSQLByStringField("scSerialNo", dto.getSpareStr3(), selectStatement);
		//SpareTime1:充值日期起始时间
		if(dto.getSpareTime1()!=null)
			selectStatement.append(" and createTime>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:充值日期截止时间
		if(dto.getSpareTime2()!=null)
			selectStatement.append(" and createTime<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//SpareStr3:设备序列号
		makeSQLByStringField("scSerialNo", dto.getSpareStr3(), selectStatement);
		
		//SpareStr4业务帐户ID
		if(dto.getSpareStr4()!=null && dto.getSpareStr4().trim().length()>0)
			makeSQLByIntField("serviceAccountId", Integer.parseInt(dto.getSpareStr4()), selectStatement);
		
		appendOrderByString(selectStatement);

		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");

		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by seqNo desc");
		else
			selectStatement.append(" order by " + dto.getOrderField()+ orderByAscend);
		
		orderByAscend = null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
