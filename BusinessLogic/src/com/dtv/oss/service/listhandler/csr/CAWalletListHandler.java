package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CAWalletDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.batch.BankDeductionHeaderListHandler;

public class CAWalletListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto = null;
	private CAWalletDAO dao = null;
	private static final Class clazz = BankDeductionHeaderListHandler.class;
	final private String tableName = " T_CaWallet ";
	
	public CAWalletListHandler(){
		this.dao=new CAWalletDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		StringBuffer selectStatement = new StringBuffer(128);
		
		begin.append("select * from " + tableName);
		selectStatement.append(" where 1=1 ");
		
		//SpareStr1ҵ���ʻ�ID
		if(dto.getSpareStr1()!=null && dto.getSpareStr1().trim().length()>0)
			makeSQLByIntField("ServiceAccountId", Integer.parseInt(dto.getSpareStr1()), selectStatement);
		
		selectStatement.append(" and scserialno in "+
								"("+
										"select td.serialno "+
										" from t_terminaldevice td "+
										" left join t_customerproduct cp on td.deviceid =cp.deviceid "+
                                       	" left join t_serviceaccount sc on sc.serviceaccountid =cp.serviceaccountid " +
                                       	" where cp.serviceaccountid="+Integer.parseInt(dto.getSpareStr1())+" and cp.status<>'C' " +
								")");
		
		appendOrderByString(selectStatement);
		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		
			selectStatement.append(" order by walletId desc");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}