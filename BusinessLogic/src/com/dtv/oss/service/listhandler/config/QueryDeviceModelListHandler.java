package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.DeviceModelDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
/**
 * �豸�ͺŲ�ѯ
 * @author 260327h
 *
 */
public class QueryDeviceModelListHandler extends ValueListHandler {
	private DeviceModelDAO dao = null;

	private final String tableName = "t_devicemodel t ";

	private DeviceModelDTO dto = null;

	public QueryDeviceModelListHandler() {
		this.dao = new DeviceModelDAO();
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof DeviceModelDTO)
			this.dto = (DeviceModelDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		//ִ�����ݲ�ѯ
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(DeviceModelDTO dto) {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"dto info\n"+dto.toString());
		StringBuffer begin = new StringBuffer();
		begin.append("select t.* ");
		begin.append(" from " + tableName);

		StringBuffer selectStatement = new StringBuffer();

		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	  	selectStatement.append(" where 1=1 ");
	  	//�����豸�ͺ�����
    	makeSQLByStringField("t.DEVICEMODEL",dto.getDeviceModel(),selectStatement,"like");
    	//�����豸�ͺ�����
    	makeSQLByStringField("t.DEVICECLASS",dto.getDeviceClass(),selectStatement);
    	//�����豸�ͺ�״̬
    	makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
    	//�����豸��Ӧ��
        makeSQLByIntField("t.PROVIDERID",dto.getProviderID(),selectStatement);
		
		selectStatement.append(" order by t.DEVICEMODEL asc");
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

}
