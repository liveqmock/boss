package com.dtv.oss.service.listhandler.stat;

import java.text.DecimalFormat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatForMultiDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CustomerProductStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatForMultiDAO dao = null;
		private static final Class clazz = CustomerProductStatListHandler.class;
		final private String tableName = " T_CustomerProduct ";

		public CustomerProductStatListHandler(){
			dao=new CommonStatForMultiDAO();
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
			
			StringBuffer sqlShow1=new StringBuffer();
			StringBuffer sqlTable1=new StringBuffer();
			StringBuffer sqlWhere1=new StringBuffer();
			StringBuffer sqlGroup1=new StringBuffer();
			
			sqlShow.append("select cp.ProductID secSubName,count(cp.psid) amount,proser.ServiceID subName");
			sqlTable.append(" from T_CustomerProduct cp,T_ProductToService proser");	
			sqlWhere.append(" where 1=1 and cp.deviceid=0");
			sqlWhere.append(" and cp.ProductID = proser.ProductID and cp.CUSTOMERID = cust.CUSTOMERID");
			sqlGroup.append(" group by cp.ProductID,proser.ServiceID");
			
			sqlShow1.append("select cp.ProductID secSubName,count(cp.psid) amount,0  subName");
			sqlTable1.append(" from T_CustomerProduct cp");	
			sqlWhere1.append(" where 1=1 and cp.deviceid>0");
			sqlWhere1.append(" and cp.CUSTOMERID = cust.CUSTOMERID");
			sqlGroup1.append(" group by cp.ProductID,'hardware' ");
			
			
			
			//默认"1"按照大的区域统计,选择了上海市也按照大的区域统计
			String id = "0";
			//选择了区域，要按照区域下的分支统计
			if(!(dto.getSpareStr1()==null||"".equals(dto.getSpareStr1())))
			{
				id = dto.getSpareStr1();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew);
			sqlGroup.append(distGroup);
			
			sqlShow1.append(distShowNew);
			sqlTable1.append(getDistTableNew(id));
			sqlWhere1.append(distWhereNew);
			sqlGroup1.append(distGroup);
		
			//SpareStr2:客户类型
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			{
				sqlWhere.append(" and cust.Customertype='" + dto2.getSpareStr2() +"'");
				sqlWhere1.append(" and cust.Customertype='" + dto2.getSpareStr2() +"'");
			}
			//SpareStr3:订户类型
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			{
				sqlTable.append(",T_ServiceAccount seracc");
				sqlWhere.append(" and cp.ServiceAccountID = seracc.ServiceAccountID");
				sqlWhere.append(" and seracc.User_Type='" + dto2.getSpareStr3() +"'");
				
				sqlTable1.append(",T_ServiceAccount seracc");
				sqlWhere1.append(" and cp.ServiceAccountID = seracc.ServiceAccountID");
				sqlWhere1.append(" and seracc.User_Type='" + dto2.getSpareStr3() +"'");
			}
			//SpareStr4:客户产品状态
			if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4())))
			{
				sqlWhere.append(" and cp.Status='" + dto2.getSpareStr4() +"'");
				sqlWhere1.append(" and cp.Status='" + dto2.getSpareStr4() +"'");
			}
			//SpareStr5:统计的产品
			if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5()) || ",".equals(dto2.getSpareStr5())))
			{
				sqlWhere.append(" and cp.productid in (" + dto2.getSpareStr5() +") ");
				sqlWhere1.append(" and cp.productid in (" + dto2.getSpareStr5() +") ");
			}
			//SpareStr6:统计产品的分类，由于目前只有两种：硬件和软件，所以只要通过t_cutomerproduct.deviceid是否为0来区分软硬件
			if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6()))){
				if(CommonConstDefinition.PRODUCTCLASS_HARD.equals(dto2.getSpareStr6()))
				{
					sqlWhere.append(" and cp.deviceid>0 ");
					sqlWhere1.append(" and cp.deviceid>0 ");
				}
				else if(CommonConstDefinition.PRODUCTCLASS_SOFTWARE.equals(dto2.getSpareStr6()))
				{
					sqlWhere.append(" and cp.deviceid=0 ");
					sqlWhere1.append(" and cp.deviceid=0 ");
				}
			}
			
			//SpareTime1:创建时间1
			if(dto2.getSpareTime1()!=null)
			{
				sqlWhere.append(" and cp.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere1.append(" and cp.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:创建时间2
			if(dto2.getSpareTime2()!=null)
			{
				sqlWhere.append(" and cp.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
				sqlWhere1.append(" and cp.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup)
			.append(" union all ").append(sqlShow1).append(sqlTable1).append(sqlWhere1).append(sqlGroup1)
			.append(" order by id desc");
			
			System.out.println(sqlStr.toString());

			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(tableName);
			//设置当前数据查询sql
			setRecordDataQueryBuffer(wrapDistrictOrOrgOrderForStat(sqlStr,"D"));
		}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sPattern = "0.0000";
		  DecimalFormat df=new DecimalFormat(sPattern);

	        //System.out.print(df.format(0.00777));

	}

}
