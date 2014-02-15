package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class SaledProductPackageStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private CommonStatDAO dao = null;
	private static final Class clazz = SaledProductPackageStatListHandler.class;
	final private String tableName = " T_ProductPackage ";

	public SaledProductPackageStatListHandler(){
		dao=new CommonStatDAO();
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
		
		StringBuffer sqlShow=new StringBuffer();
		StringBuffer sqlTable=new StringBuffer();
		StringBuffer sqlWhere=new StringBuffer();
		StringBuffer sqlGroup=new StringBuffer();

		sqlShow.append("select dist.ID as id,dist.Name as name,to_char(package.PackageID) as subName, count(package.PackageID) as amount ");
		if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
			sqlTable.append(" from T_ProductPackage package,T_Customer cust,T_CustomerProduct cp,t_address addr, " +
					distTable(dto2.getSpareStr4()));
		}else{
			sqlTable.append(" from T_ProductPackage package,T_Customer cust,T_CustomerProduct cp,t_address addr, " +
					ssqlTable_1);
		}
		sqlWhere.append(" where cp.CustomerID=cust.customerid  and cp.ReferPackageID=package.PackageID" +
				" and cp.Status <> '" + CommonConstDefinition.CUSTOMERPRODUCTP_STATUS_CANCEL +"' and cust.ADDRESSID = addr.ADDRESSID  " );
		sqlGroup.append(" Group by package.PackageID, dist.ID,dist.Name ");
		
		//SpareStr4:区域
//		if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
			sqlWhere.append(ssqlWhere);
//		}
//			else
//				sqlWhere.append(" and " + " dist.id  in( select ID  from t_districtsetting " +
//						" CONNECT BY PRIOR  id =BelongTo start with id = 1 ) ");
			
		//SpareStr5:用户类型
		if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5())))
			sqlWhere.append(" and cust.CustomerType='" + dto2.getSpareStr5() +"' ");
		
		//SpareTime1:创建时间1
		if(dto2.getSpareTime1()!=null)
			sqlWhere.append(" and package.dt_create>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建时间2
		if(dto2.getSpareTime2()!=null)
			sqlWhere.append(" and package.dt_create<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:完成时间1
		if(dto2.getSpareTime3()!=null)
			sqlWhere.append(" and cp.CreateTime>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:完成时间2
		if(dto2.getSpareTime4()!=null)
			sqlWhere.append(" and cp.CreateTime<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
		//SpareStr2:产品包状态
	    //历史产品包
		if("T".equals(dto2.getSpareStr2()))
			sqlWhere.append(" and package.Status in ('" + CommonConstDefinition.PACKAGESTATUS_CANCEL +"','" + CommonConstDefinition.PACKAGESTATUS_TERMINAL +"') ");
		else if("N".equals(dto2.getSpareStr2()))
			sqlWhere.append(" and package.Status ='" + CommonConstDefinition.PACKAGESTATUS_NORMAL +"' ");

		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
