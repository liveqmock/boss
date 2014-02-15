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
		// �����ѯ�ַ���
		constructSelectQueryString(dto);
		dao.setFrom(getFrom());
		dao.setTo(getTo());
		dao.setSpeedFlag(true);
		System.out.println("getFrom()----------------->"+getFrom());
		System.out.println("getTo()---------------->"+getTo());
		//ִ�����ݲ�ѯ
		executeSearch(dao);
		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}

	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		String tableName=" t_account a, t_customer b,t_address c,t_invoice d ";
		//	�������ѡ������ò���Ա������λ��Ͻ������
		if(dto.getSpareStr5()==null || "".equals(dto.getSpareStr5())){
			tableName =tableName+",(select dr.id,(select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =dr.id)) || '��' || (select t3.name from t_districtsetting t3 where t3.id =dr.id) �������� " 
					            +" from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id in  (select districtid from t_orggoverneddistrict where orgid ="
			                    +  BusinessUtility.getParentHasCustomerOrgID(getCurrentOperatorSubjectOrg())+")) dir1 ";
		}else {
			tableName =tableName+",(select dr.id,(select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =dr.id)) || '��' || (select t3.name from t_districtsetting t3 where t3.id =dr.id) �������� " 
					            +" from t_districtsetting dr connect by prior dr.id =dr.belongto start with dr.id ="+  dto.getSpareStr5()+") dir1 ";
		}
		
		StringBuffer begin=new StringBuffer();		
	    begin.append(" select a.accountid ,b.name, b.customerid");
		begin.append(" ,dir1.�������� || '��' || c.detailaddress ��ַ");
		begin.append(" ,sum(d.amount) �ʵ���� ");
		begin.append(" from " + tableName);
		StringBuffer selectStatement=new StringBuffer();
		selectStatement.append(" where 1=1");
		selectStatement.append(" and a.customerid =b.customerid and b.addressid =c.addressid and c.districtid =dir1.id ");
		selectStatement.append(" and a.status !='").append(CommonConstDefinition.ACCOUNT_STATUS_CLOSE).append("'");
        selectStatement.append(" and a.accountid =d.acctid ");
        selectStatement.append(" and d.status ='").append(CommonConstDefinition.INVOICE_STATUS_WAIT).append("'");
        
        //  �շ��ն�״̬(����һ������)
		if("V".equals(dto.getSpareStr4())){
		    selectStatement.append(" and exists (select * from t_serviceAccount t6 where t6.customerid =b.customerid and t6.status ='N')");
		}
        
        boolean selectFlag =false;
		// �û�֤��
        if (dto.getCustomerID() !=0){
		    makeSQLByIntField(" b.customerid",dto.getCustomerID(),selectStatement);
		    selectFlag =true;
        }
        
        // �ʻ���
		if(dto.getSpareStr7()!=null){
			selectStatement.append(" and a.accountid in ('")
			               .append(parseCustomerId(dto.getSpareStr7())).append("')");
			selectFlag =true;
		}
		
		if (!selectFlag){
           // �ͻ�����
		   if(dto.getSpareStr1()!=null){
		      selectStatement.append(" and b.customertype='").append(dto.getSpareStr1()).append("'");
		   }
		   // �ͻ�����
		   if(dto.getSpareStr3()!=null){
		      selectStatement.append(" and b.name like '"+dto.getSpareStr3()+"%'");
		   }
		
           // ��ϸ��ַ
		   if (dto.getSpareStr6() !=null){
		      selectStatement.append(" and c.detailaddress like '"+dto.getSpareStr6()+"%'");
		   }
		   // �̶��绰
		   if (dto.getSpareStr8() !=null){
			   selectStatement.append(" and b.telephone ='").append(dto.getSpareStr8()).append("'");
		   }
		   //�ƶ��绰
		   if (dto.getSpareStr9() !=null){
			   selectStatement.append(" and b.telephonemobile ='").append(dto.getSpareStr9()).append("'");
		   }
		   
		} 
		 
		 begin.append(selectStatement.toString()); 
		 
		 begin.append(" group by a.accountid,b.name,b.customerid,dir1.�������� || '��' || c.detailaddress  " 
		 		     +" having sum(d.amount) >"+dto2.getSpareStr15());
		 
		 if (dto2.getSpareStr16() !=null)
	         begin.append(" and sum(d.amount) <="+dto2.getSpareStr16());
		 
		 /*
         //	���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		 setRecordCountQueryTable("("+begin.toString()+")");
		 */
         // ���õ�ǰ���ݲ�ѯsql
	     appendOrderByString(begin);	
	     // ���õ�ǰ���ݲ�ѯsql
		 setRecordDataQueryBuffer(begin); 
	}

	
	private String parseCustomerId(String spareStr3) {
		return spareStr3.replaceAll(";","','");		
	}
	
	private void appendOrderByString(StringBuffer selectStatement) {		
		String orderByString = dto.getOrderField();
				
		if ("A".equals(orderByString)){
			selectStatement.append(" order by ��ַ,b.customerid desc ");
		}else if ("C".equals(orderByString)){
			selectStatement.append(" order by b.name ");
		}else {
			selectStatement.append(" order by b.customerid desc");
		}
		
	}
    
}
