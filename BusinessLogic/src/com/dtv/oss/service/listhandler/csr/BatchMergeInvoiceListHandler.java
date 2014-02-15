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

public class BatchMergeInvoiceListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto=null;
	private GenericImpDAO dao = null;
	private List rowCountList =new ArrayList();
	
	public BatchMergeInvoiceListHandler(){
		dao=new GenericImpDAO(rowCountList);
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(BatchMergeInvoiceListHandler.class, LogLevel.DEBUG,
				"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(BatchMergeInvoiceListHandler.class, LogLevel.DEBUG,
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
		String tableName=" t_account a, t_customer b,t_address c,t_invoice d ";
		//	如果区域不选择关联该操作员所属单位管辖的区域
		if(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())){
			tableName =tableName+",(select dr.id,(select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =dr.id)) || '◇' || (select t3.name from t_districtsetting t3 where t3.id =dr.id) 区域名称 " 
					            +" from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                    +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 ";
		}else {
			tableName =tableName+",(select dr.id,(select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =dr.id)) || '◇' || (select t3.name from t_districtsetting t3 where t3.id =dr.id) 区域名称 " 
					            +" from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 ";
		}
		
		StringBuffer begin=new StringBuffer();		
	    begin.append(" select a.accountid ,b.name, b.customerid");
		begin.append(" ,dir1.区域名称 || '◇' || c.detailaddress 地址");
		begin.append(" ,sum(d.amount) 帐单金额 ");
		begin.append(" from " + tableName);
		StringBuffer selectStatement=new StringBuffer();
		selectStatement.append(" where 1=1");
		selectStatement.append(" and a.customerid =b.customerid and b.addressid =c.addressid and c.districtid =dir1.id ");
		selectStatement.append(" and a.status !='").append(CommonConstDefinition.ACCOUNT_STATUS_CLOSE).append("'");
        selectStatement.append(" and a.accountid =d.acctid ");
        selectStatement.append(" and d.status ='").append(CommonConstDefinition.INVOICE_STATUS_WAIT).append("'");
        
        //  收费终端状态(至少一个正常)
		if("V".equals(dto.getSpareStr4())){
		    selectStatement.append(" and exists (select * from t_serviceAccount t6 where t6.customerid =b.customerid and t6.status ='N')");
		}
        
        boolean selectFlag =false;
		// 用户证号
        if (dto.getCustomerID() !=0){
		    makeSQLByIntField(" b.customerid",dto.getCustomerID(),selectStatement);
		    selectFlag =true;
        }
        
        // 帐户号
		if(dto.getSpareStr7()!=null){
			selectStatement.append(" and a.accountid in ('")
			               .append(parseCustomerId(dto.getSpareStr7())).append("')");
			selectFlag =true;
		}
		
		if (!selectFlag){
           // 客户类型
		   if(dto.getSpareStr1()!=null){
		      selectStatement.append(" and b.customertype='").append(dto.getSpareStr1()).append("'");
		   }
		   // 客户姓名
		   if(dto.getSpareStr3()!=null){
		      selectStatement.append(" and b.name like '"+dto.getSpareStr3()+"%'");
		   }
		
           // 详细地址
		   if (dto.getSpareStr6() !=null){
		      selectStatement.append(" and c.detailaddress like '"+dto.getSpareStr6()+"%'");
		   }
		   // 固定电话
		   if (dto.getSpareStr8() !=null){
			   selectStatement.append(" and b.telephone ='").append(dto.getSpareStr8()).append("'");
		   }
		   //移动电话
		   if (dto.getSpareStr9() !=null){
			   selectStatement.append(" and b.telephonemobile ='").append(dto.getSpareStr9()).append("'");
		   }
		   
		} 
		 
		 begin.append(selectStatement.toString()); 
		 
		 begin.append(" group by a.accountid,b.name,b.customerid,dir1.区域名称 || '◇' || c.detailaddress  " 
		 		     +" having sum(d.amount) >"+dto2.getSpareStr15());
		 
		 if (dto2.getSpareStr16() !=null)
	         begin.append(" and sum(d.amount) <="+dto2.getSpareStr16());
		 
		 /*
         //	设置构造取得当前查询总纪录数的sql
		 setRecordCountQueryTable("("+begin.toString()+")");
		 */
         // 设置当前数据查询sql
	     appendOrderByString(begin);	
	     // 设置当前数据查询sql
		 setRecordDataQueryBuffer(begin); 
	}

	
	private String parseCustomerId(String spareStr3) {
		return spareStr3.replaceAll(";","','");		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
				
		if ("A".equals(orderByString)){
			selectStatement.append(" order by 地址,b.customerid desc ");
		}else if ("C".equals(orderByString)){
			selectStatement.append(" order by b.name ");
		}else {
			selectStatement.append(" order by b.customerid desc");
		}
		
	}
    
}
