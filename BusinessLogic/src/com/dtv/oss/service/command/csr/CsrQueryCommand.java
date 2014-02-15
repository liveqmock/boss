/*
 * Created on 2004-8-9
 *
 * @author Mac Wang
 */
package com.dtv.oss.service.command.csr;
import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.csr.AccountItemListHandler;
import com.dtv.oss.service.listhandler.csr.AccountListHandler;
import com.dtv.oss.service.listhandler.csr.AccountPrepaymentDeductionListHandler;
import com.dtv.oss.service.listhandler.csr.AdjustAccountListHandler;
import com.dtv.oss.service.listhandler.csr.BatchInvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.BookProductCSIListHandler;
import com.dtv.oss.service.listhandler.csr.CATVListHandler;
import com.dtv.oss.service.listhandler.csr.CampaignListhandler;
import com.dtv.oss.service.listhandler.csr.CatvJobCardListHandler;
import com.dtv.oss.service.listhandler.csr.CatvTerminalListHandler;
import com.dtv.oss.service.listhandler.csr.CsiProcessLogListHandler;
import com.dtv.oss.service.listhandler.csr.CustComplainListhandler;
import com.dtv.oss.service.listhandler.csr.CustComplainProcessListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceAccountListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.CustServiceInteractionOnlyListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerBillingRuleListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerPhoneNoListHandler;
import com.dtv.oss.service.listhandler.csr.CustomerProductListHandler;
import com.dtv.oss.service.listhandler.csr.FeeRecordAllListHandler;
import com.dtv.oss.service.listhandler.csr.FiberNodeListHandler;
import com.dtv.oss.service.listhandler.csr.ForcedDepositListHandler;
import com.dtv.oss.service.listhandler.csr.GroupCustListhandler;
import com.dtv.oss.service.listhandler.csr.InvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.OneCustomerListHandler;
import com.dtv.oss.service.listhandler.csr.PackageLineListhandler;
import com.dtv.oss.service.listhandler.csr.PaymentRecordAllListHandler;
import com.dtv.oss.service.listhandler.csr.PaymentRecordListHandler;
import com.dtv.oss.service.listhandler.csr.PhoneNoListHandler;
import com.dtv.oss.service.listhandler.csr.PointsActivityListHandler;
import com.dtv.oss.service.listhandler.csr.PointsGoodsListHandler;
import com.dtv.oss.service.listhandler.csr.PointsRecordListHandler;
import com.dtv.oss.service.listhandler.csr.PrepaymentDeductionRecordListHandler;
import com.dtv.oss.service.listhandler.csr.ProdcutListHandler;
import com.dtv.oss.service.listhandler.csr.ProductPackageListhandler;
import com.dtv.oss.service.listhandler.csr.QueryCustomerBillingRuleListHandler;
import com.dtv.oss.service.listhandler.csr.RealIncomeListHandler;
import com.dtv.oss.service.listhandler.csr.SATerminalDeviceListHandler;
import com.dtv.oss.service.listhandler.csr.ServiceAccountForScheduleListHandler;
import com.dtv.oss.service.listhandler.csr.ServiceAccountListHandler;
import com.dtv.oss.service.listhandler.csr.SetoffRecordListHandler;
import com.dtv.oss.service.listhandler.csr.SimpleCustServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.SimpleCustServiceInteractionViewListHandler;
import com.dtv.oss.service.listhandler.csr.TerminateDeviceListhandler;
import com.dtv.oss.service.listhandler.csr.CAWalletChargeRecordListHandler;
import com.dtv.oss.service.listhandler.csr.CAWalletServiceInteractionListHandler;
import com.dtv.oss.service.listhandler.csr.CAWalletListHandler;
import com.dtv.oss.service.listhandler.csr.BatchPrepayListHandler;
import com.dtv.oss.service.listhandler.csr.BatchMergeInvoiceListHandler;
import com.dtv.oss.service.listhandler.csr.OweSuspendListHandler;

