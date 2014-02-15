package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CSIDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BookProductCSIListHandler extends ValueListHandler {
	private CSIDAO dao=null;
	private final String tableName=" t_custserviceinteraction a ";
	private CommonQueryConditionDTO dto=null;
	
	public BookProductCSIListHandler(){
		dao = new CSIDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
	    constructSelectQueryString(dto);
	    //执行数据查询
	    executeSearch(dao,true,true);
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto) {
		StringBuffer begin = new StringBuffer();
		begin.append("select * ");
		begin.append(" from " + tableName);
			
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		//受理单号
		if(dto.getSpareStr1()!=null){
			makeSQLByIntField(" a.id",Integer.parseInt(dto.getSpareStr1()),selectStatement);			
		}
		makeSQLByIntField(" a.customerid",Integer.parseInt(dto.getSpareStr4()),selectStatement);
		//状态
		makeSQLByStringField(" a.status",dto.getStatus(),selectStatement);
		//业务帐号
		if(dto.getSpareStr2()!=null){
			selectStatement.append(" and a.id in (select csiid from t_csicustproductinfo where referserviceaccountid='")
			.append(dto.getSpareStr2()).append("')");
		}
		//设备序列号
		if(dto.getSpareStr3()!=null){
			selectStatement.append(" and a.id in (select csiid from t_csicustproductinfo where referdeviceid in (select deviceid from t_terminaldevice where serialno='")
			.append(dto.getSpareStr3()).append("'))");
		}
		selectStatement.append(" and a.status ='"+CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT+"'");
		//预约时间
		if(dto.getBeginTime()!=null&&dto.getEndTime()!=null){
			selectStatement.append(" and a.createtime between to_timestamp('").append(dto.getBeginTime()).append("','yyyy-MM-dd-HH24:MI:SSxff') and to_timestamp('").append(dto.getEndTime()).append("','yyyy-MM-dd-HH24:MI:SSxff')");
		}else if(dto.getBeginTime() != null)
			selectStatement.append(" and a.createtime >= to_timestamp('").append(dto.getBeginTime()).append("','yyyy-MM-dd-HH24:MI:SSxff') ");
		else if(dto.getEndTime() != null)
			selectStatement.append(" and a.createtime <= to_timestamp('").append(dto.getEndTime()).append("','yyyy-MM-dd-HH24:MI:SSxff') ") ;
		if(dto.getSpareBeginTime()!=null&&dto.getSpareEndTime()!=null){
			selectStatement.append(" and a.scheduletime between to_timestamp('").append(dto.getSpareBeginTime()).append("','yyyy-MM-dd-HH24:MI:SSxff') and to_timestamp('").append(dto.getSpareEndTime()).append("','yyyy-MM-dd-HH24:MI:SSxff')");
		}else if(dto.getSpareBeginTime() != null)
			selectStatement.append(" and a.scheduletime >= to_timestamp('").append(dto.getSpareBeginTime()).append("','yyyy-MM-dd-HH24:MI:SSxff')");
		else if(dto.getSpareEndTime() != null)
			selectStatement.append(" and a.scheduletime <= to_timestamp('").append(dto.getSpareEndTime()).append("','yyyy-MM-dd-HH24:MI:SSxff')");
		selectStatement.append(" and a.type=").append("'").append(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_BP).append("'");
		selectStatement.append(" order by a.id desc");
		
//		设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
}
