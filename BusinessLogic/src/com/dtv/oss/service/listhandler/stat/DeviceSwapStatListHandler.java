package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.DeviceSwapStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.CommonUtility;

public class DeviceSwapStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;

	private DeviceSwapStatDAO dao = null;
	private String selectCriteria = "";
	private static final Class clazz = DeviceSwapStatListHandler.class;

	final private String tableName = " t_custserviceinteraction csi "+
	        						 " left join t_csicustproductinfo cpi on csi.id=cpi.csiid "+
	        						 " left join t_accountitem ai on csi.id=ai.referid "+
									 " left join t_terminaldevice olddevice on olddevice.deviceid=cpi.referdeviceid "+
									 " left join t_terminaldevice device on device.deviceid=cpi.referdestdeviceid "+
	 								 " left join t_operator operator on operator.operatorid=csi.createoperatorid "+
	                                 " left join t_customer cust on cust.customerid=csi.customerid "+
									 " left join t_csiactionreasondetail csireason on csireason.key=csi.createreason ";

	public DeviceSwapStatListHandler() {
		dao = new DeviceSwapStatDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// �����ѯ�ַ���
		constructSelectQueryString(dto);

		// ִ�����ݲ�ѯ
		executeSearch(dao, false, false);

	}

	/**
	 * Ӫҵ���շ�ͳ�� date : 2007-1-19 description: SpareStr4:�ͻ����� SpareStr2:��������
	 * SpareStr3:���ڻ��� SpareTime1:֧����ʼʱ�� SpareTime2:֧������ʱ��
	 * 
	 * @author
	 * 
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto)
			throws ListHandlerException {

		StringBuffer begin = new StringBuffer();
	    //begin.append("select csi.id id,");
		begin.append("select cust.name customername,");
		begin.append("csireason.value createreason,");
	    begin.append("olddevice.devicemodel olddevicemodel,");
	    begin.append("olddevice.serialno olddeviceid,");
	    begin.append("device.devicemodel devicemodel,");
        begin.append("device.serialno deviceid,");
	    begin.append("ai.value value,");
        begin.append("operator.operatorname operatorname,");
        begin.append("csi.workdate workdate,");
        begin.append("csi.createtime createtime from " + tableName );
        
        StringBuffer selectStatement = new StringBuffer(128);

        selectStatement.append(" where csi.type='DS' ");

		// ѡ������֯������ֻͳ����ֱ�Ӷ�Ӧ���շѼ�¼
		if (!(dto.getSpareStr3() == null || "".equals(dto.getSpareStr3()))) {
			selectStatement.append(" and csi.createorgid in ( select orgid from t_organization connect by prior orgid = parentorgid start with orgid="+ dto.getSpareStr3()+")" );
		}

		// SpareTime1:֧��ʱ��1
		if (dto.getSpareTime1() != null) {
			selectStatement.append(" and csi.workdate>=to_timestamp('").append(
					dto.getSpareTime1().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// SpareTime2:֧��ʱ��2
		if (dto.getSpareTime2() != null) {
			selectStatement.append(" and csi.workdate<=to_timestamp('").append(
					dto.getSpareTime2().toString()).append(
					"', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}

		appendOrderByString(selectStatement);
		// appendOrderByString(end);
		// ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// ���õ�ǰ���ݲ�ѯsql
		begin.append(selectStatement);
		StringBuffer sqlSel = new StringBuffer();
		sqlSel.append("select * from (select rownum id, xxx.* from (" );
		sqlSel.append(begin);
		sqlSel.append(" ) xxx )");
		setRecordDataQueryBuffer(sqlSel);
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {
		selectStatement.append(" order by csi.id desc ");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			DeviceSwapStatListHandler handler = new DeviceSwapStatListHandler();
			handler.getList();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 select csi.id id ,
	 csi.customerid customerid,
	 csi.createreason createreason,
	 (select dc.description from t_terminaldevice device left join t_deviceclass dc on device.deviceclass=dc.deviceclass where device.deviceid=cpi.referdeviceid) deviceclass,
	 (select device.serialno from t_terminaldevice device where device.deviceid=cpi.referdeviceid) deviceid,
	 (select dc.description from t_terminaldevice device left join t_deviceclass dc on device.deviceclass=dc.deviceclass where device.deviceid=cpi.referdestdeviceid) destdeviceclass,
	 (select device.serialno from t_terminaldevice device where device.deviceid=cpi.referdestdeviceid) destdeviceid,
	 ai.value value,
	 csi.createoperatorid operatorid,
	 csi.workdate workdate,
	 csi.createtime createtime
	 from t_custserviceinteraction csi 
	        left join t_csicustproductinfo cpi on csi.id=cpi.csiid
	        left join t_accountitem ai on csi.id=ai.referid
	  where csi.type='DS' and csi.createorgid
	  in ( select orgid from t_organization connect by prior orgid = parentorgid start with orgid=1 )
	  order by csi.id desc
	**/  
	
}
