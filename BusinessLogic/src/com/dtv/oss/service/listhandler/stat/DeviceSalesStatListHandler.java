package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.stat.DeviceSalesStatDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.dao.statistics.DeviceSalesStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class DeviceSalesStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private DeviceSalesStatDAO dao = null;
	private static final Class clazz = DeviceStoreStatListHandler.class;

	public DeviceSalesStatListHandler(){
		dao=new DeviceSalesStatDAO();
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
		StringBuffer selectStatement=new StringBuffer();
		          
		selectStatement.append(" select name orgname,devicemodel,sum(value) value,count(*) amount,id ");
		selectStatement.append(" from ");
		selectStatement.append(" ( ");
		selectStatement.append(" select csi.createorgid createorgid,ai.value value,td.devicemodel devicemodel from T_CustServiceInteraction csi ");
		selectStatement.append(" left join t_csicustproductinfo cpi on cpi.csiid=csi.id ");
		selectStatement.append(" left join t_customerproduct cp on cp.psid=cpi.custproductid ");
		selectStatement.append(" left join t_terminaldevice td on td.deviceid=cp.deviceid ");
		selectStatement.append(" left join t_accountitem ai on csi.id=ai.referid ");
		selectStatement.append(" left join t_customer cust on cust.customerid=csi.customerid ");
		selectStatement.append(" where csi.type in ('UO','BU','OS','OB') and csi.Status = 'S' and td.deviceid <> 0 and ai.psid=cpi.custproductid and ai.psid<>0 and ai.value>=0 and cpi.referdeviceid<>0 and ai.feetype='D' and ai.refertype='M' ");
//		SpareStr4:客户类型
		if(dto2.getSpareStr4()!=null)	
		selectStatement.append(" and cust.customertype='"+dto2.getSpareStr4()+"' ");        //model
//		SpareStr5:设备型号
		if(dto2.getSpareStr5()!=null)	
		selectStatement.append(" and td.devicemodel='"+dto2.getSpareStr5()+"' ");        //model
//		SpareTime1:创建时间1
		if(dto2.getSpareTime1()!=null)
			selectStatement.append(" and csi.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
//		SpareTime2:创建时间2
		if(dto2.getSpareTime2()!=null)
			selectStatement.append(" and csi.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		selectStatement.append(" ) ");
		selectStatement.append(" devicestat,");
		
		selectStatement.append(" (select distinct * ");
		selectStatement.append(" from (select distinct sonorg.orgid sonid,sonorg.orgname sonname,parentorg.orgid id,parentorg.orgname name ");
		selectStatement.append(" from (select orgid, orgname ");
		selectStatement.append(" from t_organization ");
		selectStatement.append(" where parentorgid = "+dto2.getSpareStr3()+") parentorg,t_organization sonorg ");      //orgid
		selectStatement.append(" CONNECT BY PRIOR sonorg.orgid = sonorg.Parentorgid ");
		selectStatement.append(" start with sonorg.orgid = parentorg.orgid) ");
		selectStatement.append(" where id in ");
		selectStatement.append(" (select orgid ");
		selectStatement.append(" from t_organization ");
		selectStatement.append(" CONNECT BY PRIOR Parentorgid = orgid ");
		selectStatement.append(" start with orgid = sonid) ");
		selectStatement.append(" union all ");
		selectStatement.append(" select orgid sonid,orgname sonname,orgid id,orgname name ");
		selectStatement.append(" from t_organization ");
		selectStatement.append(" where orgid = "+dto2.getSpareStr3()+") ");              //orgid
		selectStatement.append(" org ");
		
		selectStatement.append(" where devicestat.CreateOrgID in ( org.sonid ) ");
		selectStatement.append(" group by id,name,devicemodel ");
		selectStatement.append(" order by id,devicemodel ");

		
		setRecordDataQueryBuffer(selectStatement);
	}
	
/**
 * @param args
 */
public static void main(String[] args) {
	// TODO Auto-generated method stub

}

}
