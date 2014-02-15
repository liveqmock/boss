package com.dtv.oss.service.listhandler.stat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class OpenCustomerListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private CommonStatDAO dao = null;
	private static final Class clazz = OpenCustomerListHandler.class;
	final private String tableName = " T_CustServiceInteraction ";

	public OpenCustomerListHandler(){
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
		

		sqlTable.append(" from T_CustServiceInteraction csi");
		if(!(dto2.getSpareStr10()==null || "".equals(dto2.getSpareStr10())))
		{
			sqlWhere.append(" where csi.Type ='"+dto2.getSpareStr10()+"'");
		}else
		{
		sqlWhere.append(" where csi.Type in ('" +
			 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OS + "','" +
			 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OB + "','" +
			 CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_CO + "')" );
		}
		
		//SpareStr2:分类方式 
	    //按代理商统计
		if("A".equals(dto2.getSpareStr2())){
			sqlShow.append("select cpl.OrgID subName,count(cpl.OrgID) amount");
			sqlTable.append(",T_CSIProcessLog cpl");
			sqlTable.append(",(select orgID,orgsubtype from t_organization) orga");
			//加入该条件保证一条受理记录只得到一条日志记录
			sqlWhere.append(" and cpl.Action = '"+CommonConstDefinition.CSIPROCESSLOG_ACTION_NEW+"'");
			sqlWhere.append(" and csi.ID=cpl.CSIID");
			sqlWhere.append(" and cpl.OrgID=orga.orgID");
			sqlWhere.append(" and orga.orgsubtype='"+CommonConstDefinition.ORG_ORGSUBTYPE_S+"'");
			sqlGroup.append(" Group by cpl.OrgID");
		}
		//按来源渠道统计
		else if("O".equals(dto2.getSpareStr2())){
			sqlShow.append("select cust.OpenSourceType subName,count(cust.OpenSourceType) amount");
			sqlGroup.append(" Group by cust.OpenSourceType");
		}
		//按安装方式统计
		else if("I".equals(dto2.getSpareStr2())){
			sqlShow.append("select csi.InstallationType subName,count(csi.InstallationType) amount");
			sqlGroup.append(" Group by csi.InstallationType");
		}
		//按受理状态统计
		else if("S".equals(dto2.getSpareStr2())){
			sqlShow.append("select csi.Status subName,count(csi.Status) amount");
			sqlGroup.append(" Group by csi.Status");
		}
		else
			throw new ListHandlerException("参数错误：分类方式错误！");
		
		
		String id = "0";
		//SpareStr1:统计方式
		//按组织机构统计 从操作员受理入手
		
		if("O".equals(dto2.getSpareStr1())){
			//选择了组织机构，要按照组织机构下的分支统计
			if(!(dto2.getSpareStr3()==null||"".equals(dto2.getSpareStr3())))
			{
				id = dto2.getSpareStr3();
			}
			sqlShow.append(orgShowNewByCsi);
			sqlTable.append(getOrgTableNewByCsi(id));
			sqlWhere.append(orgWhereNewByCsi);
			sqlGroup.append(orgGroupNewByCsi);
			if("O".equals(dto2.getSpareStr2()))
			{
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and csi.CustomerID=cust.customerid");
			}
		}
		//按区域统计
		else if("D".equals(dto2.getSpareStr1())){
			//选择了区域，要按照区域下的分支统计
			if(!(dto2.getSpareStr4()==null||"".equals(dto2.getSpareStr4())))
			{
				id = dto2.getSpareStr4();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew).append(" and csi.CustomerID=cust.customerid");
			sqlGroup.append(distGroupNew);
		}
		else 
			throw new ListHandlerException("参数错误：统计方式未知！");
		
		
		
			
		//SpareStr5:用户类型
		if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5())))
		{
			if(sqlTable.indexOf(",t_customer cust")<1){
				sqlTable.append(",t_customer cust");
				sqlWhere.append(" and csi.CustomerID=cust.customerid");
			}
			sqlWhere.append(" and cust.CustomerType='" + dto2.getSpareStr5() +"'");
		}
		//SpareStr6:安装方式
		if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6())))
		{
			sqlWhere.append(" and csi.InstallationType='" + dto2.getSpareStr6() + "'");
		}
		//SpareStr7:受理状态
		if(!(dto2.getSpareStr7()==null || "".equals(dto2.getSpareStr7())))
		{
			sqlWhere.append(" and csi.Status='" + dto2.getSpareStr7() + "'");
		}
		//SpareStr8:来源渠道
		if(!(dto2.getSpareStr8()==null || "".equals(dto2.getSpareStr8())))
		{
			sqlWhere.append(" and cust.OpenSourceType='" + dto2.getSpareStr8() + "'");
		}
		//SpareStr9:来源渠道ID
		if(!(dto2.getSpareStr9()==null || "".equals(dto2.getSpareStr9())))
		{
			sqlWhere.append(" and cust.OpenSourceTypeID=" + dto2.getSpareStr9());
		}
		
		//SpareTime1:创建时间1
		if(dto2.getSpareTime1()!=null)
		{
			sqlWhere.append(" and csi.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime2:创建时间2
		if(dto2.getSpareTime2()!=null)
		{
			sqlWhere.append(" and csi.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//完成时间
		if(dto2.getSpareTime3()!=null || dto2.getSpareTime4()!=null)
		{
			if(sqlTable.indexOf(",T_CSIProcessLog cpl") == -1)
			sqlTable.append(",T_CSIProcessLog cpl");
			sqlWhere.append(" and csi.ID=cpl.CSIID and cpl.Action='"+CommonConstDefinition.CSIPROCESSLOG_ACTION_SUCCESS+"'");
			sqlWhere.append(" and csi.id in (select cpl.csiid from t_csiprocesslog cpl where cpl.action in ('S', 'F')");
		}
		//SpareTime3:完成时间1
		if(dto2.getSpareTime3()!=null)
		{
			
			sqlWhere.append(" and cpl.actiontime>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//SpareTime4:完成时间2
		if(dto2.getSpareTime4()!=null)
		{
			sqlWhere.append(" and cpl.actiontime<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//完成时间
		if(dto2.getSpareTime3()!=null || dto2.getSpareTime4()!=null)
		{
			sqlWhere.append(")");
		}
	
		StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup);
		//地域部分
		//replaceAll(distShow, ",dist.id id,dist.name ||'(*)' name").
		//replaceAll(distTable, ",t_address addr,t_districtsetting dist").
		//replaceAll(distWhere, " cust.ADDRESSID = addr.ADDRESSID and addr.DISTRICTID = dist.id and addr.DISTRICTID ="+id).
		//组织机构部分：
		//replaceAll(orgShow, ",org.id id,org.name ||'(*)' name").
		//replaceAll(orgTable, ",t_address addr,T_OrgGovernedDistrict orgDsict,t_organization org").
		//replaceAll(orgWhere, " and cust.addressid=adds.addressid and adds.districtid=orgDsict.Districtid" +
		//		" and orgDsict.id=org.orgid and org.orgid="+id);
		
		

		
		
		
		//设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable(tableName);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,dto2.getSpareStr1()));
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String bb = "(bb)ccddff";
//		StringBuffer aa = new StringBuffer();
//		aa.append(bb);
//		System.out.println(bb.substring(bb.indexOf("df")));
//		
//		
//		aa.toString();
//		System.out.println(aa.toString().replaceAll("", "hehe"));
		//System.out.println(aa.replaceAll("aa", "dd").replaceAll("bb", "ee"));

	}

}
