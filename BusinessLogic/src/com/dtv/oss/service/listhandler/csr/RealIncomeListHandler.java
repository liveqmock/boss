package com.dtv.oss.service.listhandler.csr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.customer.RealIncomeAndFeeCountWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.ExtraDAO;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;

/**
 * 合计实施的实收明细及分类合计
 * author     ：Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class RealIncomeListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = RealIncomeListHandler.class;
	private List rowCountList =new ArrayList();
	public RealIncomeListHandler(){
		dao=new GenericImpDAO(rowCountList);
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
		if (dto.getSpareStr7().equals("0")){             // 查询方式
		    executeSearchForRealIncome(dao, false,true);
		} else if (dto.getSpareStr7().equals("1")){      // 打印方式
			executeSearchForRealIncome(dao, true,true);
		} else if (dto.getSpareStr7().equals("2")){      // 导出明细方式
			executeSearchForRealIncome(dao, false,false);	
		}
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		StringBuffer tempTableStatement = new StringBuffer(128);
		String tableName = " T_ACCOUNTITEM item,t_Custserviceinteraction csi  ";
		String tableName2="  t_paymentrecord pay ";
		String tableName3="   T_ACCOUNTITEM item,t_jobcard job   ";
		String tableName4="  T_ACCOUNTITEM item,t_customerproblem prob  ";
		//如果区域不选择关联该操作员所属单位管辖的区域
		if(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())){
			tableName=tableName+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
			                   +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                   +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 "
			                   +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id) cust";
			tableName2=tableName2+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
			                   +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid =" 
			                   +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 "
			                   +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id) cust";
			tableName3=tableName3+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
			                   +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                   +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 "
			                   +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id) cust";
			tableName4=tableName4+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
			                   +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                   +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 "  
			                   +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id) cust" ;                              
		
		}else{
			tableName=tableName+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
			                   +"  from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 "
                               +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id ) cust " ;
            tableName2=tableName2+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
                                 +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 "
                                 +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id ) cust ";
            tableName3=tableName3+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
                                 +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 "
                                 +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id ) cust";
            tableName4=tableName4+",(select cust1.name,SP_GETDETAILDISTRICT(addr.districtId) distName,addr.detailAddress,cust1.customertype,cust1.customerid "
                                 +" from t_customer cust1,t_address addr,(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 "
                                 +"  where  cust1.addressid =addr.addressid and addr.districtid =dir1.id ) cust";
        }
			
		//查询和表关联语句(受理单)
		tempTableStatement.append(" Select cust.name, item.custid,cust.distName,cust.detailAddress,item.acctid ,item.createtime ,");
	    tempTableStatement.append(" (Select Value  From t_commonsettingdata Where Name='SET_F_BRFEETYPE' And Key=item.feetype) As feetype ,(Select acctitemtypename From t_acctitemtype Where acctitemtypeid =item.acctitemtypeid) acctitemtypeid ,item.Value   from " + tableName);
		tempTableStatement.append("  where 1=1 and item.custid =cust.customerid and item.refertype = 'M' ");
		tempTableStatement.append(" And item.referid = csi.id ");
		tempTableStatement.append(" And (csi.paymentstatus = 'D' or csi.paymentstatus='R') ");
		tempTableStatement.append(" And item.feetype <>'P' ");
		
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
			makeSQLByStringField(" cust.customertype",dto.getSpareStr4(),tempTableStatement);
		}
		if(!(dto.getSpareStr18()==null || "".equals(dto.getSpareStr18())))
			makeSQLByStringField("item.feetype",dto.getSpareStr18(),tempTableStatement);
		//SpareStr2:客户证号
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("item.CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),tempTableStatement);
		//SpareStr3:帐户号
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("item.ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),tempTableStatement);
		//SpareStr8:帐目类型
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByStringField("item.ACCTITEMTYPEID",dto.getSpareStr8(),tempTableStatement);
		//SpareTime1:创建起始时间
		if(dto.getSpareTime1()!=null)
			tempTableStatement.append(" and item.CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建截止时间
		if(dto.getSpareTime2()!=null)
			tempTableStatement.append(" and item.CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr19:收款人operatorid
		if(!(dto.getSpareStr19()==null || "".equals(dto.getSpareStr19())))
			tempTableStatement.append(" and item.OpId ="+dto.getSpareStr19());
		//SpareStr6:组织
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
			tempTableStatement.append(" and item.orgid in (select t11.orgid from t_organization t11 connect by prior t11.orgid =t11.parentorgid start with t11.orgid ="
					                 +dto.getSpareStr6()+ ")");
		}
		//票据编号
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9()))){
			tempTableStatement.append(" and csi.id in (select pd.referid from t_paymentrecord pd where pd.refertype ='M' and pd.fapiaono ='"+dto.getSpareStr9()+"') ");
		}
        		
		tempTableStatement.append(" union  all ");
		
		//查询和表关联语句(工单)
		tempTableStatement.append(" Select cust.name,item.custid,cust.distName,cust.detailAddress, item.acctid ,item.createtime ,");
	    tempTableStatement.append("	(Select Value  From t_commonsettingdata Where Name='SET_F_BRFEETYPE' And Key=item.feetype) As feetype ,(Select acctitemtypename From t_acctitemtype Where acctitemtypeid =item.acctitemtypeid) acctitemtypeid ,item.Value   from " + tableName3);
		tempTableStatement.append("  where 1=1 and item.custid =cust.customerid and item.refertype = 'J' ");
		tempTableStatement.append(" And item.referid = job.jobcardid ");
		tempTableStatement.append(" And job.paystatus = 'D' ");
		
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
			makeSQLByStringField(" cust.customertype",dto.getSpareStr4(),tempTableStatement);
		}
		if(!(dto.getSpareStr18()==null || "".equals(dto.getSpareStr18())))
			makeSQLByStringField("item.feetype",dto.getSpareStr18(),tempTableStatement);
		//SpareStr2:客户证号
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("item.CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),tempTableStatement);
		//SpareStr3:帐户号
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("item.ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),tempTableStatement);
		//SpareStr8:帐目类型
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByStringField("item.ACCTITEMTYPEID",dto.getSpareStr8(),tempTableStatement);
		//SpareTime1:创建起始时间
		if(dto.getSpareTime1()!=null)
			tempTableStatement.append(" and item.CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建截止时间
		if(dto.getSpareTime2()!=null)
			tempTableStatement.append(" and item.CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr19:收款人operatorid
		if(!(dto.getSpareStr19()==null || "".equals(dto.getSpareStr19())))
			tempTableStatement.append(" and item.OpId ="+dto.getSpareStr19());
		//SpareStr6:组织
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
			tempTableStatement.append(" and item.orgid in (select t11.orgid from t_organization t11 connect by prior t11.orgid =t11.parentorgid start with t11.orgid ="
	                 +dto.getSpareStr6()+ ")");
		}
        // 票据编号
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9()))){
			tempTableStatement.append(" and job.jobcardid in (select pd.referid from t_paymentrecord pd where pd.refertype ='J' and pd.fapiaono ='"+dto.getSpareStr9()+"') ");
		}
        
		tempTableStatement.append(" union all  ");
		
		//查询和表关联语句(报修)
		tempTableStatement.append(" Select cust.name,item.custid,cust.distName,cust.detailAddress, item.acctid ,item.createtime ,");
		tempTableStatement.append(" (Select Value  From t_commonsettingdata Where Name='SET_F_BRFEETYPE' And Key=item.feetype) As feetype ,(Select acctitemtypename From t_acctitemtype Where acctitemtypeid =item.acctitemtypeid) acctitemtypeid ,item.Value   from " + tableName4);
		tempTableStatement.append("  where 1=1 and item.custid =cust.customerid and item.refertype = 'P' ");
		tempTableStatement.append(" And item.referid = prob.id ");
		tempTableStatement.append(" And prob.status = 'D' ");
		
		if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
			makeSQLByStringField(" cust.customertype",dto.getSpareStr4(),tempTableStatement);
		}
		if(!(dto.getSpareStr18()==null || "".equals(dto.getSpareStr18())))
			makeSQLByStringField("item.feetype",dto.getSpareStr18(),tempTableStatement);
		//SpareStr2:客户证号
		if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
			makeSQLByIntField("item.CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),tempTableStatement);
		//SpareStr3:帐户号
		if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
			makeSQLByIntField("item.ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),tempTableStatement);
		//SpareStr8:帐目类型
		if(!(dto.getSpareStr8()==null || "".equals(dto.getSpareStr8())))
			makeSQLByStringField("item.ACCTITEMTYPEID",dto.getSpareStr8(),tempTableStatement);
		//SpareTime1:创建起始时间
		if(dto.getSpareTime1()!=null)
			tempTableStatement.append(" and item.CREATETIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:创建截止时间
		if(dto.getSpareTime2()!=null)
			tempTableStatement.append(" and item.CREATETIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareStr19:收款人operatorid
		if(!(dto.getSpareStr19()==null || "".equals(dto.getSpareStr19())))
			tempTableStatement.append(" and item.OpId ="+dto.getSpareStr19());
		//SpareStr6:组织
		if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
			tempTableStatement.append(" and item.orgid in (select t11.orgid from t_organization t11 connect by prior t11.orgid =t11.parentorgid start with t11.orgid ="
	                 +dto.getSpareStr6()+ ")");
		}
        // 票据编号
		if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9()))){
			tempTableStatement.append(" and prob.id in (select pd.referid from t_paymentrecord pd where pd.refertype ='P' and pd.fapiaono ='"+dto.getSpareStr9()+"') ");
		}
       	
		//费用类型
		if(dto.getSpareStr18()==null || "".equals(dto.getSpareStr18())){
			
			tempTableStatement.append(" union all  ");
			
			tempTableStatement.append(" Select cust.name, pay.custid,cust.distName,cust.detailAddress, pay.acctid , pay.createtime ,");
		    tempTableStatement.append(" (Select Value  From t_commonsettingdata Where Name='SET_F_PAYMENTRECORDTYPE' And Key=pay.paytype) As feetype,(Select Value  From t_commonsettingdata Where Name='SET_F_PAYMENTRECORDTYPE' And Key=pay.paytype)  As accitemtypeid ,pay.amount As Value from " + tableName2);
			tempTableStatement.append(" where 1=1 and pay.custid =cust.customerid ");
			if(!(dto.getSpareStr4()==null || "".equals(dto.getSpareStr4()))){
				makeSQLByStringField("cust.customertype",dto.getSpareStr4(),tempTableStatement);
			}
			//SpareStr2:客户证号
			if(!(dto.getSpareStr2()==null || "".equals(dto.getSpareStr2())))
				makeSQLByIntField("pay.CUSTID",Integer.valueOf(dto.getSpareStr2()).intValue(),tempTableStatement);
			//SpareStr3:帐户号
			if(!(dto.getSpareStr3()==null || "".equals(dto.getSpareStr3())))
				makeSQLByIntField("pay.ACCTID",Integer.valueOf(dto.getSpareStr3()).intValue(),tempTableStatement);
			
			//SpareTime1:创建起始时间
			if(dto.getSpareTime1()!=null)
				tempTableStatement.append(" and pay.PAYMENTTIME>=to_timestamp('").append(dto.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime2:创建截止时间
			if(dto.getSpareTime2()!=null)
				tempTableStatement.append(" and pay.PAYMENTTIME<=to_timestamp('").append(dto.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareStr19:收款人operatorid
			if(!(dto.getSpareStr19()==null || "".equals(dto.getSpareStr19())))
				tempTableStatement.append(" and pay.OpId ="+dto.getSpareStr19());
			//SpareStr6:组织
			if(!(dto.getSpareStr6()==null || "".equals(dto.getSpareStr6()))){
				tempTableStatement.append(" and pay.orgid in (select t11.orgid from t_organization t11 connect by prior t11.orgid =t11.parentorgid start with t11.orgid ="
		                 +dto.getSpareStr6()+ ")");
			}
			
			tempTableStatement.append(" and ((pay.paytype='P' Or pay.paytype='N' Or pay.paytype='RR')");
			tempTableStatement.append(" Or ");
			tempTableStatement.append(" ( pay.paytype='C' And pay.adjustmentflag='Y' ");
			tempTableStatement.append(" And Exists (Select adjust.seqno From t_accountadjustment adjust Where adjust.seqno=pay.adjustmentno And adjust.ReferRecordType='C')");
			tempTableStatement.append(" ) )");	
            //票据编号
			if(!(dto.getSpareStr9()==null || "".equals(dto.getSpareStr9()))){
				tempTableStatement.append(" and pay.fapiaono ='"+dto.getSpareStr9()+"' ");
			}
		}
        
		
        // 设置构造取得当前查询总纪录数的sql
	//	setRecordCountQueryTable("("+tempTableStatement.toString()+")");  因为用了提高查询代码，故不用该代码了. modify by david.Yang
		setSumByGroupQuerySQL("Select acctitemtypeid ,Sum(value), Count(*) count From (" + tempTableStatement.toString()+")  Group  By acctitemtypeid");
		tempTableStatement.append(" Order By createtime Desc ");
		
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append("select rownum mynum, xxx.* from (" + tempTableStatement.toString()+") xxx "));
	}
	/**
	 * 支持查询时可以进行分类和计用的
	 * 
	 * @param dao
	 * @param isWrap
	 * @param isCount
	 * @throws ListHandlerException
	 */
	protected void executeSearchForRealIncome(GenericImpDAO dao, boolean isExtra,boolean isSumByGroup) throws ListHandlerException {
	   try{
		  if (!isExtra){
             if (isSumByGroup){
               dao.setFrom(getFrom());
               dao.setTo(getTo());
               dao.setSpeedFlag(true);
             }else{
        	   dao.setSpeedFlag(false);
             }
		     executeSearch(dao);
		     setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
          } else{
             setList(new ArrayList());
          }
		} catch (Exception e) {
			 LogUtility.log(RealIncomeListHandler.class, LogLevel.WARN,
						"executeSearch", e);
				throw new ListHandlerException(e.getMessage());
		}
		
		if (isSumByGroup) {
			try {
				if (getSumByGroupQuerySQL() == null || "".equals(getSumByGroupQuerySQL())) {
					LogUtility
							.log(RealIncomeListHandler.class, LogLevel.WARN,
									"in SumByGroup executeSearch method query criteria required...");
					throw new ListHandlerException(
							"data query criteria required...");
				}

				ExtraDAO extraDAO = new ExtraDAO();
				LogUtility.log(RealIncomeListHandler.class, LogLevel.DEBUG,
						"in SumByGroup executeSearch method data count query start :" + getSumByGroupQuerySQL());
				HashMap extraMap = extraDAO.executeSelectForSumByGroup(getSumByGroupQuerySQL()); 
				setSumByGroupHashMap(extraMap);
				double extraObj =0;
				Iterator mapIterator=extraMap.keySet().iterator();
			    String key="";
			    RealIncomeAndFeeCountWrap value=null;
				while(mapIterator.hasNext()){
					key=(String)mapIterator.next();
					value=(RealIncomeAndFeeCountWrap)extraMap.get(key);
					extraObj =extraObj +value.getValue();
				}
				setExtraObj(new Double(extraObj));

				LogUtility.log(RealIncomeListHandler.class, LogLevel.DEBUG,
						"in SumByGroup executeSearch method data count query end");
			} catch (Exception e) {
				LogUtility.log(RealIncomeListHandler.class, LogLevel.WARN,
						"executeSearch", e);
				throw new ListHandlerException(e.getMessage());
			}
		}
	}
	
/*  comments by david.Yang
	protected String wrapSQL(String strSql) {
		String strWrapSql ="";
		if  (outFlag){
			strWrapSql = "select * from (select rownum mynum, xxx.* from ("
				+ strSql + ") xxx ) ";
			return strWrapSql;
		}else{
			return super.wrapSQL(strSql);
		}
	}
*/
}
