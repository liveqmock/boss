/*
 * Created on 2005-11-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.logistics;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.logistics.DeviceDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author 241115c
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeviceListHandler extends ValueListHandler {

	public DeviceListHandler() {
		this.dao = new DeviceDAO();
	}

	private CommonQueryConditionDTO dto = null;

	private DeviceDAO dao = null;

	private String selectCriteria = "";

	String tableName = "T_TerminalDevice a";
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG,
					"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		// 构造查询字符串
		constructSelectQueryString(dto);
		// 执行数据查询
		executeSearch(dao, true, true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {

		StringBuffer begin = new StringBuffer();
		begin.append("select a.* from " + tableName);
		StringBuffer selectStatement = new StringBuffer(128);

		// append additional conditions to where clause
		selectStatement.append(" where 1=1 ");

		// 1.有设备ID吗？
		if (dto.getSpareStr1() != null)
			selectStatement.append(" and a.DEVICEID=" + dto.getSpareStr1());

		// 2.有设备类型吗？
		if (dto.getSpareStr2() != null)
			selectStatement.append(" and a.DEVICECLASS='" + dto.getSpareStr2()+ "' ");

		// 3.有设备型号吗？
		if (dto.getSpareStr3() != null)
			selectStatement.append(" and a.DEVICEMODEL='" + dto.getSpareStr3() + "'");
	
		// 4.有设备序列号范围吗？
		// if BeginStr is set and EndStr is NOT set, do accurate query
		if (HelperCommonUtil.StringHaveContent(dto.getBeginStr())
				&& (!HelperCommonUtil.StringHaveContent(dto.getEndStr()))) {
			selectStatement.append(" and a.SERIALNO ='" + dto.getBeginStr() + "'");
		} 
		else {
			if (dto.getBeginStr() != null) {
				selectStatement.append(" and a.SERIALNO >='" + dto.getBeginStr() + "'");
			}
			if (dto.getEndStr() != null) {
				selectStatement.append(" and a.SERIALNO <='" + dto.getEndStr() + "'");
			}
		}

		// 5.有设备状态吗？
		if (dto.getSpareStr5() != null) {
			String sStatusVal = dto.getSpareStr5().replaceAll(";", "','");
			selectStatement.append(" and a.STATUS in ('" + sStatusVal + "')");
		}

		// 6.有仓库吗？
		if ((dto.getSpareStr6() != null))
			selectStatement.append(" and a.addresstype='D' and a.addressid =" + dto.getSpareStr6());

		// 7.是否二手？
		if ((dto.getSpareStr7() != null))
			selectStatement.append(" and a.USEFLAG ='" + dto.getSpareStr7()+ "'");

		// 8.有货架吗？
		if ((dto.getSpareStr8() != null))
			selectStatement.append(" and a.PALLETID =" + dto.getSpareStr8());
		
		// 9.有地址类型吗?
		if ((dto.getSpareStr9() != null))
			selectStatement.append(" and a.ADDRESSTYPE ='" + dto.getSpareStr9() + "'");
		//18.有地址类型吗?
		if ((dto.getSpareStr18() != null))
			selectStatement.append(" and a.ADDRESSTYPE ='" + dto.getSpareStr18() + "'");

		//所属区域
		if (HelperCommonUtil.StringHaveContent(dto.getSpareStr16())){
			//————由于组织的ORGID和客户的AdressID的大小相差甚远，不用单独来区分设备的AddressType，modify by jason.zhou———————
			//组织的AddressID
			selectStatement.append(" and ( ");
			selectStatement.append(" ( a.addressID in (select orgid from t_organization connect by prior " +
					"orgid=parentorgid start with orgid=" + dto.getSpareStr16() + ")) ");
			//客户的AddressID
			selectStatement.append(" or ( a.addressID in (select addressID from  T_ADDRESS where districtid in " +
					"(select Id from T_DISTRICTSETTING connect by prior ID=BELONGTO  start with ID=" 
					+ dto.getSpareStr16() + ")))");
			selectStatement.append(" ) ");
			//end modify 2007-2-27
		}
		
		// 10.有地址ID吗？
		if ((dto.getSpareStr10() != null))
			selectStatement.append(" and a.ADDRESSID =" + dto.getSpareStr10());
		// 11.配对有吗？
		if ((dto.getSpareStr11() != null))
			selectStatement.append(" and a.matchflag ='" + dto.getSpareStr11() + "'");

		// 12.预授权有吗？
		if ((dto.getSpareStr12() != null))
			selectStatement.append(" and a.preauthorization ='" + dto.getSpareStr12() + "'");
		
		// 有批号
		if ((dto.getSpareStr15() != null))
			selectStatement.append(" and a.BatchID in(Select Batchid From T_DeviceTransition Where batchNO='" + dto.getSpareStr15() + "') ");
		
		//设备用途
		if((dto.getSpareStr17() != null))
		{

			//selectStatement.append(" and (a.purposestrlist is null ");
			selectStatement.append(" and(1=0 ");
			String[] purposeArray=dto.getSpareStr17().split(",");
			for(int i = 0;i< purposeArray.length;i++)
			{
				selectStatement.append(" or instr(a.purposestrlist,'"+purposeArray[i]+",')>0 ");
			}
			selectStatement.append(") ");
		}
		
		//txtMacAddress、txtInterMacAddress
		makeSQLByStringField("a.MACADDRESS",dto.getSpareStr13(),selectStatement,"like");
		makeSQLByStringField("a.INTERMACADDRESS",dto.getSpareStr14(),selectStatement,"like");
		
		//System.out.println("________===="+selectStatement.toString());
		LogUtility.log(DeviceListHandler.class, LogLevel.DEBUG, selectStatement.toString());
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

		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals(""))
			selectStatement.append(" order by a.deviceid desc");
		else
			selectStatement.append(" order by a." + dto.getOrderField() + orderByAscend);

		orderByAscend = null;
	}
}