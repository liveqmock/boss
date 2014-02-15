package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class ApplyForRepairStatListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private CommonStatDAO dao = null;
	private static final Class clazz = ApplyForRepairStatListHandler.class;
	final private String tableName = " T_CustServiceInteraction ";

	public ApplyForRepairStatListHandler(){
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
		StringBuffer selectBuff=new StringBuffer();
		StringBuffer sqlShow=new StringBuffer();
		StringBuffer sqlTable=new StringBuffer();
		StringBuffer sqlWhere=new StringBuffer();
		StringBuffer sqlGroup=new StringBuffer();
		
		sqlTable.append(" from T_CustomerProblem prob");
		sqlWhere.append(" where 1=1");
		//SpareStr2:分类方式
		//按维修状态统计
		if("A".equals(dto2.getSpareStr2())){
			sqlShow.append("select prob.Status subName,count(prob.Status) amount");
			sqlGroup.append(" group by prob.Status ");
		}
	  	//按故障级别统计
	  	else if("B".equals(dto2.getSpareStr2())){
	  		sqlShow.append("select prob.ProblemLevel subName,count(prob.ProblemLevel) amount");
			sqlGroup.append(" group by prob.ProblemLevel ");
	  	}
	    //按报修类型统计
	    else if("C".equals(dto2.getSpareStr2())){
	    	sqlShow.append("select prob.ProblemCategory subName,count(prob.ProblemCategory) amount");
			sqlGroup.append(" group by prob.ProblemCategory ");
	    }
		//按故障原因统计
		else if("D".equals(dto2.getSpareStr2())){
			sqlShow.append("select jobCard.TroubleSubType subName,count(jobCard.TroubleSubType) amount");
			sqlTable.append(",T_JobCard jobCard ");
			sqlWhere.append(" and jobCard.JobCardID=prob.ReferJobCardID ");
			sqlGroup.append(" group by jobCard.TroubleSubType ");
		}
	    //按故障类型统计
	    else if("E".equals(dto2.getSpareStr2())){
	    	sqlShow.append("select jobCard.TroubleType subName,count(jobCard.TroubleType) amount");
			sqlTable.append(",T_JobCard jobCard ");
			sqlWhere.append(" and jobCard.JobCardID=prob.ReferJobCardID ");
			sqlGroup.append(" group by jobCard.TroubleType ");
	    }
		//按解决手段统计
		else if("F".equals(dto2.getSpareStr2())){
			sqlShow.append("select jobCard.ResolutionType subName,count(jobCard.ResolutionType) amount");
			sqlTable.append(",T_JobCard jobCard ");
			sqlWhere.append(" and jobCard.JobCardID=prob.ReferJobCardID");
			sqlGroup.append(" group by jobCard.ResolutionType ");
		}
		else
			throw new ListHandlerException("分类未知，无法进行统计！");

		//SpareStr1:统计方式
		String id = "0";
		//按受理机构统计 按照受理维修的机构统计，而不是按照维修的客户所在的组织机构统计(根据bug235) update by chaoqiu 20070605 begin
		if("O".equals(dto2.getSpareStr1())){
			
			//SpareStr3:组织
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
				id=dto2.getSpareStr3();
			sqlShow.append(orgShowNew);
			//sqlTable.append(getOrgTableNew(id));
			sqlTable.append(getOrgTableNewByCsi(id));
			//sqlWhere.append(orgWhereNew).append(" and cust.customerid=prob.CustID");
			sqlWhere.append(" and prob.CreateOrgID=org.sonid ");
			sqlGroup.append(orgGroupNew);
			
			//update by chaoqiu 20070605 end
			
			
			
		}
		//按区域统计 
		else if("D".equals(dto2.getSpareStr1())){
			if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4())))
				id=dto2.getSpareStr4();
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew).append(" and cust.customerid=prob.CustID");
			sqlGroup.append(distGroupNew);
		}
		else 
			throw new ListHandlerException("参数错误：统计方式未知！");
		
		//SpareStr5:报修处理状态
		if(dto2.getSpareStr5()!=null){
			sqlWhere.append(" and prob.Status='").append(dto2.getSpareStr5()).append("'");
		}
		//SpareTime1:创建时间1
		if(dto2.getSpareTime1()!=null){
			sqlWhere.append(" and prob.CreateDate>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime2:创建时间2
		if(dto2.getSpareTime2()!=null){
			sqlWhere.append(" and prob.CreateDate<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime1:完成时间1
		if(dto2.getSpareTime3()!=null){
			sqlWhere.append(" and prob.dt_lastmod>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime2:完成时间2
		if(dto2.getSpareTime4()!=null){
			sqlWhere.append(" and prob.dt_lastmod<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		
		
		
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		
		
		selectBuff.append(sqlShow).append(sqlTable).append(sqlWhere).append(sqlGroup);
		
		//设置当前数据查询sql
		setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(selectBuff,dto2.getSpareStr1()));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
