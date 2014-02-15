package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.DeviceTransDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * <p>
 * Title: BOSS_P5 for SCN
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SHDV
 * </p>
 * 
 * @author Chen jiang
 * @version 2.0
 */

public class DeviceTransListHandler extends ValueListHandler {

	/**
	 * 构造函数，设定DAO对象为DeviceDAO
	 */
	public DeviceTransListHandler() {
		this.dao = new DeviceTransDAO();
	}

	/**
	 * 设备DTO
	 */
	private CommonQueryConditionDTO dto;

	/**
	 * 查询使用的DAO对象
	 */
	private DeviceTransDAO dao = null;

	/**
	 * 查询条件
	 */
	private String selectCriteria = "";

	/**
	 * 查询的表名
	 * 
	 * @param dto
	 * @throws com.dtv.oss.service.listhandler.ListHandlerException
	 */
	String tableName = "T_DEVICETRANSITION a";

	/**
	 * 设置设备查询条件
	 * 
	 * @param dto
	 * @throws com.dtv.oss.service.listhandler.ListHandlerException
	 */
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(DeviceTransListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	/**
	 * 检查查询条件dto，包装成sql语句
	 * 
	 * @param dto
	 * @return
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		boolean bHasPrio = false;
		StringBuffer begin = new StringBuffer();
		begin.append("select a.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);

		selectStatement.append(" where 1=1 ");
		
		// 1.有BatchID吗？
		if (dto.getSpareStr1() != null && !"".equals(dto.getSpareStr1().trim())) {
				selectStatement
						.append(" and a.BATCHID=" + dto.getSpareStr1());
		}

		// 2.有设备流转动作类型吗？
		if (dto.getSpareStr2() != null && !"".equals(dto.getSpareStr2().trim())) {
				selectStatement.append(" and a.ACTION='" + dto.getSpareStr2()
						+ "'");
		}

		// 3.有设备流出地吗？
		if (dto.getSpareStr3() != null && !"".equals(dto.getSpareStr3().trim())) {
					selectStatement.append(" and a.FROMID="
							+ dto.getSpareStr3());
		}

		// 4.有设备流出地类型吗？
		if (dto.getSpareStr4() != null && !"".equals(dto.getSpareStr4().trim())) {
				selectStatement.append(" and a.FROMTYPE ='"
						+ dto.getSpareStr4() + "'");
			
		}

		// 5.有设备流入地吗？
		if (dto.getSpareStr5() != null && !"".equals(dto.getSpareStr5().trim())) {
					selectStatement
							.append(" and a.TOID =" + dto.getSpareStr5());
				
		}

		// 6.有设备流入地类型吗？
		if (dto.getSpareStr6() != null && !"".equals(dto.getSpareStr6().trim())) {
				selectStatement.append(" and a.TOTYPE ='" + dto.getSpareStr6()
						+ "'");
			
		}

		// 7.有起始时间吗？
		if (dto.getBeginTime() != null) {
					selectStatement.append(" and a.CREATETIME >= to_timestamp('"
							+ dto.getBeginTime() + "','YYYY-MM-DD-HH24:MI:SSxff')");
		}

		// 8.有终止时间吗？
		if (dto.getEndTime() != null) {
					selectStatement.append(" and a.CREATETIME <= to_timestamp('"
							+ dto.getEndTime() + "','YYYY-MM-DD-HH24:MI:SSxff')");
		}
		// 9.有deviceId吗？
		if (dto.getSpareStr9() != null && !"".equals(dto.getSpareStr9().trim())) {
					selectStatement
							.append(" and a.BATCHID in ( select batchid from t_devicetransitiondetail where deviceid ="
									+ new Integer(dto.getSpareStr9())
											.intValue() + ")");
				
		}
		// 10.有设备序列号吗？
		if (dto.getBeginStr() != null && !"".equals(dto.getBeginStr().trim())) {
					selectStatement
							.append(" and a.BATCHID in ( select batchid from t_devicetransitiondetail where serialno ='"
									+ dto.getBeginStr() + "')");
				
		}
		//是否有批号
		if (dto.getSpareStr10() != null && !"".equals(dto.getSpareStr10().trim())) {
				selectStatement.append(" and a.BatchNo ='" + dto.getSpareStr10()
						+ "'");
			
		}
		
		//是否有状态流装原因
		if (dto.getSpareStr11() != null && !"".equals(dto.getSpareStr11().trim())) {
				selectStatement.append(" and a.StatusReason ='" + dto.getSpareStr11()
						+ "'");
			
		}
		
		LogUtility.log(DeviceTransListHandler.class, LogLevel.DEBUG,
				selectStatement.toString());
		appendOrderByString(selectStatement);
		// appendOrderByString(end);
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}

	private void appendOrderByString(StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");

		if ((dto.getOrderField() == null)
				|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by a.BATCHID desc");
		else {
			selectStatement.append(" order by a." + dto.getOrderField()
					+ orderByAscend);
		}

		orderByAscend = null;

	}
}
