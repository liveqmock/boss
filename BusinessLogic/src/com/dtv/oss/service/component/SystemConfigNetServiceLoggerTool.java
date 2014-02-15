package com.dtv.oss.service.component;

import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.util.SystemLogRecorder;

public class SystemConfigNetServiceLoggerTool {

	public static void logCreateOperator(String remoteHostAddress,
			int operatorID, String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "创建" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public static void logUpdateOperator(String remoteHostAddress,
			int operatorID, String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "修改" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public static void logDeleteOperator(String remoteHostAddress,
			int operatorID, String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "删除" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public static SystemConfigNetServiceLoggerTool getInstance(
			String objectName, String remoteHostAddress, int operatorID) {
		return new SystemConfigNetServiceLoggerTool(objectName,
				remoteHostAddress, operatorID);
	}

	private String objectName;

	private String remoteHostAddress;

	private int operatorID;

	public int getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}

	public String getRemoteHostAddress() {
		return remoteHostAddress;
	}

	public void setRemoteHostAddress(String remoteHostAddress) {
		this.remoteHostAddress = remoteHostAddress;
	}

	public SystemConfigNetServiceLoggerTool(String objectName,
			String remoteHostAddress, int operatorID) {
		this.objectName = objectName;
		this.remoteHostAddress = remoteHostAddress;
		this.operatorID = operatorID;
	}

	public void logCreate(String dataDesc) throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "创建" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public void logUpdate(String dataDesc) throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "修改" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public void logDelete(String dataDesc) throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "删除" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public void logCreate(String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "创建"+ objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public void logUpdate(String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "修改" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

	public void logDelete(String objectName, String dataDesc)
			throws ServiceException {

		SystemLogRecorder.createSystemLog(remoteHostAddress, operatorID, 0,
				SystemLogRecorder.LOGMODULE_NET, "删除" + objectName,
				dataDesc, SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

	}

}