public class CsrQueryCommand extends QueryCommand {
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch(event.getType()) {
			//�û���ѯ
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER:
				handler = new CustomerListHandler();
				break;
            //�ͻ��绰�����ѯ
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER_PHONENO:
				handler = new CustomerPhoneNoListHandler();
				break;	
			//��ѯ��һ�û���Ϣ	
			case CsrQueryEJBEvent.QUERY_TYPE_ONECUSTOMER:
				handler = new OneCustomerListHandler();
				break;
			//�ͻ�Ͷ��
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTCOMPLAIN:
	        	handler=new CustComplainListhandler();
	        	break;	
	        //��ѯ�����û����ӿͻ�
			case CsrQueryEJBEvent.QUERY_GROUP_CUST:
				handler = new GroupCustListhandler();
				break;
            //��ѯҵ���ʻ���Ϣ���ݿͻ�
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_BY_CUST:
				handler = new CustServiceAccountListHandler();
				break;				
			//��ѯҵ���ʻ���Ϣ
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT:
				handler = new ServiceAccountListHandler();
				break;			
			//lookup info of accout add by chen jiang ,date 2005/10/28
			case CsrQueryEJBEvent.QUERY_TYPE_ACCOUNT:
				handler = new AccountListHandler();
			    break;
			
			//���ҿɹ���Ĳ�Ʒ���� add by zhouxushun ,date 2005-10-10
			case CsrQueryEJBEvent.QUERY_TYPE_CAN_PURCHASE_PRODUCTPACKAGE:
				handler=new  ProductPackageListhandler();
				break;
			//���Ҳ�Ʒ���Ĳ�Ʒ��Ϣ�� add by zhouxushun , date :2005-10-10
			case CsrQueryEJBEvent.QUERY_TYPE_CURRENT_PACKAGE:
				handler=new PackageLineListhandler();
				break;
			//�����Ż���Ϣ�� add by zhouxushun , date 2005-10-11 
			case CsrQueryEJBEvent.QUERY_TYPE_CAMPAIGN:
				handler=new CampaignListhandler();
				break;
			//�����ն��豸�� add by zhouxushun , date :2005-10-11
			case CsrQueryEJBEvent.QUERY_TYPE_TERMINALDEVICE:
				handler=new TerminateDeviceListhandler();
				break;
			//ͨ���û���ַ��catvid�����ն��û� add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CATV:
	            handler =new CATVListHandler();
			    break;               
			//ͨ��ͨ�õ�CommonQueryConditionDTO����ԤԼ�� add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION:
				handler = new CustServiceInteractionListHandler();
				break;
		    //ͨ��ͨ�õ�CommonQueryConditionDTO������������־��Ϣ add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CSI_PROCESS_LOG:
            	handler =new CsiProcessLogListHandler();
                break;
            //ͨ��ͨ�õ�CommonQueryConditionDTO����������¼ add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTION:
				handler = new SimpleCustServiceInteractionListHandler();
				break;
			//�����ʵ���¼ add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CUSTINVOICE:
				handler = new InvoiceListHandler();
				break;
		    //�����ʵ�֧����¼ add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_PAYMENT_RECORD:
				handler = new PaymentRecordListHandler();
				break;	
			//�����ʵ����ü�¼ add by david.Yang
            case CsrQueryEJBEvent.QUERY_TYPE_CUSTACCOUNTITEM_RECORD:
            	handler =new AccountItemListHandler();
            	break;
            //���ҿͻ���Ʒ add by david.Yang
			case CsrQueryEJBEvent.QUERY_TYPE_CUSTPRODUCT:
				handler = new CustomerProductListHandler();
				break;
			//�����ʻ���Ԥ��ֿ� add by david.yang
			case CsrQueryEJBEvent.QUERY_TYPE_PREPAYMENTDEDUCTION:
				handler = new AccountPrepaymentDeductionListHandler();
				break;
    		//����Ѻ���¼	
            case CsrQueryEJBEvent.QUERY_TYPE_FORCED_DEPOSIT:
				handler = new ForcedDepositListHandler();
				break;
            //���ֻ��ѯ	
            case CsrQueryEJBEvent.QUERY_POINTS_ACTIVITY:
				handler = new PointsActivityListHandler();
				break;
            case CsrQueryEJBEvent.QUERY_POINTS_GOODS:
				handler = new PointsGoodsListHandler();
				break;	
            case CsrQueryEJBEvent.QUERY_POINTS_RECORD:
            	handler = new PointsRecordListHandler();
            	break;		
            	
		    //��ѯ���ʼ�¼ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_ADJUST_ACCOUNT:
	        	handler=new AdjustAccountListHandler();
	        	break;
			//��ѯ���ü�¼ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_FEE_RECORD:
	        	handler=new FeeRecordAllListHandler();
	        	break;
	        //��ѯ֧����¼ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PAMENTT_RECORD:
	        	handler=new PaymentRecordAllListHandler();
	        	break;
	        //Ԥ��ֿۼ�¼��ѯ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PREPAYMENT_DEDUCTION_RECORD:
	        	handler=new PrepaymentDeductionRecordListHandler();
	        	break;
	        //���ʼ�¼��ѯ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_SETOFF_RECORD:
	        	handler=new SetoffRecordListHandler();
	        	break;
	        	  //���ʼ�¼��ѯ add by jason.zhou
	        case CsrQueryEJBEvent.QUERY_PRODUCT:
	        	handler=new ProdcutListHandler();
	        	break; 	
	        case CsrQueryEJBEvent.QUERY_TYPE_PHONENO:
	        	handler=new PhoneNoListHandler();
	        	break; 
	        case CsrQueryEJBEvent.QUERY_SERVICEACCOUNT_TERMINALDEVICE:
	        	handler=new SATerminalDeviceListHandler();
	        	break;	
	        case CsrQueryEJBEvent.QUERY_COMPLAIN_PROCESS:
	        	handler=new CustComplainProcessListHandler();
	        	break;
	        //��װ������ӡ
	        case CsrQueryEJBEvent.QUERY_TYPE_CUSTSERVICEINTERACTION_ONLY:
	        	handler=new CustServiceInteractionOnlyListHandler();
	        	break;
	        //�����ʵ���ѯ
	        case CsrQueryEJBEvent.QUERY_BATCH_INVOICE:
	        	handler=new BatchInvoiceListHandler();
	        	break;
	       	 //�ʵ��ϲ�������ѯ
	        case CsrQueryEJBEvent.QUERY_MERGE_BATCH_INVOICE:
	        	handler=new BatchMergeInvoiceListHandler();
	        	break;
	        //�����ͻ���Ʒ������ѯ
	        case CsrQueryEJBEvent.QUERY_BOOK_PRODUCT_CUSTSERVICEINTERACTION:
	        	handler=new BookProductCSIListHandler();
	        	break;
	        //�ͻ����Ի����ʲ�ѯ
	        case CsrQueryEJBEvent.QUERY_CUSTOMER_BILLINGRULE:
	        	handler=new CustomerBillingRuleListHandler();
	        	break;
	        //�ͻ����Ի����ʲ�ѯ 2(�ӿͻ��������������-->���Ի����� ���)
	        case CsrQueryEJBEvent.QUERY_CUSTOMER_BILLING_RULE:
	        	handler=new QueryCustomerBillingRuleListHandler();
	        	break;
	        //ģ���ն˲�ѯ
	        case CsrQueryEJBEvent.QUERY_TYPE_CatvTerminal:
	        	handler=new CatvTerminalListHandler();
	        	break;
	        //��ڵ��ѯ
	        case CsrQueryEJBEvent.QUERY_TYPE_FiberNode:
	        	handler=new FiberNodeListHandler();
	        	break;
	        //ģ�⹤����ѯ
	        case CsrQueryEJBEvent.QUERY_TYPE_JOBCARD_CATV:
	        	handler=new CatvJobCardListHandler();
	        	break;
	        //��ѯҵ���ʻ���Ϣ for �ų�
			case CsrQueryEJBEvent.QUERY_TYPE_SERVICE_ACCOUNT_FOR_SCHEDULE:
				handler = new ServiceAccountForScheduleListHandler();
				break;
			//СǮ����ֵ��¼��ѯ
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_CHARGE:
				handler = new CAWalletChargeRecordListHandler();
				break;
			//СǮ��ҵ���ʻ���ѯ
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET_Service_Interaction:
				handler = new CAWalletServiceInteractionListHandler();
				break;
			//СǮ��ҵ���ʻ���ѯ
			case CsrQueryEJBEvent.QUERY_TYPE_CA_WALLET:
				handler = new CAWalletListHandler();
				break;	
			//������ѯ��ͨ����ͼ���Ż� 
            case CsrQueryEJBEvent.QUERY_TYPE_SIMPLECUSTSERVICEINTERACTIONVIEW:
				handler = new SimpleCustServiceInteractionViewListHandler();	
				break;
			//�ֽ�����ϸ����ϼ�
			case CsrQueryEJBEvent.QUERY_CASH_FLOW_COUNT:
				handler = new RealIncomeListHandler();
				break;
			//ͣ��,Ƿ�Ѳ�ѯ
			case CsrQueryEJBEvent.QUERY_OWE_SUSPEND_CUSTOMER:
				handler =new OweSuspendListHandler();
				break;
				//����Ԥ���ѯ
			case CsrQueryEJBEvent.QUERY_BATCH_PREPAY_ACCOUNT:
				handler =new BatchPrepayListHandler();
				break;		
			default:
				break;
		}
		return handler;
	}
}
