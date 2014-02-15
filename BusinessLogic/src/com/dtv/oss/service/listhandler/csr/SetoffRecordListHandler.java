package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.SetoffRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

/**
 * 支付费用记录ValueListHandler
 * author     ：Jason.Zhou 
 * date       : 2006-2-13
 * description:
 * @author 250713z
 *
 */
public class SetoffRecordListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	private SetoffRecordDAO dao = null;
	private static final Class clazz = SetoffRecordListHandler.class;

	public SetoffRecordListHandler(){
		dao=new SetoffRecordDAO();
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
		if(!(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1())))
			executeSearch(dao, false, false);
		else
			executeSearch(dao, true, true,true);

	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {

		StringBuffer selectStatement = new StringBuffer();

		//SpareStr1:序号
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			contructUnionSQL(selectStatement,dto2);
			//设置构造取得当前查询总纪录数的sql
			setRecordCountQueryTable(" ( " + selectStatement.toString() + " )");
			setRecordCountSuffixBuffer(new StringBuffer(" "));
			
			//设置当前数据查询sql
			setRecordDataQueryBuffer(selectStatement);
			return;
		}
		
		if(!(dto2.getSpareStr6()==null || "".equals(dto2.getSpareStr6())))
			contructPDFSQL(selectStatement,dto2);
		else 
			contructUnionSQL(selectStatement,dto2);
		
		//appendOrderByString(selectStatement);
		//设置构造取得当前查询总纪录数的sql
		
		StringBuffer orderSelectStatement =new StringBuffer();
		appendOrderByString(orderSelectStatement,selectStatement);	
		
		setRecordCountQueryTable(" ( " + orderSelectStatement.toString() + " ) ");
		setRecordCountSuffixBuffer(new StringBuffer(" "));
		setExtraQuerySQL("select sum(VALUE) from ( "  + orderSelectStatement.toString() + " )");
		//设置当前数据查询sql
		setRecordDataQueryBuffer(orderSelectStatement);
	}
	
	//select * from （ selectStatement ） order by的方式来排序
	private void appendOrderByString(StringBuffer orderSelectStatement, StringBuffer selectStatement) {
		String orderByAscend = (dto.getIsAsc() ? " asc" : " desc");
		
		orderSelectStatement.append("select * from (");
		orderSelectStatement.append(selectStatement);
		orderSelectStatement.append(")");

		if ((dto.getOrderField() == null)|| dto.getOrderField().trim().equals("")){
			orderSelectStatement.append(" order by SEQNO desc");
		}
		else {
			orderSelectStatement.append(" order by " + dto.getOrderField() + orderByAscend);
		}
		
		orderByAscend = null;
	}

	private void contructPDFSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2) {
		//支付关联表为支付表
		if(CommonConstDefinition.SETOFFREFERTYPE_P.equals(dto2.getSpareStr6())){
			contructPSQL(selectStatement,dto2);
		}
		//支付关联表为预存抵扣表
		else if(CommonConstDefinition.SETOFFREFERTYPE_D.equals(dto2.getSpareStr6())){
			contructDSQL(selectStatement,dto2);
		}
		//支付关联表为费用表
		else if(CommonConstDefinition.SETOFFREFERTYPE_F.equals(dto2.getSpareStr6())){
			contructFSQL(selectStatement,dto2);
		}
	}
	
	private void contructUnionSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		contructPSQL(selectStatement,dto2);
		selectStatement.append(" union ");
		
		contructDSQL(selectStatement,dto2);
		selectStatement.append(" union ");
		
		contructFSQL(selectStatement,dto2);
	}
	
	private void contructPSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		selectStatement.append("select setoff.*,fee.VALUE as feeAmount,fee.CREATETIME as feeTime,itemtype.ACCTITEMTYPENAME as feeAcctItemType, " +
				" pay.AMOUNT as paymentAmount,pay.CREATETIME as paymentTime,mop.NAME as payAcctItemType,'' as payCommonSettingValue " +
				" from T_FinanceSetoffMap setoff,T_AccountItem fee,T_PaymentRecord pay,T_MethodOfPayment mop,T_AcctItemType itemtype " +
				" where setoff.PLUSREFERID=pay.SEQNO and setoff.MINUSREFERID=fee.AI_NO and fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID and " +
				" pay.MOPID=mop.MOPID and setoff.PLUSREFERTYPE='P' ");

		//SpareStr1:序号
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			makeSQLByIntField("setoff.SeqNo",Integer.valueOf(dto2.getSpareStr1()).intValue(),selectStatement);
			return;
		}
		
		//SpareStr2:客户证号
		if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			makeSQLByIntField("setoff.CUSTID",Integer.valueOf(dto2.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:帐户号
		if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			makeSQLByIntField("setoff.ACCTID",Integer.valueOf(dto2.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:组织
		if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer ");
			selectStatement.append(" where customer.orgid in (select orgid from t_organization ");
			selectStatement.append(" where status='V' and hascustomerflag='Y' connect ");
			selectStatement.append(" by prior orgid=parentorgid start with orgid=" + dto2.getSpareStr4() + " ) )");
		}
		//SpareStr5:区域
		if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto2.getSpareStr5() + " ) )");
		}
		//SpareStr6:费用类型
		if(!(dto2.getSpareStr7()==null || "".equals(dto2.getSpareStr7())))
			makeSQLByStringField("setoff.MINUSREFERTYPE",dto2.getSpareStr7(),selectStatement);
		//SpareStr8:支付记录ID
		if(!(dto2.getSpareStr8()==null || "".equals(dto2.getSpareStr8())))
			makeSQLByIntField("setoff.PLUSREFERID",Integer.valueOf(dto2.getSpareStr8()).intValue(),selectStatement);
		//SpareStr9:帐目类型
		if(!(dto2.getSpareStr9()==null || "".equals(dto2.getSpareStr9())))
			makeSQLByStringField("fee.AcctItemTypeID",dto2.getSpareStr9(),selectStatement);
		//SpareStr10:费用记录ID
		if(!(dto2.getSpareStr10()==null || "".equals(dto2.getSpareStr10())))
			makeSQLByIntField("setoff.MinusReferID",Integer.valueOf(dto2.getSpareStr10()).intValue(),selectStatement);
		
		//SpareTime1:销帐时间1
		if(dto2.getSpareTime1()!=null)
			selectStatement.append(" and setoff.CREATETIME>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:销帐时间2
		if(dto2.getSpareTime2()!=null)
			selectStatement.append(" and setoff.CREATETIME<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:支付时间1
		if(dto2.getSpareTime3()!=null)
			selectStatement.append(" and pay.CREATETIME>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:支付时间2
		if(dto2.getSpareTime4()!=null)
			selectStatement.append(" and pay.CREATETIME<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime5:费用创建时间1
		if(dto2.getSpareTime5()!=null)
			selectStatement.append(" and fee.CREATETIME>=to_timestamp('").append(dto2.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime6:费用创建时间2
		if(dto2.getSpareTime6()!=null)
			selectStatement.append(" and fee.CREATETIME<=to_timestamp('").append(dto2.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	}
	
	private void contructDSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
	
		selectStatement.append("select setoff.*,fee.VALUE as feeAmount,fee.CREATETIME as feeTime,itemtype.ACCTITEMTYPENAME as feeAcctItemType, " +
				" pay.AMOUNT as paymentAmount,pay.CREATETIME as paymentTime,pay.PREPAYMENTTYPE as payAcctItemType, " +
				" 'SET_F_PREPAYMENTTYPE' as payCommonSettingValue " +
				" from T_FinanceSetoffMap setoff,T_AccountItem fee,T_PrePaymentDeductionRecord pay,T_AcctItemType itemtype " +
				" where setoff.PLUSREFERID=pay.SEQNO and setoff.MINUSREFERID=fee.AI_NO and fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID " +
				" and setoff.PLUSREFERTYPE='D' ");
		
		//SpareStr1:序号
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			makeSQLByIntField("setoff.SeqNo",Integer.valueOf(dto2.getSpareStr1()).intValue(),selectStatement);
			return;
		}
		
		//SpareStr2:客户证号
		if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			makeSQLByIntField("setoff.CUSTID",Integer.valueOf(dto2.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:帐户号
		if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			makeSQLByIntField("setoff.ACCTID",Integer.valueOf(dto2.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:组织
		if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer ");
			selectStatement.append(" where customer.orgid in (select orgid from t_organization ");
			selectStatement.append(" where status='V' and hascustomerflag='Y' connect ");
			selectStatement.append(" by prior orgid=parentorgid start with orgid=" + dto2.getSpareStr4() + " ) )");
		}
		//SpareStr5:区域
		if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto2.getSpareStr5() + " ) )");
		}
		//SpareStr7:费用类型
		if(!(dto2.getSpareStr7()==null || "".equals(dto2.getSpareStr7())))
			makeSQLByStringField("setoff.MinusReferType",dto2.getSpareStr7(),selectStatement);
		//SpareStr8:支付记录ID
		if(!(dto2.getSpareStr8()==null || "".equals(dto2.getSpareStr8())))
			makeSQLByIntField("setoff.PLUSREFERID",Integer.valueOf(dto2.getSpareStr8()).intValue(),selectStatement);
		//SpareStr9:帐目类型
		if(!(dto2.getSpareStr9()==null || "".equals(dto2.getSpareStr9())))
			makeSQLByStringField("fee.AcctItemTypeID",dto2.getSpareStr9(),selectStatement);
		//SpareStr10:费用记录ID
		if(!(dto2.getSpareStr10()==null || "".equals(dto2.getSpareStr10())))
			makeSQLByIntField("setoff.MinusReferID",Integer.valueOf(dto2.getSpareStr10()).intValue(),selectStatement);
		
		//SpareTime1:销帐时间1
		if(dto2.getSpareTime1()!=null)
			selectStatement.append(" and setoff.CREATETIME>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:销帐时间2
		if(dto2.getSpareTime2()!=null)
			selectStatement.append(" and setoff.CREATETIME<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:支付时间1
		if(dto2.getSpareTime3()!=null)
			selectStatement.append(" and pay.CREATETIME>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:支付时间2
		if(dto2.getSpareTime4()!=null)
			selectStatement.append(" and pay.CREATETIME<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime5:费用创建时间1
		if(dto2.getSpareTime5()!=null)
			selectStatement.append(" and fee.CREATETIME>=to_timestamp('").append(dto2.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime6:费用创建时间2
		if(dto2.getSpareTime6()!=null)
			selectStatement.append(" and fee.CREATETIME<=to_timestamp('").append(dto2.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		
	}
	
	private void contructFSQL(StringBuffer selectStatement,CommonQueryConditionDTO dto2){
		selectStatement.append("select setoff.*,fee.VALUE as feeAmount,fee.CREATETIME as feeTime,itemtype.ACCTITEMTYPENAME as feeAcctItemType, " +
				" pay.VALUE as paymentAmount,pay.CREATETIME as paymentTime,payitemtype.ACCTITEMTYPENAME as payAcctItemType, " +
				" '' as payCommonSettingValue " +
				" from T_FinanceSetoffMap setoff,T_AccountItem fee,T_AccountItem pay,T_AcctItemType itemtype,T_AcctItemType payitemtype " +
				" where setoff.PLUSREFERID=pay.AI_NO and setoff.MINUSREFERID=fee.AI_NO and fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID " +
				" and pay.ACCTITEMTYPEID=payitemtype.ACCTITEMTYPEID and setoff.PLUSREFERTYPE='F' ");
		
		//SpareStr1:序号
		if(!(dto2.getSpareStr1()==null || "".equals(dto2.getSpareStr1()))){
			makeSQLByIntField("setoff.SeqNo",Integer.valueOf(dto2.getSpareStr1()).intValue(),selectStatement);
			return;
		}
		
		//SpareStr2:客户证号
		if(!(dto2.getSpareStr2()==null || "".equals(dto2.getSpareStr2())))
			makeSQLByIntField("setoff.CUSTID",Integer.valueOf(dto2.getSpareStr2()).intValue(),selectStatement);
		//SpareStr3:帐户号
		if(!(dto2.getSpareStr3()==null || "".equals(dto2.getSpareStr3())))
			makeSQLByIntField("setoff.ACCTID",Integer.valueOf(dto2.getSpareStr3()).intValue(),selectStatement);
		//SpareStr4:组织
		if(!(dto2.getSpareStr4()==null || "".equals(dto2.getSpareStr4()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer ");
			selectStatement.append(" where customer.orgid in (select orgid from t_organization ");
			selectStatement.append(" where status='V' and hascustomerflag='Y' connect ");
			selectStatement.append(" by prior orgid=parentorgid start with orgid=" + dto2.getSpareStr4() + " ) )");
		}
		//SpareStr5:区域
		if(!(dto2.getSpareStr5()==null || "".equals(dto2.getSpareStr5()))){
			selectStatement.append(" and setoff.custid in (select customer.customerid from t_customer customer,t_address address ");
			selectStatement.append(" where customer.addressid=address.addressid and address.districtid in  ");
			selectStatement.append(" ( select id from t_DISTRICTSETTING where status='V' connect by prior id=belongto start with id=");
			selectStatement.append(dto2.getSpareStr5() + " ) )");
		}
		//SpareStr7:费用类型
		if(!(dto2.getSpareStr7()==null || "".equals(dto2.getSpareStr7())))
			makeSQLByStringField("setoff.MinusReferType",dto2.getSpareStr7(),selectStatement);
		//SpareStr8:支付记录ID
		if(!(dto2.getSpareStr8()==null || "".equals(dto2.getSpareStr8())))
			makeSQLByIntField("setoff.PLUSREFERID",Integer.valueOf(dto2.getSpareStr8()).intValue(),selectStatement);
		//SpareStr9:帐目类型
		if(!(dto2.getSpareStr9()==null || "".equals(dto2.getSpareStr9())))
			makeSQLByStringField("fee.AcctItemTypeID",dto2.getSpareStr9(),selectStatement);
		//SpareStr10:费用记录ID
		if(!(dto2.getSpareStr10()==null || "".equals(dto2.getSpareStr10())))
			makeSQLByIntField("setoff.MinusReferID",Integer.valueOf(dto2.getSpareStr10()).intValue(),selectStatement);
		
		//SpareTime1:销帐时间1
		if(dto2.getSpareTime1()!=null)
			selectStatement.append(" and setoff.CREATETIME>=to_timestamp('").append(dto2.getSpareTime1().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime2:销帐时间2
		if(dto2.getSpareTime2()!=null)
			selectStatement.append(" and setoff.CREATETIME<=to_timestamp('").append(dto2.getSpareTime2().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime3:支付时间1
		if(dto2.getSpareTime3()!=null)
			selectStatement.append(" and pay.CREATETIME>=to_timestamp('").append(dto2.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime4:支付时间2
		if(dto2.getSpareTime4()!=null)
			selectStatement.append(" and pay.CREATETIME<=to_timestamp('").append(dto2.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime5:费用创建时间1
		if(dto2.getSpareTime5()!=null)
			selectStatement.append(" and fee.CREATETIME>=to_timestamp('").append(dto2.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		//SpareTime6:费用创建时间2
		if(dto2.getSpareTime6()!=null)
			selectStatement.append(" and fee.CREATETIME<=to_timestamp('").append(dto2.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
	}
	
	
	/*
	private ArrayList secondQuery(Collection list,CommonQueryConditionDTO dto2) throws ListHandlerException{
		if(list==null || list.size()==0)
			return null;
		
		ArrayList result=new ArrayList();
		Iterator itList=list.iterator();
		
		FinanceSetoffMap2AcctItemTypeWrap dto=null;
		
		Connection conn=null;
		Statement stmt=null;
		
		try{
			conn=HomeFactory.getFactory().getDataSource().getConnection();
			stmt=conn.createStatement();
			
			while(itList.hasNext()){
				FinanceSetOffMapDTO setoffDTO=(FinanceSetOffMapDTO)itList.next();
				//通过二次查找数据库
				StringBuffer secondSQL=contructSecondSQL(dto2,setoffDTO);
				ResultSet rs=stmt.executeQuery(secondSQL.toString());
				
				//支付关联表为支付表
				if(CommonConstDefinition.SETOFFREFERTYPE_P.equals(setoffDTO.getPlusReferType())){
					if(rs.next()){
						dto=new FinanceSetoffMap2AcctItemTypeWrap();
						dto.setSetOffDTO(setoffDTO);
						dto.setPayAcctItemType(rs.getString("NAME"));
						dto.setPaymentAmount(rs.getdouble("AMOUNT"));
						dto.setPaymentTime(rs.getTimestamp("paymentcreatetime"));
						dto.setFeeAcctItemType(rs.getString("AcctItemTypeName"));
						dto.setFeeAmount(rs.getdouble("VALUE"));
						dto.setFeeTime(rs.getTimestamp("feecreatetime"));
						
						result.add(dto);
					}
				}
				//支付关联表为预存抵扣表
				else if(CommonConstDefinition.SETOFFREFERTYPE_D.equals(setoffDTO.getPlusReferType())){
					if(rs.next()){
						dto=new FinanceSetoffMap2AcctItemTypeWrap();
						dto.setSetOffDTO(setoffDTO);
						dto.setPaymentCommonSettingName("SET_F_PREPAYMENTTYPE");
						dto.setPayCommonSettingValue(rs.getString("PREPAYMENTTYPE"));
						dto.setPaymentAmount(rs.getdouble("AMOUNT"));
						dto.setPaymentTime(rs.getTimestamp("prepaymentcreatetime"));
						dto.setFeeAcctItemType(rs.getString("AcctItemTypeName"));
						dto.setFeeAmount(rs.getdouble("VALUE"));
						dto.setFeeTime(rs.getTimestamp("feecreatetime"));
						
						result.add(dto);
					}
				}
				//支付关联表为费用表
				else if(CommonConstDefinition.SETOFFREFERTYPE_F.equals(setoffDTO.getPlusReferType())){
					dto=new FinanceSetoffMap2AcctItemTypeWrap();
					boolean isAdd=true;
					
					while(rs.next()){
						//读取支付记录
						if(setoffDTO.getPlusReferId()==rs.getInt("AI_NO")){
							if(!(dto2==null || "".equals(dto2.getSpareStr7()))){
								if(!dto2.getSpareStr7().equals(rs.getString("ACCTITEMTYPEID"))){
									isAdd=false;
									break;
								}
							}
							if(!isDateMiddleOf(rs.getTimestamp("CREATETIME"),dto2.getSpareTime3(),dto2.getSpareTime4())){
								isAdd=false;
								break;
							}
							dto.setPaymentAmount(rs.getdouble("VALUE"));
							dto.setPaymentTime(rs.getTimestamp("CREATETIME"));
							dto.setPayAcctItemType("AcctItemTypeName");
						}
						//读取费用记录
						else{
							if(!(dto2==null || "".equals(dto2.getSpareStr9()))){
								if(!dto2.getSpareStr9().equals(rs.getString("ACCTITEMTYPEID"))){
									isAdd=false;
									break;
								}
							}
							if(!isDateMiddleOf(rs.getTimestamp("CREATETIME"),dto2.getSpareTime5(),dto2.getSpareTime6())){
								isAdd=false;
								break;
							}
							dto.setFeeAmount(rs.getdouble("VALUE"));
							dto.setFeeTime(rs.getTimestamp("CREATETIME"));
							dto.setFeeAcctItemType("AcctItemTypeName");
						}
					}
					
					if(isAdd)
						result.add(dto);
				}
			}
		}
		catch(Exception e){
			LogUtility.log(clazz,LogLevel.WARN,"executeSearch",e);
	        throw new ListHandlerException("数据查找出错！");
		}
		return result;
	}
	
	private StringBuffer contructSecondSQL(CommonQueryConditionDTO dto3,FinanceSetOffMapDTO setoffDTO){
		StringBuffer sql=new StringBuffer();
		
		//支付关联表为支付表
		if(CommonConstDefinition.SETOFFREFERTYPE_P.equals(setoffDTO.getPlusReferType())){
			sql.append("select payment.seqNo,payment.AMOUNT,payment.CREATETIME as paymentcreatetime,payment.MOPID,mop.NAME,");
			sql.append("fee.AI_NO,fee.CREATETIME as feecreatetime,fee.VALUE, fee.ACCTITEMTYPEID,itemtype.AcctItemTypeName");
			sql.append(" from t_paymentRecord payment,T_AccountItem fee,T_AcctItemType itemtype,T_MethodOfPayment mop where ");
			sql.append(" payment.MOPID=mop.MOPID and fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID and ");
			sql.append("payment.seqNo=" + setoffDTO.getPlusReferId() +" and fee.AI_NO=" + setoffDTO.getMinusReferId());
			
			//SpareStr7:支付类型
			if(!(dto3.getSpareStr7()==null || "".equals(dto3.getSpareStr7())))
					sql.append(" and payment.MOPID=" + dto3.getSpareStr7());
			//SpareStr9:帐目类型
			if(!(dto3.getSpareStr9()==null || "".equals(dto3.getSpareStr9())))
				sql.append(" and fee.ACCTITEMTYPEID='" + dto3.getSpareStr9() + "'");
			
			//SpareTime3:支付时间1
			if(dto3.getSpareTime3()!=null)
				sql.append(" and payment.CREATETIME>=to_timestamp('").append(dto3.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime4:支付时间2
			if(dto3.getSpareTime4()!=null)
				sql.append(" and payment.CREATETIME<=to_timestamp('").append(dto3.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime5:费用创建时间1
			if(dto3.getSpareTime5()!=null)
				sql.append(" and fee.CREATETIME>=to_timestamp('").append(dto3.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime6:费用创建时间2
			if(dto3.getSpareTime6()!=null)
				sql.append(" and fee.CREATETIME<=to_timestamp('").append(dto3.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//支付关联表为预存抵扣表
		else if(CommonConstDefinition.SETOFFREFERTYPE_D.equals(setoffDTO.getPlusReferType())){
			sql.append("select prepayment.SEQNO,prepayment.AMOUNT,prepayment.CREATETIME as prepaymentcreatetime,prepayment.PREPAYMENTTYPE,");
			sql.append(" fee.AI_NO,fee.CREATETIME as feecreatetime,fee.VALUE, fee.ACCTITEMTYPEID,itemtype.AcctItemTypeName from ");
			sql.append(" T_PrePaymentDeductionRecord prepayment,T_AccountItem fee,T_AcctItemType itemtype ");
			sql.append(" where fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID and ");
			sql.append("prepayment.SEQNO=" + setoffDTO.getPlusReferId() + " and fee.AI_NO=" + setoffDTO.getMinusReferId());

			//SpareStr7:支付类型
			if(!(dto3.getSpareStr7()==null || "".equals(dto3.getSpareStr7())))
					sql.append(" and prepayment.PREPAYMENTTYPE='" + dto3.getSpareStr7() + "' ");
			//SpareStr9:帐目类型
			if(!(dto3.getSpareStr9()==null || "".equals(dto3.getSpareStr9())))
				sql.append(" and fee.ACCTITEMTYPEID='" + dto3.getSpareStr9() + "'");
			
			//SpareTime3:支付时间1
			if(dto3.getSpareTime3()!=null)
				sql.append(" and prepayment.CREATETIME>=to_timestamp('").append(dto3.getSpareTime3().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime4:支付时间2
			if(dto3.getSpareTime4()!=null)
				sql.append(" and prepayment.CREATETIME<=to_timestamp('").append(dto3.getSpareTime4().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime5:费用创建时间1
			if(dto3.getSpareTime5()!=null)
				sql.append(" and fee.CREATETIME>=to_timestamp('").append(dto3.getSpareTime5().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
			//SpareTime6:费用创建时间2
			if(dto3.getSpareTime6()!=null)
				sql.append(" and fee.CREATETIME<=to_timestamp('").append(dto3.getSpareTime6().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		}
		//支付关联表为费用表
		else if(CommonConstDefinition.SETOFFREFERTYPE_F.equals(setoffDTO.getPlusReferType())){
			sql.append("select fee.AI_NO,fee.CREATETIME,fee.VALUE, fee.ACCTITEMTYPEID,itemtype.AcctItemTypeName ");
			sql.append(" from T_AccountItem fee,T_AcctItemType itemtype where fee.ACCTITEMTYPEID=itemtype.ACCTITEMTYPEID");
			sql.append(" and (fee.AI_NO=" + setoffDTO.getPlusReferId() + " or fee.AI_NO=" + setoffDTO.getMinusReferId() + ")");
		}
		
		//输出拼出的SQL语句
		LogUtility.log(clazz,LogLevel.DEBUG,"销帐记录查询第二次查询SQL=" + sql.toString());
		return sql;
	}
	
	private boolean isDateMiddleOf(Timestamp in,Timestamp less,Timestamp great){
		if(in==null)
			return true;
		
		if(less==null && great==null)
			return true;
		
		if(less==null){
			if(in.compareTo(great)>0)
				return false;
			else
				return true;
		}
		
		if(great==null){
			if(in.compareTo(less)<0 )
				return false;
			else 
				return true;
		}
		
		if(less!=null && great !=null){
			if(in.compareTo(great)>0 || in.compareTo(less)<0 )
				return false;
			else 
				return true;
		}
		return true;
	}
	*/
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
