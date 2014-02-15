package com.dtv.oss.service.listhandler.stat;

import java.text.DecimalFormat;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.statistics.CommonStatDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CustomerProductPackageStatListHandler extends ValueListHandler {

		private CommonQueryConditionDTO dto = null;
		private CommonStatDAO dao = null;
		private static final Class clazz = CustomerProductPackageStatListHandler.class;
		final private String tableName = " (select customerid,serviceaccountid, ReferPackageID PackageID,min(CreateTime) CreateTime," +
				"case when (count(psid)=count(case when status='C' then 1 else null end)) then 'I' else 'V' end status " +
				"from T_CustomerProduct group by customerid,serviceaccountid,ReferPackageID) custPackage";

		public CustomerProductPackageStatListHandler(){
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
			//�����ѯ�ַ���
			constructSelectQueryString(dto);
			
			//ִ�����ݲ�ѯ
			executeSearch(dao, false, false);

		}
		
		private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {

			StringBuffer sqlShow=new StringBuffer();
			StringBuffer sqlTable=new StringBuffer();
			StringBuffer sqlWhere=new StringBuffer();
			StringBuffer sqlGroup=new StringBuffer();
			
			
			sqlShow.append("select custPackage.PackageID subname,count(custPackage.PackageID) amount");
			sqlTable.append(" from"+tableName);	
			sqlWhere.append(" where 1=1");
			sqlGroup.append(" group by custPackage.PackageID");
			
		
			
			
			//Ĭ��"1"���մ������ͳ��,ѡ�����Ϻ���Ҳ���մ������ͳ��
			String id = "0";
			//ѡ��������Ҫ���������µķ�֧ͳ��
			if(!(dto.getSpareStr1()==null||"".equals(dto.getSpareStr1())))
			{
				id = dto.getSpareStr1();
			}
			sqlShow.append(distShowNew);
			sqlTable.append(getDistTableNew(id));
			sqlWhere.append(distWhereNew).append(" and custPackage.CUSTOMERID = cust.CUSTOMERID");
			sqlGroup.append(distGroup);
			
		
			//SpareStr2:�ͻ�����
			if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			{
				sqlWhere.append(" and cust.Customertype='" + dto2.getSpareStr2() +"'");
				
			}
			//SpareStr3:��������
			if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			{
				sqlTable.append(",T_ServiceAccount seracc");
				sqlWhere.append(" and custPackage.ServiceAccountID = seracc.ServiceAccountID");
				sqlWhere.append(" and seracc.User_Type='" + dto2.getSpareStr3() +"'");
				
			
			}
			//SpareStr4:�ͻ���Ʒ��״̬
			if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4())))
			{
				sqlWhere.append(" and custPackage.Status='" + dto2.getSpareStr4() +"'");
			
			}
			//SpareStr5:ͳ�ƵĲ�Ʒ
			if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5()) || ",".equals(dto2.getSpareStr5())))
			{
				sqlWhere.append(" and custPackage.PackageID in (" + dto2.getSpareStr5() +") ");
			
			}
			//SpareStr6:ͳ�Ʋ�Ʒ�������
			if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6()))){
				sqlTable.append(",T_ProductPackage package");
				sqlWhere.append(" and custPackage.PackageID = package.PackageID");
				sqlWhere.append(" and package.PackageClass='" + dto2.getSpareStr6() +"'");
			}
			
			//SpareTime1:����ʱ��1
			if(dto2.getSpareTime1()!=null)
			{
				sqlWhere.append(" and custPackage.CreateTime>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			//SpareTime2:����ʱ��2
			if(dto2.getSpareTime2()!=null)
			{
				sqlWhere.append(" and custPackage.CreateTime<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			}
			StringBuffer sqlStr = sqlShow.append(sqlTable).append(sqlWhere).append(sqlGroup)
			.append(" order by id desc");
			
			//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
			setRecordCountQueryTable(tableName);
			//���õ�ǰ���ݲ�ѯsql
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
