package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.FailedIntallationStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 安装失败统计
 * 该统计有两种情况，一种是预约开户安装，一种是正常用户添加业务帐户安装
 * author     ：Jason.Zhou 
 * date       : 2006-3-16
 * description:
 * @author 250713z
 * @update by chaoqiu on 2007-08-07 按操作机构统计取工单里的CreateOpID统计,不关联到T_CustServiceInteraction
 */
public class FailedInstallationStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private FailedIntallationStatDAO dao = null;
		private static final Class clazz = FailedInstallationStatListHandler.class;
		final private String tableName = " T_JobCard ";

		public FailedInstallationStatListHandler(){
			dao=new FailedIntallationStatDAO();
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
			StringBuffer selectBuff=new StringBuffer(); 
			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			StringBuffer sqlTableNew=new StringBuffer();
			StringBuffer sqlWhereNew=new StringBuffer();
			
			sqlShow.append("select jobcard.WorkResultReason as subName, count(jobcard.JobCardID) as amount");
			sqlTable.append(" from T_JobCard jobcard");
			sqlWhere.append(" where jobcard.JobType='"+CommonConstDefinition.JOBCARD_TYPE_INSTALLATION+
					"' and jobcard.status in('"+CommonConstDefinition.JOBCARD_STATUS_FAIL+
					"','"+CommonConstDefinition.JOBCARD_STATUS_TERMINAL+"')");
			sqlGroup.append(" Group by jobcard.WorkResultReason");

			sqlTableNew.append(" from T_JobCard jobcard");
			sqlWhereNew.append(" where jobcard.JobType='"+CommonConstDefinition.JOBCARD_TYPE_INSTALLATION+
					"' and jobcard.status in('"+CommonConstDefinition.JOBCARD_STATUS_FAIL+
					"','"+CommonConstDefinition.JOBCARD_STATUS_TERMINAL+"')");
			
			//SpareStr1:统计方式
			String id = "0";
			if("O".equals(dto.getSpareStr1())){
				//选择了组织机构，要按照组织机构下的分支统计
				if(!(dto.getSpareStr3()==null||"".equals(dto.getSpareStr3())))
				{
					id = dto.getSpareStr3();
				}
				sqlShow.append(orgShowNewByCsi);
				sqlTable.append(getOrgTableNewByCsi(id));
				sqlWhere.append(" and jobcard.CreateOpID=org.sonid");
				sqlGroup.append(",org.id,org.name");
				
				sqlTableNew.append(getOrgTableNewByCsi(id));
				sqlWhereNew.append(" and jobcard.CreateOrgID=org.sonid");
			}
			//按区域统计
			else if("D".equals(dto.getSpareStr1())){
				//选择了区域，要按照区域下的分支统计
				if(!(dto.getSpareStr4()==null||"".equals(dto.getSpareStr4())))
				{
					id = dto.getSpareStr4();
				}
				sqlShow.append(distShowNew);
				sqlTable.append(getDistTableNew(id));
				sqlWhere.append(distWhereNew).append(" and jobCard.custid=cust.customerid");
				sqlGroup.append(",dist.id,dist.name");
				
				sqlTableNew.append(getDistTableNew(id).replaceAll(",t_customer cust", ",T_NewCustomerInfo custinfo"));
				sqlWhereNew.append(distWhereNew.replaceAll("cust.ADDRESSID", "custinfo.FromAddressID"))
				.append(" and jobCard.custid=custinfo.CustID")
				.append(" and custinfo.CustID=0");
			}
				
			//SpareStr2:客户类型
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2()))){
				if(sqlTable.indexOf(",t_customer cust")<1){
					sqlTable.append(",t_customer cust");
					sqlWhere.append(" and jobCard.custid=cust.customerid");
				}
				sqlWhere.append(" and cust.CustomerType='" + dto2.getSpareStr2() +"'");
				
				if(sqlTableNew.indexOf("T_NewCustomerInfo custinfo")<1){
					sqlTableNew.append(",T_NewCustomerInfo custinfo");
					sqlWhereNew.append(" and jobCard.custid=custinfo.CustID");
				}
				sqlWhereNew.append(" and custinfo.Type='" + dto2.getSpareStr2() +"'");
			}
						
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null){
				sqlWhere.append(" and jobCard.dt_create>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhereNew.append(" and jobCard.dt_create>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null){
				sqlWhere.append(" and jobCard.dt_create<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhereNew.append(" and jobCard.dt_create<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}	
			//SpareTime3:预约上门时间1
			if(dto2.getSpareTime3()!=null){
				sqlWhere.append(" and jobCard.PreferedDate>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhereNew.append(" and jobCard.PreferedDate>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime4:预约上门时间2
			if(dto2.getSpareTime4()!=null){
				sqlWhere.append(" and jobCard.PreferedDate<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhereNew.append(" and jobCard.PreferedDate<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			
			selectBuff.append(sqlShow).append(sqlTable).append(sqlWhere);
			selectBuff.append(sqlGroup);
			
			
			selectBuff.append(" union ");
			selectBuff.append(sqlShow).append(sqlTableNew).append(sqlWhereNew);
			selectBuff.append(sqlGroup);
			
	
			
			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(tableName);
			//设置当前数据查询sql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(selectBuff,dto.getSpareStr1()));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
