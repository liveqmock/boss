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

public class BatchPrepayListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = BatchPrepayListHandler.class;
	private List rowCountList =new ArrayList();
	
	public BatchPrepayListHandler(){
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
		StringBuffer begin = new StringBuffer();
		String      sql ="";	
		sql =sql  +" select cust.customerid,cust.name ,acc.accountid accountid,acc.accountname accountname "
			      +" ,b.balance " 
			      +" , cust.customertype  "
			      +" ,(case when cust.telephone is not null and cust.telephonemobile is not null "
                  +"        then cust.telephone || '/' || cust.telephonemobile "
                  +"        else cust.telephone ||  cust.telephonemobile end) tel "
                  +" ,SP_GETDETAILADDRESS(cust.addressid) address "
                  +" ,decode(cust.status,'N','正常','P','潜在','') CUSTSTATUS "
                  +" from t_account acc,t_address addr, ";
         //	所属区域
         if (dto.getSpareStr1() ==null){
        	 sql =sql +" (select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
                 +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1, ";	 
         } else{
        	 sql =sql +" (select dr.id from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr1()+") dir1, ";
         }
         sql =sql +" (Select  * From (Select   tc.customerid, (select nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where pr.custid=tc.customerid  and pr.status = 'V') "  
                  +"                                           - "  
                  +"                                          (select nvl(sum(ai.value),0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.custid =tc.customerid and ai.status in ('V','1','3'))  As balance "
                  +"                  From t_customer tc "
                  +"                  ) a ";
         // 帐户余额
         if (dto2.getSpareStr15() !=null && dto2.getSpareStr16() !=null){
 			sql =sql+"  Where a.balance Between "+dto2.getSpareStr15() +" And  "+ dto2.getSpareStr16()+") b, ";
 		 }else if (dto2.getSpareStr15() !=null){
 			sql =sql+"  Where a.balance >="+dto2.getSpareStr15()+") b, ";
 		 }else if (dto2.getSpareStr16() !=null){
 			sql =sql+"  Where a.balance <="+dto2.getSpareStr16()+") b, ";
 		 }else {
 			sql =sql+"  ) b,";
 		 }
               
         sql =sql +" t_customer cust ";
         sql =sql +" where 1=1 and b.customerid=cust.customerid and cust.customerid = acc.customerid  "
                  +" and acc.status != 'C' and cust.addressid = addr.addressid and addr.districtid =dir1.id ";
         //	帐户号
	     if (dto.getSpareStr5() !=null){
	    	    sql=sql+ " and acc.accountid in ("+dto.getSpareStr5()+") ";
	     } else{
	         //客户类型
	         if (dto.getSpareStr2() !=null){
	             sql=sql+ " and cust.customertype='"+dto.getSpareStr2()+"' ";
	         }
	    
	         //客户证号
	         if (dto.getSpareStr3() !=null){
	             sql=sql+ " and cust.customerid="+dto.getSpareStr3();
	         }
	    
	         // 帐户名称
	         if (dto.getSpareStr4() !=null){
	            sql=sql+ " and acc.accountname like ('%"+dto.getSpareStr4()+"%') ";
	         }
	         
	         // 收费终端状态(至少一个正常)
			 if("V".equals(dto.getSpareStr6())){
				 sql=sql+ " and exists ( select * from t_serviceAccount t6 where t6.customerid =cust.customerid "
						+ "              and t6.status ='N'  )";
			 }
		    //详细地址
		    if (dto.getSpareStr7() !=null){
		    	sql =sql + " and addr.detailaddress like '"+dto.getSpareStr7()+"%'";
		    }
		     
	    }     
			
	    /*
		// 设置构造取得当前查询总纪录数的sql
		setRecordCountQueryTable("("+ sql+")");
		*/
	     
		begin.append(sql);
        // 设置当前数据查询sql
	    appendOrderByString(begin);	
        // 设置当前数据查询sql
		setRecordDataQueryBuffer(begin);
		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
				
		if ("A".equals(orderByString)){
			selectStatement.append(" order by address,cust.customerid ");
		}else if ("C".equals(orderByString)){
			selectStatement.append(" order by cust.name,cust.customerid ");
		}else {
			selectStatement.append(" order by cust.customerid desc");
		}
		
	}
	
}
