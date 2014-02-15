package com.dtv.oss.service.listhandler.csr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CustServiceAccountDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.batch.BankDeductionHeaderListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.util.DBUtil;

public class CAWalletServiceInteractionListHandler  extends ValueListHandler {
	private CommonQueryConditionDTO dto = null;
	private CustServiceAccountDAO dao = null;
	private static final Class clazz = BankDeductionHeaderListHandler.class;
	final private String tableName = " T_SERVICEACCOUNT SC "+
									"left JOIN T_CUSTOMERPRODUCT CP ON SC.SERVICEACCOUNTID = CP.SERVICEACCOUNTID "+
									"left JOIN T_TERMINALDEVICE TER ON TER.DEVICEID = CP.DEVICEID "+
									"left join t_Cawallet ca on ca.serviceaccountid = sc.serviceaccountid "+
									"left join t_cawalletdefine def on def.cawalletcode = ca.cawalletcode "+
									"WHERE TER.DEVICEMODEL IN ("+getDeviceModelList()+") ";
	
	public CAWalletServiceInteractionListHandler(){
		this.dao=new CustServiceAccountDAO();
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
		
		begin.append("SELECT distinct SC.SERVICEACCOUNTID SERVICEACCOUNTID," +
							"SC.SERVICEID SERVICEID," +
							"SC.CUSTOMERID CUSTOMERID," +
							"SC.USERID USERID," +
							"SC.USER_TYPE USER_TYPE," +
							"SC.SUBSCRIBERID SUBSCRIBERID," +
							"SC.CREATETIME CREATETIME," +
							"SC.STATUS STATUS," +
							"SC.ServiceCode ServiceCode," +
							"SC.DT_CREATE DT_CREATE," +
							"SC.DT_LASTMOD DT_LASTMOD," +
							"SC.SERVICEACCOUNTNAME SERVICEACCOUNTNAME," +			
							"TER.SERIALNO DESCRIPTION "+
							"FROM " + tableName );
		//selectStatement.append(" where 1=1 ");
		
		selectStatement.append("AND CP.deviceid>0 "); //Ӳ��
		selectStatement.append("AND SC.STATUS = 'N' "); //����״̬
		selectStatement.append("AND CP.STATUS <> 'C' "); //�ͻ���Ʒ��Ϊȡ��
		//SpareStr5:�ͻ�֤��
		makeSQLByIntField("SC.customerID", Integer.parseInt(dto.getSpareStr5()), selectStatement);
		//SpareStr1ҵ���ʻ�ID
		if(dto.getSpareStr1()!=null && dto.getSpareStr1().trim().length()>0)
			makeSQLByIntField("SC.serviceaccountid", Integer.parseInt(dto.getSpareStr1()), selectStatement);
		//SpareStr2:�豸���к�
		makeSQLByStringField("TER.SERIALNO", dto.getSpareStr2(), selectStatement);
		//SpareStr3:СǮ����� walletId
		if(dto.getSpareStr3()!=null && dto.getSpareStr3().trim().length()>0)
			makeSQLByIntField("ca.walletId", Integer.parseInt(dto.getSpareStr3()), selectStatement);
		//SpareStr4:СǮ������
		makeSQLByStringField("def.CaWalletCode", dto.getSpareStr4(), selectStatement);

		appendOrderByString(selectStatement);

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {

		selectStatement.append(" order by SC.serviceaccountid desc");

	}

	//�õ��豸(DeviceModel),�ö��ŷָ����� ��'Nagra_���ܿ�','NDS���ܿ�'��
	private String getDeviceModelList() {
		String strReturn = "";
		String deviceModel = "";
		Connection conn = DBUtil.getConnection();
		Statement sqlStrStmt = null;
		ResultSet rs = null;
		String strSql = "SELECT DEVICEMODELLIST FROM T_CAWALLETDEFINE WHERE STATUS = 'V'";

		try {
			LogUtility.log(CAWalletServiceInteractionListHandler.class, LogLevel.DEBUG,
					"getDeviceModelList", strSql);
			sqlStrStmt =conn.createStatement();
			rs =sqlStrStmt.executeQuery(strSql);
			String[] array;
			while(rs.next()) {
				
				deviceModel = rs.getString("DEVICEMODELLIST");
				if(!"".equals(deviceModel)){
					array = deviceModel.split(",");
					if(array.length>=2){     //���������豸
						for(int i=0;i<array.length;i++){
							strReturn = strReturn + "'"+array[i]+"',";
						}
					}
					else{
						strReturn = strReturn + "'"+deviceModel+"',";
					}
				}
			}
		} catch (SQLException e) {
			LogUtility.log(CAWalletServiceInteractionListHandler.class, LogLevel.WARN,
					"getDeviceModelList", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					LogUtility.log(CAWalletServiceInteractionListHandler.class, LogLevel.WARN,
							"getDeviceModelList", e);
				}
			}
			if (sqlStrStmt != null) {
				try {
					sqlStrStmt.close();
				} catch (SQLException e) {
					LogUtility.log(CAWalletServiceInteractionListHandler.class, LogLevel.WARN,
							"getDeviceModelList", e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LogUtility.log(CAWalletServiceInteractionListHandler.class, LogLevel.WARN,
							"getDeviceModelList", e);
				}
			}
		}
		
		if(!"".equals(strReturn))
		strReturn = strReturn.substring(0,strReturn.length()-1);
		return strReturn;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "Nagra_���ܿ�,NDS���ܿ�";
		String[] array=test.split(",");
		//System.out.println(getDeviceModelList());
	}

}

