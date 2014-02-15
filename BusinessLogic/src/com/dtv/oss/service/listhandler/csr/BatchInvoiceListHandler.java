package com.dtv.oss.service.listhandler.csr;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO; 
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;

import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BatchInvoiceListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto=null;
	private GenericImpDAO dao = null;
	private List rowCountList =new ArrayList();
	
	public BatchInvoiceListHandler(){
		dao=new GenericImpDAO(rowCountList);
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(BatchInvoiceListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(BatchInvoiceListHandler.class, LogLevel.DEBUG,
				"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		// 构造查询字符串
		constructSelectQueryString(dto);
		dao.setFrom(getFrom());
		dao.setTo(getTo());
		dao.setSpeedFlag(true);
		System.out.println("getFrom()----------------->"+getFrom());
		System.out.println("getTo()---------------->"+getTo());
		//执行数据查询
		executeSearch(dao);
		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		String tableName=" t_invoice a,t_customer b,t_address c ";
		//	如果区域不选择关联该操作员所属单位管辖的区域
		if(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())){
			tableName =tableName+",(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                    +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 ";
		}else {
			tableName =tableName+",(select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 ";
		}
		
		StringBuffer begin=new StringBuffer();
		begin.append("select a.invoiceno , a.custid,b.name,a.acctid ");
		begin.append("    , ((select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =c.districtid))" 
				   +" || '◇' || (select t3.name from t_districtsetting t3 where t3.id =c.districtid)"
				   +" || '◇' || c.detailaddress) 地址");
		begin.append("    , a.invoiceCycleID ,  a.amount ");
		begin.append(" from " + tableName);
		StringBuffer selectStatement=new StringBuffer();
		selectStatement.append(" where 1=1");
		selectStatement.append(" and a.custid =b.customerid and b.addressid =c.addressid and c.districtid =dir1.id ");
		selectStatement.append(" and a.status='").append(CommonConstDefinition.INVOICE_STATUS_WAIT).append("'");
        // 批量支付
		if(dto.getSpareStr3()!=null){
			selectStatement.append(" and a.invoiceno in ('")
			               .append(parseInvoiceNo(dto.getSpareStr3())).append("')");
		}else{
		   // 用户证号
		   makeSQLByIntField(" a.custid",dto.getCustomerID(),selectStatement);
           // 用户类型
		   if(dto.getSpareStr1()!=null){
			  selectStatement.append(" and b.customertype='").append(dto.getSpareStr1()).append("'");
		   }
		   // 帐务周期
		   if(dto.getBeginStr()!=null&&dto.getEndStr()!=null){
			 selectStatement.append(" and a.invoicecycleid in (select bill.id from t_billingcycle bill where bill.name between '")
			                .append(dto.getBeginStr()).append("' and '")
			                .append(dto.getEndStr()).append("')");
		   }else if(dto.getBeginStr()!=null){
			 selectStatement.append(" and a.invoicecycleid in (select bill.id from t_billingcycle bill where  bill.name>='")
			                .append(dto.getBeginStr()).append("')");
		   }else if(dto.getEndStr()!=null){
			 selectStatement.append(" and a.invoicecycleid in (select bill.id from t_billingcycle bill where bill.name<='")
			                .append(dto.getEndStr()).append("')");
		   }
		   // 付费类型
		   if(dto.getSpareStr2()!=null){
			 selectStatement.append(" and a.acctid in (select acct.accountid from t_account acct where acct.mopid=")
			                .append(Integer.parseInt(dto.getSpareStr2())).append(")");
		   }
				
		   // 收费终端状态(至少一个正常)
		   if("V".equals(dto.getSpareStr4())){
			  selectStatement.append(" and exists ( select * from t_serviceAccount t6 where t6.customerid =a.custid "
					                +"              and t6.status ='N' )");
		   }
		
           //  帐户余额
		   if (dto2.getSpareStr15() !=null || dto2.getSpareStr16() !=null){
			  if (dto2.getSpareStr15() !=null){
				 selectStatement.append(" and (");
				 selectStatement.append("(select nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where pr.custid =a.custid  and pr.status = 'V') ");
				 selectStatement.append(" - ");
				 selectStatement.append(" (select nvl(sum(ai.value),0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.custid =a.custid and ai.status in ('V','1','3')) ");
				 selectStatement.append(" ) >="+dto2.getSpareStr15() );
			     if (dto2.getSpareStr16() !=null){
				    selectStatement.append(" and (");
				    selectStatement.append("(select nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where pr.custid =a.custid  and pr.status = 'V') ");
				    selectStatement.append(" - ");
				    selectStatement.append(" (select nvl(sum(ai.value),0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.custid =a.custid and ai.status in ('V','1','3')) ");
				    selectStatement.append(" ) <="+dto2.getSpareStr16()  );
			     }
			  } else{
				 selectStatement.append(" and (");
				 selectStatement.append("(select nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where pr.custid =a.custid  and pr.status = 'V') ");
				 selectStatement.append(" - ");
				 selectStatement.append(" (select nvl(sum(ai.value),0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.custid =a.custid and ai.status in ('V','1','3')) ");
				 selectStatement.append(" ) <="+dto2.getSpareStr16()  );
			 }
	       }
		   
		   //详细地址
		   if (dto.getSpareStr6() !=null){
		     selectStatement.append(" and c.detailaddress like '"+dto.getSpareStr6()+"%'");
		   }
		   
		   //帐户证号
		   if (dto.getSpareStr7() !=null){
			 selectStatement.append(" and a.acctid ="+dto.getSpareStr7());
		   }
		}
		
		/*
       // 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable("("+begin.append(selectStatement.toString())+")");    
		*/  
		// 设置当前数据查询sql
		begin.append(selectStatement.toString());
		appendOrderByString(begin);	
		setRecordDataQueryBuffer(begin);		
	}

	private String parseInvoiceNo(String spareStr3) {
		return spareStr3.replaceAll(";","','");		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
				
		if ("A".equals(orderByString)){
			selectStatement.append(" order by 地址,a.custid,a.invoicecycleid desc ");
		}else if("C".equals(orderByString)){
			selectStatement.append(" order by b.name,a.custid,a.invoicecycleid desc ");
		}else {
			selectStatement.append(" order by a.invoicecycleid ,a.custid desc");
		}
		
	}

}
